package com.example.admin.eulerityhackathon;

import java.util.ArrayList;

/**
 * Created by admin on 3/30/18.
 */

public class Event {

    private String url;

    /*
     * @param eventUrl = “url” attribute pointing to a target image.
     */
    public Event(String url){
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
