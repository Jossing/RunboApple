package com.jossing.runboapple.welcome.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jossing.runboapple.welcome.model.WelcomePicture;
import com.jossing.runboapple.welcome.view.IWelcomeActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Jossing , Create on 2017/3/30
 */

public class WelcomePresenter implements IWelcomePresenter {
    private IWelcomeActivity activity;

    public WelcomePresenter(IWelcomeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showWelcomePicture(final Context context) {
        // 先尝试读取缓存
        String url = getWelcomePictureURLFromCache(context);
        if (!url.equals("null")) {
            activity.showWelcomePicture(url);
        }
        // 再从网络获取
        BmobQuery<WelcomePicture> query = new BmobQuery<>();
        query.setLimit(1);
        query.findObjects(context, new FindListener<WelcomePicture>() {
            @Override
            public void onSuccess(List<WelcomePicture> list) {
                WelcomePicture welcomePicture = list.get(0);
                String url = welcomePicture.getPictureURL(context);
                welcomePictureURLCache(context, url);
                activity.showWelcomePicture(url);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("获取图片失败", "(" + i + ")" + s);
                Toast.makeText(context, s + "(" + i + ")", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 缓存欢迎图片的 URL 到 SharedPreferences
     * @param context Context 对象
     * @param url 图片的 URL
     */
    private void welcomePictureURLCache(Context context, String url) {
        SharedPreferences spf = context.getSharedPreferences("WelcomePictureURL", MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("URL", url);
        editor.apply();
    }

    /**
     * 从缓存读取图片 URL，如果没有就 return "null"
     * @param context Context 对象
     * @return 欢迎图片的 URL
     */
    private String getWelcomePictureURLFromCache(Context context) {
        SharedPreferences spf = context.getSharedPreferences("WelcomePictureURL", MODE_PRIVATE);
        return spf.getString("URL", "null");
    }
}
