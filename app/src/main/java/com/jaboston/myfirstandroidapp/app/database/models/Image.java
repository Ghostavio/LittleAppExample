package com.jaboston.myfirstandroidapp.app.database.models;

/**
 * Created by josephboston on 13/05/2014.
 */
public class Image {

    private long id;
    private String url;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    // return the string url of the image that is stored in the Volley image cache.
    @Override
    public String toString(){
        return url;
    }

}
