package com.jc.nlp.web;

import com.jc.common.utils.StringUtils;
import com.jc.core.web.BaseController;
import com.jc.nlp.domain.Right;
import com.jc.nlp.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class NlpController extends BaseController {

    @Autowired
    private RightService rightService;

    @RequestMapping("/form")
    public String form() {
        return "form";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        logger.info("12312331");
        List<Right> list = rightService.findList(new Right());
        return "";
    }

    @RequestMapping("/hanlp")
    public Map<String, Object> hanlp(String text) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, List<String>> map = new HashMap<>();
        List<String> personList = new ArrayList<>();
        List<String> orgList = new ArrayList<>();
        List<String> locationList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        String[] laws = text.split("《");
        for (int i = 1; i < laws.length; i++) {
            //提取法条
            String law = "《" + laws[i];
            Pattern p = Pattern.compile("(?<=第).*?(?=条)");
            Matcher m = p.matcher(law);
            ArrayList<String> list = new ArrayList<String>();
            while (m.find()) {
                list.add("第" + String.valueOf(m.group(0)) + "条");
            }
            if (list.size() != 0) {
                //提取法律
                Pattern p1 = Pattern.compile("(?<=《)[^》]+(?=》)");
                Matcher m1 = p1.matcher(law);
                String lawName = "";
                while (m1.find()) {
                    lawName = m1.group(0);
                }
                resultList.add("《" + lawName + "》" + StringUtils.join2(list, ","));
            }
        }
        personList = new ArrayList<>(new HashSet<>(personList));
        orgList = new ArrayList<>(new HashSet<>(orgList));
        locationList = new ArrayList<>(new HashSet<>(locationList));
        resultList = new ArrayList<>(new HashSet<>(resultList));
        map.put("persion", personList);
        map.put("org", orgList);
        map.put("location", locationList);
        map.put("law", resultList);
        resultMap.put("state", "1");
        resultMap.put("list", map);
        return resultMap;
    }

}
