/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.chanzor.office.modules.chanzor.entity;

import org.hibernate.validator.constraints.Length;

import com.chanzor.office.common.persistence.DataEntity;

public class CzDocument extends DataEntity<CzDocument> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String description;		// 描述
	private String createTime;		// 创建时间
	private String updateTime;		// 更新时间
	private String status;		// 数据状态
	private String content;		//新闻文章
	private String image;		//图片

	public CzDocument() {
		super();
	}

	public CzDocument(String id){
		super(id);
	}

	@Length(min=1, max=80, message="标题长度必须介于 1 和 80 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=1, max=140, message="描述长度必须介于 1 和 140 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}