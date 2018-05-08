package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public class LightPost {
    public String getPostID() {
        return postId;
    }

    public String getAuthorID() {
        return authorId;
    }

    public String getText() {
        return text;
    }

   public String postId;
    String authorId;
   String text;

    public String getPostId() {
        return postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public LightPost()
    {

    }
    public LightPost(String postID,String authorID,String text){
        this.authorId = authorID;
        this.postId = postID;
        this.text = text;

    }
}
