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
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import com.google.android.gms.ads.InterstitialAd;



public class BolmeActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    Integer sayi1,sayi2,dogruevap;
    TextView sayi1View,sayi2View,soruView,skorTxv,dogruView,yanlisView,sorusayisiView,puanView,bossayisi;
    Button sikaBtn,sikbBtn,sikcBtn,sikdBtn,testcikisBtn,tamamlaBtn,nextquestionBtn;
    MediaPlayer cevapmusic,countmusic;
    Boolean useranswered;



    int questioncounter = 0;

    int range1up;
    int range1down;
    int range2up;
    int range2down;

    int cevapcounter;
    int scorecounter=0;
    int dogrucounter=0;
    int yanliscounter=0;
    int boscounter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.dortislemlayout);

        RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
        LinearLayout sonuclar = (LinearLayout) findViewById(R.id.sonuclarlayout);
        LinearLayout zorluk = (LinearLayout) findViewById(R.id.zorluklayout);
        Button kolayBtn = findViewById(R.id.kolayBtn); Button ortaBtn = findViewById(R.id.ortaBtn); Button zorBtn = findViewById(R.id.zorBtn);

        sorugovde.setVisibility(View.GONE);
        sonuclar.setVisibility(View.GONE);
        zorluk.setVisibility(View.VISIBLE);
