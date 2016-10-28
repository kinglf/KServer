package cn.trafficdata.KServer.client.Utils;

/**
 * Created by Kinglf on 2016/10/25.
 * 两种模式: true.下载模式
 *          false.维护模式
 */
public class SchemaManager {
    /**
     * 模式类型,boolean
     * 默认为true.下载模式
     */
    private static boolean schemaType=true;
    public static boolean getSchemaType(){
        return schemaType;
    }
    public static String getSchemaTypeString(){
        if(schemaType){
            return "下载模式";
        }else {
            return "维护模式";
        }
    }
    public static boolean changeSchemaType(boolean _schemaType){
        if(_schemaType==schemaType){
            //与当前模式一致,不做任何处理
            return true;
        }else {
            if(_schemaType){
                //从维护模式到下载模式
                //从本地文件恢复所有任务和配置文件
            }else {
                //从下载模式到维护模式
                //保存所有任务和配置到本地文件位置
            }
            return true;
        }
    }

}
