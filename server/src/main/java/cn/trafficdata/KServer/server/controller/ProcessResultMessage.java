package cn.trafficdata.KServer.server.controller;

import cn.trafficdata.KServer.server.model.ResultMessage;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class ProcessResultMessage implements Runnable {
    private ResultMessage resultMessage;

    public ProcessResultMessage(ResultMessage message) {
        this.resultMessage = message;
    }


    public void run() {
        //TO DO PROCESS RESULTMESSAGE
    }
}
