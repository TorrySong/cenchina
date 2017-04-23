package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**属性文件工具类
 * Created by t-songtao on 2015/12/3.
 */
public final class PropsUtil {

    /**
     *  加载属性文件
     */
    public static Properties loadProps(String fileNam){
        Properties props = null;
        InputStream in = null;
        in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileNam);
        props = new Properties();
        try {
            props.load(new InputStreamReader(in,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

    /**
     * 获取属性值，字符串类型，默认值为""
     */
   public static String getValue(Properties properties, String key){
       String value = "";
       if(properties.containsKey(key)){
           value = properties.getProperty(key);
       }
       return value;
}


}
