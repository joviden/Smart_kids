package com.smartkids.akillicocuklar2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.games.Games;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class TekCiftActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private AdView mAdView;
    GoogleApiClient mGoogleApiClient;
    MediaPlayer gamemusic;
    CountDownTimer oyunsure;
    Timer t;
    Integer kalansure,sayi1,sayi2,sayi3,sayi4,sayi5,sayi6,sayi7,sayi8,sayi9,cevap1,cevap2,cevap3,cevap4;
    Float sayi9f,tip;
    private boolean isPaused = false;







    int balonsayisi = 0;
    int dogrubalon = 0;
    int hatalibalon =0;
    int scorecounter =0;
    int sira = 0;
    int sorusayac=0;
    int butonsec=0;






    @Override

    protected void onCreate(Bundle savedInstance) {






        super.onCreate(savedInstance);
        setContentView(R.layout.tekciftlayout);
//////////
        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
///////////////
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

        /////////////////
        RelativeLayout boardlayout = (RelativeLayout)findViewById(R.id.boardlayout);
        final RelativeLayout baslatlayout = (RelativeLayout)findViewById(R.id.baslatlayout);
        final LinearLayout sonuclarlayout = (LinearLayout)findViewById(R.id.sonuclarlayout);

        TextView sorumetin =(TextView)findViewById(R.id.sorumetinTxv);
        LinearLayout countdownlayout = (LinearLayout)findViewById(R.id.countdownlayout);
        boardlayout.setVisibility(View.GONE);
        baslatlayout.setVisibility(View.VISIBLE);
        sonuclarlayout.setVisibility(View.GONE);

        countdownlayout.setVisibility(View.GONE);






    }

    public void basla (View view) {
        TextView sorumetin =(TextView)findViewById(R.id.sorumetinTxv);
        LinearLayout countdownlayout = (LinearLayout)findViewById(R.id.countdownlayout);
        final Button gerisayimbtn =(Button)findViewById(R.id.gerisayimbtn);
        final Button yurutBtn =(Button)findViewById(R.id.yurut);
        sorumetin.setVisibility(View.INVISIBLE);
        countdownlayout.setVisibility(View.VISIBLE);
        yurutBtn.setEnabled(false);




        final LinearLayout sonuclarlayout = (LinearLayout)findViewById(R.id.sonuclarlayout);
        final RelativeLayout baslatlayout = (RelativeLayout)findViewById(R.id.baslatlayout);
        final RelativeLayout boardlayout = (RelativeLayout)findViewById(R.id.boardlayout);

        final Button cikisbtn = (Button) findViewById(R.id.cikisBtn);
        final Button pausebtn = (Button)findViewById(R.id.pauseBtn);


        scorecounter=0;
        dogrubalon=0;
        hatalibalon=0;
        balonsayisi=0;


        final CountDownTimer gerisayim = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gerisayimbtn.setText("" + millisUntilFinished / 1000);

            }

            @Override
            public void onFinish() {

                balonakisi();



                boardlayout.setVisibility(View.VISIBLE);
                yurutBtn.setEnabled(true);

                gamemusic = MediaPlayer.create(TekCiftActivity.this,R.raw.gamemusic1);
                gamemusic.start();
                gamemusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        gamemusic.start();
                    }
                });

            }
        }.start();



    }




    @Override
    public void onBackPressed() {


        RelativeLayout baslatlayout = (RelativeLayout)findViewById(R.id.baslatlayout);
        RelativeLayout boardlayout = (RelativeLayout)findViewById(R.id.boardlayout);
        if (boardlayout.getVisibility()==View.VISIBLE){
            Toast.makeText(TekCiftActivity.this,getString(R.string.quittoast),Toast.LENGTH_LONG).show();
        }



    }




    public void balonakisi() {


        final LinearLayout balon1 =  findViewById(R.id.balonview1);
        final LinearLayout balon2 =  findViewById(R.id.balonview2);
        final LinearLayout balon3 =  findViewById(R.id.balonview3);
        final LinearLayout balon4 =  findViewById(R.id.balonview4);

        final Button sayi1Btn =  findViewById(R.id.sayi1Btn);final Button sayi2Btn =  findViewById(R.id.sayi2Btn);final Button sayi3Btn =  findViewById(R.id.sayi3Btn);
        final Button sayi4Btn =  findViewById(R.id.sayi4Btn);final Button sayi5Btn =  findViewById(R.id.sayi5Btn);final Button sayi6Btn =  findViewById(R.id.sayi6Btn);
        final Button sayi7Btn =  findViewById(R.id.sayi7Btn);final Button sayi8Btn =  findViewById(R.id.sayi8Btn);
        final Button sembolBtn1 =  findViewById(R.id.operatorBtn1);final Button sembolBtn2 = findViewById(R.id.operatorBtn2);
        final Button sembolBtn3 =  findViewById(R.id.operatorBtn3);final Button sembolBtn4 =  findViewById(R.id.operatorBtn4);


        final ArrayList<LinearLayout> balonlar = new ArrayList<>();
        balonlar.add(balon1); balonlar.add(balon2);  balonlar.add(balon3);  balonlar.add(balon4);



        ////////////OYUN//////////////////


                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                final int height = displayMetrics.heightPixels;
                final int width = displayMetrics.widthPixels;
                final float q = height+300;
                final float z = width;




                Log.i("ekran x",String.valueOf(width));
                Log.i("ekran y",String.valueOf(height));




        //Log.i("poziyon11",String.valueOf(balon1.getX()));
       // Log.i("poziyon12",String.valueOf(balon2.getX()));
       // Log.i("poziyon13",String.valueOf(balon3.getX()));
       // Log.i("poziyon14",String.valueOf(balon4.getX()));









                t = new Timer();

//Set the schedule function and rate
                t.scheduleAtFixedRate(new TimerTask() {
                                          @Override
                                          public void run() {
                                              //Called each time when 1000 milliseconds (1 second) (the period parameter)

                                              runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {



                                                      balon1.setClickable(true); balon2.setClickable(true); balon3.setClickable(true); balon4.setClickable(true);
                                                      balon1.setBackgroundResource(R.drawable.balon0);
                                                      balon2.setBackgroundResource(R.drawable.balon1);
                                                      balon3.setBackgroundResource(R.drawable.balon2);
                                                      balon4.setBackgroundResource(R.drawable.balon3);


                                                      sayi1Btn.setVisibility(View.VISIBLE);sayi2Btn.setVisibility(View.VISIBLE);sayi3Btn.setVisibility(View.VISIBLE);sayi4Btn.setVisibility(View.VISIBLE);sayi5Btn.setVisibility(View.VISIBLE);
                                                      sayi6Btn.setVisibility(View.VISIBLE);sayi7Btn.setVisibility(View.VISIBLE);sayi8Btn.setVisibility(View.VISIBLE);
                                                      sembolBtn1.setVisibility(View.VISIBLE);sembolBtn2.setVisibility(View.VISIBLE);sembolBtn3.setVisibility(View.VISIBLE);sembolBtn4.setVisibility(View.VISIBLE);



                                                      soruhazirlama();

                                                     // if (butonsec==0){Log.i("cevap1",String.valueOf(cevap1));Log.i("cesayi1",String.valueOf(sayi1));Log.i("ceoperator1",sembolBtn1.getText().toString());Log.i("cesayi2",String.valueOf(sayi2));}
                                                    //  if (butonsec==1){Log.i("cevap2",String.valueOf(cevap2));Log.i("cesayi3",String.valueOf(sayi3));Log.i("ceoperator2",sembolBtn2.getText().toString());Log.i("cesayi4",String.valueOf(sayi4));}
                                                    //  if (butonsec==2){Log.i("cevap3",String.valueOf(cevap3));Log.i("cesayi5",String.valueOf(sayi5));Log.i("ceoperator3",sembolBtn3.getText().toString());Log.i("cesayi6",String.valueOf(sayi6));}
                                                    //  if (butonsec==3){Log.i("cevap4",String.valueOf(cevap4));Log.i("cesayi7",String.valueOf(sayi7));Log.i("ceoperator4",sembolBtn4.getText().toString());Log.i("cesayi8",String.valueOf(sayi8));}



                                                      balon1.setX(z);balon2.setX(z);balon3.setX(z);balon4.setX(z);
                                                      balonlar.get(sira).setX(z);
                                                      balonlar.get(sira).setVisibility(View.VISIBLE);

                                                      final ObjectAnimator move = ObjectAnimator.ofFloat(balonlar.get(sira), "translationX",0,-z);
                                                      move.setDuration(6000);
                                                      move.start();


                                                      sira++;
                                                      if (sira>3){sira=0;}


                                                  }
                                              });
                                          }
                                      },
