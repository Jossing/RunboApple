package com.jossing.runboapple.order.view;

import android.view.Menu;
import android.view.MenuInflater;

import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.order.model.Address;

import java.util.List;

/**
 * Created by Somnus on 2017/4/13.
 */

public interface IAddressActivity {


    void saveSuccess();
    void RefreshSuccess();
    void RefreshFail();
    void onLoadAddressListSuccess(List<Address> addressList);
    void sendNull(Integer size);
    void delSuccess();

}
