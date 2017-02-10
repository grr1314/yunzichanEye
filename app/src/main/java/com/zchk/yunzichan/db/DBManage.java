package com.zchk.yunzichan.db;

import android.content.Context;

public class DBManage {

    public static final String ASSETUSERINFO_CREATE = "create table IF NOT EXISTS assetUserInfo(id integer primary key autoincrement,"
            + "account VARCHAR(32) NOT NULL, "
            + "password VARCHAR(32),"
            + "companyId Integer,"
            + "company VARCHAR(32),"
            + "userName VARCHAR(32),"
            + "roleId Integer,"
            + "roleName VARCHAR(32))";

    public static final String EQUIPMENTMAINTAINRECORD_CREATE = "create table IF NOT EXISTS equipmentMaintainRecord(id integer primary key autoincrement,"
            + "checkUser VARCHAR(32) NOT NULL,"
            + "assetCode VARCHAR(50) NOT NULL,"
            + "checkTime DATETIME ,"
            + "status INT NOT NULL,"
            + "checkValue INT NOT NULL,"
            + "note VARCHAR(128),"
            + "isupload INT NOT NULL)";

    public static final String TAGBASICINFO_CREATE = "create table IF NOT EXISTS TagBasicInfo(id integer primary key autoincrement,"
            + "tagId CHAR(50),"
            + "tagName CHAR(50),"
            + "tagType CHAR(50),"
            + "tagModel CHAR(50),"
            + "tagFactory CHAR(50),"
            + "tagProducer CHAR(50),"
            + "tagPosition CHAR(50),"
            + "tagFilePath CHAR(50),"
            + "userName CHAR(50),"
            + "createTime DATETIME,"
            + "allInfo CHAR(500),"
            + "isupload INT NOT NULL)";

    public static final String CHECKSENSELIFTROLEID_CREATE = "create table IF NOT EXISTS Check_SenseLIFT_RoleId (id integer primary key autoincrement,"
            + "LabelId VARCHAR(32),"
            + "AlarmLamp VARCHAR(32),"
            + "AlarmFlag VARCHAR(32),"
            + "AlarmTemp VARCHAR(32),"
            + "AlarmOrder VARCHAR(32),"
            + "AlarmRunning VARCHAR(32),"
            + "DoorsWindows VARCHAR(32),"
            + "CheckUser VARCHAR(32),"
            + "RecordTime datetime,"
            + "Note VARCHAR(128),"
            + "isupload INT NOT NULL)";

    public static final String CHECKSENSEPOMROLEID_CREATE = "create table IF NOT EXISTS Check_SensePOM_RoleId (id integer primary key autoincrement,"
            + "LabelId VARCHAR(32),"
            + "PowerMeter VARCHAR(32),"
            + "CheckUser VARCHAR(32),"
            + "RecordTime datetime,"
            + "Note VARCHAR(128)," + "isupload INT NOT NULL)";

    public static final String CHECKSENSEELVROLEID_CREATE = "create table IF NOT EXISTS Check_SenseELV_RoleId (id integer primary key autoincrement,"
            + "LabelId VARCHAR(32),"
            + "Monitoring VARCHAR(32),"
            + "Screen VARCHAR(32),"
            + "WeakCurrentShaft VARCHAR(32),"
            + "AirConditioner VARCHAR(32),"
            + "Other VARCHAR(32),"
            + "CheckUser VARCHAR(32),"
            + "RecordTime datetime,"
            + "Note VARCHAR(128)," + "isupload INT NOT NULL)";

    public static final String CHECKSENSEFERROLEID_CREATE = "create table IF NOT EXISTS Check_SenseFER_RoleId (id integer primary key autoincrement,"
            + "LabelId VARCHAR(32),"
            + "running VARCHAR(32),"
            + "BlockBat VARCHAR(32),"
            + "ElectricBoxApperence VARCHAR(32),"
            + "OpenMode VARCHAR(32),"
            + "FireEquipement VARCHAR(32),"
            + "CheckUser VARCHAR(32),"
            + "RecordTime datetime,"
            + "Note VARCHAR(128)," + "isupload INT NOT NULL)";

