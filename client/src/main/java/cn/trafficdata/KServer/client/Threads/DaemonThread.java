package cn.trafficdata.KServer.client.Threads;

import cn.trafficdata.KServer.client.Controller.Controller;
import cn.trafficdata.KServer.client.Utils.DailUtils;
import cn.trafficdata.KServer.client.Utils.SchemaManager;
import cn.trafficdata.KServer.client.configurable.Environment;
import cn.trafficdata.KServer.common.model.*;
import cn.trafficdata.KServer.common.utils.KLog;
import cn.trafficdata.KServer.common.utils.KryoSerializableUtil;
import cn.trafficdata.KServer.common.utils.UrlUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/11/7.
 */
public class DaemonThread implements Runnable {
    public static boolean pauseSwitch = false;
    public static Map<String, String> readyForDailingMap = new HashMap<>();
    public static Map<String, Thread> ThreadPool = new HashMap<String, Thread>(Environment.getMaxThreads());


    public DaemonThread() {
        Thread.currentThread().setName("DaemonThread");

    }

    public void run() {
        while (true) {
            //判断是否需要重启adsl,本类中设置public boolean,用于执行线程操作
            if (pauseSwitch) {
                //然后循环等待所有线程进入等待重启状态
                boolean ready = false;
                while (!ready) {
                    sleep(2);
                    for (String domain : Controller.WebUrlListMap.keySet()) {
                        String readyStr = readyForDailingMap.get(domain);
                        if (readyStr != null) {
                            if (readyStr.equals("ready")) {
                                ready = true;
                            } else {
                                ready = false;
                            }
                        }
                    }
                }
                //重启
                DailUtils.dailAdsl();
                //重启完成后,改变本类的开关,并删除所有就绪状态,执行线程监测到可以继续下载时,关闭等待重启状态
                //少个是否连接成功的测试
                pauseSwitch = false;
                readyForDailingMap.clear();
            } else {
                sleep(10);
                //1.检查是否需要提交结果获取新任务
                if (getWebUrlTotal() < Environment.MinWebUrlTotal || getPageResultList() > Environment.MaxPageTotal) {
                    //提交pageResultList中内容并请求新任务
                    try {
                        TaskMessage taskMessage = submitPagesToserver();
                        if (taskMessage != null) {
                            //解析任务,并将任务放在Controller的weburlListMap中
                            //暂不作处理命令
                            String command = taskMessage.getCommand();
                            Map<String, HttpClientConfig> httpClientConfigHashMap = taskMessage.getHttpClientConfigHashMap();
                            processHttpClientConfigHashMap(httpClientConfigHashMap);
                            List<WebUrl> webUrls = taskMessage.getWebUrls();
                            for (WebUrl webUrl : webUrls) {
                                //分配任务
                                checkDomainAndPushToWebUrlListMap(webUrl);
                            }
                        }
                    }catch (Exception e){

                    }
                } else {
                    for (String domain : Controller.WebUrlListMap.keySet()) {
                        Thread thread = ThreadPool.get(domain);
                        if (thread != null) {
                            if (!thread.isAlive()) {
                                ThreadPool.remove(domain);
                            }
                        }
                    }
                    for (String domain : Controller.WebUrlListMap.keySet()) {
                        Thread thread = ThreadPool.get(domain);
                        if (thread == null && ThreadPool.size() < Environment.MaxThreads) {
                            thread = new Thread(new DownloadThread(domain));
                            ThreadPool.put(domain, thread);
                            thread.start();
                        }
                    }
                }
            }
        }
    }

    protected static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {
            // Do nothing
        }
    }

    private static int getWebUrlTotal() {
        int total = 0;
        for (String domain : Controller.WebUrlListMap.keySet()) {
            total = +Controller.WebUrlListMap.get(domain).size();
        }
        return total;
    }

    private static int getPageResultList() {
        return Controller.PageResultList.size();
    }


    private static TaskMessage submitPagesToserver() {
        String ip = Environment.getHost_ip();
        int port = Environment.getHost_port();
        //1.将pages和本机信息包装成ResultMessage对象
        List<Page> waitForSendPageList = new ArrayList<Page>();
        for (int i = 0; i < Controller.PageResultList.size(); i++) {
            waitForSendPageList.add(Controller.PageResultList.remove(0));
        }
        ResultMessage rm = new ResultMessage();
        rm.setFinishTasks(waitForSendPageList);
        rm.setUnfinishTasks(webUrlListMap2StrList(Controller.WebUrlListMap));
        rm.setHostInfo(new HostInfo(Environment.localhostName, Environment.markcode, SchemaManager.getSchemaTypeString(), Environment.MaxThreads, Controller.LogList));
        Socket socket = null;
        TaskMessage taskMessage = null;

        try {
            socket = new Socket(ip, port);
            KLog.printJson(rm);
            KryoSerializableUtil.sendMessage(socket.getOutputStream(), rm);
//            socket.shutdownOutput();
            taskMessage = KryoSerializableUtil.readObjectFromInputStream(socket.getInputStream(), TaskMessage.class);
//            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return taskMessage;

    }

    private static List<String> webUrlListMap2StrList(Map<String, List<WebUrl>> webUrlListMap) {
        List<String> webStrs = new ArrayList<String>();
        for (Map.Entry<String, List<WebUrl>> webUrlList : webUrlListMap.entrySet()) {
            List<WebUrl> webUrls = webUrlList.getValue();
            for (WebUrl webUrl : webUrls) {
                webStrs.add(webUrl.getUrl());
            }
        }
        return webStrs;
    }

    private static void checkDomainAndPushToWebUrlListMap(WebUrl webUrl) {
        String domain = UrlUtils.getDomain(webUrl.getUrl());
        List<WebUrl> webUrls = Controller.WebUrlListMap.get(domain);
        if (webUrls == null) {
            Controller.WebUrlListMap.put(domain, new ArrayList<WebUrl>());
        }
        Controller.WebUrlListMap.get(domain).add(webUrl);
    }

    private static void processHttpClientConfigHashMap(Map<String, HttpClientConfig> httpClientConfigHashMap) {
        for (Map.Entry<String, HttpClientConfig> map : httpClientConfigHashMap.entrySet()) {
            Environment.httpClientConfigHashMap.put(map.getKey(), map.getValue());
        }


    }
}
