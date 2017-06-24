package com.jossing.runboapple.settings.presenter;

import android.content.Context;

/**
 * @author Jossing , Create on 2017/4/13
 */

public interface ISettingsPresenter {

    long getPhotoCacheSize(Context context);

    String fileSizeToString(long fileSize);

    void clearPhotoCache(Context context);
}
