package cn.trafficdata.KServer.server.utils;

import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.WebUrl;
import cn.trafficdata.KServer.common.utils.KLog;
import cn.trafficdata.KServer.common.utils.UrlUtils;

import java.util.*;

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
            String domain= UrlUtils.getDomain(urlStr);
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
    public static Map<String,Integer> getDomainListByPageList(List<Page> pages){
        Map<String,Integer> urlMapDomain=new HashMap<String, Integer>();
        for(Page page:pages){
            String domain=UrlUtils.getDomain(page.getWebUrl().getUrl());
            if(urlMapDomain.get(domain)==null){
                urlMapDomain.put(domain,page.getProjectId());
            }
        }
        return urlMapDomain;
    }


}
