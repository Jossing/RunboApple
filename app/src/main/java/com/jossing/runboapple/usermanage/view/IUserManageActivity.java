package com.jossing.runboapple.usermanage.view;

/**
 * Created by Somnus on 2017/3/30.
 */

public interface IUserManageActivity {
    void loginSuccess();
    void loginFailure(String s);
    void registerSuccess();
    void registerFailure(String s);
}
