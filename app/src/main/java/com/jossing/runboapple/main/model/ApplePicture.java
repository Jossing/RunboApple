package com.jossing.runboapple.main.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author Jossing , Create on 2017/3/31
 */

public class ApplePicture extends BmobObject {
    private BmobFile picture;
    private Apple apple;

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }


    public BmobFile getPicture() {
        return picture;
    }

    public Apple getApple() {
        return apple;
    }
}
