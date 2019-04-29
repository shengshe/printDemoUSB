package com.example.sheng.printdemo_usb53;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sinochem.printlib.Interface.IPrintConnectedState;
import com.sinochem.printlib.Interface.PrintDeviceErrorCallBack;
import com.sinochem.printlib.daping.hangxin_sp.PrintUtils_HX;
import com.sinochem.printlib.daping.hangxin_sp.USBPrintDevices_HX;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private PrintUtils_HX printUtils_hx;


    //航行打印机
    USBPrintDevices_HX printDevicesHx = new USBPrintDevices_HX(this, new PrintDeviceErrorCallBack() {
        @Override
        public void onConnectSucess() {
            Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onConnectFailed() {
            Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onConnectClose() {
            Toast.makeText(MainActivity.this, "连接关闭", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void noDevice() {
            Toast.makeText(MainActivity.this, "未检测到打印机设备", Toast.LENGTH_SHORT).show();
        }
    });
    //打印机连接状态，在调用检测时走
    IPrintConnectedState printConnectedState = new IPrintConnectedState() {
        @Override
        public void stateNormal() {
            Toast.makeText(MainActivity.this, "打印机正常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void stateError() {
            Toast.makeText(MainActivity.this, "打印机异常", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void noPaper() {
            Toast.makeText(MainActivity.this, "打印机无纸", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void paperWillDo() {
            Toast.makeText(MainActivity.this, "打印机纸将近", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void sendStateMsgError() {
            Toast.makeText(MainActivity.this, "发送状态监测指令失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void receiveStateMsgError() {
            Toast.makeText(MainActivity.this, "接受状态监测指令失败", Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bugly.init(getApplicationContext(), "380e2cadc0", true);
        Beta.checkUpgrade(false,false);


    }

    //连接打印机
    public void connect(View view) {
        printDevicesHx.initPrintDevice();//连接打印机
        printUtils_hx = new PrintUtils_HX(printDevicesHx);//创建打印类并初始化
    }

    //连接状态检测
    public void connectState(View view) {

        printDevicesHx.checkPrintState(printConnectedState);
    }

    //断开连接
    public void disConnect(View view) {
        printDevicesHx.disConnect();
    }

    //打印
    public void print(View view) {
        printUtils_hx.setPrintFontSize();
        printUtils_hx.setFontMiddle();
        printUtils_hx.printTextContent("打印机测试title");
        printUtils_hx.setPrintFontSize();
        printUtils_hx.setFontLeft();
        printUtils_hx.printTextContent("打印内容测试：\n"
                + "当前时间："
                + new SimpleDateFormat("yyyy-MM-dd HHmmss").format(System.currentTimeMillis())
                + "油站名称：山东高速XXX站"
        );
        printUtils_hx.printCut_off_line();//分割线
        printUtils_hx.setFontMiddle();
        printUtils_hx.printTextContent("谢谢惠顾，欢迎下次光临！");
        printUtils_hx.printTextContent(new SimpleDateFormat("yyyy-MM-dd HHmmss").format(System.currentTimeMillis()));

        printDevicesHx.pagerHalfCut();//半切纸
    }
}
