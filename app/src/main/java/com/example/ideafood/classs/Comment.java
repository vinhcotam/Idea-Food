package com.example.ideafood.classs;

import java.util.Random;

public class Comment {
    String commentid;
    String content;
    String date;
    String fatherid;
    String postid;
    String userid;

    public Comment(String commentid, String content, String date, String fatherid, String postid, String userid) {
        this.commentid = commentid;
        this.content = content;
        this.date = date;
        this.fatherid = fatherid;
        this.postid = postid;
        this.userid = userid;
    }

    public Comment() {
    }
    //test
    public Comment(String content) {
        commentid = Integer.toString((new Random()).nextInt());
        this.content = content;
        this.date = "1/1/2000";
        this.fatherid = "-1";
        this.postid = "post_test";
        this.userid = "user_test";

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
