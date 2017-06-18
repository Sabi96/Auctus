package com.sabi.project.auctus.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Sabi on 21.4.2017..
 */

@DatabaseTable(tableName = "bids")
public class Bid {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false)
    public double price;
    @DatabaseField(canBeNull = false)
    public Date dateTime;
    @DatabaseField(columnName = "auction_id", foreign = true, foreignAutoRefresh = true, canBeNull = false)
    public Auction auction;
    @DatabaseField(columnName = "user_id", foreign = true, foreignAutoRefresh = true, canBeNull = false)
    public User user;

    public Bid(Long id, double price, Date dateTime, Auction auction, User user) {
        this.id = id;
        this.price = price;
        this.dateTime = dateTime;
        this.auction = auction;
        this.user = user;
    }

    public Bid() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
