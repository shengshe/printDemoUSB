package com.sinochem.printlib.Interface;

/**
 * Created by ShengS on 2018/8/16.
 */

public interface IPrintConnectedState {
    void stateNormal();//正常

    void stateError();//连接异常

    void noPaper();//却纸

    void paperWillDo();//纸将尽

    void sendStateMsgError();//发送数据失败

    void receiveStateMsgError();//接受数据失败
}