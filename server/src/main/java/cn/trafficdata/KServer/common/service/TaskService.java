package cn.trafficdata.KServer.common.service;

import cn.trafficdata.KServer.common.configurable.Field;
import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.TaskMessage;
import cn.trafficdata.KServer.common.model.WebUrl;
import cn.trafficdata.KServer.common.utils.MessageUtil;

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
     * @param unFinshTaskDomainNumMap
     * @return
     */
    private static List<WebUrl> getNewWebUrlList(Map<String, Integer> unFinshTaskDomainNumMap){
        List<WebUrl> webUrls=new ArrayList<WebUrl>();
//        unFinshTaskDomainNumMap
        //根据domain->数量的map   请求相关任务
        for(Map.Entry<String, Integer> map:unFinshTaskDomainNumMap.entrySet()){
            List<WebUrl> newTasks = RedisServerImpl.getNewTask(map.getKey(), Field.MaxWebUrlNumEveryDomainEveryClient - map.getValue());
            webUrls.addAll(newTasks);
            if(webUrls.size()>=Field.MaxWebUrlTotalNum4Response){
                break;
            }
        }
        if(webUrls.size()<Field.MaxWebUrlTotalNum4Response){
            //循环请求不在unFinshTaskDomainNumMap中的domain 的任务,知道足够为止
            //如果任务添加后也不够,启动解析还未执行的任务到Redis中
        }

        //当webUrls总数超过一定值(在ServerController中定义),即任务饱和后返回
        return webUrls;
    }
    private static Map<String,HttpClientConfig>  getHttpClientConfigHashMap(Map<String, Integer> unFinshTaskDomainNumMapByWebUrl){
        Map<String,HttpClientConfig>  HttpClientConfigHashMap=new HashMap<String, HttpClientConfig>();
        //封装配置
        for(String domain:unFinshTaskDomainNumMapByWebUrl.keySet()){
            HttpClientConfig httpClientConfig = Field.httpClientConfigMap.get(domain);
            if(httpClientConfig==null){
                httpClientConfig=Field.httpClientConfigMap.get(Field.Default_HttpConfigName);
            }
            HttpClientConfigHashMap.put(domain,httpClientConfig);
        }
        return HttpClientConfigHashMap;
    }


    public static TaskMessage getNewTaskMessage(List<String> unFinshTask){
        TaskMessage taskMessage=null;
        //请求任务模式,默认将命令设置为下载模式
        taskMessage.setCommand(Field.Schema_Download);//downloader
        //根据剩余任务量得到新任务
        Map<String, Integer> unFinshTaskDomainNumMap = MessageUtil.getUnFinshTaskDomainNumMap(unFinshTask);
        List<WebUrl> newWebUrlList = getNewWebUrlList(unFinshTaskDomainNumMap);
        taskMessage.setWebUrls(newWebUrlList);
        //根据新任务得到配置
        Map<String, Integer> unFinshTaskDomainNumMapByWebUrl = MessageUtil.getUnFinshTaskDomainNumMapByWebUrl(newWebUrlList);
        Map<String, HttpClientConfig> httpClientConfigHashMap = getHttpClientConfigHashMap(unFinshTaskDomainNumMapByWebUrl);
        taskMessage.setHttpClientConfigHashMap(httpClientConfigHashMap);
        return taskMessage;
    }
}
