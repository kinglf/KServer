package cn.trafficdata.KServer.server.utils;

import cn.trafficdata.KServer.server.model.ResultMessage;
import cn.trafficdata.KServer.server.model.TaskMessage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class KryoSerializableUtil {
    private final static Kryo kryo=new Kryo();

    static {
        //kryo 的配置
        kryo.setRegistrationRequired(false);
    }

    public static <T>  T readObjectFromInputStream(InputStream is,Class<T> t) throws IllegalArgumentException {
        return kryo.readObject(new Input(is),t);
    }
    public static void sendMessage(OutputStream os,Object obj) throws IllegalArgumentException{
        Output output=new Output(os);
        kryo.writeObject(output,obj);
    }
    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream out = null;
        Output output = null;
        try {
            out = new ByteArrayOutputStream();
            output = new Output(out, 1024);
            kryo.writeClassAndObject(output, obj);
            return output.toBytes();
        } catch (Exception e) {
            return null;
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                }
            }
            if (null != output) {
                output.close();
                output = null;
            }
        }
    }
    public static Object deserialize(byte[] bytes) {
        Input input = null;
        try {
            input = new Input(bytes, 0, 1024);
            return kryo.readClassAndObject(input);
        } catch (Exception e) {
            return null;
        } finally {
            if (null != input) {
                input.close();
                input = null;
            }
        }
    }


    public static void main(String[] args) {
        byte[] bytes=new byte[1024];
        Output output=new Output(bytes);
    }
}
