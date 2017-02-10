package com.zchk.yunzichan.entity.model.checkroute;

/**
 * Created by admin on 2016/11/3.
 */
public class TaskRouteSuspendActionQueryMessage {

    public String startTime;//（String）暂停开始时间
    public String endTime;//（Stirng）暂停结束时间
    public int routeId;//（int）路线Id
    public String season;//（String）暂停或终止原因
    public int stopType;//（int）操作类型（0表示暂停，1表示终止， 2表示取消暂停）
    public String userAccountName;//（String）当前登录人

}
