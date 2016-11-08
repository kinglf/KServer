package cn.trafficdata.KServer.client.Downloader;

import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.WebUrl;

/**
 * Created by Kinglf on 2016/11/8.
 */
public interface Downloader {

    public Page download(WebUrl webUrl);
}
