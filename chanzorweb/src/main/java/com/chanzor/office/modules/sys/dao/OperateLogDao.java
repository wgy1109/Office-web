/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.sys.dao;

import com.chanzor.office.common.persistence.CrudDao;
import com.chanzor.office.common.persistence.annotation.MyBatisDao;
import com.chanzor.office.modules.sys.entity.OperateLog;

/**
 * 操作日志DAO接口
 * @author zhangyq
 * @version 2016-10-21
 */
@MyBatisDao
public interface OperateLogDao extends CrudDao<OperateLog> {
	
}