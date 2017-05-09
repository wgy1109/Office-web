package com.chanzor.office.modules.chanzor.util.alipay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.message.BasicNameValuePair;

import com.chanzor.office.common.utils.Const;
import com.chanzor.office.common.utils.StringUtils;

@SuppressWarnings("rawtypes")
public class FormData extends ArrayList implements List, Serializable {

	private static final long serialVersionUID = 1L;

	List<BasicNameValuePair> formParam = null ;
	Map returnMap = null;
	HttpServletRequest request;
	public Map getReturnMap() {
		return returnMap;
	}
	public void setReturnMap(Map returnMap) {
		this.returnMap = returnMap;
	}
	public List<BasicNameValuePair> getFormParam() {
		return formParam;
	}
	public void setFormParam(List<BasicNameValuePair> formParam) {
		this.formParam = formParam;
	}

	@SuppressWarnings("unchecked")
	public FormData(HttpServletRequest request) {
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMaps = new HashMap();
		List formParams = new ArrayList(); 
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			if(name.equals("pageSize")){
				if(StringUtils.isNotBlank(value)){
					if(Integer.parseInt(value) > 500){
						value = "500";
					}
				}
			}
			returnMaps.put(name, value);
			formParams.add(new BasicNameValuePair(name, value)); 
		}
		if(returnMaps.containsKey("pageNo")){
			returnMaps.put("currentPage", returnMaps.get("pageNo"));
			formParams.add(new BasicNameValuePair("currentPage", returnMaps.get("pageNo").toString()));
		}else{
			returnMaps.put("currentPage", Const.PAGENO);
			formParams.add(new BasicNameValuePair("currentPage", Const.PAGENO)); 
			formParams.add(new BasicNameValuePair("pageSize", Const.PAGESIZE)); 
		}
		String SALE_MANAGER_ID = request.getSession().getAttribute("SALE_MANAGER_ID")+"";
	    formParams.add(new BasicNameValuePair("SALE_MANAGER_ID", SALE_MANAGER_ID));
		formParam = formParams;
		returnMap = returnMaps;
	}

	public FormData() {
		formParam = new ArrayList(); 
	}

	public void clear() {
		formParam.clear();
	}

	public int size() {
		return formParam.size();
	}
	
}
