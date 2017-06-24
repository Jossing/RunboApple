package com.jossing.runboapple.ordermanage.presenter;

import android.content.Context;

import com.jossing.runboapple.usermanage.model.User;

/**
 * @author Jossing , Create on 2017/4/16
 */

public interface IMyOrderPresenter {

    /**
     * 根据买家 id 或卖家 id 查询订单
     * @param seller_buyer "seller" or "bayer"
     */
    void queryOrders(Context context, String seller_buyer, User user);
}
