package com.cx.bottom_navigation_bar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<BottomNavigationItem> data = new ArrayList<>();
    private MyFragment my1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my1 = new MyFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.search_edit_frame, my1);
        ft.commit();
        BottomNavigationItem b1 = (BottomNavigationItem) findViewById(R.id.bni1);
        BottomNavigationItem b2 = (BottomNavigationItem) findViewById(R.id.bni2);
        BottomNavigationItem b3 = (BottomNavigationItem) findViewById(R.id.bni3);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        data.add(b1);
        data.add(b2);
        data.add(b3);

    }

    private void setClick(int postion) {
        for (int i = 0; i < data.size(); i++) {
            if (i == postion) {
                data.get(i).setClick(true);
            } else {
                data.get(i).setClick(false);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bni1:
                setClick(0);
                my1.getTv().setText("fragment1");
                break;
            case R.id.bni2:
                setClick(1);
                my1.getTv().setText("fragment2");
                break;
            case R.id.bni3:
                setClick(2);
                my1.getTv().setText("fragment3");
                break;
        }


    }
}
