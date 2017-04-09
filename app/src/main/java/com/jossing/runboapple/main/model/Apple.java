package com.jossing.runboapple.main.model;

import android.content.Context;

import com.jossing.runboapple.usermanage.model.User;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 商品信息类
 * @author Jossing , Create on 2017/3/16
 */

public class Apple extends BmobObject {
    // 商品名称，要求长度大于 2
    private String name;
    // 品质，A、B、C、D
    private String quality;
    // 产地
    private String address;
    // 特征、描述
    private String description;
    // 供货数量，要求大于 0
    private Integer count;
    // 商品价格
    private Double price;
    // 卖家
    private User seller;
    // 图片
    private BmobFile picture;


    public void setName(String name) {
        this.name = name;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getQuality() {
        return quality;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCount() {
        return count;
    }

    public Double getPrice() {
        return price;
    }

    public User getSeller() {
        return seller;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public String getPictureURL(Context content) {
        return picture.getFileUrl(content);
    }
}
