package com.example.ideafood.Module;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Posts {
    String postid, date, header, postname, userid, category;
    ArrayList<String> content_post;

    public Posts() {
    }

    public Posts(String postid, String date, String header, String postname, String userid, String category, ArrayList<String> content_post) {
        this.postid = postid;
        this.date = date;
        this.header = header;
        this.postname = postname;
        this.userid = userid;
        this.category = category;
        this.content_post = content_post;
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

    public ArrayList<String> getContent_post() {
        return content_post;
    }

    public void setContent_post(ArrayList<String> content_post) {
        this.content_post = content_post;
    }
}


