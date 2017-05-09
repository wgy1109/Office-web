/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.cms.dao;

import com.chanzor.office.common.persistence.CrudDao;
import com.chanzor.office.common.persistence.annotation.MyBatisDao;
import com.chanzor.office.modules.cms.entity.Site;

/**
 * 站点DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface SiteDao extends CrudDao<Site> {

}
