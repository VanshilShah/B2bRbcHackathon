package com.vanshil.rbchacks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vanshilshah on 24/09/16.
 */
public class Store {
    @Expose
    @SerializedName("name")
    String name;

    @Expose
    @SerializedName("location")
    String location;

    @Expose
    @SerializedName("latitude")
    double latitude;

    @Expose
    @SerializedName("longitude")
    double longitude;

    @Expose
    @SerializedName("products")
    List<Integer> products;

    @Expose
    @SerializedName("email")
    String email;

    @Expose
    @SerializedName("number")
    String number;

    @Expose
    @SerializedName("rbc_prime")
    boolean prime;

    public Store(String name, String location, double latitude, double longitude, List<Integer> products, String email, String number, boolean prime) {
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.products = products;
        this.email = email;
        this.number = number;
        this.prime = prime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Integer> getProducts() {
        return products;
    }

    public void setProducts(List<Integer> products) {
        this.products = products;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isPrime() {
        return prime;
    }

    public void setPrime(boolean prime) {
        this.prime = prime;
    }
}
