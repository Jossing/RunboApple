package com.jossing.runboapple.comment.view;

import com.jossing.runboapple.comment.model.Comment;

import java.util.List;

/**
 * @author Jossing , Create on 2017/4/14
 */

public interface ICommentActivity {

    void onQueryAppleCommentsDone(List<Comment> comments);
}
