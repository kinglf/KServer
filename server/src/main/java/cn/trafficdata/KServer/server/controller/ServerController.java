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
    protected static ExecutorService socketExecutorService;
    private final static Logger logger= LoggerFactory.getLogger(ServerController.class);

    private final static int SERVER_PORT=9999;
    private final static int PROCESS_RESULTMESSAGE_THREAD_NUM=2;
    private final static int PROCESS_SOCKET_THREAD_NUM=10;




    private static void init(){
       executorService= Executors.newFixedThreadPool(PROCESS_RESULTMESSAGE_THREAD_NUM);
        socketExecutorService=Executors.newFixedThreadPool(PROCESS_SOCKET_THREAD_NUM);
    }


    public static void main(String[] args) {
        //1.init
        init();
        /**
         * 2.启动服务
         */
        try {
            serverSocket=new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            logger.error("服务启动失败:{}",e.getMessage());
            return;
        }
        /**
         * 3.循环接入Socket请求
         */
        while(true){
            try {
                Socket socket = serverSocket.accept();
//                socketExecutorService.execute(new ProcessSocket(socket));
                socketExecutorService.execute(new ProcessSocket2(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //2.server.start();
    }

}
