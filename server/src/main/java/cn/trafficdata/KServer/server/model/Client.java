package cn.trafficdata.KServer.server.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Kinglf on 2016/11/5.
 */
public class Client {
    @JSONField(name="_id")
    private String mongoId;
    private String latlon;
    private int threads;

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    private String ip;
    private String schema;
    private String markcode;//机器标识码
    private long lastRequestTime;//最后一次请求的时间
    private int lastResponsePageNum;//最后一次返回结果的数量

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public String getLatlon() {
        return latlon;
    }

    public void setLatlon(String latlon) {
        this.latlon = latlon;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getMarkcode() {
        return markcode;
    }

    public void setMarkcode(String markcode) {
        this.markcode = markcode;
    }

    public long getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    public int getLastResponsePageNum() {
        return lastResponsePageNum;
    }

    public void setLastResponsePageNum(int lastResponsePageNum) {
        this.lastResponsePageNum = lastResponsePageNum;
    }
}
