package com.smartkids.akillicocuklar2;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;



public class MainmenuActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private AdView mAdView;

    Integer toplamscore,toplamascore,cikarmascore,carpmascore,bolmescore,ritmikscore,buyukkucukscore,simetriscore,tekciftscore,totalscore,avatar,
            toplamaskorkumulatif,cikarmaskorkumulatif,carpmaskorkumulatif,bolmeskorkumulatif,ritmikskorkumulatif,buyukkucukskorkumulatif,simetriskorkumulatif,
            tekciftskorkumulatif,level,timescorekumulatif,inttimescore;

    GoogleApiClient mGoogleApiClient;

    private boolean soundOff = false;
    int sound = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenulayout);

        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ////////////////////info button////////////////////////////////
        final RelativeLayout welcomelayout = (RelativeLayout)findViewById(R.id.welcomelayout);
        final LinearLayout sonuclarlayout = findViewById(R.id.sonuclarlayout);
        final Button infoBtn = findViewById(R.id.infoBtn);Button disclaimundstand = findViewById(R.id.understanddisclaim);
        final RelativeLayout infolayout = (RelativeLayout)findViewById(R.id.infolayout);
        final ScrollView disclaimTxt = findViewById(R.id.disclaimView);
        final ScrollView infoTxt = findViewById(R.id.infoView);
        Button disclaimreadBtn = findViewById(R.id.readdisclaimBtn);
        Button geriBtn = findViewById(R.id.geriBtn);

        infolayout.setVisibility(View.GONE);
        sonuclarlayout.setVisibility(View.GONE);

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infolayout.setVisibility(View.VISIBLE);
                infoTxt.setVisibility(View.VISIBLE);
                welcomelayout.setVisibility(View.GONE);
                disclaimTxt.setVisibility(View.GONE);


            }
        });

        geriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomelayout.setVisibility(View.VISIBLE);
                infolayout.setVisibility(View.GONE);

            }
        });

        disclaimreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disclaimTxt.setVisibility(View.VISIBLE);
                infoTxt.setVisibility(View.GONE);

            }
        });

        disclaimundstand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disclaimTxt.setVisibility(View.GONE);
                infoTxt.setVisibility(View.VISIBLE);


            }
        });



        ///////////////////////////////////////////////////////





        ////////////avatar choose once////////////////////


        final RelativeLayout charchooselayout = (RelativeLayout)findViewById(R.id.charchooselayout);
        SharedPreferences sp = getSharedPreferences("avatarchoose", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        Integer i = sp.getInt("avatarchoose",0);


        if (i==0) {
            welcomelayout.setVisibility(View.GONE);
            charchooselayout.setVisibility(View.VISIBLE);
        }else {
            welcomelayout.setVisibility(View.VISIBLE);
            charchooselayout.setVisibility(View.GONE);

        }
        spEditor.putInt("avatarchoose",1);
        spEditor.commit();

        level = sp.getInt("level",0);




        ///////////////////////////////////////



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                       // Toast.makeText(getApplicationContext(),"sorunvar",Toast.LENGTH_SHORT).show();
                    }
                }).build();

        Button topleaderbtn = (Button)findViewById(R.id.leader);
        topleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                   // Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
                    // Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_score_table),50);
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,getString(R.string.leaderboard_smart_kids_kings)),0);


            }
        });

        Button achievementbtn = (Button)findViewById(R.id.achievement);
        achievementbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mGoogleApiClient.isConnected()){
                    //Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
                    startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),0);

                }else {
                    mGoogleApiClient.connect();
                    // Toast.makeText(getApplicationContext(),"baglı degil",Toast.LENGTH_SHORT).show();

                }
            }
        });

        Button profilBtn = findViewById(R.id.profileBtn);
        Button backtomainBtn = findViewById(R.id.backtomainBtn);
        profilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sonuclarlayout.setVisibility(View.VISIBLE);
                welcomelayout.setVisibility(View.GONE);
                skoraktar();

            }
        });

        backtomainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sonuclarlayout.setVisibility(View.GONE);
                welcomelayout.setVisibility(View.VISIBLE);

            }
        });





    }


    public void skoraktar(){
        LinearLayout sonuclarlayout = findViewById(R.id.sonuclarlayout);
        Button toplamapuanTxtv = findViewById(R.id.toplamapuanTxtv); Button cikarmapuanTxtv = findViewById(R.id.cikarmapuanTxtv); Button carpmapuanTxtv = findViewById(R.id.carpmapuanTxtv);
        Button bolmepuanTxtv = findViewById(R.id.bolmepuanTxtv); Button simetripuanTxtv = findViewById(R.id.simetripuanTxtv); Button serialspuanTxtv = findViewById(R.id.serialspuanTxtv);
        Button oddevenpuanTxtv = findViewById(R.id.oddevenpuanTxtv); Button buyukkucukpuanTxtv = findViewById(R.id.buyukkucukpuanTxtv); Button timechallengepuanTxtv = findViewById(R.id.timechallengepuanTxtv);
        Button smarttotalpuanTxtv = findViewById(R.id.smarttotalpuanTxtv); Button levelTxtv = findViewById(R.id.levelTxtv); Button nextlevelptsTxtv = findViewById(R.id.nextlevelptsTxtv);
        Button backtomainBtn = findViewById(R.id.backtomainBtn);

        ProgressBar levelbar = findViewById(R.id.pointsbar);

        int seviye = levelbar.getProgress();
        int max = levelbar.getMax();
        int fark = max-seviye;


        toplamapuanTxtv.setText(getText(R.string.toplamapuan)+" "+String.valueOf(toplamaskorkumulatif));
        cikarmapuanTxtv.setText(getText(R.string.cikarmapuan)+" "+String.valueOf(cikarmaskorkumulatif));
        carpmapuanTxtv.setText(getText(R.string.carpmapuan)+" "+String.valueOf(carpmaskorkumulatif));
        bolmepuanTxtv.setText(getText(R.string.bolmepuan)+" "+String.valueOf(bolmeskorkumulatif));
        simetripuanTxtv.setText(getText(R.string.simetripuan)+" "+String.valueOf(simetriskorkumulatif));
        serialspuanTxtv.setText(getText(R.string.ritmikpuan)+" "+String.valueOf(ritmikskorkumulatif));
        oddevenpuanTxtv.setText(getText(R.string.tekciftpuan)+" "+String.valueOf(tekciftskorkumulatif));
        buyukkucukpuanTxtv.setText(getText(R.string.buyukkucukpuan)+" "+String.valueOf(buyukkucukskorkumulatif));
        timechallengepuanTxtv.setText(getText(R.string.timechallengepuan)+" "+String.valueOf(timescorekumulatif));
        smarttotalpuanTxtv.setText(getText(R.string.totalsmartpuan)+" "+String.valueOf(toplamscore));
        levelTxtv.setText(getText(R.string.levelsayisi)+" "+String.valueOf(level));
        nextlevelptsTxtv.setText(getText(R.string.nextlevelpts)+" "+String.valueOf(fark));




    }

    public void onResume() {
        super.onResume();

        mGoogleApiClient.connect();

        final ImageView icon1n1,icon1n2,icon1n3,icon1n4,icon2n1,icon2n2,icon2n3,icon2n4,icon3n1,icon3n2,icon3n3,icon3n4,icon4n1,icon4n2,icon4n3,icon4n4,icon5n1,icon5n2,icon5n3,icon5n4,
                icon6n1,icon6n2,icon6n3,icon6n4,icon7n1,icon7n2,icon7n3,icon7n4,icon8n1,icon8n2,icon8n3,icon8n4,icon9n1,icon9n2,icon9n3,icon9n4,icon10n1,icon10n2,icon11n1,
                characterview;

        final RelativeLayout welcomelayout = (RelativeLayout)findViewById(R.id.welcomelayout);
        final RelativeLayout charchooselayout = (RelativeLayout)findViewById(R.id.charchooselayout);
        characterview=findViewById(R.id.characterview);








        final SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor spEditor = sp.edit();

        level = sp.getInt("level",0);
        toplamascore = sp.getInt("toplamaleader",0);
        cikarmascore = sp.getInt("cikarmaleader",0);
        bolmescore = sp.getInt("bolmeleader",0);
        carpmascore = sp.getInt("carpmaleader",0);
        buyukkucukscore = sp.getInt("buyukkucukleader",0);
        simetriscore = sp.getInt("simetrileader",0);
        tekciftscore = sp.getInt("balonleader",0);
        ritmikscore = sp.getInt("ritmikleader",0);
        inttimescore = sp.getInt("timescore",0);


        toplamaskorkumulatif = sp.getInt("toplamakumulatif",0);
        cikarmaskorkumulatif = sp.getInt("cikarmakumulatif",0);
        bolmeskorkumulatif = sp.getInt("bolmekumulatif",0);
        carpmaskorkumulatif = sp.getInt("carpmakumulatif",0);
        buyukkucukskorkumulatif = sp.getInt("buyukkucukkumulatif",0);
        ritmikskorkumulatif = sp.getInt("ritmikkumulatif",0);
        tekciftskorkumulatif = sp.getInt("balonkumulatif",0);
        simetriskorkumulatif = sp.getInt("simetrikumulatif",0);
        timescorekumulatif = sp.getInt("timescorekumulatif",0);

        Log.i("toplamakumulatif",String.valueOf(toplamaskorkumulatif));
        Log.i("cikarmakumulatif",String.valueOf(cikarmaskorkumulatif));
        Log.i("carpmakumulatif",String.valueOf(carpmaskorkumulatif));
        Log.i("bolmekumulatif",String.valueOf(bolmeskorkumulatif));
        Log.i("ritmikkumulatif",String.valueOf(ritmikskorkumulatif));
        Log.i("buyukkucukkumulatif",String.valueOf(buyukkucukskorkumulatif));
        Log.i("simetrikumulatif",String.valueOf(simetriskorkumulatif));
        Log.i("tekciftkumulatif",String.valueOf(tekciftskorkumulatif));
        Log.i("timescorekumulatif",String.valueOf(timescorekumulatif));


        Log.i("skortoplama",String.valueOf(toplamascore));
        Log.i("skorcikarma",String.valueOf(cikarmascore));
        Log.i("skorcarpma",String.valueOf(carpmascore));
        Log.i("skorbolme",String.valueOf(bolmescore));
        Log.i("skorritmik",String.valueOf(ritmikscore));
        Log.i("skorbuyukkucuk",String.valueOf(buyukkucukscore));
        Log.i("skorsimetri",String.valueOf(simetriscore));
        Log.i("skortekcift",String.valueOf(tekciftscore));
        Log.i("skortime",String.valueOf(inttimescore));


        toplamscore = toplamaskorkumulatif+cikarmaskorkumulatif+carpmaskorkumulatif+bolmeskorkumulatif+ritmikskorkumulatif+
                buyukkucukskorkumulatif+tekciftskorkumulatif+simetriskorkumulatif+timescorekumulatif;


        Log.i("kumulatif",String.valueOf(toplamscore));



        totalscore = sp.getInt("toplamskor",0);
        int tag = sp.getInt("avatar",0);
        ImageView v = (ImageView)findViewById(tag);

        if (tag==0) {
            characterview.setImageDrawable(null);
        }else {

            characterview.setImageDrawable(v.getDrawable());

        }








        ProgressBar toplamscorebar = (ProgressBar)findViewById(R.id.pointsbar);
        Button progrestxt = (Button) findViewById(R.id.progressTxt);
        toplamscorebar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#43ff50"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        icon1n1 = findViewById(R.id.icon1n1);icon1n2 = findViewById(R.id.icon1n2);icon1n3 = findViewById(R.id.icon1n3);icon1n4 = findViewById(R.id.icon1n4);
        icon2n1 = findViewById(R.id.icon2n1);icon2n2 = findViewById(R.id.icon2n2);icon2n3 = findViewById(R.id.icon2n3);icon2n4 = findViewById(R.id.icon2n4);
        icon3n1 = findViewById(R.id.icon3n1);icon3n2 = findViewById(R.id.icon3n2);icon3n3 = findViewById(R.id.icon3n3);icon3n4 = findViewById(R.id.icon3n4);
        icon4n1 = findViewById(R.id.icon4n1);icon4n2 = findViewById(R.id.icon4n2);icon4n3 = findViewById(R.id.icon4n3);icon4n4 = findViewById(R.id.icon4n4);
        icon5n1 = findViewById(R.id.icon5n1);icon5n2 = findViewById(R.id.icon5n2);icon5n3 = findViewById(R.id.icon5n3);icon5n4 = findViewById(R.id.icon5n4);
        icon6n1 = findViewById(R.id.icon6n1);icon6n2 = findViewById(R.id.icon6n2);icon6n3 = findViewById(R.id.icon6n3);icon6n4 = findViewById(R.id.icon6n4);
        icon7n1 = findViewById(R.id.icon7n1);icon7n2 = findViewById(R.id.icon7n2);icon7n3 = findViewById(R.id.icon7n3);icon7n4 = findViewById(R.id.icon7n4);
        icon8n1 = findViewById(R.id.icon8n1);icon8n2 = findViewById(R.id.icon8n2);icon8n3 = findViewById(R.id.icon8n3);icon8n4 = findViewById(R.id.icon8n4);
        icon9n1 = findViewById(R.id.icon9n1);icon9n2 = findViewById(R.id.icon9n2);icon9n3 = findViewById(R.id.icon9n3);icon9n4 = findViewById(R.id.icon9n4);
        icon10n1 = findViewById(R.id.icon10n1);icon10n2 = findViewById(R.id.icon10n2);
        icon11n1 = findViewById(R.id.icon11n1);

        LinearLayout lvl1grp =findViewById(R.id.level1grp); LinearLayout lvl2grp =findViewById(R.id.level2grp); LinearLayout lvl3grp =findViewById(R.id.level3grp);
        LinearLayout lvl4grp =findViewById(R.id.level4grp); LinearLayout lvl5grp =findViewById(R.id.level5grp); LinearLayout lvl6grp =findViewById(R.id.level6grp);
        LinearLayout lvl7grp =findViewById(R.id.level7grp); LinearLayout lvl8grp =findViewById(R.id.level8grp); LinearLayout lvl9grp =findViewById(R.id.level9grp);
        LinearLayout lvl10grp =findViewById(R.id.level10grp); LinearLayout lvl11grp =findViewById(R.id.level11grp);

        characterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomelayout.setVisibility(View.GONE);
                charchooselayout.setVisibility(View.VISIBLE);

            }
        });



        if (toplamscore<1000) {
            toplamscorebar.setMax(1000);
            toplamscorebar.setProgress((int) toplamscore);
            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 0.6);icon2n2.setAlpha((float) 0.6);icon2n3.setAlpha((float) 0.6);icon2n4.setAlpha((float) 0.6);
            icon3n1.setAlpha((float) 0.6);icon3n2.setAlpha((float) 0.6);icon3n3.setAlpha((float) 0.6);icon3n4.setAlpha((float) 0.6);
            icon4n1.setAlpha((float) 0.6);icon4n2.setAlpha((float) 0.6);icon4n3.setAlpha((float) 0.6);icon4n4.setAlpha((float) 0.6);
            icon5n1.setAlpha((float) 0.6);icon5n2.setAlpha((float) 0.6);icon5n3.setAlpha((float) 0.6);icon5n4.setAlpha((float) 0.6);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);



            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);
                    spEditor.putInt("avatarchoose",1);
                    spEditor.commit();

                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                   // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);
                    spEditor.putInt("avatarchoose",1);
                    spEditor.commit();

                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);
                    spEditor.putInt("avatarchoose",1);
                    spEditor.commit();

                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);
                    spEditor.putInt("avatarchoose",1);
                    spEditor.commit();

                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////
            lvl2grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl3grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl4grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl5grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl6grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl7grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl8grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl9grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////


        }

        if (toplamscore>999 && toplamscore<5000) {

            if (level==0) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                spEditor.putInt("level", 1);

                final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }


            toplamscorebar.setMax(5000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 2 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 0.6);icon3n2.setAlpha((float) 0.6);icon3n3.setAlpha((float) 0.6);icon3n4.setAlpha((float) 0.6);
            icon4n1.setAlpha((float) 0.6);icon4n2.setAlpha((float) 0.6);icon4n3.setAlpha((float) 0.6);icon4n4.setAlpha((float) 0.6);
            icon5n1.setAlpha((float) 0.6);icon5n2.setAlpha((float) 0.6);icon5n3.setAlpha((float) 0.6);icon5n4.setAlpha((float) 0.6);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                   // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////

            lvl3grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl4grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl5grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl6grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl7grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl8grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl9grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////

        }

        if (toplamscore>4999 && toplamscore<10000) {

            if (level==1) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                spEditor.putInt("level", 2);


                final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }
            toplamscorebar.setMax(10000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 3 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 0.6);icon4n2.setAlpha((float) 0.6);icon4n3.setAlpha((float) 0.6);icon4n4.setAlpha((float) 0.6);
            icon5n1.setAlpha((float) 0.6);icon5n2.setAlpha((float) 0.6);icon5n3.setAlpha((float) 0.6);icon5n4.setAlpha((float) 0.6);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                   // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////


            lvl4grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl5grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl6grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl7grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl8grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl9grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////
        }
        if (toplamscore>9999 && toplamscore<15000) {
            if (level==2) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                spEditor.putInt("level", 3);

                final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }
            toplamscorebar.setMax(15000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 4 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 0.6);icon5n2.setAlpha((float) 0.6);icon5n3.setAlpha((float) 0.6);icon5n4.setAlpha((float) 0.6);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon4n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////

            lvl5grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl6grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl7grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl8grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl9grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////
        }
        if (toplamscore>14999 && toplamscore<25000) {

                if (level==3) {
                    Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                    charchooselayout.setVisibility(View.VISIBLE);
                    spEditor.putInt("level", 4);

                    final  MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                    victory.start();
                    victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            victory.release();
                        }
                    });
                }
            toplamscorebar.setMax(25000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 5 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon4n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            icon5n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();

                }
            });
            icon5n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            //////////////////////////LEVEL CHECK TOAST////////////////////

            lvl6grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl7grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl8grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl9grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////
        }
        if (toplamscore>24999 && toplamscore<35000) {


                if (level==4) {
                    Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                    charchooselayout.setVisibility(View.VISIBLE);
                    spEditor.putInt("level", 5);

                    final  MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                    victory.start();
                    victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            victory.release();
                        }
                    });
                }

            toplamscorebar.setMax(35000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 6 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon4n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            icon5n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();

                }
            });
            icon5n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon6n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////


            lvl7grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl8grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl9grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////

        }
        if (toplamscore>34999 && toplamscore<50000) {


                if (level==5) {
                    Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                    charchooselayout.setVisibility(View.VISIBLE);
                    spEditor.putInt("level", 6);

                    final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                    victory.start();
                    victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            victory.release();
                        }
                    });
                }
            toplamscorebar.setMax(50000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 7 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon4n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            icon5n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();

                }
            });
            icon5n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon6n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon7n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            //////////////////////////LEVEL CHECK TOAST////////////////////



            lvl8grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl9grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////
        }
        if (toplamscore>49999 && toplamscore<75000) {


                if (level==6) {
                    Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                    charchooselayout.setVisibility(View.VISIBLE);
                    spEditor.putInt("level", 7);

                    final  MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                    victory.start();
                    victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            victory.release();
                        }
                    });
                }
            toplamscorebar.setMax(75000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 8 ");
            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 1);icon8n2.setAlpha((float) 1);icon8n3.setAlpha((float) 1);icon8n4.setAlpha((float) 1);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon4n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            icon5n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();

                }
            });
            icon5n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon6n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon7n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon8n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            //////////////////////////LEVEL CHECK TOAST////////////////////

            lvl9grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });

            //////////////////////////LEVEL CHECK TOAST////////////////////
        }
        if (toplamscore>74999 && toplamscore<100000) {


                if (level==7) {
                    Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                    charchooselayout.setVisibility(View.VISIBLE);
                    spEditor.putInt("level", 8);

                    final  MediaPlayer   victory = MediaPlayer.create(this,R.raw.trumpet);
                    victory.start();
                    victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            victory.release();
                        }
                    });
                }

            toplamscorebar.setMax(100000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 9 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 1);icon8n2.setAlpha((float) 1);icon8n3.setAlpha((float) 1);icon8n4.setAlpha((float) 1);
            icon9n1.setAlpha((float) 1);icon9n2.setAlpha((float) 1);icon9n3.setAlpha((float) 1);icon9n4.setAlpha((float) 1);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon4n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            icon5n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();

                }
            });
            icon5n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon6n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon7n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon8n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon9n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            //////////////////////////LEVEL CHECK TOAST////////////////////
            lvl10grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            //////////////////////////LEVEL CHECK TOAST////////////////////


        }

        if (toplamscore>99999 && toplamscore<125000) {


                if (level==8) {
                    Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                    charchooselayout.setVisibility(View.VISIBLE);
                    spEditor.putInt("level", 9);

                    final  MediaPlayer    victory = MediaPlayer.create(this,R.raw.trumpet);
                    victory.start();
                    victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            victory.release();
                        }
                    });
                }

            toplamscorebar.setMax(125000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 10 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 1);icon8n2.setAlpha((float) 1);icon8n3.setAlpha((float) 1);icon8n4.setAlpha((float) 1);
            icon9n1.setAlpha((float) 1);icon9n2.setAlpha((float) 1);icon9n3.setAlpha((float) 1);icon9n4.setAlpha((float) 1);
            icon10n1.setAlpha((float) 1);icon10n2.setAlpha((float) 1);
            icon11n1.setAlpha((float) 0.6);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon4n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            icon5n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();

                }
            });
            icon5n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon6n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon7n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon8n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon9n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon10n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon10n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon10n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon10n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon10n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon10n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            //////////////////////////LEVEL CHECK TOAST////////////////////

            lvl11grp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz),Toast.LENGTH_SHORT).show();
                }
            });
            //////////////////////////LEVEL CHECK TOAST////////////////////

        }

        if (toplamscore>124999) {


                if (level==9) {
                    Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                    charchooselayout.setVisibility(View.VISIBLE);
                    spEditor.putInt("level", 10);

                    final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                    victory.start();
                    victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            victory.release();
                        }
                    });
                }

            toplamscorebar.setMax(1250000);
            toplamscorebar.setProgress((int) toplamscore);
            progrestxt.setText("Level 11 ");

            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 1);icon8n2.setAlpha((float) 1);icon8n3.setAlpha((float) 1);icon8n4.setAlpha((float) 1);
            icon9n1.setAlpha((float) 1);icon9n2.setAlpha((float) 1);icon9n3.setAlpha((float) 1);icon9n4.setAlpha((float) 1);
            icon10n1.setAlpha((float) 1);icon10n2.setAlpha((float) 1);
            icon11n1.setAlpha((float) 1);

            icon1n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));


                }
            });
            icon1n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon1n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterview.setImageResource(R.drawable.icon1n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);


                    int tag = icon1n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });



            icon2n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon2n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon2n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon2n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon3n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon3n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    // Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon3n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon3n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon3n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });


            icon4n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });
            icon4n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon4n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon4n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                    //Log.i("tag2",String.valueOf(tag));
                }
            });

            icon5n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();

                }
            });
            icon5n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon5n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon5n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon5n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon6n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon6n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon6n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon6n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon7n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon7n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon7n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon7n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon8n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon8n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon8n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon8n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon9n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n3icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n3.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon9n4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon9n4icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon9n4.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon10n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon10n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon10n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });
            icon10n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon10n2icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon10n2.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });

            icon11n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    characterview.setImageResource(R.drawable.icon11n1icon);
                    welcomelayout.setVisibility(View.VISIBLE);
                    charchooselayout.setVisibility(View.GONE);

                    int tag = icon11n1.getId();
                    spEditor.putInt("avatar",tag);
                    spEditor.commit();
                }
            });


        }

