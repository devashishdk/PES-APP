package com.devashishthakur.pesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AllPlayersAdapter  extends RecyclerView.Adapter<AllPlayersAdapter.TestViewHolder>{

    Context mCtx;
    List<Players> playersList;
    int pageID;
    AllPlayersAdapter(Context mCtx,List<Players> testList,int pageID)
    {
        this.mCtx = mCtx;
        this.playersList = testList;
        this.pageID = pageID;
    }
    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.single_player,
                parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Players test = playersList.get(position);

        holder.title.setText(test.getPlayerpesname());
        holder.desc.setText(test.getOverallpesrating());
        int rating_p = Integer.valueOf(holder.desc.getText().toString());

        if(rating_p >= 90)
        {
            holder.desc.setTextColor(Color.parseColor("#1565C0"));
        }
        else if(rating_p >= 85 && rating_p < 90)
        {
            holder.desc.setTextColor(Color.parseColor("#4CAF50"));
        }
        else if(rating_p >= 80 && rating_p < 85)
        {
            holder.desc.setTextColor(Color.parseColor("#F9A825"));
        }
        else
        {
            holder.desc.setTextColor(Color.parseColor("#757575"));

        }


        String pos = String.valueOf(test.getPosition());
        holder.posi.setText(pos);

        if(pos.equals("CF")||pos.equals("RWF")||pos.equals("LWF")||pos.equals("SS"))
        {
            holder.posi.setTextColor(Color.parseColor("#d32f2f"));
        }
        else if(pos.equals("CMF")||pos.equals("RMF")||pos.equals("LMF")||pos.equals("AMF")||pos.equals("DMF"))
        {
            holder.posi.setTextColor(Color.parseColor("#689F38"));
        }
        else if(pos.equals("CB")||pos.equals("RB")||pos.equals("LB"))
        {
            holder.posi.setTextColor(Color.parseColor("#512DA8"));
        }
        else if(pos.equals("GK"))
        {
            holder.posi.setTextColor(Color.parseColor("#FBC02D"));
        }

        final String PushId = playersList.get(position).getPushid();

        final String pageno = playersList.get(position).getPageno();

        holder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(mCtx, PlayerDetailActivity.class);
                mIntent.putExtra("Key", PushId);
                mIntent.putExtra("pageID",String.valueOf(pageno));
                mCtx.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView title,desc,posi;
        CardView card_layout;
        public TestViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.player_name);
            desc = (TextView) itemView.findViewById(R.id.overall_rating);
            card_layout = (CardView) itemView.findViewById(R.id.card_layout);
            posi = (TextView) itemView.findViewById(R.id.player_position);
        }
    }
}
