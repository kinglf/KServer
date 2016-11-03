package cn.trafficdata.KServer.common.model;

import java.util.List;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class HostInfo {
    private String name;
    private String schema;
    private List<String> logList;

    public List<String> getLogList() {
        return logList;
    }

    public void setLogList(List<String> logList) {
        this.logList = logList;
    }

    public void setLogList(String logStr) {
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
}