///////////////////////////////////////

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();


        /////////////////////////////LEADERBOARD///////////////////////////////////

        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_smart_kids_kings),toplamscore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_summation_stars),toplamascore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_subtraction_stars),cikarmascore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_division_stars),bolmescore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_multiplication_stars),carpmascore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_serials_stars),ritmikscore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_symmetry_stars),simetriscore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_odd__even_stars),tekciftscore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_greater__lesser_stars),buyukkucukscore);
        Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_time_challenge_stars),inttimescore);




        ///////////////////////////////ACHİEVEMENTS//////////////////////////////////
        if(toplamaskorkumulatif>1000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_good_starter_summation));}
        if(cikarmaskorkumulatif>1000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_good_starter_subtraction));}
        if(carpmaskorkumulatif>1000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_good_starter_multiplication));}
        if(bolmeskorkumulatif>1000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_good_starter_division));}
        if(buyukkucukskorkumulatif>1000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_good_starter_greaterlesser));}
        if(ritmikskorkumulatif>1000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_good_starter_serials));}
        if(simetriskorkumulatif>1000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_good_starter_symmetry));}
        if(tekciftskorkumulatif>1000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_good_starter_oddeven));}

        if(toplamaskorkumulatif>20000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_summation_expert));}
        if(cikarmaskorkumulatif>20000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_subtraction_expert));}
        if(carpmaskorkumulatif>20000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_multiplication_expert));}
        if(bolmeskorkumulatif>20000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_division_expert));}
        if(buyukkucukskorkumulatif>20000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_greater_or_lesser_expert));}
        if(ritmikskorkumulatif>20000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_serials_expert));}
        if(simetriskorkumulatif>20000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_symmetry_expert));}
        if(tekciftskorkumulatif>20000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_odd_or_even_expert));}

        if(toplamaskorkumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_summation_proffesor));}
        if(cikarmaskorkumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_subtraction_proffessor));}
        if(carpmaskorkumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_multiplication_proffessor));}
        if(bolmeskorkumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_division_proffessor));}
        if(buyukkucukskorkumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_greater_of_lesser_proffessor));}
        if(ritmikskorkumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_serials_proffessor));}
        if(simetriskorkumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_symmetry_proffessor));}
        if(tekciftskorkumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_odd_or_even_proffessor));}

        if(timescorekumulatif>5000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_real_time_shooter));}
        if(timescorekumulatif>10000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_real_time_crusher));}
        if(timescorekumulatif>50000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_real_time_expert));}
        if(timescorekumulatif>100000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_real_time_professor));}


        if(toplamaskorkumulatif>5000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_5000_smart_points));}
        if(toplamaskorkumulatif>10000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_10000_smart_points));}
        if(toplamaskorkumulatif>25000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_25000_smart_points));}
        if(toplamaskorkumulatif>40000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_40000_smart_points));}
        if(toplamaskorkumulatif>80000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_80000_smart_points));}
        if(toplamaskorkumulatif>100000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_100000_smart_points));}
        if(toplamaskorkumulatif>150000) {Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_150000_smart_points));}





    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void training (View view) {
        Intent i = new Intent(MainmenuActivity.this, TrainingActivity.class);startActivity(i);
    }

    public void mathgames (View view) {
        Intent i = new Intent(MainmenuActivity.this, MathgamesActivity.class);startActivity(i);
    }
    public void timechallenge (View view) {
        Intent i = new Intent(MainmenuActivity.this, TimechallengeActivity.class);startActivity(i);
    }

    public void twoplayers (View view) {Toast.makeText(MainmenuActivity.this,getString(R.string.comingsoon),Toast.LENGTH_LONG).show();}



    public void leveldurumu(View view) {
        ProgressBar levelbar = findViewById(R.id.pointsbar);

        int seviye = levelbar.getProgress();
        int max = levelbar.getMax();
        int fark = max-seviye;
         Toast.makeText(getApplicationContext(),fark +" "+ getString(R.string.leveldurumu),Toast.LENGTH_SHORT).show();




    }



    @Override
    public void onBackPressed() {



       RelativeLayout welcomelayout = (RelativeLayout)findViewById(R.id.welcomelayout);
        final RelativeLayout byebyelayout = (RelativeLayout)findViewById(R.id.byebyelayout);

        final RelativeLayout gulegulelayout = (RelativeLayout)findViewById(R.id.gulegulelayout);
        RelativeLayout charchooselayout = (RelativeLayout)findViewById(R.id.charchooselayout);
        final RelativeLayout infolayout = (RelativeLayout)findViewById(R.id.infolayout);
        infolayout.setVisibility(View.GONE);

        if (welcomelayout.getVisibility()==View.VISIBLE) {

            welcomelayout.setVisibility(View.GONE);
            byebyelayout.setVisibility(View.VISIBLE);

            Button apprate = (Button) findViewById(R.id.rateBtn);

            SharedPreferences sp = getSharedPreferences("rateclick", Activity.MODE_PRIVATE);
            Integer i = sp.getInt("rateclick",0);
            //Log.i("sayi",String.valueOf(i));
            if (i==0) {
                apprate.setVisibility(View.VISIBLE);
            }else {
                apprate.setVisibility(View.INVISIBLE);
                apprate.setEnabled(false);
            }



        }else {
            charchooselayout.setVisibility(View.GONE);
            welcomelayout.setVisibility(View.VISIBLE);
        }



    }

    public void rateapp (View view) {

        SharedPreferences sp = getSharedPreferences("rateclick", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor = sp.edit();

        spEditor.putInt("rateclick",1);
        spEditor.commit();
        Integer ii = sp.getInt("rateclick",0);
        Log.i("sayi2",String.valueOf(ii));

        Uri uri = Uri.parse("market://details?id="+ getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));

        }
    }

    public void appcikis (View view) {

        this.finishAffinity();
    }









}