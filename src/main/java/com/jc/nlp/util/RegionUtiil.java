package com.jc.nlp.util;

import com.jc.common.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegionUtiil {

    private static final String CACHE_PROVINCE = "cache_province";

    private static final String CACHE_CITY = "cache_city";

    private static final String All_REGIONS = "all_regions";

    /**
     * 无地理信息，自己检索
     *
     * @return
     */
    public static String getRegionCode(String content) {
        String regionCode;
        List<Map> allRegions = (List<Map>) CacheUtils.get(All_REGIONS);
        if (allRegions == null) {
            allRegions = CacheInit.initAllRegions();
        }
        List<Map> provinces = (List<Map>) CacheUtils.get(CACHE_PROVINCE);
        if (provinces == null) {
            provinces = CacheInit.initProvinceCache();
        }
        List<Map> citys = (List<Map>) CacheUtils.get(CACHE_CITY);
        if (citys == null) {
            citys = CacheInit.initCityCache();
        }
        String provinceCode = "";
        for (Map province : provinces) {
            if (content.contains((String) province.get("name"))) {
                provinceCode = (String) province.get("value");
                break;
            }
        }
        if (!"".equals(provinceCode)) { /*找到省级区划*/
            List<Map> cityRegions = new ArrayList<>();
            for (Map region : allRegions) {
                if (provinceCode.equals(region.get("value"))) {
                    cityRegions = (List<Map>) region.get("subs");
                    break;
                }
            }
            String cityCode = "";
            for (Map cityRegion : cityRegions) {
                if (content.contains((String) cityRegion.get("name"))) {
                    cityCode = (String) cityRegion.get("value");
                    break;
                }
            }
            if (!"".equals(cityCode)) {/*找到市级区划*/
                List<Map> conRegions = new ArrayList<>();
                for (Map cityRegion : cityRegions) {
                    if (cityCode.equals(cityRegion.get("value"))) {
                        conRegions = (List<Map>) cityRegion.get("subs");
                        break;
                    }
                }
                String conCode = "";
                for (Map conRegion : conRegions) {
                    if (content.contains((String) conRegion.get("name"))) {
                        conCode = (String) conRegion.get("value");
                        break;
                    }
                }
                if (!"".equals(conCode)) { /*找到县级行政区划*/
                    regionCode = conCode;
                } else {/*没找到县级行政区划*/
                    regionCode = cityCode;
                }
            } else {/*没找到市级区划*/
                regionCode = provinceCode;
            }
        } else { /*没找到省级区划*/
            String cityCode = "";
            for (Map city : citys) {
                if (content.contains((String) city.get("name"))) {
                    cityCode = (String) city.get("value");
                    break;
                }
            }
            if (!"".equals(cityCode)) {/*找到市级行政区划*/
                List<Map> conRegions = new ArrayList<>();
                out:
                for (Map region : allRegions) {
                    String pjpro = cityCode.substring(0, 2) + "0000";
                    if (pjpro.equals(region.get("value"))) {
                        for (Map citysub : (List<Map>) region.get("subs")) {
                            if (cityCode.equals(citysub.get("value"))) {
                                conRegions = (List<Map>) citysub.get("subs");
                                break out;
                            }
                        }
                    }
                }
                String conCode = "";
                for (Map conRegion : conRegions) {
                    if (content.contains((String) conRegion.get("name"))) {
                        conCode = (String) conRegion.get("value");
                        break;
                    }
                }
                if (!"".equals(conCode)) { /*找到县级行政区划*/
                    regionCode = conCode;
                } else {/*没找到县级行政区划*/
                    regionCode = cityCode;
                }
            } else {/*没有找到市级行政区划*/
                regionCode = cityCode;
            }
        }
        return regionCode;
    }


}
