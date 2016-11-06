package cn.trafficdata.KServer.server.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Kinglf on 2016/11/5.
 */
public class Client {
    @JSONField(name="_id")
    private String mongoId;
    private String latlon;
    private String ip;
    private String schema;
    private String markcode;//机器标识码
    private long lastRequestTime;//最后一次请求的时间
    private int lastResponsePageNum;//最后一次返回结果的数量
}
