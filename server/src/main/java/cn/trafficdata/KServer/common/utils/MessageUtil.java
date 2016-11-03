package cn.trafficdata.KServer.common.utils;

import cn.trafficdata.KServer.common.model.WebUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class MessageUtil {
    //    public static Map<>
    public static void main(String[] args) {



    }

    public static Map<String,Integer> getUnFinshTaskDomainNumMap(List<String> urlStrList){
        Map<String,Integer> urlMapDomain=new HashMap<String, Integer>();
        for(String urlStr:urlStrList){
            String domain=UrlUtils.getDomain(urlStr);
            if(urlMapDomain.get(domain)==null){
                urlMapDomain.put(domain,1);
            }else{
                urlMapDomain.put(domain,urlMapDomain.get(domain)+1);
            }
        }
        return urlMapDomain;
    }
    public static Map<String,Integer> getUnFinshTaskDomainNumMapByWebUrl(List<WebUrl> urlStrList){
        Map<String,Integer> urlMapDomain=new HashMap<String, Integer>();
        for(WebUrl webUrl:urlStrList){
            String domain=UrlUtils.getDomain(webUrl.getUrl());
            if(urlMapDomain.get(domain)==null){
                urlMapDomain.put(domain,1);
            }else{
                urlMapDomain.put(domain,urlMapDomain.get(domain)+1);
            }
        }
        return urlMapDomain;
    }


}
