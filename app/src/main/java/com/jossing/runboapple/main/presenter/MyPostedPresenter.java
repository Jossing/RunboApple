package com.jossing.runboapple.main.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.model.ApplePicture;
import com.jossing.runboapple.main.view.IMyPostedActivity;
import com.jossing.runboapple.usermanage.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * @author Jossing , Create on 2017/4/9
 */

public class MyPostedPresenter implements IMyPostedPresenter {
    private IMyPostedActivity activity;

    public MyPostedPresenter(IMyPostedActivity activity) {
        this.activity = activity;

    }

    @Override
    public void queryMyPostedApples(final Context context, String sellerId) {
        Log.e("query my posted apples", "开始查询我发布的苹果");
        BmobQuery<Apple> query = new BmobQuery<>();
        query.addWhereEqualTo("seller", sellerId);
        query.order("-createdAt");
        query.setLimit(1000);
        query.findObjects(context, new FindListener<Apple>() {
            @Override
            public void onSuccess(List<Apple> list) {
                Log.e("query my posted apples", "查询我发布的苹果成功");
                activity.onQueryMyPostedApplesSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("查询我发布的苹果失败", "(" + i + ")" + s);
                RunboAppleApp.toastShow(context, "(" + i + ")" + s, Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void postApple(Context context, String name, String quality, String description,
                          String address, Integer count, Double price,
                          User seller, List<Uri> pictureUris) {

        /* 构造一个 Apple 对象 */
        Apple apple = new Apple();
        apple.setName(name);
        apple.setDescription(description);
        apple.setQuality(quality);
        apple.setAddress(address);
        apple.setCount(count);
        apple.setPrice(price);
        apple.setSeller(seller);

        first(context, apple, pictureUris);
    }

    /**
     * 第一步：上传第一张照片
     * @param apple Apple 对象
     * @param pictureUris 所有照片路径
     */
    private void first(final Context context, final Apple apple, final List<Uri> pictureUris) {
        Log.e("post apple", "开始第一步，上传苹果预览图");
        activity.onPostingApple("第一步，上传苹果预览图", 0, 0);
        final BmobFile picture1 = new BmobFile(new File(pictureUris.get(0).getPath()));
        picture1.upload(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                Log.e("post apple", "上传预览图完成");
                apple.setPicture(picture1);
                second(context, apple, pictureUris);
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("post apple", "(" + i + ") " + s);
                activity.onPostAppleFailure(i, s);
            }
        });
    }

    /**
     * 第二步：发布苹果
     * @param apple Apple 对象
     * @param pictureUris 所有照片路径
     */
    private void second(final Context context, final Apple apple, final List<Uri> pictureUris) {
        Log.e("post apple", "开始第二步，发布苹果");
        activity.onPostingApple("第二步，发布苹果", 0, 0);
        apple.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("post apple", "发布苹果完成");
                third(context, apple, pictureUris);
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("post apple", "(" + i + ") " + s);
                activity.onPostAppleFailure(i, s);
            }
        });
    }

    /**
     * 第三步：上传所有照片
     * @param apple 包含 objectId 的 Apple 对象
     * @param pictureUris 所有照片路径
     */
    private void third(final Context context, final Apple apple, List<Uri> pictureUris) {
        Log.e("post apple", "开始第三步，上传所有苹果照片");
        pictureUris.remove(0); // 第一张照片已经上传过，所以这里要删除

        if (pictureUris.size() == 0) {
            Log.e("post apple", "全部上传完成");
            activity.onPostAppleSuccess();
            return;
        }

        final String[] picturePaths = new String[pictureUris.size()];
        for (int i = 0; i < pictureUris.size(); i++) {
            picturePaths[i] = pictureUris.get(i).getPath();
        }
        activity.onPostingApple("第三步，上传所有照片", 0, 0);
        BmobFile.uploadBatch(context, picturePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                int position = files.size() - 1;
                Log.e("post apple", "第 " + position + " 张照片上传完成");
                ApplePicture applePicture = new ApplePicture();
                applePicture.setApple(apple);
                applePicture.setPicture(files.get(files.size() - 1));
                applePicture.save(context);

                if (files.size() == picturePaths.length) {
                    Log.e("post apple", "全部上传完成");
                    activity.onPostAppleSuccess();
                }
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                activity.onPostingApple("第三步，上传所有照片 (" + curIndex + "/" + total + ")", totalPercent, curPercent);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("post apple", "(" + i + ") " + s);
                activity.onPostAppleFailure(i, s);
            }
        });
    }

    @Override
    public Uri getPhotoUri(Context context) {
        /* 取得存放路径 */
        String uriStr = context.getCacheDir().getPath() + "/apple_photo";
        Log.e("take photo", "缓存目录 : " + uriStr);
        /* 生成一个随机文件名 */
        long randNum = new Random().nextLong();
        String fileName = randNum + ".jpg";
        Log.e("take photo", "文件名 : " + fileName);
        /* 创建缓存目录，存放照片文件 */
        File outputDir = new File(uriStr);
        if (!outputDir.exists()) {
            // 如果文件夹不存在就创建它
            Log.e("take photo", "创建缓存目录成功");
            outputDir.mkdirs();
        } else {
            Log.e("take photo", "缓存目录已存在");
        }
        /* 创建 file 对象，用于存储拍照后的照片 */
        File outputFile = new File(uriStr, fileName);
        try {
            if (outputFile.exists()) {
                // 如果文件已存在就删除
                outputFile.delete();
            }
            outputFile.createNewFile();
            Log.e("take photo", "创建文件成功");
            return Uri.fromFile(outputFile);
        } catch (IOException e) {
            Log.e("take photo", "创建文件失败, " + e.getLocalizedMessage());
            e.printStackTrace();
            RunboAppleApp.toastShow(context, "创建文件失败, " + e.getLocalizedMessage(), Toast.LENGTH_LONG);
            return null;
        }
    }

    @Override
    public void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
            Log.e("delete file", "已完成删除");
        }
    }

    @Override
    public void compressPhoto(final Context context, final Uri uri, final int quality) {
        RunboAppleApp.toastShow(context, "正在处理照片，请稍后", Toast.LENGTH_LONG);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("compress photo", "开始压缩照片");
                File file = new File(uri.getPath());
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)) {
                        activity.onCompressPhotoDone(true);
                        Log.e("compress photo", "压缩照片完成");
                    } else {
                        Log.e("compress photo", "压缩照片失败，将删除失败照片文件，请重新拍摄");
                        deleteFile(file);
                        activity.onCompressPhotoDone(false);
                    }
                } catch (FileNotFoundException e) {
                    Log.e("compress photo", "压缩照片失败，将删除失败照片文件，请重新拍摄");
                    deleteFile(file);
                    activity.onCompressPhotoDone(false);
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
