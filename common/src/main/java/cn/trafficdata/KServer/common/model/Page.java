package cn.trafficdata.KServer.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import java.io.Serializable;
import java.nio.charset.Charset;

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


    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public String getRedirectedToUrl() {
        return redirectedToUrl;
    }

    public void setRedirectedToUrl(String redirectedToUrl) {
        this.redirectedToUrl = redirectedToUrl;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getContentData() {
        return contentData;
    }

    public void setContentData(byte[] contentData) {
        this.contentData = contentData;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getContentCharset() {
        return contentCharset;
    }

    public void setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Header[] getFetchResponseHeaders() {
        return fetchResponseHeaders;
    }

    public void setFetchResponseHeaders(Header[] fetchResponseHeaders) {
        this.fetchResponseHeaders = fetchResponseHeaders;
    }

    public void load(HttpEntity entity) throws Exception {

        contentType = null;
        Header type = entity.getContentType();
        if (type != null) {
            contentType = type.getValue();
        }

        contentEncoding = null;
        Header encoding = entity.getContentEncoding();
        if (encoding != null) {
            contentEncoding = encoding.getValue();
        }

        Charset charset = ContentType.getOrDefault(entity).getCharset();
        if (charset != null) {
            contentCharset = charset.displayName();
        }

        contentData = EntityUtils.toByteArray(entity);
    }

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
