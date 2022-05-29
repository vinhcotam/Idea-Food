package com.example.ideafood.classs;

import java.util.ArrayList;

public class Post {
    String userid;
    String category;
    String date;
    String header;
    String postname;
    String postid;
    Boolean status;
    ArrayList<String> content_post;

    public Post() {
    }

    public Post(String userid, String category, String postid, String date, String header, String name, Boolean status, ArrayList<String> content_post) {
        this.userid = userid;
        this.category = category;
        this.date = date;
        this.header = header;
        this.postname = name;
        this.status = status;
        this.content_post = content_post;
        this.postid = postid;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPostName() {
        return postname;
    }

    public void setPostName(String name) {
        this.postname = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<String> getContent_post() {
        return content_post;
    }

    public void setContent_post(ArrayList<String> content_post) {
        this.content_post = content_post;
    }
}
