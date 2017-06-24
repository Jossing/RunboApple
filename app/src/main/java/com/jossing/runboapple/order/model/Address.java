package com.jossing.runboapple.order.model;

import com.jossing.runboapple.usermanage.model.User;

import cn.bmob.v3.BmobObject;

/**
 * Created by Somnus on 2017/4/13.
 */

public class Address extends BmobObject {
    // 用户
    private User user;
    // 联系名字
    private String nick;
    // 用户联系电话
    private String phone;
    // 用户联系地址
    private String address;
    // 地址选中
    private Boolean select;


    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
