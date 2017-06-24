package com.jossing.runboapple.main.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.comment.model.Comment;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.model.ApplePicture;
import com.jossing.runboapple.main.view.IAppleDetailedActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
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
    public void queryApple(final Context context, String appleId) {
        BmobQuery<Apple> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", appleId);
        query.include("seller");
        query.findObjects(context, new FindListener<Apple>() {

            @Override
            public void onSuccess(List<Apple> list) {
                activity.onQueryAppleDone(list.get(0));
            }

            @Override
            public void onError(int i, String s) {
                Log.e("获取苹果信息失败", "(" + i + ")" + s);
                RunboAppleApp.toastShow(context, "(" + i + ")" + s, Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void queryPictureList(final Context context, Apple apple) {
        BmobQuery<ApplePicture> query = new BmobQuery<>();
        query.addWhereEqualTo("apple", apple);
        query.findObjects(context, new FindListener<ApplePicture>() {

            @Override
            public void onSuccess(List<ApplePicture> list) {
                activity.onQueryPictureListDone(list);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("获取图片列表失败", "(" + i + ")" + s);
                RunboAppleApp.toastShow(context, "(" + i + ")" + s, Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void queryCommentCount(final Context context, String appleId) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("apple", appleId);
        query.count(context, Comment.class, new CountListener() {
            @Override
            public void onSuccess(int i) {
                activity.onQueryCommentCountDone(i);
            }

            @Override
            public void onFailure(int i, String s) {
                activity.onQueryCommentCountDone(-1);
                Log.e("query comment count", "(" + i + ") " + s);
                RunboAppleApp.toastShow(context, "(" + i + ") " + s, Toast.LENGTH_LONG);
            }
        });
    }
}
