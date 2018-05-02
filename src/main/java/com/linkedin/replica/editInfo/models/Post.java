package com.linkedin.replica.editInfo.models;


import java.util.ArrayList;

public class Post {
    private String authorId, postId;
    private String title, text;
    private ArrayList<String> images, videos, likers;
    private int commentsCount;
    private long timestamp;
    private boolean isCompanyPost, isArticle, liked;
    private String authorName, authorProfilePictureUrl;
    private String headline;

    public Post() { }

    public Post(String authorId, String postId, String title, String text, ArrayList<String> images,
                ArrayList<String> videos, ArrayList<String> likers, int commentsCount, long timestamp,
                boolean isCompanyPost, boolean isArticle, boolean liked, String authorName,
                String authorProfilePictureUrl, String headline) {

        this.authorId = authorId;
        this.postId = postId;
        this.title = title;
        this.text = text;
        this.images = images;
        this.videos = videos;
        this.likers = likers;
        this.commentsCount = commentsCount;
        this.timestamp = timestamp;
        this.isCompanyPost = isCompanyPost;
        this.isArticle = isArticle;
        this.liked = liked;
        this.authorName = authorName;
        this.authorProfilePictureUrl = authorProfilePictureUrl;
        this.headline = headline;
    }

    @Override
    public boolean equals(Object obj) {
        Post objPost = (Post) obj;
        System.out.println(postId + " " + objPost.postId);
        return objPost.postId.equals(this.postId);
    }


    public String getPostId() {
        return postId;
    }
}