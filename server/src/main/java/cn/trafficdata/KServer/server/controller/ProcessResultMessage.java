package cn.trafficdata.KServer.server.controller;

import cn.trafficdata.KServer.common.model.HostInfo;
import cn.trafficdata.KServer.common.model.LogMessage;
import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.ResultMessage;
import cn.trafficdata.KServer.server.model.Client;
import cn.trafficdata.KServer.server.service.MongoDBServiceImpl;
import cn.trafficdata.KServer.server.utils.MessageUtil;

import java.net.SocketAddress;
import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class ProcessResultMessage implements Runnable {
    private ResultMessage resultMessage;
    private SocketAddress socketAddress;

    public ProcessResultMessage(ResultMessage message, SocketAddress socketAddress) {
        this.resultMessage = message;
        this.socketAddress=socketAddress;
    }


    public void run() {
        HostInfo hostInfo = resultMessage.getHostInfo();
        List<Page> finishTasks = resultMessage.getFinishTasks();
        //TO DO PROCESS RESULTMESSAGE
        //1.更新client信息 hostinfo
        //保存日志
        MongoDBServiceImpl.saveLogs(hostInfo.getLogList());

        Client client=new Client();
        client.setIp(socketAddress.toString());
        client.setMarkcode(hostInfo.getMarkcode());
        client.setLastRequestTime(System.currentTimeMillis());
        client.setLastResponsePageNum(finishTasks.size());
        client.setSchema(hostInfo.getSchema());
        client.setThreads(hostInfo.getMaxThreads());
        MongoDBServiceImpl.initOrUpdateClient(client);
        //2.保存结果 List<Page>
        MongoDBServiceImpl.savePage(finishTasks);

        //3.检查相映domain的project结果是否已满,如果已满则设置任务为完成.
        Map<String, Integer> domainListByPageList = MessageUtil.getDomainListByPageList(finishTasks);
        for(String domain:domainListByPageList.keySet()){
            boolean fullPage = MongoDBServiceImpl.isFullPage(domain);
            if(fullPage){
                MongoDBServiceImpl.changeProjectStatus(domainListByPageList.get(domain),2);
            }
        }
    }
}
