package com.example.ideafood.Module;

import java.util.Random;

public class Comment {
    String commentid;
    String content;
    String date;
    String fatherid;
    String postid;
    String username;

    public Comment() {
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFatherid() {
        return fatherid;
    }

    public void setFatherid(String fatherid) {
        this.fatherid = fatherid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Comment(String commentid, String content, String date, String fatherid, String postid, String username) {
        this.commentid = commentid;
        this.content = content;
        this.date = date;
        this.fatherid = fatherid;
        this.postid = postid;
        this.username = username;
    }
}
