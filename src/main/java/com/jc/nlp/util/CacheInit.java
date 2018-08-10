package com.jc.nlp.util;

import com.jc.common.utils.CacheUtils;
import com.jc.common.utils.SpringContextHolder;
import com.jc.nlp.domain.GeoRegions;
import com.jc.nlp.service.GeoRegionsService;

import java.util.*;

public class CacheInit {

    private static GeoRegionsService geoRegionsService = SpringContextHolder.getBean("geoRegionsService");

    private static final String CACHE_PROVINCE = "cache_province";

    private static final String CACHE_CITY = "cache_city";

    private static final String All_REGIONS = "all_regions";

    public static List<Map> initProvinceCache() {
        List<Map> allRegions = (List<Map>) CacheUtils.get(All_REGIONS);
        if (allRegions == null) {
            allRegions = initAllRegions();
        }
        List<Map> provinces = new ArrayList<>();
        for (Map region : allRegions) {
            Map provincename = new HashMap();
            provincename.put("value", region.get("value"));
            provincename.put("name", region.get("name"));
            provinces.add(provincename);
            String abbr = (String) region.get("abbr");
            if (!"".equals(abbr)) {
                String[] abbrs = abbr.split(",");
                for (String a : abbrs) {
                    Map provinceabbr = new HashMap();
                    provinceabbr.put("value", region.get("value"));
                    provinceabbr.put("name", a);
                    provinces.add(provinceabbr);
                }
            }
        }
        CacheUtils.put(CACHE_PROVINCE, provinces);
        return provinces;
    }

    public static List<Map> initCityCache() {
        List<Map> allRegions = (List<Map>) CacheUtils.get(All_REGIONS);
        if (allRegions == null) {
            allRegions = initAllRegions();
        }
        List<Map> citys = new ArrayList<>();
        for (Map region : allRegions) {
            List<Map> subscity = (List<Map>) region.get("subs");
            for (Map city : subscity) {
                Map cityname = new HashMap();
                cityname.put("value", city.get("value"));
                cityname.put("name", city.get("name"));
                citys.add(cityname);
                String abbr = (String) city.get("abbr");
                if (!"".equals(abbr)) {
                    String[] abbrs = abbr.split(",");
                    for (String a : abbrs) {
                        Map cityabbr = new HashMap();
                        cityabbr.put("value", city.get("value"));
                        cityabbr.put("name", a);
                        citys.add(cityabbr);
                    }
                }
            }
        }
        CacheUtils.put(CACHE_CITY, citys);
        return citys;
    }

    public static List<Map> initAllRegions() {
        List<GeoRegions> geoRegions = geoRegionsService.queryAllGeo();
        List<GeoRegions> proviences = new ArrayList<>();
        for (GeoRegions georegion : geoRegions) {
            if (String.valueOf(georegion.getCode()).substring(2).equals("0000")) {
                proviences.add(georegion);
            }
        }
        List<Map> regions = new ArrayList<>();
        for (GeoRegions provience : proviences) {
            List<GeoRegions> citys = new ArrayList<>();
            for (GeoRegions georegioncity : geoRegions) {
                if (provience.getCode() != georegioncity.getCode() && String.valueOf(provience.getCode()).substring(0, 2).equals(String.valueOf(georegioncity.getCode()).substring(0, 2)) && String.valueOf(georegioncity.getCode()).substring(4).equals("00")) {
                    citys.add(georegioncity);
                }
            }
            Map<String, Object> mapProvience = new LinkedHashMap<>();
            List<Map> listCitys = new ArrayList<>();
            for (GeoRegions city : citys) {
                Map<String, Object> mapCity = new LinkedHashMap<>();
                List<Map> listCountys = new ArrayList<>();
                for (GeoRegions county : geoRegions) {
                    if (city.getCode() != county.getCode() && String.valueOf(city.getCode()).substring(0, 4).equals(String.valueOf(county.getCode()).substring(0, 4))) {
                        Map<String, Object> mapCounty = new LinkedHashMap<>();
                        mapCounty.put("value", String.valueOf(county.getCode()));
                        mapCounty.put("name", county.getName());
                        if (county.getAbbr() != null) {
                            mapCounty.put("abbr", county.getAbbr());
                        } else {
                            mapCounty.put("abbr", "");
                        }
                        listCountys.add(mapCounty);
                    }
                }
                mapCity.put("value", String.valueOf(city.getCode()));
                mapCity.put("subs", listCountys);
                mapCity.put("name", city.getName());
                if (city.getAbbr() != null) {
                    mapCity.put("abbr", city.getAbbr());
                } else {
                    mapCity.put("abbr", "");
                }
                listCitys.add(mapCity);
            }
            mapProvience.put("value", String.valueOf(provience.getCode()));
            mapProvience.put("subs", listCitys);
            mapProvience.put("name", provience.getName());
            if (provience.getAbbr() != null) {
                mapProvience.put("abbr", provience.getAbbr());
            } else {
                mapProvience.put("abbr", "");
            }
            regions.add(mapProvience);
        }
        CacheUtils.put(All_REGIONS, regions);
        return regions;
    }

}
