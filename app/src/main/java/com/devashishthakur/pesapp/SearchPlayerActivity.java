package com.devashishthakur.pesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchPlayerActivity extends AppCompatActivity {

    RecyclerView mPlayersList;
    ProgressBar progressBar;
    AllPlayersAdapter testAdapter;
    List<Players> playersList;
    LinearLayout navigation;
    LinearLayoutManager manager;
    EditText search_layout;
    Button prev,next;
    ImageButton searchButton;
    int currentItems,totalItems,scrolledOutItems;
    boolean isScrolling = false;
    int pageID = 1;
    Toolbar toolbar;
    LinearLayout error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        error = (LinearLayout) findViewById(R.id.Error);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigation = (LinearLayout) findViewById(R.id.navigation);
        mPlayersList = (RecyclerView) findViewById(R.id.postList);
        search_layout = (EditText) findViewById(R.id.search_layout);

        searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playersList.clear();
                progressBar.setVisibility(View.VISIBLE);
                getSearchData();
            }
        });
        mPlayersList.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        mPlayersList.setLayoutManager(manager);
        playersList = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.homeProgress);


        mPlayersList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();
                if(isScrolling && (currentItems + scrolledOutItems == totalItems))
                {
                    isScrolling = false;
                    try{
                        navigation.setVisibility(View.VISIBLE);
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });

        prev = (Button) findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageID > 1) {
                    pageID--;
                    playersList.clear();
                    getData();
                }
            }
        });
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageID < 18) {
                    pageID++;
                    playersList.clear();
                    getData();
                }
            }
        });
        /*
        testList.add(new Test(
           "ABC",
           "DSDS",
                "5.0"
        ));
        testList.add(new Test(
                "DEF",
                "DSDS",
                "5.0"
        ));
        */
        getData();
    }

    void getSearchData()
    {

        for(int i=1;i<=18;i++) {
            DatabaseReference dbProducts = FirebaseDatabase.getInstance().getReference("player").child(String.valueOf(i));
            Query searchQuerry = dbProducts.orderByChild("playerpesname")
                    .startAt(search_layout.getText().toString().toUpperCase())
                    .endAt(search_layout.getText().toString().toUpperCase() + "\uf8ff");
            searchQuerry.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                            Players p = productSnapshot.getValue(Players.class);
                            playersList.add(p);
                        }
                        testAdapter = new AllPlayersAdapter(SearchPlayerActivity.this, playersList, pageID);
                        mPlayersList.setAdapter(testAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                    else
                    {

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
        navigation.setVisibility(View.GONE);
    }

    void getData()
    {
        DatabaseReference dbProducts = FirebaseDatabase.getInstance().getReference("player").child(String.valueOf(pageID));

        dbProducts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                        Players p = productSnapshot.getValue(Players.class);
                        playersList.add(p);
                    }
                    testAdapter = new AllPlayersAdapter(SearchPlayerActivity.this, playersList,pageID);
                    mPlayersList.setAdapter(testAdapter);
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    public void onClickBack(View view)
    {
        finish();
    }

    public void onClickTryAgain(View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        getData();


    }
}
