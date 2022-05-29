package com.example.ideafood.Module;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Posts {
    String postid, date, header, postname, username, category;
    ArrayList<String> content_post;
    boolean status;

    public Posts() {
    }

    public Posts(String postid, String date, String header, String postname, String username, String category, ArrayList<String> content_post, boolean status) {
        this.postid = postid;
        this.date = date;
        this.header = header;
        this.postname = postname;
        this.username = username;
        this.category = category;
        this.content_post = content_post;
        this.status = status;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
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

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getContent_post() {
        return content_post;
    }

    public void setContent_post(ArrayList<String> content_post) {
        this.content_post = content_post;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}


