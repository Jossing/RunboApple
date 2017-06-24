package com.jossing.runboapple.usermanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.main.view.MyPostedActivity;
import com.jossing.runboapple.ordermanage.view.MyOrderActivity;
import com.jossing.runboapple.settings.view.AboutActivity;
import com.jossing.runboapple.settings.view.SettingsActivity;
import com.jossing.runboapple.usermanage.model.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author Jossing , Create on 2017/3/18
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    ImageView imvBg;
    ImageView imvAvatar;
    TextView tvName;
    TextView tvOrder;
    TextView tvMyPosted;
    TextView tvSettings;
    TextView tvAbout;

    public enum RequestCode { LOGIN, LOGOUT }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        rootView = view;
        initWidget();

        showCurrentUserInfo();

        return view;
    }

    private void initWidget() {
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvOrder = (TextView) rootView.findViewById(R.id.tv_order);
        tvOrder.setOnClickListener(this);
        imvBg = (ImageView) rootView.findViewById(R.id.imv_bg);
        Glide.with(getActivity().getApplicationContext())
                .load(R.drawable.bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(android.R.color.darker_gray)
                .bitmapTransform(new BlurTransformation(getActivity(), 25))
                .into(imvBg);
        imvAvatar = (ImageView) rootView.findViewById(R.id.imv_avatar);
        imvAvatar.setOnClickListener(this);
        tvMyPosted = (TextView) rootView.findViewById(R.id.tv_my_posted);
        tvMyPosted.setOnClickListener(this);
        tvSettings = (TextView) rootView.findViewById(R.id.tv_settings);
        tvSettings.setOnClickListener(this);
        tvAbout = (TextView) rootView.findViewById(R.id.tv_about);
        tvAbout.setOnClickListener(this);
    }

    /**
     * 展示用户信息，头像和用户名
     */
    private void showCurrentUserInfo() {
        User currentUser = BmobUser.getCurrentUser(getActivity(), User.class);
        if (currentUser != null) {
            tvName.setText(currentUser.getUsername());
            BmobFile avatar = currentUser.getAvatar();
            if (avatar != null) {
                Glide.with(getActivity().getApplicationContext())
                        .load(avatar.getFileUrl(getActivity()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.def_avatar_red)
                        .bitmapTransform(new CropCircleTransformation(getActivity()))
                        .into(imvAvatar);
            }
        } else {
            imvAvatar.setImageResource(R.drawable.def_avatar_red);
            tvName.setText(getResources().getText(R.string.mine_fragment_login));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_avatar: // 头像图标
                User currentUser1 = BmobUser.getCurrentUser(getActivity(), User.class);
                if (currentUser1 != null) {
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("isCurrentUser", true);
                    startActivityForResult(intent, RequestCode.LOGOUT.ordinal());
                } else {
                    Intent intent = new Intent(getActivity(), UserManageActivity.class);
                    startActivityForResult(intent, RequestCode.LOGIN.ordinal());
                }
                break;
            case R.id.tv_order:
                User currentUser3 = BmobUser.getCurrentUser(getActivity(), User.class);
                if (currentUser3 == null) {
                    RunboAppleApp.toastShow(getActivity(), "请先登录哦~", Toast.LENGTH_SHORT);
                } else {
                    startActivity(new Intent(getActivity(), MyOrderActivity.class));
                }
                break;
            case R.id.tv_my_posted: // 我发布的 按钮
                User currentUser2 = BmobUser.getCurrentUser(getActivity(), User.class);
                if (currentUser2 != null) {
                    Intent intent = new Intent(getActivity(), MyPostedActivity.class);
                    intent.putExtra("isCurrentUser", true);
                    startActivityForResult(intent, RequestCode.LOGOUT.ordinal());
                } else {
                    Intent intent = new Intent(getActivity(), UserManageActivity.class);
                    startActivityForResult(intent, RequestCode.LOGIN.ordinal());
                    RunboAppleApp.toastShow(getActivity(), "请先登录哦~", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.tv_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.tv_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.LOGIN.ordinal()) {
            if (resultCode == UserManageActivity.ResultCode.LOGIN_SUCCESS.ordinal()) {
                showCurrentUserInfo();
            } else {
                // do noting
            }
        } else if (requestCode == RequestCode.LOGOUT.ordinal()) {
            if (resultCode == PersonActivity.ResultCode.LOGOUT_SUCCESS.ordinal()) {
                showCurrentUserInfo();
            } else {
                // do noting
            }
        }
    }
}
