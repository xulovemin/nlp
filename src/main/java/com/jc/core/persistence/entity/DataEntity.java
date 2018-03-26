package com.jc.core.persistence.entity;

import java.beans.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
* @Description: 数据Entity基类
* @author 高研 
* @date 2015年9月10日 下午5:21:52
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	/** 是否可用:1:可用，0:不可用 */
	private Integer deleteFlag = 1;
	
	private Date updateTimeNew;
	
	private Date updateTime;
	
	private Long updateUser;
	
	private Long createUser;
	
	private Date createTime;
	
	private Long createUserDept;

	private String ids;
	
	private String logKey;
	
	/**
	 * 自定义SQL（SQL标识，SQL内容）
	 */
	protected Map<String, String> sqlMap;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Transient
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public boolean isSave() {
		if(getId() == null || getId() == 0){
			return true;
		}
		return false;
    }

	@Transient
	public Date getUpdateTimeNew() {
		return updateTimeNew;
	}

	public void setUpdateTimeNew(Date updateTimeNew) {
		this.updateTimeNew = updateTimeNew;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUserDept() {
		return createUserDept;
	}

	public void setCreateUserDept(Long createUserDept) {
		this.createUserDept = createUserDept;
	}
	
	@JsonIgnore
	public Map<String, String> getSqlMap() {
		if (sqlMap == null){
			sqlMap = new HashMap<String, String>();
		}
		return sqlMap;
	}

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Transient
	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}
	
}
