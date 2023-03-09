package com.fahmiar.iotforkids.model;

public class Item {
    private String name,data,image,id_item, onSw, offSw;

    public Item(String id_item, String name, String data, String image, String onSw, String offSw){
        this.name = name;
        this.data = data;
        this.image = image;
        this.id_item = id_item;
        this.onSw = onSw;
        this.offSw = offSw;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImage() { return image;}

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_item() {return id_item;}

    public  void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getOnSw() {
        return onSw;
    }

    public void setOnSw(String onSw) {
        this.onSw = onSw;
    }

    public String getOffSw() {
        return offSw;
    }

    public void setOffSw(String offSw) {
        this.offSw = offSw;
    }
}
