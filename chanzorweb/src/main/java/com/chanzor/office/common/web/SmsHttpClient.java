package com.chanzor.office.common.web;

import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chanzor.office.common.mapper.JsonMapper;
import com.chanzor.office.modules.sys.entity.Office;
import com.chanzor.office.modules.sys.entity.User;
import com.chanzor.office.modules.sys.utils.UserUtils;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class SmsHttpClient {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Value("${chanzor.sms.http.url:1}")
	private String smsHttpUrl;

	@SuppressWarnings("unchecked")
	public <T, K> K post(List<BasicNameValuePair> formParams, String url, HttpResponseListener<T, K> listener,
			TypeReference<T> typeReference) {
		try {
			User user = UserUtils.getUser();

			formParams.add(new BasicNameValuePair("user_id", user.getId()));
			formParams.add(new BasicNameValuePair("userId", user.getId()));

			// formParams.add(new BasicNameValuePair("bussinesstype", "2"));
			// bussinesstype 为2 表示 销售经理 (普通销售人员) 为 1表示支撑经理 为null 表示非销售经理或支撑经理
			// user.getOffice().getType().equals("3") 表示组织机构类型为 小组（即各个销售队)
			Office office = user.getOffice();
			if ((office != null) && (office.getName() != null) && (user.getOffice().getType().equals("3"))) {
				formParams.add(new BasicNameValuePair("bussinesstype", "2"));
			}
			formParams.add(new BasicNameValuePair("chargeName", user.getName()));
			formParams.add(new BasicNameValuePair("userName", user.getLoginName()));
			formParams.add(new BasicNameValuePair("username", user.getLoginName()));
			formParams.add(new BasicNameValuePair("lastlogin_ip", user.getLoginIp()));

			String httpUrl = String.format("%s/%s", smsHttpUrl, url);
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
			headers.setContentType(type);
			List<String> params = formParams.stream().map(pair -> {
				try {
					return String.format("%s=%s", pair.getName(), URLEncoder.encode(pair.getValue(), "utf-8"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());

			String requestParams = StringUtils.join(params, "&");
			logger.info("发送请求：{} {}", httpUrl, requestParams);
			ResponseEntity<String> result = restTemplate.exchange(httpUrl, HttpMethod.POST,
					new HttpEntity<>(requestParams, headers), String.class);

			if (result.getStatusCode() != HttpStatus.OK) {
				logger.info("发送返回 : {} ", result);
				return listener.onFailed(result.getStatusCode().value());
			} else {
				String returnResult = result.getBody();
				logger.info("发送返回 : {} ", returnResult.length() > 60
						? String.format("%s...", returnResult.substring(0, 60)) : returnResult);
				if (typeReference.getType().getTypeName().equals(String.class.getName())) {
					return listener.onSuccess((T) result.getBody());
				} else {
					return listener.onSuccess(JsonMapper.getInstance().readValue(result.getBody(), typeReference));
				}
			}
		} catch (Exception e) {
			logger.info("请求admin模块error,url:{},formParams:{},原因:{}",url,formParams,e);
			return listener.onFailed(500);
		}
	}

	public static interface HttpResponseListener<T, K> {
		public K onSuccess(T t);

		public K onFailed(int httpCode);
	}
}
