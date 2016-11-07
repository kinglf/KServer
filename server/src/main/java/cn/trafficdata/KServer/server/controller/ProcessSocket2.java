package cn.trafficdata.KServer.server.controller;

import cn.trafficdata.KServer.common.model.ResultMessage;
import cn.trafficdata.KServer.common.model.TaskMessage;
import cn.trafficdata.KServer.common.utils.KryoSerializableUtil;
import cn.trafficdata.KServer.server.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by Kinglf on 2016/10/27.
 * 使用Kryo进行序列化
 */
public class ProcessSocket2 implements Runnable {
    private final static Logger logger= LoggerFactory.getLogger(ProcessSocket2.class);
    private Socket socket;

    public ProcessSocket2(Socket socket) {
        this.socket = socket;
//        new Thread(this).start();
    }

    public void run() {
        try {
            ResultMessage resultMessage = KryoSerializableUtil.readObjectFromInputStream(socket.getInputStream(),ResultMessage.class);
            socket.shutdownInput();//关闭传入流
            String remoteSocketAddress = socket.getRemoteSocketAddress().toString();
            ServerController.executorService.execute(new ProcessResultMessage(resultMessage,socket.getRemoteSocketAddress()));//存储结果
           //记录接入信息

            //使用本线程从数据库中获取数据,如果超时或者失败则返回空的TaskMessage对象
            List<String> unfinishTasks = resultMessage.getUnfinishTasks();

            //封装新任务
            TaskMessage newTaskMessage = TaskService.getNewTaskMessage(unfinishTasks,resultMessage.getHostInfo().getMaxThreads());
            //发送
            KryoSerializableUtil.sendMessage(socket.getOutputStream(),newTaskMessage);
            socket.shutdownOutput();//关闭传出流
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                try {
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                } catch (IOException e2) {
                    try {
                        socket.close();
                    } catch (IOException e1) {
                    }
                }

            }
        }
    }
}
