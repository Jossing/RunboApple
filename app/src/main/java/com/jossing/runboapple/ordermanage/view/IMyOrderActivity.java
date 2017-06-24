package com.jossing.runboapple.ordermanage.view;

import com.jossing.runboapple.order.model.Order;

import java.util.List;

/**
 * @author Jossing , Create on 2017/4/16
 */

public interface IMyOrderActivity {

    /**
     * 当查询订单完成
     * @param orders 订单列表，当查询出错时为 null
     * @param msg 错误消息，当查询成功时为 ""
     */
    void onQueryOrdersDone(List<Order> orders, String msg, String seller_buyer);
}
