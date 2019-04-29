package com.sinochem.printlib.Interface;

/**
 * Created by ShengS on 2018/8/16.
 * 打印机功能类
 */

public interface IPrintFunction {
    /**
     * 打印文本内容
     */
    void printTextContent(String content);

    /**
     * 打印文本内容
     */
    void printBytesContent(byte[] bytes);

    /**
     * 打印二维码
     *
     * @param msg 二维码内容
     */
    void printQRCode(String msg);

    /**
     * 打印表格（商品详情）
     */
    void printTable();

    /**
     * 打印分割线
     */
    void printCut_off_line();

    /**
     * 打印双分割线
     */
    void print2Cut_off_line();

    /**
     * 设置字体大小，默认2倍宽高
     */
    void setPrintFontSize();

    /**
     * 设置默认字体宽高设置
     */
    void setDefultPrintFontSize();

    /**
     * 设置文字居中
     */
    void setFontMiddle();

    /**
     * 设置文字左对齐
     */
    void setFontLeft();

    /**
     * 设置文字右对齐
     */
    void setFontRight();

}
