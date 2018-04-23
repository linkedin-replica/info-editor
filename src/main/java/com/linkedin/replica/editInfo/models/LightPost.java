package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public class LightPost {
    public String getPostID() {
        return postID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getText() {
        return text;
    }

    String postID;
    String authorID;
   String text;
    public LightPost()
    {

    }
    public LightPost(String postID,String authorID,String text){
        this.authorID = authorID;
        this.postID = postID;
        this.text = text;

    }
}
