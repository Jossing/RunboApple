package com.jossing.runboapple.welcome.presenter;

import android.content.Context;
import android.widget.Toast;

import com.jossing.runboapple.welcome.model.WelcomePicture;
import com.jossing.runboapple.welcome.view.IWelcomeActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Jossing , Create on 2017/3/30
 */

public class WelcomePresenter implements IWelcomePresenter {
    private IWelcomeActivity activity;

    public WelcomePresenter(IWelcomeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showWelcomePicture(final Context context) {
        BmobQuery<WelcomePicture> query = new BmobQuery<>();
        query.setLimit(1);
        query.findObjects(context, new FindListener<WelcomePicture>() {
            @Override
            public void onSuccess(List<WelcomePicture> list) {
                WelcomePicture welcomePicture = list.get(0);
                activity.showWelcomePicture(welcomePicture.getPictureURL(context));
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context, s + "(" + i + ")", Toast.LENGTH_LONG).show();
            }
        });
    }
}
