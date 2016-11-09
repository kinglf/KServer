package cn.trafficdata.KServer.client.Controller;

import cn.trafficdata.KServer.client.Threads.DaemonThread;
import cn.trafficdata.KServer.common.model.LogMessage;
import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.WebUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/24.
 */
public class Controller {
    //所有任务都放在这里
    public static Map<String,List<WebUrl>> WebUrlListMap=new HashMap<String, List<WebUrl>>();
    //所有结果都放在这里
    public static List<Page> PageResultList=new ArrayList<Page>();
    //所有需要传输的日志都放在这里
    public static List<LogMessage> LogList=new ArrayList<LogMessage>();









    public static void main(String[] args) throws InterruptedException {
        //总入口
        /**
         * 1.配置基础信息  在Environment中static配置.使用时直接调用
         * 2.检查redis中是否有任务,如果有任务则放入恢复模式,配置及其他
         */

        //3.启动守护线程,然后通过守护线程去管理子线程以及请求任务,但是各种信息的存储都放在本类中.
        Thread deamonThread=new Thread(new DaemonThread());
        deamonThread.start();
        while (true){
            if (!deamonThread.isAlive()){
                deamonThread.start();
            }
            Thread.sleep(3*1000);//每半小时检测一次守护线程是否存活,如果不存活则激活
        }
    }

}
