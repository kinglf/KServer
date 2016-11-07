package cn.trafficdata.KServer.common.model;

import org.omg.CORBA.Environment;

import java.util.List;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class HostInfo {
    private String name;
    private String markcode;//识别码
    private String schema;
    private int maxThreads;
    private List<LogMessage> logList;

    public HostInfo(){

    }
    public HostInfo(String name,String markcode,String schema,int maxThreads,List<LogMessage> logMessageList){
        this.name=name;
        this.markcode=markcode;
        this.schema=schema;
        this.maxThreads=maxThreads;
        this.logList=logMessageList;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public List<LogMessage> getLogList() {
        return logList;
    }

    public void setLogList(List<LogMessage> logList) {
        this.logList = logList;
    }

    public void setLogList(LogMessage logStr) {
        this.logList.add(logStr);
    }


    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarkcode() {
        return markcode;
    }

    public void setMarkcode(String markcode) {
        this.markcode = markcode;
    }
}
