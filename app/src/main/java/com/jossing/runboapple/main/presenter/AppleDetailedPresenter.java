package com.jossing.runboapple.main.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.model.ApplePicture;
import com.jossing.runboapple.main.view.IAppleDetailedActivity;

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
                Toast.makeText(context, "(" + i + ")" + s, Toast.LENGTH_LONG).show();
            }
        });
    }
}
