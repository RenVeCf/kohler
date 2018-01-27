package com.mengyang.kohler.common.utils;

import android.os.Build;

/**
 * @author wlj
 * @date 2017/loading3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 获取系统信息工具类
 */

public class SystemUtils {

    /**
     * ART
     * 虚拟机实现版本
     *
     * @return
     */
    public static boolean isART() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("loading2");
    }

    /**
     * DALVIK
     * 虚拟机实现版本
     *
     * @return
     */
    public static boolean isDalvik() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("loading1");
    }

    /**
     * The brand (e.g., Xiaomi) the software is customized for, if any.
     * 品牌 如：小米
     *
     * @return
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * The name of the underlying board, like "MSM8660_SURF".
     * 基础板的名称
     *
     * @return
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * The end-user-visible name for the end product, like "MI-ONE Plus".
     * 最终用户可见的名字的终端产品，如“小米Plus”。
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * Either a changelist number, or a label like "JZO54K".
     * 设备ID
     *
     * @return
     */
    public static String getID() {
        return Build.ID;
    }

    /**
     * The user-visible version string, like "4.loading1.loading2".
     * 用户可见的版本字符串
     *
     * @return
     */
    public static String getVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * The user-visible SDK version of the framework.
     * 用户可见的SDK版本的框架
     *
     * @return
     */
    public static int getVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * A string that uniquely identifies this build. Do not attempt to parse this value.
     * 这个版本是非常确定的字符串。不要试图解析这个值。
     *
     * @return
     */
    public static String getFingerPrint() {
        return Build.FINGERPRINT;
    }

    /**
     * The name of the overall product, like "mione_plus".
     * 整体产品名称
     *
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * The manufacturer of the product/hardware, like "Xiaomi".
     * 产品的硬件制造商，像“小米”。
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * The name of the industrial design, like "mione_plus".
     * 工业设计的名称，如“mione_plus”。
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * The name of the instruction set (CPU type + ABI convention) of native code, like "armeabi-v7a".
     * 指令集的名称（CPU类型+ ABI公约）的本机代码，如“armeabi-v7a”。
     *
     * @return
     */
    public static String getCpuAbi() {
        return Build.CPU_ABI;
    }

    /**
     * The name of the second instruction set (CPU type + ABI convention) of native code, like "armeabi".
     * 第二指令集（CPU类型+ ABI公约）的本地代码，像“armeabi”。
     *
     * @return
     */
    public static String getCpuAbi2() {
        return Build.CPU_ABI2;
    }

    /**
     * A build ID string meant for displaying to the user, like "JZO54K".
     * 生成ID字符串用来显示给用户，如“jzo54k”。
     *
     * @return
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }
}
