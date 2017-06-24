package com.jossing.runboapple.order.view;

import com.jossing.runboapple.order.model.Address;

/**
 * Created by Somnus on 2017/4/9.
 */

public interface IOrderActivity {
    void sendData(int year, int month, int day);

    void buy_Success();
    void buy_fail(String s);
    void sendNumber(Integer number);
    void sendTotalPrice(Double totalPrice);
    void Select_Address(Address address);

}
