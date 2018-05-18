package com.jc.nlp.web;

import com.jc.common.utils.FileUtils;
import com.jc.common.utils.StringUtils;
import com.jc.nlp.util.FileUtil;
import com.jc.nlp.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args) {
        File f = new File(Test.class.getResource("/flfg.txt").getFile());
        try {
            List<String> lists = FileUtils.readLines(f);
            for (String text : lists) {
                getFlft(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getFlft(String text) {
        String[] laws = text.replaceAll(" ", "").split("《");
        List<String> resultList = new ArrayList<>();
        for (int i = 1; i < laws.length; i++) {
            //提取法条
            String law = "《" + laws[i];
            Pattern p = Pattern.compile("(?<=第).*?(?=条)");
            Matcher m = p.matcher(law);
            String ft = "";
            if (m.find()) {
                ft = "第" + String.valueOf(m.group(0)) + "条";
            }
            if (ft.length() != 0) {
                //提取法律
                Pattern p1 = Pattern.compile("(?<=《)[^》]+(?=》)");
                Matcher m1 = p1.matcher(law);
                String fl = "";
                while (m1.find()) {
                    fl = m1.group(0);
                }
                String flft = "《" + fl + "》" + ft;
                if (text.contains(flft)) {
                    resultList.add(flft);
                }
            }
        }
        System.out.println(JsonUtil.java2Json(resultList));
    }

}
