package com.sinochem.printlib.Interface;

/**
 * Created by ShengS on 2018/8/16.
 * 打印机设备初始化异常
 */

public interface PrintDeviceErrorCallBack {
    void onConnectSucess();

    void onConnectFailed();

    void onConnectClose();

    /**
     * 设备未连接
     */
    void noDevice();
}
