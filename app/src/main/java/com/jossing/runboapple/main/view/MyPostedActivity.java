package com.jossing.runboapple.main.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.jossing.runboapple.R;
import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.main.adapter.AppleAdapter;
import com.jossing.runboapple.main.adapter.ApplePictureAdapter;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.presenter.IMyPostedPresenter;
import com.jossing.runboapple.main.presenter.MyPostedPresenter;
import com.jossing.runboapple.usermanage.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;

public class MyPostedActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, IMyPostedActivity {

    private IMyPostedPresenter presenter;
    private Uri currentPhotoUri; // 当前照片的存储路径

    /* 查看已发布的苹果 */
    private FloatingActionButton fabPost;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvMyPosted;
    private AppleAdapter appleAdapter;
    private RelativeLayout rlEmpty;

    /* 发布苹果 */
    private NestedScrollView nsvPostView;
    private RecyclerView rvPicture;
    private ApplePictureAdapter pictureAdapter;
    private Spinner spinnerQuality;
    private EditText etName;
    private EditText etDescription;
    private EditText etAdress;
    private EditText etCount;
    private EditText etPrice;
    private Button btnPostApple;

    private ProgressDialog progressDialog;

    public enum RequestCode { TAKE_PHOTO, CROP_PHOTO }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MyPostedPresenter(this);
        setContentView(R.layout.activity_my_posted);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidget();

        Intent intent = getIntent();
        boolean currentUser = intent.getBooleanExtra("isCurrentUser", false);
        if (currentUser) {
            fabPost.setOnClickListener(this);
        } else {
            fabPost.setVisibility(View.GONE);
        }

