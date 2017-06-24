package com.jossing.runboapple.order.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jossing.runboapple.R;
import com.jossing.runboapple.order.model.Address;
import com.jossing.runboapple.order.view.AddressActivity;
import com.jossing.runboapple.order.view.OrderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2017/4/13.
 */

public class AddressAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private RecyclerView rv;
    private List<Address> addressList = new ArrayList<>();
    private Address address_select;


    public AddressAdapter(Activity activity, RecyclerView rv){
        this.activity = activity;
        this.rv = rv;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = new ArrayList<>(addressList);
        notifyDataSetChanged();
    }

//    public void setSelectItem(Address address_select){
//        this.address_select = address_select;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_address, parent, false);
        return new AddressItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddressItemViewHolder) {
            Address address = addressList.get(position);
            ((AddressItemViewHolder) holder).setAddress(address);
            if (address.getSelect()) {
                address_select = address;
            }

        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public class AddressItemViewHolder extends RecyclerView.ViewHolder {
        TextView address_nick;
        TextView address_phone;
        TextView address_address;
        CheckBox address_check;
        private Address addressNew;
        ImageView address_del;

        public AddressItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            address_nick = (TextView)view.findViewById(R.id.address_name);
            address_phone = (TextView)view.findViewById(R.id.address_phone);
            address_address = (TextView)view.findViewById(R.id.address_address);
            address_check = (CheckBox) view.findViewById(R.id.address_check);
            address_del = (ImageView)view.findViewById(R.id.address_del);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(address_select != null){
                        Intent intent = new Intent(activity, OrderActivity.class);
                        intent.putExtra("addressNew",addressNew);
                        activity.setResult(Activity.RESULT_OK, intent);
                        ((AddressActivity)activity).setTrue(activity, addressNew.getObjectId(), address_select.getObjectId());
                        activity.finish();
                    } else {
                        Intent intent = new Intent(activity, OrderActivity.class);
                        intent.putExtra("addressNew", addressNew);
                        activity.setResult(Activity.RESULT_OK, intent);
                        ((AddressActivity)activity).True(activity,addressNew.getObjectId());
                        activity.finish();
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(activity)
                            .setTitle("提示")
                            .setIcon(R.drawable.ic_warn_fill_red_24dp)
                            .setMessage("确认删除？")
                            .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(addressNew.getSelect()){
                                        Toast.makeText(activity, "选中地址不可删除", Toast.LENGTH_SHORT).show();
                                    } else {
                                        ((AddressActivity)activity).Del(activity,addressList.get(getPosition()).getObjectId());
                                    }
                                }
                            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();
                    return false;
                }
            });

        }

        public void setAddress(Address address) {
            this.addressNew = address;
            address_nick.setText(address.getNick());
            address_phone.setText(address.getPhone());
            address_address.setText(address.getAddress());
            address_check.setChecked(address.getSelect());
        }

        public Address getAddress() {
            return addressNew;
        }

    }
}
