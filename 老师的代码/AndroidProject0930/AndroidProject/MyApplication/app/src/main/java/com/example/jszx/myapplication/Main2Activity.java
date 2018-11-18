package com.example.jszx.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    Button buttonPreviews,buttonNext;
    ImageView myImage;
    int[] arrayImageIDs;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myImage = (ImageView) this.findViewById(R.id.image_content);
        arrayImageIDs=new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4};
        myImage.setImageDrawable(getResources().getDrawable(arrayImageIDs[index]));

        buttonPreviews= (Button) this.findViewById(R.id.buttonPreviews);
        buttonPreviews.setOnClickListener(new Main2Activity.mPreviewsClick());
        buttonNext= (Button) this.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new Main2Activity.mNextClick());
    }
    class mPreviewsClick implements View.OnClickListener {
        public void onClick(View v) {
            index=(index-1+arrayImageIDs.length)%arrayImageIDs.length;
            myImage.setImageDrawable(getResources().getDrawable(arrayImageIDs[index]));
        }
    }
    class mNextClick implements View.OnClickListener {
        public void onClick(View v) {
            index=(index+1)%arrayImageIDs.length;
            myImage.setImageDrawable(getResources().getDrawable(arrayImageIDs[index]));
        }
    }
}
