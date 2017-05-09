/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.chanzor.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chanzor.office.common.persistence.Page;
import com.chanzor.office.common.service.CrudService;
import com.chanzor.office.modules.chanzor.entity.CzDocument;
import com.chanzor.office.modules.chanzor.dao.CzDocumentDao;

@Service
@Transactional(readOnly = true)
public class CzDocumentService extends CrudService<CzDocumentDao, CzDocument> {
	
	@Autowired
	CzDocumentDao dao;

	public CzDocument get(String id) {
		return super.get(id);
	}
	
	public List<CzDocument> getList(Map map) {
		return dao.getList(map);
	}
	
	public List<CzDocument> findList(CzDocument czDocument) {
		return super.findList(czDocument);
	}
	
	public Page<CzDocument> findPage(Page<CzDocument> page, CzDocument czDocument) {
		return super.findPage(page, czDocument);
	}
	
	@Transactional(readOnly = false)
	public void save(CzDocument czDocument) {
		if (czDocument.getIsNewRecord()){
			super.save(czDocument);
			dao.insertarticle(czDocument);
		}else{
			super.save(czDocument);
			dao.updatearticle(czDocument);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CzDocument czDocument) {
		dao.deletearticle(czDocument);
		super.delete(czDocument);
	}
	
	public Map<String, Object> getCount(Map map) {
		return dao.getCount(map);
	}
	
}