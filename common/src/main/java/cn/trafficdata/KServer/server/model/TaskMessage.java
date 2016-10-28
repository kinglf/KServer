package cn.trafficdata.KServer.server.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/25.
 */
public class TaskMessage {
    /**
     * domain->httpClientConfig 的存储Map
     */
    private Map<String,HttpClientConfig> httpClientConfigHashMap;
    /**
     * 任务List集合
     */
    private List<WebUrl> webUrls;

    public Map<String, HttpClientConfig> getHttpClientConfigHashMap() {
        return httpClientConfigHashMap;
    }

    public void setHttpClientConfigHashMap(Map<String, HttpClientConfig> httpClientConfigHashMap) {
        this.httpClientConfigHashMap = httpClientConfigHashMap;
    }

    public List<WebUrl> getWebUrls() {
        return webUrls;
    }

    public void setWebUrls(List<WebUrl> webUrls) {
        this.webUrls = webUrls;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * 执行命令,
     *  1.null 继续,无操作
     *  2.Schema->downLoader 下载模式
     *  3.Schema->maintenance 维护模式
     *  4.Redial 重新拨号
     */
    private String command;
}
