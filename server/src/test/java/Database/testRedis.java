package Database;

import cn.trafficdata.KServer.common.model.WebUrl;
import cn.trafficdata.KServer.common.utils.KLog;
import cn.trafficdata.KServer.common.utils.KryoSerializableUtil;
import cn.trafficdata.KServer.server.service.RedisServerImpl;
import cn.trafficdata.KServer.server.utils.RedisUtil;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kinglf on 2016/11/9.
 */
public class testRedis {
    public static void main(String[] args) {
        List<WebUrl> newTask = RedisServerImpl.getNewTask("aifei.com", 100);
        KLog.printJson(newTask);
    }
    @Test
    public void testKryo(){
        WebUrl webUrl=new WebUrl();
        webUrl.setUrl("abc.com");
        webUrl.setReferer("bcd.com");
        webUrl.setCookies("hahah");
        webUrl.setType(0);
        webUrl.setId(1);
        webUrl.setProjectID(20);
        webUrl.setPrams(new HashMap<String, String>());
        byte[] serialize = KryoSerializableUtil.serialize(webUrl);
        RedisUtil.rpush("testbyte".getBytes(),serialize);
    }
    @Test
    public void getRedisToKryo(){
        Kryo kryo=new Kryo();
        byte[] lpop = RedisUtil.lpop("testbyte".getBytes());
        WebUrl webUrl = KryoSerializableUtil.deserialize(lpop, WebUrl.class);
        KLog.printJson(webUrl);

    }
    @Test
    public void getRedisToKryo2(){
        Kryo kryo=new Kryo();
        byte[] lpop = RedisUtil.lpop("testbyte".getBytes());
        Input input=new Input(lpop);
        WebUrl webUrl = kryo.readObject(input, WebUrl.class);
        KLog.printJson(webUrl);
    }
}
