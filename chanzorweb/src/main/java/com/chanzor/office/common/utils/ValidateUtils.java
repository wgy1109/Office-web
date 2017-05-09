package com.chanzor.office.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	 //短信 单价正则验证
		public static boolean checkSmsPrice(String smsPrice) {
			boolean flag = false;
			try {
				Pattern regex = Pattern
						.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{1,4})?$");
				Matcher matcher = regex.matcher(smsPrice);
				flag = matcher.matches();
			} catch (Exception e) {
				flag = false;
			}
			return flag;
		}
		
		//金额正则验证
		public static boolean checkSmsMoney(String smsMoney) {
			boolean flag = false;
			try {
				Pattern regex = Pattern
						.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$");
				Matcher matcher = regex.matcher(smsMoney);
				flag = matcher.matches();
			} catch (Exception e) {
				flag = false;
			}
			return flag;
		}
	   
		//正整数验证
		public static boolean checkPositive(String positiveInteger) {
			boolean flag = false;
			try {
				Pattern regex = Pattern
						.compile("^[1-9]\\d*$");
				Matcher matcher = regex.matcher(positiveInteger);
				flag = matcher.matches();
			} catch (Exception e) {
				flag = false;
			}
			return flag;
		}
}
