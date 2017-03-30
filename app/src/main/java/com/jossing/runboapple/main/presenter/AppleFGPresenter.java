package com.jossing.runboapple.main.presenter;

import com.jossing.runboapple.main.view.IAppleFragment;
import com.jossing.runboapple.model.Apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Jossing , Create on 2017/3/19
 */

public class AppleFGPresenter implements IAppleFGPresenter {
    private IAppleFragment fragment;

    public AppleFGPresenter(IAppleFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void loadAppleList(Integer limit) {
        List<Apple> appleList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            Apple apple = new Apple("apple" + (i + 1),
                    "红富士苹果 " + (i + 1),
                    Apple.Quality.A,
                    "河北衡水",
                    "超级大超级甜超级大超级甜超级大超级甜超级大超级甜超级大超级甜超级大超级甜",
                    new Random().nextInt(1000));
            appleList.add(apple);
        }
        fragment.setAppleAdapterData(appleList);
    }
}
