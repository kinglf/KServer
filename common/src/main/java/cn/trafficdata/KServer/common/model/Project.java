package cn.trafficdata.KServer.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kinglf on 2016/10/18.
 */
public class Project implements Serializable {
    private static final long serialVersionUID = 3952929371099931222L;
    @JSONField(name="_id")
    private String mongoId;//自动生成

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    private int id;//编号
    private String name;//名称
    private int level;//优先级 1-10 数字越小优先越高
    private String descrption;//项目描述
    private long create_timeStamp;//创建日期,时间戳
    private String creator;//创建者
    private int status;//0未开始,1执行中,2已完成};
    private boolean isUseJavaScript;
    private HttpClientConfig httpClientConfig;//当前站点的采集配置
    private int urlTotal;//url总数
    private List<WebUrl> webUrlList;

    public List<WebUrl> getWebUrlList() {
        return webUrlList;
    }

    public void setWebUrlList(List<WebUrl> webUrlList) {
        this.webUrlList = webUrlList;
    }

    /**
     * isUpdate暂不做考虑
     * 是否更新数据,true如果数据库中存在结果,继续布置任务,否则取消布置
     */
    private boolean isUpdate;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public long getCreate_timeStamp() {
        return create_timeStamp;
    }

    public void setCreate_timeStamp(long create_timeStamp) {
        this.create_timeStamp = create_timeStamp;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isUseJavaScript() {
        return isUseJavaScript;
    }

    public void setUseJavaScript(boolean useJavaScript) {
        isUseJavaScript = useJavaScript;
    }

    public HttpClientConfig getHttpClientConfig() {
        return httpClientConfig;
    }

    public void setHttpClientConfig(HttpClientConfig httpClientConfig) {
        this.httpClientConfig = httpClientConfig;
    }

    public int getUrlTotal() {
        return urlTotal;
    }

    public void setUrlTotal(int urlTotal) {
        this.urlTotal = urlTotal;
    }
}
