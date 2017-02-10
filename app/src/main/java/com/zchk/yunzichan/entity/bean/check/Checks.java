package com.zchk.yunzichan.entity.bean.check;

import java.util.List;
import java.util.Map;

/**
 * ���е��ģ��
 * @author SenseTech
 *
 */
public class Checks {

	public List<Checks_Elevator>Check_Elevator;
	public List<Checks_Fire>Check_Fire;
	public List<Checks_Elec>Check_Elec;
	public List<Checks_Weak>Check_Weak;
	
	
	public Checks() {
		super();
	}
	
	public Checks(List<Checks_Elevator> check_Elevator,
			List<Checks_Fire> check_Fire, List<Checks_Elec> check_Elec,
			List<Checks_Weak> check_Weak) {
		super();
		Check_Elevator = check_Elevator;
		Check_Fire = check_Fire;
		Check_Elec = check_Elec;
		Check_Weak = check_Weak;
	}
	public List<Checks_Elevator> getCheck_Elevator() {
		return Check_Elevator;
	}
	public void setCheck_Elevator(List<Checks_Elevator> check_Elevator) {
		Check_Elevator = check_Elevator;
	}
	public List<Checks_Fire> getCheck_Fire() {
		return Check_Fire;
	}
	public void setCheck_Fire(List<Checks_Fire> check_Fire) {
		Check_Fire = check_Fire;
	}
	public List<Checks_Elec> getCheck_Elec() {
		return Check_Elec;
	}
	public void setCheck_Elec(List<Checks_Elec> check_Elec) {
		Check_Elec = check_Elec;
	}
	public List<Checks_Weak> getCheck_Weak() {
		return Check_Weak;
	}
	public void setCheck_Weak(List<Checks_Weak> check_Weak) {
		Check_Weak = check_Weak;
	}
	
	
	
}
