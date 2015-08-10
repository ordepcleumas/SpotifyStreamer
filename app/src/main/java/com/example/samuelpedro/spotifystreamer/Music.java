package com.example.samuelpedro.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Samuel on 01-07-2015.
 */
public class Music implements Parcelable {

    public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {
        /**
         * Create a new instance of the Parcelable class, instantiating it
         * from the given Parcel whose data had previously been written by
         * {@link Parcelable#writeToParcel Parcelable.writeToParcel()}.
         *
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        /**
         * Create a new array of the Parcelable class.
         *
         * @param size Size of the array.
         * @return Returns an array of the Parcelable class, with every entry
         * initialized to null.
         */
        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
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

    private Music(Parcel in) {
        id = in.readString();
        albumName = in.readString();
        trackName = in.readString();
        photoSmall = in.readString();
        photoLarge = in.readString();
        preview_url = in.readString();
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

    @Override
    public String toString() {
        return "Music{" +
                "albumName='" + albumName + '\'' +
                ", id='" + id + '\'' +
                ", trackName='" + trackName + '\'' +
                ", photoSmall='" + photoSmall + '\'' +
                ", photoLarge='" + photoLarge + '\'' +
                ", preview_url='" + preview_url + '\'' +
                '}';
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(albumName);
        dest.writeString(trackName);
        dest.writeString(photoSmall);
        dest.writeString(photoLarge);
        dest.writeString(preview_url);
    }
}
