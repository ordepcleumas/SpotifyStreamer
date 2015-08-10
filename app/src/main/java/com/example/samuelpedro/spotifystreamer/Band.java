package com.example.samuelpedro.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Samuel on 25-06-2015.
 */
public class Band implements Parcelable {
    public static final Parcelable.Creator<Band> CREATOR = new Parcelable.Creator<Band>() {

        /**
         * Create a new instance of the Parcelable class, instantiating it
         * from the given Parcel whose data had previously been written by
         * {@link Parcelable#writeToParcel Parcelable.writeToParcel()}.
         *
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Band createFromParcel(Parcel source) {
            return new Band(source);
        }

        /**
         * Create a new array of the Parcelable class.
         *
         * @param size Size of the array.
         * @return Returns an array of the Parcelable class, with every entry
         * initialized to null.
         */
        @Override
        public Band[] newArray(int size) {
            return new Band[size];
        }
    };
    private String id;
    private String name;
    private String image;

    public Band() {
    }

    public Band(String id, String name) {
        this.setId(id);
        this.setName(name);

    }

    public Band(String id, String name, String image) {
        this.setId(id);
        this.setName(name);
        this.setImage(image);
    }

    private Band(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Band{" +
                "CREATOR=" + CREATOR +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
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
        dest.writeString(name);
        dest.writeString(image);
    }
}
