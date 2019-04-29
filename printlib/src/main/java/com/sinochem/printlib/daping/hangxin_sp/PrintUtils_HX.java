package com.sinochem.printlib.daping.hangxin_sp;

import android.util.Log;

import com.printer.sdk.PrinterConstants;
import com.printer.sdk.PrinterInstance;
import com.sinochem.printlib.Interface.IPrint;
import com.sinochem.printlib.Interface.IPrintFunction;

import static android.content.ContentValues.TAG;

/**
 * Created by ShengS on 2018/8/16.
 * 打印机功能
 */

public class PrintUtils_HX implements IPrintFunction {

    private final PrinterInstance mPrinter;//打印机实力对象

    public PrintUtils_HX(IPrint print) {//IPrint这里传具体实现类

        mPrinter = (PrinterInstance) print.getDevices();
        if (mPrinter != null) {

            mPrinter.initPrinter();//初始化打印机
        } else {
            Log.e(TAG, "PrintUtils_HX: mrinter is null");
        }
    }

    @Override
    public void printTextContent(String content) {
        mPrinter.printText(content + "\r\n");
    }

    @Override
    public void printBytesContent(byte[] bytes) {
        mPrinter.sendBytesData(bytes);
    }

    @Override
    public void printQRCode(String msg) {

    }

    @Override
    public void printTable() {

    }

    @Override
    public void printCut_off_line() {
        mPrinter.printText("-----------------------------" + "\r\n");
    }

    @Override
    public void print2Cut_off_line() {

    }

    @Override
    public void setPrintFontSize() {
        if (mPrinter != null) {
            mPrinter.setFont(0, 1, 1, 0, 0);
        } else {
            Log.e(TAG, "setPrintFontSize PrintUtils_HX: mrinter is null");
        }
    }

    @Override
    public void setDefultPrintFontSize() {
        mPrinter.setFont(0, 0, 0, 0, 0);
    }

    @Override
    public void setFontMiddle() {
        if (mPrinter != null) {
            mPrinter.setPrinter(PrinterConstants.Command.ALIGN, PrinterConstants.Command.ALIGN_CENTER);
        } else {
            Log.e(TAG, "PrintUtils_HX: mrinter is null");
        }

    }

    @Override
    public void setFontLeft() {
        if (mPrinter != null) {
            mPrinter.setPrinter(PrinterConstants.Command.ALIGN, PrinterConstants.Command.ALIGN_LEFT);
        } else {
            Log.e(TAG, "PrintUtils_HX: mrinter is null");
        }
    }

    @Override
    public void setFontRight() {
        if (mPrinter != null) {
            mPrinter.setPrinter(PrinterConstants.Command.ALIGN, PrinterConstants.Command.ALIGN_RIGHT);
        } else {
            Log.e(TAG, "PrintUtils_HX: mrinter is null");
        }
    }
}
