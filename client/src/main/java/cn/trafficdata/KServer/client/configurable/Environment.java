package cn.trafficdata.KServer.client.configurable;

import cn.trafficdata.KServer.client.Utils.ConfigUtils;
import cn.trafficdata.KServer.common.model.HttpClientConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Kinglf on 2016/10/24.
 * 配置文件应用优先级,远程->备份/恢复->默认配置
 */
public class Environment extends Properties{

    /**
     * 本地化文件存储目录
     */
    private static String DirPath="d:/temp/";
    /**
     * 配置序列化文件存储文件名
     */
    private static String ConfigFileName="Config.bin";
    /**
     * 任务序列化文件存储文件名
     */
    private static String TasksFileName="Tasks.bin";

    /**
     * 读取配置文件的工具类
     */
    private static ConfigUtils configUtils;
    /**
     * 本机名称
     */
    public static String localhostName;
    /**
     * 主机IP
     */
    private static String host_ip;
    /**
     * 主机端口
     */
    private static int host_port;
    /**
     * domain->httpClientConfig 的存储Map
     */
    public static Map<String,HttpClientConfig> httpClientConfigHashMap=new HashMap<String,HttpClientConfig>();

    public static int MinWebUrlTotal=50;
    public static int MaxPageTotal=1000;

    public static int MaxThreads=10;

    public static String markcode="ABCDEFGHIJKLMN";

    public static String Default_HttpConfigName="default";







    public static int getMaxThreads() {
        return MaxThreads;
    }

    public static void setMaxThreads(int maxThreads) {
        MaxThreads = maxThreads;
    }






    public Environment (){
        if(configUtils.isEmpty()){

            //加载资源
            configUtils=ConfigUtils.getInstance();
            try {
                localhostName=configUtils.getString("localhostName", InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                localhostName="No Name";
            }
            host_ip=configUtils.getString("host_ip","127.0.0.1");
            host_port=configUtils.getInt("host_port",9999);

        }
    }

    public static String getLocalhostName() {
        return localhostName;
    }

    public static String getHost_ip() {
        return host_ip;
    }

    public static int getHost_port() {
        return host_port;
    }
    public static HttpClientConfig getHttpClientConfig(String domain){
        HttpClientConfig httpClientConfig = httpClientConfigHashMap.get(domain);
        if(httpClientConfig==null){
            httpClientConfig=httpClientConfigHashMap.get(Default_HttpConfigName);
        }
        return httpClientConfig;
    }
}
