package com.jc.nlp.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public JsonUtil() {
    	
    }

    public static String java2Json(Object object) {
        StringWriter sw = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(sw, object);
        } catch (JsonGenerationException var5) {
        } catch (JsonMappingException var6) {
        } catch (IOException var7) {
        }
        if (sw != null) {
            try {
                sw.flush();
                sw.close();
            } catch (IOException var4) {
            }
        }
        return sw.toString();
    }

    public static <T> Object json2Java(String json, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        Object jacksonToBean = null;
        if (json != null && !"".equals(json)) {
            try {
                jacksonToBean = mapper.readValue(json, type);
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }
        return jacksonToBean;
    }
    
    public static <T> List<T> json2Array(String json, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, new Class[]{type});
        Object list = new ArrayList();
        try {
            list = (List)mapper.readValue(json, javaType);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return (List)list;
    }

}
