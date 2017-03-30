package com.jossing.runboapple.main.presenter;

import com.jossing.runboapple.main.view.IMainActivity;

/**
 * @author Jossing , Create on 2017/3/16
 */

public class MainPresenter implements IMainPresenter {
    private IMainActivity activity;

    public MainPresenter(IMainActivity iMainActivity) {
        this.activity = iMainActivity;
    }
}
