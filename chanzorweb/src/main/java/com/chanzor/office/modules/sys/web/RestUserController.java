package com.chanzor.office.modules.sys.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chanzor.office.modules.sys.entity.User;
import com.chanzor.office.modules.sys.service.SystemService;
import com.chanzor.office.modules.sys.utils.ResponseEntityFactory;

@Controller
@RequestMapping(value = "/api/v1/user")
public class RestUserController {
	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "{identify}", method = RequestMethod.GET)
	public ResponseEntity<User> addClientUser(@PathVariable String identify, HttpServletResponse response) {
		User user = systemService.getUser(identify);
		user = user == null ? new User() : user;
		return ResponseEntityFactory.createResponseEntity(user);
	}

}
