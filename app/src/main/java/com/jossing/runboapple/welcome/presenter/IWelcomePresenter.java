package com.jossing.runboapple.welcome.presenter;

import android.content.Context;

/**
 * @author Jossing , Create on 2017/3/30
 */

public interface IWelcomePresenter {

    void showWelcomePicture(Context context);

    interface GetPictureURLListener {
        /**
         * 当完成时
         * @param success true 表示成功，false 表示失败。
         * @param str 表示 URL 或者错误信息。
         */
        void onDone(boolean success, String str);
    }
}
