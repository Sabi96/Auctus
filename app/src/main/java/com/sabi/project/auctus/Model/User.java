package com.sabi.project.auctus.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by Sabi on 21.4.2017..
 */

@DatabaseTable(tableName = "users")
public class User {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private String email;
    @DatabaseField(canBeNull = false)
    private String password;
    @DatabaseField
    private String picture;
    @DatabaseField
    private String address;
    @DatabaseField
    private String phone;
    //@DatabaseField(foreign = true, foreignAutoRefresh = true)
    private List<Auction> auctions;
    //@DatabaseField(foreign = true, foreignAutoRefresh = true)
    private List<Bid> bids;

    public User(Long id, String name, String email, String password, String picture, String address, String phone, List<Auction> auctions, List<Bid> bids) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.address = address;
        this.phone = phone;
        this.auctions = auctions;
        this.bids = bids;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
