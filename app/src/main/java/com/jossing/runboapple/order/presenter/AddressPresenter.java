package com.jossing.runboapple.order.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.order.model.Address;
import com.jossing.runboapple.order.view.IAddressActivity;
import com.jossing.runboapple.usermanage.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Somnus on 2017/4/13.
 */

public class AddressPresenter implements IAddressPresenter {
    private IAddressActivity iAddressActivity;

    public AddressPresenter(IAddressActivity iAddressActivity){
        this.iAddressActivity = iAddressActivity;
    }

    @Override
    public void loadAddressList(final Context context) {
        BmobUser bmobUser = BmobUser.getCurrentUser(context);
        BmobQuery<Address> query = new BmobQuery<Address>();
        query.addWhereEqualTo("user", bmobUser.getObjectId());
        query.findObjects(context,new FindListener<Address>() {
            @Override
            public void onSuccess(List<Address> list) {
                Log.e("load address list", "size : " + list.size());
                if(list.size() != 0) {
                    iAddressActivity.onLoadAddressListSuccess(list);
                    iAddressActivity.RefreshSuccess();
                } else {
                    iAddressActivity.sendNull(list.size());
                    iAddressActivity.RefreshSuccess();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"获取失败"+s,Toast.LENGTH_SHORT).show();
                iAddressActivity.RefreshFail();
            }
        });
    }

    @Override
    public void setSelect(final Context context, String objectNew, String objectOld) {
        List<BmobObject> addressSelect = new ArrayList<BmobObject>();
        Address addressNew = new Address();
        Address addressOld = new Address();
        addressNew.setObjectId(objectNew);
        addressNew.setSelect(true);
        addressOld.setObjectId(objectOld);
        addressOld.setSelect(false);
        addressSelect.add(addressNew);
        addressSelect.add(addressOld);
        new BmobObject().updateBatch(context, addressSelect, new UpdateListener() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(context,"失败"+msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void Select(final Context context, String object) {
        Address address = new Address();
        address.setSelect(true);
        address.update(context, object, new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context,"失败"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addAddress(String nick, String phone, String address, final Context context) {
        User currentUser = BmobUser.getCurrentUser(context, User.class);
        Address add = new Address();
        add.setUser(currentUser);
        add.setNick(nick);
        add.setPhone(phone);
        add.setSelect(false);
        add.setAddress(address);
        add.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                iAddressActivity.saveSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context,"失败"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void delAddress(final Context context, String object) {
        Address address = new Address();
        address.setObjectId(object);
        address.delete(context, new DeleteListener() {
            @Override
            public void onSuccess() {
                iAddressActivity.delSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context,"失败"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
