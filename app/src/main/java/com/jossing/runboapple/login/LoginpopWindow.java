package com.jossing.runboapple.login;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jossing.runboapple.R;

/**
 * Created by Somnus on 2017/3/19.
 */

public class LoginpopWindow extends PopupWindow {
    private View conentView;
    private NestedScrollView nestedScrollView_login,nestedScrollView_register;
    private CoordinatorLayout coordinatorLayout;
    int  defaultAlpha = 0x9a;

    public LoginpopWindow(final Activity context){
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.login_register,null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(conentView);
        this.setWidth(w);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(colorDrawable);
        this.setAnimationStyle(R.style.ImPopupLayoutAnimation);

        nestedScrollView_login = (NestedScrollView)conentView.findViewById(R.id.login);
        BottomSheetBehavior.from(nestedScrollView_login).setState(BottomSheetBehavior.STATE_EXPANDED);
        BottomSheetBehavior.from(nestedScrollView_login).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset == 0f){
                    LoginpopWindow.this.dismiss();
                }
            }
        });
        nestedScrollView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetBehavior.from(nestedScrollView_login).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        nestedScrollView_register = (NestedScrollView)conentView.findViewById(R.id.register);
        BottomSheetBehavior.from(nestedScrollView_register).setState(BottomSheetBehavior.STATE_EXPANDED);
        BottomSheetBehavior.from(nestedScrollView_register).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset == 0f){
                    LoginpopWindow.this.dismiss();
                }
            }
        });
        nestedScrollView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetBehavior.from(nestedScrollView_register).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });


    }

    private void setBackgroundAlpha(int Alpha){
        coordinatorLayout.setBackgroundColor(Color.argb(Alpha, 0, 0, 0));
    }

}