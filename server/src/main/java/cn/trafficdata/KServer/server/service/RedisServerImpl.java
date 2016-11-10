package cn.trafficdata.KServer.server.service;

import cn.trafficdata.KServer.common.model.WebUrl;
import cn.trafficdata.KServer.common.utils.KryoSerializableUtil;
import cn.trafficdata.KServer.server.utils.RedisUtil;
import cn.trafficdata.KServer.common.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinglf on 2016/11/3.
 * FIFO 先进先出 头出尾入
 *
 */
public class RedisServerImpl {
    public final static Logger logger= LoggerFactory.getLogger(RedisServerImpl.class);
    public static boolean addTask(WebUrl webUrl){
        try{
            String domain= UrlUtils.getDomain(webUrl.getUrl());
            byte[] serialize = KryoSerializableUtil.serialize(webUrl);
            RedisUtil.rpush(domain.getBytes(),serialize);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
    @Deprecated
    public static boolean addTask(List<WebUrl> webUrls){

        try{
            String domain= UrlUtils.getDomain(webUrls.get(0).getUrl());
            List<byte[]> serializeList=new ArrayList<byte[]>();
            for(WebUrl webUrl:webUrls){
                byte[] serialize = KryoSerializableUtil.serialize(webUrl);
                serializeList.add(serialize);
            }
            RedisUtil.rpush(domain.getBytes(),serializeList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
    public static boolean addTask(String domain,List<WebUrl> webUrls){

        try{
            List<byte[]> serializeList=new ArrayList<byte[]>();
            for(WebUrl webUrl:webUrls){
                byte[] serialize = KryoSerializableUtil.serialize(webUrl);
                serializeList.add(serialize);
            }
            RedisUtil.rpush(domain.getBytes(),serializeList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
//    public static List<WebUrl> getNewTask(String domain,int num){
//        List<WebUrl> webUrls=new ArrayList<WebUrl>();
//        //lrange 取出,只是查看,没有移除数据库
//        List<byte[]> byteArrs = RedisUtil.lrange(domain.getBytes(), 0, num);
//        for(byte[] bytes:byteArrs){
//            WebUrl webUrl = KryoSerializableUtil.deserialize(bytes, WebUrl.class);
//            webUrls.add(webUrl);
//        }
//        return webUrls;
//
//    }

    public static List<WebUrl> getNewTask(String domain,int num){
        List<WebUrl> webUrls=new ArrayList<WebUrl>();
        for(int i=0;i<num;i++){
            byte[] bytes = RedisUtil.lpop(domain.getBytes());
            if (bytes==null){
                return webUrls;
            }
            WebUrl webUrl = KryoSerializableUtil.deserialize(bytes, WebUrl.class);
            webUrls.add(webUrl);
        }
        return webUrls;
    }

    /**
     * 删改查,暂时没写
     */

}
