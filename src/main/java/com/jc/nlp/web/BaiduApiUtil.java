package com.jc.nlp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaiduApiUtil {

    protected static Logger logger = LoggerFactory.getLogger(BaiduApiUtil.class);

    /**
     * http请求封装方法
     *
     * @param httpArg
     * @param ak
     * @return
     */
    public static String request(String httpArg, String ak) {
        BufferedReader reader = null;
        String result = "";
        StringBuffer sbf = new StringBuffer();
        String httpUrl = "http://api.map.baidu.com/geocoder/v2/?" + httpArg + "&output=json&ak=" + ak;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("百度异常:", e);
        }
        return result;
    }

    public static void main(String[] args) {
        String httpAddr = "address=元竹镇";
        String result = request(httpAddr, "584fa8af082f87b10783eab60748e804");
        System.out.println(result);
        String httpLoca = "location=32.33532081355528,120.20192960766515";
        String result1 = request(httpLoca, "584fa8af082f87b10783eab60748e804");
        System.out.println(result1);
    }
}