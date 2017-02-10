package com.zchk.yunzichan.entity.bean.check;

import com.zchk.yunzichan.entity.model.check.CheckRecordDetailsItem;

import java.io.Serializable;

public class CheckMaintenanceSearchOneManager implements Serializable{
	
	public String deviceID;
	public String time;
	public String state;
	public String account;
	private CheckRecordDetailsItem check;
	
	
	public CheckMaintenanceSearchOneManager() {
		super();
	}

	public CheckMaintenanceSearchOneManager(String deviceID, String time, String state, String account, CheckRecordDetailsItem check) {
		this.deviceID = deviceID;
		this.time = time;
		this.state = state;
		this.account = account;
		this.check = check;
	}

	public CheckRecordDetailsItem getCheck() {
		return check;
	}

	public void setCheck(CheckRecordDetailsItem check) {
		this.check = check;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

}
