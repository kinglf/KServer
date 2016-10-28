package cn.trafficdata.KServer.testLog;

import cn.trafficdata.KServer.server.utils.KLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Kinglf on 2016/10/27.
 */
public class testLog {
    private static Logger logger= LoggerFactory.getLogger(testLog.class);
    public static void main(String[] args) {
        aaaa("");
    }


    public static void aaaa(String abc){
//        KLog.info("This is a test.17");
        logger.debug("this is a test");

    }
}
