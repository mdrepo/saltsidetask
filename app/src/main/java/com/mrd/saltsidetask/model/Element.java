package com.mrd.saltsidetask.model;

import java.io.Serializable;

/**
 * Created by mayurdube on 19/03/17.
 */

public class Element implements Serializable{

    String image;
    String description;
    String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
