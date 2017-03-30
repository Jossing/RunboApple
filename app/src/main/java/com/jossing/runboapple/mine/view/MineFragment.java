package com.jossing.runboapple.mine.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.login.LoginpopWindow;

/**
 * @author Jossing , Create on 2017/3/18
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    ImageView imvBg;
    CardView cvAvatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        rootView = view;
        initWidget();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initWidget() {
        imvBg = (ImageView) rootView.findViewById(R.id.imv_bg);
        Glide.with(this)
                .load(R.drawable.bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //让 Glide 既缓存全尺寸又缓存其他尺寸
                .placeholder(android.R.color.darker_gray)
                .centerCrop()
                .into(imvBg);
        cvAvatar = (CardView) rootView.findViewById(R.id.cv_avatar);
        cvAvatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_avatar:
                onShowLoginPop();
                break;
        }
    }

    private void onShowLoginPop() {
        final LoginpopWindow loginpopWindow = new LoginpopWindow(getActivity());
        backgroundAlpha(0.8f);
        loginpopWindow.showAtLocation(rootView, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
        loginpopWindow.setOnDismissListener(new poponDismissListener());
        ImageView login_im_close = (ImageView)loginpopWindow.getContentView().findViewById(R.id.login_close);
        TextView login_tx_register = (TextView)loginpopWindow.getContentView().findViewById(R.id.login_tx_register);
        final NestedScrollView nv_login = (NestedScrollView)loginpopWindow.getContentView().findViewById(R.id.login);
        final NestedScrollView nv_register = (NestedScrollView)loginpopWindow.getContentView().findViewById(R.id.register);
        ImageView register_im_close = (ImageView)loginpopWindow.getContentView().findViewById(R.id.register_close);
        ImageView register_im_back = (ImageView)loginpopWindow.getContentView().findViewById(R.id.register_back);

        final Animation animation_left_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
        final Animation animation_right_out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);

        register_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nv_login.startAnimation(animation_left_in);
                nv_register.startAnimation(animation_right_out);
                animation_left_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        nv_login.setVisibility(View.VISIBLE);
                        nv_register.setVisibility(View.GONE);

                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        login_im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginpopWindow.dismiss();
            }
        });
        register_im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginpopWindow.dismiss();
            }
        });
        login_im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginpopWindow.dismiss();
            }
        });

        final Animation animation_left_out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
        final Animation animation_right_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);

        login_tx_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nv_login.startAnimation(animation_left_out);
                nv_register.startAnimation(animation_right_in);
                animation_right_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        nv_login.setVisibility(View.GONE);
                        nv_register.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        nv_login.setVisibility(View.GONE);
                        nv_register.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }


    public class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
}
