package com.jossing.runboapple.main.view;

import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.model.ApplePicture;

import java.util.List;

/**
 * @author Jossing , Create on 2017/3/19
 */

public interface IAppleDetailedActivity {

    void onQueryAppleDone(Apple apple);

    void onQueryCommentCountDone(int count);

    void onQueryPictureListDone(List<ApplePicture> applePictureList);
}
