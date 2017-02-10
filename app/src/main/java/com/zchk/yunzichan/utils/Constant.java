package com.zchk.yunzichan.utils;

/**
 * 存放程序中所有使用到的常量例如 IP等
 *
 * @author SenseTech
 *
 */
public class Constant {

	/**
	 * IP
	 */
	public static final String IP = "http://192.168.1.204:8080/DevOpsNoSpring/servlet/DevOpsService?cmd=";
	public static final String UploadCheck = IP + "CheckDataUploadByAppCmd";// 点检信息数据上传的接口
//	public static String url = GlobalDefine.CmdPath.cmdPath
//			+ "GetCheckTemplateCmd";
	/**
	 * NetWork
	 */
	public static final int NETWORKONLINE = 0;// 处于在线状态
	public static final int NETWORKOFF = 1;// 处于离线状态

	/**
	 * 数据状态
	 */
	public static final int HASUPLOAD = 0;// 表示已经上传
	public static final int HASNOTUPLOAD = 1;// 表示还没有上传


	public final static int INDICATOR_HEIGHT=25;
	public final static int BOTTOMBAR_HEIGHT=60;

	public final static int realTimeDataActivity=01;
	public final static int CheckAndMaintenanceQueryActivity=02;
}
