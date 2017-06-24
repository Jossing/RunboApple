package com.jossing.runboapple.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.view.AppleDetailedActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author Jossing , Create on 2017/3/18
 */

public class AppleAdapter extends RecyclerView.Adapter {
    private Context context;
    private RecyclerView rv;
    private LayoutInflater inflater;

    private List<Apple> appleList = new ArrayList<>();
    private int itemCount = 0;

    public AppleAdapter(Context context, RecyclerView rv) {
        this.context = context;
        this.rv = rv;
        inflater = LayoutInflater.from(context);
    }

    public void setAppleList(List<Apple> newAppleList) {
        // 文艺青年新宠
        // 利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，
        // 和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(appleList, newAppleList), false);

        // 别忘了将新数据给Adapter
        // this.appleList = newAppleList;
        // 不保存新数据集的引用，而是创建一个新的数据集对象
        appleList = new ArrayList<>(newAppleList);
        itemCount = appleList.size();

        // 利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，
        // 传入RecyclerView的Adapter，轻松成为文艺青年
        diffResult.dispatchUpdatesTo(this);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (rv.getLayoutManager() instanceof GridLayoutManager) {
            itemView = inflater.inflate(R.layout.apple_grid_item, parent, false);
        } else {
            // rv.getLayoutManager() instanceof LinearLayoutManager
            itemView = inflater.inflate(R.layout.apple_list_item, parent, false);
        }
        return new AppleItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {//判断数据更改是否为空，说明是新增的，直接去绑定数据
        if (payloads == null || payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            Log.i("onBindViewHolder", "position:" + position + " payloads is empty");
            return;
        }
        if (!(holder instanceof AppleItemViewHolder)) {
            return;
        }
        //如果不为空，说明有部分数据发生了更改，那么只要根据数据去更新变更的UI即可
        Log.i("onBindViewHolder", "position:" + position + " payloads is not empty");
        AppleItemViewHolder viewHolder = (AppleItemViewHolder) holder;
        Bundle bundle = (Bundle) payloads.get(0);
        viewHolder.updateData(bundle);
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
        TextView tvPrice;
        TextView tvDescription;

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
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvDescription = (TextView) view.findViewById(R.id.tv_description);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AppleDetailedActivity.class);
                    intent.putExtra("apple", apple);
                    context.startActivity(intent);
                }
            });
        }

        /**
         * 显示数据
         * @param apple 苹果数据模型
         */
        public void setData(Apple apple) {
            this.apple = apple;
            Glide.with(context.getApplicationContext())
                    .load(R.drawable.bg)
                    .into(imv);
            tvName.setText(apple.getName());
            String qualityStr = apple.getQuality() + " 级";
            tvQuality.setText(qualityStr);
            tvAddress.setText(apple.getAddress());
            String countStr = " (" + apple.getCount() + " kg)";
            tvCount.setText(countStr);
            String priceStr = apple.getPrice() + "/kg";
            tvPrice.setText(priceStr);
            tvDescription.setText(apple.getDescription());
            Glide.with(context.getApplicationContext())
                    .load(apple.getPictureURL(context))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imv);
        }

        public void updateData(Bundle bundle) {
            Log.e("updateData", "bundle size " + bundle.size());
            String name = bundle.getString("name");
            if (name != null && !name.equals("")) {
                apple.setName(name);
                tvName.setText(name);
            }
            String quality = bundle.getString("quality" + " 级");
            if (quality != null && !quality.equals("")) {
                apple.setQuality(quality);
                tvQuality.setText(quality);
            }
            String address = bundle.getString("address");
            if (address != null && !address.equals("")) {
                apple.setAddress(address);
                tvAddress.setText(address);
            }
            String description = bundle.getString("description");
            if (description != null && !description.equals("")) {
                apple.setDescription(description);
                tvDescription.setText(description);
            }
            int count = bundle.getInt("count", -1);
            if (count != -1) {
                apple.setCount(count);
                tvCount.setText(" (" + count + " kg)");
            }
            double price = bundle.getDouble("price", -1);
            if (price != -1) {
                apple.setPrice(price);
                tvPrice.setText(price + "/kg");
            }
            BmobFile picture = (BmobFile) bundle.getSerializable("picture");
            if (picture != null) {
                apple.setPicture(picture);
                Glide.with(context.getApplicationContext())
                        .load(picture.getFileUrl(context))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(imv);
            }
        }

        public Apple getData() {
            return apple;
        }
    }


    /**
     * 介绍：核心类 用来判断 新旧 Item 是否相等
     * 作者：zhangxutong
     * 邮箱：zhangxutong@imcoming.com
     * 时间： 2016/9/12.
     */
    public class DiffCallBack extends DiffUtil.Callback {
        private List<Apple> oldAppleList, newAppleList;

        public DiffCallBack(List<Apple> oldAppleList, List<Apple> newAppleList) {
            this.oldAppleList = oldAppleList;
            this.newAppleList = newAppleList;
        }

        /**
         * 老数据集 size
         */
        @Override
        public int getOldListSize() {
            return oldAppleList != null ? oldAppleList.size() : 0;
        }

        /**
         * 新数据集 size
         */
        @Override
        public int getNewListSize() {
            return newAppleList != null ? newAppleList.size() : 0;
        }

        /**
         * Called by the DiffUtil to decide whether two object represent the same Item.
         * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
         * For example, if your items have unique ids, this method should check their id equality.
         * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
         * 本例判断name字段是否一致
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list
         * @return True if the two items represent the same object or false if they are different.
         */
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            Apple beanOld = oldAppleList.get(oldItemPosition);
            Apple beanNew = newAppleList.get(newItemPosition);
            return beanOld.getObjectId().equals(beanNew.getObjectId());
        }

        /**
         * Called by the DiffUtil when it wants to check whether two items have the same data.
         * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
         * DiffUtil uses this information to detect if the contents of an item has changed.
         * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
         * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
         * DiffUtil 用这个方法替代equals方法去检查是否相等。
         * so that you can change its behavior depending on your UI.
         * 所以你可以根据你的UI去改变它的返回值
         * For example, if you are using DiffUtil with a
         * {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter}, you should
         * return whether the items' visual representations are the same.
         * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
         * This method is called only if {@link #areItemsTheSame(int, int)} returns
         * {@code true} for these items.
         * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list which replaces the
         *                        oldItem
         * @return True if the contents of the items are the same or false if they are different.
         */
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Apple oldApple = oldAppleList.get(oldItemPosition);
            Apple newApple = newAppleList.get(newItemPosition);

            if (!TextUtils.equals(oldApple.getName(), newApple.getName())) {
                Log.e("areContentsTheSame", "" + false);
                return false;
            }
            if (!TextUtils.equals(oldApple.getQuality(), newApple.getQuality())) {
                Log.e("areContentsTheSame", "" + false);
                return false;
            }
            if (!TextUtils.equals(oldApple.getAddress(), newApple.getAddress())) {
                Log.e("areContentsTheSame", "" + false);
                return false;
            }
            if (!TextUtils.equals(oldApple.getDescription(), newApple.getDescription())) {
                Log.e("areContentsTheSame", "" + false);
                return false;
            }
            if (oldApple.getCount().intValue() != newApple.getCount().intValue()) {
                Log.e("areContentsTheSame", "" + false);
                return false;
            }
            if (oldApple.getPrice().doubleValue() != newApple.getPrice().doubleValue()) {
                Log.e("areContentsTheSame", "" + false);
                return false;
            }
            if (!TextUtils.equals(oldApple.getPictureURL(context), newApple.getPictureURL(context))) {
                Log.e("areContentsTheSame", "" + false);
                return false;
            }
            Log.e("areContentsTheSame", "" + true);
            return true;
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            Apple oldApple = oldAppleList.get(oldItemPosition);
            Apple newApple = newAppleList.get(newItemPosition);

            Bundle bundle = new Bundle();
            if (!TextUtils.equals(oldApple.getName(), newApple.getName())) {
                bundle.putString("name", newApple.getName());
            }
            if (!TextUtils.equals(oldApple.getQuality(), newApple.getQuality())) {
                bundle.putString("quality", newApple.getQuality());
            }
            if (!TextUtils.equals(oldApple.getAddress(), newApple.getAddress())) {
                bundle.putString("address", newApple.getAddress());
            }
            if (!TextUtils.equals(oldApple.getDescription(), newApple.getDescription())) {
                bundle.putString("description", newApple.getDescription());
            }
            if (oldApple.getCount().intValue() != newApple.getCount().intValue()) {
                bundle.putInt("count", newApple.getCount());
            }
            if (oldApple.getPrice().doubleValue() != newApple.getPrice().doubleValue()) {
                bundle.putDouble("price", newApple.getPrice());
            }
            if (!TextUtils.equals(oldApple.getPictureURL(context), newApple.getPictureURL(context))) {
                bundle.putSerializable("picture", newApple.getPicture());
            }
            Log.e("bundle", "" + bundle.size());
            return bundle.size() != 0 ? bundle : null;
        }
    }
}
