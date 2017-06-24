package com.jossing.runboapple.main.view;

import com.jossing.runboapple.main.model.Apple;

import java.util.List;

/**
 * @author Jossing , Create on 2017/4/9
 */

public interface IMyPostedActivity {

    void onQueryMyPostedApplesSuccess(List<Apple> appleList);

    void onPostingApple(String mag, int progress, int secondaryProgress);

    void onPostAppleSuccess();

    void onPostAppleFailure(int i, String s);

    void onCompressPhotoDone(boolean success);
}
