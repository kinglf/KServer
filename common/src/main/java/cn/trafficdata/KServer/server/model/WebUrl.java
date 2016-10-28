package cn.trafficdata.KServer.server.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/18.
 * 下发给node的任务
 */
public class WebUrl implements Serializable {
    @Override
    public String toString() {
        return String.valueOf(JSON.toJSON(this));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, String> getPrams() {
        return prams;
    }

    public void setPrams(Map<String, String> prams) {
        this.prams = prams;
    }

    private static final long serialVersionUID = 1L;
    private int id;//所有任务的id
    private int projectID;//项目id
    private String url;
    private int type;//0->GET;1->POST;
    private Map<String,String> prams;






}
