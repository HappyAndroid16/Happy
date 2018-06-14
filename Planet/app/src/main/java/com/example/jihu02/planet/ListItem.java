package com.example.jihu02.planet;

import android.widget.Button;

public class ListItem {
    String text;
    String stext;
    int resId;

    public ListItem(String text, String stext, int resId) {
        this.text = text;
        this.stext = stext;
        this.resId = resId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStext() {
        return stext;
    }

    public void setStext(String stext) {
        this.stext = stext;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }



}

