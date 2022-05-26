package com.example.ideafood.Module;

public class Video {
    String videoid,videoname,link,postid;

    public Video() {
    }

    public Video(String videoid, String videoname, String link, String postid) {
        this.videoid = videoid;
        this.videoname = videoname;
        this.link = link;
        this.postid = postid;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}
