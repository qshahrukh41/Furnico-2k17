package com.log2n.rentafurniture;

/**
 * Created by shahrukh on 8/20/17.
 */

public class Item {
    private String itemName;
    private String Location;
    private String Rent;

    public Item() {
    }

    public Item( String itemName, String Location,String Rent ){
        this.itemName = itemName;
        this.Location = Location;
        this.Rent=Rent;
    }



    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getRent() {
        return Rent;
    }

    public void setRent(String Rent) {
        this.Rent = Rent;
    }
}
