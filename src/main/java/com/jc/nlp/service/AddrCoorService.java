package com.jc.nlp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.core.service.CrudService;
import com.jc.nlp.domain.AddrCoor;
import com.jc.nlp.dao.AddrCoorDao;

@Service
@Transactional(readOnly = true)
public class AddrCoorService extends CrudService<AddrCoorDao, AddrCoor> {
	
}
