package com.sabi.project.auctus.Fragment;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.text.InputType;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.j256.ormlite.dao.Dao;
        import com.sabi.project.auctus.Adapter.BidListAdapter;
        import com.sabi.project.auctus.Model.Auction;
        import com.sabi.project.auctus.Model.Bid;
        import com.sabi.project.auctus.Model.Item;
        import com.sabi.project.auctus.StaticData;
        import com.sabi.project.auctus.Model.User;
        import com.sabi.project.auctus.R;
        import com.sabi.project.auctus.Service.NotificationService;

        import java.sql.SQLException;
        import java.util.Date;

public class ItemAuctionsFragment extends Fragment {

    private Long id;
    private double min_price;
    private Auction auction;
    BidListAdapter adapter;

    public static ItemAuctionsFragment newInstance(Long id) {
        ItemAuctionsFragment fragment = new ItemAuctionsFragment();
        fragment.init(id);
        return fragment;
    }

    public void calculateMinPrice(){
        if (auction.getBids().isEmpty()){
            min_price = auction.getStartPrice();
        }else {
            min_price = auction.getBids().get(auction.getBids().size() - 1).getPrice() + 1;
        }
    }

    public void init(Long id){
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_bids, container, false);
        try {
            final Dao<Item, Long> itemDao = StaticData.helper.getItemDao();
            final Dao<Auction,Long> auctionDao = StaticData.helper.getAuctionDao();
            final Dao<Bid,Long> bidDao = StaticData.helper.getBidDao();
            final Dao<User,Long> userDao = StaticData.helper.getUserDao();

            Item item = itemDao.queryForId(id);

            View button = view.findViewById(R.id.auction_new_bid);

            item.setAuctions(auctionDao.queryBuilder().where().eq("item_id",item.getId()).query());

            TextView label1=(TextView) view.findViewById(R.id.lblNoAuctions);
            ListView listView = (ListView) view.findViewById(R.id.auction_bids);
        if (item.getAuctions().isEmpty() || item.getAuctions().get(item.getAuctions().size()-1).getStartDate().after(new Date()) ||
                (item.getAuctions().get(item.getAuctions().size()-1).getEndDate()!=null && item.getAuctions().get(item.getAuctions().size()-1).getEndDate().before(new Date()))){
            label1.setText(getString(R.string.item_no_auctions));
            button.setVisibility(View.INVISIBLE);
            label1.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);

        }else{
            auction = item.getAuctions().get(item.getAuctions().size()-1);

            if (auction.getUser().getId()==1){
                label1.setText(getString(R.string.auction_own));
                button.setVisibility(View.GONE);
                label1.setVisibility(View.VISIBLE);
            }
                auction.setBids(bidDao.queryBuilder().where().eq("auction_id", auction.getId()).query());

                adapter = new BidListAdapter(getActivity(), auction.getBids());
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);

                calculateMinPrice();

        }

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                        alertDialog.setTitle("Price");
                        alertDialog.setMessage("Enter bid price");
                        final EditText input = new EditText(getActivity());
                        input.setText(String.valueOf(min_price));
                        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        alertDialog.setView(input);

                        alertDialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String val = input.getText().toString();
                                        if (val.equals("")) {
                                            Toast.makeText(getActivity(),"Price must not be empty!",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Double price;
                                            try {
                                                price = Double.parseDouble(val);
                                                if (price >= min_price){
                                                    Bid bid = new Bid();
                                                    bid.setPrice(price);
                                                    bid.setDateTime(new Date());
                                                    bid.setAuction(auction);
                                                    bid.setUser(userDao.queryForId((long) 1));
                                                    bid = bidDao.createIfNotExists(bid);
                                                    auction.getBids().add(bid);
                                                    adapter.notifyDataSetChanged();
                                                    calculateMinPrice();
                                                    Toast.makeText(getActivity(),"Your bid has been accepted!",Toast.LENGTH_SHORT).show();
                                                    Intent serviceIntent = new Intent(getActivity(),NotificationService.class);
                                                    serviceIntent.putExtra("item",id);
                                                    getActivity().startService(serviceIntent);

                                                }else{
                                                    Toast.makeText(getActivity(),"Price must be higher than previous bids and/or start price!",Toast.LENGTH_SHORT).show();
                                                }

                                            } catch (Exception e) {
                                                Toast.makeText(getActivity(),"Price must be a number!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                }
        );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}