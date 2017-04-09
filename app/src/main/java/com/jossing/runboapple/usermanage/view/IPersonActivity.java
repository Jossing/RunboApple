package com.jossing.runboapple.usermanage.view;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2017/4/2.
 */

public interface IPersonActivity {

    void getUserInfoFailure(String msg);

    void showUserInfo(String username, String nick, String sex, BmobFile avatar);
}
