package com.devashishthakur.pesapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PlayerDetailActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView playerName,squadNo,teamName,league,nationality,region,playerHeight,playerWeight,playerAge,foot,position;
    TextView attackingProwress,ballControl,dribbling,lowPass,loftedPass,finishing,placeKicking,swerve,header,defensiveProwress,interception,kickingPower,speed,explosivePower,balance,physicalContact,jump,stamina,goalkeeping,catching,clearing,reflexes,coverage,weakFootUsage,weakFootAccuracy,injuryResistance;
    Toolbar toolbar;
    ProgressBar progressBar;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.detailProgress);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        scrollView = (ScrollView) findViewById(R.id.scrollViewMain);
        scrollView.setVisibility(View.GONE);
        playerName = (TextView) findViewById(R.id.com_devashishthakur_pesapp_MyTextView);
        squadNo = (TextView) findViewById(R.id.player_squad_no);
        teamName = (TextView) findViewById(R.id.player_team);
        league = (TextView) findViewById(R.id.player_league);
        nationality = (TextView) findViewById(R.id.player_nationality);
        region = (TextView) findViewById(R.id.player_region);
        playerHeight = (TextView) findViewById(R.id.player_height);
        playerWeight = (TextView) findViewById(R.id.player_weight);
        playerAge = (TextView) findViewById(R.id.player_age);
        foot = (TextView) findViewById(R.id.player_foot);
        position = (TextView) findViewById(R.id.player_position);
        attackingProwress = (TextView) findViewById(R.id.player_attacking_prowress);
        ballControl = (TextView) findViewById(R.id.player_ball_control);
        dribbling = (TextView) findViewById(R.id.player_dribbling);
        lowPass = (TextView) findViewById(R.id.player_low_pass);
        loftedPass = (TextView) findViewById(R.id.player_lofted_pass);
        finishing = (TextView) findViewById(R.id.player_finishing);
        placeKicking = (TextView) findViewById(R.id.player_place_kicking);
        swerve = (TextView) findViewById(R.id.player_swerve);
        header = (TextView) findViewById(R.id.player_header);
        defensiveProwress = (TextView) findViewById(R.id.player_defensive_prowress);
        interception = (TextView) findViewById(R.id.player_interceptions);
        kickingPower = (TextView) findViewById(R.id.player_kicking_power);
        speed = (TextView) findViewById(R.id.player_speed);
        explosivePower = (TextView) findViewById(R.id.player_explosive_power);
        balance = (TextView) findViewById(R.id.player_balance);
        physicalContact = (TextView) findViewById(R.id.player_physical_contact);
        jump = (TextView) findViewById(R.id.player_jump);
        stamina = (TextView) findViewById(R.id.player_stamina);
        goalkeeping = (TextView) findViewById(R.id.player_goalkeeping);
        catching = (TextView) findViewById(R.id.player_catching);
        clearing = (TextView) findViewById(R.id.player_clearing);
        reflexes = (TextView) findViewById(R.id.player_reflex);
        coverage = (TextView) findViewById(R.id.player_coverage);
        weakFootAccuracy = (TextView) findViewById(R.id.player_weak_foot_accuracy);
        weakFootUsage = (TextView) findViewById(R.id.player_weak_foot_usage);
        injuryResistance = (TextView) findViewById(R.id.player_injury_resistance);




        final String push_id = getIntent().getStringExtra("Key");
        final String page_id = getIntent().getStringExtra("pageID");

        databaseReference = FirebaseDatabase.getInstance().getReference("player").child(page_id).child(push_id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String player_name = dataSnapshot.child("playerpesname").getValue().toString();
                playerName.setText(player_name);
                try {
                    squadNo.setText(dataSnapshot.child("squadpesnumber").getValue().toString());
                }
                catch (Exception e)
                {
                    squadNo.setText("-");
                }
                teamName.setText(dataSnapshot.child("teampesname").getValue().toString());
                league.setText(dataSnapshot.child("league").getValue().toString());
                nationality.setText(dataSnapshot.child("nationality").getValue().toString());
                region.setText(dataSnapshot.child("region").getValue().toString());
                playerHeight.setText(dataSnapshot.child("height").getValue().toString());
                playerWeight.setText(dataSnapshot.child("weight").getValue().toString());
                playerAge.setText(dataSnapshot.child("age").getValue().toString());
                foot.setText(dataSnapshot.child("foot").getValue().toString());
                String pos = dataSnapshot.child("position").getValue().toString();
                position.setText(pos);

                if(pos.toString().equals("CF")||pos.toString().equals("RWF")||pos.toString().equals("LWF")||pos.toString().equals("SS"))
                {
                    position.setTextColor(Color.parseColor("#d32f2f"));
                }
                else if(pos.toString().equals("CMF")||pos.toString().equals("RMF")||pos.toString().equals("LMF")||pos.toString().equals("AMF")||pos.toString().equals("DMF"))
                {
                    position.setTextColor(Color.parseColor("#689F38"));
                }
                else if(pos.toString().equals("CB")||pos.toString().equals("RB")||pos.toString().equals("LB"))
                {
                    position.setTextColor(Color.parseColor("#512DA8"));
                }
                else if(pos.toString().equals("GK"))
                {
                    position.setTextColor(Color.parseColor("#FBC02D"));
                }


                //Abilities

                attackingProwress.setText(dataSnapshot.child("attackingpesprowess").getValue().toString());
                int rating_p = Integer.valueOf(attackingProwress.getText().toString());
                /*
                if(rating_p >= 95)
                {
                    attackingProwress.setTextColor(Color.parseColor("#0097A7"));
                }
                else if(rating_p >= 85 && rating_p < 95)
                {
                    attackingProwress.setTextColor(Color.parseColor("#4CAF50"));
                }
                else if(rating_p >= 75 && rating_p < 85)
                {
                    attackingProwress.setTextColor(Color.parseColor("#FBC02D"));
                }
                */
                setTextViewColor(rating_p,attackingProwress);

                ballControl.setText(dataSnapshot.child("ballpescontrol").getValue().toString());

                int rating_ball_control = Integer.valueOf(ballControl.getText().toString());
                setTextViewColor(rating_ball_control,ballControl);
                dribbling.setText(dataSnapshot.child("dribbling").getValue().toString());

                int rating_dribbling = Integer.valueOf(dribbling.getText().toString());
                setTextViewColor(rating_dribbling,dribbling);

                lowPass.setText(dataSnapshot.child("lowpespass").getValue().toString());

                int rating_lowpass = Integer.valueOf(lowPass.getText().toString());
                setTextViewColor(rating_lowpass,lowPass);

                loftedPass.setText(dataSnapshot.child("loftedpespass").getValue().toString());

                int rating_loftedPass = Integer.valueOf(loftedPass.getText().toString());
                setTextViewColor(rating_loftedPass,loftedPass);

                finishing.setText(dataSnapshot.child("finishing").getValue().toString());

                int rating_finishing = Integer.valueOf(finishing.getText().toString());
                setTextViewColor(rating_finishing,finishing);

                placeKicking.setText(dataSnapshot.child("placepeskicking").getValue().toString());

                int rating_placeKicking = Integer.valueOf(placeKicking.getText().toString());
                setTextViewColor(rating_placeKicking,placeKicking);

                swerve.setText(dataSnapshot.child("swerve").getValue().toString());

                int rating_swerve = Integer.valueOf(swerve.getText().toString());
                setTextViewColor(rating_swerve,swerve);

                header.setText(dataSnapshot.child("header").getValue().toString());

                int rating_header = Integer.valueOf(header.getText().toString());
                setTextViewColor(rating_header,header);

                defensiveProwress.setText(dataSnapshot.child("defensivepesprowess").getValue().toString());

                int rating_defensive = Integer.valueOf(defensiveProwress.getText().toString());
                setTextViewColor(rating_defensive,defensiveProwress);

                interception.setText(dataSnapshot.child("ballpeswinning").getValue().toString());

                int rating_interception = Integer.valueOf(interception.getText().toString());
                setTextViewColor(rating_interception,interception);

                kickingPower.setText(dataSnapshot.child("kickingpespower").getValue().toString());

                int rating_kicking_power = Integer.valueOf(kickingPower.getText().toString());
                setTextViewColor(rating_kicking_power,kickingPower);

                speed.setText(dataSnapshot.child("speed").getValue().toString());

                int rating_speed = Integer.valueOf(speed.getText().toString());
                setTextViewColor(rating_speed,speed);

                explosivePower.setText(dataSnapshot.child("explosivepespower").getValue().toString());

                int rating_explosive_power = Integer.valueOf(explosivePower.getText().toString());
                setTextViewColor(rating_explosive_power,explosivePower);

                balance.setText(dataSnapshot.child("unwaveringpesbalance").getValue().toString());

                int rating_balance = Integer.valueOf(balance.getText().toString());
                setTextViewColor(rating_balance,balance);

                physicalContact.setText(dataSnapshot.child("physicalpescontact").getValue().toString());

                int rating_physical = Integer.valueOf(physicalContact.getText().toString());
                setTextViewColor(rating_physical,physicalContact);

                jump.setText(dataSnapshot.child("jump").getValue().toString());

                int rating_jump = Integer.valueOf(jump.getText().toString());
                setTextViewColor(rating_jump,jump);

                stamina.setText(dataSnapshot.child("stamina").getValue().toString());

                int rating_stamina = Integer.valueOf(stamina.getText().toString());
                setTextViewColor(rating_stamina,stamina);

                goalkeeping.setText(dataSnapshot.child("goalkeeping").getValue().toString());
                int rating_gk = Integer.valueOf(goalkeeping.getText().toString());
                setTextViewColor(rating_gk,goalkeeping);

                catching.setText(dataSnapshot.child("gkpescatch").getValue().toString());

                int rating_catching = Integer.valueOf(catching.getText().toString());
                setTextViewColor(rating_catching,catching);

                clearing.setText(dataSnapshot.child("clearing").getValue().toString());

                int rating_clearing = Integer.valueOf(clearing.getText().toString());
                setTextViewColor(rating_clearing,clearing);

                reflexes.setText(dataSnapshot.child("reflexes").getValue().toString());

                int rating_reflexes = Integer.valueOf(reflexes.getText().toString());
                setTextViewColor(rating_reflexes,reflexes);

                coverage.setText(dataSnapshot.child("coverage").getValue().toString());

                int rating_coverage = Integer.valueOf(coverage.getText().toString());
                setTextViewColor(rating_coverage,coverage);

                weakFootAccuracy.setText(dataSnapshot.child("weakpesfootpesaccuracy").getValue().toString());
                weakFootUsage.setText(dataSnapshot.child("weakpesfootpesusage").getValue().toString());
                injuryResistance.setText(dataSnapshot.child("injurypesresistance").getValue().toString());

                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });
    }

    void setTextViewColor(int rating,TextView textView)
    {
        if(rating >= 95)
        {
            //textView.setTextColor(Color.parseColor("#424242"));
            //textView.setBackgroundColor(Color.parseColor("#d50000"));
            textView.setBackground(getResources().getDrawable(R.drawable.bgtextred));
        }
        else if(rating >= 85 && rating < 95)
        {
            //textView.setTextColor(Color.parseColor("#424242"));
            //textView.setBackgroundColor(Color.parseColor("#d50000"));
            textView.setBackground(getResources().getDrawable(R.drawable.bgtextgreen));
        }
        else if(rating >= 75 && rating < 85)
        {
            //textView.setTextColor(Color.parseColor("#424242"));
            //textView.setBackgroundColor(Color.parseColor("#d50000"));
            textView.setBackground(getResources().getDrawable(R.drawable.bgtextyellow));
        }
        else
        {
            //textView.setTextColor(Color.parseColor("#424242"));
            //textView.setBackgroundColor(Color.parseColor("#d50000"));
            textView.setBackground(getResources().getDrawable(R.drawable.bgtext));
        }
    }
    public void onClickBack(View view)
    {
        finish();
    }

}
