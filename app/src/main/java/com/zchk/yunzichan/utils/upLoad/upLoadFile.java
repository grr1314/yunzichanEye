package com.zchk.yunzichan.utils.upLoad;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.db.DBUtils;
import com.zchk.yunzichan.entity.model.check.CheckTemplateMessageItem;
import com.zchk.yunzichan.entity.model.check.DBTableColRowInfo;
import com.zchk.yunzichan.entity.model.check.DBTableRowInfo;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderProperty;
import com.zchk.yunzichan.entity.model.upload.APPDataUploadMessage;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 兄弟！哥尽力了
 *
 * @author SenseTech
 */
public class upLoadFile {
    private static final String TAG = "upLoadFile";

    private static DBUtils dbUtils;
    public static String upLoadAll(DBUtils dbu, Context context) {
        dbUtils=dbu;
        APPDataUploadMessage message = new APPDataUploadMessage();

        CheckTemplateMessageItem[] checkTemplateMessageItems = null;

        //第一步：获取所有点检信息表名字
        List<String> list = dbu.selectTableNames();
        List<String> listTable = new ArrayList<>();
        for (int tables = 0; tables < list.size(); tables++) {
            String[] str = list.get(tables).split("_");
            if (str.length == 3) {
                listTable.add(list.get(tables));
            }
        }
        for (int i = 0; i < listTable.size(); i++) {
            checkTemplateMessageItems=new CheckTemplateMessageItem[listTable.size()];
            CheckTemplateMessageItem checkTemplateMessageItem=new CheckTemplateMessageItem();
//            Log.e(TAG, listTable.get(i));
            //下面我从表里面查询数据记录
            List<Map<String, String>> lsCount = dbu.selectCheck(listTable.get(i));
//            Log.e(TAG,lsCount.size()+"");
            //填装行数据
            checkTemplateMessageItem.items=Row(lsCount.size(), listTable.get(i));
            checkTemplateMessageItem.dbTableName=listTable.get(i);

            checkTemplateMessageItems[i]=checkTemplateMessageItem;
        }


        message.checkItems=checkTemplateMessageItems;

        List<String> listRepair = dbu.selectRepair();
        MaintenanceOrderProperty mops[] = new MaintenanceOrderProperty[listRepair
                .size()];
        MaintenanceOrderProperty mop;
        for (int i = 0; i < listRepair.size(); i++) {
            mop = JSON.parseObject(listRepair.get(i), MaintenanceOrderProperty.class);
            mops[i] = mop;
        }
        message.orderItems = mops;
        String str = JsonTools.StringToJson_UpLoad(message);
        Log.e(TAG, str);
        return str;
    }

    private static  DBTableColRowInfo[] Row(int size, String name) {
        DBTableColRowInfo[] dbTableColRowInfo = new DBTableColRowInfo[size];
//        Log.e(TAG, "table name:"+name);
//        Log.e(TAG,dbTableColRowInfo.length+"");
//        if (size==0)
//        {
//            DBTableColRowInfo dbTableColRowInfo1;
//            //接下来封装每一行的数据
//            dbTableColRowInfo1 = RowCol(name);
////            dbTableColRowInfo[0] = dbTableColRowInfo1;
//        }
        for (int i = 0; i < dbTableColRowInfo.length; i++) {
            DBTableColRowInfo dbTableColRowInfo1;


            //接下来封装每一行的数据
            dbTableColRowInfo1 = RowCol(name);

            dbTableColRowInfo[i] = dbTableColRowInfo1;
        }
        return dbTableColRowInfo;
    }

    private static DBTableColRowInfo RowCol(String name) {

        String[] str = name.split("_");
        String s = str[1];

        List<Map<String, Object>> tempCode = dbUtils.selectTemplateInfo(s);//拿到了字段
        //第三步：补全字段名
        Map<String, Object> item1 = new HashMap<>();
        item1.put("nameEn", "LabelId");
        tempCode.add(item1);
        item1 = new HashMap<>();
        item1.put("nameEn", "PicPath");
        tempCode.add(item1);
        item1 = new HashMap<>();
        item1.put("nameEn", "RecordTime");
        tempCode.add(item1);
        Log.e(TAG,s+"-----"+tempCode.size()+"");
        DBTableColRowInfo dbTableColRowInfo = new DBTableColRowInfo();


        DBTableRowInfo[] dbTableRowInfos = new DBTableRowInfo[tempCode.size()];

        for (int i=0;i<tempCode.size();i++)
        {
            DBTableRowInfo dbTableRowInfo=new DBTableRowInfo();
            dbTableRowInfo.value="--";
            dbTableRowInfo.colName="--";
            dbTableRowInfos[i]=dbTableRowInfo;
        }
        dbTableColRowInfo.row = dbTableRowInfos;
        return dbTableColRowInfo;
    }

}
