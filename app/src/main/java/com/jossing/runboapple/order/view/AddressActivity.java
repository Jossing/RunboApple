package com.jossing.runboapple.order.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jossing.runboapple.R;
import com.jossing.runboapple.order.adapter.AddressAdapter;
import com.jossing.runboapple.order.model.Address;
import com.jossing.runboapple.order.presenter.AddressPresenter;
import com.jossing.runboapple.order.presenter.IAddressPresenter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Somnus on 2017/4/13.
 */

public class AddressActivity extends AppCompatActivity
        implements IAddressActivity {
    private RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private IAddressPresenter iAddressPresenter;
    private ImageView address_null_im;
    private TextView address_null_tv;
    // private Address addressOld;
    private Address addressNew;
    private Integer size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iAddressPresenter = new AddressPresenter(this);
        // addressOld = (Address) getIntent().getSerializableExtra("addressOld");
        init();
        iAddressPresenter.loadAddressList(this);
        swipeRefreshLayout.setRefreshing(true);
    }

//    android.os.Handler handler= new android.os.Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            swipeRefreshLayout.setRefreshing(false);
//        }
//    };

    private void init(){
        recyclerView = (RecyclerView)findViewById(R.id.address);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddressActivity.this));
        addressAdapter = new AddressAdapter(AddressActivity.this,recyclerView);
        // addressAdapter.setSelectItem(addressOld);
        recyclerView.setAdapter(addressAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.address_sw);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        address_null_im = (ImageView)findViewById(R.id.address_null_im);
        address_null_tv = (TextView)findViewById(R.id.address_null_tv);
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            iAddressPresenter.loadAddressList(AddressActivity.this);
        }
    };

    public void setTrue(Context context,String objectNew,String objectOld){
        iAddressPresenter.setSelect(context,objectNew,objectOld);
    }

    @Override
    public void saveSuccess() {
        swipeRefreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
        address_null_tv.setVisibility(View.GONE);
        address_null_im.setVisibility(View.GONE);
//        swipeRefreshLayout.post(new Runnable() {
//
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//                // iAddressPresenter.loadAddressList(AddressActivity.this);
//                address_null_tv.setVisibility(View.GONE);
//                address_null_im.setVisibility(View.GONE);
//            }
//        });
    }

    public void True(Context context,String object){
        iAddressPresenter.Select(context,object);
    }

    public void Del(Context context,String object){
        iAddressPresenter.delAddress(context,object);
    }

    @Override
    public void RefreshSuccess() {
        // Message ok = new Message();
        // handler.sendMessage(ok);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void RefreshFail() {
        // Message ok = new Message();
        // handler.sendMessage(ok);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_address_add:
                dialog(this);
                break;
            case android.R.id.home:
                /* 这里存在问题 */
                if (size != null && size == 0){
                    addressNew = new Address();
                    addressNew.setNick("");
                    Intent intent = new Intent(this, OrderActivity.class);
                    intent.putExtra("addressNew",addressNew);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                } else {
                    finish();
                }
                finish();
                break;
        }
        return false;
    }

    @Override
    public void onLoadAddressListSuccess(List<Address> addressList) {
        recyclerView.setVisibility(View.VISIBLE);
        addressAdapter.setAddressList(addressList);
        // addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void delSuccess() {
        swipeRefreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
        // iAddressPresenter.loadAddressList(AddressActivity.this);
    }

    @Override
    public void sendNull(Integer size) {
        address_null_tv.setVisibility(View.VISIBLE);
        address_null_im.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        this.size = size;
    }

    private void dialog(final Context context){
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_address, null, false);
        final EditText name = (EditText) view.findViewById(R.id.edit_insert_name);
        final EditText phone = (EditText) view.findViewById(R.id.edit_insert_phone);
        final EditText address = (EditText) view.findViewById(R.id.edit_insert_address);
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str = name.getText().toString();
                        String ste = phone.getText().toString();
                        String stt = address.getText().toString();
                        if(str.equals("") || ste.equals("") || stt.equals("")) {
                            Toast.makeText(context, "请输入相关信息", Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialogInterface, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            iAddressPresenter.addAddress(str,ste,stt,context);
                            dialogInterface.cancel();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try
                        {
                            Field field = dialogInterface.getClass()
                                    .getSuperclass().getDeclaredField(
                                            "mShowing" );
                            field.setAccessible( true );
                            // 将mShowing变量设为false，表示对话框已关闭
                            field.set(dialogInterface, true );
                            dialogInterface.cancel();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                })
                .show();
    }

}
