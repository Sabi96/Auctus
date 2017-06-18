package com.sabi.project.auctus.Dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sabi.project.auctus.Model.Auction;
import com.sabi.project.auctus.Model.Bid;
import com.sabi.project.auctus.Model.Item;
import com.sabi.project.auctus.StaticData;
import com.sabi.project.auctus.Model.User;
import com.sabi.project.auctus.R;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "auctus.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Long> userDao = null;
    private Dao<Auction, Long> auctionDao = null;
    private Dao<Item, Long> itemDao = null;
    private Dao<Bid, Long> bidDao = null;
    private Context c;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    private static DatabaseHelper helper = null;

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (helper == null) {
            helper = new DatabaseHelper(context);
            try {
                helper.auctionDao = helper.getAuctionDao();
                helper.userDao = helper.getUserDao();
                helper.bidDao = helper.getBidDao();
                helper.itemDao = helper.getItemDao();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Item.class);
            TableUtils.createTable(connectionSource, Auction.class);
            TableUtils.createTable(connectionSource, Bid.class);
            fillDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void fillDatabase() throws SQLException{
        Dao<User, Long> userDao = StaticData.helper.getUserDao();

        userDao.createIfNotExists(new User(null,"Sabi Franjo","sabifranjo@gmail.com","sabi","/Android/data/com.sabi.project.auctus/images_users/me.png","Strosmajerova 23","0641118590",new ArrayList<Auction>(),new ArrayList<Bid>()));
        userDao.createIfNotExists(new User(null,"Nikola Trtic","trle@gmail.com","trle","/Android/data/com.sabi.project.auctus/images_users/trle.png","Zrtava Fasizma 30","0641118590",new ArrayList<Auction>(),new ArrayList<Bid>()));
        userDao.createIfNotExists(new User(null,"Aleksandar Stefanovic","coa@gmail.com","coa","/Android/data/com.sabi.project.auctus/images_users/coa.png","Njegoseva 11","0644990006",new ArrayList<Auction>(),new ArrayList<Bid>()));



        Dao<Item,Long> itemDao = StaticData.helper.getItemDao();
        itemDao.createIfNotExists(new Item(null,"Sony PS4","Sony Playstation 4 in perfect condition, bought it last year, got tired of it.","/Android/data/com.sabi.project.auctus/images_items/ps4.png",true, new ArrayList<Auction>()));
        itemDao.createIfNotExists(new Item(null,"PS4 Controller","Sony Playstation 4 Dualshock 4 Controller in good condition, I have been using it since the console first came out.","/Android/data/com.sabi.project.auctus/images_items/ps4con.png",false, new ArrayList<Auction>()));
        itemDao.createIfNotExists(new Item(null,"Xbox 360","Microsoft's not so new gaming console, the Xbox 360, working very well, without any cosmetic wear.","/Android/data/com.sabi.project.auctus/images_items/360.png",false, new ArrayList<Auction>()));
        itemDao.createIfNotExists(new Item(null,"PlayStation VR Headset","Step into the world of VR with these new Sony PlayStation 4 headsets.","/Android/data/com.sabi.project.auctus/images_items/PSVR.png",true, new ArrayList<Auction>()));
        itemDao.createIfNotExists(new Item(null,"Nintendo 3ds","It's Nintendo's best-selling handheld console, but now with more power and more 3D!","/Android/data/com.sabi.project.auctus/images_items/3ds.png",true, new ArrayList<Auction>()));
        itemDao.createIfNotExists(new Item(null,"Nintendo Wii","Nintendo does it again! The Wii is a family gaming console with motion control capabilities.","/Android/data/com.sabi.project.auctus/images_items/wii.png",false, new ArrayList<Auction>()));

        List<User> users = userDao.queryForAll();
        List<Item> items = itemDao.queryForAll();

        Dao<Auction,Long> auctionDao = StaticData.helper.getAuctionDao();
        auctionDao.createIfNotExists(new Auction(null , 20000 , new GregorianCalendar(2017,4,10).getTime(), new GregorianCalendar(2017,4,20).getTime(),users.get(0),items.get(0),new ArrayList<Bid>()));
        auctionDao.createIfNotExists(new Auction(null , 5000 , new GregorianCalendar(2017,5,1).getTime(), new GregorianCalendar(2017,5,30).getTime(),users.get(0),items.get(1),new ArrayList<Bid>()));
        auctionDao.createIfNotExists(new Auction(null , 12000 , new GregorianCalendar(2017,4,30).getTime(), new GregorianCalendar(2017,5,25).getTime(),users.get(1),items.get(2),new ArrayList<Bid>()));
        auctionDao.createIfNotExists(new Auction(null , 15000 , new GregorianCalendar(2017,3,1).getTime(), new GregorianCalendar(2017,4,1).getTime(),users.get(1),items.get(3),new ArrayList<Bid>()));
        auctionDao.createIfNotExists(new Auction(null , 16000 , new GregorianCalendar(2017,4,5).getTime(), new GregorianCalendar(2017,4,25).getTime(),users.get(2),items.get(3),new ArrayList<Bid>()));
        auctionDao.createIfNotExists(new Auction(null , 7000 , new GregorianCalendar(2017,4,15).getTime(), new GregorianCalendar(2017,4,20).getTime(),users.get(2),items.get(4),new ArrayList<Bid>()));
        auctionDao.createIfNotExists(new Auction(null , 7000 , new GregorianCalendar(2017,5,1).getTime(), new GregorianCalendar(2017,5,25).getTime(),users.get(1),items.get(4),new ArrayList<Bid>()));
        auctionDao.createIfNotExists(new Auction(null , 6000 , new GregorianCalendar(2017,4,1).getTime(), new GregorianCalendar(2017,6,31).getTime(),users.get(2),items.get(5),new ArrayList<Bid>()));

        List<Auction> auctions = auctionDao.queryForAll();

        Dao<Bid,Long> bidDao= StaticData.helper.getBidDao();
        bidDao.createIfNotExists(new Bid(null, 20500, new GregorianCalendar(2017,4,11).getTime(),auctions.get(0),users.get(1)));
        bidDao.createIfNotExists(new Bid(null, 21000, new GregorianCalendar(2017,4,12).getTime(),auctions.get(0),users.get(2)));
        bidDao.createIfNotExists(new Bid(null, 22000, new GregorianCalendar(2017,4,15).getTime(),auctions.get(0),users.get(1)));
        bidDao.createIfNotExists(new Bid(null, 22100, new GregorianCalendar(2017,4,16).getTime(),auctions.get(0),users.get(2)));
        bidDao.createIfNotExists(new Bid(null, 22500, new GregorianCalendar(2017,4,19).getTime(),auctions.get(0),users.get(1)));

        bidDao.createIfNotExists(new Bid(null, 5001, new GregorianCalendar(2017,5,1).getTime(),auctions.get(1),users.get(1)));

        bidDao.createIfNotExists(new Bid(null, 12100, new GregorianCalendar(2017,5,1).getTime(),auctions.get(2),users.get(0)));
        bidDao.createIfNotExists(new Bid(null, 12200, new GregorianCalendar(2017,5,2).getTime(),auctions.get(2),users.get(2)));

        bidDao.createIfNotExists(new Bid(null, 15100, new GregorianCalendar(2017,3,5).getTime(),auctions.get(3),users.get(2)));
        bidDao.createIfNotExists(new Bid(null, 15500, new GregorianCalendar(2017,3,8).getTime(),auctions.get(3),users.get(0)));
        bidDao.createIfNotExists(new Bid(null, 16600, new GregorianCalendar(2017,3,9).getTime(),auctions.get(3),users.get(2)));

        bidDao.createIfNotExists(new Bid(null, 16050, new GregorianCalendar(2017,4,6).getTime(),auctions.get(4),users.get(0)));
        bidDao.createIfNotExists(new Bid(null, 16200, new GregorianCalendar(2017,4,8).getTime(),auctions.get(4),users.get(1)));
        bidDao.createIfNotExists(new Bid(null, 16250, new GregorianCalendar(2017,4,8).getTime(),auctions.get(4),users.get(0)));
        bidDao.createIfNotExists(new Bid(null, 16400, new GregorianCalendar(2017,4,24).getTime(),auctions.get(4),users.get(1)));
        bidDao.createIfNotExists(new Bid(null, 16200, new GregorianCalendar(2017,4,24).getTime(),auctions.get(4),users.get(0)));

        bidDao.createIfNotExists(new Bid(null, 7010, new GregorianCalendar(2017,4,17).getTime(),auctions.get(5),users.get(1)));

        bidDao.createIfNotExists(new Bid(null, 6500, new GregorianCalendar(2017,4,17).getTime(),auctions.get(7),users.get(1)));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Bid.class, true);
            TableUtils.dropTable(connectionSource, Auction.class, true);
            TableUtils.dropTable(connectionSource, Item.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<User, Long> getUserDao() throws SQLException{
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<Auction, Long> getAuctionDao() throws SQLException{
        if (auctionDao == null) {
            auctionDao = getDao(Auction.class);
        }
        return auctionDao;
    }

    public Dao<Item, Long> getItemDao() throws SQLException{
        if (itemDao == null) {
            itemDao = getDao(Item.class);
        }
        return itemDao;
    }

    public Dao<Bid, Long> getBidDao() throws SQLException{
        if (bidDao == null) {
            bidDao = getDao(Bid.class);
        }
        return bidDao;
    }

    @Override
    public void close() {
        super.close();
        userDao=null;
        auctionDao = null;
        itemDao = null;
        bidDao = null;
    }
}
