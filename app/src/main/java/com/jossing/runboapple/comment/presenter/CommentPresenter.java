package com.jossing.runboapple.comment.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.comment.model.Comment;
import com.jossing.runboapple.comment.view.ICommentActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Jossing , Create on 2017/4/14
 */

public class CommentPresenter implements ICommentPresenter {
    private ICommentActivity activity;

    public CommentPresenter(ICommentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void queryAppleComments(final Context context, String appleId) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.setLimit(1000);
        query.order("-createdAt");
        query.addWhereEqualTo("apple", appleId);
        query.include("author");
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                activity.onQueryAppleCommentsDone(list);
                if (list.size() == 0) {
                    RunboAppleApp.toastShow(context, "还没有用户评论哦~", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onError(int i, String s) {
                activity.onQueryAppleCommentsDone(null);
                Log.e("query apple comments", "(" + i + ") " + s);
                RunboAppleApp.toastShow(context, "(" + i + ") " + s, Toast.LENGTH_LONG);
            }
        });
    }
}
