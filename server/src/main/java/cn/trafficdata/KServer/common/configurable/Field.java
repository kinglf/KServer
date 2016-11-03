package cn.trafficdata.KServer.common.configurable;

import cn.trafficdata.KServer.common.model.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class Field {
    public final static String Schema_Download="downloader";
    public final static String Default_HttpConfigName="default";
    public final static byte[] TaskQueueNameInRedis="TaskQueue".getBytes();
    public final static int MaxWebUrlTotalNum4Response=500;//每次请求各个domain的总数量,数量暂定
    public final static int MaxWebUrlNumEveryDomainEveryClient=100;//每次请求每个client的每个domain数量最大值,暂定
    public static Map<String,HttpClientConfig> httpClientConfigMap=new HashMap<String, HttpClientConfig>();

    static {
        httpClientConfigMap.put(Default_HttpConfigName,new HttpClientConfig());
    }

}
