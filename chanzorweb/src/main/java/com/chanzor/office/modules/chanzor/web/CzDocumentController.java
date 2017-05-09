package com.chanzor.office.modules.chanzor.web;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chanzor.office.common.config.Global;
import com.chanzor.office.common.persistence.Page;
import com.chanzor.office.common.utils.StringUtils;
import com.chanzor.office.common.web.BaseController;
import com.chanzor.office.modules.chanzor.entity.CzChargeRecord;
import com.chanzor.office.modules.chanzor.entity.CzDocument;
import com.chanzor.office.modules.chanzor.service.CzChargeRecordService;
import com.chanzor.office.modules.chanzor.service.CzDocumentService;
import com.chanzor.office.modules.chanzor.util.FileUtil;
import com.chanzor.office.modules.chanzor.util.alipay.FileUtils;
import com.chanzor.office.modules.chanzor.util.alipay.util.AlipaySubmit;
import com.chanzor.office.modules.chanzor.util.alipay.util.AlipayUtil;
import com.chanzor.office.modules.chanzor.util.alipay.util.UtilDate;


@Controller
@RequestMapping(value = "${adminPath}/chanzor/czDocument")
public class CzDocumentController extends BaseController {
	public String nginx_url = "/test/nginx1.8.1/html/Uploads/";    //图片存放路径nginx路径下可以直接访问

	@Autowired
	private CzDocumentService czDocumentService;
	@Autowired
	private CzChargeRecordService czChargeRecordService;
	
	@ModelAttribute
	public CzDocument get(@RequestParam(required=false) String id) {
		CzDocument entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = czDocumentService.get(id);
		}
		if (entity == null){
			entity = new CzDocument();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CzDocument czDocument, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CzDocument> page = czDocumentService.findPage(new Page<CzDocument>(request, response), czDocument); 
		model.addAttribute("page", page);
		return "modules/chanzor/czDocumentList";
	}

	@RequestMapping(value = "form")
	public String form(CzDocument czDocument, Model model) {
		model.addAttribute("czDocument", czDocument);
		return "modules/chanzor/czDocumentForm";
	}

