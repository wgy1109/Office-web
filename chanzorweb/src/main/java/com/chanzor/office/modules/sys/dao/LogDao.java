/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.sys.dao;

import java.util.List;

import com.chanzor.office.common.persistence.CrudDao;
import com.chanzor.office.common.persistence.annotation.MyBatisDao;
import com.chanzor.office.modules.sys.entity.Log;
import com.chanzor.office.modules.sys.entity.*;

/**
 * 日志DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {
	public List<User> finduserList(String id);			//查找主管所在小组的员工
	
	public User finduserbyid(String id);
	
	
}
