package com.sinochem.printlib.Interface;

import android.content.Context;

/**
 * Created by ShengS on 2018/8/16.
 * 打印机控制类
 */

public interface IPrint {

    /**
     * 初始化打印机
     */
    void initPrintDevice();

    /**
     * 连接打印机
     */
//    void connectPrintDevice();

    /**
     * 检查打印机状态
     */
    void checkPrintState(IPrintConnectedState printConnectedState);

    /**
     * 断开连接
     */
    void disConnect();

    /**
     * 打印机切纸
     */
    void pagerHalfCut();

    /**
     * 打印机实例对象 进行打印。
     */
    Object getDevices();

    /**
     * 获取印机纸张尺寸（款度)
     *
     * @return
     */
    int getPrintPaperSize();
}
