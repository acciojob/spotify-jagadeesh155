package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SpotifyService {

    @Autowired
    private SpotifyRepository spotifyRepository;

    // ----------------- USER -----------------
    public User createUser(String name, String mobile){
        return spotifyRepository.createUser(name, mobile);
    }

    // ----------------- ARTIST -----------------
    public Artist createArtist(String name){  // fixed return type
        return spotifyRepository.createArtist(name);
    }

    // ----------------- ALBUM -----------------
    public Album createAlbum(String title, String artistName){
        return spotifyRepository.createAlbum(title, artistName);
    }

    // ----------------- SONG -----------------
    public Song createSong(String title, String albumName, int length) throws Exception{
        return spotifyRepository.createSong(title, albumName, length);
    }

    // ----------------- PLAYLIST -----------------
    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception{
        return spotifyRepository.createPlaylistOnLength(mobile, title, length);
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception{
        return spotifyRepository.createPlaylistOnName(mobile, title, songTitles);
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception{
        return spotifyRepository.findPlaylist(mobile, playlistTitle);
    }

    // ----------------- LIKE -----------------
    public Song likeSong(String mobile, String songTitle) throws Exception{
        return spotifyRepository.likeSong(mobile, songTitle);
    }

    // ----------------- POPULAR -----------------
    public String mostPopularArtist(){
        return spotifyRepository.mostPopularArtist();
    }

    public String mostPopularSong(){
        return spotifyRepository.mostPopularSong();
    }
}
