package com.jossing.runboapple.usermanage.model;

import android.content.Context;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Somnus on 2017/3/30.
 * 用户类
 */

public class User extends BmobUser{
    private BmobFile avatar;
    private String nick;
    private String sex;

    @Override
    public void setUsername(String username) {
        nick = username; // 默认将用户名作为昵称
        super.setUsername(username);
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public String getNick() {
        return nick;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public void update(Context context, UpdateListener listener) {
        String checkResult = checkData();
        if (checkResult.equals("合法")) {
            super.update(context, listener);
        } else {
            listener.onFailure(-1, checkResult);
        }

    }

    @Override
    public void signUp(Context context, SaveListener listener) {
        String checkResult = checkData();
        if (checkResult.equals("合法")) {
            super.signUp(context, listener);
        } else {
            listener.onFailure(-1, checkResult);
        }
    }

    /**
     * 检查用户信息合法性
     */
    private String checkData() {
        String checkUsernameResult = checkUsername();
        if (!checkUsernameResult.equals("合法")) {
            return checkUsernameResult;
        }
        String checkSexResult = checkSex();
        if (!checkSexResult.equals("合法")) {
            return checkSex();
        }
        return "合法";
    }

    /**
     * 检查性别
     */
    private String checkSex() {
        if (sex == null) {
            sex = "未指定";
        } else if (!sex.equals("男") && !sex.equals("女") && !sex.equals("其他")) {
            return "性别应为 男、女、其他";
        }
        return "合法";
    }

    /**
     * 检查用户名
     */
    private String checkUsername() {
        if (getUsername().length() < 3) {
            return "账号长度小于 3 ";
        }
        if (getUsername().length() > 10) {
            return "账号长度大于 10 ";
        }
        return "合法";
    }
}
