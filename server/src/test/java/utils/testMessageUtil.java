package utils;
import cn.trafficdata.KServer.common.utils.MessageUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class testMessageUtil {

    @Test
    public void testGetUnFinshTaskDomainNumMap(){
        List<String> urlList=new ArrayList<String>();
        urlList.add("https://www.oschina.net/question/12_8749");
        urlList.add("https://www.oschina.net/question/12_8749");
        urlList.add("https://www.oschina.net/question/12_8749");
        urlList.add("https://www.oschina.net/question/12_8749");
        urlList.add("https://www.oschina.net/question/12_8749");
        urlList.add("https://www.oschina.net/question/12_8749");
        urlList.add("https://www.oschina.net/question/12_8749");
        urlList.add("http://blog.csdn.net/albertfly/article/details/51747463");
        urlList.add("http://blog.csdn.net/albertfly/article/details/51747463");
        urlList.add("http://blog.csdn.net/albertfly/article/details/51747463");
        urlList.add("http://blog.csdn.net/albertfly/article/details/51747463");
        urlList.add("http://blog.csdn.net/albertfly/article/details/51747463");
        urlList.add("http://blog.csdn.net/albertfly/article/details/51747463");

        Map<String, Integer> unFinshTaskDomainNumMap = MessageUtil.getUnFinshTaskDomainNumMap(urlList);
        for(String key:unFinshTaskDomainNumMap.keySet()){
            System.out.println(key+"剩余"+unFinshTaskDomainNumMap.get(key));
        }


    }

}
