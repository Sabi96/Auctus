package com.sabi.project.auctus.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.List;

/**
 * Created by Sabi on 21.4.2017..
 */
@DatabaseTable(tableName = "auctions")
public class Auction {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false)
    private double startPrice;
    @DatabaseField(canBeNull = false)
    private Date startDate;
    @DatabaseField
    private Date endDate;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private User user;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "item_id")
    private Item item;
    //@DatabaseField(foreign = true)
    private List<Bid> bids;

    public Auction(Long id, double startPrice, Date startDate, Date endDate, User user, Item item, List<Bid> bids) {
        this.id = id;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.item = item;
        this.bids = bids;
    }

    public Auction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
