/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.chanzor.dao;

import java.util.List;
import java.util.Map;

import com.chanzor.office.common.persistence.CrudDao;
import com.chanzor.office.common.persistence.annotation.MyBatisDao;
import com.chanzor.office.modules.chanzor.entity.CzDocument;

@MyBatisDao
public interface CzDocumentDao extends CrudDao<CzDocument> {
	
	public int insertarticle(CzDocument entity);
	
	public int deletearticle(CzDocument entity);
	
	public int updatearticle(CzDocument entity);
	
	public List<CzDocument> getList(Map map);
	
	public Map<String, Object> getCount(Map map);
	
}