        delayToRefresh();
    }

    private void initWidget() {
        progressDialog = new ProgressDialog(this);

        /* 查看已发布的苹果 */
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        fabPost = (FloatingActionButton) findViewById(R.id.fab_post);
        rlEmpty = (RelativeLayout) findViewById(R.id.rl_empty);
        rvMyPosted = (RecyclerView) findViewById(R.id.rv_my_posted);
        rvMyPosted.setHasFixedSize(true);
        rvMyPosted.setLayoutManager(new LinearLayoutManager(this));
        appleAdapter = new AppleAdapter(this, rvMyPosted);
        rvMyPosted.setAdapter(appleAdapter);

        /* 发布苹果 */
        nsvPostView = (NestedScrollView) findViewById(R.id.nsv_post_view);
        rvPicture = (RecyclerView) findViewById(R.id.rv_picture);
        rvPicture.setHasFixedSize(true);
        rvPicture.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        pictureAdapter = new ApplePictureAdapter(this);
        rvPicture.setAdapter(pictureAdapter);
        spinnerQuality = (Spinner) findViewById(R.id.spinner_quality);
        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);
        etAdress = (EditText) findViewById(R.id.et_address);
        etCount = (EditText) findViewById(R.id.et_count);
        etPrice = (EditText) findViewById(R.id.et_price);
        btnPostApple = (Button) findViewById(R.id.btn_post_apple);
        btnPostApple.setOnClickListener(this);
    }

    /**
     * 延迟 120ms 后主动刷新
     */
    private void delayToRefresh() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isFinishing() || isDestroyed()) return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(true);
                            onRefresh(); // 手动调用
                        }
                    }
                });
            }
        }, 250L);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (nsvPostView.getVisibility() == View.VISIBLE) {
            toMyPostedView();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 切换到 “我发布的” 界面
     */
    private void toMyPostedView() {
        setTitle(R.string.my_posted_activity_title);
        // nsvPostView.setVisibility(View.GONE);
        RunboAppleApp.animationHideView(nsvPostView);
        fabPost.show();
    }


    /**
     * 切换到 “发布” 界面
     */
    private void toPostView() {
        /* 清空 “发布” 界面 */
        etName.setText("");
        etDescription.setText("");
        etAdress.setText("");
        etCount.setText("");
        etPrice.setText("");
        spinnerQuality.setSelection(0);
        pictureAdapter.clear();
        /* 切换 */
        setTitle("发布");
        // nsvPostView.setVisibility(View.VISIBLE);
        RunboAppleApp.animationShowView(nsvPostView);
        fabPost.hide();
    }

    /**
     * 调用系统相机拍摄照片
     */
    public void takePhoto() {
        if (currentPhotoUri != null) {
            // 说明当前还有照片正在处理
            RunboAppleApp.toastShow(this, "正在处理照片，请稍后", Toast.LENGTH_LONG);
            return;
        }
        // RunboAppleApp.toastShow(activity, "增加照片", Toast.LENGTH_LONG);
        Uri photoUri = presenter.getPhotoUri(this);
        if (photoUri == null) {
            Log.e("take photo", "照片输出路径为空，取消拍照");
            return;
        }
        currentPhotoUri = photoUri;
        /* 跳转到相机界面 */
        Uri contentUri = RunboAppleApp.getContentUri(this, new File(currentPhotoUri.getPath()));
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        grantUriPermission(getPackageName(), contentUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Log.e("take photo", "开始拍照");
        startActivityForResult(intent, RequestCode.TAKE_PHOTO.ordinal());
    }

    /**
     * 调用系统裁剪裁剪照片
     */
    public void cropPhoto() {
        if (currentPhotoUri == null) {
            Log.e("crop photo", "照片路径为空，取消裁剪");
            return;
        }
        Uri contentUri = RunboAppleApp.getContentUri(this, new File(currentPhotoUri.getPath()));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(contentUri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        grantUriPermission(getPackageName(), contentUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Log.e("crop photo", "开始裁剪照片");
        startActivityForResult(intent, RequestCode.CROP_PHOTO.ordinal());
    }

    /**
     * 由负责压缩照片的子线程调用
     * @param success true 代表成功，false 代表失败
     */
    @Override
    public void onCompressPhotoDone(final boolean success) {
        runOnUiThread(new Runnable() { @Override public void run() {
            if (success) {
                // 完成照片的 拍摄、裁剪、压缩 处理
                RunboAppleApp.toastShow(MyPostedActivity.this, "拍照完成", Toast.LENGTH_SHORT);
                pictureAdapter.addUri(currentPhotoUri);
            } else {
                RunboAppleApp.toastShow(MyPostedActivity.this, "无法处理照片，请重试", Toast.LENGTH_LONG);
            }
            // 置空
            currentPhotoUri = null;
        }});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_post:
                if (nsvPostView.getVisibility() == View.GONE) {
                    toPostView();
                }
                break;
            case R.id.btn_post_apple:
                // RunboAppleApp.toastShow(this, "发布苹果成功！", Toast.LENGTH_LONG);
                postApple();
                break;
        }
    }

    /**
     * 从界面上获取信息然后发布苹果
     */
    private void postApple() {
        /* 获取并检查当前用户信息 */
        User seller = BmobUser.getCurrentUser(this, User.class);
        if (seller == null || seller.getObjectId() == null || seller.getObjectId().equals("")) {
            RunboAppleApp.toastShow(this, "你可能还没有登录，请登录后重新操作", Toast.LENGTH_LONG);
            return;
        }

        /* 获取并检查苹果名称 */
        String name = etName.getText().toString().trim();
        if (name.length() < 2 || name.length() > 16) {
            etName.setError("请确保字数在 2 ~ 16 之间");
            return;
        }

        /* 获取质量 */
        String quality;
        switch (spinnerQuality.getSelectedItemPosition()) {
            case 0:
                quality = "A";
                break;
            case 1:
                quality = "B";
                break;
            case 2:
                quality = "C";
                break;
            case 3:
            default:
                quality = "D";
        }

        /* 获取并检查苹果描述信息 */
        String description = etDescription.getText().toString().trim();
        if (description.length() < 8 || description.length() > 100) {
            etDescription.setError("请确保字数在 8 ~ 100 之间");
            return;
        }

        /* 获取并检查产地信息 */
        String address = etAdress.getText().toString().trim();
        if (address.length() < 2) {
            etAdress.setError("请确保字数大于 2");
            return;
        }

        /* 获取并检查供货数量 */
        String countStr = etCount.getText().toString().trim();
        if (countStr.length() <= 0) {
            etCount.setError("请输入供货数量");
            return;
        }
        Integer count = Integer.parseInt(countStr);
        if (count <= 0) {
            etCount.setError("你确定供货数量是 " + count + " kg 吗？");
            return;
        }

        /* 获取并检查供货数量 */
        String priceStr = etPrice.getText().toString().trim();
        if (priceStr.length() <= 0) {
            etPrice.setError("请输入单价");
            return;
        }
        Double price  = Double.parseDouble(priceStr);
        if (price <= 0) {
            etPrice.setError("你确定单价是 " + price + "/kg 吗？");
            return;
        }

        /* 检查照片 */
        List<Uri> pictureUris = new ArrayList<>(pictureAdapter.getPictureUris());
        if (pictureUris.size() == 0) {
            RunboAppleApp.toastShow(this, "要求至少上传一张照片", Toast.LENGTH_LONG);
            return;
        }

        /* 发布 */
        onPostingApple("", 0, 0);
        presenter.postApple(this, name, quality, description, address,
                count, price, seller, pictureUris);
    }

    @Override
    public void onRefresh() {
        Log.e("refreshLayout", "isRefreshing");
        if (refreshLayout.isRefreshing()) {
            presenter.queryMyPostedApples(this, BmobUser.getCurrentUser(this, User.class).getObjectId());
        }
    }

    /**
     * 当查询苹果列表完成时
     * @param appleList 苹果列表
     */
    @Override
    public void onQueryMyPostedApplesSuccess(List<Apple> appleList) {
        // Log.e("apple list size", "" + appleList.size());
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (appleList.size() == 0) {
            RunboAppleApp.animationShowView(rlEmpty);
            // rlEmpty.setVisibility(View.VISIBLE);
        } else {
            RunboAppleApp.animationHideView(rlEmpty);
            //rlEmpty.setVisibility(View.GONE);
        }
        appleAdapter.setAppleList(appleList);
    }

    @Override
    public void onPostingApple(String msg, int progress, int secondaryProgress) {
        if (!progressDialog.isShowing()) {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
            progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            progressDialog.setMax(100);
            progressDialog.show();
        }
        progressDialog.setTitle("正在发布...\n" + msg);
        progressDialog.setProgress(progress);
        progressDialog.setSecondaryProgress(secondaryProgress);
    }

    @Override
    public void onPostAppleSuccess() {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
            progressDialog = new ProgressDialog(this);
        }
        toMyPostedView();
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onPostAppleFailure(int i, String s) {
        if (!progressDialog.isShowing()) return;
        RunboAppleApp.toastShow(this, "(" + i + ") " + s, Toast.LENGTH_LONG);
        progressDialog.setTitle("发生错误\n" + "(" + i + ") " + s);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "好吧",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.cancel();
                        progressDialog = new ProgressDialog(MyPostedActivity.this);
                    }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.TAKE_PHOTO.ordinal()) {
            if (resultCode == RESULT_OK) {
                Log.e("take photo", "拍照完成，准备进行裁剪");
                cropPhoto();
            } else {
                // 用户取消拍照 或 拍照失败
                Log.e("take photo", "拍照失败，将删除异常文件");
                presenter.deleteFile(new File(currentPhotoUri.getPath()));
                currentPhotoUri = null;
            }
        } else if (requestCode == RequestCode.CROP_PHOTO.ordinal()) {
            int quality; // 指定照片质量
            if (resultCode == RESULT_OK) {
                Log.e("crop photo", "完成照片裁剪");
                quality = 50;
            } else {
                Log.e("crop photo", "没有完成照片裁剪");
                quality = 30;
            }
            // 调用压缩代码
            presenter.compressPhoto(this, currentPhotoUri, quality);
        }
    }
}
