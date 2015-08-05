package com.sample.orm.ormsample.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Thanh Nguyen on 8/4/2015.
 */
public class Contact extends BaseModel implements Serializable {
    @DatabaseField
    private String name;

    @DatabaseField
    private String image;

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
