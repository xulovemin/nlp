package com.jc.system.listener;

import com.jc.nlp.util.CacheInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void onApplicationEvent(final ApplicationReadyEvent event) {
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        CacheInit.initAllRegions();
        CacheInit.initProvinceCache();
        CacheInit.initCityCache();
    }
}