	@RequestMapping(value = "save")
	public String save(CzDocument czDocument, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, czDocument)){
			return form(czDocument, model);
		}
		czDocument.setContent(StringEscapeUtils.unescapeHtml4(czDocument.getContent()));
		czDocumentService.save(czDocument);
		addMessage(redirectAttributes, "保存资讯信息成功");
		return "redirect:"+Global.getAdminPath()+"/chanzor/czDocument/list?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(CzDocument czDocument, RedirectAttributes redirectAttributes) {
		czDocumentService.delete(czDocument);
		addMessage(redirectAttributes, "删除资讯信息成功");
		return "redirect:"+Global.getAdminPath()+"/chanzor/czDocument/list?repage";
	}
	
	@RequestMapping(value = "getList")
	@ResponseBody
	public Map<String, Object> getList(CzDocument czDocument, HttpServletRequest request, HttpServletResponse response) {
		Map reasonMap = getFormData().getReturnMap();
		Map<String,Object> map = new HashMap<String, Object>();
		List<CzDocument> page = czDocumentService.getList(reasonMap);
		Map<String, Object> getCountmap = czDocumentService.getCount(reasonMap);
		map.put("list", page);
		map.put("count", getCountmap.get("count"));
		return map;
	}
	
	@RequestMapping(value = "getContent")
	@ResponseBody
	public String getContent(CzDocument czDocument, HttpServletRequest request, HttpServletResponse response) {
		Map reasonMap = getFormData().getReturnMap();
		CzDocument czdocument = czDocumentService.get(reasonMap.get("id").toString());
		return czdocument.getContent();
	}
	
	@RequestMapping("updateImage")
	@ResponseBody
	public String updateImage(MultipartFile file, HttpServletRequest request) throws Exception {
		if (file == null || file.getSize() / 1024 / 1024 > 10) {
			return "";
		}
		Map data = getFormData().getReturnMap();
		String src = FileUtil.updaloadPic(file, nginx_url + "appSignCert/" + getRandomNum(), "title_img");
		if (!"".equals(src)) {
			src = src.substring(src.indexOf("appSignCert"));
		}
		return src;
	}
	
	@RequestMapping(value = "uploadFile")
	public void uploadFile(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		Map data = getFormData().getReturnMap();
		File file = new File(nginx_url+data.get("filename"));
		FileUtils.downFile(file, request, response);
	}
	
	//生成随机数
	public static int getRandomNum() {
		Random r = new Random();
		return r.nextInt(900000) + 100000;// (Math.random()*(999999-100000)+100000)
	}
	
	/*- 以下支付代码，用于ICP证申请，ICP证申请后可删除 -*/
	
	String notify_url="http://www.chanzor.com/a/chanzor/czDocument/alipay/alipay_notify_url";
	String return_url="http://www.chanzor.com/a/chanzor/czDocument/alipay/alipay_return_url";
	String subject = "畅卓信息资费";
	String body = "北京畅卓科技有限公司有偿信息";
	String money = "5";	
	@RequestMapping(value = "payMth")
	public String payMth(HttpServletRequest request, HttpServletResponse response, Model model) throws Throwable {
		String out_trade_no = UtilDate.getOrderNum();
		Map<String, String> parameterMap = AlipayUtil.buildParameterMap(notify_url, return_url, out_trade_no,
				subject, money, body, "spinfo");
		String sHtmlText = AlipaySubmit.buildRequest(parameterMap, "post", "确认");
		CzChargeRecord czchargerecord = new CzChargeRecord();
		czchargerecord.setAmount(money);
		czchargerecord.setProductId(out_trade_no);
		czChargeRecordService.save(czchargerecord);
		model.addAttribute("sHtmlText", sHtmlText);
		return "modules/chanzor/alipay";
	}
	
	public Map<String, Object> bindParamToMap(HttpServletRequest request) {
		Enumeration enumer = request.getParameterNames();
		Map<String, Object> map = new HashMap<String, Object>();
		while (enumer.hasMoreElements()) {
			String key = (String) enumer.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}
	
	@RequestMapping(value = "alipay/alipay_notify_url")
	public String alipay_notify_url(HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("----------------------------调用异步返回方法-----------------");
		Map<String, Object> param = bindParamToMap(request);
		Map<String, String> p = new HashMap<String, String>();
		for (String key : param.keySet()) {
			p.put(key, param.get(key).toString());
		}
		boolean flag = AlipaySubmit.verify(p);
		if (flag) {
			String ordernum = p.get("out_trade_no");
			CzChargeRecord czchargerecord = new CzChargeRecord();
			czchargerecord.setAlipayType("1");
			czchargerecord.setProductId(ordernum);
			czChargeRecordService.updateTypeByProductId(czchargerecord);
			return "success";
		} else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "alipay/alipay_return_url")
	public ModelAndView alipay_return_url(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> param = bindParamToMap(request);
		Map<String, String> p = new HashMap<String, String>();
		for (String key : param.keySet()) {
			p.put(key, param.get(key).toString());
		}
		boolean flag = AlipaySubmit.verify(p);
		if (flag) {
			String ordernum = p.get("out_trade_no");
			CzChargeRecord czchargerecord = new CzChargeRecord();
			czchargerecord.setProductId(ordernum);
			czchargerecord = czChargeRecordService.getCzChargeRecordBypid(czchargerecord);
			if (czchargerecord.getAlipayType() != null && 1==Integer.parseInt(czchargerecord.getAlipayType().toString())) {
				 	File file = new File("/usr/local/images/demo.rar");
					FileUtils.downFile(file, request, response);
					mv.addObject("sHtmlText", "充值成功!");
					mv.addObject("chargeType", 1);
					mv.setViewName("modules/chanzor/alipayComplete");
					return mv;
				} else {
					mv.addObject("chargeType", 0);
					mv.addObject("sHtmlText", "正在充值，请稍等。。。。。。");
					mv.setViewName("modules/chanzor/alipayComplete");
					return mv;
				}
		} else {
			mv.addObject("chargeType", -1);
			mv.addObject("sHtmlText", "充值失败,验签名失败...");
			mv.setViewName("modules/chanzor/alipayComplete");
		}
		return mv;
	}
	
	@RequestMapping(value = "payindex")
	public String payindex(CzDocument czDocument, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/chanzor/payindex";
	}

}