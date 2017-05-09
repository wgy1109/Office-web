/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.sys.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chanzor.office.common.persistence.Page;
import com.chanzor.office.common.service.CrudService;
import com.chanzor.office.modules.sys.dao.OperateLogDao;
import com.chanzor.office.modules.sys.entity.OperateLog;

/**
 * 操作日志Service
 * 
 * @author zhangyq
 * @version 2016-10-21
 */
@Service
@Transactional(readOnly = true)
public class OperateLogService extends CrudService<OperateLogDao, OperateLog> {

	public OperateLog get(String id) {
		return super.get(id);
	}

	public List<OperateLog> findList(OperateLog operateLog) {
		return super.findList(operateLog);
	}

	public Page<OperateLog> findPage(Page<OperateLog> page, OperateLog operateLog) {
		return super.findPage(page, operateLog);
	}

	@Transactional(readOnly = false)
	public void save(OperateLog operateLog) {

		super.save(operateLog);
	}

	@Transactional(readOnly = false)
	public void delete(OperateLog operateLog) {
		super.delete(operateLog);
	}

	@SuppressWarnings("unchecked")
	public String getAllLogInfo(Object oldInfo, Object newInfo, String type) {
		String oldContent = "";
		String allContent = "";
		if (oldInfo != null) {
			for (String key : ((Map<String, Object>) oldInfo).keySet()) {
				oldContent += key + "=" + ((Map<String, Object>) oldInfo).get(key) + ",";
			}
		}
		String newContent = "";
		for (String key : ((Map<String, Object>) newInfo).keySet()) {
			newContent += key + "=" + ((Map<String, Object>) newInfo).get(key) + ",";
		}
		if (oldInfo != null) {
			allContent = "修改前:" + oldContent + "修改后:" + newContent;
		} else {
			allContent += "新增" + type + ":" + newContent;
		}
		return allContent;

	}
	
	@SuppressWarnings("unchecked")
	public String getAllChannelInfo(Map<String, Object> list) {
		String oldContent = "";
		String allContent = "";
		if (list.get("oldChannelGroup") != null) {
			for (Integer channel : (List<Integer>)list.get("oldChannelGroup")) {
				oldContent += channel+ ",";
			}
		}
		String newContent = "";
		for( Integer channel : (List<Integer>)list.get("newChannelGroup")) {
			newContent += channel + ",";
		}
		if (list.get("oldChannelGroup") != null) {
			allContent = "修改前:" + oldContent + "修改后:" + newContent;
		} else {
			allContent +=newContent;
		}
		return allContent;

	}

}