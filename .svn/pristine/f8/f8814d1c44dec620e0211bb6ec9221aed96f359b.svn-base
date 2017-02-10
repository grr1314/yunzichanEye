package cn.yzl.imgupload.application;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 易点付 伊 on 2016/10/19.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化 Logger
        Logger.init("IMG_UPLOAD").methodCount(0).hideThreadInfo().logLevel(LogLevel.NONE);
    }
}
