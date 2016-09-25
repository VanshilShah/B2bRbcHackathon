package com.vanshil.rbchacks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vanshilshah on 24/09/16.
 */
public class Product {
    @Expose
    @SerializedName("product_name")
    String name;

    @Expose
    @SerializedName("store_id")
    String storeID;

    @Expose
    @SerializedName("keywords")
    List<String> keywords;

    public Product(String name, String storeID, List<String> keywords) {
        this.name = name;
        this.storeID = storeID;
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
