package com.zchk.yunzichan.entity.model.upload;

import com.zchk.yunzichan.entity.model.RequestAndResponse;
import com.zchk.yunzichan.entity.model.check.CheckTemplateMessageItem;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderProperty;

/**
 * �����ϴ��Ľṹ
 * @author SenseTech
 *
 */
public class APPDataUploadMessage {
	public String sum;
	public String userName;
	public RequestAndResponse resp;
	public CheckTemplateMessageItem[] checkItems;
	public MaintenanceOrderProperty[] orderItems;
}
