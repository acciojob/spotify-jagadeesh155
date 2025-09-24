package com.driver;

import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class SpotifyRepository {

    private HashMap<Playlist, List<User>> playlistListenerMap;
    private HashMap<User, Playlist> creatorPlaylistMap;
    private HashMap<Playlist, List<Song>> playlistSongMap;
    private HashMap<Song, List<User>> songLikeMap;

    private HashMap<Album, List<Song>> albumSongMap;
    private HashMap<Artist, List<Album>> artistAlbumMap;
    private HashMap<Song, Album> songAlbumMap;
    private HashMap<Album, Artist> albumArtistMap;

    private List<User> users;
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<Album> albums;
    private List<Artist> artists;

    public SpotifyRepository() {
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        albumSongMap = new HashMap<>();
        artistAlbumMap = new HashMap<>();
        songAlbumMap = new HashMap<>();
        albumArtistMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User user = new User(name, mobile);
        users.add(user);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        artists.add(artist);
        artistAlbumMap.put(artist, new ArrayList<>());
        return artist;
    }

    public Album createAlbum(String title, String artistName) {
        Artist artist = null;
        for (Artist a : artists) {
            if (a.getName().equals(artistName)) {
                artist = a;
                break;
            }
        }
        if (artist == null) {
            artist = createArtist(artistName);
        }

        Album album = new Album(title);
        albums.add(album);

        albumSongMap.put(album, new ArrayList<>());
        albumArtistMap.put(album, artist);
        artistAlbumMap.get(artist).add(album);

        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {
        Album album = null;
        for (Album a : albums) {
            if (a.getTitle().equals(albumName)) {
                album = a;
                break;
            }
        }
        if (album == null) throw new Exception("Album does not exist");

        Song song = new Song(title, length);
        songs.add(song);

        albumSongMap.get(album).add(song);
        songAlbumMap.put(song, album);

        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        User user = getUser(mobile);
        if (user == null) throw new Exception("User does not exist");

        Playlist playlist = new Playlist(title);
        playlists.add(playlist);

        List<Song> playlistSongs = new ArrayList<>();
        for (Song s : songs) {
            if (s.getLength() == length) playlistSongs.add(s);
        }
        playlistSongMap.put(playlist, playlistSongs);

        creatorPlaylistMap.put(user, playlist);

        List<User> listeners = new ArrayList<>();
        listeners.add(user);
        playlistListenerMap.put(playlist, listeners);

        return playlist;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        User user = getUser(mobile);
        if (user == null) throw new Exception("User does not exist");

        Playlist playlist = new Playlist(title);
        playlists.add(playlist);

        List<Song> playlistSongs = new ArrayList<>();
        for (Song s : songs) {
            if (songTitles.contains(s.getTitle())) playlistSongs.add(s);
        }
        playlistSongMap.put(playlist, playlistSongs);

        creatorPlaylistMap.put(user, playlist);

        List<User> listeners = new ArrayList<>();
        listeners.add(user);
        playlistListenerMap.put(playlist, listeners);

        return playlist;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        User user = getUser(mobile);
        if (user == null) throw new Exception("User does not exist");

        Playlist playlist = null;
        for (Playlist p : playlists) {
            if (p.getTitle().equals(playlistTitle)) {
                playlist = p;
                break;
            }
        }
        if (playlist == null) throw new Exception("Playlist does not exist");

        if (creatorPlaylistMap.containsKey(user) && creatorPlaylistMap.get(user).equals(playlist))
            return playlist;

        List<User> listeners = playlistListenerMap.getOrDefault(playlist, new ArrayList<>());
        if (!listeners.contains(user)) {
            listeners.add(user);
            playlistListenerMap.put(playlist, listeners);
        }

        return playlist;
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
        User user = getUser(mobile);
        if (user == null) throw new Exception("User does not exist");

        Song song = null;
        for (Song s : songs) {
            if (s.getTitle().equals(songTitle)) {
                song = s;
                break;
            }
        }
        if (song == null) throw new Exception("Song does not exist");

        List<User> likers = songLikeMap.getOrDefault(song, new ArrayList<>());
        if (!likers.contains(user)) {
            likers.add(user);
            songLikeMap.put(song, likers);

            song.setLikes(song.getLikes() + 1);

            Album album = songAlbumMap.get(song);
            if (album != null) {
                Artist artist = albumArtistMap.get(album);
                if (artist != null) artist.setLikes(artist.getLikes() + 1);
            }
        }
        return song;
    }

    public String mostPopularArtist() {
        int max = -1;
        String name = "";
        for (Artist a : artists) {
            if (a.getLikes() > max) {
                max = a.getLikes();
                name = a.getName();
            }
        }
        return name;
    }

    public String mostPopularSong() {
        int max = -1;
        String title = "";
        for (Song s : songs) {
            if (s.getLikes() > max) {
                max = s.getLikes();
                title = s.getTitle();
            }
        }
        return title;
    }

    private User getUser(String mobile) {
        for (User u : users) {
            if (u.getMobile().equals(mobile)) return u;
        }
        return null;
    }
}
