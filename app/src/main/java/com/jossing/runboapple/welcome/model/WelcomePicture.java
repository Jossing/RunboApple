package com.jossing.runboapple.welcome.model;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author Jossing , Create on 2017/3/30
 */

public class WelcomePicture extends BmobObject {
    private BmobFile picture;

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getPictureURL(Context context) {
        return picture.getFileUrl(context);
    }
}
