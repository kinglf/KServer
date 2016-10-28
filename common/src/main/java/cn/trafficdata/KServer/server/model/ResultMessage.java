package cn.trafficdata.KServer.server.model;

import java.util.List;

/**
 * Created by Kinglf on 2016/10/25.
 * 从节点返回给服务器的通讯结构
 * 服务器需要知道的是
 *             1.主机名称
 *             1.当前运行模式 Schema->downLoader/Schema->maintenance
 *             2.完成的任务(包含成功/失败)
 *             3.未执行的任务UrlList
 *
 */
public class ResultMessage {
    private HostInfo hostInfo;
    private List<Page> finishTasks;
    private List<String> unfinishTasks;
    public HostInfo getHostInfo() {
        return hostInfo;
    }

    public void setHostInfo(HostInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    public List<Page> getFinishTasks() {
        return finishTasks;
    }

    public void setFinishTasks(List<Page> finishTasks) {
        this.finishTasks = finishTasks;
    }

    public List<String> getUnfinishTasks() {
        return unfinishTasks;
    }

    public void setUnfinishTasks(List<String> unfinishTasks) {
        this.unfinishTasks = unfinishTasks;
    }


}
