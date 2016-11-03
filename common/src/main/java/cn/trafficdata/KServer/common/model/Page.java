package cn.trafficdata.KServer.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Kinglf on 2016/10/18.
 * node请求处理完weburl返回的结果,其中包括WebUrl
 */
public class Page implements Serializable {
    @JSONField(name="_id")
    private String mongoId;//自动生成

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
    private static final long serialVersionUID = 1689436333318223585L;
    private int projectId;
    private WebUrl webUrl;
}
