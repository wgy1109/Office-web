/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chanzor.office.common.persistence.Page;
import com.chanzor.office.common.utils.DateUtils;
import com.chanzor.office.common.utils.StringUtils;
import com.chanzor.office.common.web.BaseController;
import com.chanzor.office.modules.sys.entity.OperateLog;
import com.chanzor.office.modules.sys.service.OperateLogService;

/**
 * 操作日志Controller
 * @author zhangyq
 * @version 2016-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/operateLog")
public class OperateLogController extends BaseController {

	@Autowired
	private OperateLogService operateLogService;
	
	@ModelAttribute
	public OperateLog get(@RequestParam(required=false) String id) {
		OperateLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = operateLogService.get(id);
		}
		if (entity == null){
			entity = new OperateLog();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(OperateLog operateLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OperateLog> page = operateLogService.findPage(new Page<OperateLog>(request, response), operateLog); 
		model.addAttribute("page", page);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(operateLog.getUser_chinese_name())){
			returnMap.put("user_chinese_name", operateLog.getUser_chinese_name());
		}
		if(operateLog.getLog_type() != null){
			returnMap.put("log_type",operateLog.getLog_type());
		}
		if(operateLog.getCreate_date_begin() != null){
			returnMap.put("create_date_begin",DateUtils.formatDate(operateLog.getCreate_date_begin(), "yyyy-MM-dd HH:mm:ss"));
		}
		if(operateLog.getCreate_date_end() != null){
			returnMap.put("create_date_end",DateUtils.formatDate(operateLog.getCreate_date_end(), "yyyy-MM-dd HH:mm:ss"));
		}
		model.addAttribute("returnMap", returnMap);
		return "modules/sys/operateLogList";
	}

	

}