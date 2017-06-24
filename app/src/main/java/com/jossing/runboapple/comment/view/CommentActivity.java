package com.jossing.runboapple.comment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.jossing.runboapple.R;
import com.jossing.runboapple.comment.adapter.CommentAdapter;
import com.jossing.runboapple.comment.model.Comment;
import com.jossing.runboapple.comment.presenter.CommentPresenter;
import com.jossing.runboapple.comment.presenter.ICommentPresenter;
import com.jossing.runboapple.main.model.Apple;

import java.util.List;

/**
 * @author Jossing , Create on 2017/4/14
 */

public class CommentActivity extends AppCompatActivity
        implements ICommentActivity, SwipeRefreshLayout.OnRefreshListener {

    private ICommentPresenter presenter;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvComment;
    private CommentAdapter adapter;

    private Apple apple;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CommentPresenter(this);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidget();
        
        apple = (Apple) getIntent().getSerializableExtra("apple");
        String commentCount = getIntent().getStringExtra("commentCount");

        String title = getResources().getString(R.string.comment_activity_title);
        setTitle(title + commentCount);

        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    private void initWidget() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        adapter = new CommentAdapter(this);
        rvComment = (RecyclerView) findViewById(R.id.rv_comment);
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        if (refreshLayout.isRefreshing()) {
            presenter.queryAppleComments(this, apple.getObjectId());
        }
    }

    @Override
    public void onQueryAppleCommentsDone(List<Comment> comments) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (comments != null) {
            adapter.setData(comments);
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
}
