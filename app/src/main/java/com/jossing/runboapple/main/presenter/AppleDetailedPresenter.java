package com.jossing.runboapple.main.presenter;

import com.jossing.runboapple.main.view.IAppleDetailedActivity;

/**
 * @author Jossing , Create on 2017/3/19
 */

public class AppleDetailedPresenter implements IAppleDetailedPresenter {
    private IAppleDetailedActivity activity;

    public AppleDetailedPresenter(IAppleDetailedActivity activity) {
        this.activity = activity;
    }
}
