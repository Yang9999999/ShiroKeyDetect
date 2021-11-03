package utils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    public boolean HttpShiroDetect(){
        return true;
    }
    public static HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
    // Java get 发包,返回值为body内容
    public static String sendGet(String url, String rememberMe) {
        String result = "";
        BufferedReader in = null;
        URLConnection httpUrlConn = null;
        HttpsURLConnection hsc = null;
        HttpURLConnection hc = null;
        InputStream inputStream = null;
        InputStreamReader isr = null;
        Object br = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            if (url.startsWith("https")) {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] tm = new TrustManager[]{new MyCert()};
                sslContext.init(null, tm, new SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                hsc = (HttpsURLConnection)realUrl.openConnection();

                hsc.setSSLSocketFactory(ssf);
                hsc.setHostnameVerifier(allHostsValid);
                httpUrlConn = hsc;
            }else {
                hc = (HttpURLConnection)realUrl.openConnection();
                hc.setRequestMethod("GET");
                hc.setInstanceFollowRedirects(false);
                System.out.println(hc.getRequestProperties());
                httpUrlConn = hc;
            }
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            httpUrlConn.setRequestProperty("accept", "*/*");
            httpUrlConn.setRequestProperty("connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpUrlConn.setRequestProperty("Cookie","rememberMe="+rememberMe);
            // 建立实际的连接
            httpUrlConn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = httpUrlConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    httpUrlConn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    // Java get 发包,返回值为Header内容
    public static String getHeader(String url, String rememberMe) {
        String result = "";
        BufferedReader in = null;

        URLConnection httpUrlConn = null;
        HttpsURLConnection hsc = null;
        HttpURLConnection hc = null;
        InputStream inputStream = null;
        InputStreamReader isr = null;
        Object br = null;
        try {
            URL realUrl = new URL(url);
            if (url.startsWith("https")) {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] tm = new TrustManager[]{new MyCert()};
                sslContext.init(null, tm, new SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                hsc = (HttpsURLConnection)realUrl.openConnection();

                hsc.setSSLSocketFactory(ssf);
                hsc.setHostnameVerifier(allHostsValid);
                httpUrlConn = hsc;
            }else {
                hc = (HttpURLConnection)realUrl.openConnection();
                hc.setRequestMethod("GET");
                hc.setInstanceFollowRedirects(false);
                System.out.println(hc.getRequestProperties());
                httpUrlConn = hc;
            }
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            httpUrlConn.setRequestProperty("accept", "*/*");
            httpUrlConn.setRequestProperty("connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpUrlConn.setRequestProperty("Cookie","rememberMe="+rememberMe);
            // 建立实际的连接
            httpUrlConn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = httpUrlConn.getHeaderFields();
            // 遍历所有的响应头字段
            String line;
            for (String key : map.keySet()) {
                result += key + "--->" + map.get(key);
            }
            return result;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
