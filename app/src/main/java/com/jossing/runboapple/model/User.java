package com.jossing.runboapple.model;

/**
 * @author Jossing , Create on 2017/3/16
 */

public class User {
    // 用户 ID
    private String id;
    // 用户名称，要求长度大于 4
    private String name;
    // 联系电话
    private String tel;
    // 联系地址
    private String address;
    // 备用地址
    private String secondaryAddress;

    public User(String id, String name) {
        this(id, name, "", "", "");
    }

    public User(String id, String name, String tel, String address, String secondaryAddress) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.secondaryAddress = secondaryAddress;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSecondaryAddress(String secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public String getSecondaryAddress() {
        return secondaryAddress;
    }
}
