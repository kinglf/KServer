package SocketConnection;

import cn.trafficdata.KServer.common.model.WebUrl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Kinglf on 2016/10/19.
 */
public class Server {
    private static int port = 8099;

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new SocketThread(socket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class SocketThread implements Runnable {
    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
        new Thread(this).start();
    }

    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object o = ois.readObject();

            if (o instanceof List) {
                List<WebUrl> webUrls= (List<WebUrl>) o;
                for(WebUrl webUrl:webUrls){
                    System.out.println(webUrl.toString());
                }

            } else if (o instanceof WebUrl) {
                WebUrl webUrl = (WebUrl) o;
                System.out.println(webUrl.toString());
            }
            ois.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}