package com.jossing.runboapple.main.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.RunboAppleApplication;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.model.ApplePicture;
import com.jossing.runboapple.main.view.IAppleDetailedActivity;
import com.jossing.runboapple.usermanage.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Jossing , Create on 2017/3/19
 */

public class AppleDetailedPresenter implements IAppleDetailedPresenter {
    private IAppleDetailedActivity activity;

    public AppleDetailedPresenter(IAppleDetailedActivity activity) {
        this.activity = activity;
    }

    @Override
    public void queryPictureList(final Context context, Apple apple) {
        BmobQuery<ApplePicture> query = new BmobQuery<>();
        query.addWhereEqualTo("apple", apple);
        query.findObjects(context, new FindListener<ApplePicture>() {

            @Override
            public void onSuccess(List<ApplePicture> list) {
                activity.onQueryPictureListSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("获取图片列表失败", "(" + i + ")" + s);
                RunboAppleApplication.toastShow(context, "(" + i + ")" + s, Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void querySeller(final Context context, String sellerId) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", sellerId);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list.size() != 0) {
                    activity.onQuerySellerSuccess(list.get(0));
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("获取卖家信息失败", "(" + i + ")" + s);
                RunboAppleApplication.toastShow(context, "(" + i + ")" + s, Toast.LENGTH_LONG);
            }
        });
    }
}
