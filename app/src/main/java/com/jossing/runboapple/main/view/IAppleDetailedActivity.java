package com.jossing.runboapple.main.view;

import com.jossing.runboapple.main.model.ApplePicture;

import java.util.List;

/**
 * @author Jossing , Create on 2017/3/19
 */

public interface IAppleDetailedActivity {


    void onQueryPictureListSuccess(List<ApplePicture> applePictureList);
}
