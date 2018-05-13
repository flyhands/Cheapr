package kict.edu.my.cheapr.models;

/**
 * Created by anas on 5/12/18.
 */

public class Product {
    private String id;
    private String name;
    private String category;
    private String thumbnail;

    public Product(String id, String name, String category, String thumbnail) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.thumbnail = thumbnail;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return name;

    }
}
