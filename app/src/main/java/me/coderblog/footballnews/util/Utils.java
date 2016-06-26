package me.coderblog.footballnews.util;

import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import me.coderblog.footballnews.FootballNewsApplication;

/**
 * 普通工具类
 *
 * 作用：封装了常用零碎的功能
 */
public class Utils {

    //获取Context对象
    public static Context getContext() {
        return FootballNewsApplication.getContext();
    }

    //获取Handler对象
    public static Handler getHandler() {
        return FootballNewsApplication.getHandler();
    }

    //获取主线程的ID
    public static int getMainThreadID() {
        return FootballNewsApplication.getMainThreadID();
    }

    /*====================================加载资源文件===============================================*/

    //获取strings.xml文件中的字符串
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    //获取strings.xml文件中的字符串数组
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    //获取colors.xml文件中的颜色
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    //获取dimens.xml文件中的尺寸(单位为像素)
    public static int getPixelDimem(int id) {
        return getContext().getResources().getDimensionPixelOffset(id);
    }

    /*===========================================dp与px转化========================================*/

    //dp转化为px
    public static int dp2px(float dp) {
        //获取设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    //px转化为dp
    public static float px2dp(int px) {
        //获取设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    /*====================================加载布局文件==============================================*/

    //将布局文件转化为View对象
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    /*======================================线程相关================================================*/

    //判断是否运行在主线程
    public static boolean isRunOnMainThread() {
        //获取当前线程的Id，如果当前线程的Id和主线程的Id相同，表示运行在主线程
        return Process.myTid() == getMainThreadID();
    }

    //运行在主线程中
    public static void RunOnMainThread(Runnable r) {

        if (isRunOnMainThread()) {
            //已经在主线程直接运行
            r.run();
        } else {
            //如果是子线程，借助Handler让其运行在主线程
            getHandler().post(r);
        }
    }

}
