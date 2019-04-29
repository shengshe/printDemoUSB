package com.sinochem.printlib.daping.hangxin_sp;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.printer.sdk.PrinterConstants;
import com.printer.sdk.PrinterInstance;
import com.printer.sdk.usb.USBPort;
import com.sinochem.printlib.Interface.IPrint;
import com.sinochem.printlib.Interface.IPrintConnectedState;
import com.sinochem.printlib.Interface.PrintDeviceErrorCallBack;
import com.sinochem.printlib.PrintLib_Constans.Constans_PrintLib;
import com.sinochem.printlib.SDCardUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by ShengS on 2018/8/16.
 * 航信大屏打印机_usb连接
 */

public class USBPrintDevices_HX implements IPrint {

    private final Context context;
    private final PrintDeviceErrorCallBack deviceErrorCallBack;
    private ArrayList<UsbDevice> deviceList;
    private static UsbDevice mUSBDevice;
    private static PrinterInstance myPrinter;//打印机实例
    private String devicesName;
    private String devicesAddress;
    private static final String ACTION_USB_PERMISSION = "com.android.usb.USB_PERMISSION";
    private static boolean isConnected;


    public USBPrintDevices_HX(Context context, PrintDeviceErrorCallBack deviceErrorCallBack) {
        this.context = context;
        this.deviceErrorCallBack = deviceErrorCallBack;
    }

    // 用于接受连接状态消息的 Handler
    private Handler mHandler = new Handler() {


        @SuppressLint("ShowToast")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS:
                    isConnected = true;
                    if (deviceErrorCallBack != null)
                        deviceErrorCallBack.onConnectSucess();
//                    GlobalContants.ISCONNECTED = isConnected;
//                    GlobalContants.DEVICENAME = devicesName;
                    break;
                case PrinterConstants.Connect.FAILED:
                    isConnected = false;
                    if (deviceErrorCallBack != null)
                        deviceErrorCallBack.onConnectFailed();
//                    Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
//                    XLog.i(TAG, "ZL at SettingActivity Handler() 连接失败!");
                    break;
                case PrinterConstants.Connect.CLOSED:
                    isConnected = false;
                    if (deviceErrorCallBack != null)
                        deviceErrorCallBack.onConnectClose();
//                    GlobalContants.ISCONNECTED = isConnected;
//                    GlobalContants.DEVICENAME = devicesName;
//                    Toast.makeText(mContext, R.string.conn_closed, Toast.LENGTH_SHORT).show();
//                    XLog.i(TAG, "ZL at SettingActivity Handler() 连接关闭!");
                    break;
                case PrinterConstants.Connect.NODEVICE:
                    isConnected = false;
                    if (deviceErrorCallBack != null)
                        deviceErrorCallBack.noDevice();
//                    Toast.makeText(mContext, R.string.conn_no, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void initPrintDevice() {
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        //连接打印机
        usbConn(manager);
    }

    public void usbConn(UsbManager manager) {
        //过滤当前设备
        doDiscovery(manager);
        if (deviceList.isEmpty()) {
            SDCardUtils.saveMessageToSD_YJ("无打印设备");

//            Toast.makeText(context, R.string.no_connected, 0).show();
            if (deviceErrorCallBack != null)
                deviceErrorCallBack.noDevice();
            return;
        }
        mUSBDevice = deviceList.get(0);
        if (mUSBDevice == null) {
            SDCardUtils.saveMessageToSD_YJ("无打印设备");
//            mHandler.obtainMessage(Connect.FAILED).sendToTarget();
            if (deviceErrorCallBack != null)
                deviceErrorCallBack.noDevice();
            return;
        }
        myPrinter = PrinterInstance.getPrinterInstance(context, mUSBDevice, mHandler);
//        devicesName = mUSBDevice.getDeviceName();
//        devicesAddress = "vid: " + mUSBDevice.getVendorId() + "  pid: " + mUSBDevice.getProductId();
        UsbManager mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        if (mUsbManager.hasPermission(mUSBDevice)) {
            myPrinter.openConnection();
            SDCardUtils.saveMessageToSD_YJ("打印机连接成功");
        } else {
            // 没有权限询问用户是否授予权限
            SDCardUtils.saveMessageToSD_YJ("无USB权限");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
            filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
            context.registerReceiver(mUsbReceiver, filter);
            mUsbManager.requestPermission(mUSBDevice, pendingIntent); // 该代码执行后，系统弹出一个对话框
        }
    }

    @SuppressLint("NewApi")
    private void doDiscovery(UsbManager manager) {
        SDCardUtils.saveMessageToSD_YJ("开始搜索usb外设");
        HashMap<String, UsbDevice> devices = manager.getDeviceList();
        deviceList = new ArrayList();
        for (UsbDevice device : devices.values()) {
            SDCardUtils.saveMessageToSD_YJ("搜索打印机设备，名称：" + device.getDeviceName()
                    + "\n ProductId:" + device.getProductId()
                    + "\n VendorId:" + device.getVendorId()
            );
            if (USBPort.isUsbPrinter(device)) {
                SDCardUtils.saveMessageToSD_YJ("搜索到打印机设备，名称：" + device.getDeviceName()
                        + "\n ProductId:" + device.getProductId()
                        + "\n VendorId:" + device.getVendorId()
                );
                deviceList.add(device);
            }
        }
    }

    /**
     * 监听用户授权usb连接的广播
     */
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @SuppressLint("NewApi")
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.w("sss", "receiver action: " + action);

            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    context.unregisterReceiver(mUsbReceiver);
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
                            && mUSBDevice.equals(device)) {
                        myPrinter.openConnection();
                        SDCardUtils.saveMessageToSD_YJ("打印机连接成功");
                    } else {
                        mHandler.obtainMessage(PrinterConstants.Connect.FAILED).sendToTarget();
                        SDCardUtils.saveMessageToSD_YJ("打印机连接失败，权限被拒绝");
                        Log.e(TAG, "permission denied for device " + device);
                    }
                }
            }
        }
    };


    @Override
    public void checkPrintState(final IPrintConnectedState printConnectedState) {
        if (isConnected) {
            new Thread(new Runnable() {
                private String strStatus;

                public void run() {
                    int i = myPrinter.getCurrentStatus();
                    if (i == 0) {
//                        myPrinter.initPrinter();//状态正常，初始化打印机
                        strStatus = "打印机状态正常";
                        printConnectedState.stateNormal();
                    } else if (i == -1) {
                        strStatus = "接收数据失败";
                        printConnectedState.receiveStateMsgError();
                    } else if (i == -2) {
                        strStatus = "打印机缺纸";
                        printConnectedState.noPaper();
                    } else if (i == -3) {
                        strStatus = "打印机纸将尽";
                        printConnectedState.paperWillDo();
                    } else if (i == -4) {
                        strStatus = "打印机开盖";
                    } else if (i == -5) {
                        strStatus = "发送数据失败";
                        printConnectedState.sendStateMsgError();
                    }
                }
            }).start();
        } else {

        }
    }

    @Override
    public void disConnect() {
        if (myPrinter != null) {
            myPrinter.closeConnection();
            myPrinter = null;
        }
    }

    @Override
    public void pagerHalfCut() {
        if (isConnected) {
            new Thread(new Runnable() {
                public void run() {
                    myPrinter.cutPaper(66, 50);
                }
            }).start();

        }
    }

    @Override
    public Object getDevices() {
        if (isConnected)
            return myPrinter;
        return null;
    }

    @Override
    public int getPrintPaperSize() {
        return Constans_PrintLib.printPaperSize80;//该款打印机宽度 80
    }
}
