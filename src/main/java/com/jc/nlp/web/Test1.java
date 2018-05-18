package com.jc.nlp.web;

import com.jc.common.utils.FileUtils;
import com.jc.nlp.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {
    public static void main(String[] args) {
        File f = new File(Test.class.getResource("/flfg.txt").getFile());
        try {
            List<String> lists = FileUtils.readLines(f);
            List<Map<String, Object>> listResult = new ArrayList<>();
            for (String text : lists) {
                Map<String, Object> map = getFlft(text);
                listResult.add(map);
            }
            System.out.println(JsonUtil.java2Json(listResult));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, Object> getFlft(String text) {
        Map<String, Object> map = new HashMap<>();
        while (text.length() > 0) {
            if (text.indexOf("《") != -1) {
                text = text.substring(text.indexOf("《"));
                if (text.indexOf("。") != -1 && text.indexOf("。") + 1 <= text.length()) {
                    String step = text.substring(text.indexOf("《"), text.indexOf("。") + 1);
                    Pattern p = Pattern.compile("(?<=第).*?(?=条)");
                    Matcher m = p.matcher(step);
                    if (m.find()) {
                        String ft = step.substring(m.start() - 1);
                        Pattern p1 = Pattern.compile("(?<=《)[^》]+(?=》)");
                        Matcher m1 = p1.matcher(step);
                        String lawName = "";
                        while (m1.find()) {
                            lawName = m1.group(0);
                        }
                        map.put("lawName", "《" + lawName + "》");
                        map.put("ft", ft);
                    }
                    text = text.substring(text.indexOf("。") + 1);
                    if (text.indexOf("《") != -1) {
                        text = text.substring(text.indexOf("《"));
                    } else {
                        text = "";
                    }
                } else if (text.indexOf(".") != -1 && text.indexOf(".") + 1 <= text.length()) {
                    String step = text.substring(text.indexOf("《"), text.indexOf(".") + 1);
                    Pattern p = Pattern.compile("(?<=第).*?(?=条)");
                    Matcher m = p.matcher(step);
                    if (m.find()) {
                        String ft = step.substring(m.start() - 1);
                        Pattern p1 = Pattern.compile("(?<=《)[^》]+(?=》)");
                        Matcher m1 = p1.matcher(step);
                        String lawName = "";
                        while (m1.find()) {
                            lawName = m1.group(0);
                        }
                        map.put("lawName", "《" + lawName + "》");
                        map.put("ft", ft);
                    }
                    text = text.substring(text.indexOf(".") + 1);
                    if (text.indexOf("《") != -1) {
                        text = text.substring(text.indexOf("《"));
                    } else {
                        text = "";
                    }
                } else {
                    String step = text.substring(text.indexOf("《"));
                    Pattern p = Pattern.compile("(?<=第).*?(?=条)");
                    Matcher m = p.matcher(step);
                    if (m.find()) {
                        String ft = step.substring(m.start() - 1);
                        Pattern p1 = Pattern.compile("(?<=《)[^》]+(?=》)");
                        Matcher m1 = p1.matcher(step);
                        String lawName = "";
                        while (m1.find()) {
                            lawName = m1.group(0);
                        }
                        map.put("lawName", "《" + lawName + "》");
                        map.put("ft", ft);
                    }
                    text = "";
                }
            } else {
                text = "";
            }
        }
        return map;
    }
}
