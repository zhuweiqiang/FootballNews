package me.coderblog.footballnews;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.tendcloud.tenddata.TCAgent;

/**
 * 自定义Application，进行全局的初始化
 */
public class FootballNewsApplication extends Application {

    private static Context context; //Context对象
    private static Handler handler; //Handler对象
    private static int mainThreadID;//主线程ID

    @Override
    public void onCreate() {
        super.onCreate();

        TCAgent.LOG_ON=true;
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        TCAgent.init(this, "E1FC04E9E8E73C2D0F8AAA9FD45B2152", "cool");
        TCAgent.setReportUncaughtExceptions(true);

        context = getApplicationContext();
        handler = new Handler();
        mainThreadID = Process.myTid();
    }

    //提供相应getter方法
    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadID() {
        return mainThreadID;
    }
}
