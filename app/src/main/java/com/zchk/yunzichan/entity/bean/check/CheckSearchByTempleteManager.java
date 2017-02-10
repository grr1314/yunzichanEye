package com.zchk.yunzichan.entity.bean.check;

public class CheckSearchByTempleteManager {
	
	public String deviceID;
	public String time;
	public String checkUser;
	public CheckSearchByTempleteManager(String deviceID, String time, String checkUser) {
		super();
		this.deviceID = deviceID;
		this.time = time;
		this.checkUser = checkUser;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

}
