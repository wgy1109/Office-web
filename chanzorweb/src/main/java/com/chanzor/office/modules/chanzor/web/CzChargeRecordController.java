package com.chanzor.office.modules.chanzor.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chanzor.office.common.config.Global;
import com.chanzor.office.common.persistence.Page;
import com.chanzor.office.common.web.BaseController;
import com.chanzor.office.common.utils.StringUtils;
import com.chanzor.office.modules.chanzor.entity.CzChargeRecord;
import com.chanzor.office.modules.chanzor.service.CzChargeRecordService;

@Controller
@RequestMapping(value = "${adminPath}/chanzor/czChargeRecord")
public class CzChargeRecordController extends BaseController {

	@Autowired
	private CzChargeRecordService czChargeRecordService;
	
	@ModelAttribute
	public CzChargeRecord get(@RequestParam(required=false) String id) {
		CzChargeRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = czChargeRecordService.get(id);
		}
		if (entity == null){
			entity = new CzChargeRecord();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CzChargeRecord czChargeRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CzChargeRecord> page = czChargeRecordService.findPage(new Page<CzChargeRecord>(request, response), czChargeRecord); 
		model.addAttribute("page", page);
		return "modules/chanzor/czChargeRecordList";
	}

	@RequestMapping(value = "form")
	public String form(CzChargeRecord czChargeRecord, Model model) {
		model.addAttribute("czChargeRecord", czChargeRecord);
		return "modules/chanzor/czChargeRecordForm";
	}

	@RequestMapping(value = "save")
	public String save(CzChargeRecord czChargeRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, czChargeRecord)){
			return form(czChargeRecord, model);
		}
		czChargeRecordService.save(czChargeRecord);
		addMessage(redirectAttributes, "保存付费记录成功");
		return "redirect:"+Global.getAdminPath()+"/chanzor/czChargeRecord/list?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(CzChargeRecord czChargeRecord, RedirectAttributes redirectAttributes) {
		czChargeRecordService.delete(czChargeRecord);
		addMessage(redirectAttributes, "删除付费记录成功");
		return "redirect:"+Global.getAdminPath()+"/chanzor/czChargeRecord/list?repage";
	}

}