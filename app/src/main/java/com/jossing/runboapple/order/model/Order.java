package com.jossing.runboapple.order.model;

import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.usermanage.model.User;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Somnus on 2017/4/13.
 */

public class Order extends BmobObject {

    // 买家
    private User buyer;
    // 卖家
    private User seller;
    // 送货地址
    private Address address;
    // 所选苹果
    private Apple apple;
    // 苹果数量
    private Integer count;
    // 买家备注
    private String note;
    // 预期送达时间（配送时间）
    private BmobDate date;
    // 订单总额
    private Double totalPrice;

    private Boolean done;




    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Apple getApple() {
        return apple;
    }

    public User getSeller() {
        return seller;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getDone() {
        return done;
    }
}
