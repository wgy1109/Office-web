/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.common.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chanzor.office.common.beanvalidator.BeanValidators;
import com.chanzor.office.common.mapper.JsonMapper;
import com.chanzor.office.common.utils.DateUtils;
import com.chanzor.office.modules.chanzor.util.alipay.FormData;


/**
 * 控制器支持类
 * @author ThinkGem
 * @version 2013-3-23
 */
public abstract class BaseController {
	
	@Autowired
	public SmsHttpClient smsHttpClient;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	@Autowired
	protected RestTemplate restTemplate;
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}
	
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {  
        return "error/400";
    }
	
	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({AuthenticationException.class})
    public String authenticationException() {  
        return "error/403";
    }
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}
	
	/**
	 * 得到FormData
	 */
	public FormData getFormData() {
		return new FormData(this.getRequest());
	}
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	
	public List<Map<String, Object>> jsonArrayToList(JSONArray jsonArray){
		  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		  for(int i = 0; i<jsonArray.length(); i++){
			  JSONObject json = jsonArray.getJSONObject(i);
			  Map<String, Object> map = new HashMap<String, Object>();
			  Iterator keys=json.keys();  
			    while(keys.hasNext()){  
			        String key=(String) keys.next();  
			        String value=json.get(key).toString();  
			        map.put(key, value);  
			    } 
			  list.add(map);
		  }
		  return list;
	}
	
	public Map<String, Object> jsonObjectToList(JSONObject jsonObject){
		  Map<String, Object> map = new HashMap<String, Object>();
		  Iterator keys=jsonObject.keys();  
		    while(keys.hasNext()){  
		        String key=(String) keys.next();  
		        String value=jsonObject.get(key).toString();  
		        map.put(key, value);  
		    } 
		  return map;
	}
	
	// 辅助 得到 总数和总计费数
	public String Allnum(Map<String, Object> sms) {
		int num = (Integer) sms.get("charge_count") + (Integer) sms.get("error_count");
		String Allnum = sms.get("phone_count").toString() + "/" + num;
		return Allnum;
	}

	// 辅助 得到 成功条数、失败条数、未知条数
	public String yesOrNo(Map<String, Object> sms) {
		String yesOrNo = "0/0/0";
		if ((Integer) sms.get("status") >= 300 && (Integer) sms.get("status") < 400) {
			yesOrNo = sms.get("charge_count") + "/" + sms.get("error_count") + "/" + 0;
		}
		return yesOrNo;
	}
	
	// 辅助 得到发送运营商数量
	public String getMessageSendChannel(Map<String, Object> sms) {
		String messageSendChannel = ((Integer) sms.get("cmpp_count") != 0
				? ("移动(" + sms.get("cmpp_count").toString() + ")") : "")
				+ ((Integer) sms.get("sgip_count") != 0 ? ("联通(" + sms.get("sgip_count").toString() + ")") : "")
				+ ((Integer) sms.get("smgp_count") != 0 ? ("电信(" + sms.get("smgp_count").toString() + ")") : "");
		return messageSendChannel;
	}

	public boolean isPhone(String value) {
		String phoneEvelidate = "^[1][3,4,5,7,8][0-9]{9}$";
		Pattern p = Pattern.compile(phoneEvelidate);
		Matcher m = p.matcher(value);
		return m.matches();
	}
	
	public List<String> checkDistinct(List<Map<String, Object>> list,String Type) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = "";
		String str="";
		if(Type.equals("phone")){
			str="MDN";
		}else{
			str="CONTENT";
		}
		for (Map<String, Object> listMap : list) {
			mobile += listMap.get(str) + ",";
		}
		String[] word = mobile.split(",");
		TreeSet<String> set = new TreeSet();// 去重复使用TreeSet
		// 排序
		Arrays.sort(word);
		// 计算出现个数
		for (int i = 0; i < word.length; i++) {
			for (int j = 0; j < word.length; j++) {
				if (word[i].equals(word[j])) {
				}
			}
			set.add(word[i]);
		}
		// 去重复
		List<String> sList = new ArrayList<String>();
		for (String s : set) {
			sList.add(s);
		}
		return sList;
	}
	
}
