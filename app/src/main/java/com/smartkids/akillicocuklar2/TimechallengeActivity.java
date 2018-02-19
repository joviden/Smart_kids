package com.smartkids.akillicocuklar2;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TimechallengeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private AdView mAdView;
    GoogleApiClient mGoogleApiClient;
    private boolean isPaused = false;


    RelativeLayout sorugovde,gerisayim;
    LinearLayout sonuclar;
    Integer sayi1,sayi2,sayi3,level,cevap,sorucounter,dogrucounter,yanliscounter,scorecounter,progress,levelsure,cevapcounter,kalansure;
    //Long kalansure;
    Float sayi3f,tip;
    TextView sayi1View,sayi2View;
    MediaPlayer optionclick,gamemusic;
    ProgressBar timebar;
    CountDownTimer oyunsure,gerisayimcounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timechallenge);
        sorucounter=0;
        yanliscounter=0;
        dogrucounter=0;
        scorecounter=0;
        level=0;
        progress=0;
        cevapcounter=0;
        kalansure=0;
        levelsure=20000;

/////////////////////
        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

/////////////////////

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

        Button timeleaderbtn = (Button)findViewById(R.id.leader);
        timeleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mGoogleApiClient.isConnected()){
                    // Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
                    // Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_score_table),50);
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,getString(R.string.leaderboard_time_challenge_stars)),0);
                }else {
                    mGoogleApiClient.connect();
                    //Toast.makeText(getApplicationContext(),"baglı degil",Toast.LENGTH_SHORT).show();

                }

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


 /////////////////////////

        sorugovde = findViewById(R.id.sorugovdelayout);
        gerisayim = findViewById(R.id.timegirislayout);
        sonuclar = findViewById(R.id.sonuclarlayout);
        Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        Button sikdBtn = (Button)findViewById(R.id.sikdBtn);
        final Button girisBtn = (Button)findViewById(R.id.girisBtn);
        final Button cikisBtn = (Button)findViewById(R.id.cikisBtn);
        final Button countertext = (Button)findViewById(R.id.progressTxt);


        final Button gerisayimbtn = (Button)findViewById(R.id.gerisayimbtn);
        final LinearLayout gerisayimlayout = (LinearLayout)findViewById(R.id.countdownlayout);
        final LinearLayout btngrp = (LinearLayout)findViewById(R.id.btngrp);

        TextView levelTxt = (TextView)findViewById(R.id.sorunumarasıTxtv);
        levelTxt.setText(getString(R.string.level)+ " 1");
        timebar = findViewById(R.id.timebar);

        sorugovde.setVisibility(View.GONE);
        gerisayim.setVisibility(View.VISIBLE);
        btngrp.setVisibility(View.VISIBLE);
        gerisayimlayout.setVisibility(View.INVISIBLE);
        sonuclar.setVisibility(View.GONE);
        girisBtn.setEnabled(true);
        cikisBtn.setEnabled(true);



      girisBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              sorugovde.setAlpha(1);

              gerisayimlayout.setVisibility(View.VISIBLE);
              btngrp.setVisibility(View.INVISIBLE);
              girisBtn.setEnabled(false);
              cikisBtn.setEnabled(false);

              gerisayimcounter = new CountDownTimer(4000,1000) {
                  @Override
                  public void onTick(long millisUntilFinished) {

                      gerisayimbtn.setText("" + millisUntilFinished / 1000);

                  }

                  @Override
                  public void onFinish() {

                      gerisayim.setVisibility(View.GONE);
                      sorugovde.setVisibility(View.VISIBLE);


                      gamemusic = MediaPlayer.create(TimechallengeActivity.this,R.raw.gamemusic1);
                      gamemusic.start();

                      gamemusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                          @Override
                          public void onCompletion(MediaPlayer mediaPlayer) {
                              gamemusic.start();

                          }
                      });

                      soruhazirlama();

                  }
              }.start();

              //////////////////////////////////////////////

          }

      });
    }

    public void clicksika (View view) {
        final Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button)findViewById(R.id.sikdBtn);
        final TextView skorTxv = (TextView)findViewById(R.id.skorTxv);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);


        sikaBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);

        sorucounter++;
        cevapcounter++;
        oyunsure.cancel();


        if (cevap.equals(Integer.parseInt(sikaBtn.getText().toString()))) {

            optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            dogrucounter++;

            scorecounter=scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorTxv.setText(String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

        } else {

            optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            sikaBtn.setBackgroundResource(R.drawable.hatalisik);
            yanliscounter++;
            if (life1.getVisibility()==View.VISIBLE){
                life1.setVisibility(View.INVISIBLE);
            }else if(life2.getVisibility()==View.VISIBLE){
                life2.setVisibility(View.INVISIBLE);

            }else {
                if (life3.getVisibility()==View.VISIBLE) {
                    life3.setVisibility(View.GONE);

                    /////////////////////////////////
                    if (gamemusic != null) {
                        gamemusic.stop();
                        gamemusic.release();
                    }
                    scoreboard();

                }

            }

            /////////////////////////////////////



            if (cevap.equals(Integer.parseInt(sikbBtn.getText().toString()))){sikbBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (cevap.equals(Integer.parseInt(sikcBtn.getText().toString()))){sikcBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (cevap.equals(Integer.parseInt(sikdBtn.getText().toString()))){sikdBtn.setBackgroundResource(R.drawable.siklarclicked);}
        }

        ///////////////////////////////////TEKRAR SORU/////////////////////////

        if (yanliscounter<3) {

           soruhazirlama();

        }





    }
    public void clicksikb (View view) {
        final Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button)findViewById(R.id.sikdBtn);
        final TextView skorTxv = (TextView)findViewById(R.id.skorTxv);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);


        sikbBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);

        sorucounter++;
        cevapcounter++;
        oyunsure.cancel();

        if (cevap.equals(Integer.parseInt(sikbBtn.getText().toString()))) {

            optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            dogrucounter++;

            scorecounter=scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorTxv.setText(String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

        } else {

            optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            sikbBtn.setBackgroundResource(R.drawable.hatalisik);
            yanliscounter++;
            if (life1.getVisibility()==View.VISIBLE){
                life1.setVisibility(View.INVISIBLE);
            }else if(life2.getVisibility()==View.VISIBLE){
                life2.setVisibility(View.INVISIBLE);

            }else {
                if (life3.getVisibility()==View.VISIBLE) {
                    life3.setVisibility(View.GONE);

                    /////////////////////////////////
                    if (gamemusic != null) {
                        gamemusic.stop();
                        gamemusic.release();
                    }
                    scoreboard();

                }

            }

            /////////////////////////////////////

            /////////////////////////////
            if (cevap.equals(Integer.parseInt(sikaBtn.getText().toString()))){sikaBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (cevap.equals(Integer.parseInt(sikcBtn.getText().toString()))){sikcBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (cevap.equals(Integer.parseInt(sikdBtn.getText().toString()))){sikdBtn.setBackgroundResource(R.drawable.siklarclicked);}
        }

        ///////////////////////////////////TEKRAR SORU/////////////////////////

        if (yanliscounter<3) {

            soruhazirlama();

        }
    }
    public void clicksikc (View view) {
        final Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button)findViewById(R.id.sikdBtn);
        final TextView skorTxv = (TextView)findViewById(R.id.skorTxv);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);


        sikcBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);

        sorucounter++;
        cevapcounter++;
        oyunsure.cancel();

        if (cevap.equals(Integer.parseInt(sikcBtn.getText().toString()))) {

            optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            dogrucounter++;

            scorecounter=scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorTxv.setText(String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

        } else {

            optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            sikcBtn.setBackgroundResource(R.drawable.hatalisik);
            yanliscounter++;

            if (life1.getVisibility()==View.VISIBLE){
                life1.setVisibility(View.INVISIBLE);
            }else if(life2.getVisibility()==View.VISIBLE){
                life2.setVisibility(View.INVISIBLE);

            }else {
                if (life3.getVisibility()==View.VISIBLE) {
                    life3.setVisibility(View.GONE);

                    /////////////////////////////////
                    if (gamemusic != null) {
                        gamemusic.stop();
                        gamemusic.release();
                    }
                    scoreboard();

                }

            }

            /////////////////////////////////////
            if (cevap.equals(Integer.parseInt(sikbBtn.getText().toString()))){sikbBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (cevap.equals(Integer.parseInt(sikaBtn.getText().toString()))){sikaBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (cevap.equals(Integer.parseInt(sikdBtn.getText().toString()))){sikdBtn.setBackgroundResource(R.drawable.siklarclicked);}
        }

        ////////////////////////////////TEKRAR SORU/////////////////////////

        if (yanliscounter<3) {

            soruhazirlama();

        }





    }
    public void clicksikd (View view) {
        final Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button)findViewById(R.id.sikdBtn);
        final TextView skorTxv = (TextView)findViewById(R.id.skorTxv);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);


        sikdBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);

        sorucounter++;
        cevapcounter++;
        oyunsure.cancel();

        if (cevap.equals(Integer.parseInt(sikdBtn.getText().toString()))) {

            optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            dogrucounter++;

            scorecounter=scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorTxv.setText(String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

        } else {

            optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            sikdBtn.setBackgroundResource(R.drawable.hatalisik);
            yanliscounter++;

            if (life1.getVisibility()==View.VISIBLE){
                life1.setVisibility(View.INVISIBLE);
            }else if(life2.getVisibility()==View.VISIBLE){
                life2.setVisibility(View.INVISIBLE);

            }else {
                if (life3.getVisibility()==View.VISIBLE) {
                    life3.setVisibility(View.GONE);

                    /////////////////////////////////

                    if (gamemusic != null) {
                        gamemusic.stop();
                        gamemusic.release();
                    }
                    scoreboard();

                }

            }

            /////////////////////////////////////
            if (cevap.equals(Integer.parseInt(sikbBtn.getText().toString()))){sikbBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (cevap.equals(Integer.parseInt(sikcBtn.getText().toString()))){sikcBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (cevap.equals(Integer.parseInt(sikaBtn.getText().toString()))){sikaBtn.setBackgroundResource(R.drawable.siklarclicked);}
        }

        ////////////////////////////////TEKRAR SORU/////////////////////////

        if (yanliscounter<3) {

            soruhazirlama();

        }
    }

    public void cikis(View view) {


        Intent i = new Intent(TimechallengeActivity.this,MainmenuActivity.class);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(i);

    }

    public void backmenu (View view) {

        sorugovde = findViewById(R.id.sorugovdelayout);
        gerisayim = findViewById(R.id.timegirislayout);
        sonuclar = findViewById(R.id.sonuclarlayout);
        Button girisBtn = (Button)findViewById(R.id.girisBtn);
        final Button gerisayimbtn = (Button)findViewById(R.id.gerisayimbtn);
        final LinearLayout gerisayimlayout = (LinearLayout)findViewById(R.id.countdownlayout);
        final LinearLayout btngrp = (LinearLayout)findViewById(R.id.btngrp);
        final LinearLayout cikislayout = (LinearLayout)findViewById(R.id.cikislayout);
        final Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button)findViewById(R.id.sikdBtn);
        final LinearLayout sorulayout = (LinearLayout)findViewById(R.id.sorusayilarlayout);

        cikislayout.setVisibility(View.GONE);
        sorugovde.setVisibility(View.GONE);
        //gerisayim.setVisibility(View.VISIBLE);
        btngrp.setVisibility(View.VISIBLE);
        gerisayimlayout.setVisibility(View.GONE);
        sonuclar.setVisibility(View.GONE);
        sorugovde.setAlpha(1);
        oyunsure.cancel();
        sikaBtn.setEnabled(true);sikbBtn.setEnabled(true);sikcBtn.setEnabled(true);sikdBtn.setEnabled(true);
        sikaBtn.setVisibility(View.VISIBLE);sikbBtn.setVisibility(View.VISIBLE);sikcBtn.setVisibility(View.VISIBLE);sikdBtn.setVisibility(View.VISIBLE);
        sorulayout.setVisibility(View.VISIBLE);



        scoreboard();


    }

    public void pause (View view) {
        Button pause = (Button)findViewById(R.id.cevapBtn);
        Button tamamlaaBtn = (Button)findViewById(R.id.tamamlaBtn);
        Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        Button sikdBtn = (Button)findViewById(R.id.sikdBtn);
        LinearLayout sorulayout = (LinearLayout)findViewById(R.id.sorusayilarlayout);
        RelativeLayout alphalayout = (RelativeLayout)findViewById(R.id.alphalayout);

        //Log.i("deneme",pause.getText().toString());



        if (!isPaused) {

            gamemusic.pause();
            Log.i("kalansure",String.valueOf(kalansure));
            pause.setText(getString(R.string.go));
            sikaBtn.setEnabled(false);sikbBtn.setEnabled(false);sikcBtn.setEnabled(false);sikdBtn.setEnabled(false);
            sikaBtn.setVisibility(View.INVISIBLE);sikbBtn.setVisibility(View.INVISIBLE);sikcBtn.setVisibility(View.INVISIBLE);sikdBtn.setVisibility(View.INVISIBLE);
            sorulayout.setVisibility(View.INVISIBLE);
            alphalayout.setAlpha((float)0.4);
            isPaused=true;
            tamamlaaBtn.setEnabled(false);




        }else {

            gamecounter();
            gamemusic.start();
            pause.setText(getString(R.string.pause));
            sikaBtn.setEnabled(true);sikbBtn.setEnabled(true);sikcBtn.setEnabled(true);sikdBtn.setEnabled(true);
            sikaBtn.setVisibility(View.VISIBLE);sikbBtn.setVisibility(View.VISIBLE);sikcBtn.setVisibility(View.VISIBLE);sikdBtn.setVisibility(View.VISIBLE);
            sorulayout.setVisibility(View.VISIBLE);
            alphalayout.setAlpha(1);
            isPaused=false;
            tamamlaaBtn.setEnabled(true);
        }




    }

    public void tamamla (View view) {
        final LinearLayout cikislayout = (LinearLayout)findViewById(R.id.cikislayout);
        Button exitbutton = (Button)findViewById(R.id.exitBtn);
        Button geridon = (Button)findViewById(R.id.geridonBtn);
        final Button pauseBtn = (Button)findViewById(R.id.cevapBtn);
        final Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button)findViewById(R.id.sikdBtn);
        final LinearLayout sorulayout = (LinearLayout)findViewById(R.id.sorusayilarlayout);
        cikislayout.setVisibility(View.VISIBLE);
        isPaused=true;
        gamemusic.pause();
        sikaBtn.setEnabled(false);sikbBtn.setEnabled(false);sikcBtn.setEnabled(false);sikdBtn.setEnabled(false);
        sikaBtn.setVisibility(View.INVISIBLE);sikbBtn.setVisibility(View.INVISIBLE);sikcBtn.setVisibility(View.INVISIBLE);sikdBtn.setVisibility(View.INVISIBLE);
        sorulayout.setVisibility(View.INVISIBLE);
        sorugovde.setAlpha((float)0.4);

        pauseBtn.setEnabled(false);



        geridon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cikislayout.setVisibility(View.INVISIBLE);
                sorugovde.setAlpha(1);
                isPaused=false;
               gamecounter();
                gamemusic.start();
                sikaBtn.setEnabled(true);sikbBtn.setEnabled(true);sikcBtn.setEnabled(true);sikdBtn.setEnabled(true);
                sikaBtn.setVisibility(View.VISIBLE);sikbBtn.setVisibility(View.VISIBLE);sikcBtn.setVisibility(View.VISIBLE);sikdBtn.setVisibility(View.VISIBLE);
                sorulayout.setVisibility(View.VISIBLE);
                pauseBtn.setEnabled(true);
            }
        });




    }

    @Override
    public void onBackPressed() {

        Toast.makeText(TimechallengeActivity.this,getString(R.string.quittoast),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public  void  gamecounter() {
        final Button countertext = (Button)findViewById(R.id.progressTxt);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);

        oyunsure = new CountDownTimer(kalansure,100) {

            @Override
            public void onTick(long millisUntilFinished) {
                timebar.setMax(levelsure);
                kalansure = (int) millisUntilFinished;

                if (isPaused) {
                    cancel();

                } else {

                    countertext.setText(String.valueOf(millisUntilFinished / 1000));

                    timebar.setProgress(levelsure - kalansure);
                    //Log.v("Tick", "Tick of Progress"+ i+ millisUntilFinished);


                    Log.i("Tick of Progress", String.valueOf(progress));

                }
            }

            @Override
            public void onFinish() {


                if (life1.getVisibility() == View.VISIBLE && life2.getVisibility() == View.VISIBLE && life3.getVisibility() == View.VISIBLE) {
                    life1.setVisibility(View.INVISIBLE);
                    optionclick = MediaPlayer.create(TimechallengeActivity.this, R.raw.wronganswer);
                    optionclick.start();
                    optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            optionclick.release();
                        }
                    });
                    soruhazirlama();

                } else if (life2.getVisibility() == View.VISIBLE && life3.getVisibility() == View.VISIBLE) {
                    life2.setVisibility(View.INVISIBLE);
                    optionclick = MediaPlayer.create(TimechallengeActivity.this, R.raw.wronganswer);
                    optionclick.start();
                    optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            optionclick.release();
                        }
                    });
                    soruhazirlama();

                }else  {
                    life3.setVisibility(View.INVISIBLE);

                    /////////////////////////////////
                    if (gamemusic != null) {
                        gamemusic.stop();
                        gamemusic.release();
                    }
                    scoreboard();

                }

            }


        }.start();
    }

    public void scoreboard() {

        //gamemusic.stop();
       // gamemusic.release();
        sonuclar.setVisibility(View.VISIBLE);
        sonuclar.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this,R.anim.slide_in));
        sorugovde.setVisibility(View.GONE);

        final Button dogruView=(Button)findViewById(R.id.dogrusayisiTxtv);
        final Button yanlisView=(Button)findViewById(R.id.yanlissayisiTxtv);
        final Button sorusayisiView=(Button)findViewById(R.id.sorusayisiTxtv);
        final Button puanView = (Button)findViewById(R.id.toplampuanTxtv);
        final Button testcikisBtn = (Button)findViewById(R.id.testcikisBtn);


        testcikisBtn.setEnabled(false);
        sorugovde.setVisibility(View.GONE);
        sonuclar.setVisibility(View.VISIBLE);

        if (cevapcounter >0) {
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(0, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    puanView.setText(getString(R.string.totalpoints) + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(2000);
            animator.start();
            //ara
            final ValueAnimator animatoryanlis = new ValueAnimator();
            animatoryanlis.setObjectValues(0, yanliscounter);
            animatoryanlis.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    yanlisView.setText(getString(R.string.wronganswers )+ String.valueOf(animatoryanlis.getAnimatedValue()));
                }
            });
            animatoryanlis.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animatoryanlis.setDuration(2000);
            animatoryanlis.start();
            //ara
            final ValueAnimator animatordogru = new ValueAnimator();
            animatordogru.setObjectValues(0, dogrucounter);
            animatordogru.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    dogruView.setText(getString(R.string.rightanswers)+ String.valueOf(animatordogru.getAnimatedValue()));
                }
            });
            animatordogru.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animatordogru.setDuration(2000);
            animatordogru.start();
            //ara
            final ValueAnimator animatorsoru = new ValueAnimator();
            animatorsoru.setObjectValues(0, sorucounter);
            animatorsoru.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    sorusayisiView.setText(getString(R.string.totalquestion )+ String.valueOf(animatorsoru.getAnimatedValue()));
                }
            });
            animatorsoru.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animatorsoru.setDuration(2000);
            animatorsoru.start();


            final MediaPlayer countsound = MediaPlayer.create(TimechallengeActivity.this,R.raw.count1);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(2000, 2000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();
                        testcikisBtn.setEnabled(true);

                    }
                }
            };
            timer.start();
            //media finished

        } else { //çıkış yaptır!!!!!!
            Toast.makeText(TimechallengeActivity.this,getString(R.string.toasthicsorucevaplamadın),Toast.LENGTH_SHORT).show();



            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(sorucounter));
            testcikisBtn.setEnabled(true);
        }

        /////////////////skor bilgileri burada///////////////////////////////

        Integer inttimesoru,inttimedogru,inttimemeyanlis,inttimescore,timescorekumulatif;
        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        inttimesoru = sp.getInt("timesoru",0);
        inttimedogru= sp.getInt("timedogru",0);
        inttimemeyanlis = sp.getInt("timeyanlis",0);
        inttimescore = sp.getInt("timescore",0);
        timescorekumulatif = sp.getInt("timescorekumulatif",0);


        spEditor.putInt("timesoru", (sorucounter+inttimesoru));
        spEditor.putInt("timedogru",(dogrucounter+inttimedogru));
        spEditor.putInt("timeyanlis",(yanliscounter+inttimemeyanlis));
        spEditor.putInt("timescore",(scorecounter));
        spEditor.putInt("timescorekumulatif",(scorecounter+timescorekumulatif));
        spEditor.commit();


        timescorekumulatif = sp.getInt("timescorekumulatif",0);

        Log.i("skorerdem",String.valueOf(timescorekumulatif));

        if (mGoogleApiClient.isConnected()){
            //Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
            Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_time_challenge_stars),scorecounter);
            //startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,getString(R.string.leaderboard_smart_kids_kings)),0);
        }else {
            mGoogleApiClient.connect();
            //Toast.makeText(getApplicationContext(),"baglı degil",Toast.LENGTH_SHORT).show();
        }

        ////////////////////////SKOR BİLGİLERİ BİTTİ////////////////////////




        /////////////////////////////////////
    }

    public  void soruhazirlama() {

        final Button sikaBtn = (Button)findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button)findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button)findViewById(R.id.sikdBtn);


                sikaBtn.setEnabled(true);
                sikbBtn.setEnabled(true);
                sikcBtn.setEnabled(true);
                sikdBtn.setEnabled(true);
                sikaBtn.setBackgroundResource(R.drawable.siklar);
                sikbBtn.setBackgroundResource(R.drawable.siklar);
                sikcBtn.setBackgroundResource(R.drawable.siklar);
                sikdBtn.setBackgroundResource(R.drawable.siklar);
                final RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
                sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.fadein_out));

                if (dogrucounter < 5) {
                    level = 1;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 1");
                }
                if (dogrucounter > 5 && dogrucounter < 10) {
                    level = 2;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 2");
                    if (dogrucounter == 6) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }
                if (dogrucounter > 10 && dogrucounter < 15) {
                    level = 3;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 3");
                    if (dogrucounter == 11) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }
                if (dogrucounter > 15 && dogrucounter < 20) {
                    level = 4;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 4");
                    if (dogrucounter == 16) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }
                if (dogrucounter > 20 && dogrucounter < 25) {
                    level = 5;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 5");
                    gamemusic.reset();
                    gamemusic = MediaPlayer.create(TimechallengeActivity.this, R.raw.gamemusic2);
                    gamemusic.start();
                    gamemusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            gamemusic.start();
                        }
                    });
                    if (dogrucounter == 21) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }
                if (dogrucounter > 25 && dogrucounter < 30) {
                    level = 6;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 6");
                    if (dogrucounter == 26) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }
                if (dogrucounter > 30 && dogrucounter < 35) {
                    level = 7;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 7");
                    if (dogrucounter == 31) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }
                if (dogrucounter > 35 && dogrucounter < 40) {
                    level = 8;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 8");
                    if (dogrucounter == 36) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }
                if (dogrucounter > 40 && dogrucounter < 45) {
                    level = 9;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 9");
                    if (dogrucounter == 41) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }
                if (dogrucounter > 45 && dogrucounter < 50) {
                    level = 10;
                    TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
                    levelTxt.setText(getString(R.string.level) + " 10");
                    if (dogrucounter == 46) {
                        levelTxt.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        sorugovde.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
                        final MediaPlayer victory = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
                        victory.start();
                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                victory.release();
                            }
                        });
                    }
                }

                if (level == 1) {
                    kalansure = 20000;
                    levelsure = 20000;
                }
                if (level == 2) {
                    kalansure = 18000;
                    levelsure = 18000;
                }
                if (level == 3) {
                    kalansure = 16000;
                    levelsure = 16000;
                }
                if (level == 5) {
                    kalansure = 14000;
                    levelsure = 14000;
                }
                if (level == 6) {
                    kalansure = 11000;
                    levelsure = 11000;
                }
                if (level == 7) {
                    kalansure = 10000;
                    levelsure = 10000;
                }
                if (level == 8) {
                    kalansure = 8000;
                    levelsure = 8000;
                }
                if (level == 9) {
                    kalansure = 5000;
                    levelsure = 5000;
                }
                if (level == 10) {
                    kalansure = 4000;
                    levelsure = 4000;
                }
                if (level > 10) {
                    kalansure = 3000;
                    levelsure = 3000;
                }
                Log.i("dogrusayisi", String.valueOf(dogrucounter));
                Log.i("levelsayisi", String.valueOf(level));

                gamecounter();


                sayi1View = (TextView) findViewById(R.id.sayi1Txtv);
                sayi2View = (TextView) findViewById(R.id.sayi2Txtv);
                Button sembol = (Button) findViewById(R.id.islmesembol);

                ArrayList<String> semboller = new ArrayList<>();
                semboller.add("+");
                semboller.add("-");
                semboller.add("x");
                semboller.add("÷");
                Collections.shuffle(semboller);
                sembol.setText(semboller.get(0));

                //Log.i("sembol",sembol.getText().toString());

                ///////////////////TOPLAMA////////////////////////////////

                if (sembol.getText() == "+") {
                    Random random = new Random();
                    sayi1 = random.nextInt(20 - 0 + 0) + 0;
                    sayi2 = random.nextInt(20 - 0 + 0) + 0;
                    sayi1View.setText(String.valueOf(sayi1));
                    sayi2View.setText(String.valueOf(sayi2));
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
                    sikaBtn.setText("" + siklar.get(0));
                    sikbBtn.setText("" + siklar.get(1));
                    sikcBtn.setText("" + siklar.get(2));
                    sikdBtn.setText("" + siklar.get(3));


                }

                //////////////////////TOPLAMA BİTTİ///////////////////////


                //////////////////////ÇIKARMA ///////////////////////

                if (sembol.getText() == "-") {
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
                    sayi2View.setText(String.valueOf(sayi2));
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
                    sikaBtn.setText("" + siklar.get(0));
                    sikbBtn.setText("" + siklar.get(1));
                    sikcBtn.setText("" + siklar.get(2));
                    sikdBtn.setText("" + siklar.get(3));

                }

                //////////////////////ÇIKARMA BİTTİ ///////////////////////

                //////////////////////ÇARPMA ///////////////////////


                if (sembol.getText() == "x") {
                    Random random = new Random();
                    sayi1 = random.nextInt(10 - 0 + 0) + 0;
                    sayi2 = random.nextInt(10 - 0 + 0) + 0;
                    sayi1View.setText(String.valueOf(sayi1));
                    sayi2View.setText(String.valueOf(sayi2));
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
                    sikaBtn.setText("" + siklar.get(0));
                    sikbBtn.setText("" + siklar.get(1));
                    sikcBtn.setText("" + siklar.get(2));
                    sikdBtn.setText("" + siklar.get(3));
                }

                //////////////////////ÇARPMA BİTTİ///////////////////////

                //////////////////////BÖLME ///////////////////////

                if (sembol.getText() == "÷") {

                    do {
                        Random random = new Random();
                        int a = random.nextInt(100 - 1 + 1) + 1;
                        int b = random.nextInt(11 - 1 + 1) + 1;
                        if (a < b) {
                            sayi1 = b;
                            sayi2 = a;
                        } else {
                            sayi1 = a;
                            sayi2 = b;
                        }
                        sayi3 = sayi1 / sayi2;
                        sayi3f = (float) sayi1 / sayi2;
                        tip = sayi3f - sayi3;
                    } while (tip != 0.0);

                    sayi1View.setText(String.valueOf(sayi1));
                    sayi2View.setText(String.valueOf(sayi2));
                    cevap = sayi1 / sayi2;

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
                    sikaBtn.setText("" + siklar.get(0));
                    sikbBtn.setText("" + siklar.get(1));
                    sikcBtn.setText("" + siklar.get(2));
                    sikdBtn.setText("" + siklar.get(3));
                }


                ///////////////////////////////////////////TEKRAR SORU BİTTİ/////////////////





        Log.i("kalansure",String.valueOf(kalansure));Log.i("levelsure",String.valueOf(levelsure));

    }

    public void onPause() {
        super.onPause();
        Button pause = (Button)findViewById(R.id.cevapBtn);        Button sikaBtn = (Button)findViewById(R.id.sikaBtn);        Button sikbBtn = (Button)findViewById(R.id.sikbBtn);
        Button sikcBtn = (Button)findViewById(R.id.sikcBtn);        Button sikdBtn = (Button)findViewById(R.id.sikdBtn);        LinearLayout sorulayout = (LinearLayout)findViewById(R.id.sorusayilarlayout);
        RelativeLayout alphalayout = (RelativeLayout)findViewById(R.id.alphalayout);
        sorugovde = findViewById(R.id.sorugovdelayout);

        //Log.i("deneme",pause.getText().toString());


            isPaused=true;
        if (sorugovde.getVisibility()==View.VISIBLE) {
            gamemusic.pause();
        }


            pause.setText(getString(R.string.go));
            sikaBtn.setEnabled(false);sikbBtn.setEnabled(false);sikcBtn.setEnabled(false);sikdBtn.setEnabled(false);
            sikaBtn.setVisibility(View.INVISIBLE);sikbBtn.setVisibility(View.INVISIBLE);sikcBtn.setVisibility(View.INVISIBLE);sikdBtn.setVisibility(View.INVISIBLE);
            sorulayout.setVisibility(View.INVISIBLE);
            alphalayout.setAlpha((float)0.4);

    }





}
