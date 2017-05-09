package com.chanzor.office.modules.sys.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.chanzor.office.modules.sys.service.SystemService;

public class SystemHashedCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Object accountCredentials = getCredentials(info);
		String pwd = String.valueOf(token.getPassword());
		if (pwd.length() >= 32) {
			return equals(pwd, accountCredentials);
		}
		// 将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
		return SystemService.validatePassword(String.valueOf(token.getPassword()), accountCredentials.toString());
	}
}
