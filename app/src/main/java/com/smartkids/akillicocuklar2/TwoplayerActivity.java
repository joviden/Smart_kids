package com.smartkids.akillicocuklar2;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by erdem.salgin on 19.2.2018.
 */

public class TwoplayerActivity extends AppCompatActivity {

    Integer sayi1,sayi2,cevap,kalansure;


    CountDownTimer sure,gerisayim;

    private boolean redclicked = false;  private boolean blueclicked = false;  public boolean playagainblue = false;  public boolean playagainred = false;
    private boolean timepaused = false;
    private boolean redcikis = false;  private boolean bluecikis = false;

    int sorucounter=0;
    int puanmavi=0;
    int puankirmizi=0;
    int scoremavi=0;
    int scorekirmizi=0;


    MediaPlayer gamemusic,finalmusic;
    private AdView mAdView;
    private AdView mAdViewust;
    private AdView mAdViewalt;
    private InterstitialAd mInterstitialAd;





    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.twoplayerlayout);

        ////////////////////////////////////////////



        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdViewust = findViewById(R.id.adViewust);
        AdRequest adRequestust = new AdRequest.Builder().build();
        mAdViewust.loadAd(adRequestust);

        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdViewalt = findViewById(R.id.adViewalt);
        AdRequest adRequestalt = new AdRequest.Builder().build();
        mAdViewalt.loadAd(adRequestalt);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4100535460120599/6760445164");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        ////////////////////



        LinearLayout maviresultlayout = findViewById(R.id.maviresultlayout);  LinearLayout kirmiziresultlayout = findViewById(R.id.kirmiziresultlayout);
        LinearLayout cikislayout = findViewById(R.id.cikislayout);
        final RelativeLayout twoplayergiris = findViewById(R.id.twoplayergirislayout);
        final RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        final LinearLayout gerisayimlayout = findViewById(R.id.countdownlayout);
        final LinearLayout seceneklerlayout = findViewById(R.id.seceneklerlayout);
        final Button gerisayimBtn =  findViewById(R.id.gerisayimbtn);
         final Button girisBtn =  findViewById(R.id.girisBtn);
        final Button infoBtn =  findViewById(R.id.twoplayersinfo);
         final Button cikisBtn =  findViewById(R.id.cikisBtn);
        Button exitapp1Btn = findViewById(R.id.exitmenu1Btn);  Button exitapp2Btn = findViewById(R.id.exitmenu2Btn);

        final androidx.appcompat.widget.SwitchCompat toplamaswitch = findViewById(R.id.toplamaswitch);
        final androidx.appcompat.widget.SwitchCompat cikarmaswitch = findViewById(R.id.cikarmaswitch);
        final androidx.appcompat.widget.SwitchCompat bolmeswitch = findViewById(R.id.bolmeswitch);
        final androidx.appcompat.widget.SwitchCompat carpmaswitch = findViewById(R.id.carpmaswitch);

        maviresultlayout.setVisibility(View.GONE); kirmiziresultlayout.setVisibility(View.GONE);
        sorugovde.setVisibility(View.GONE);
        twoplayergiris.setVisibility(View.VISIBLE);
        gerisayimlayout.setVisibility(View.GONE);
        cikislayout.setVisibility(View.GONE);

        try{

        final  MediaPlayer hakemdudugu = MediaPlayer.create(TwoplayerActivity.this,R.raw.hakemdudugu);
        hakemdudugu.start();
        hakemdudugu.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                hakemdudugu.release();
            }
        });
        }catch (Exception e) {e.printStackTrace();}

        girisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toplamaswitch.isChecked() || cikarmaswitch.isChecked() ||bolmeswitch.isChecked() || carpmaswitch.isChecked()) {

                    girisBtn.setEnabled(false);
                    cikisBtn.setEnabled(false);
                    seceneklerlayout.setVisibility(View.GONE);
                    infoBtn.setVisibility(View.GONE);

                    gerisayimlayout.setVisibility(View.VISIBLE);
                    gerisayim = new CountDownTimer(4000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            gerisayimBtn.setText("" + millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            girisBtn.setEnabled(true);
                            cikisBtn.setEnabled(true);
                            twoplayergiris.setVisibility(View.GONE);
                            sorugovde.setVisibility(View.VISIBLE);

                            soruhazirlama();

                            try{

                            gamemusic = MediaPlayer.create(TwoplayerActivity.this,R.raw.footballmusic);
                            gamemusic.start();
                            gamemusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    gamemusic.start();
                                }
                            });
                            }catch (Exception e) {e.printStackTrace();}

                        }
                    }.start();


                }else {
                    Toast.makeText(TwoplayerActivity.this,getString(R.string.switchcheck),Toast.LENGTH_SHORT).show();

                }


            }
        });

        cikisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(TwoplayerActivity.this, MainmenuActivity.class);
                startActivity(myIntent);
                TwoplayerActivity.this.finish();
            }
        });



    }





    public void soruhazirlama() {

        final ImageView goalView1 = findViewById(R.id.goalView1);   final ImageView goalView2 = findViewById(R.id.goalView2);
        Button sorusayisiView1 = findViewById(R.id.sorusayisiView1); Button sorusayisiView2 = findViewById(R.id.sorusayisiView2);

        goalView1.setBackgroundResource(R.drawable.goal); goalView2.setBackgroundResource(R.drawable.goal);
        sorusayisiView1.setBackgroundColor(Color.TRANSPARENT); sorusayisiView2.setBackgroundColor(Color.TRANSPARENT);





        sorucounter++;

        if (sorucounter==5) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

        if (sorucounter>10 && scorekirmizi!=scoremavi){macsonucu();}

        if (sorucounter>10 && scorekirmizi==scoremavi) {

            kalansure = 16000;

            sorusayisiView1.setBackgroundResource(R.drawable.goldengoal); sorusayisiView1.setText("");
            sorusayisiView2.setBackgroundResource(R.drawable.goldengoal); sorusayisiView2.setText("");
            goalView1.setBackgroundResource(R.drawable.goldengoal); goalView2.setBackgroundResource(R.drawable.goldengoal);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soruburada();
                }
            }, 2000);

            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(200); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(5);
            goalView1.startAnimation(anim); goalView2.startAnimation(anim);



        }

        if (sorucounter<11) {

        sorusayisiView1.setText(sorucounter+"/10");   sorusayisiView2.setText(sorucounter+"/10");
        kalansure = 16000;

        blueclicked = false;  redclicked = false;

            soruburada();
        }




    }








    public void sonucdegelendirMavi (View view) {


        final Button sika1Btn = (Button)findViewById(R.id.sika1Btn);
        final Button sikb1Btn = (Button)findViewById(R.id.sikb1Btn);
        final Button sikc1Btn = (Button)findViewById(R.id.sikc1Btn);
        final Button sikd1Btn = (Button)findViewById(R.id.sikd1Btn);
        final Button sika2Btn = (Button)findViewById(R.id.sika2Btn);
        final Button sikb2Btn = (Button)findViewById(R.id.sikb2Btn);
        final Button sikc2Btn = (Button)findViewById(R.id.sikc2Btn);
        final Button sikd2Btn = (Button)findViewById(R.id.sikd2Btn);
        final Button skormavi1Btn = findViewById(R.id.skormavi1Btn);
        final Button skormavi2Btn = findViewById(R.id.skormavi2Btn);
        Button exitapp1Btn = findViewById(R.id.exitmenu1Btn);  Button exitapp2Btn = findViewById(R.id.exitmenu2Btn);

        ImageView goalView1 = findViewById(R.id.goalView1);

        blueclicked = true;
        view.setBackgroundResource(R.drawable.siklar);

        sika1Btn.setEnabled(false); sikb1Btn.setEnabled(false); sikc1Btn.setEnabled(false); sikd1Btn.setEnabled(false);
        sika2Btn.setEnabled(false); sikb2Btn.setEnabled(false); sikc2Btn.setEnabled(false); sikd2Btn.setEnabled(false);
        exitapp1Btn.setEnabled(false); exitapp2Btn.setEnabled(false);

        if (view.getTag().toString().equals(Integer.toString(cevap))) {

            if (sure!=null) {sure.cancel();}

            view.setBackgroundResource(R.drawable.siklarclicked);

            scoremavi++;
            skormavi1Btn.setText(String.valueOf(scoremavi)); skormavi2Btn.setText(String.valueOf(scoremavi));

            goalView1.setVisibility(View.VISIBLE);



            final MediaPlayer goalmusic = MediaPlayer.create(TwoplayerActivity.this,R.raw.goal);
            goalmusic.start();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (goalmusic!=null ) {goalmusic.release(); }
                    soruhazirlama();
                }
            }, 2000);

            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(200); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(5);
            goalView1.startAnimation(anim);



        }else {

            view.setBackgroundResource(R.drawable.hatalisik);

            if (redclicked) {

                if (sure!=null) {sure.cancel();}
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        soruhazirlama();
                    }
                }, 2000);

                if (cevap.equals(Integer.parseInt(sika1Btn.getText().toString()))){sika1Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikb1Btn.getText().toString()))){sikb1Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikc1Btn.getText().toString()))){sikc1Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikd1Btn.getText().toString()))){sikd1Btn.setBackgroundResource(R.drawable.siklarclicked);}

                if (cevap.equals(Integer.parseInt(sika2Btn.getText().toString()))){sika2Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikb2Btn.getText().toString()))){sikb2Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikc2Btn.getText().toString()))){sikc2Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikd2Btn.getText().toString()))){sikd2Btn.setBackgroundResource(R.drawable.siklarclicked);}

            } else  {
                sika2Btn.setEnabled(true); sikb2Btn.setEnabled(true); sikc2Btn.setEnabled(true); sikd2Btn.setEnabled(true);
            }



        }



    }


    public void sonucdegelendirKirmizi (View view) {


        final Button sika1Btn = (Button)findViewById(R.id.sika1Btn);
        final Button sikb1Btn = (Button)findViewById(R.id.sikb1Btn);
        final Button sikc1Btn = (Button)findViewById(R.id.sikc1Btn);
        final Button sikd1Btn = (Button)findViewById(R.id.sikd1Btn);
        final Button sika2Btn = (Button)findViewById(R.id.sika2Btn);
        final Button sikb2Btn = (Button)findViewById(R.id.sikb2Btn);
        final Button sikc2Btn = (Button)findViewById(R.id.sikc2Btn);
        final Button sikd2Btn = (Button)findViewById(R.id.sikd2Btn);
        final Button skorkirmizi1Btn = findViewById(R.id.skorkirmizi1Btn);
        final Button skorkirmizi2Btn = findViewById(R.id.skorkirmizi2Btn);
        Button exitapp1Btn = findViewById(R.id.exitmenu1Btn);  Button exitapp2Btn = findViewById(R.id.exitmenu2Btn);
        ImageView goalView2 = findViewById(R.id.goalView2);

        redclicked = true;
        view.setBackgroundResource(R.drawable.siklar);

        sika1Btn.setEnabled(false); sikb1Btn.setEnabled(false); sikc1Btn.setEnabled(false); sikd1Btn.setEnabled(false);
        sika2Btn.setEnabled(false); sikb2Btn.setEnabled(false); sikc2Btn.setEnabled(false); sikd2Btn.setEnabled(false);
        exitapp1Btn.setEnabled(false); exitapp2Btn.setEnabled(false);

        if (view.getTag().toString().equals(Integer.toString(cevap))) {

            view.setBackgroundResource(R.drawable.siklarclicked);

            if (sure!=null) {sure.cancel();}

            scorekirmizi++;
            skorkirmizi1Btn.setText(String.valueOf(scorekirmizi)); skorkirmizi2Btn.setText(String.valueOf(scorekirmizi));

            goalView2.setVisibility(View.VISIBLE);



            final MediaPlayer goalmusic = MediaPlayer.create(TwoplayerActivity.this,R.raw.goal);
            goalmusic.start();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (goalmusic!=null ) {goalmusic.release(); }
                    soruhazirlama();
                }
            }, 2000);

            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(200); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(5);
            goalView2.startAnimation(anim);



        }else {

            view.setBackgroundResource(R.drawable.hatalisik);



            if (blueclicked) {
                if (sure!=null) {sure.cancel();}
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        soruhazirlama();
                    }
                }, 2000);

                if (cevap.equals(Integer.parseInt(sika2Btn.getText().toString()))){sika2Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikb2Btn.getText().toString()))){sikb2Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikc2Btn.getText().toString()))){sikc2Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikd2Btn.getText().toString()))){sikd2Btn.setBackgroundResource(R.drawable.siklarclicked);}

                if (cevap.equals(Integer.parseInt(sika1Btn.getText().toString()))){sika1Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikb1Btn.getText().toString()))){sikb1Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikc1Btn.getText().toString()))){sikc1Btn.setBackgroundResource(R.drawable.siklarclicked);}
                if (cevap.equals(Integer.parseInt(sikd1Btn.getText().toString()))){sikd1Btn.setBackgroundResource(R.drawable.siklarclicked);}

            } else {
                sika1Btn.setEnabled(true); sikb1Btn.setEnabled(true); sikc1Btn.setEnabled(true); sikd1Btn.setEnabled(true);
            }



        }



    }


    public void macsonucu() {

       // if (gamemusic!=null && gamemusic.isPlaying()) {gamemusic.stop();}


            if (gamemusic!=null ) { gamemusic.release(); gamemusic=null;}

            bluecikis=false; redcikis=false;


        finalmusic = MediaPlayer.create(TwoplayerActivity.this,R.raw.goal);
        finalmusic.start();

        final Button playagainblueBtn = findViewById(R.id.playagain1Btn);
        final Button playagainredBtn = findViewById(R.id.playagain2Btn);

        final Button exitapp1Btn = findViewById(R.id.exitmenu1Btn);  final Button exitapp2Btn = findViewById(R.id.exitmenu2Btn);
        final Button exitactivity1 = findViewById(R.id.cikis1Btn);  final Button exitactivity2 = findViewById(R.id.cikis2Btn);

        exitapp1Btn.setEnabled(false); exitapp2Btn.setEnabled(false);

        exitactivity1.setBackgroundResource(R.drawable.hatalisik);
        exitactivity2.setBackgroundResource(R.drawable.hatalisik);
        playagainblueBtn.setBackgroundResource(R.drawable.siklarclicked);
        playagainredBtn.setBackgroundResource(R.drawable.siklarclicked);


        LinearLayout maviresullayout = findViewById(R.id.maviresultlayout);  LinearLayout kirmiziresullayout = findViewById(R.id.kirmiziresultlayout);
        LinearLayout siklarmavilayout1 = findViewById(R.id.siklarmavilayout1); LinearLayout siklarmavilayout2 = findViewById(R.id.siklarmavilayout2);LinearLayout sorusayilarmavilayout = findViewById(R.id.sorusayilarmavilayout);
        LinearLayout siklarkirmizilayout1 = findViewById(R.id.siklarkirmizilayout1);  LinearLayout siklarkirmizilayout2 = findViewById(R.id.siklarkirmizilayout2); LinearLayout sorusayilarkırmızılayout = findViewById(R.id.sorusayilarkırmızılayout);
        ImageView maviresultgif = findViewById(R.id.maviresultgif);  ImageView kirmiziresultgif = findViewById(R.id.kirmiziresultgif);
        Button maviresultBtn = findViewById(R.id.maviresultBtn); Button kirmiziresultBtn = findViewById(R.id.kirmiziresultBtn);


        sorusayilarkırmızılayout.setVisibility(View.GONE); sorusayilarmavilayout.setVisibility(View.GONE);
        siklarkirmizilayout1.setVisibility(View.GONE); siklarkirmizilayout2.setVisibility(View.GONE); siklarmavilayout1.setVisibility(View.GONE); siklarmavilayout2.setVisibility(View.GONE);
        if (sure!=null) {sure.cancel();}

        if (scoremavi>scorekirmizi){maviresultgif.setBackgroundResource(R.drawable.win); kirmiziresultgif.setBackgroundResource(R.drawable.loose);
            maviresultBtn.setText(getString(R.string.tebrikler)); kirmiziresultBtn.setText(getString(R.string.teselli));}
        if (scorekirmizi>scoremavi){maviresultgif.setBackgroundResource(R.drawable.loose); kirmiziresultgif.setBackgroundResource(R.drawable.win);
            maviresultBtn.setText(getString(R.string.teselli)); kirmiziresultBtn.setText(getString(R.string.tebrikler));}

        maviresullayout.setVisibility(View.VISIBLE); kirmiziresullayout.setVisibility(View.VISIBLE);



        playagainblueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playagainblue=true;
                bluecikis=false;
                exitactivity1.setBackgroundResource(R.drawable.hatalisik);
                exitactivity2.setBackgroundResource(R.drawable.hatalisik);
                playagainblueBtn.setBackgroundResource(R.drawable.edittext);
              //  playagainblueBtn.setEnabled(false);
                if (playagainred){ playagainblueBtn.setEnabled(true); playagainredBtn.setEnabled(true);
                    playagainredBtn.setBackgroundResource(R.drawable.siklarclicked);
                    playagainblueBtn.setBackgroundResource(R.drawable.siklarclicked);
                    scorekirmizi=0; scoremavi=0;
                    playagainred=false; playagainblue=false;
                    puankirmizi=0; puanmavi=0; sorucounter=0;soruhazirlama();
                    if (finalmusic!=null) {finalmusic.release(); }
                    gamemusic = MediaPlayer.create(TwoplayerActivity.this,R.raw.footballmusic);
                    gamemusic.start();
                    gamemusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            gamemusic.start();
                        }
                    });}
            }
        });
        playagainredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playagainred=true;
                redcikis=false;
                exitactivity1.setBackgroundResource(R.drawable.hatalisik);
                exitactivity2.setBackgroundResource(R.drawable.hatalisik);
                playagainredBtn.setBackgroundResource(R.drawable.edittext);
               // playagainredBtn.setEnabled(false);
                if (playagainblue){playagainblueBtn.setEnabled(true); playagainredBtn.setEnabled(true);
                    playagainredBtn.setBackgroundResource(R.drawable.siklarclicked);
                    playagainblueBtn.setBackgroundResource(R.drawable.siklarclicked);
                    scorekirmizi=0; scoremavi=0;
                    playagainred=false; playagainblue=false;
                    puankirmizi=0; puanmavi=0; sorucounter=0;soruhazirlama();
                    if (finalmusic!=null) { finalmusic.release(); }
                    gamemusic = MediaPlayer.create(TwoplayerActivity.this,R.raw.footballmusic);
                    gamemusic.start();
                    gamemusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            gamemusic.start();
                        }
                    });}
            }
        });

        exitactivity1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                bluecikis=true;
                playagainblue=false;
                exitactivity1.setBackgroundResource(R.drawable.edittext);
                playagainblueBtn.setBackgroundResource(R.drawable.siklarclicked);
                playagainredBtn.setBackgroundResource(R.drawable.siklarclicked);
              //  exitactivity1.setEnabled(false);


                if (redcikis && bluecikis){


                    if (gamemusic!=null ) { gamemusic.release(); gamemusic=null;}
                    if (finalmusic!=null ) { finalmusic.release();}

                    Intent myIntent = new Intent(TwoplayerActivity.this, MainmenuActivity.class);
                    startActivity(myIntent);

                }
            }
        });

        exitactivity2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                redcikis=true;
                playagainred=false;
                exitactivity2.setBackgroundResource(R.drawable.edittext);
                playagainblueBtn.setBackgroundResource(R.drawable.siklarclicked);
                playagainredBtn.setBackgroundResource(R.drawable.siklarclicked);
             //   exitactivity2.setEnabled(false);


                if (bluecikis && redcikis){


                    if (gamemusic!=null ) { gamemusic.release(); gamemusic=null;}
                    if (finalmusic!=null ) { finalmusic.release();}

                    Intent myIntent = new Intent(TwoplayerActivity.this, MainmenuActivity.class);
                    startActivity(myIntent);

                }
            }
        });

    }





    @Override
    public void onBackPressed() {
            Toast.makeText(TwoplayerActivity.this,getString(R.string.quittoast),Toast.LENGTH_LONG).show();

    }

    public void  exitintent (View view) {

        bluecikis=false; redcikis=false;
        /////////////////////reklam///////////////////////////////

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        /////////////////////reklam///////////////////////////////

        final LinearLayout cikislayout = findViewById(R.id.cikislayout);
        final LinearLayout siklarmavilayout1 = findViewById(R.id.siklarmavilayout1);
        final LinearLayout siklarmavilayout2 = findViewById(R.id.siklarmavilayout2);
        final LinearLayout sorusayilarmavilayout = findViewById(R.id.sorusayilarmavilayout);
        final LinearLayout siklarkirmizilayout1 = findViewById(R.id.siklarkirmizilayout1);
        final LinearLayout siklarkirmizilayout2 = findViewById(R.id.siklarkirmizilayout2);
        final LinearLayout sorusayilarkırmızılayout = findViewById(R.id.sorusayilarkırmızılayout);
        final Button exitbutton1 = findViewById(R.id.exitBtn1); final Button exitbutton2 = findViewById(R.id.exitBtn2);
        final Button exitmenu1Btn = findViewById(R.id.exitmenu1Btn);  final Button exitmenu2Btn = findViewById(R.id.exitmenu2Btn);
        Button geridon1 = findViewById(R.id.geridonBtn1);  Button geridon2 = findViewById(R.id.geridonBtn2);

        exitmenu1Btn.setEnabled(false); exitmenu2Btn.setEnabled(false);

        Log.i("kalansurekirmizi",String.valueOf(kalansure));

        geridon1.setBackgroundResource(R.drawable.siklarclicked); geridon2.setBackgroundResource(R.drawable.siklarclicked);
        exitbutton1.setBackgroundResource(R.drawable.hatalisik);  exitbutton2.setBackgroundResource(R.drawable.hatalisik);


        try {
            cikislayout.setVisibility(View.VISIBLE);
            siklarkirmizilayout1.setVisibility(View.GONE); siklarkirmizilayout2.setVisibility(View.GONE); sorusayilarkırmızılayout.setVisibility(View.GONE);
            siklarmavilayout1.setVisibility(View.GONE);    siklarmavilayout2.setVisibility(View.GONE); sorusayilarmavilayout.setVisibility(View.GONE);
            timepaused=true; if (gamemusic!=null && gamemusic.isPlaying()) {gamemusic.pause();}
        }catch (Exception e) {e.printStackTrace();}



        geridon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cikislayout.setVisibility(View.INVISIBLE);
                siklarkirmizilayout1.setVisibility(View.VISIBLE); siklarkirmizilayout2.setVisibility(View.VISIBLE); sorusayilarkırmızılayout.setVisibility(View.VISIBLE);
                siklarmavilayout1.setVisibility(View.VISIBLE);    siklarmavilayout2.setVisibility(View.VISIBLE); sorusayilarmavilayout.setVisibility(View.VISIBLE);

                timepaused=false; surebaslat(); gamemusic.start();
                bluecikis=false; redcikis=false; exitbutton1.setEnabled(true); exitbutton2.setEnabled(true);
                exitbutton1.setBackgroundResource(R.drawable.hatalisik); exitbutton2.setBackgroundResource(R.drawable.hatalisik);
                exitmenu1Btn.setEnabled(true); exitmenu2Btn.setEnabled(true);


            }
        });
        geridon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cikislayout.setVisibility(View.INVISIBLE);
                siklarkirmizilayout1.setVisibility(View.VISIBLE); siklarkirmizilayout2.setVisibility(View.VISIBLE); sorusayilarkırmızılayout.setVisibility(View.VISIBLE);
                siklarmavilayout1.setVisibility(View.VISIBLE);    siklarmavilayout2.setVisibility(View.VISIBLE); sorusayilarmavilayout.setVisibility(View.VISIBLE);

                timepaused=false; surebaslat(); gamemusic.start();
                redcikis=false; bluecikis=false;  exitbutton1.setEnabled(true); exitbutton2.setEnabled(true);
                exitmenu1Btn.setEnabled(true); exitmenu2Btn.setEnabled(true);
                exitbutton1.setBackgroundResource(R.drawable.hatalisik); exitbutton2.setBackgroundResource(R.drawable.hatalisik);
            }
        });

        exitbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitbutton1.setBackgroundResource(R.drawable.edittext);
                redcikis=true;
                exitbutton1.setEnabled(false);

                if (redcikis) {Log.i("mavicikis","mavicikis yapıldı");}

                if (bluecikis && redcikis){


                    if (gamemusic!=null ) { gamemusic.release(); gamemusic=null;}

                    Intent myIntent = new Intent(TwoplayerActivity.this, MainmenuActivity.class);
                    startActivity(myIntent);

                }



            }
        });

        exitbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitbutton2.setBackgroundResource(R.drawable.edittext);
                bluecikis=true;
                exitbutton2.setEnabled(false);

                if (bluecikis) {Log.i("kirmizicikis","kirmizicikis yapıldı");}

                if (redcikis && redcikis){


                    if (gamemusic!=null ) { gamemusic.release(); gamemusic=null;}

                    Intent myIntent = new Intent(TwoplayerActivity.this, MainmenuActivity.class);
                    startActivity(myIntent);

                }



            }
        });




    }

    public void soruburada() {

        ImageView goalView1 = findViewById(R.id.goalView1);  ImageView goalView2 = findViewById(R.id.goalView2);
        final Button skormavi1Btn = findViewById(R.id.skormavi1Btn);        final Button skormavi2Btn = findViewById(R.id.skormavi2Btn);
        final Button skorkirmizi1Btn = findViewById(R.id.skorkirmizi1Btn);        final Button skorkirmizi2Btn = findViewById(R.id.skorkirmizi2Btn);


        goalView1.setVisibility(View.GONE); goalView2.setVisibility(View.GONE);
        skormavi1Btn.setText(String.valueOf(scoremavi)); skormavi2Btn.setText(String.valueOf(scoremavi));
        skorkirmizi1Btn.setText(String.valueOf(scorekirmizi)); skorkirmizi2Btn.setText(String.valueOf(scorekirmizi));

        Button sayi1View = findViewById(R.id.sayi1Btn);
        Button sayi2View = findViewById(R.id.sayi2Btn);
        Button sembol1 = (Button) findViewById(R.id.islmesembol1);
        Button sayi3View = findViewById(R.id.sayi3Btn);
        Button sayi4View = findViewById(R.id.sayi4Btn);
        Button sembol2 = (Button) findViewById(R.id.islmesembol2);
        androidx.appcompat.widget.SwitchCompat toplamaswitch = findViewById(R.id.toplamaswitch);
        androidx.appcompat.widget.SwitchCompat cikarmaswitch = findViewById(R.id.cikarmaswitch);
        androidx.appcompat.widget.SwitchCompat bolmeswitch = findViewById(R.id.bolmeswitch);
        androidx.appcompat.widget.SwitchCompat carpmaswitch = findViewById(R.id.carpmaswitch);

        final Button sika1Btn = findViewById(R.id.sika1Btn);
        final Button sikb1Btn = findViewById(R.id.sikb1Btn);
        final Button sikc1Btn = findViewById(R.id.sikc1Btn);
        final Button sikd1Btn = findViewById(R.id.sikd1Btn);
        final Button sika2Btn = findViewById(R.id.sika2Btn);
        final Button sikb2Btn = findViewById(R.id.sikb2Btn);
        final Button sikc2Btn = findViewById(R.id.sikc2Btn);
        final Button sikd2Btn = findViewById(R.id.sikd2Btn);



        ArrayList<String> semboller = new ArrayList<>();

        if (toplamaswitch.isChecked()){semboller.add("+");}
        if (cikarmaswitch.isChecked()){semboller.add("-");}
        if (bolmeswitch.isChecked()){semboller.add("÷");}
        if (carpmaswitch.isChecked()){semboller.add("x");}
        //semboller.add("+");
        //semboller.add("-");
        //semboller.add("x");
        //semboller.add("÷");
        Collections.shuffle(semboller);
        sembol1.setText(semboller.get(0));
        sembol2.setText(semboller.get(0));

        //Log.i("sembol",sembol.getText().toString());

        ///////////////////TOPLAMA////////////////////////////////

        if (sembol1.getText() == "+") {
            Random random = new Random();
            sayi1 = random.nextInt(20 - 0 + 0) + 0;
            sayi2 = random.nextInt(20 - 0 + 0) + 0;
            sayi1View.setText(String.valueOf(sayi1));
            sayi3View.setText(String.valueOf(sayi1));
            sayi2View.setText(String.valueOf(sayi2));
            sayi4View.setText(String.valueOf(sayi2));
            cevap = sayi1 + sayi2;

            ArrayList<Integer> presiklar = new ArrayList<Integer>();
            for (int i = cevap - 4; i <= cevap + 4; i++) {
                if (i >= 0) {
                    presiklar.add(i);
                }
            }
            presiklar.add(cevap + 10);
            presiklar.remove(Integer.valueOf(cevap));
            Collections.shuffle(presiklar);

            ArrayList<Integer> siklar = new ArrayList<Integer>();
            siklar.add(presiklar.get(0));
            siklar.add(presiklar.get(1));
            siklar.add(presiklar.get(2));
            siklar.add(cevap);
            Collections.shuffle(siklar);
            sika1Btn.setText("" + siklar.get(0));  sika1Btn.setTag(siklar.get(0));
            sikb1Btn.setText("" + siklar.get(1));  sikb1Btn.setTag(siklar.get(1));
            sikc1Btn.setText("" + siklar.get(2));  sikc1Btn.setTag(siklar.get(2));
            sikd1Btn.setText("" + siklar.get(3));  sikd1Btn.setTag(siklar.get(3));

            Collections.shuffle(siklar);
            sika2Btn.setText("" + siklar.get(0));  sika2Btn.setTag(siklar.get(0));
            sikb2Btn.setText("" + siklar.get(1));  sikb2Btn.setTag(siklar.get(1));
            sikc2Btn.setText("" + siklar.get(2));  sikc2Btn.setTag(siklar.get(2));
            sikd2Btn.setText("" + siklar.get(3));  sikd2Btn.setTag(siklar.get(3));


        }

        //////////////////////TOPLAMA BİTTİ///////////////////////


        //////////////////////ÇIKARMA ///////////////////////

        if (sembol1.getText() == "-") {
            Random random = new Random();
            int a = random.nextInt(20 - 0 + 0) + 0;
            int b = random.nextInt(20 - 0 + 0) + 0;
            if (a < b) {
                sayi1 = b;
                sayi2 = a;
            } else {
                sayi1 = a;
                sayi2 = b;
            }
            sayi1View.setText(String.valueOf(sayi1));
            sayi3View.setText(String.valueOf(sayi1));
            sayi2View.setText(String.valueOf(sayi2));
            sayi4View.setText(String.valueOf(sayi2));
            cevap = sayi1 - sayi2;

            ArrayList<Integer> presiklar = new ArrayList<Integer>();
            for (int i = cevap - 4; i <= cevap + 4; i++) {
                if (i >= 0) {
                    presiklar.add(i);
                }
            }
            // presiklar.add(cevap+10);
            presiklar.remove(Integer.valueOf(cevap));
            Collections.shuffle(presiklar);

            ArrayList<Integer> siklar = new ArrayList<Integer>();
            siklar.add(presiklar.get(0));
            siklar.add(presiklar.get(1));
            siklar.add(presiklar.get(2));
            siklar.add(cevap);
            Collections.shuffle(siklar);
            sika1Btn.setText("" + siklar.get(0));  sika1Btn.setTag(siklar.get(0));
            sikb1Btn.setText("" + siklar.get(1));  sikb1Btn.setTag(siklar.get(1));
            sikc1Btn.setText("" + siklar.get(2));  sikc1Btn.setTag(siklar.get(2));
            sikd1Btn.setText("" + siklar.get(3));  sikd1Btn.setTag(siklar.get(3));

            Collections.shuffle(siklar);
            sika2Btn.setText("" + siklar.get(0));  sika2Btn.setTag(siklar.get(0));
            sikb2Btn.setText("" + siklar.get(1));  sikb2Btn.setTag(siklar.get(1));
            sikc2Btn.setText("" + siklar.get(2));  sikc2Btn.setTag(siklar.get(2));
            sikd2Btn.setText("" + siklar.get(3));  sikd2Btn.setTag(siklar.get(3));

        }

        //////////////////////ÇIKARMA BİTTİ ///////////////////////

        //////////////////////ÇARPMA ///////////////////////


        if (sembol1.getText() == "x") {
            Random random = new Random();
            sayi1 = random.nextInt(10 - 0 + 0) + 0;
            sayi2 = random.nextInt(10 - 0 + 0) + 0;
            sayi1View.setText(String.valueOf(sayi1));
            sayi3View.setText(String.valueOf(sayi1));
            sayi2View.setText(String.valueOf(sayi2));
            sayi4View.setText(String.valueOf(sayi2));
            cevap = sayi1 * sayi2;

            ArrayList<Integer> presiklar = new ArrayList<Integer>();
            for (int i = cevap - 4; i <= cevap + 4; i++) {
                if (i >= 0) {
                    presiklar.add(i);
                }
            }
            presiklar.add(cevap + 10);
            presiklar.remove(Integer.valueOf(cevap));
            Collections.shuffle(presiklar);

            ArrayList<Integer> siklar = new ArrayList<Integer>();
            siklar.add(presiklar.get(0));
            siklar.add(presiklar.get(1));
            siklar.add(presiklar.get(2));
            siklar.add(cevap);
            Collections.shuffle(siklar);
            sika1Btn.setText("" + siklar.get(0));  sika1Btn.setTag(siklar.get(0));
            sikb1Btn.setText("" + siklar.get(1));  sikb1Btn.setTag(siklar.get(1));
            sikc1Btn.setText("" + siklar.get(2));  sikc1Btn.setTag(siklar.get(2));
            sikd1Btn.setText("" + siklar.get(3));  sikd1Btn.setTag(siklar.get(3));

            Collections.shuffle(siklar);
            sika2Btn.setText("" + siklar.get(0));  sika2Btn.setTag(siklar.get(0));
            sikb2Btn.setText("" + siklar.get(1));  sikb2Btn.setTag(siklar.get(1));
            sikc2Btn.setText("" + siklar.get(2));  sikc2Btn.setTag(siklar.get(2));
            sikd2Btn.setText("" + siklar.get(3));  sikd2Btn.setTag(siklar.get(3));
        }

        //////////////////////ÇARPMA BİTTİ///////////////////////

        //////////////////////BÖLME ///////////////////////

        if (sembol1.getText() == "÷") {


            Random random = new Random();
            sayi1 = random.nextInt(11 - 1 + 1) + 1;
            sayi2 = random.nextInt(11 - 1 + 1) + 1;

            int e = sayi1 * sayi2;


            sayi1View.setText(String.valueOf(e));
            sayi3View.setText(String.valueOf(e));
            sayi2View.setText(String.valueOf(sayi2));
            sayi4View.setText(String.valueOf(sayi2));
            cevap = sayi1;

            ArrayList<Integer> presiklar = new ArrayList<Integer>();
            for (int i = cevap - 4; i <= cevap + 4; i++) {
                if (i >= 0) {
                    presiklar.add(i);
                }
            }
            // presiklar.add(cevap+10);
            presiklar.remove(Integer.valueOf(cevap));
            Collections.shuffle(presiklar);

            ArrayList<Integer> siklar = new ArrayList<Integer>();
            siklar.add(presiklar.get(0));
            siklar.add(presiklar.get(1));
            siklar.add(presiklar.get(2));
            siklar.add(cevap);

            Collections.shuffle(siklar);
            sika1Btn.setText("" + siklar.get(0));  sika1Btn.setTag(siklar.get(0));
            sikb1Btn.setText("" + siklar.get(1));  sikb1Btn.setTag(siklar.get(1));
            sikc1Btn.setText("" + siklar.get(2));  sikc1Btn.setTag(siklar.get(2));
            sikd1Btn.setText("" + siklar.get(3));  sikd1Btn.setTag(siklar.get(3));

            Collections.shuffle(siklar);
            sika2Btn.setText("" + siklar.get(0));  sika2Btn.setTag(siklar.get(0));
            sikb2Btn.setText("" + siklar.get(1));  sikb2Btn.setTag(siklar.get(1));
            sikc2Btn.setText("" + siklar.get(2));  sikc2Btn.setTag(siklar.get(2));
            sikd2Btn.setText("" + siklar.get(3));  sikd2Btn.setTag(siklar.get(3));

            ////////////////////////BÖLME BİTTİ/////////////////////////////////////////////////
            ///////////////////////////////////////////TEKRAR SORU BİTTİ/////////////////
        }

        redclicked = false;
        blueclicked = false;

        Button exitapp1Btn = findViewById(R.id.exitmenu1Btn);  Button exitapp2Btn = findViewById(R.id.exitmenu2Btn);
        LinearLayout siklarmavilayout1 = findViewById(R.id.siklarmavilayout1);
        LinearLayout siklarmavilayout2 = findViewById(R.id.siklarmavilayout2);
        LinearLayout sorusayilarmavilayout = findViewById(R.id.sorusayilarmavilayout);
        LinearLayout siklarkirmizilayout1 = findViewById(R.id.siklarkirmizilayout1);
        LinearLayout siklarkirmizilayout2 = findViewById(R.id.siklarkirmizilayout2);
        LinearLayout sorusayilarkırmızılayout = findViewById(R.id.sorusayilarkırmızılayout);



        exitapp1Btn.setEnabled(true); exitapp2Btn.setEnabled(true);
        sika1Btn.setBackgroundResource(R.drawable.skortabelamavi);
        sikb1Btn.setBackgroundResource(R.drawable.skortabelamavi);
        sikc1Btn.setBackgroundResource(R.drawable.skortabelamavi);
        sikd1Btn.setBackgroundResource(R.drawable.skortabelamavi);
        sika2Btn.setBackgroundResource(R.drawable.skortabelakirmizi);
        sikb2Btn.setBackgroundResource(R.drawable.skortabelakirmizi);
        sikc2Btn.setBackgroundResource(R.drawable.skortabelakirmizi);
        sikd2Btn.setBackgroundResource(R.drawable.skortabelakirmizi);

        siklarkirmizilayout1.setVisibility(View.VISIBLE); siklarkirmizilayout2.setVisibility(View.VISIBLE); sorusayilarkırmızılayout.setVisibility(View.VISIBLE);
        siklarmavilayout1.setVisibility(View.VISIBLE);    siklarmavilayout2.setVisibility(View.VISIBLE); sorusayilarmavilayout.setVisibility(View.VISIBLE);


        siklarmavilayout1.startAnimation(AnimationUtils.loadAnimation(TwoplayerActivity.this, R.anim.slide_in));
        siklarmavilayout2.startAnimation(AnimationUtils.loadAnimation(TwoplayerActivity.this, R.anim.slide_in));
        siklarkirmizilayout1.startAnimation(AnimationUtils.loadAnimation(TwoplayerActivity.this, R.anim.slide_in));
        siklarkirmizilayout2.startAnimation(AnimationUtils.loadAnimation(TwoplayerActivity.this, R.anim.slide_in));
        sorusayilarkırmızılayout.startAnimation(AnimationUtils.loadAnimation(TwoplayerActivity.this, R.anim.slide_in));
        sorusayilarmavilayout.startAnimation(AnimationUtils.loadAnimation(TwoplayerActivity.this, R.anim.slide_in));


        // Log.i("puanmavi", String.valueOf(puanmavi));
        // Log.i("puankirmizi", String.valueOf(puankirmizi));
        // Button puankirmiziBtn = findViewById(R.id.puankirmiziBtn);
        // Button puanmaviBtn = findViewById(R.id.puanmaviBtn);

        // puankirmiziBtn.setText(String.valueOf(puankirmizi));
        // puanmaviBtn.setText(String.valueOf(puanmavi));


        LinearLayout maviresultlayout = findViewById(R.id.maviresultlayout);
        LinearLayout kirmiziresultlayout = findViewById(R.id.kirmiziresultlayout);

        maviresultlayout.setVisibility(View.GONE);
        kirmiziresultlayout.setVisibility(View.GONE);

         final MediaPlayer hakemdudugu = MediaPlayer.create(TwoplayerActivity.this, R.raw.hakemdudugu);
        hakemdudugu.start();
        hakemdudugu.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                hakemdudugu.release();

            }
        });

        // Button question1Btn = findViewById(R.id.question1Btn);
        //  Button question2Btn = findViewById(R.id.question2Btn);

        //  question1Btn.setText(String.valueOf(sorucounter) + "/12");
        //  question2Btn.setText(String.valueOf(sorucounter) + "/12");


        surebaslat();

        sika1Btn.setEnabled(true);
        sikb1Btn.setEnabled(true);
        sikc1Btn.setEnabled(true);
        sikd1Btn.setEnabled(true);
        sika2Btn.setEnabled(true);
        sikb2Btn.setEnabled(true);
        sikc2Btn.setEnabled(true);
        sikd2Btn.setEnabled(true);




    }

    public void surebaslat(){

        Log.i("kalan sure" , Integer.toString(kalansure));

        sure = new CountDownTimer(kalansure, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                Button suremaviBtn = findViewById(R.id.suremaviBtn);  Button surekirmiziBtn = findViewById(R.id.surekirmiziBtn);

                suremaviBtn.setText(String.valueOf(millisUntilFinished / 1000));
                surekirmiziBtn.setText(String.valueOf(millisUntilFinished / 1000));

                kalansure = (int) millisUntilFinished;

                if (timepaused){sure.cancel();}

            }

            @Override
            public void onFinish() {
                Button suremaviBtn = findViewById(R.id.suremaviBtn);   Button surekirmiziBtn = findViewById(R.id.surekirmiziBtn);
                suremaviBtn.setText("0");
                surekirmiziBtn.setText("0");

                final MediaPlayer surebitti = MediaPlayer.create(TwoplayerActivity.this,R.raw.hakemdudugu);
                surebitti.start();
                surebitti.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        surebitti.release();
                    }
                });

                soruhazirlama();

            }
        }.start();


    }

    public void onPause() {
        super.onPause();

        redcikis=false;
        bluecikis=false;

        LinearLayout maviresullayout = findViewById(R.id.maviresultlayout);

        final LinearLayout cikislayout = findViewById(R.id.cikislayout);
        final LinearLayout siklarmavilayout1 = findViewById(R.id.siklarmavilayout1);
        final LinearLayout siklarmavilayout2 = findViewById(R.id.siklarmavilayout2);
        final LinearLayout sorusayilarmavilayout = findViewById(R.id.sorusayilarmavilayout);
        final LinearLayout siklarkirmizilayout1 = findViewById(R.id.siklarkirmizilayout1);
        final LinearLayout siklarkirmizilayout2 = findViewById(R.id.siklarkirmizilayout2);
        final LinearLayout sorusayilarkırmızılayout = findViewById(R.id.sorusayilarkırmızılayout);
        RelativeLayout twoplayergiris = findViewById(R.id.twoplayergirislayout);
        final Button exitmenu1Btn = findViewById(R.id.exitmenu1Btn);  final Button exitmenu2Btn = findViewById(R.id.exitmenu2Btn);
        final Button exitbutton1 = (Button)findViewById(R.id.exitBtn1);
        final Button exitbutton2 = (Button)findViewById(R.id.exitBtn2);
        final Button geridon1 = (Button)findViewById(R.id.geridonBtn1);
        final Button geridon2 = (Button)findViewById(R.id.geridonBtn2);

        Log.i("kalansurekirmizi",String.valueOf(kalansure));

        geridon1.setBackgroundResource(R.drawable.siklarclicked); geridon2.setBackgroundResource(R.drawable.siklarclicked);
        exitbutton1.setBackgroundResource(R.drawable.hatalisik);  exitbutton2.setBackgroundResource(R.drawable.hatalisik);

        if (maviresullayout.getVisibility()==View.GONE && twoplayergiris.getVisibility()==View.GONE ){
            try {
                cikislayout.setVisibility(View.VISIBLE);
                siklarkirmizilayout1.setVisibility(View.GONE); siklarkirmizilayout2.setVisibility(View.GONE); sorusayilarkırmızılayout.setVisibility(View.GONE);
                siklarmavilayout1.setVisibility(View.GONE);    siklarmavilayout2.setVisibility(View.GONE); sorusayilarmavilayout.setVisibility(View.GONE);
                if (gamemusic!=null && gamemusic.isPlaying()) {gamemusic.pause();} timepaused=true;

            }catch (Exception e) { e.printStackTrace();}
           }



        geridon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cikislayout.setVisibility(View.INVISIBLE);
                siklarkirmizilayout1.setVisibility(View.VISIBLE); siklarkirmizilayout2.setVisibility(View.VISIBLE); sorusayilarkırmızılayout.setVisibility(View.VISIBLE);
                siklarmavilayout1.setVisibility(View.VISIBLE);    siklarmavilayout2.setVisibility(View.VISIBLE); sorusayilarmavilayout.setVisibility(View.VISIBLE);
                timepaused=false; surebaslat();
                if (gamemusic!=null) {gamemusic.start();}
                bluecikis=false; redcikis=false; exitbutton1.setEnabled(true); exitbutton2.setEnabled(true);
                exitbutton1.setBackgroundResource(R.drawable.hatalisik); exitbutton2.setBackgroundResource(R.drawable.hatalisik);
                exitmenu1Btn.setEnabled(true); exitmenu2Btn.setEnabled(true);

            }
        });

        geridon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cikislayout.setVisibility(View.INVISIBLE);
                siklarkirmizilayout1.setVisibility(View.VISIBLE); siklarkirmizilayout2.setVisibility(View.VISIBLE); sorusayilarkırmızılayout.setVisibility(View.VISIBLE);
                siklarmavilayout1.setVisibility(View.VISIBLE);    siklarmavilayout2.setVisibility(View.VISIBLE); sorusayilarmavilayout.setVisibility(View.VISIBLE);
                timepaused=false; surebaslat();
                if (gamemusic!=null) {gamemusic.start();}
                redcikis=false; bluecikis=false;  exitbutton1.setEnabled(true); exitbutton2.setEnabled(true);
                exitbutton1.setBackgroundResource(R.drawable.hatalisik); exitbutton2.setBackgroundResource(R.drawable.hatalisik);
                exitmenu1Btn.setEnabled(true); exitmenu2Btn.setEnabled(true);
            }
        });

        exitbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitbutton1.setBackgroundResource(R.drawable.edittext);
                redcikis=true;
                exitbutton1.setEnabled(false);

                if (bluecikis && redcikis){

                  //  if (gamemusic!=null && gamemusic.isPlaying()) {gamemusic.stop();}
                    if (gamemusic!=null ) { gamemusic.release(); gamemusic=null;}

                    Intent myIntent = new Intent(TwoplayerActivity.this, MainmenuActivity.class);
                    startActivity(myIntent);

                }



            }
        });

        exitbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitbutton2.setBackgroundResource(R.drawable.edittext);
                bluecikis=true;
                exitbutton2.setEnabled(false);

                if (redcikis && bluecikis ){

                 //   if (gamemusic!=null && gamemusic.isPlaying()) {gamemusic.stop();}
                    if (gamemusic!=null ) { gamemusic.release(); gamemusic=null;}

                    Intent myIntent = new Intent(TwoplayerActivity.this, MainmenuActivity.class);
                    startActivity(myIntent);

                }



            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();
        if (gamemusic != null) {
            gamemusic.release();
            gamemusic = null;
        }

    }











}
