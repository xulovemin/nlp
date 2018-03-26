package com.jc.core.persistence.entity;

import java.io.Serializable;

/**
* @Description: Entity支持类
* @author 高研 
* @date 2015年9月10日 下午5:21:39
 */
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 显示
	 */
	public static final String SHOW = "1";
	/**
	 * 隐藏
	 */
	public static final String HIDE = "0";
	
	/**
	 * 是
	 */
	public static final String YES = "1";
	/**
	 * 否
	 */
	public static final String NO = "0";

	/**
	 * 删除标记
	 */
	public static String DEL_FLAG = "delFlag";
	/**
	 * 1:可用
	 */
	public static final int DEL_FLAG_NORMAL = 0;
	/**
	 * 0:不可用
	 */
	public static final int DEL_FLAG_DELETE = 1;
	/**
	 * 超管用户名
	 */
	public static final String ADMIN_USER_NAME = "admin";
	
	/**
	 * 字典根级CODE名称
	 */
	public static final String CODE_ROOT_NAME = "system";
	
}
