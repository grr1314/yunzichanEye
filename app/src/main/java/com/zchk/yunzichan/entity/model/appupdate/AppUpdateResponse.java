package com.zchk.yunzichan.entity.model.appupdate;



/**
 * Created by admin on 2016/12/21.
 */
public class AppUpdateResponse {
//    返回结构：
//    resp（RequestAndResponse）
    //    requestCommand（String）
    //    responseCommand（String）
    //    failReason（String）
//    version（int）版本号
//    note（String）版本描述
//    apkUrl（String）下载地址
    public RequestAndResponse resp;
    public String version;
    public String note;
    public  String apkUrl;
}
