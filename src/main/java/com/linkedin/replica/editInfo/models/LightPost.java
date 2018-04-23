package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public class LightPost {
    public String getPostID() {
        return postId;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getText() {
        return text;
    }

   public String postId;
    String authorID;
   String text;
   String commentsCount;
   ArrayList<String> images;
    ArrayList<String> videos;
   public boolean isArticle;
   boolean isCompanyPost;
   ArrayList<String>likers;

    public LightPost()
    {

    }
    public LightPost(String postID,String authorID,String text){
        this.authorID = authorID;
        this.postId = postID;
        this.text = text;

    }
}
