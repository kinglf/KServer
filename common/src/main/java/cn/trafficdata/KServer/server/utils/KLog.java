package cn.trafficdata.KServer.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Kinglf on 2016/10/27.
 */
public class KLog {
    //error
    //info
    private static List<String> logList=new ArrayList<String>();

    public static void info(String info){
        StringBuilder stringBuilder=new StringBuilder();
        StackTraceElement stackTraceElement=Thread.currentThread().getStackTrace()[2];
        String className=stackTraceElement.getClassName();
        String methodName=stackTraceElement.getMethodName();
        int lineNum=stackTraceElement.getLineNumber();
        stringBuilder.append("[INFO]:");
        stringBuilder.append("["+className+"]->");
        stringBuilder.append("["+methodName+"]->");
        stringBuilder.append("["+lineNum+"]:");
        stringBuilder.append(info);
        saveLog(stringBuilder.toString());
    }


    private static boolean saveLog(String info){
        logList.add(info);
        System.out.println(info);
        return true;
    }
}
