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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");
        addEditText = (EditText)findViewById(R.id.add_edit_text);
        addEditText.setText(name);

        Button buttonSubmit = (Button) findViewById(R.id.submit_button);
        buttonSubmit.setOnClickListener(new ButtonSubmitClick());
    }
    private class ButtonSubmitClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("name",String.valueOf(addEditText.getText()));
            Log.i("text",String.valueOf(addEditText.getText()));
            setResult(RESULT_OK,intent);
            AddItemActivity.this.finish();
        }
    }
}
