package com.devashishthakur.pesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    GuideFragment guideFragment;
    NewsFragment newsFragment;
    PatchFragment patchFragment;
    BottomNavigationView bottomNav;
    FrameLayout frameLayout;
    Toolbar toolbar;
    FloatingActionButton searchButton;

    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolBar();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        searchButton = (FloatingActionButton) findViewById(R.id.search_button_fab);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchPlayerActivity.class);
                startActivity(intent);
            }
        });
        bottomNav = (BottomNavigationView) findViewById(R.id.nav_bottom_view);
        frameLayout = (FrameLayout) findViewById(R.id.nav_frame);

        guideFragment = new GuideFragment();
        patchFragment = new PatchFragment();
        newsFragment = new NewsFragment();

        setFragment(guideFragment,"guide");

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case (R.id.nav_reader):
                        setFragment(guideFragment,"guide");
                        return true;
                    case(R.id.nav_social):
                        setFragment(patchFragment,"patch");
                        return true;
                    case (R.id.nav_ask):
                        setFragment(newsFragment, "news");

                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_video) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("url", "https://www.youtube.com/channel/UCfgHp8uIcjE__RvrQZkYZaA");
            //intent.putExtra("link",firstWord);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void setFragment(Fragment fragment,String tag) {

        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_frame,fragment,tag);
        fragmentTransaction.commit();
    }

    void setUpToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ThePesApp");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //actionBarDrawerToggle =  new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        //drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();
    }

}
