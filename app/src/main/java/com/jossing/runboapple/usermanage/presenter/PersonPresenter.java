package com.jossing.runboapple.usermanage.presenter;

import android.content.Context;
import android.util.Log;

import com.jossing.runboapple.usermanage.view.IPersonActivity;
import com.jossing.runboapple.usermanage.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Somnus on 2017/4/2.
 */

public class PersonPresenter implements IPersonPresenter {
    private IPersonActivity iPersonActivity;

    public PersonPresenter(IPersonActivity iPersonActivity){
        this.iPersonActivity = iPersonActivity;
    }

    @Override
    public void getCurrentUserInfo(Context context) {
        User currentUser = BmobUser.getCurrentUser(context, User.class);
        if (currentUser != null) {
            String username = currentUser.getUsername();
            String nick = currentUser.getNick();
            String sex = currentUser.getSex();
            BmobFile avatar = currentUser.getAvatar();
            iPersonActivity.showUserInfo(username, nick, sex, avatar);
        } else {
            iPersonActivity.getUserInfoFailure("还未登录");
        }
    }

    @Override
    public void getUserInfo(User user) {
        String username = user.getUsername();
        String nick = user.getNick();
        String sex = user.getSex();
        BmobFile avatar = user.getAvatar();
        iPersonActivity.showUserInfo(username, nick, sex, avatar);
    }

    @Override
    public void getUserInfo(Context context, String objectId) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list.size() != 0) {
                    getUserInfo(list.get(0));
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("获取用户信息失败", "(" + i + ")" + s);
                iPersonActivity.getUserInfoFailure("(" + i + ")" + s);
            }
        });
    }
}
