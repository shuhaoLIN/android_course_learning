package com.example.chapter3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//
//            }
//        });
        button.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.edit_text);
        imageView = (ImageView)findViewById(R.id.iamge_view);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                String edit = editText.getText().toString();
                if(edit.length() == 0){
                    Toast.makeText(MainActivity.this,"文本框没有输入",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(MainActivity.this,edit,Toast.LENGTH_SHORT).show();

                //我要实现的是判断哪一个图片，然后再换成其他图片
//                imageView.setImageResource(R.drawable.p_3);
//                if(progressBar.getVisibility() == View.VISIBLE){
//                    progressBar.setVisibility(View.INVISIBLE);
//                }else if(progressBar.getVisibility() == View.INVISIBLE){
//                    progressBar.setVisibility(View.VISIBLE);
//                }
                int progress = progressBar.getProgress();
                progress = progress + 10;
                progressBar.setProgress(progress);

//                Intent intent = new Intent(MainActivity.this,progressBars.class);
//                startActivity(intent);

                //怎么实现点否定的不会消失
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("重要通知！！！");
                dialog.setMessage("你爱我对不对？");
                dialog.setPositiveButton("对，超级爱你", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"爱你么么哒",Toast.LENGTH_SHORT).show();
                    }
                });
//                dialog.setNegativeButton("滚，不爱你", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
                dialog.show();
//                dialog.setCancelable(true);
                break;
            default:
                break;
        }

    }
}
