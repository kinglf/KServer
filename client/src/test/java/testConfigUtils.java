import cn.trafficdata.KServer.client.Utils.ConfigUtils;

/**
 * Created by Kinglf on 2016/10/24.
 */
public class testConfigUtils {
    public static void main(String[] args) {
        ConfigUtils instance = ConfigUtils.getInstance();
        String string = instance.getString("bccc", "bcd");
        System.out.println(string);
    }



}
