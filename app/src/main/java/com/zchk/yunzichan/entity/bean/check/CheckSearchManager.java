package com.zchk.yunzichan.entity.bean.check;

public class CheckSearchManager {
	
	private String deviceID;
	private String time;
	private String state;
	private String deviceName;
	public CheckSearchManager()
	{
		super();
	}
	public CheckSearchManager(String deviceID, String time, String state) {
		super();
		this.deviceID = deviceID;
		this.time = time;
		this.state = state;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
}
