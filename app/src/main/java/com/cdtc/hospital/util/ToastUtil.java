package com.cdtc.hospital.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Sweven on 2018/9/15.
 * Email:sweventears@Foxmail.com
 * <p>
 * Toast工具类
 */
public class ToastUtil {
    private static Toast toast;
    private Activity activity;

    public ToastUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 显示一个较短时间的提示
     * 适用于所有类
     *
     * @param context 上下文
     * @param message 显示的文字
     */
    @SuppressLint("ShowToast")
    public static void showShort(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 显示一个较长时间的提示
     * 适用于所有类
     *
     * @param context 上下文
     * @param message 显示的文字
     */
    @SuppressLint("ShowToast")
    public static void showLong(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 取消显示
     */
    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 对错误操作进行提示，利于编程者阅读
     * 适用于所有类
     *
     * @param context 上下文
     * @param message 要显示的文字
     */
    @SuppressLint("ShowToast")
    public static void showError(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 显示一个较短时间的提示
     * 适用于activity类
     *
     * @param message 显示的文字
     */
    @SuppressLint("ShowToast")
    public void showShort(String message) {
        if (toast == null) {
            toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 显示一个较长时间的提示
     * 适用于activity类
     *
     * @param message 显示的文字
     */
    @SuppressLint("ShowToast")
    public void showLong(String message) {
        if (toast == null) {
            toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 对错误操作进行提示，利于编程者阅读
     * 适用于activity类
     *
     * @param message 要显示的文字
     */
    @SuppressLint("ShowToast")
    public void showError(String message) {
        if (toast == null) {
            toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
