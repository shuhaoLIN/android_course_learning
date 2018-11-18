package com.example.mytesthandle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView)findViewById(R.id.showMessage);
        Button button = (Button)findViewById(R.id.getMessage);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();

                //获取整数
                int num2 = msg.what;
                textView.append("整数:"+num2+"\n");

                //获取整数加上字符串
                int num = bundle.getInt("Int");
                String getStr = bundle.getString("Str");
                textView.append("整数加上字符串"+num+","+getStr+"\n");
                //获取复杂对象
                sendMess ms =(sendMess) bundle.getSerializable("ms");
                if (ms == null) Toast.makeText(MainActivity.this,"null",Toast.LENGTH_LONG).show();
                textView.append("获取到的复杂对象的数据：ms：学号为："+ms.getNum()+",姓名为："+ms.getName()+",专业为："+ms.getMajor());



            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetMessage(handler);
            }
        });


    }
    public void GetMessage(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                    //发送int消息
                    //handler.sendEmptyMessage(1);

//                    Message msg = Message.obtain();
//                    Bundle bundle = new Bundle();
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();

                    //发送int加字符串
                    bundle.putString("Str", "这是发送过来的字符串");
                    bundle.putInt("Int", 255);



                    //发送对象
                    int num = 12345;
                    String name = "LIN";
                    String major = "软件工程";
                    sendMess ms = new sendMess();
                    ms.setNum(num);
                    ms.setName(name);
                    ms.setMajor(major);
                    bundle.putSerializable("ms",ms);

                    //发出上面的对象
                    msg.setData(bundle);
                    handler.sendMessage(msg);

            }
        }).start();
    }

    class sendMess implements Serializable {
        private String name;
        private int num;
        private String major;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }
    }
}