//Set how long before to start calling the TimerTask (in milliseconds)
                        0,
//Set the amount of time between each execution (in milliseconds)
                        3000);


    }




    public void skorboard (){

        final Button konularadonBtn = (Button)findViewById(R.id.testcikisBtn);
        final TextView toplambalonview = (TextView)findViewById(R.id.toplambalonTxtv);
        final TextView toplampuanview = (TextView)findViewById(R.id.toplampuanTxtv);
        final TextView dogrubalonview = (TextView)findViewById(R.id.dogrubalonTxtv);
        final TextView hatalibalonview = (TextView)findViewById(R.id.hatalibalonTxtv);
        final TextView patlatılanbalonview = (TextView)findViewById(R.id.patlatılanbalonTxtv);
        final TextView skorview = (TextView)findViewById(R.id.skorTxv);
        final LinearLayout sonuclarlayout = (LinearLayout)findViewById(R.id.sonuclarlayout);
        final RelativeLayout baslatlayout = (RelativeLayout)findViewById(R.id.baslatlayout);
        final RelativeLayout boardlayout = (RelativeLayout)findViewById(R.id.boardlayout);
        final Button gerisayimbtn =(Button)findViewById(R.id.gerisayimbtn);


//        gamemusic.stop(); gamemusic.release();
        gerisayimbtn.setText("süre bitti!");
        boardlayout.setVisibility(View.GONE);
        sonuclarlayout.setVisibility(View.VISIBLE);
        baslatlayout.setVisibility(View.GONE);
        ////////toplam balon animator
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, balonsayisi);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                toplambalonview.setText(getString(R.string.toplambalon) + String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator.setDuration(2000);
        animator.start();
        //////////
        ////////////patlatılan balon
        ValueAnimator patlatılanbalon = new ValueAnimator();
        patlatılanbalon.setObjectValues(0, (dogrubalon+hatalibalon));
        patlatılanbalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                patlatılanbalonview.setText(getString(R.string.patlatilanbalon) + String.valueOf(animation.getAnimatedValue()));
            }
        });
        patlatılanbalon.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        patlatılanbalon.setDuration(2000);
        patlatılanbalon.start();
        ///////////////////////
        ///////dogru balon animator
        ValueAnimator animatordogrubalon = new ValueAnimator();
        animatordogrubalon.setObjectValues(0,dogrubalon );
        animatordogrubalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                dogrubalonview.setText(getString(R.string.dogrubalon) + String.valueOf(animation.getAnimatedValue()));
            }
        });
        animatordogrubalon.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animatordogrubalon.setDuration(2000);
        animatordogrubalon.start();
        /////////////
        ///////////hatalı balon animator
        ValueAnimator animatorhatalibalon = new ValueAnimator();
        animatorhatalibalon.setObjectValues(0,hatalibalon );
        animatorhatalibalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                hatalibalonview.setText(getString(R.string.hatalibalon) + String.valueOf(animation.getAnimatedValue()));
            }
        });
        animatorhatalibalon.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animatorhatalibalon.setDuration(2000);
        animatorhatalibalon.start();
        //////////////////
        /////////////////toplampuan animator
        ValueAnimator toplampuananimator = new ValueAnimator();
        toplampuananimator.setObjectValues(0,scorecounter );
        toplampuananimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                toplampuanview.setText(getString(R.string.totalpoints) + String.valueOf(animation.getAnimatedValue()));
            }
        });
        toplampuananimator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        toplampuananimator.setDuration(2000);
        toplampuananimator.start();
        ///////////////////////
        //////////////media start
        final MediaPlayer countsound = MediaPlayer.create(TekCiftActivity.this,R.raw.count1);
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
                    konularadonBtn.setEnabled(true);

                }
            }
        };
        timer.start();
        //media finished
        //////////////////


        /////////////////skor bilgileri burada///////////////////////////////

        Integer inttoplambalon,intdogrubalon,intyanlisbalon,inttekciftleader,inttekciftkumulatif;
        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        inttoplambalon = sp.getInt("toplambalon",0);
        intdogrubalon= sp.getInt("dogrubalon",0);
        intyanlisbalon = sp.getInt("yanlisbalon",0);
        inttekciftkumulatif = sp.getInt("balonkumulatif",0);



        spEditor.putInt("toplambalon", (inttoplambalon+(dogrubalon+hatalibalon)));
        spEditor.putInt("dogrubalon",(intdogrubalon+dogrubalon));
        spEditor.putInt("yanlisbalon",(intyanlisbalon+hatalibalon));
        spEditor.putInt("balonleader",(scorecounter));
        spEditor.putInt("balonkumulatif",(scorecounter+inttekciftkumulatif));
        spEditor.commit();

        ////////////////////////SKOR BİLGİLERİ BİTTİ////////////////////////




        if (mGoogleApiClient.isConnected()){
            //Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
            Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_odd__even_stars),scorecounter);
            //startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,getString(R.string.leaderboard_smart_kids_kings)),0);
        }else {
            mGoogleApiClient.connect();
            //Toast.makeText(getApplicationContext(),"baglı degil",Toast.LENGTH_SHORT).show();
        }

        ////////////////////////SKOR BİLGİLERİ BİTTİ////////////////////////

    }

    public void paused (View view) {

        final Button pausebtn = (Button)findViewById(R.id.pauseBtn);
        final Button cikisBtn = (Button)findViewById(R.id.cikisBtn);
        final LinearLayout balon1 =  findViewById(R.id.balonview1);
        final LinearLayout balon2 =  findViewById(R.id.balonview2);
        final LinearLayout balon3 =  findViewById(R.id.balonview3);
        final LinearLayout balon4 =  findViewById(R.id.balonview4);

        RelativeLayout giflayout = findViewById(R.id.giflayout);

        balon1.setVisibility(View.GONE);balon2.setVisibility(View.GONE);balon3.setVisibility(View.GONE);balon4.setVisibility(View.GONE);


        if (!isPaused){
            gamemusic.pause();
            t.cancel();
            pausebtn.setBackgroundResource(R.drawable.play);
            giflayout.setVisibility(View.INVISIBLE);
            isPaused = true;
            cikisBtn.setEnabled(false);

        }
        else{
            gamemusic.start();
            balonakisi();
            pausebtn.setBackgroundResource(R.drawable.pause);
            giflayout.setVisibility(View.VISIBLE);
            isPaused = false;
            cikisBtn.setEnabled(true);

        }

    }

    public void soruhazirlama(){

        Log.i("sira",String.valueOf(butonsec));

        Button sayi1Btn =  findViewById(R.id.sayi1Btn);
        Button sayi2Btn =  findViewById(R.id.sayi2Btn);
        Button sayi3Btn =  findViewById(R.id.sayi3Btn);
        Button sayi4Btn =  findViewById(R.id.sayi4Btn);
        Button sayi5Btn =  findViewById(R.id.sayi5Btn);
        Button sayi6Btn =  findViewById(R.id.sayi6Btn);
        Button sayi7Btn =  findViewById(R.id.sayi7Btn);
        Button sayi8Btn =  findViewById(R.id.sayi8Btn);

        Button sembolBtn1 =  findViewById(R.id.operatorBtn1);
        Button sembolBtn2 =  findViewById(R.id.operatorBtn2);
        Button sembolBtn3 =  findViewById(R.id.operatorBtn3);
        Button sembolBtn4 =  findViewById(R.id.operatorBtn4);


        ArrayList<String> semboller = new ArrayList<>();
        semboller.add("+");       semboller.add("-");    semboller.add("x");            semboller.add("÷");


        ArrayList<Button> butonlar = new ArrayList<>();
        butonlar.add(sembolBtn1);butonlar.add(sembolBtn2);butonlar.add(sembolBtn3);butonlar.add(sembolBtn4);

        butonlar.get(butonsec).setText(semboller.get(butonsec));


        //Log.i("sembol",sembol.getText().toString());

        ///////////////////TOPLAMA////////////////////////////////

            Random random = new Random();
            sayi1 = random.nextInt(20 - 0 + 0) + 0;
            sayi2 = random.nextInt(20 - 0 + 0) + 0;

        //////////////////////TOPLAMA BİTTİ///////////////////////


        //////////////////////ÇIKARMA ///////////////////////

            Random random2 = new Random();
            int a = random2.nextInt(20 - 0 + 0) + 0;
            int b = random2.nextInt(20 - 0 + 0) + 0;
            if (a < b) {
                sayi3 = b;
                sayi4 = a;
            } else {
                sayi3 = a;
                sayi4 = b;



        }
        //////////////////////ÇIKARMA BİTTİ ///////////////////////

        //////////////////////ÇARPMA ///////////////////////

            Random random3 = new Random();
            sayi5 = random3.nextInt(10 - 0 + 0) + 0;
            sayi6 = random3.nextInt(10 - 0 + 0) + 0;


        //////////////////////ÇARPMA BİTTİ///////////////////////

        //////////////////////BÖLME ///////////////////////

                Random random4 = new Random();
                int c = random4.nextInt(11 - 1 + 1) + 1;
                int d = random4.nextInt(11 - 1 + 1) + 1;
                int e = c*d;

                sayi7=e;
                sayi8=c;




            /////////////////////////////////////////////////////////////////////////
        if (butonsec==0){sayi1Btn.setText(String.valueOf(sayi1));sayi2Btn.setText(String.valueOf(sayi2)); cevap1=sayi1+sayi2;}
        if (butonsec==1){sayi3Btn.setText(String.valueOf(sayi3));sayi4Btn.setText(String.valueOf(sayi4));cevap2=sayi3-sayi4;}
        if (butonsec==2){sayi5Btn.setText(String.valueOf(sayi5));sayi6Btn.setText(String.valueOf(sayi6));cevap3=sayi5*sayi6;}
        if (butonsec==3){sayi7Btn.setText(String.valueOf(sayi7));sayi8Btn.setText(String.valueOf(sayi8)); cevap4=d;}


        butonsec++;
        if (butonsec>3){butonsec=0;}

        Log.i("cevap1",String.valueOf(cevap1));
        Log.i("cevap2",String.valueOf(cevap2));
        Log.i("cevap3",String.valueOf(cevap3));
        Log.i("cevap4",String.valueOf(cevap4));




        }



    public void balonpatlat1 (View view) {

        Button sayi1Btn =  findViewById(R.id.sayi1Btn);Button sayi2Btn =  findViewById(R.id.sayi2Btn);
        Button sembolBtn1 =  findViewById(R.id.operatorBtn1);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);
        final TextView skorview = findViewById(R.id.skorTxv);
        final LinearLayout balon1 =  findViewById(R.id.balonview1);


        balon1.setClickable(false);
        sayi1Btn.setVisibility(View.GONE);sayi2Btn.setVisibility(View.GONE);sembolBtn1.setVisibility(View.GONE);


        if (cevap1%2==0) {
            dogrubalon++;
            balon1.setBackgroundResource(R.drawable.konfeti);


            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.GONE);
                }
            }, 350);


            skorview.setBackgroundResource(R.drawable.siklarclicked);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

           final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });


        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon1.setBackgroundResource(R.drawable.boombad);


            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.GONE);
                }
            }, 350);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            /////can kaldı mı?///////////
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
                    skorboard();

                }

            }



            //////////////////


        }


    }
    public  void balonpatlat2 (View view) {



        final TextView skorview = findViewById(R.id.skorTxv);
        final LinearLayout balon1 =  findViewById(R.id.balonview2);
        Button sayi3Btn =  findViewById(R.id.sayi3Btn);
        Button sayi4Btn =  findViewById(R.id.sayi4Btn);
        Button sembolBtn2 =  findViewById(R.id.operatorBtn2);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);

        Log.i("cevap",String.valueOf(cevap2));
        balon1.setClickable(false);
        sayi3Btn.setVisibility(View.GONE);sayi4Btn.setVisibility(View.GONE);sembolBtn2.setVisibility(View.GONE);


        if (cevap2%2==0) {
            dogrubalon++;
            balon1.setBackgroundResource(R.drawable.konfeti);


            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.GONE);
                }
            }, 350);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });


        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon1.setBackgroundResource(R.drawable.boombad);
            //bulut1.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.GONE);
                }
            }, 350);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            /////can kaldı mı?///////////
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
                    skorboard();

                }

            }



            //////////////////


        }






    }
    public  void balonpatlat3 (View view) {

        final TextView skorview = findViewById(R.id.skorTxv);
        final LinearLayout balon1 =  findViewById(R.id.balonview3);
        Button sayi5Btn =  findViewById(R.id.sayi5Btn);Button sayi6Btn =  findViewById(R.id.sayi6Btn);
        Button sembolBtn3 =  findViewById(R.id.operatorBtn3);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);


        Log.i("cevap",String.valueOf(cevap3));
        balon1.setClickable(false);
        sayi5Btn.setVisibility(View.GONE);sayi6Btn.setVisibility(View.GONE);sembolBtn3.setVisibility(View.GONE);


        if (cevap3%2==0) {
            dogrubalon++;
            balon1.setBackgroundResource(R.drawable.konfeti);


            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.GONE);
                }
            }, 350);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });


        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon1.setBackgroundResource(R.drawable.boombad);
            //bulut1.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.GONE);
                }
            }, 350);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            /////can kaldı mı?///////////
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
                    skorboard();

                }

            }



            //////////////////


        }


    }
    public  void balonpatlat4 (View view) {

        final TextView skorview = findViewById(R.id.skorTxv);
        final LinearLayout balon1 =  findViewById(R.id.balonview4);
        Button sayi7Btn =  findViewById(R.id.sayi7Btn);Button sayi8Btn =  findViewById(R.id.sayi8Btn);
        Button sembolBtn4 =  findViewById(R.id.operatorBtn4);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);


        Log.i("cevap",String.valueOf(cevap4));
        balon1.setClickable(false);
        sayi7Btn.setVisibility(View.GONE);sayi8Btn.setVisibility(View.GONE);sembolBtn4.setVisibility(View.GONE);


        if (cevap4%2==0) {
            dogrubalon++;
            balon1.setBackgroundResource(R.drawable.konfeti);


            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.GONE);
                }
            }, 350);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });


        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon1.setBackgroundResource(R.drawable.boombad);
            //bulut1.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.GONE);
                }
            }, 350);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            /////can kaldı mı?///////////
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
                    skorboard();

                }

            }



            //////////////////


        }


    }


    public void onPause() {
        super.onPause();

        RelativeLayout boardlayout = (RelativeLayout)findViewById(R.id.boardlayout);
        final Button pausebtn = (Button)findViewById(R.id.pauseBtn);
        RelativeLayout giflayout = findViewById(R.id.giflayout);
        final LinearLayout balon1 =  findViewById(R.id.balonview1);
        final LinearLayout balon2 =  findViewById(R.id.balonview2);
        final LinearLayout balon3 =  findViewById(R.id.balonview3);
        final LinearLayout balon4 =  findViewById(R.id.balonview4);
        balon1.setVisibility(View.GONE);balon2.setVisibility(View.GONE);balon3.setVisibility(View.GONE);balon4.setVisibility(View.GONE);


        isPaused=true;
        if (boardlayout.getVisibility()==View.VISIBLE) {
            gamemusic.pause();
        }
        pausebtn.setBackgroundResource(R.drawable.play);
        t.cancel();
        giflayout.setVisibility(View.INVISIBLE);


    }

    public void tamamla (View view) {
        final LinearLayout cikislayout = (LinearLayout)findViewById(R.id.cikislayout);
        final RelativeLayout giflayout = findViewById(R.id.giflayout);
        Button exitbutton = (Button)findViewById(R.id.exitBtn);
        Button geridon = (Button)findViewById(R.id.geridonBtn);
        final Button pausebtn = (Button)findViewById(R.id.pauseBtn);
        final LinearLayout balon1 =  findViewById(R.id.balonview1);
        final LinearLayout balon2 =  findViewById(R.id.balonview2);
        final LinearLayout balon3 =  findViewById(R.id.balonview3);
        final LinearLayout balon4 =  findViewById(R.id.balonview4);

        balon1.setVisibility(View.GONE);balon2.setVisibility(View.GONE);balon3.setVisibility(View.GONE);balon4.setVisibility(View.GONE);


        cikislayout.setVisibility(View.VISIBLE);
        giflayout.setVisibility(View.INVISIBLE);
        isPaused=true;
        gamemusic.pause();
        t.cancel();
        pausebtn.setEnabled(false);



        geridon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cikislayout.setVisibility(View.INVISIBLE);
                pausebtn.setEnabled(true);

                isPaused=false;

                gamemusic.start();
                balonakisi();
                giflayout.setVisibility(View.VISIBLE);
            }
        });




    }

    public void backmenu (View view) {




        skorboard();


    }

    public void konularadon (View view) {

        Intent myIntent = new Intent(TekCiftActivity.this, MathgamesActivity.class);
        myIntent.putExtra("tekciftscore", scorecounter);
        startActivity(myIntent);





        // Intent i = new Intent(TekCiftActivity.this,MathgamesActivity.class);startActivity(i);
        //  overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        //  startActivity(i);



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


}

