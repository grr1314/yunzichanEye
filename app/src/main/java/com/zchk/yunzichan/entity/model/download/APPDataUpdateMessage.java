package com.zchk.yunzichan.entity.model.download;


import com.zchk.yunzichan.entity.model.RequestAndResponse;
import com.zchk.yunzichan.entity.model.check.APPTempAndDevRelationItem;

public class APPDataUpdateMessage {

	public RequestAndResponse resp;
	public AppAccountItem[] account;
	public APPAssetItem[] assets;
	public APPTempleteItem[] templete;
	public APPAssetTypeItem[] assetsType;
	public APPTempAndDevRelationItem[] relation;
	public String accountName;

}
