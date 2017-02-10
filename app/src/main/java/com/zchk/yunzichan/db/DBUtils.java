package com.zchk.yunzichan.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.bean.Device;
import com.zchk.yunzichan.entity.bean.MaintenanceSearchManager;
import com.zchk.yunzichan.entity.bean.UserInfo;
import com.zchk.yunzichan.entity.bean.check.Checks;
import com.zchk.yunzichan.entity.bean.check.Checks_Elec;
import com.zchk.yunzichan.entity.bean.check.Checks_Elevator;
import com.zchk.yunzichan.entity.bean.check.Checks_Fire;
import com.zchk.yunzichan.entity.bean.check.Checks_Weak;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderProperty;
import com.zchk.yunzichan.utils.DateUtils;
import com.zchk.yunzichan.utils.JsonTools;

/**
 * 操作数据库等方法集合 考虑将这个类写为单例类
 *
 * @author SenseTech
 */
public class DBUtils {

    private static final String TAG = "DBUtils";
    MyApplication application;
    SQLiteDatabase db;

    public DBUtils(MyApplication application) {
        super();
        this.application = application;
        db = application.getHelper().getWritableDatabase();

    }

    /**
     * 清空所有数据库表的数据 到月底清空所有数据
     */
    public void clearAllTableData() throws Exception {
        // 清空用户表
        db.execSQL("DELETE FROM assetUserInfo");
        db.execSQL("DELETE FROM MaintenanceOrder_OperatorRecord");
        db.execSQL("DELETE FROM ASSET");
//        db.execSQL("DELETE FROM CheckTemplete");
        db.execSQL("DELETE FROM tempanddev");
        db.execSQL("DELETE FROM assetType");
    }

    /**
     * 查询用户表
     */
    public void select() throws Exception {
        Cursor cursor = db.query("assetUserInfo", null, null, null, null, null,
                null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor
                        .getString(cursor.getColumnIndex("account"));
                Log.e("NAME", name + "");
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * 插入新的用户
     */
    public void insertUser(List<UserInfo> ls) throws Exception {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < ls.size(); i++) {
            UserInfo user = ls.get(i);
            cv.put("account", user.getAccount());
            cv.put("password", user.getPassword());
            cv.put("companyId", user.getCompanyId());
            cv.put("company", user.getCompany());
            cv.put("userName", user.getUserName());
            cv.put("roleId", user.getRoleId());
            cv.put("roleName", user.getRoleName());
            db.insert("assetUserInfo", null, cv);
            cv.clear();
        }
    }

    /**
     * 插入服务器更新的设备数据
     *
     * @param ls
     * @throws Exception
     */
    public void insertDevice(List<Device> ls) throws Exception {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < ls.size(); i++) {
            Device device = ls.get(i);
            cv.put("nameCn", device.getNameCn());
            cv.put("typeId", device.getTypeId());
            cv.put("typeName", device.getTypeName());
            cv.put("lableCode", device.getLableCode());
            cv.put("companyId", device.getCompanyId());
            cv.put("company", device.getCompany());
            db.insert("Asset", null, cv);
            cv.clear();
        }
    }

    public List<Map<String, Object>> selectDevice() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        Cursor cursor = db.rawQuery("select * from Asset", null);
        if (cursor.moveToFirst()) {
            do {
                String lable = cursor.getString(cursor
                        .getColumnIndex("lableCode"));
                String nameCn = cursor.getString(cursor
                        .getColumnIndex("nameCn"));
                String company = cursor.getString(cursor
                        .getColumnIndex("company"));
                String typeName = cursor.getString(cursor
                        .getColumnIndex("typeName"));
                item = new HashMap<String, Object>();
                item.put("lableCode", lable);
                item.put("nameCn", nameCn);
                item.put("company", company);
                item.put("typeName", typeName);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 插入资产与模板的关系表
     *
     * @param ls
     */
    public void inertTempAndDev(List<Map<String, Object>> ls) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < ls.size(); i++) {
            cv.put("typeCode", ls.get(i).get("typeCode").toString());
            cv.put("templeteCode", ls.get(i).get("templeteCode").toString());
            db.insert("tempanddev", null, cv);
            cv.clear();
        }
    }

