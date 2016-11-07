package cn.trafficdata.KServer.server.service;

import cn.trafficdata.KServer.server.configurable.Field;
import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.Project;
import cn.trafficdata.KServer.common.model.TaskMessage;
import cn.trafficdata.KServer.common.model.WebUrl;
import cn.trafficdata.KServer.server.utils.MessageUtil;
import com.alibaba.fastjson.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class TaskService {
    /**
     * 没写完
     *
     * @param unFinshTaskDomainNumMap
     * @return
     */
    private static List<WebUrl> getNewWebUrlList(Map<String, Integer> unFinshTaskDomainNumMap, int threads) {
        List<WebUrl> webUrls = new ArrayList<WebUrl>();
//        unFinshTaskDomainNumMap
        //根据domain->数量的map   请求相关任务
        for (Map.Entry<String, Integer> map : unFinshTaskDomainNumMap.entrySet()) {
            List<WebUrl> newTasks = RedisServerImpl.getNewTask(map.getKey(), Field.MaxWebUrlNumEveryDomainEveryClient - map.getValue());
            webUrls.addAll(newTasks);
        }
        if (unFinshTaskDomainNumMap.size() < threads) {
            int retryNum = 0;//重试次数
            //循环请求不在unFinshTaskDomainNumMap中的domain 的任务
            List<Project> unFinishProjectList = MongoDBServiceImpl.getUnFinishProjectList();
            while (unFinshTaskDomainNumMap.size() < threads && retryNum <= 5) {
                try {
                    Project project = unFinishProjectList.remove(0);
                    if (unFinshTaskDomainNumMap.get(project.getDomain()) == null) {
                        List<WebUrl> newTask = RedisServerImpl.getNewTask(project.getDomain(), Field.MaxWebUrlNumEveryDomainEveryClient);
                        if (newTask.size() != 0) {
                            unFinshTaskDomainNumMap.put(project.getDomain(), 0);
                            webUrls.addAll(newTask);
                        } else {
                            retryNum++;
                        }
                    }
                } catch (IndexOutOfBoundsException ioobe) {
                    //利用索引越界异常来判定系统需要新的Project解析
                    //本次请求中,不对新解析的任务进行提交,
                    //下次请求,再提交.
                    // TO DO ProcessNotStartProject()
                    ProcessNotStartProject();
                }
            }
        }
        return webUrls;
    }

    private static Map<String, HttpClientConfig> getHttpClientConfigHashMap(Map<String, Integer> unFinshTaskDomainNumMapByWebUrl) {
        Map<String, HttpClientConfig> HttpClientConfigHashMap = new HashMap<String, HttpClientConfig>();
        //封装配置
        for (String domain : unFinshTaskDomainNumMapByWebUrl.keySet()) {
            HttpClientConfig httpClientConfig = Field.httpClientConfigMap.get(domain);
            if (httpClientConfig == null) {
                httpClientConfig = Field.httpClientConfigMap.get(Field.Default_HttpConfigName);
            }
            HttpClientConfigHashMap.put(domain, httpClientConfig);
        }
        return HttpClientConfigHashMap;
    }


    public static TaskMessage getNewTaskMessage(List<String> unFinshTask, int threads) {
        TaskMessage taskMessage = null;
        try {
            //请求任务模式,默认将命令设置为下载模式
            taskMessage.setCommand(Field.Schema_Download);//downloader
            //根据剩余任务量得到新任务
            Map<String, Integer> unFinshTaskDomainNumMap = MessageUtil.getUnFinshTaskDomainNumMap(unFinshTask);
            List<WebUrl> newWebUrlList = getNewWebUrlList(unFinshTaskDomainNumMap, threads);
            taskMessage.setWebUrls(newWebUrlList);
            //根据新任务得到配置
            Map<String, Integer> unFinshTaskDomainNumMapByWebUrl = MessageUtil.getUnFinshTaskDomainNumMapByWebUrl(newWebUrlList);
            Map<String, HttpClientConfig> httpClientConfigHashMap = getHttpClientConfigHashMap(unFinshTaskDomainNumMapByWebUrl);
            taskMessage.setHttpClientConfigHashMap(httpClientConfigHashMap);
        }catch (Exception e){

        }
        return taskMessage;
    }

    /**
     * 解析数据库中还未开始的任务进入队列
     * 方法锁?是否该用,需判定在project请求时是否会有阻塞方法
     *
     * @return
     */
    public synchronized static void ProcessNotStartProject() {
        try {
            Project nextProject = MongoDBServiceImpl.getNextProject();
            List<WebUrl> webUrlListOfNextProject = nextProject.getWebUrlList();
            //设置状态为执行中  status=1;
            MongoDBServiceImpl.updateProject(nextProject.getMongoId(), "status", 1);
            //将任务放入redis中
            RedisServerImpl.addTask(webUrlListOfNextProject);
        } catch (JSONException je) {

        }

    }
}
