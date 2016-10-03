package com.cx.bottom_navigation_bar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by CX on 2016/10/2.
 */

public class MyFragment extends Fragment {
    private TextView tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.myfragment,container,false);
       tv= (TextView) view.findViewById(R.id.tv);
        return view;
    }

    public TextView getTv() {
        return tv;
    }
}
