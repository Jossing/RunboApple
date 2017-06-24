package com.jossing.runboapple.main.presenter;

import android.content.Context;
import android.net.Uri;

import com.jossing.runboapple.usermanage.model.User;

import java.io.File;
import java.util.List;

/**
 * @author Jossing , Create on 2017/4/9
 */

public interface IMyPostedPresenter {

    /**
     * 查询 某人 发布的苹果列表
     * @param context Context
     * @param sellerId 某人的 objectId
     */
    void queryMyPostedApples(Context context, String sellerId);

    /**
     * 发布苹果
     * @param context Context
     * @param name 名称
     * @param quality 质量
     * @param description 描述
     * @param address 产地
     * @param count 供货数量
     * @param price 单价
     * @param seller 卖家信息
     * @param pictureUris 所有照片的存储路径
     */
    void postApple(Context context, String name, String quality, String description,
                   String address, Integer count, Double price,
                   User seller, List<Uri> pictureUris);

    /**
     * @return 照片保存在本地的 uri
     */
    Uri getPhotoUri(Context context);

    /**
     * 删除指定文件
     * @param file 指定的文件
     */
    void deleteFile(File file);

    /**
     * 压缩照片
     * @param uri 输入兼输出路径
     * @param quality 照片质量
     */
    void compressPhoto(Context context, Uri uri, int quality);
}