/////////////////////reklamlar////////////////////////////

        Button soru = findViewById(R.id.soruTxtv);
        Button sembol = findViewById(R.id.sembolBtn);
        soru.setText(getString(R.string.questionfordivision));
        sembol.setText(" ÷ ");


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


        soruView = (TextView) findViewById(R.id.sorunumarasıTxtv);
        soruView.setText(getString(R.string.questionleft) + " 1");


        sorugovde.setVisibility(View.GONE);
        zorluk.setVisibility(View.VISIBLE);

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
                range1up=10;
                range1down=1;
                range2up=5;
                range2down=1;
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
                range1up=15;
                range1down=9;

                range2up=10;
                range2down=4;

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
                range1up=20;
                range1down=12;

                range2up=15;
                range2down=8;

                zorluk.setVisibility(View.GONE);
                sorugovde.setVisibility(View.VISIBLE);
                soruhazirlama();

            }
        });



    }





    public void cevapla (View view) {

        useranswered =true;

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

        Log.i(" dogru cevap" , String.valueOf(dogruevap));
        Log.i("cevap user" , view.getTag().toString());

        if (view.getTag().toString().equals(Integer.toString(dogruevap))) {

            questioncounter++;

           try {
            cevapmusic = MediaPlayer.create(this,R.raw.rightanswer);
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
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soruhazirlama();
                }
            }, 1100);



        } else {

            try {

            cevapmusic = MediaPlayer.create(this,R.raw.wronganswer);
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

            scorecounter=scorecounter-100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter+100, scorecounter);
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
            if (Integer.parseInt(sikaBtn.getText().toString())==dogruevap) {sikaBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikbBtn.getText().toString())==dogruevap) {sikbBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikcBtn.getText().toString())==dogruevap) {sikcBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikdBtn.getText().toString())==dogruevap) {sikdBtn.setBackgroundResource(R.drawable.siklarclicked);}




        }


    }

    public void nextquestion(View view) {
        sikaBtn = (Button) findViewById(R.id.sikaBtn);
        sikbBtn = (Button) findViewById(R.id.sikbBtn);
        sikcBtn = (Button) findViewById(R.id.sikcBtn);
        sikdBtn = (Button) findViewById(R.id.sikdBtn);

        questioncounter++;


            if (!useranswered) {boscounter++;}

        //Log.i("bos",String.valueOf(boscounter));





        soruhazirlama();


    }

    public void tamamla(View view) {

        /////////////////////reklam///////////////////////////////

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        /////////////////////reklam///////////////////////////////
        RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);
        dogruView=(Button)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(Button)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(Button)findViewById(R.id.sorusayisiTxtv);
        bossayisi=(Button)findViewById(R.id.bossayisiTxtv);
        puanView = (Button)findViewById(R.id.toplampuanTxtv);
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
            animatorsoru.setObjectValues(0, questioncounter);
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
            //ara
           // int x=yanliscounter+dogrucounter;
           // boscounter=questioncounter-x;
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
            Toast.makeText(BolmeActivity.this,getString(R.string.toasthicsorucevaplamadın),Toast.LENGTH_SHORT).show();
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

        Integer intbolmesoru,intbolmedogru,intbolmeyanlis,intbolmebos,intbolmelamaleader,intbolmekumulatif;
        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        intbolmesoru = sp.getInt("bolmesoru",0);
        intbolmedogru= sp.getInt("bolmedogru",0);
        intbolmeyanlis = sp.getInt("bolmeyanlis",0);
        intbolmebos = sp.getInt("bolmebos",0);
        intbolmekumulatif = sp.getInt("bolmekumulatif",0);


        spEditor.putInt("bolmesoru", (questioncounter+intbolmesoru));
        spEditor.putInt("bolmedogru",(dogrucounter+intbolmedogru));
        spEditor.putInt("bolmeyanlis",(yanliscounter+intbolmeyanlis));
        spEditor.putInt("bolmebos",(boscounter+intbolmebos));
        spEditor.putInt("bolmeleader",(scorecounter));
        spEditor.putInt("bolmekumulatif",(scorecounter+intbolmekumulatif));
        spEditor.commit();

        ////////////////////////SKOR BİLGİLERİ BİTTİ////////////////////////



    }

    public void cikis(View view) {





        Intent i = new Intent(BolmeActivity.this,TrainingActivity.class);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(i);
        BolmeActivity.this.finish();

    }

    public void soruhazirlama () {

        useranswered =false;

        /////////////////////////reklamlar//////////////////////////////////////////////////////////

        if (questioncounter==5 || questioncounter==10 || questioncounter==15 || questioncounter==20 ||questioncounter==25) {  if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } }

        /////////////////////////reklamlar//////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////

        sikaBtn = (Button) findViewById(R.id.sikaBtn);
        sikbBtn = (Button) findViewById(R.id.sikbBtn);
        sikcBtn = (Button) findViewById(R.id.sikcBtn);
        sikdBtn = (Button) findViewById(R.id.sikdBtn);
        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        Button cevapBtn = findViewById(R.id.nextquestionBtn);


        sikaBtn.setBackgroundResource(R.drawable.siklar);
        sikbBtn.setBackgroundResource(R.drawable.siklar);
        sikcBtn.setBackgroundResource(R.drawable.siklar);
        sikdBtn.setBackgroundResource(R.drawable.siklar);
        sikaBtn.setEnabled(true);
        sikbBtn.setEnabled(true);
        sikcBtn.setEnabled(true);
        sikdBtn.setEnabled(true);
        cevapBtn.setEnabled(true);

        sikaBtn.setVisibility(View.VISIBLE);
        sikbBtn.setVisibility(View.VISIBLE);
        sikcBtn.setVisibility(View.VISIBLE);
        sikdBtn.setVisibility(View.VISIBLE);

        /////////////////////////////////
        try {

        final MediaPlayer gecis = MediaPlayer.create(this,R.raw.sorugecisi);
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
        animationlayout.startAnimation(AnimationUtils.loadAnimation(BolmeActivity.this, R.anim.slide_in));

        ////////////////////////////////

        Random random = new Random();
        dogruevap = random.nextInt(range1up -range1down) + range1down;
        sayi2 = random.nextInt(range2up - range2down) + range2down;
        sayi1 = sayi2 * dogruevap;


        sayi1View = (TextView) findViewById(R.id.sayi1Txtv);
        sayi2View = (TextView) findViewById(R.id.sayi2Txtv);


        sayi1View.setText(String.valueOf(sayi1));

        sayi2View.setText(String.valueOf(sayi2));

        //şiklar bölümü//




        Integer sayi4 = dogruevap + 6;
        Integer sayi5 = dogruevap - 6;

        //----------------------------
        ArrayList<Integer> presiklar = new ArrayList<Integer>();
        for (int i = sayi5; i <= sayi4; i = i + 1) {
            if (i >= 0) {
                presiklar.add(i);
            }
        }
        presiklar.remove(Integer.valueOf(dogruevap));
        Collections.shuffle(presiklar);
        //---------------------------
        ArrayList<Integer> siklar = new ArrayList<>();
        siklar.add(presiklar.get(0));
        siklar.add(presiklar.get(1));
        siklar.add(presiklar.get(2));
        siklar.add(dogruevap);
        Collections.shuffle(siklar);


        sikaBtn.setText(""+siklar.get(0)); sikaBtn.setTag(siklar.get(0));
        sikbBtn.setText(""+siklar.get(1)); sikbBtn.setTag(siklar.get(1));
        sikcBtn.setText(""+siklar.get(2)); sikcBtn.setTag(siklar.get(2));
        sikdBtn.setText(""+siklar.get(3)); sikdBtn.setTag(siklar.get(3));

    }

    @Override
    public void onBackPressed() {

        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
        if (sorugovde.getVisibility()==View.VISIBLE){
            Toast.makeText(BolmeActivity.this,getString(R.string.quittoast),Toast.LENGTH_SHORT).show();
        }
        if (zorluk.getVisibility()==View.VISIBLE){
            Intent myIntent = new Intent(BolmeActivity.this, TrainingActivity.class);
            startActivity(myIntent);
            this.finish();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();


    }





}







