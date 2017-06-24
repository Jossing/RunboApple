package com.jossing.runboapple.order.presenter;

import android.content.Context;

import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.order.model.Address;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Somnus on 2017/4/9.
 */

public interface IOrderPresenter {
    void search(Context context);

    void buy(Context context, Address address, Apple apple, Integer count, String note, BmobDate date, Double totalPrice);

    void sendDate(int year, int month, int day);
    void sendNumber(Integer number);
    void sendTotalPrice(Double totalPrice);
}
