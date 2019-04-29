package com.sinochem.printlib;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 创建日期：2017/9/15 on 15:14
 * 描述:
 * 作者:sheng
 */
public class SDCardUtils {

    //保存硬件日志
    public static void saveMessageToSD_YJ(String text) {
        final String msg = "\n==>>\n" + text;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            ToastUtil.show(MyApplication.mContext,"保存日志");
                    try {
                        String parentName = MyTime.getTime1();
                        String fileName = MyTime.getTime2() + ".txt";//getTime2

                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                File.separator + "大屏日志信息-硬件" + File.separator + parentName + File.separator;
//                String path = "/sdcard/智能终端请求日志/";
//                String time = MyTime.geTime();
//                        String fileName = "requestPay.txt";
                        File dir = new File(path);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File file = new File(dir, fileName);
                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        FileOutputStream fos = null;
                        fos = new FileOutputStream(file, true);
                        fos.write(msg.getBytes());
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    //保存上传失败流水
    public static void saveOrderMessageToSD(String msg) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            ToastUtil.show(MyApplication.mContext,"保存日志");
            msg = MyTime.getTime_() + "\n" + msg;
            msg += "\n\n";
            try {
                String parentName = MyTime.getTime1();
                String fileName = MyTime.getTime2() + ".txt";//getTime2

                String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        File.separator + "智能终端流水" + File.separator + parentName + File.separator /*+ fileName*/;
//                String path = "/sdcard/智能终端请求日志/";
//                String time = MyTime.geTime();
//                String fileName = "lock_Unlock.txt";
                File parentFile = new File(path);
//                File parentFile = dir.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                File file = new File(parentFile, fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
//                FileOutputStream fos = null;
//                fos = new FileOutputStream(file, true);
//                fos.write(msg.getBytes("GBK"));
//                fos.close();

                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file, true), "GBK");
                BufferedWriter writer = new BufferedWriter(write);
                writer.write(msg);
                writer.newLine();

                writer.flush();
                writer.close();
                write.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
