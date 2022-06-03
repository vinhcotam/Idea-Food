package com.example.ideafood.Module;

import java.util.ArrayList;

public class Dsach {
    String dsachid,dsachname,username,date;
    ArrayList<String> postid;

    public Dsach() {
    }

    public Dsach(String dsachid, String dsachname, String username, String date, ArrayList<String> postid) {
        this.dsachid = dsachid;
        this.dsachname = dsachname;
        this.username = username;
        this.date = date;
        this.postid = postid;
    }

    public String getDsachid() {
        return dsachid;
    }

    public void setDsachid(String dsachid) {
        this.dsachid = dsachid;
    }

    public String getDsachname() {
        return dsachname;
    }

    public void setDsachname(String dsachname) {
        this.dsachname = dsachname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getPostid() {
        return postid;
    }

    public void setPostid(ArrayList<String> postid) {
        this.postid = postid;
    }
}
