package com.example.samuelpedro.spotifystreamer;

/**
 * Created by Samuel on 01-07-2015.
 */
public class Music {
    private String id;
    private String albumName;
    private String trackName;
    private String photoSmall;
    private String photoLarge;
    private String preview_url;


    public Music(String id, String albumName, String trackName) {
        this.id = id;
        this.albumName = albumName;
        this.trackName = trackName;
    }

    public Music(String id, String albumName, String trackName, String photoSmall, String photoLarge) {
        this.id = id;
        this.albumName = albumName;
        this.trackName = trackName;
        this.photoSmall = photoSmall;
        this.photoLarge = photoLarge;
    }

    public Music(String id, String albumName, String trackName, String photoSmall, String photoLarge, String preview_url) {
        this.id = id;
        this.albumName = albumName;
        this.trackName = trackName;
        this.photoSmall = photoSmall;
        this.photoLarge = photoLarge;
        this.preview_url = preview_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getPhotoSmall() {
        return photoSmall;
    }

    public void setPhotoSmall(String photoSmall) {
        this.photoSmall = photoSmall;
    }

    public String getPhotoLarge() {
        return photoLarge;
    }

    public void setPhotoLarge(String photoLarge) {
        this.photoLarge = photoLarge;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }
}
