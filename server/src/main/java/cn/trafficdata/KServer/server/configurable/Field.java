package cn.trafficdata.KServer.server.configurable;

import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.server.utils.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class Field {

    //Configuration自行写的配置文件解析类,继承自Properties
    public final static Configuration conf=Configuration.getInstance();
    public final static String Schema_Download = "downloader";
    public final static String Default_HttpConfigName = "default";
    public final static byte[] TaskQueueNameInRedis = "TaskQueue".getBytes();
//    @Deprecated
//    public final static int MaxWebUrlTotalNum4Response = 500;//每次请求各个domain的总数量,数量暂定
    public final static int MaxWebUrlNumEveryDomainEveryClient = 100;//每次请求每个client的每个domain数量最大值,暂定
    public static Map<String, HttpClientConfig> httpClientConfigMap = new HashMap<String, HttpClientConfig>();
    public static boolean SchemaSwitch = false;

    /**
     * redis 配置
     */
    public static String JEDIS_IP;
    public static int JEDIS_PORT;
    public static String JEDIS_PASSWORD;

    /**
     * MongoDB
     */
    public static String MongoDB_IP;
    public static int MongoDB_PORT;
    public static String MongoDB_DATABASE;
    public static String Project_Collection_Name = "Projects";
    public static String Page_Collection_Name = "Pages";
    public static String Client_Collection_Name = "Clients";
    public static String Log_Collection_Name = "Logs";


    static {
        httpClientConfigMap.put(Default_HttpConfigName, new HttpClientConfig());
        /*=====================模式开关======================*/
        int schemaswitch = conf.getInt("schemaswitch", 1);
        if (schemaswitch == 0) {
            SchemaSwitch = true;
        } else {
            SchemaSwitch = false;
        }
        /*=====================jedis 配置加载======================*/
        JEDIS_IP = conf.getString("jedis.ip", "127.0.0.1");
        JEDIS_PORT = conf.getInt("jedis.port", 6379);
        JEDIS_PASSWORD = conf.getString("jedis.password", null);
        /*=====================MongoDB配置加载======================*/
        MongoDB_IP = conf.getString("mongodb.ip", "127.0.0.1");
        MongoDB_PORT = conf.getInt("mongodb.port", 27017);
        MongoDB_DATABASE = conf.getString("mongodb.database", "KServer");
        Project_Collection_Name = conf.getString("mongodb.Project_Collection_Name", "Projects");
        Page_Collection_Name = conf.getString("mongodb.Page_Collection_Name", "Pages");
        Client_Collection_Name = conf.getString("mongodb.Client_Collection_Name", "Clients");
        Log_Collection_Name = conf.getString("mongodb.Log_Collection_Name", "Logs");
    }

}
