package cn.trafficdata.KServer.client.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Kinglf on 2016/10/24.
 */
public class ConfigUtils extends Properties{


    private static final long serialVersionUID = -7630154103211873965L;
    private static ConfigUtils instance = null;

    public static synchronized ConfigUtils getInstance() {
        if (instance == null) {
            instance = new ConfigUtils();
        }
        return instance;
    }
    public static synchronized ConfigUtils getInstance(String fileName) {
        if (instance == null) {
            instance = new ConfigUtils(fileName);
        }
        return instance;
    }
    public ConfigUtils() {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("config.ini");
        try {
            this.load(in);
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public ConfigUtils(String filename) {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
        try {
            this.load(in);
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
    }
    public String getProperty(String key, String defaultValue) {
        String val = getProperty(key);
        return (val == null || val.isEmpty()) ? defaultValue : val;
    }

    public String getString(String name, String defaultValue) {
        return this.getProperty(name, defaultValue);
    }

    public int getInt(String name, int defaultValue) {
        String val = this.getProperty(name);
        return (val == null || val.isEmpty()) ? defaultValue : Integer.parseInt(val);
    }

    public long getLong(String name, long defaultValue) {
        String val = this.getProperty(name);
        return (val == null || val.isEmpty()) ? defaultValue : Integer.parseInt(val);
    }

    public float getFloat(String name, float defaultValue) {
        String val = this.getProperty(name);
        return (val == null || val.isEmpty()) ? defaultValue : Float.parseFloat(val);
    }

    public double getDouble(String name, double defaultValue) {
        String val = this.getProperty(name);
        return (val == null || val.isEmpty()) ? defaultValue : Double.parseDouble(val);
    }

    public byte getByte(String name, byte defaultValue) {
        String val = this.getProperty(name);
        return (val == null || val.isEmpty()) ? defaultValue : Byte.parseByte(val);
    }
}
