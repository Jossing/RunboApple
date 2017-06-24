package com.jossing.runboapple.settings.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jossing.runboapple.R;
import com.jossing.runboapple.settings.presenter.ISettingsPresenter;
import com.jossing.runboapple.settings.presenter.SettingsPresenter;

/**
 * @author Jossing , Create on 2017/4/12
 */

public class SettingsActivity extends AppCompatActivity implements ISettingsActivity {
    private ISettingsPresenter presenter;

    private RelativeLayout rlClearCache;
    private TextView tvClearCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingsPresenter(this);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidget();

    }

    private void initWidget() {
        rlClearCache = (RelativeLayout) findViewById(R.id.rl_clear_cache);
        tvClearCache = (TextView) findViewById(R.id.tv_clear_cache);
        showPhotoCacheSize();
    }

    private void showPhotoCacheSize() {
        long cacheSize = presenter.getPhotoCacheSize(this);
        String size = presenter.fileSizeToString(cacheSize);
        String des = "此操作不会影响使用。";
        String text = des + "( " + size + " )";
        tvClearCache.setText(text);
    }

    /**
     * 当 “清理缓存” 设置项被点击时调用
     * @param view 触发事件的 view
     */
    public void onClearPhotoCache(View view) {
        rlClearCache.setClickable(false);
        presenter.clearPhotoCache(this);
    }

    @Override
    public void onClearPhotoCacheDone() {
        rlClearCache.setClickable(true);
        showPhotoCacheSize();
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
