package com.jossing.runboapple.main.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.common.ViewImageActivity;
import com.jossing.runboapple.main.view.MyPostedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jossing , Create on 2017/4/10
 */

public class ApplePictureAdapter extends RecyclerView.Adapter {

    private Activity activity;
    private List<Uri> pictureUris;
    private LayoutInflater layoutInflater;

    private final static int TYPE_NORMAL = 0;
    private final static int TYPE_ACTION = 1;

    public ApplePictureAdapter(Activity activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        pictureUris = new ArrayList<>();
    }

    public void addUri(Uri uri) {
        pictureUris.add(uri);
        notifyItemInserted(pictureUris.size() - 1);
    }

    public void removeUri(int position) {
        pictureUris.remove(position);
        notifyItemRemoved(position);
    }

    public List<Uri> getPictureUris() {
        return pictureUris;
    }

    public void clear() {
        pictureUris.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == (getItemCount() - 1) ? TYPE_ACTION : TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.apple_picture_item, parent, false);
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TYPE_ACTION:
                holder = new ApplePictureViewHolder(view, true);
                break;
            case TYPE_NORMAL:
            default:
                holder = new ApplePictureViewHolder(view, false);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            ApplePictureViewHolder holder1 = (ApplePictureViewHolder) holder;
            holder1.showPicture(pictureUris.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return pictureUris.size() + 1; // 增加一个 action item
    }


    /**
     * 图片列表的 viewHolder
     */
    class ApplePictureViewHolder extends RecyclerView.ViewHolder {
        ImageView imvPicture;
        ImageView imvCancel;

        private Uri pictureUri;

        ApplePictureViewHolder(View view, boolean actionMode) {
            super(view);

            imvPicture = (ImageView) view.findViewById(R.id.imv_picture);
            imvCancel = (ImageView) view.findViewById(R.id.imv_cancel);

            if (actionMode) {
                toActionMode();
            } else {
                toNormalMode();
            }
        }

        void showPicture(Uri uri) {
            pictureUri = uri;
            Glide.with(activity.getApplicationContext())
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imvPicture);
        }

        void removePicture() {
            if (pictureUri != null) pictureUri = null;

            removeUri(getAdapterPosition());
        }

        void toNormalMode() {
            imvCancel.setVisibility(View.VISIBLE);
            imvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removePicture();
                }
            });
            imvPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ViewImageActivity.class);
                    intent.putExtra("imagePath", pictureUri == null ? "" : pictureUri.getPath());
                    activity.startActivity(intent);
                }
            });
        }

        void toActionMode() {
            imvPicture.setImageResource(R.drawable.ic_add_picture_white_48dp);
            imvPicture.setScaleType(ImageView.ScaleType.CENTER);
            imvPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity instanceof MyPostedActivity) {
                        ((MyPostedActivity) activity).takePhoto();
                    }
                }
            });
        }
    }
}
