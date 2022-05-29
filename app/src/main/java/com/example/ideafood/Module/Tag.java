package com.example.ideafood.Module;

public class Tag {
    String tagid,category;

    public Tag() {
    }

    public Tag(String tagid, String category) {
        this.tagid = tagid;
        this.category = category;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
