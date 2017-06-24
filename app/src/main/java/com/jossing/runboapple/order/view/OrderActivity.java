package com.jossing.runboapple.order.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jossing.runboapple.R;
import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.view.AppleDetailedActivity;
import com.jossing.runboapple.order.model.Address;
import com.jossing.runboapple.order.presenter.IOrderPresenter;
import com.jossing.runboapple.order.presenter.OrderPresenter;
import com.jossing.runboapple.usermanage.view.PersonActivity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Somnus on 2017/4/9.
 */

public class OrderActivity extends AppCompatActivity
        implements IOrderActivity ,View.OnClickListener{
    private IOrderPresenter iOrderPresenter;
    private RelativeLayout goodsInfo;
    private RelativeLayout order_description,order_seller,order_number,buyerInfo;
    private TextView order_username,order_phone,order_address,order_goods_name,order_goods_description;
    private TextView order_goods_address,order_money,order_goods_description_times;
    private TextView order_invoice_info,order_goods_num,order_freight_tv;
    private TextView order_goods_quality,order_goods_price;
    private TextView order_seller_name;
    private ImageView order_goods_pic;
    private EditText order_message;
    private Button order_confirm;
    private Apple apple;
    private String  i ;
    private Address addressOld;
    private Address addressNew;
    private boolean address_YN = false;
    private int year,month,day;
    private int Year=0,Month=0,Day=0;
    private Spinner order_goods_number;
    private ArrayList<String> list_number;
    private Integer num = 0;
    private Double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apple = (Apple) getIntent().getSerializableExtra("apple");
        iOrderPresenter = new OrderPresenter(this);
        init();
        getDate();
        showAppleDetailed();
        getSpinner(apple.getCount());
        order_goods_number = (Spinner) findViewById(R.id.order_goods_number);
        ArrayAdapter adapter = new ArrayAdapter<String>(OrderActivity.this,android.R.layout.simple_spinner_item, list_number);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order_goods_number.setAdapter(adapter);
        order_goods_number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                iOrderPresenter.sendNumber(i);
                BigDecimal number1 = new BigDecimal(i);
                BigDecimal number2 = new BigDecimal(apple.getPrice());
                BigDecimal bigDecimal = number1.multiply(number2);
                BigDecimal b = new BigDecimal(bigDecimal.doubleValue());
                double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                iOrderPresenter.sendTotalPrice(f1);
                order_money.setText(String.valueOf(f1));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                iOrderPresenter.sendNumber(0);
            }
        });
        iOrderPresenter.search(this);
    }

    private void init(){
        list_number = new ArrayList<String>();
        goodsInfo = (RelativeLayout)findViewById(R.id.rl_apple);
        goodsInfo.setOnClickListener(this);
        buyerInfo = (RelativeLayout)findViewById(R.id.order_buyer_info);
        buyerInfo.setOnClickListener(this);
        order_seller = (RelativeLayout)findViewById(R.id.order_seller);
        order_seller.setOnClickListener(this);
        order_description = (RelativeLayout)findViewById(R.id.order_description);
        order_description.setOnClickListener(this);
        order_number = (RelativeLayout)findViewById(R.id.order_number);
        order_number.setOnClickListener(this);
        order_message = (EditText)findViewById(R.id.order_message);
        order_seller_name = (TextView)findViewById(R.id.order_seller_name);
        order_username = (TextView)findViewById(R.id.order_username);
        order_phone = (TextView)findViewById(R.id.order_phone);
        order_address = (TextView)findViewById(R.id.order_address);
        order_goods_pic = (ImageView)findViewById(R.id.imv);
        order_goods_name = (TextView)findViewById(R.id.tv_name);
        order_goods_description = (TextView)findViewById(R.id.tv_description);
        order_goods_quality = (TextView)findViewById(R.id.tv_quality);
        order_goods_price = (TextView)findViewById(R.id.tv_price);
        order_goods_num = (TextView)findViewById(R.id.tv_count);
        order_money = (TextView)findViewById(R.id.order_money) ;
        order_goods_address = (TextView)findViewById(R.id.tv_address);
        order_goods_description_times = (TextView)findViewById(R.id.order_goods_description_times);
        order_money = (TextView)findViewById(R.id.order_money);
        order_confirm = (Button)findViewById(R.id.order_confirm_btn);
        order_confirm.setOnClickListener(this);
    }

    private void showAppleDetailed() {
        order_goods_name.setText(apple.getName());
        order_goods_description.setText(apple.getDescription());
        order_goods_price.setText(apple.getPrice().toString() + "/kg");
        order_goods_quality.setText(apple.getQuality() + " 级");
        order_goods_address.setText(apple.getAddress());
        order_goods_num.setText(" (" + apple.getCount().toString() + " kg)");
        Glide.with(getApplicationContext()).load(apple.getPictureURL(this)).into(order_goods_pic);
        order_goods_description_times.setText(month+"月"+day+"日");

        order_seller_name.setText(apple.getSeller().getNick());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.order_seller:
                Intent intent4 = new Intent(this, PersonActivity.class);
                intent4.putExtra("anotherUserObjectId", apple.getSeller().getObjectId());
                startActivity(intent4);
                break;
            case R.id.rl_apple:
                // 苹果信息
                Intent intent = new Intent(OrderActivity.this, AppleDetailedActivity.class);
                intent.putExtra("apple", apple);
                finish();
                startActivity(intent);
                break;
            case R.id.order_buyer_info:
                // 地址信息
                Intent i = new Intent(OrderActivity.this, AddressActivity.class);
                i.putExtra("addressOld",addressOld);
                startActivityForResult(i,1);
                break;
            case R.id.order_description:
                // 配送时间
                dialog();
                break;
            case R.id.order_confirm_btn:
                // 下单按钮
                Log.w("下单", "ok");
                if(num == 0){
                    Toast.makeText(this,"请选择苹果数量",Toast.LENGTH_SHORT).show();
                } else if (Year==0||Month==0||Day==0){
                    Toast.makeText(this,"请选择预计送达时间",Toast.LENGTH_SHORT).show();
                } else if (totalPrice == 0.0){
                    Toast.makeText(this,"请选择苹果数量",Toast.LENGTH_SHORT).show();
                } else if(address_YN) {
                    /* 构造送货时间对象 */
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String dateStr = Year+"-"+Month+"-"+Day;
                    BmobDate date;
                    try {
                        date = new BmobDate(sdf.parse(dateStr));
                    } catch (ParseException e) {
                        RunboAppleApp.toastShow(this, "时间错误", Toast.LENGTH_LONG);
                        break;
                    }

                    String note = order_message.getText().toString();
                    order_confirm.setEnabled(false);
                    iOrderPresenter.buy(this, addressOld, apple, num, note, date, totalPrice);
                }else {
                    Toast.makeText(this,"请填写地址信息",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private List<String> getSpinner(int number){
        String num;
        for (int i = 0;i<=number;i++){
            if(i == 0){
                num = "请选择苹果数量";
            } else {
                num = i+"kg";
            }
            list_number.add(num);
        }
        return list_number;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode){
                case 1:
                    if(resultCode == RESULT_OK){
                        addressNew = (Address) data.getSerializableExtra("addressNew");
                        if (addressNew.getNick().equals("")){
                            order_username.setText("XXX");
                            order_phone.setText("XXXXXXXXXXX");
                            order_address.setText("xxxxxxx");
                            Toast.makeText(this,"请填写相关地址信息",Toast.LENGTH_SHORT).show();
                            address_YN = false;
                        } else {
                            order_username.setText(addressNew.getNick());
                            order_phone.setText(addressNew.getPhone());
                            order_address.setText(addressNew.getAddress());
                            addressOld = addressNew;
                            address_YN = true;
                        }
                    }
                    break;
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    @Override
    public void sendTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public void Select_Address(Address address) {
        this.addressOld = address;
        order_username.setText(address.getNick());
        order_phone.setText(address.getPhone());
        order_address.setText(address.getAddress());
        address_YN = true;
    }

    @Override
    public void sendData(int year, int month, int day) {
        this.Year = year;
        this.Month = month;
        this.Day = day;
        Log.w("date",Year+"年"+Month+"月"+Day+"日");
    }

    @Override
    public void buy_Success() {
        RunboAppleApp.toastShow(this, "购买成功", Toast.LENGTH_SHORT);
        Intent i = new Intent(this, CompleteActivity.class);
        i.putExtra("apple", apple);
        finish();
        startActivity(i);
    }

    @Override
    public void buy_fail(String s) {
        RunboAppleApp.toastShow(this, "购买失败" + s, Toast.LENGTH_LONG);
        order_confirm.setEnabled(true);
    }

    @Override
    public void sendNumber(Integer number) {
        this.num = number;
        Log.w("num",num.toString());
    }

    private void dialog() {
        //取得系统日期:

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (monthOfYear+1<month|| (monthOfYear+1 == month&&dayOfMonth<day)){
                    Toast.makeText(OrderActivity.this,"所选日期不能小于今天",Toast.LENGTH_SHORT).show();
                } else {
                    iOrderPresenter.sendDate(year,(monthOfYear + 1),dayOfMonth);
                    order_goods_description_times.setText((monthOfYear + 1)+"月"+dayOfMonth+"日");
                }
            }
        },Year,Month-1,Day);
        datePickerDialog.show();
    }

    private void hold(final DatePickerDialog datePickerDialog){
        datePickerDialog.setButton(1, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                datePickerDialog.dismiss();
            }
        });
    }

    private void getDate(){
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTime(curDate);
        year = calendar.get(java.util.Calendar.YEAR);
        month = calendar.get(java.util.Calendar.MONTH)+1;
        day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        this.Year = year;
        this.Month = month;
        this.Day = day;
    }

}
