package com.devashishthakur.pesapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Item> items = new ArrayList<>();
    postAdapter adapter;
    View view;
    String token;
    ProgressBar progressBar;
    int currentItems,totalItems,scrolledOutItems;
    Boolean isScrolling = false;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        token = "";
        recyclerView = (RecyclerView) view.findViewById(R.id.postList);
        manager = new LinearLayoutManager(getContext());
        adapter = new postAdapter(getContext(),items);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        getData();
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });

        getData();

        return view;
    }

    public void getData()
    {
        items.clear();
        //String url = BloggerAPI.url + "?key=" + BloggerAPI.key;
        String url = BloggerAPI.url + "search?q=Guide"+"&key=" + BloggerAPI.key;
        if (!(token.equals(""))){
            url = url+ "&pageToken="+ token;
        }
        if(token == null){
            return;
        }
        Call<PostList> postList = BloggerAPI.getService().getPostList(url);
        progressBar = (ProgressBar) view.findViewById(R.id.homeProgress);

        postList.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList list = response.body();

                token = list.getNextPageToken();
                try {
                    items.addAll(list.getItems());
                    adapter.notifyDataSetChanged();
                    //recyclerView.setAdapter(new postAdapter(MainActivity.this,list.getItems()));
                }
                catch (Exception e)
                {

                }
                progressBar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(GONE);
            }
        });
    }


}
