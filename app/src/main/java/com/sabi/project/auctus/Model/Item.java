package com.sabi.project.auctus.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by Sabi on 21.4.2017..
 */

@DatabaseTable(tableName = "items")
public class Item {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private String description;
    @DatabaseField
    private String picture;
    @DatabaseField(canBeNull = false)
    private boolean sold;
    //@DatabaseField(foreign = true)
    private List<Auction> auctions;

    public Item(Long id, String name, String description, String picture, boolean sold, List<Auction> auctions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.sold = sold;
        this.auctions = auctions;
    }

    public Item() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
}
