package cn.trafficdata.KServer.common.utils;

import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.ResultMessage;
import cn.trafficdata.KServer.common.model.TaskMessage;
import cn.trafficdata.KServer.common.model.WebUrl;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class KryoSerializableUtil {
    private final static Kryo kryo=new Kryo();

    static {
        //kryo 的配置
        kryo.setRegistrationRequired(false);
//        kryo.setReferences(false);
//        kryo.register(ResultMessage.class,new JavaSerializer());
//        kryo.register(TaskMessage.class,new JavaSerializer());
//        kryo.register(Page.class,new JavaSerializer());
//        kryo.register(WebUrl.class,new JavaSerializer());
    }

    public static <T>  T readObjectFromInputStream(InputStream is,Class<T> t) throws IllegalArgumentException {
//        return kryo.readObject(new Input(is),t);
        kryo.register(t,new JavaSerializer());
        Object o = kryo.readClassAndObject(new Input(is));
        return (T) o;

    }
    public static void sendMessage(OutputStream os,Object obj) throws IllegalArgumentException{

        try{
            Output output=new Output(os);
//            kryo.writeObject(output,obj);
            kryo.register(obj.getClass(),new JavaSerializer());
            kryo.writeClassAndObject(output,obj);
            output.flush();
//            output.close();
        }catch (Exception se){
            se.printStackTrace();
        }
    }
    public static byte[] serialize(Object obj) {
        ByteOutputStream out = null;
        Output output = null;
        try {
            out = new ByteOutputStream();
            output = new Output(out);
            kryo.writeObject(output, obj);
            output.flush();
            return out.getBytes();
        } catch (Exception e) {
            return null;
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (Exception e) {
                }
            }
            if (null != output) {
                output.close();
                output = null;
            }
        }
    }
    public static <T> T deserialize(byte[] bytes,Class<T> t) {
        Input input = null;
        try {
            input = new Input(bytes);
            return kryo.readObject(input,t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != input) {
                input.close();
                input = null;
            }
        }
    }


}
