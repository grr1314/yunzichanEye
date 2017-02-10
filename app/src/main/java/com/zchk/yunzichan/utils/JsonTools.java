package com.zchk.yunzichan.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zchk.yunzichan.entity.model.RequestAndResponse;
import com.zchk.yunzichan.entity.model.appupdate.AppUpdateRequest;
import com.zchk.yunzichan.entity.model.check.APPCheckInfoMessage;
import com.zchk.yunzichan.entity.model.check.CheckByDeviceBasicInfoMessage;
import com.zchk.yunzichan.entity.model.check.CheckDetailCmd;
import com.zchk.yunzichan.entity.model.check.CheckLastInfoByUserMessage;
import com.zchk.yunzichan.entity.model.check.CheckRecordDetailsItem;
import com.zchk.yunzichan.entity.model.check.CheckTemplateMessage;
import com.zchk.yunzichan.entity.model.check.LeaderInspectionRequest;
import com.zchk.yunzichan.entity.model.check.SiteInspectionMessage;
import com.zchk.yunzichan.entity.model.check.selectCheck;
import com.zchk.yunzichan.entity.model.checkroute.CheckRouteSearchMessage;
import com.zchk.yunzichan.entity.model.checkroute.DeviceListInfoQueryMessage;
import com.zchk.yunzichan.entity.model.checkroute.PersonalTaskRouteQueryMessage;
import com.zchk.yunzichan.entity.model.checkroute.TaskRouteActionQueryMessage;
import com.zchk.yunzichan.entity.model.checkroute.TaskRouteSuspendActionQueryMessage;
import com.zchk.yunzichan.entity.model.download.APPDataUpdateMessage;
import com.zchk.yunzichan.entity.model.leader.leaderCheckRequest;
import com.zchk.yunzichan.entity.model.leader.leadercheck;
import com.zchk.yunzichan.entity.model.login.WeixinGetNavbarPathMessage;
import com.zchk.yunzichan.entity.model.maintenance.MaintainAddMaintainInfosMessage;
import com.zchk.yunzichan.entity.model.maintenance.MaintainAdvancedQueryMessage;
import com.zchk.yunzichan.entity.model.maintenance.MaintainlastMaintainInfosQueryMessage;
import com.zchk.yunzichan.entity.model.maintenance.MaintainrepairInfosQueryMessage;
import com.zchk.yunzichan.entity.model.modifypassword.ModifyPWRequest;
import com.zchk.yunzichan.entity.model.query.AdminCheckDeviceInfoMessage;
import com.zchk.yunzichan.entity.model.query.AdminMaintainDeviceInfoMessage;
import com.zchk.yunzichan.entity.model.realtime.RealTimeDataMessage;
import com.zchk.yunzichan.entity.model.repair.ApplyMaintenancePropertyMessage;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderProperty;
import com.zchk.yunzichan.entity.model.update.UpdateByVersionRequest;
import com.zchk.yunzichan.entity.model.upload.APPDataUploadMessage;


/**
 * 有关Json解析和封装的所有方法
 *
 * @author SenseTech
 */
public class JsonTools {
    private static final String TAG = "JsonTools";

    public static String StringToJson_CheckDetail(CheckDetailCmd checkTemp) {
        return StrutsToJson(checkTemp);
    }
    /**
     * 所属功能：点检 将CheckTemplateMessage结构转为为JSON
     *
     * @param checkTemp
     * @return
     */
    public static String StringToJson_Check(APPCheckInfoMessage checkTemp) {
        return StrutsToJson(checkTemp);
    }

    public static String StringToJson_CheckUP(CheckTemplateMessage checkTemp) {
        return StrutsToJson(checkTemp);
    }

    public static String StringToJson_leaderCharts(leaderCheckRequest checkTemp) {
        return StrutsToJson(checkTemp);
    }
    /**
     * 所属功能：点检 将lastCheckInfosQueryMessage结构转化为JSON
     *
     * @param last
     * @return
     */
    public static String StringToJson_lastCheck(
            CheckByDeviceBasicInfoMessage last) {
        return StrutsToJson(last);
    }

    public static String StringToJson_spotCheck(SiteInspectionMessage last) {
        return StrutsToJson(last);
    }

    public static String StringToJson_diector(RequestAndResponse mess) {
        return StrutsToJson(mess);
    }

    /**
     * 所属功能：报修单 作用：将添加报修工单信息的条件打包成Json的方法
     *
     * @param request
     * @return
     */
    public static String StringToJson_Back(MaintenanceOrderProperty request) {
        return StrutsToJson(request);
    }

