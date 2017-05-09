/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chanzor.office.common.persistence.Page;
import com.chanzor.office.common.service.CrudService;
import com.chanzor.office.common.utils.DateUtils;
import com.chanzor.office.modules.sys.dao.LogDao;
import com.chanzor.office.modules.sys.dao.OfficeDao;
import com.chanzor.office.modules.sys.entity.Log;
import com.chanzor.office.modules.sys.entity.User;

/**
 * 日志Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {
	
	@Autowired
	private LogDao logDao;

	public Page<Log> findPage(Page<Log> page, Log log) {
		
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findPage(page, log);
		
	}
	
	public List<User> findUserListByid(String id){
		return logDao.finduserList(id);
	}
	
	public User findUserByid(String id){
		return logDao.finduserbyid(id);
	}
	
}
