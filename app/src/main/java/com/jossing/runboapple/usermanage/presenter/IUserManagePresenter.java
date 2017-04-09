package com.jossing.runboapple.usermanage.presenter;

import android.content.Context;

/**
 * Created by Somnus on 2017/3/30.
 */

public interface IUserManagePresenter {

    void login(Context context, String email, String password);
    void register(Context context, String email, String password);
}
