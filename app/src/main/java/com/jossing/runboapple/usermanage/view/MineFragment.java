package com.jossing.runboapple.usermanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.usermanage.model.User;

import cn.bmob.v3.BmobUser;

/**
 * @author Jossing , Create on 2017/3/18
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    ImageView imvBg;
    TextView tvName;
    CardView cvAvatar;

    public enum RequestCode { LOGIN, LOGOUT }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        rootView = view;
        initWidget();
        return view;
    }

    private void initWidget() {
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        showCurrentUserName();
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

    private void showCurrentUserName() {
        User currentUser = BmobUser.getCurrentUser(getActivity(), User.class);
        if (currentUser != null) {
            tvName.setText(currentUser.getUsername());
        } else {
            tvName.setText(getResources().getText(R.string.mine_fragment_login));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_avatar:
                User currentUser = BmobUser.getCurrentUser(getActivity(), User.class);
                if (currentUser != null) {
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("isCurrentUser", true);
                    startActivityForResult(intent, RequestCode.LOGOUT.ordinal());
                } else {
                    Intent intent = new Intent(getActivity(), UserManageActivity.class);
                    startActivityForResult(intent, RequestCode.LOGIN.ordinal());
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.LOGIN.ordinal()) {
            if (resultCode == UserManageActivity.ResultCode.LOGIN_SUCCESS.ordinal()) {
                showCurrentUserName();
            } else {
                // do noting
            }
        } else if (requestCode == RequestCode.LOGOUT.ordinal()) {
            if (resultCode == PersonActivity.ResultCode.LOGOUT_SUCCESS.ordinal()) {
                showCurrentUserName();
            } else {
                // do noting
            }
        }
    }
}
