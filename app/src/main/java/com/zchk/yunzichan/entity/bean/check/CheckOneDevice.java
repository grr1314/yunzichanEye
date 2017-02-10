package com.zchk.yunzichan.entity.bean.check;

public class CheckOneDevice {

	private String deviceName;
	private String deviceType;
	private String deviceManufacturer;
	private String checkTime;
	private String deviceNum;
	
	
	
	public CheckOneDevice() {
		super();
	}
	public CheckOneDevice(String deviceName, String deviceType,
			String deviceManufacturer, String checkTime, String deviceNum) {
		super();
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.deviceManufacturer = deviceManufacturer;
		this.checkTime = checkTime;
		this.deviceNum = deviceNum;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	
	
}
