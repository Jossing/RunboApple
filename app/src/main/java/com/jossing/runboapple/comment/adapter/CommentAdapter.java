package com.jossing.runboapple.comment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.comment.model.Comment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author Jossing , Create on 2017/4/15
 */

public class CommentAdapter extends RecyclerView.Adapter {
    private List<Comment> comments;

    private Context context;
    private LayoutInflater inflater;

    public CommentAdapter(Context context) {
        this.context = context;
        comments = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Comment> comments) {
        this.comments = new ArrayList<>(comments);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comment_list_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder holder1 = (CommentViewHolder) holder;
        ((CommentViewHolder) holder).showData(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    private class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView imvAvatar;
        TextView tvName;
        TextView tvCreatedAt;
        TextView tvContent;
        RatingBar rbLevel;

        CommentViewHolder(View view) {
            super(view);

            initWidget(view);
        }

        void initWidget(View view) {
            imvAvatar = (ImageView) view.findViewById(R.id.imv_avatar);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvCreatedAt = (TextView) view.findViewById(R.id.tv_created_at);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            rbLevel = (RatingBar) view.findViewById(R.id.rb_level);
        }

        void showData(Comment comment) {
            SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
            String createdAtStr = comment.getCreatedAt().substring(0, 16);
            try {
                Date createdAt = sdfFrom.parse(createdAtStr);
                createdAtStr = sdfTo.format(createdAt);
            } catch (ParseException e) {
                Log.e("parse createAt string", "error : " + e.getLocalizedMessage());
            }
            tvCreatedAt.setText(createdAtStr);

            tvName.setText(comment.getAuthor().getNick());
            rbLevel.setRating(comment.getLevel());
            tvContent.setText(comment.getContent());

            Glide.with(context.getApplicationContext())
                    .load(comment.getAuthor().getAvatar().getFileUrl(context))
                    .centerCrop()
                    .placeholder(R.drawable.def_avatar_red)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(imvAvatar);
        }
    }
}