    /**
     * 所属功能：报修单 作用：将报修工单的查询条件信息打包成Json
     *
     * @param apply
     * @return
     */
    public static String StringToJson_Repair(
            ApplyMaintenancePropertyMessage apply) {
        return StrutsToJson(apply);
    }

    /**
     * 所属功能：维修
     *
     * @param maintain
     * @return
     */
    public static String StringToJson_Maintenance_Select(
            MaintainrepairInfosQueryMessage maintain) {
        return StrutsToJson(maintain);
    }

    public static String StringToJson_Maintenance_ASelect(
            MaintainAdvancedQueryMessage mess) {
        return StrutsToJson(mess);
    }

    /**
     * 所属功能：维修
     *
     * @param mess
     * @return
     */
    public static String StringToJson_Maintenance_last(
            MaintainlastMaintainInfosQueryMessage mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_Maintenance_Insert(
            MaintainAddMaintainInfosMessage mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_UpLoad(APPDataUploadMessage message) {
        return StrutsToJson(message);
    }

    /**
     * 所属功能：点检记录查询
     *
     * @param mess
     * @return
     */
    public static String StringToJson_CheckSelect(
            CheckLastInfoByUserMessage mess) {
        return StrutsToJson(mess);
    }
    /**
     * 所属功能：点检记录查询
     *
     * @param mess
     * @return
     */
    public static String StringToJson_CheckSelect1(
            selectCheck mess) {
        return StrutsToJson(mess);
    }


    /**
     * @param mess
     * @return
     */
    public static String StringToJson_CheckLast(CheckLastInfoByUserMessage mess) {
        return StrutsToJson(mess);
    }

    /**
     * 所属功能：数据更新
     *
     * @param mess
     * @return
     */
    public static String StringToJson_Update(APPDataUpdateMessage mess) {
        return StrutsToJson(mess);
    }

    /**
     * 所属功能：实时数据
     *
     * @param mess
     * @return
     */
    public static String StringToJson_RealTime(RealTimeDataMessage mess) {
        return StrutsToJson(mess);
    }

    /**
     * 所属功能：领导督查
     *
     * @param mess
     * @return
     */
    public static String StringToJson_AdminCheck(
            LeaderInspectionRequest mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_AdminMaintenance(
            AdminMaintainDeviceInfoMessage mess) {
        return StrutsToJson(mess);
    }
    /**
     * 所属功能：点检路线
     *
     * @param mess
     * @return
     */
    public static String StringToJson_CheckRoute(PersonalTaskRouteQueryMessage mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_CheckSingleRoute(DeviceListInfoQueryMessage mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_CheckStart(TaskRouteActionQueryMessage mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_CheckRoutePause(TaskRouteSuspendActionQueryMessage mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_CheckEnd(TaskRouteSuspendActionQueryMessage mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_realTime(RealTimeDataMessage obj) {
        return StrutsToJson(obj);
    }

    public static String StringToJson_Login(WeixinGetNavbarPathMessage mess) {
        return StrutsToJson(mess);
    }

    public static String StringToJson_leaderCheck(CheckRecordDetailsItem mess)
    {
        return StrutsToJson(mess);
    }
    //更新数据库
    public static String StringToJson_Update(UpdateByVersionRequest mess)
    {
        return StrutsToJson(mess);}
    //App版本更新
    public static String StringToJson_AppUpdate(AppUpdateRequest mess)
    {
        return StrutsToJson(mess);}
    //修改用户密码
    public static String StringToJson_ModifyPassWord(ModifyPWRequest mess) {
        return StrutsToJson(mess);
    }
    /**
     * 将结构转化为JSONString
     *
     * @param obj
     * @return
     */
    private static String StrutsToJson(Object obj) {
        String jsonString = JSON.toJSONString(obj);

        int lastIndexOf = jsonString.indexOf("null");
        if (lastIndexOf != -1) {
            jsonString = jsonString.substring(0, lastIndexOf)
                    + jsonString
                    .substring(lastIndexOf + 4, jsonString.length());
        }
        Log.e(TAG, "集合对象生成:" + jsonString);
        return jsonString;
    }

    /**
     * 将Json转化为Struts
     *
     * @param jsonString
     * @param cls
     * @return
     */
    @SuppressWarnings("unused")
    public static Object JsonToStruts(String jsonString,
                                      @SuppressWarnings("rawtypes") Class cls) {
        @SuppressWarnings("unchecked")
        Object object = JSON.parseObject(jsonString, cls);
        return object;
    }


}
