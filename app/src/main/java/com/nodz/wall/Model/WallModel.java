package com.nodz.wall.Model;

import android.content.Context;

import java.util.ArrayList;

public class WallModel {

    String tag, down, url;
    Context context;
    ArrayList<WallModel> list;

    public WallModel(String tag, String down, String url) {
        this.tag = tag;
        this.down = down;
        this.url = url;
    }

    public WallModel(Context context, ArrayList<WallModel> list) {
        this.context = context;
        this.list = list;
    }

    public WallModel() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag){
        this.tag = tag;
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
