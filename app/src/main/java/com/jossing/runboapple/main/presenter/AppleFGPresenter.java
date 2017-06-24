package com.jossing.runboapple.main.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.view.IAppleFragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Jossing , Create on 2017/3/19
 */

public class AppleFGPresenter implements IAppleFGPresenter {
    private IAppleFragment fragment;

    public AppleFGPresenter(IAppleFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void loadAppleList(final Context context, Integer limit) {
        BmobQuery<Apple> queryApple = new BmobQuery<>();
        queryApple.setLimit(limit);
        queryApple.order("-createdAt");
        queryApple.include("seller");
        queryApple.findObjects(context, new FindListener<Apple>() {
            @Override
            public void onSuccess(List<Apple> list) {
                Log.e("加载苹果列表", "成功。");
                fragment.onLoadAppleListSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("加载苹果列表", "失败：(" + i + ")" + s);
                Toast.makeText(context, "(" + i + ")" + s, Toast.LENGTH_LONG).show();
            }
        });
    }
}
