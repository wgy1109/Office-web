/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.chanzor.dao;

import com.chanzor.office.common.persistence.CrudDao;
import com.chanzor.office.common.persistence.annotation.MyBatisDao;
import com.chanzor.office.modules.chanzor.entity.CzChargeRecord;

@MyBatisDao
public interface CzChargeRecordDao extends CrudDao<CzChargeRecord> {
	
	public int updateTypeByProductId(CzChargeRecord czChargeRecord);
	
	public CzChargeRecord getCzChargeRecordBypid(CzChargeRecord czChargeRecord);
	
	
}