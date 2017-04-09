package com.jossing.runboapple.usermanage.presenter;


import android.content.Context;
import android.util.Log;

import com.jossing.runboapple.usermanage.model.User;
import com.jossing.runboapple.usermanage.view.IUserManageActivity;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Somnus on 2017/3/30.
 */

public class UserManagePresenter implements IUserManagePresenter {
    private IUserManageActivity iUserManageActivity;

    public UserManagePresenter(IUserManageActivity iLogin){
        this.iUserManageActivity = iLogin;
    }

    @Override
    public void login(Context context,String username,String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                iUserManageActivity.loginSuccess();
            }
            @Override
            public void onFailure(int i, String s) {
                Log.w("error","(" + i + ")" + s);
                iUserManageActivity.loginFailure("(" + i + ")" + s);
            }
        });
    }

    @Override
    public void register(final Context context, String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                iUserManageActivity.registerSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.w("error", "(" + i + ")" + s);
                iUserManageActivity.registerFailure("(" + i + ")" + s);
            }
        });
    }
}