    public String selectTempAndDev(String typeCode) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        Log.e(TAG, typeCode);
        Cursor cursor = db.rawQuery("select * from tempanddev where typeCode='"
                + typeCode + "'", null);
        String templeteCode = null;
        if (cursor.moveToFirst()) {
            do {
                templeteCode = cursor.getString(cursor
                        .getColumnIndex("templeteCode"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return templeteCode;
    }

    /**
     * 插入设备与资产的关系
     *
     * @param ls
     */
    public void insertAssetType(List<Map<String, Object>> ls) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < ls.size(); i++) {
            cv.put("typeCode", ls.get(i).get("typeCode").toString());
            cv.put("classifyNameCn", ls.get(i).get("classifyNameCn").toString());
            db.insert("assetType", null, cv);
            cv.clear();
        }
    }

    public List<Map<String, Object>> selectAssetType() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        Cursor cursor = db.rawQuery(
                "select * from assetType", null);
        String typeCode = null;
        String name;
        if (cursor.moveToFirst()) {
            do {
                typeCode = cursor.getString(cursor.getColumnIndex("typeCode"));
                name = cursor.getString(cursor.getColumnIndex("classifyNameCn"));
                item = new HashMap<>();
                item.put("typeCode", typeCode);
                item.put("name", name);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public String selectAssetType(String classifyNameCn) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        Log.e(TAG, classifyNameCn);
        Cursor cursor = db.rawQuery(
                "select * from assetType where classifyNameCn='"
                        + classifyNameCn + "'", null);
        String typeCode = null;
        if (cursor.moveToFirst()) {
            do {
                typeCode = cursor.getString(cursor.getColumnIndex("typeCode"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return typeCode;
    }

    public List<Map<String, Object>> selectDeviceById(String lableCode) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        Log.e(TAG, lableCode);
        Cursor cursor = db.rawQuery("select * from Asset where lableCode='"
                + lableCode + "'", null);
        if (cursor.moveToFirst()) {
            do {
                String lable = cursor.getString(cursor
                        .getColumnIndex("lableCode"));
                String nameCn = cursor.getString(cursor
                        .getColumnIndex("nameCn"));
                String company = cursor.getString(cursor
                        .getColumnIndex("company"));
                String typeName = cursor.getString(cursor
                        .getColumnIndex("typeName"));
                item = new HashMap<>();
                item.put("lableCode", lable);
                item.put("nameCn", nameCn);
                item.put("company", company);
                item.put("typeName", typeName);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 插入维保记录
     *
     * @param list
     */
    public void insertMaintain(List<Map<String, Object>> list) {

        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("checkUser", list.get(i).get("checkUser").toString());
            values.put("assetCode", list.get(i).get("assetCode").toString());
            values.put("checkTime", "2016-01-24");
            values.put("status", 1);
            values.put("checkValue", 1);
            values.put("note", list.get(i).get("note").toString());
            values.put("isupload", 0);
            db.insert("equipmentMaintainRecord", null, values);
            values.clear();
        }
    }

    public List<MaintenanceSearchManager> getMaintain() {

        List<MaintenanceSearchManager> list = new ArrayList<MaintenanceSearchManager>();
        MaintenanceSearchManager item = null;
        Cursor cursor = db.rawQuery("select * from equipmentMaintainRecord",
                null);
        if (cursor.moveToFirst()) {
            do {
                item = new MaintenanceSearchManager();
                String assetCode = cursor.getString(cursor
                        .getColumnIndex("assetCode"));
                String note = cursor.getString(cursor.getColumnIndex("note"));
                String time = cursor.getString(cursor
                        .getColumnIndex("checkTime"));
                item.setDeviceID(assetCode);
                item.setNote(note);
                item.setTime(time);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 插入报修单
     *
     * @param listNew
     */
    public void insertRepair(List<Map<String, Object>> listNew) {

        ContentValues cv = new ContentValues();
        cv.put("orderCodeOld", "");
        cv.put("reportPersonOld", "");
        cv.put("telephoneOld", "");
        cv.put("receptionUserOld", "");
        cv.put("deviceNameCnOld", "");
        cv.put("deviceIdOld", "");
        cv.put("deviceFatherOld", "");
        cv.put("faultReportOld", "");
        cv.put("repairUserOld", "");
        cv.put("disposeTimeOld", "");
        cv.put("statusOld", "");

        cv.put("orderCodeNew", listNew.get(0).get("orderCodeNew").toString());
        cv.put("reportPersonNew", listNew.get(0).get("reportPersonNew")
                .toString());
        cv.put("telephoneNew", listNew.get(0).get("telephoneNew").toString());
        cv.put("receptionUserNew", listNew.get(0).get("receptionUserNew")
                .toString());
        cv.put("deviceNameCnNew", listNew.get(0).get("deviceNameCnNew")
                .toString());
        cv.put("deviceIdNew", listNew.get(0).get("deviceIdNew").toString());
        cv.put("deviceFatherNew", listNew.get(0).get("deviceFatherNew")
                .toString());
        cv.put("faultReportNew", listNew.get(0).get("faultReportNew")
                .toString());
        cv.put("repairUserNew", listNew.get(0).get("repairUserNew").toString());

        cv.put("disposeTimeNew", listNew.get(0).get("disposeTimeNew")
                .toString());
        cv.put("statusNew", listNew.get(0).get("statusNew").toString());
        cv.put("OperateType", listNew.get(0).get("OperateType").toString());
        cv.put("Operator", listNew.get(0).get("Operator").toString());
        cv.put("OperateTime", listNew.get(0).get("OperateTime").toString());
        cv.put("isupload", 1);// 默认是0,0标识未上传1表示已经上传

        db.insert("MaintenanceOrder_OperatorRecord", null, cv);
        cv.clear();

    }

    /**
     * 插入保修工單信息
     *
     * @param orderCodeOld
     * @param reportPersonOld
     * @param telephoneOld
     * @param receptionUserOld
     * @param deviceNameCnOld
     * @param deviceIdOld
     * @param deviceFatherOld
     * @param faultReportOld
     * @param repairUserOld
     * @param disposeTimeOld
     * @param statusOld
     * @param orderCodeNew
     * @param reportPersonNew
     * @param telephoneNew
     * @param receptionUserNew
     * @param deviceNameCnNew
     * @param deviceIdNew
     * @param deviceFatherNew
     * @param faultReportNew
     * @param repairUserNew
     * @param disposeTimeNew
     * @param statusNew
     * @param OperateType
     * @param Operator
     * @param OperateTime
     */
    public void insertRepair(String orderCodeOld, String reportPersonOld,
                             String telephoneOld, String receptionUserOld,
                             String deviceNameCnOld, String deviceIdOld, String deviceFatherOld,
                             String faultReportOld, String repairUserOld, String disposeTimeOld,
                             Integer statusOld,

                             String orderCodeNew, String reportPersonNew, String telephoneNew,
                             String receptionUserNew, String deviceNameCnNew,
                             String deviceIdNew, String deviceFatherNew, String faultReportNew,
                             String repairUserNew, String disposeTimeNew, Integer statusNew,
                             String OperateType, String Operator, String OperateTime)
            throws Exception {
        ContentValues cv = new ContentValues();
        cv.put("orderCodeOld", orderCodeOld);
        cv.put("reportPersonOld", reportPersonOld);
        cv.put("telephoneOld", telephoneOld);
        cv.put("receptionUserOld", receptionUserOld);
        cv.put("deviceNameCnOld", deviceNameCnOld);
        cv.put("deviceIdOld", deviceIdOld);
        cv.put("deviceFatherOld", deviceFatherOld);
        cv.put("faultReportOld", faultReportOld);
        cv.put("repairUserOld", repairUserOld);
        cv.put("disposeTimeOld", disposeTimeOld);
        cv.put("statusOld", statusOld);

        cv.put("orderCodeNew", orderCodeNew);
        cv.put("reportPersonNew", reportPersonNew);
        cv.put("telephoneNew", telephoneNew);
        cv.put("receptionUserNew", receptionUserNew);
        cv.put("deviceNameCnNew", deviceNameCnNew);
        cv.put("deviceIdNew", deviceIdNew);
        cv.put("deviceFatherNew", deviceFatherNew);
        cv.put("faultReportNew", faultReportNew);
        cv.put("faultReportNew", faultReportNew);
        cv.put("repairUserNew", repairUserNew);

        cv.put("disposeTimeNew", disposeTimeNew);
        cv.put("statusNew", statusNew);
        cv.put("OperateType", OperateType);
        cv.put("Operator", Operator);
        cv.put("OperateTime", OperateTime);
        cv.put("isupload", 1);// 默认是0,0标识未上传1表示已经上传

        db.insert("MaintenanceOrder_OperatorRecord", null, cv);
        cv.clear();
    }

    /**
     * 查询Repair表(查询全部内容)
     */
    public List<String> selectRepair() {
        MaintenanceOrderProperty re;
        List<String> list = new ArrayList<String>();
        Cursor cursor = db.query("MaintenanceOrder_OperatorRecord", null, null,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                re = new MaintenanceOrderProperty();
                re.orderCode = cursor.getString(cursor
                        .getColumnIndex("orderCodeNew"));
                re.reportPerson = cursor.getString(cursor
                        .getColumnIndex("reportPersonNew"));
                re.telephone = cursor.getString(cursor
                        .getColumnIndex("telephoneNew"));
                re.receptionUser = cursor.getString(cursor
                        .getColumnIndex("receptionUserNew"));
                re.deviceNameCn = cursor.getString(cursor
                        .getColumnIndex("deviceNameCnNew"));
                re.building = cursor.getString(cursor
                        .getColumnIndex("deviceFatherNew"));
                re.faultReport = cursor.getString(cursor
                        .getColumnIndex("faultReportNew"));
                re.repairUser = cursor.getString(cursor
                        .getColumnIndex("repairUserNew"));
                re.status = 0;
                re.id = 0;

                re.disposeTime = "2016-06-27";
                re.userAccountName = "mike";
                String sr = JsonTools.StringToJson_Back(re);
                list.add(sr);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // 考虑是否还要写一个以日期作为条件的查询方法
    @SuppressWarnings("unused")
    public String selectRepairByDate(Date start, Date end) throws Exception {
        MaintenanceOrderProperty re = new MaintenanceOrderProperty();
        String startDate = start.toString();
        String endDate = end.toString();
        // 以时间作为查询条件
        Cursor cursor = db.query("MaintenanceOrder_OperatorRecord", null,
                "disposeTimeOld<=" + endDate, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                re.orderCode = cursor.getString(cursor
                        .getColumnIndex("orderCodeNew"));
                re.reportPerson = cursor.getString(cursor
                        .getColumnIndex("reportPersonNew"));
                re.telephone = cursor.getString(cursor
                        .getColumnIndex("telephoneNew"));
                re.receptionUser = cursor.getString(cursor
                        .getColumnIndex("receptionUserNew"));
                re.deviceNameCn = cursor.getString(cursor
                        .getColumnIndex("deviceNameCnNew"));
                re.building = cursor.getString(cursor
                        .getColumnIndex("deviceFatherNew"));
                re.faultReport = cursor.getString(cursor
                        .getColumnIndex("faultReportNew"));
                re.repairUser = cursor.getString(cursor
                        .getColumnIndex("repairUserNew"));
                re.status = 0;
                re.id = 0;
                re.disposeTime = "2016-06-27";
                re.userAccountName = application.userInfo.getAccount()
                        .toString();

            } while (cursor.moveToNext());
        }
        cursor.close();
        return JsonTools.StringToJson_Back(re);
    }

    // 考虑是否还要写一个以是否上传为条件的查询方法
    public List<String> selectRepairByStatus(int status) throws Exception {
        // MaintenanceOrderProperty re = new MaintenanceOrderProperty();

        MaintenanceOrderProperty re;

        List<String> list = new ArrayList<String>();
        // 以是否上传作为查询条件
        Cursor cursor = db.rawQuery(
                "select * from MaintenanceOrder_OperatorRecord where isupload="
                        + status, null);
        if (cursor.moveToFirst()) {
            do {
                re = new MaintenanceOrderProperty();
                re.orderCode = cursor.getString(cursor
                        .getColumnIndex("orderCodeNew"));
                re.reportPerson = cursor.getString(cursor
                        .getColumnIndex("reportPersonNew"));
                re.telephone = cursor.getString(cursor
                        .getColumnIndex("telephoneNew"));
                re.receptionUser = cursor.getString(cursor
                        .getColumnIndex("receptionUserNew"));
                re.deviceNameCn = cursor.getString(cursor
                        .getColumnIndex("deviceNameCnNew"));
                re.building = cursor.getString(cursor
                        .getColumnIndex("deviceFatherNew"));
                re.faultReport = cursor.getString(cursor
                        .getColumnIndex("faultReportNew"));
                re.repairUser = cursor.getString(cursor
                        .getColumnIndex("repairUserNew"));
                re.status = 0;
                re.id = 0;
                re.disposeTime = "2016-06-27";
                re.userAccountName = "mike";
                String str = JsonTools.StringToJson_Back(re);
                list.add(str);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 根据是否上传来更新数据用以修改数据的上传状态 注意：此方法在上传数据操作成功以后再调用
     *
     * @throws Exception
     */
    public void updateRepairByStatus() throws Exception {
        ContentValues values = new ContentValues();
        values.put("statusNew", 1);
        db.update("MaintenanceOrder_OperatorRecord", values, "statusNew=0",
                null);
    }

    // 写一个批量删除的方法以日期作为条件
    public void clearRepairByDate(String startDate, String endDate)
            throws Exception {
        String sqlDelete = "delete from MaintenanceOrder_OperatorRecord where disposeTimeNew Between'"
                + startDate + "'and'" + endDate + "'";
        db.execSQL(sqlDelete);
    }

    /**
     * 由于点检设备相当的都切操作都不相同，所以每种设备的对应一张表。
     */
    public void insertCheck(String TableName, List<Map<String, String>> list, String label) {
        Log.e(TAG, "TableName:" + TableName + "-----list长度:" + list.size() + "-----label:" + label);
        ContentValues values = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG, "value:" + list.get(i).get("name") + "---" + list.get(i).get("value"));
            values.put(list.get(i).get("name"), list.get(i).get("value"));
        }
        values.put("LabelId", label);
        values.put("RecordTime", DateUtils.getTime());
        values.put("CheckUser", MyApplication.userInfo.getAccount());
        db.insert(TableName, null, values);
        values.clear();

    }

    /**
     * 查询单张表的数据
     *
     * @param tableName
     * @return
     */
    public List<Map<String, String>> selectCheck(String tableName) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> item;
        Cursor cursor = db.rawQuery("select * from " + tableName,
                null);
        if (cursor.moveToFirst()) {
            do {
                item = new HashMap<>();
                String time = cursor.getString(cursor
                        .getColumnIndex("RecordTime"));
                String name = cursor.getString(cursor
                        .getColumnIndex("LabelId"));
                item.put("time", time);
                item.put("name", name);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 查所有的点检数据
     */
    public List<Map<String, String>> selectCheck(String name, String nameEn) {
        List<Map<String, String>> list1 = new ArrayList<>();
        Map<String, String> item;
        Cursor cursor = db.query(name, null, null,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                item = new HashMap<>();
                item.put(nameEn, cursor.getString(cursor.getColumnIndex(nameEn)));
                list1.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list1;
    }

    public int selectCheckCount(String name) {
        Log.e(TAG, "表名：" + name);
        String sql = "select count(*) from" + name;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        Log.e(TAG, "记录数量：" + count);
        return count;
    }

    /**
     * 单条插入点检信息
     */
    public void insertCheckOne(Checks_Elevator ch) {
        Log.e(TAG, "stup1");
        insertCheckOne_Elevator(ch);
    }

    /**
     * 插入弱电设备
     *
     * @param list
     */
    private void insertChenkWeak(List<Checks_Weak> list) {
        // TODO Auto-generated method stub
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("LabelId", list.get(i).getLabelId());
            values.put("Monitoring", list.get(i).getMonitoring());
            values.put("Screen", list.get(i).getScreen());
            values.put("WeakCurrentShaft", list.get(i).getWeakCurrentShaft());
            values.put("AirConditioner", list.get(i).getAirConditioner());
            values.put("Other", list.get(i).getOther());
            values.put("CheckUser", list.get(i).getCheckUser());
            values.put("RecordTime", list.get(i).getRecordTime());
            values.put("Note", list.get(i).getNote());
            values.put("isupload", list.get(i).getIsupload());
            db.insert("Check_SenseELV_RoleId", null, values);
            values.clear();
        }

    }

    public List<String> selectTableNames() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.query("sqlite_master ", null, null,
                null, null, null, "name", null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("Name", "item" + cursor.getString(cursor.getColumnIndex("name")));
                list.add(cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Map<String, String>> selcetTable(String name) {
        List<Map<String, String>> list = new ArrayList<>();
        return list;
    }


    public List<String> selectTablesName() {
        List<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from tables where tableName like 'check_%_roid'", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("tableName")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 插入消防设备数据
     *
     * @param list
     */
    private void insertCheckFire(List<Checks_Fire> list) {
        // TODO Auto-generated method stub
        if (list == null) {
            return;
        }
    }

    /**
     * 插入电表数据
     *
     * @param list
     */
    private void insertCheckElec(List<Checks_Elec> list) {
        // TODO Auto-generated method stub
        if (list == null) {
            return;
        }
    }

    /**
     * 单条插入电梯机房数据
     *
     * @param ch
     */
    private void insertCheckOne_Elevator(Checks_Elevator ch) {
        if (ch == null) {
            Log.e(TAG, "null");
            return;
        }
        Log.e(TAG, "insert");
        ContentValues values = new ContentValues();
        values.put("LabelId", ch.getLabelId());
        values.put("AlarmLamp", ch.getAlarmLamp());
        values.put("AlarmFlag", ch.getAlarmFlag());
        values.put("AlarmTemp", ch.getAlarmTemp());
        values.put("AlarmOrder", ch.getAlarmOrder());
        values.put("AlarmRunning", ch.getAlarmRunning());
        values.put("DoorsWindows", ch.getDoorsWindows());
        values.put("CheckUser", ch.getCheckUser());
        values.put("RecordTime", ch.getRecordTime());
        values.put("Note", ch.getNote());
        values.put("isupload", 0);
        db.insert("Check_SenseLIFT_RoleId", null, values);
        values.clear();
    }

    /**
     * 插入电梯数据
     *
     * @param list
     */
    private void insertCheckElevator(List<Checks_Elevator> list) {
        // TODO Auto-generated method stub
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("LabelId", list.get(i).getLabelId());
            values.put("AlarmLamp", list.get(i).getAlarmLamp());
            values.put("AlarmFlag", list.get(i).getAlarmFlag());
            values.put("AlarmTemp", list.get(i).getAlarmTemp());
            values.put("AlarmOrder", list.get(i).getAlarmOrder());
            values.put("AlarmRunning", list.get(i).getAlarmRunning());
            values.put("DoorsWindows", list.get(i).getDoorsWindows());
            values.put("CheckUser", list.get(i).getCheckUser());
            values.put("RecordTime", list.get(i).getRecordTime());
            values.put("Note", list.get(i).getNote());
            values.put("isupload", list.get(i).getIsupload());
            db.insert("Check_SenseLIFT_RoleId", null, values);
            values.clear();
        }
    }

    public List<Map<String, Object>> selectTemplateInfo(String type) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> item;
//        String[] selectionArgs = {type};//具体的条件,注意要对应条件字段
        Cursor cursor = db.query("CheckTemplete", null, "templeteCode='"+type+"'", null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                item = new HashMap<>();
                item.put("templateCode",
                        cursor.getString(cursor.getColumnIndex("templeteCode")));
                item.put("nameEn",
                        cursor.getString(cursor.getColumnIndex("nameEn")));
                item.put("nameCn",
                        cursor.getString(cursor.getColumnIndex("nameCn")));
                item.put("dataType",
                        cursor.getString(cursor.getColumnIndex("dataType")));
                item.put("defaultValue",
                        cursor.getString(cursor.getColumnIndex("defaultValue")));
                item.put("allValue",
                        cursor.getString(cursor.getColumnIndex("allValue")));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.e(TAG, "list size:" + list.size());
        return list;
    }

    /**
     * 插入模板
     *
     * @param list
     */
    public void insertTempcode(List<Map<String, Object>> list) {
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("templeteCode", list.get(i).get("templeteCode")
                    .toString());
            values.put("nameCn", list.get(i).get("nameCn").toString());
            values.put("nameEn", list.get(i).get("nameEn").toString());
            values.put("dataType", list.get(i).get("dataType").toString());
            values.put("defaultValue", list.get(i).get("defaultValue")
                    .toString());
            values.put("allValue", list.get(i).get("allValue").toString());
            values.put("sign", list.get(i).get("sign").toString());
            db.insert("CheckTemplete", null, values);
            values.clear();
        }
    }

    /**
     * 查询电梯
     *
     * @return
     */
    public List<Map<String, Object>> selectCheckElevator() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        Cursor cursor = db.query("Check_SenseLIFT_RoleId", null, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                list.clear();
                item = new HashMap<>();
                item.put("AlarmLamp",
                        cursor.getString(cursor.getColumnIndex("AlarmLamp")));
                item.put("AlarmFlag",
                        cursor.getString(cursor.getColumnIndex("AlarmFlag")));
                item.put("AlarmTemp",
                        cursor.getString(cursor.getColumnIndex("AlarmTemp")));
                item.put("AlarmOrder",
                        cursor.getString(cursor.getColumnIndex("AlarmOrder")));
                item.put("DoorsWindows",
                        cursor.getString(cursor.getColumnIndex("DoorsWindows")));
                item.put("CheckUser",
                        cursor.getString(cursor.getColumnIndex("CheckUser")));
                item.put("Note",
                        cursor.getString(cursor.getColumnIndex("Note")));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insertDeviceInfo() {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        db.insert("DeviceBasicInfo", null, values);
        values.clear();

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction();
        db.close();
    }

    // 写一个清空上个月所有数据的方法
    public void clearAllTable(String startDate, String endDate)
            throws Exception {
        // Log.e(tag, msg);
        clearRepairByDate(startDate, endDate);

    }
}
