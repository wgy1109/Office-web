/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chanzor.office.common.service.TreeService;
import com.chanzor.office.modules.sys.dao.OfficeDao;
import com.chanzor.office.modules.sys.entity.Office;
import com.chanzor.office.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	@Autowired
	private OfficeDao officeDao;

	public List<Office> findAll() {
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll) {
		if (isAll != null && isAll) {
			return UserUtils.getOfficeAllList();
		} else {
			return UserUtils.getOfficeList();
		}
	}

	@Transactional(readOnly = true)
	public List<Office> findList(Office office) {
		if (office != null) {
			office.setParentIds(office.getParentIds() + "%");
			return dao.findByParentIdsLike(office);
		}
		return new ArrayList<Office>();
	}

	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	public List<Office> findOfficeParentList(Map<String, Object> map) {
		return officeDao.findOfficeParentList(map);

	}

	public Office findOfficeById(String officeId) {
		return officeDao.findOfficeById(officeId);
	}

	public List<Office> findSaleOffice(Office office) {
		return officeDao.findSaleOffice(office);
	}

}
