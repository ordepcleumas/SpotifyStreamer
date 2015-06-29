package com.example.samuelpedro.spotifystreamer;

/**
 * Created by Samuel on 25-06-2015.
 */
public class Band {
    private int id;
    private String name;
    private String image;

    public Band() {
    }

    public Band(int id, String name) {
        this.setId(id);
        this.setName(name);

    }

    public Band(int id, String name, String image) {
        this.setId(id);
        this.setName(name);
        this.setImage(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
