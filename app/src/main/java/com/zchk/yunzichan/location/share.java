package com.zchk.yunzichan.location;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zchk.yunzichan.entity.bean.Device;
import com.zchk.yunzichan.entity.bean.UserInfo;

import java.util.List;

/**
 * �����������û���Ϣ��¼��Ϣ�Ļ���
 *
 * @author SenseTech
 */
public class share {
    //
    private static String USERINFO = "userInfo";

    private static String SET_MODE = "mode";

    private static String ISLOGIN = "login";

    private static String USER_PERMISSION = "permission";
    private static String LAST_USER = "last_user";
    private static String DEVICEINFO = "device_info";

    private static String ERROR = "error";

    private static String ERROR_DEVICE = "error_device";


    public static void setErrorName(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(ERROR_DEVICE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("errorName", name);
        editor.commit();
    }

    public static String getErrorName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ERROR_DEVICE, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString("errorName","");
        return value;
    }

    public static void setError(Context context, Boolean isError) {
        SharedPreferences preferences = context.getSharedPreferences(ERROR,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("error", isError);
        editor.commit();
    }

    public static Boolean getError(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ERROR, Context.MODE_PRIVATE);
        Boolean value = sharedPreferences.getBoolean("error", false);
        return value;
    }


    public static void setLastUser(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(LAST_USER,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("lastUser", name);
        editor.commit();
    }

    public static String getLastUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                LAST_USER, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString("lastUser", "");
        return value;
    }

    public static void logout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(ISLOGIN,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        clearDevice(context);
    }

    private static void clearDevice(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DEVICEINFO,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void clearError(Context context)
    {
        clearErrorDeviceName(context);
        clearErrorDevice(context);
    }
    private static void clearErrorDevice(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(ERROR,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    private static void clearErrorDeviceName(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(ERROR_DEVICE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void setDeviceInfo(String string, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DEVICEINFO,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("device", string);
        editor.commit();
    }

    public static String getDeviceInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DEVICEINFO,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        String str = preferences.getString("device", "");
        return str;
    }


    public static void setUserInfo(Context context, String account, String pass, String roleName, String userName) {

        SharedPreferences preferences = context.getSharedPreferences(USERINFO,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("account", account);
        editor.putString("password", pass);
        editor.putString("roleName", roleName);
        editor.putString("userName", userName);
        editor.commit();
    }

    /**
     * @return
     */
    public static UserInfo getUserInfo(Context context) {
        UserInfo str = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USERINFO, Context.MODE_PRIVATE);
        str = new UserInfo();
        str.setAccount(sharedPreferences.getString("account", ""));
        str.setPassword(sharedPreferences.getString("password", ""));
        str.setRoleName(sharedPreferences.getString("roleName", ""));
        str.setUserName(sharedPreferences.getString("userName", ""));
        return str;
    }

    /**
     * @param str
     */
    public static void setMode(Context context, int mode) {
        SharedPreferences preferences = context.getSharedPreferences(SET_MODE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt("mode", mode);
        editor.commit();
    }

    public static void setLoginStatus(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ISLOGIN, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean("LoginStatus", b);
        editor.commit();

    }

    public static boolean getLoginStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ISLOGIN, Context.MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean("LoginStatus", false);
        return b;
    }

    /**
     * @return
     */
    public static int getMode(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SET_MODE, Context.MODE_PRIVATE);
        int mode = sharedPreferences.getInt("mode", 1);
        return mode;

    }

    /**
     * @param context
     * @param permission
     */
    public static void setUserPermission(Context context, int permission) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_PERMISSION, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt("permission", permission);
        editor.commit();
    }

    /**
     * @param context
     * @return
     */
    public static int getUserPermission(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_PERMISSION, Context.MODE_PRIVATE);
        int permission = sharedPreferences.getInt("permission", -1);
        return permission;
    }

}
