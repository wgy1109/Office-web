/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.chanzor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chanzor.office.common.persistence.Page;
import com.chanzor.office.common.service.CrudService;
import com.chanzor.office.modules.chanzor.entity.CzChargeRecord;
import com.chanzor.office.modules.chanzor.dao.CzChargeRecordDao;
import com.chanzor.office.modules.chanzor.dao.CzDocumentDao;

@Service
@Transactional(readOnly = true)
public class CzChargeRecordService extends CrudService<CzChargeRecordDao, CzChargeRecord> {
	
	@Autowired
	CzChargeRecordDao dao;

	public CzChargeRecord get(String id) {
		return super.get(id);
	}
	
	public List<CzChargeRecord> findList(CzChargeRecord czChargeRecord) {
		return super.findList(czChargeRecord);
	}
	
	public Page<CzChargeRecord> findPage(Page<CzChargeRecord> page, CzChargeRecord czChargeRecord) {
		return super.findPage(page, czChargeRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(CzChargeRecord czChargeRecord) {
		super.save(czChargeRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(CzChargeRecord czChargeRecord) {
		super.delete(czChargeRecord);
	}
	@Transactional(readOnly = false)
	public int updateTypeByProductId(CzChargeRecord czChargeRecord){
		return dao.updateTypeByProductId(czChargeRecord);
	}
	
	public CzChargeRecord getCzChargeRecordBypid(CzChargeRecord czChargeRecord){
		return dao.getCzChargeRecordBypid(czChargeRecord);
	}
	
}