package com.jossing.runboapple.usermanage.presenter;

import android.content.Context;

import com.jossing.runboapple.usermanage.model.User;

/**
 * Created by Somnus on 2017/4/2.
 */

public interface IPersonPresenter {


    void getCurrentUserInfo(Context context);

    void getUserInfo(User user);

    void getUserInfo(Context context, String objectId);
}
