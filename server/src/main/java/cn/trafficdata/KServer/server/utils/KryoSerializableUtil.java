package cn.trafficdata.KServer.server.utils;

import cn.trafficdata.KServer.server.model.ResultMessage;
import cn.trafficdata.KServer.server.model.TaskMessage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class KryoSerializableUtil {
    private static Kryo kryo;

    static {
        kryo = new Kryo();
    }

    public static ResultMessage readResultMessageFromInputStream(InputStream is) {
        return kryo.readObject(new Input(is), ResultMessage.class);

    }

    public static TaskMessage readTaskMessageFromInputStream(InputStream is) {
        return kryo.readObject(new Input(is), TaskMessage.class);
    }

    public static void sendMessage(OutputStream os,Object obj){
        Output output=new Output(os);
        kryo.writeObject(output,obj);
    }
}
