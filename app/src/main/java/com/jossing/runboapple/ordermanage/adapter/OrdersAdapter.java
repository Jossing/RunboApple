package com.jossing.runboapple.ordermanage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jossing.runboapple.R;
import com.jossing.runboapple.order.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jossing , Create on 2017/4/17
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private Context context;
    private LayoutInflater inflater;

    private List<Order> orders;

    private String seller_buyer;

    public OrdersAdapter(Context context, String seller_buyer) {
        this.context = context;
        this.orders = new ArrayList<>();
        this.seller_buyer = seller_buyer;
        this.inflater = LayoutInflater.from(context);
    }

    public void setOrders(List<Order> orders) {
        this.orders = new ArrayList<>(orders);
        notifyDataSetChanged();
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.orders_list_item, parent, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        holder.showOrder(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }



    class OrdersViewHolder extends RecyclerView.ViewHolder {
        ImageView imvApple;
        TextView tvAppleName;
        TextView tvAppleDescription;
        TextView tvApplePrice;
        TextView tvTotalPrice;
        TextView tvTotalCount;
        Button btn_1; // 更新物流
        Button btn_2; // 查看物流
        Button btn_3; // 确认收货

        private Order order;

        OrdersViewHolder(View view) {
            super(view);
            initWidget(view);
        }

        private void initWidget(View view) {
            imvApple = (ImageView) view.findViewById(R.id.imv_apple);
            tvAppleName = (TextView) view.findViewById(R.id.tv_apple_name);
            tvAppleDescription = (TextView) view.findViewById(R.id.tv_apple_description);
            tvApplePrice = (TextView) view.findViewById(R.id.tv_apple_price);
            tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
            tvTotalCount = (TextView) view.findViewById(R.id.tv_total_count);
            btn_1 = (Button) view.findViewById(R.id.btn_1);
            btn_2 = (Button) view.findViewById(R.id.btn_2);
            btn_3 = (Button) view.findViewById(R.id.btn_3);

            if (seller_buyer.equals("seller")) {
                btn_1.setVisibility(View.VISIBLE);
                btn_2.setVisibility(View.GONE);
            } else {
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.VISIBLE);
            }
        }

        void showOrder(Order order) {
            this.order = order;
            Glide.with(context.getApplicationContext())
                    .load(order.getApple().getPictureURL(context))
                    .into(imvApple);
            tvAppleName.setText(order.getApple().getName());
            tvAppleDescription.setText(order.getApple().getDescription());
            tvApplePrice.setText("" + order.getApple().getPrice() + "/kg");
            tvTotalPrice.setText("" + order.getTotalPrice() + "");
            tvTotalCount.setText("(" + order.getCount() + ")");
            if (order.getDone()) {
                btn_3.setVisibility(View.GONE);
            }
        }
    }
}
