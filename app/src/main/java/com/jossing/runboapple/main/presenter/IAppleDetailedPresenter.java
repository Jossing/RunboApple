package com.jossing.runboapple.main.presenter;

import android.content.Context;

import com.jossing.runboapple.main.model.Apple;

/**
 * @author Jossing , Create on 2017/3/19
 */

public interface IAppleDetailedPresenter {

    void queryApple(Context context, String appleId);

    void queryCommentCount(Context context, String appleId);

    void queryPictureList(Context context, Apple apple);
}
