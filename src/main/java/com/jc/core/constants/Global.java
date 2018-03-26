package com.jc.core.constants;

/**
* @Description: 全局配置类
* @author 高研 
* @date 2015年9月11日 上午9:22:44
 */
public class Global {
	/**
	 * 时间格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 返回成功
	 */
	public static final String RESULT_STATE = "resultState";
	/**
	 * 返回成功信息
	 */
	public static final String RESULT_MESSAGE = "resultMessage";
	/**
	 * 后台验证表单错误
	 */
	public static final String RESULT_FORMERRORMESSAGE = "formErrorMessage";
	/**
	 * 超级管理员登录名称
	 */
	public static final String ADMIN_NAME = "admin";
	/**
	 * 系统根文件目录
	 */
	public static final String basePath = Global.class.getClassLoader().getResource("").toString().substring(6);
	
}
