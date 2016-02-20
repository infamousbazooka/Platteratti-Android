package com.amberzile.magnusfernandes.platteratti;

public class Item {
    private String title, description;
    private int price;
    private byte[] image;

    public Item() {
    }

    public Item(String title) {
        this.title = title;
    }

    public Item(String description, byte[] image, int price, String title) {
        this.description = description;
        this.image = image;
        this.price = price;
        this.title = title;
    }

    public Item(String title, String description, int price, byte[] image) {
        this.setTitle(title);
        this.setDescription(description);
        this.setPrice(price);
        this.setImage(image);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
