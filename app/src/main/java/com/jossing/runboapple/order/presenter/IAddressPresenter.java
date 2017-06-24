package com.jossing.runboapple.order.presenter;

import android.content.Context;

/**
 * Created by Somnus on 2017/4/13.
 */

public interface IAddressPresenter {
    void loadAddressList(Context context);
    void setSelect(Context context, String objectNew, String objectOld);
    void Select(Context context, String object);
    void addAddress(String nick, String phone, String address, Context context);
    void delAddress(Context context, String object);
}
