package cn.trafficdata.KServer.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.http.Header;

import java.io.Serializable;

/**
 * Created by Kinglf on 2016/10/18.
 * node请求处理完weburl返回的结果,其中包括WebUrl
 */
public class Page  {
    @JSONField(name="_id")
    private String mongoId;//自动生成
    private WebUrl webUrl;
    private int projectId;
    private boolean redirect;
    private String redirectedToUrl;
    private int statusCode;
    private byte[] contentData;
    private String contentType;
    private String contentEncoding;
    private String contentCharset;
    private String language;
    protected Header[] fetchResponseHeaders;





    public String getMongoId() {
        return mongoId;
    }
    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
    public int getProjectId() {
        return projectId;
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public WebUrl getWebUrl() {
        return webUrl;
    }
    public void setWebUrl(WebUrl webUrl) {
        this.webUrl = webUrl;
    }
}
