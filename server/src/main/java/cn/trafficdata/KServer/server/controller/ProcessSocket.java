package cn.trafficdata.KServer.server.controller;

import cn.trafficdata.KServer.server.model.ResultMessage;
import cn.trafficdata.KServer.server.model.TaskMessage;
import cn.trafficdata.KServer.server.utils.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Kinglf on 2016/10/27.
 * 使用java原生序列化方式
 */
public class ProcessSocket implements Runnable {
    private final static Logger logger= LoggerFactory.getLogger(ProcessSocket.class);
    private Socket socket;

    public ProcessSocket(Socket socket) {
        this.socket = socket;
//        new Thread(this).start();
    }


    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();
            if (obj instanceof ResultMessage) {
                //1.使用线程池处理数据保存
                ResultMessage resultMessage= (ResultMessage) obj;
                ServerController.executorService.execute(new ProcessResultMessage(resultMessage));
                //使用本线程从数据库中获取数据,如果超时或者失败则返回空的TaskMessage对象
                List<String> unfinishTasks = resultMessage.getUnfinishTasks();
                //封装新任务
                TaskMessage newTaskMessage = TaskService.getNewTaskMessage(unfinishTasks);
                ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(newTaskMessage);
                oos.flush();
                oos.close();
            }
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                try {
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

            }
        }
    }

}
