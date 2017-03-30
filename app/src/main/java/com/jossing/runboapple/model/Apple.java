package com.jossing.runboapple.model;

import java.io.Serializable;

/**
 * 商品信息类
 * @author Jossing , Create on 2017/3/16
 */

public class Apple implements Serializable {
    // 商品 id
    private String id;
    // 商品名称，要求长度大于 2
    private String name;
    // 品质
    private Quality quality;
    // 产地
    private String address;
    // 特征
    private String description;
    // 供货数量，要求大于 0
    private Integer count;

    public Apple(String id, String name, Quality quality, Integer count) {
        this(id, name, quality, "", "", count);
    }

    public Apple(String id, String name, Quality quality, String address, String description, Integer count) {
        this.id = id;
        this.name = name;
        this.quality = quality;
        this.address = address;
        this.description = description;
        this.count = count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Quality getQuality() {
        return quality;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCount() {
        return count;
    }

    // 优、良、中、差，四种品质
    public enum Quality { A, B, C, D }
}
