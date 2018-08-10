package com.jc.nlp.dao;

import com.jc.core.persistence.dao.CrudDao;
import com.jc.nlp.domain.GeoRegions;

import java.util.List;

public interface GeoRegionsDao extends CrudDao<GeoRegions> {

    List<GeoRegions> queryAllGeo();

}