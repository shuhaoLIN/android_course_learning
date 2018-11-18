package com.example.shiyan8;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.shiyan72.R;

/**
 * Created by lenovo on 2018/10/31.
 */
public class MapFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View contentView = inflater.inflate(R.layout.fragment_map, container, false);

        return contentView;
    }
}
