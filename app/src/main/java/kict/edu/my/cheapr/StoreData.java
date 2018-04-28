package kict.edu.my.cheapr;

import java.util.Date;

/**
 * Created by User on 17/4/2018.
 */

public class StoreData {

    private String id;
    private String item_name;
    private String category;
    private String category_name;
    private String supermarket;
    private Date dateCreated;
    private Date dateUpdated;

    public void StoreData(){

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItem_name(String name) {
        this.item_name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setSupermarket(String supermarket) {
        this.supermarket = supermarket;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getId() {

        return id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getCategory() {
        return category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getSupermarket() {
        return supermarket;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

//    public StoreData(String id, String item_name, String category, String category_name, String supermarket, Date dateCreated, Date dateUpdated) {
//        this.id = id;
//        this.item_name = item_name;
//        this.category = category;
//        this.category_name = category_name;
//        this.supermarket = supermarket;
//        this.dateCreated = dateCreated;
//        this.dateUpdated = dateUpdated;
//
//    }
    @Override
    public String toString() {return item_name;}
}
