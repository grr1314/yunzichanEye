package com.zchk.yunzichan.entity.bean.check;
/**
 * ���-����
 * @author SenseTech
 *
 */
public class Checks_Weak {

	private String LabelId;
	private String Monitoring;
	private String Screen;
	private String WeakCurrentShaft;
	private String AirConditioner;
	private String Other;
	private String CheckUser;
	private String RecordTime;
	private String Note;
	private String isupload;
	
	
	public Checks_Weak() {
		super();
	}
	
	
	public Checks_Weak(String labelId, String monitoring, String screen,
			String weakCurrentShaft, String airConditioner, String other,
			String checkUser, String recordTime, String note, String isupload) {
		super();
		LabelId = labelId;
		Monitoring = monitoring;
		Screen = screen;
		WeakCurrentShaft = weakCurrentShaft;
		AirConditioner = airConditioner;
		Other = other;
		CheckUser = checkUser;
		RecordTime = recordTime;
		Note = note;
		this.isupload = isupload;
	}


	
	public String getIsupload() {
		return isupload;
	}


	public void setIsupload(String isupload) {
		this.isupload = isupload;
	}


	public String getLabelId() {
		return LabelId;
	}
	public void setLabelId(String labelId) {
		LabelId = labelId;
	}
	public String getMonitoring() {
		return Monitoring;
	}
	public void setMonitoring(String monitoring) {
		Monitoring = monitoring;
	}
	public String getScreen() {
		return Screen;
	}
	public void setScreen(String screen) {
		Screen = screen;
	}
	public String getWeakCurrentShaft() {
		return WeakCurrentShaft;
	}
	public void setWeakCurrentShaft(String weakCurrentShaft) {
		WeakCurrentShaft = weakCurrentShaft;
	}
	public String getAirConditioner() {
		return AirConditioner;
	}
	public void setAirConditioner(String airConditioner) {
		AirConditioner = airConditioner;
	}
	public String getOther() {
		return Other;
	}
	public void setOther(String other) {
		Other = other;
	}
	public String getCheckUser() {
		return CheckUser;
	}
	public void setCheckUser(String checkUser) {
		CheckUser = checkUser;
	}
	public String getRecordTime() {
		return RecordTime;
	}
	public void setRecordTime(String recordTime) {
		RecordTime = recordTime;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	
	
}
	
