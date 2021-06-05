package com.nodz.wall;

import android.content.Context;

import java.util.ArrayList;

public class WallModel {

    String like, down, url;

    public WallModel(String like, String down, String url) {
        this.like = like;
        this.down = down;
        this.url = url;
    }

    public WallModel(Context getData, ArrayList<WallModel> list) {
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getUrl() {
        return url;
    }

    public int setUrl(String url) {
        this.url = url;
        return 0;
    }
}
