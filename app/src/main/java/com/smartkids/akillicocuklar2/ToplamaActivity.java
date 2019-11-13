package com.smartkids.akillicocuklar2;


import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.google.android.gms.ads.InterstitialAd;




public class ToplamaActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    Integer sayi1,sayi2,dogrucevap;
    TextView sayi1View,sayi2View,soruView,skorTxv,dogruView,yanlisView,sorusayisiView,puanView,bossayisi;
    Button sikaBtn,sikbBtn,sikcBtn,sikdBtn,testcikisBtn,tamamlaBtn,nextquestionBtn;
    MediaPlayer cevapmusic,countmusic;
    Boolean useranswered;


    int questioncounter = 0;
    int rangedown;
    int rangeup;
    int cevapcounter;
    int scorecounter=0;
    int dogrucounter=0;
    int yanliscounter=0;
    int boscounter;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dortislemlayout);


        Button kolayBtn = findViewById(R.id.kolayBtn); Button ortaBtn = findViewById(R.id.ortaBtn); Button zorBtn = findViewById(R.id.zorBtn);


/////////////////////reklamlar////////////////////////////


        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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



        ///////////////////////reklamlar/////////////////////////////

        Button soru = findViewById(R.id.soruTxtv);
        Button sembol = findViewById(R.id.sembolBtn);
        soru.setText(getString(R.string.questionforaddition));
        sembol.setText(" + ");


        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);

        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);

        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        soruView.setText(getString(R.string.questionleft) +" 1");

        sorugovde.setVisibility(View.GONE);
        zorluk.setVisibility(View.VISIBLE);
        sonuclar.setVisibility(View.GONE);


        kolayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
                RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);

                zorluk.setVisibility(View.GONE);
                sorugovde.setVisibility(View.VISIBLE);
                rangedown=0;
                rangeup=10;
                soruhazirlama();
            }
        });

        ortaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
                RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
                rangedown=15;
                rangeup=50;
                zorluk.setVisibility(View.GONE);
                sorugovde.setVisibility(View.VISIBLE);
                soruhazirlama();

            }
        });

        zorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
                RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
                rangedown=50;
                rangeup=100;
                zorluk.setVisibility(View.GONE);
                sorugovde.setVisibility(View.VISIBLE);
                soruhazirlama();

            }
        });





    }



    public void soruhazirlama(){
        useranswered=false;

        /////////////////////////reklamlar//////////////////////////////////////////////////////////

         if (questioncounter==10 || questioncounter==20 || questioncounter==30 || questioncounter==40 ||questioncounter==50) {  if (mInterstitialAd.isLoaded()) {
             mInterstitialAd.show();
         } }

        /////////////////////////reklamlar//////////////////////////////////////////////////////////


        sikaBtn = (Button) findViewById(R.id.sikaBtn);
        sikbBtn = (Button) findViewById(R.id.sikbBtn);
        sikcBtn = (Button) findViewById(R.id.sikcBtn);
        sikdBtn = (Button) findViewById(R.id.sikdBtn);
        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);



        sikaBtn.setBackgroundResource(R.drawable.siklar);
        sikbBtn.setBackgroundResource(R.drawable.siklar);
        sikcBtn.setBackgroundResource(R.drawable.siklar);
        sikdBtn.setBackgroundResource(R.drawable.siklar);
        sikaBtn.setEnabled(true);
        sikbBtn.setEnabled(true);
        sikcBtn.setEnabled(true);
        sikdBtn.setEnabled(true);


        sikaBtn.setVisibility(View.VISIBLE);
        sikbBtn.setVisibility(View.VISIBLE);
        sikcBtn.setVisibility(View.VISIBLE);
        sikdBtn.setVisibility(View.VISIBLE);

        /////////////////////////////////

        try {
            final MediaPlayer gecis = MediaPlayer.create(this, R.raw.sorugecisi);
            gecis.start();
            gecis.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    gecis.release();
                }
            });
        }catch (Exception e) {e.printStackTrace();}

        ////////////////////////////////


        soruView.setText(getString(R.string.questionleft) +" "+ Integer.toString(questioncounter+1));

        RelativeLayout animationlayout = (RelativeLayout) findViewById(R.id.animationlayout);
        animationlayout.startAnimation(AnimationUtils.loadAnimation(ToplamaActivity.this, R.anim.slide_in));

        ////////////////////////////////
        Random random = new Random();
        sayi1 = random.nextInt(rangeup-rangedown+1)+rangedown;
        sayi2 = random.nextInt(rangeup-rangedown+1)+rangedown;

        sayi1View = (TextView)findViewById(R.id.sayi1Txtv);
        sayi2View = (TextView)findViewById(R.id.sayi2Txtv);

        String stringsayi1 = String.valueOf(sayi1);
        sayi1View.setText(stringsayi1);


        String stringsayi2 = String.valueOf(sayi2);
        sayi2View.setText(stringsayi2);

        //şiklar bölümü//

        sikaBtn = (Button)findViewById(R.id.sikaBtn);
        sikbBtn = (Button)findViewById(R.id.sikbBtn);
        sikcBtn = (Button)findViewById(R.id.sikcBtn);
        sikdBtn = (Button)findViewById(R.id.sikdBtn);

        dogrucevap = sayi1+sayi2;
        Integer sayi4 = dogrucevap+4;
        Integer sayi5 = dogrucevap-4;

        //----------------------------
        ArrayList<Integer> presiklar = new ArrayList<Integer>();
        for (int i=sayi5;i<=sayi4;i=i+1) {
            if (i>=0) {
                presiklar.add(i);
            }
        }
        presiklar.add(dogrucevap + 10);
        presiklar.remove(Integer.valueOf(dogrucevap));
        Collections.shuffle(presiklar);
        //---------------------------
        ArrayList<Integer>siklar=new ArrayList<>();
        siklar.add(presiklar.get(0));
        siklar.add(presiklar.get(1));
        siklar.add(presiklar.get(2));
        siklar.add(dogrucevap);
        Collections.shuffle(siklar);




        sikaBtn.setText(""+siklar.get(0)); sikaBtn.setTag(siklar.get(0));
        sikbBtn.setText(""+siklar.get(1)); sikbBtn.setTag(siklar.get(1));
        sikcBtn.setText(""+siklar.get(2)); sikcBtn.setTag(siklar.get(2));
        sikdBtn.setText(""+siklar.get(3)); sikdBtn.setTag(siklar.get(3));
    }




    public void cevapla (View view) {

        useranswered=true;

        sikaBtn = (Button)findViewById(R.id.sikaBtn);
        sikbBtn = (Button)findViewById(R.id.sikbBtn);
        sikcBtn = (Button)findViewById(R.id.sikcBtn);
        sikdBtn = (Button)findViewById(R.id.sikdBtn);
        skorTxv = (TextView)findViewById(R.id.skorTxv);
        sayi1View=(TextView)findViewById(R.id.sayi1Txtv);
        sayi2View=(TextView)findViewById(R.id.sayi2Txtv);
        tamamlaBtn=(Button)findViewById(R.id.tamamlaBtn);
        nextquestionBtn = findViewById(R.id.nextquestionBtn);



        view.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);
        tamamlaBtn.setEnabled(false);
        nextquestionBtn.setEnabled(false);


        cevapcounter++;

        Log.i(" dogru cevap" , String.valueOf(dogrucevap));
        Log.i("cevap user" , view.getTag().toString());

       if (view.getTag().toString().equals(Integer.toString(dogrucevap))) {

           questioncounter++;

           try {


               cevapmusic = MediaPlayer.create(this, R.raw.rightanswer);
               cevapmusic.start();
               cevapmusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mediaPlayer) {

                       cevapmusic.release();

                       tamamlaBtn.setEnabled(true);
                       nextquestionBtn.setEnabled(true);
                   }
               });
           }catch (Exception e) {e.printStackTrace();}

           dogrucounter++;

           scorecounter=scorecounter+30;
           ValueAnimator animator = new ValueAnimator();
           animator.setObjectValues(scorecounter-30, scorecounter);
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
           Handler handler1 = new Handler();
           handler1.postDelayed(new Runnable() {
               @Override
               public void run() {
                   soruhazirlama();
               }
           }, 1100);



       } else {

           try {


               cevapmusic = MediaPlayer.create(this, R.raw.wronganswer);
               cevapmusic.start();
               cevapmusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mediaPlayer) {

                       cevapmusic.release();

                       tamamlaBtn.setEnabled(true);
                       nextquestionBtn.setEnabled(true);
                   }
               });
           }catch (Exception e) {e.printStackTrace();}

           scorecounter=scorecounter-30;
           ValueAnimator animator = new ValueAnimator();
           animator.setObjectValues(scorecounter+30, scorecounter);
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

           view.setBackgroundResource(R.drawable.hatalisik);
           yanliscounter++;
           if (Integer.parseInt(sikaBtn.getText().toString())==dogrucevap) {sikaBtn.setBackgroundResource(R.drawable.siklarclicked);}
           if (Integer.parseInt(sikbBtn.getText().toString())==dogrucevap) {sikbBtn.setBackgroundResource(R.drawable.siklarclicked);}
           if (Integer.parseInt(sikcBtn.getText().toString())==dogrucevap) {sikcBtn.setBackgroundResource(R.drawable.siklarclicked);}
           if (Integer.parseInt(sikdBtn.getText().toString())==dogrucevap) {sikdBtn.setBackgroundResource(R.drawable.siklarclicked);}




       }


    }

    public void nextquestion(View view) {
        questioncounter++;



        sikaBtn = (Button) findViewById(R.id.sikaBtn);
        sikbBtn = (Button) findViewById(R.id.sikbBtn);
        sikcBtn = (Button) findViewById(R.id.sikcBtn);
        sikdBtn = (Button) findViewById(R.id.sikdBtn);
        tamamlaBtn=(Button)findViewById(R.id.tamamlaBtn);




        if (!useranswered) {boscounter++;}

           soruhazirlama();


    }

    public void tamamla(View view) {

        /////////////////////reklam///////////////////////////////

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        /////////////////////reklam///////////////////////////////


        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);
        dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(TextView)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(TextView)findViewById(R.id.sorusayisiTxtv);
        bossayisi=(TextView)findViewById(R.id.bossayisiTxtv);
        puanView = (TextView)findViewById(R.id.toplampuanTxtv);
        testcikisBtn = (Button)findViewById(R.id.testcikisBtn);


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
                    yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(animatoryanlis.getAnimatedValue()));
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
                    dogruView.setText(getString(R.string.rightanswers) + String.valueOf(animatordogru.getAnimatedValue()));
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
            animatorsoru.setObjectValues(0, questioncounter);
            animatorsoru.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(animatorsoru.getAnimatedValue()));
                }
            });
            animatorsoru.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animatorsoru.setDuration(2000);
            animatorsoru.start();


            final ValueAnimator animatorbossoru = new ValueAnimator();
            animatorbossoru.setObjectValues(0, boscounter);
            animatorbossoru.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    bossayisi.setText(getString(R.string.unanswered) + String.valueOf(animatorbossoru.getAnimatedValue()));
                }
            });
            animatorbossoru.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animatorbossoru.setDuration(2000);
            animatorbossoru.start();

            try{

            countmusic = MediaPlayer.create(this,R.raw.count1);
            countmusic.start();
            countmusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                        countmusic.release();

                    testcikisBtn.setEnabled(true);

                }
            });
            }catch (Exception e) {e.printStackTrace();}







        } else {
            Toast.makeText(ToplamaActivity.this,getString(R.string.toasthicsorucevaplamadın),Toast.LENGTH_SHORT).show();

            boscounter=0;
            questioncounter=0;


            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(0));
            bossayisi.setText(getString(R.string.unanswered) + String.valueOf(0));
            testcikisBtn.setEnabled(true);
        }

        /////////////////skor bilgileri burada///////////////////////////////

        Integer inttopsoru,inttopdogru,inttopyanlis,inttopbos,inttopscore;
        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        inttopsoru = sp.getInt("toplamasoru",0);
        inttopdogru= sp.getInt("toplamadogru",0);
        inttopyanlis = sp.getInt("toplamayanlis",0);
        inttopbos = sp.getInt("toplamabos",0);
        inttopscore = sp.getInt("toplamakumulatif",0);


        spEditor.putInt("toplamasoru", (questioncounter+inttopsoru));
        spEditor.putInt("toplamadogru",(dogrucounter+inttopdogru));
        spEditor.putInt("toplamayanlis",(yanliscounter+inttopyanlis));
        spEditor.putInt("toplamabos",(boscounter+inttopbos));
        spEditor.putInt("toplamaleader",(scorecounter));
        spEditor.putInt("toplamakumulatif",(scorecounter+inttopscore));
        spEditor.commit();

        ////////////////////////SKOR BİLGİLERİ BİTTİ////////////////////////






    }

    public void cikis(View view) {



        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        Intent i = new Intent(ToplamaActivity.this,TrainingActivity.class);startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(i);

        ToplamaActivity.this.finish();




    }

    @Override
    public void onBackPressed() {

        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        if (sorugovde.getVisibility()==View.VISIBLE){
            Toast.makeText(ToplamaActivity.this,getString(R.string.quittoast),Toast.LENGTH_SHORT).show();
        }
        if (zorluk.getVisibility()==View.VISIBLE){
            Intent myIntent = new Intent(ToplamaActivity.this, TrainingActivity.class);
            startActivity(myIntent);
            this.finish();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       //if (cevapsound != null) {
        //    cevapsound.release();
        //    cevapsound = null;
       // }
        System.gc();
    }




}





