package com.example.ideafood.Module;

public class Img {
    String imgid,linkimg,imgname,postid;

    public Img() {
    }

    public Img(String imgid, String linkimg, String imgname, String postid) {
        this.imgid = imgid;
        this.linkimg = linkimg;
        this.imgname = imgname;
        this.postid = postid;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getLinkimg() {
        return linkimg;
    }

    public void setLinkimg(String linkimg) {
        this.linkimg = linkimg;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}
