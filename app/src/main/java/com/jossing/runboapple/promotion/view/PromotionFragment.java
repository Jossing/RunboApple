package com.jossing.runboapple.promotion.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jossing.runboapple.R;

/**
 * @author Jossing , Create on 2017/3/18
 */

public class PromotionFragment extends Fragment {
    private View rootView;
    public Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_promotion, container, false);
        initWidget();
        return rootView;
    }

    private void initWidget() {
        // 配置 toolbar
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("活动");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }
}
