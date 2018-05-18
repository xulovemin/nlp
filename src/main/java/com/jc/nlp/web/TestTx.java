package com.jc.nlp.web;

import com.jc.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTx {

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
                if (basiss.size() == summarys.length) {
                    for (int i = 0; i < basiss.size(); i++) {
                        Map<String, Object> mapmany = new HashMap<>();
                        mapmany.put("lawName", basiss.get(i));
                        mapmany.put("ft", summarys[i]);
                        list.add(mapmany);
                    }
                }
            }
        }
        return list;
    }

}
