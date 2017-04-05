package com.jossing.runboapple.main.presenter;

import android.content.Context;

/**
 * @author Jossing , Create on 2017/3/19
 */

public interface IAppleFGPresenter {

    /**
     * 进入界面首次获取苹果列表
     * @param context Context
     * @param limit 获取个数
     */
    void loadAppleList(Context context, Integer limit);
}
