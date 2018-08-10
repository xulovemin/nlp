package com.jc.nlp.service;

import com.jc.core.service.CrudService;
import com.jc.nlp.dao.GeoRegionsDao;
import com.jc.nlp.domain.GeoRegions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GeoRegionsService extends CrudService<GeoRegionsDao, GeoRegions> {

    public List<GeoRegions> queryAllGeo() {
        return dao.queryAllGeo();
    }

}
