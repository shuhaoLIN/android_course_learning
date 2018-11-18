package com.example.test_baseadapter;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BActivity extends AppCompatActivity {

    private EditText editText;
    private ItemClass returnItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        final Intent intent = getIntent();
        final Bundle item = intent.getExtras();
        ItemClass itemClass = (ItemClass)item.getSerializable("itemClass");
        editText = (EditText)findViewById(R.id.edit_text);
        Button button = (Button)findViewById(R.id.submit_button);

        editText.setText(itemClass.getItemName()+" "+itemClass.getItemContent());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_return = new Intent();
                String[] returnText = editText.getText().toString().split(" ");
                if(returnText.length != 2){
                    Toast.makeText(BActivity.this,
                            "请确保输入有空格隔开！",Toast.LENGTH_LONG).show();

                }
                else {
                    returnItem = new ItemClass(returnText[0],returnText[1],  R.drawable.watermelon_pic);
                    intent_return.putExtra("data_return",returnItem);
                    setResult(RESULT_OK, intent_return);
                    finish();
                }

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent_return = new Intent();
            String[] returnText = editText.getText().toString().split(" ");
            if(returnText.length != 2){
                Toast.makeText(BActivity.this,
                        "请确保输入有空格隔开！",Toast.LENGTH_LONG).show();

            }
            else {
                returnItem = new ItemClass(returnText[0],returnText[1],  R.drawable.watermelon_pic);
                intent_return.putExtra("data_return",returnItem);
                setResult(RESULT_OK, intent_return);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}