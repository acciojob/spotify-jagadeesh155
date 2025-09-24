package com.driver;

import java.util.Date;

public class Album {
    private String title;
    private Date releaseDate;
    private int likes;

    public Album() {}

    public Album(String title) {
        this.title = title;
        this.releaseDate = new Date();
        this.likes = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
