package cn.trafficdata.KServer.server.model;

import java.io.Serializable;

/**
 * Created by Kinglf on 2016/10/18.
 * node请求处理完weburl返回的结果,其中包括WebUrl
 */
public class Page implements Serializable {
    private static final long serialVersionUID = 1689436333318223585L;
    private WebUrl webUrl;
}
