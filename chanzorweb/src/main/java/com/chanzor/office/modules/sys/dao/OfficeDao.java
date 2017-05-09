/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.chanzor.office.common.persistence.TreeDao;
import com.chanzor.office.common.persistence.annotation.MyBatisDao;
import com.chanzor.office.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	public List<Office> findOfficeParentList(Map<String, Object> map);

	public Office findOfficeById(String officeId);
	
	public List<Office> findSaleOffice(Office office);

}
