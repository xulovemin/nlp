package com.jc.nlp.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LawUtil {

    public static List<Map<String, Object>> getFlft(String basis, String basis_summary) {
        List<Map<String, Object>> list = new ArrayList<>();
        basis = StringUtils.replaceS_C(basis);
        basis_summary = StringUtils.replaceS_C(basis_summary);
        Pattern rbas = Pattern.compile("\\《[^\\》]+\\》");
        Matcher mbas = rbas.matcher(basis);
        List<String> basiss = new ArrayList<>();
        while (mbas.find()) {
            basiss.add(mbas.group());
        }
        if (basiss.size() > 0) {
            if (basiss.size() == 1) {
                Map<String, Object> mapone = new HashMap<>();
                mapone.put("lawName", basiss.get(0));
                mapone.put("ft", basis_summary);
                list.add(mapone);
            } else {
                Pattern rsum = Pattern.compile("\\d+\\、|\\d+\\.");
                Matcher msum = rsum.matcher(basis_summary);
                String new_summary = basis_summary;
                while (msum.find()) {
                    String split = msum.group();
                    new_summary = new_summary.replace(split, "&~");
                }
                String[] summarys = new_summary.split("&~");
                if (basiss.size() <= summarys.length) {
                    for (int i = 0; i < basiss.size(); i++) {
                        Map<String, Object> mapmany = new HashMap<>();
                        mapmany.put("lawName", basiss.get(i));
                        mapmany.put("ft", summarys[i]);
                        list.add(mapmany);
                    }
                } else if (basiss.size() > summarys.length) {
                    if(summarys.length == 1) {
                        Map<String, Object> mapone = new HashMap<>();
                        mapone.put("lawName", basiss.get(0));
                        String basis_summary_one = summarys[0];
                        mapone.put("ft", basis_summary_one.substring(0, basis_summary_one.indexOf("。") + 1));
                        list.add(mapone);
                    } else {
                        for (int i = 0; i < summarys.length; i++) {
                            Map<String, Object> mapmany = new HashMap<>();
                            mapmany.put("lawName", basiss.get(i));
                            mapmany.put("ft", summarys[i]);
                            list.add(mapmany);
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> map = getFlft("《建筑工程施工发包与承包计价管理办法》《建设工程工程量清单计价规范》","（住房和城乡建设部令第16号）第六条第二款国有资金投资的建筑工程招标的，应当设有最高投标限价；非国有资金投资的建筑工程招标的，可以设有最高投标限价。第三款最高投标限价及其成果文件，应由招标人报工程所在地县级以上地方人民政府住房城乡建设主管部门备案。2、（GB50500-2013）（2012年住房和城乡建设部国家质量监督检验检疫总局发布）5.1.6“招标人应在发布招标文件时公布招标控制价，同时应将招标控制价及有关资料报送工程所在地或有该工程管辖权的行业主管部门工程造价管理机构备查”。");
        System.out.println(map);
    }
}