    public static final String REPAIR_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS MaintenanceOrder_OperatorRecord ("
            + "id integer primary key autoincrement ,"
            + "orderCodeOld varchar(32) NULL,"
            + "reportPersonOld varchar(32) NULL,"
            + "telephoneOld varchar(32) NULL,"
            + "receptionUserOld varchar(32) NULL,"
            + "deviceNameCnOld varchar(32) NULL,"
            + "deviceIdOld varchar(32) NULL,"
            + "deviceFatherOld varchar(32) NULL,"
            + "faultReportOld varchar(256) NULL,"
            + "repairUserOld varchar(32) NULL,"
            + "disposeTimeOld datetime NULL ,"
            + "statusOld int NULL ,"
            + "orderCodeNew varchar(32) NULL ,"
            + "reportPersonNew varchar(32) NULL ,"
            + "telephoneNew varchar(32) NULL ,"
            + "receptionUserNew varchar(32) NULL ,"
            + "deviceNameCnNew varchar(32) NULL ,"
            + "deviceIdNew varchar(32) NULL ,"
            + "deviceFatherNew varchar(32) NULL ,"
            + "faultReportNew varchar(256) NULL ,"
            + "repairUserNew varchar(32) NULL ,"
            + "disposeTimeNew datetime NULL ,"
            + "statusNew int NULL ,"
            + "OperateType varchar(32) NULL ,"
            + "Operator varchar(32) NULL ,"
            + "OperateTime datetime NULL ," + "isupload INT NOT NULL)";

    public static final String DEVISE_BASE = "create table IF NOT EXISTS CheckTemplete (id integer primary key autoincrement,"
            + "templeteCode VARCHAR(32),"
            + "nameCn VARCHAR(32),"
            + "nameEn VARCHAR(32),"
            + "dataType VARCHAR(32),"
            + "defaultValue VARCHAR(32),"
            + "allValue VARCHAR(32),"
            + "sign VARCHAR(32))";

    public static final String DEVICE_BASE_INFO = "create table IF NOT EXISTS DeviceBasicInfo("
            + "id integer primary key autoincrement ,"
            + "deviceType int NOT NULL ,"
            + "manufacturerID int NOT NULL ,"
            + "nameEn varchar(64) NOT NULL ,"
            + "nameCn varchar(128) NOT NULL ,"
            + "insertTime datetime NULL ,"
            + "updateTime datetime NULL ,"
            + "factoryId int NULL ,"
            + "assetsManual varchar(256) NULL ,"
            + "labelOrNot int NULL ,"
            + "code varchar(32) NULL ,"
            + "building varchar(64) NULL ,"
            + "position varchar(64) NULL )";

    public static final String ASSET = "create table IF NOT EXISTS Asset("
            + "id integer primary key autoincrement ,"
            + "nameCn varchar(128) NOT NULL ,"
            + "typeId int NOT NULL ,"
            + "typeName varchar(32) NOT NULL ,"
            + "lableCode varchar(128) NULL ,"
            + "companyId int NULL ,"
            + "company varchar(32) NULL)";

    public static final String TempAndDev = "create table IF NOT EXISTS tempanddev("
            + "id integer primary key autoincrement ,"
            + "typeCode varchar(128) NOT NULL ,"
            + "templeteCode varchar(32) NOT NULL)";

    public static final String APPAssetTypeItem = "create table IF NOT EXISTS assetType("
            + "id integer primary key autoincrement ,"
            + "typeCode varchar(128) NOT NULL ,"
            + "classifyNameCn varchar(32) NOT NULL)";

    public static final String CHECKROUTE = "create table IF NOT EXISTS checkRoute("
            + "id integer primary key autoincrement ,"
            + "routeName varchar(128) NOT NULL ,"
            + "typeId int NOT NULL ,"
            + "typeName varchar(32) NOT NULL ,"
            + "lableCode varchar(128) NULL ,"
            + "companyId int NULL ,"
            + "company varchar(32) NULL)";

    public static final String check_sensexgd_roleid = "create table IF NOT EXISTS check_sensexgd_roleid("
            + "id integer primary key autoincrement ,"
            + "LabelId varchar(128) NOT NULL ,"
            + "CheckUser varchar(32) NOT NULL ,"
            + "PicPath varchar(128) NULL ,"
            + "CheckPointa varchar(32) NOT NULL ,"
            + "Note varchar(128) NULL ,"
            + "RecordTime datetime)";

    public static final String check_sensefpg_roleid = "create table IF NOT EXISTS check_sensefpg_roleid("
            + "id integer primary key autoincrement ,"
            + "LabelId varchar(128) NOT NULL ,"
            + "CheckUser varchar(32) NOT NULL ,"
            + "Waterlevel varchar(32) NOT NULL,"
            + "presslevel varchar(32) NOT NULL,"
            + "innerdeveice varchar(32) NOT NULL,"
            + "windowsdoor varchar(32) NOT NULL,"
            + "PicPath varchar(128) NULL ,"
            + "RecordTime datetime)";

    public static final String check_sensefau_roleid = "create table IF NOT EXISTS check_sensefau_roleid("
            + "id integer primary key autoincrement ,"
            + "LabelId varchar(128) NOT NULL ,"
            + "CheckUser varchar(32) NOT NULL ,"
            + "autoopen varchar(32) NOT NULL,"
            + "pressnumber varchar(32) NOT NULL,"
            + "openwell varchar(32) NOT NULL,"
            + "nowell varchar(32) NOT NULL,"
            + "notem varchar(32) NOT NULL,"
            + "PicPath varchar(128) NULL ,"
            + "companyId int(11) NULL,"
            + "RecordTime datetime)";

    public static final String check_senseiew_roleid = "create table IF NOT EXISTS check_senseiew_roleid("
            + "id integer primary key autoincrement ,"
            + "LabelId varchar(128) NOT NULL ,"
            + "CheckUser varchar(32) NOT NULL ,"
            + "temiswell varchar(32) NOT NULL,"
            + "cleanwell varchar(32) NOT NULL,"
            + "indoortem varchar(32) NOT NULL,"
            + "PicPath varchar(128) NULL ,"
            + "companyId int(11) NULL,"
            + "RecordTime datetime)";

    public static final String check_sensesgp_roleid = "create table IF NOT EXISTS check_sensesgp_roleid("
            + "id integer primary key autoincrement ,"
            + "LabelId varchar(128) NOT NULL ,"
            + "CheckUser varchar(32) NOT NULL ,"
            + "autoopenwell varchar(32) NOT NULL,"
            + "mantelopen varchar(32) NOT NULL,"
            + "innerwell varchar(32) NOT NULL,"
            + "PicPath varchar(128)  NULL ,"
            + "companyId int(11) NULL,"
            + "RecordTime datetime)";

    public static final String check_sensenlt_roleid = "create table IF NOT EXISTS check_sensenlt_roleid("
            + "id integer primary key autoincrement ,"
            + "LabelId varchar(128) NOT NULL ,"
            + "CheckUser varchar(32) NOT NULL ,"
            + "iscleanwell varchar(32) NOT NULL,"
            + "isdevicewelll varchar(32) NOT NULL,"
            + "ietemwell varchar(32) NOT NULL,"
            + "doorclosewell varchar(32) NOT NULL,"
            + "PicPath varchar(128)  NULL ,"
            + "companyId int(11) NULL,"
            + "RecordTime datetime)";

    public static final String check_vidicon_roleid = "create table IF NOT EXISTS check_vidicon_roleid("
            + "id integer primary key autoincrement ,"
            + "LabelId varchar(128) NOT NULL ,"
            + "CheckUser varchar(32) NOT NULL ,"
            + "eyeisclean varchar(32) NOT NULL,"
            + "eyepower varchar(32) NOT NULL,"
            + "eyevideo varchar(32) NOT NULL,"
            + "PicPath varchar(128)  NULL ,"
            + "companyId int(11)  NULL,"
            + "RecordTime datetime)";

    public static String tables="create table IF NOT EXISTS tables("
            + "id integer primary key autoincrement ,"
            + "type int NOT NULL ,"
            + "tableName varchar(128))";


    public static DatabaseHelper getDatabaseHelper(Context context) {
        return new DatabaseHelper(context, "DevOpsDevelop");
    }

}
