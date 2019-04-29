package com.sinochem.printlib.PrintProxy;

import com.sinochem.printlib.Interface.IPrint;
import com.sinochem.printlib.Interface.IPrintFunction;

/**
 * Created by ShengS on 2018/8/18.
 * 通用打印代理 代理所有打印设备
 */

public class PrintUniversalProxy {
    private final IPrint printDevice;
    private final IPrintFunction iPrintFunction;

    /**
     * 构造函数传进打印机设备实现类和功能实现类
     *
     * @param printDevice
     * @param iPrintFunction
     */
    public PrintUniversalProxy(IPrint printDevice, IPrintFunction iPrintFunction) {
        this.printDevice = printDevice;
        this.iPrintFunction = iPrintFunction;
    }

    /**
     * 初始化打印机
     */
    public void initPrint() {
        printDevice.initPrintDevice();
    }

    /**
     * 打印模板，通用
     */
    public void printTemPlate(Object obj) {
    }
}
