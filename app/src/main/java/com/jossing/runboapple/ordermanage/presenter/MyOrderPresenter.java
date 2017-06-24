package com.jossing.runboapple.ordermanage.presenter;

import android.content.Context;
import android.util.Log;

import com.jossing.runboapple.order.model.Order;
import com.jossing.runboapple.ordermanage.view.IMyOrderActivity;
import com.jossing.runboapple.usermanage.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Jossing , Create on 2017/4/16
 */

public class MyOrderPresenter implements IMyOrderPresenter {

    private IMyOrderActivity activity;

    public MyOrderPresenter(IMyOrderActivity activity) {
        this.activity = activity;
    }

    @Override
    public void queryOrders(Context context, final String seller_buyer, User user) {
        if (!seller_buyer.equals("seller") && !seller_buyer.equals("buyer")) {
            Log.e("query orders", "参数错误");
            activity.onQueryOrdersDone(null, "参数错误", seller_buyer);
            return;
        }
        BmobQuery<Order> query = new BmobQuery<>();
        query.setLimit(1000);
        query.include("seller,buyer,apple,address");
        query.order("-createAt");
        query.addWhereEqualTo(seller_buyer, user);
        query.findObjects(context, new FindListener<Order>() {
            @Override
            public void onSuccess(List<Order> list) {
                activity.onQueryOrdersDone(list, "", seller_buyer);
            }

            @Override
            public void onError(int i, String s) {
                activity.onQueryOrdersDone(null, "(" + i + ")" + s, seller_buyer);
            }
        });
    }
}
