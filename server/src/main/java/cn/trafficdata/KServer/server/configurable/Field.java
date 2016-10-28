package cn.trafficdata.KServer.server.configurable;

import cn.trafficdata.KServer.server.model.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class Field {
    public final static String Schema_Download="downloader";
    public final static String Default_HttpConfigName="default";
    public static Map<String,HttpClientConfig> httpClientConfigMap=new HashMap<String, HttpClientConfig>();

}
