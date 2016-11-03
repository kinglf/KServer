package SocketConnection;

import cn.trafficdata.KServer.common.model.WebUrl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/19.
 */
public class Client implements Runnable {
    private String address = "61.50.105.254";
    private int port = 8091;
    public static Object obj;

    public Client(Object obj) {
        this.obj = obj;
        new Thread(this).start();
    }

    public void run() {
        Socket socket = new Socket();
        InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
        try {

            socket.connect(isa);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(obj);
            oos.flush();
            ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
            try {
                Object obj=ois.readObject();
                if(obj instanceof List){
                    List<WebUrl> webUrls= (List<WebUrl>) obj;
                    for(WebUrl webUrl:webUrls){
                        System.out.println(webUrl.toString());
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

class testClient {
    public static void main(String[] args) {
        List<WebUrl> webUrls = new ArrayList<WebUrl>();
        for (int i = 0; i < 10000; i++) {
            WebUrl webUrl = new WebUrl();
            webUrl.setId((int) (Math.random() * 100));
            webUrl.setProjectID((int) (Math.random() * 100));
            webUrl.setType((int) (Math.random() * 100));
            webUrl.setUrl("www.sina.com.cn");
            Map<String, String> maps = new HashMap<String, String>();
            maps.put("abc", "def");
            maps.put("123", "456");
            webUrl.setPrams(maps);
            webUrls.add(webUrl);
        }
        new Client(webUrls);
    }
}
