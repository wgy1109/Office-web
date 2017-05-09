package com.chanzor.office.modules.phoneData.web.datamanage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chanzor.office.common.web.BaseController;
import com.chanzor.office.common.web.SmsHttpClient;

@Controller
@RequestMapping(value = "${adminPath}/phoneData/dataManage")
public class PhoneDataController extends BaseController {
	
	@Autowired
	private SmsHttpClient smsHttpClient;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = {"list"})
	public String list( HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/phonedata/datamanage/list";
	}

}
