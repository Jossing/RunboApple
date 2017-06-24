package com.jossing.runboapple.comment.model;

import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.usermanage.model.User;

import cn.bmob.v3.BmobObject;

/**
 * @author Jossing , Create on 2017/4/15
 */

public class Comment extends BmobObject {
    private Apple apple;
    private User author;
    private Integer level;
    private String content;

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Apple getApple() {
        return apple;
    }

    public User getAuthor() {
        return author;
    }

    public Integer getLevel() {
        return level;
    }

    public String getContent() {
        return content;
    }
}
