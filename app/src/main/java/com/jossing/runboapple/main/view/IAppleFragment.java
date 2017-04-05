package com.jossing.runboapple.main.view;

import com.jossing.runboapple.main.model.Apple;

import java.util.List;

/**
 * @author Jossing , Create on 2017/3/19
 */

public interface IAppleFragment {

    /**
     * 当苹果列表获取完成
     * @param appleList 苹果列表
     */
    void onLoadAppleListSuccess(List<Apple> appleList);
}
