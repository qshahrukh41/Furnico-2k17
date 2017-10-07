package com.log2n.rentafurniture;

import java.io.Serializable;

/**
 * Created by shahrukh on 9/7/17.
 */

public class ImageUpload implements Serializable{

    public String name;
    public String url;
    public String location;
    public String rent;
    public String email;
    public String upload_id;
    public String image_id;


    public String getName() {
        return name;
    }

    public String getUrl(){
        return url;
    }

    public String getLocation(){
        return location;
    }

    public String getRent(){
        return rent;
    }

    public String getEmail() {
        return email;
    }

    public String getUpload_id() {
        return upload_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public ImageUpload(String name, String url, String location, String rent, String email, String upload_id, String image_id){
        this.name=name;
        this.url=url;
        this.location=location;
        this.rent=rent;
        this.email=email;
        this.upload_id=upload_id;
        this.image_id=image_id;
    }

    public ImageUpload(){}
}
