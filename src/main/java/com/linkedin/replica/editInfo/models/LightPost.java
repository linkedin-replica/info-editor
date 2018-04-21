package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public class LightPost {
    String postID;
    String authorID;
    ArrayList<Media>media;
    public LightPost()
    {

    }
    public LightPost(String postID,String authorID,ArrayList<Media>media){
        this.authorID = authorID;
        this.postID = postID;
        this.media = media;

    }
}
