package com.jossing.runboapple.order.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.order.model.Address;
import com.jossing.runboapple.order.model.Order;
import com.jossing.runboapple.order.view.IOrderActivity;
import com.jossing.runboapple.usermanage.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Somnus on 2017/4/9.
 */

public class OrderPresenter implements IOrderPresenter {
    private IOrderActivity iOrderActivity;

    public OrderPresenter(IOrderActivity iOrderActivity){
        this.iOrderActivity = iOrderActivity;
    }

    @Override
    public void search(final Context context) {
        User currentUser = BmobUser.getCurrentUser(context, User.class);
        BmobQuery<Address> query = new BmobQuery<Address>();
        query.addWhereEqualTo("user",currentUser.getObjectId());
        query.addWhereEqualTo("select", true);
        query.findObjects(context,new FindListener<Address>() {
            @Override
            public void onSuccess(List<Address> list) {
                for (Address address : list) {
                    iOrderActivity.Select_Address(address);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"获取失败"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void buy(final Context context, Address address, final Apple apple,
                    final Integer count, String note, BmobDate date, Double totalPrice) {

        User currentUser = BmobUser.getCurrentUser(context, User.class);
        if (currentUser == null) {
            iOrderActivity.buy_fail("你好像还没有登录？");
            return;
        }

        Order order = new Order();
        order.setBuyer(currentUser);
        order.setAddress(address);
        order.setApple(apple);
        order.setNote(note);
        order.setCount(count);
        order.setDate(date);
        order.setTotalPrice(totalPrice);
        order.setSeller(apple.getSeller());
        order.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                // 更新 apple 剩余数量
                updateAppleCount(context, apple, (apple.getCount() - count));
            }

            @Override
            public void onFailure(int i, String s) {
                Log.w("下单失败", "(" + i + ") " + s);
                iOrderActivity.buy_fail("(" + i + ") " + s);
            }
        });
    }

    @Override
    public void sendDate(int year, int month, int day) {
        iOrderActivity.sendData(year,month,day);
    }

    @Override
    public void sendNumber(Integer number) {
        iOrderActivity.sendNumber(number);
    }

    @Override
    public void sendTotalPrice(Double totalPrice) {
        iOrderActivity.sendTotalPrice(totalPrice);
    }


    private void updateAppleCount(Context context, Apple apple, Integer newCount) {
        apple.setCount(newCount);
        apple.update(context, new UpdateListener() {

            @Override
            public void onSuccess() {
                iOrderActivity.buy_Success();
            }

            @Override
            public void onFailure(int i, String s) {
                iOrderActivity.buy_fail("(" + i + ") " + s);
            }
        });
    }
}
