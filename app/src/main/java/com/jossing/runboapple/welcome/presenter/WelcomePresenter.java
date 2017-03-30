package com.jossing.runboapple.welcome.presenter;

import com.jossing.runboapple.welcome.view.IWelcomeActivity;

/**
 * @author Jossing , Create on 2017/3/30
 */

public class WelcomePresenter implements IWelcomePresenter {
    private IWelcomeActivity activity;

    public WelcomePresenter(IWelcomeActivity activity) {
        this.activity = activity;
    }

    @Override
    public String getWelcomePictureURL() {
        // BmobQuery<GameScore> query = new BmobQuery<GameScore>(); oo
        return null;
    }
}
