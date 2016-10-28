package cn.trafficdata.KServer.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class ServerController {
    private static ServerSocket serverSocket;
    protected static ExecutorService executorService;
    private final static Logger logger= LoggerFactory.getLogger(ServerController.class);

    private final static int SERVER_PORT=9999;
    private final static int PROCESS_RESULTMESSAGE_THREAD_NUM=2;



    private static void init(){
       executorService= Executors.newFixedThreadPool(PROCESS_RESULTMESSAGE_THREAD_NUM);
    }


    public static void main(String[] args) {
        //1.init
        init();
        try {
            serverSocket=new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            logger.error("服务启动失败:{}",e.getMessage());
            return;
        }
        try {
            Socket socket = serverSocket.accept();


        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.server.start();
    }

}
