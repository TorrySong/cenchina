package util;


import net.sf.json.JSONObject;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: fyong
 * Date: 15-8-31
 * Time: 上午11:12
 * 青云取数据方法
 */
public class Result {


    public static String getResult(String url) throws IOException {
        String result = "";
        BufferedReader in = null;
        String paramStr = "";
        String requestMethod ="GET";
        try {

            URL realUrl = new URL(url);
            URLConnection connection = null;
            if (url.substring(0, 5).toUpperCase().equals("HTTPS")) {
                HttpsURLConnection httpsConn = (HttpsURLConnection)realUrl.openConnection();

                httpsConn.setHostnameVerifier(new HostnameVerifier(){
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                connection = httpsConn;
            } else {
                connection = realUrl.openConnection();
            }

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            if (requestMethod.equals("POST")) {
                // 发送POST请求必须设置如下两行
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                PrintWriter out = new PrintWriter(connection.getOutputStream());
                // 发送请求参数
                out.print(paramStr);
                // flush输出流的缓冲
                out.flush();
            }

            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
//                e2.printStackTrace();
            }
        }
//        System.out.println("old:"+result);
        return result;
    }
    public static String getObjectResult(String url,String time) throws IOException {
        String result = "";
        BufferedReader in = null;
        String paramStr = "";
        String requestMethod ="GET";

        try {

            URL realUrl = new URL("http://stor.taikangcloud.com:80");
            URLConnection connection = null;
            if (url.substring(0, 5).toUpperCase().equals("HTTPS")) {
                HttpsURLConnection httpsConn = (HttpsURLConnection)realUrl.openConnection();

                httpsConn.setHostnameVerifier(new HostnameVerifier(){
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                connection = httpsConn;
            } else {
                connection = realUrl.openConnection();
            }

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Authorization","QS-HMAC-SHA256 QFMOHFNGZNSJRLEAMIWD:"+url);
            connection.setRequestProperty("Date",time);
            connection.setRequestProperty("Host","stor.taikangcloud.com");
            if (requestMethod.equals("PUT")) {
                // 发送POST请求必须设置如下两行
                connection.setDoOutput(true);
                connection.setDoInput(true);

            }

            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
//                e2.printStackTrace();
            }
        }
//        System.out.println("old:"+result);
        return result;
    }

    /**
     * 发起post请求，返回相应结果字符串
     * @param url 请求路径
     * @param params 请求参数，JSONObject
     * @return
     */
    public static String sendRequestPost(String url,JSONObject params){
        StringBuilder result =new StringBuilder();
        try {
            URL requestUrl = new URL(url);
            URLConnection connection = null;
            if(url.substring(0,5).toUpperCase().equals("HTTPS")){
                HttpsURLConnection httpConn = (HttpsURLConnection)connection;
                httpConn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                });
            }else{
                connection = requestUrl.openConnection();
            }
            connection.setRequestProperty("accept","application/json;");// 设置接收数据的格式
            connection.setRequestProperty("connection","Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("Content-Type","application/json"); // 设置发送数据的格式
            connection.setDoOutput(true);//post请求需要设置如下两行
            connection.setDoInput(true);
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(params.toString());
            out.flush();
            out.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String s=null;
            while ((s=br.readLine())!=null){
                result.append(s);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    public static void main(String[] args) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //"2015-01-28 00:04:00"
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");   //"2015-01-28 00:04:00"
        TimeZone t = TimeZone.getTimeZone("Asia/Shanghai");//不管系统是啥时区，获得+8区时间
        sdf1.setTimeZone(t);
        Date d = new Date();
        String nowtime = sdf.format(d)+"T"+sdf1.format(d)+"Z"; //当前时间

        String enc_nowtime =java.net.URLEncoder.encode(nowtime,"UTF-8");
        System.out.println(enc_nowtime);
//        testPost(enc_nowtime);

        String url ="http://api.it.taikangcloud.com/i3/?access_key_id=ODPXYTGBPIYWQCBFWEMG&action=DescribeUsers&expires="+enc_nowtime+"&lang=zh-cn&signature_method=HmacSHA256&signature_version=1&time_stamp="+enc_nowtime+"&users.1=usr-So2SuSk2";

//        url=QcSign.UrlSingresult(url,"i3");
//        System.out.println(getResult(url));
    }

}
