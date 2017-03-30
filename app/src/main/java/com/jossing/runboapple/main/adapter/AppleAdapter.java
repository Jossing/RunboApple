package com.jossing.runboapple.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jossing.runboapple.R;
import com.jossing.runboapple.main.view.AppleDetailedActivity;
import com.jossing.runboapple.model.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jossing , Create on 2017/3/18
 */

public class AppleAdapter extends RecyclerView.Adapter {
    private Context context;
    private RecyclerView rv;

    private List<Apple> appleList = new ArrayList<>();
    private int itemCount = 0;


    public AppleAdapter(Context context, RecyclerView rv) {
        this.context = context;
        this.rv = rv;
    }

    public void setData(List<Apple> appleList) {
        this.appleList.clear();
        itemCount = 0;
        for (Apple apple : appleList) {
            this.appleList.add(apple);
            itemCount += 1;
            notifyItemInserted(itemCount - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (rv.getLayoutManager() instanceof GridLayoutManager) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.apple_grid_item, parent, false);
        } else {
            // rv.getLayoutManager() instanceof LinearLayoutManager
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.apple_list_item, parent, false);
        }
        return new AppleItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppleItemViewHolder) {
            ((AppleItemViewHolder) holder).setData(appleList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }


    public class AppleItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imv;
        TextView tvName;
        TextView tvQuality;
        TextView tvAddress;
        TextView tvCount;

        private Apple apple;

        public AppleItemViewHolder(View view) {
            super(view);
            initWidget(view);
        }

        private void initWidget(View view) {
            imv = (ImageView) view.findViewById(R.id.imv);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvQuality = (TextView) view.findViewById(R.id.tv_quality);
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
            tvCount = (TextView) view.findViewById(R.id.tv_count);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AppleDetailedActivity.class);
                    intent.putExtra("apple", apple);
                    context.startActivity(intent);
                }
            });
        }

        public void setData(Apple apple) {
            this.apple = apple;
            Glide.with((Activity) context)
                    .load(R.drawable.bg)
                    .into(imv);
            tvName.setText(apple.getName());
            String qualityStr = apple.getQuality().toString() + " 级";
            tvQuality.setText(qualityStr);
            tvAddress.setText(apple.getAddress());
            String countStr = apple.getCount() + " kg";
            tvCount.setText(countStr);
        }

        public Apple getData() {
            return apple;
        }
    }
}
