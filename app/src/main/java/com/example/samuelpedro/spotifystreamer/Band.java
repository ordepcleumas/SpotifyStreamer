package com.example.samuelpedro.spotifystreamer;

/**
 * Created by Samuel on 25-06-2015.
 */
public class Band {
    private String name;
    private String id;
    private String image;

    public Band(String id, String name, String image) {
        this.setId(id);
        this.setName(name);
        this.setImage(image);

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
}
