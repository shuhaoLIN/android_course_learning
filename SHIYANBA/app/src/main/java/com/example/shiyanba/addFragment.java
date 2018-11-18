package com.example.shiyanba;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by lenovo on 2018/10/30.
 */
public class addFragment extends Fragment {
    private int layoutID;
    Button submit;
    EditText add_edit;
    public void setLayoutID(int layoutID){
        this.layoutID = layoutID;
    }

    /**
     * 返回一个view，使用layoutInflater将相应的layout
     * 载入形成相应的view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(layoutID,null);
        submit = (Button)view.findViewById(R.id.submit_button);
        add_edit = (EditText)view.findViewById(R.id.add_edit_text);
        String name = getArguments().getString("name");
        add_edit.setText(name);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
