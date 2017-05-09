/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.sys.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.chanzor.office.common.persistence.DataEntity;

/**
 * 操作日志Entity
 * @author zhangyq
 * @version 2016-10-21
 */
public class OperateLog extends DataEntity<OperateLog> {
	
	private static final long serialVersionUID = 1L;
	private Integer logType;		// 10：企业审核通过 15：企业审核驳回 20：应用审核通过 25：应用审核驳回30：企业账户充值 35：开票审核
									//40：应用充值 45：应用配置修改50：通道启用 55：通道禁用 60：通道编辑 65 ：通道删除 
									//70：通道组启用 75：通道组禁用 80：通道组编辑 85：通道组删除 90：短信审核通过
									//95：	短信审核驳回 100：语音审核通过 105：语音审核驳回  110：模板审核通过
									//115：模板审核驳回 120：系统模板新增 125：系统模板修改 130：系统模板删除'
	private String logContent;		// 日志内容
	private String operatorIp;		// 操作者IP
	private String userChineseName;	// 操作者中文名
	
	
	//以下为查询条件
	private String user_chinese_name;  //操作者
	private Integer log_type;          //日志类型
	private Date create_date_begin;     //创建时间查询开始
	private Date create_date_end;       //创建时间查询开始
	
	public String getUser_chinese_name() {
		return user_chinese_name;
	}

	public void setUser_chinese_name(String user_chinese_name) {
		this.user_chinese_name = user_chinese_name;
	}

	public Integer getLog_type() {
		return log_type;
	}

	public void setLog_type(Integer log_type) {
		this.log_type = log_type;
	}

	public Date getCreate_date_begin() {
		return create_date_begin;
	}

	public void setCreate_date_begin(Date create_date_begin) {
		this.create_date_begin = create_date_begin;
	}

	public Date getCreate_date_end() {
		return create_date_end;
	}

	public void setCreate_date_end(Date create_date_end) {
		this.create_date_end = create_date_end;
	}

	public OperateLog() {
		super();
	}

	public OperateLog(String id){
		super(id);
	}

	@NotNull(message="日志类型不可为空")
	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	
	@Length(min=1, max=2048, message="日志内容长度必须介于 1 和 2048 之间")
	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	
	@Length(min=1, max=128, message="操作者IP长度必须介于 1 和 128 之间")
	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	
	@Length(min=1, max=64, message="操作者中文名长度必须介于 1 和 64 之间")
	public String getUserChineseName() {
		return userChineseName;
	}

	public void setUserChineseName(String userChineseName) {
		this.userChineseName = userChineseName;
	}
	
}