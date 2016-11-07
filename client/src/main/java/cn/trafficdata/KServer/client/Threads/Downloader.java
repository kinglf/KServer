package cn.trafficdata.KServer.client.Threads;

import cn.trafficdata.KServer.client.Controller.Controller;
import cn.trafficdata.KServer.client.configurable.Environment;
import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.WebUrl;

/**
 * Created by Kinglf on 2016/11/7.
 */
public class Downloader implements Runnable {
    protected String domain;
    protected HttpClientConfig httpClientConfig;
    public Downloader(String domain){
        this.domain=domain;
        httpClientConfig= Environment.getHttpClientConfig(domain);
    }
    public void run() {
        while (true){
            WebUrl webUrl = Controller.WebUrlListMap.get(domain).remove(0);

        }
    }
}
