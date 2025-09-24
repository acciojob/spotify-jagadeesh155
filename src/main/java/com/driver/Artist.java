package com.driver;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private String name;
    private int likes;
    private List<Album> albums; // ✅ Needed

    public Artist(){
        this.albums = new ArrayList<>();
    }

    public Artist(String name){
        this.name = name;
        this.likes = 0;
        this.albums = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
