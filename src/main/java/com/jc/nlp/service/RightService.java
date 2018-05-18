package com.jc.nlp.service;

import com.jc.core.service.CrudService;
import com.jc.nlp.dao.RightDao;
import com.jc.nlp.domain.Right;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RightService extends CrudService<RightDao, Right> {

}
