package com.example.shiyan72;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {
    private EditText addEditText;
    private EditText addEditTextPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");
        addEditText = (EditText)findViewById(R.id.add_edit_text);
        addEditTextPrice = (EditText)findViewById(R.id.add_edit_text_price);
        addEditText.setText(name);

        Button buttonSubmit = (Button) findViewById(R.id.submit_button);
        buttonSubmit.setOnClickListener(new ButtonSubmitClick());
    }
    private class ButtonSubmitClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            String[] text = {String.valueOf(addEditText.getText()),String.valueOf(addEditTextPrice.getText())};
            intent.putExtra("name",text);
            Log.i("text",text[0] + text[1]);
            setResult(RESULT_OK,intent);
            AddItemActivity.this.finish();
        }
    }
}
