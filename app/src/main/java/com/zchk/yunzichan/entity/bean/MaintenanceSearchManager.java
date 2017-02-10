package com.zchk.yunzichan.entity.bean;

public class MaintenanceSearchManager {
	
	public String deviceID;
	public String time;
	public String note;
	public MaintenanceSearchManager() {
		super();
	}
	public MaintenanceSearchManager(String deviceID, String time, String note) {
		super();
		this.deviceID = deviceID;
		this.time = time;
		this.note = note;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
