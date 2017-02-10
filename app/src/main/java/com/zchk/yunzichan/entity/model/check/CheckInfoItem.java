package com.zchk.yunzichan.entity.model.check;

/**
 * Created by admin on 2016/11/19.
 */
public class CheckInfoItem {
    public String qrCode;//（String）二维码编号
    public String nameCn;//（String）资产名称
    public String typeCn;//（String）资产类型
    public String companyCn;//（String）服务商
    public String checkTime;//（String）维保时间
    public String checkUser;//（String）维保人
    public CheckContentItem[] checkNote;//（CheckContentItem[]）维保内容

}
