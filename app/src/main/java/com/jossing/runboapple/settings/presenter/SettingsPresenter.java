package com.jossing.runboapple.settings.presenter;

import android.content.Context;
import android.util.Log;

import com.jossing.runboapple.settings.view.ISettingsActivity;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @author Jossing , Create on 2017/4/13
 */

public class SettingsPresenter implements ISettingsPresenter {
    private ISettingsActivity activity;

    public SettingsPresenter(ISettingsActivity activity) {
        this.activity = activity;
    }

    @Override
    public long getPhotoCacheSize(Context context) {
        long dirSize = 0;
        String cacheDir = context.getCacheDir() + "/apple_photo";
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdirs();
            Log.e("photo cache size", "缓存照片的目录不存在，将创建它并直接返回 0");
            return dirSize;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            // Log.e("photo cache size", "length : " + file.length());
            dirSize += file.length();
        }
        Log.e("photo cache size", "缓存照片目录大小 : " + dirSize);
        return dirSize;
    }

    @Override
    public String fileSizeToString(long fileSize) {
        if (fileSize == 0) return "0 B";

        String[] danwei = { " B", " KB", " MB" , " GB" };
        Double size = fileSize / 1d;
        int i = 0;
        for (; i < 4; i++) {
            Double d = size / 1024d;
            if (d < 1) {
                break;
            } else {
                size = d;
            }
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String str = df.format(size) + danwei[i];
        Log.e("file size to string", "缓存照片目录大小 : " + str);
        return str;
    }

    @Override
    public void clearPhotoCache(Context context) {
        String cacheDir = context.getCacheDir() + "/apple_photo";
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdirs();
            Log.e("clear photo cache", "缓存照片的目录不存在，将创建它");
            activity.onClearPhotoCacheDone();
            return;
        }
        File[] files =  dir.listFiles();
        for (File file : files) {
            Log.e("clear photo cache", "删除文件 : " + file.getName());
            file.delete();
        }
        activity.onClearPhotoCacheDone();
    }
}
