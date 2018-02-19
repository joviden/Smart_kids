package com.smartkids.akillicocuklar2;


import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;




public class ToplamaActivity extends Activity {

    private AdView mAdView;


    Integer sayi1,sayi2,dogrucevap;
    TextView sayi1View,sayi2View,soruView,skorTxv,dogruView,yanlisView,sorusayisiView,puanView,bossayisi;
    Button sikaBtn,sikbBtn,sikcBtn,sikdBtn,kolayBtn,ortaBtn,zorBtn,testcikisBtn;
    MediaPlayer optionclick;


    int questioncounter = 1;
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
        setContentView(R.layout.toplamalayout);
        Button kolayBtn = findViewById(R.id.kolayBtn); Button ortaBtn = findViewById(R.id.ortaBtn); Button zorBtn = findViewById(R.id.zorBtn);



        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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
                LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
                RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);

                zorluk.setVisibility(View.GONE);
                sorugovde.setVisibility(View.VISIBLE);
                rangedown=0;
                rangeup=10;
                soruhazırlama();
            }
        });

        ortaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
                RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
                rangedown=15;
                rangeup=50;
                zorluk.setVisibility(View.GONE);
                sorugovde.setVisibility(View.VISIBLE);
                soruhazırlama();

            }
        });

        zorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
                RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
                rangedown=50;
                rangeup=100;
                zorluk.setVisibility(View.GONE);
                sorugovde.setVisibility(View.VISIBLE);
                soruhazırlama();

            }
        });





    }



    public void soruhazırlama(){
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
        presiklar.remove(Integer.valueOf(dogrucevap));
        Collections.shuffle(presiklar);
        //---------------------------
        ArrayList<Integer>siklar=new ArrayList<>();
        siklar.add(presiklar.get(0));
        siklar.add(presiklar.get(1));
        siklar.add(presiklar.get(2));
        siklar.add(dogrucevap);
        Collections.shuffle(siklar);




        sikaBtn.setText(""+siklar.get(0));
        sikbBtn.setText(""+siklar.get(1));
        sikcBtn.setText(""+siklar.get(2));
        sikdBtn.setText(""+siklar.get(3));
    }


    public void clicksika (View view) {
        sikaBtn = (Button)findViewById(R.id.sikaBtn);
        sikbBtn = (Button)findViewById(R.id.sikbBtn);
        sikcBtn = (Button)findViewById(R.id.sikcBtn);
        sikdBtn = (Button)findViewById(R.id.sikdBtn);
        skorTxv = (TextView)findViewById(R.id.skorTxv);
        sayi1View=(TextView)findViewById(R.id.sayi1Txtv);
        sayi2View=(TextView)findViewById(R.id.sayi2Txtv);


        sikaBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);
        //sikbBtn.setVisibility(View.INVISIBLE);
        //sikcBtn.setVisibility(View.INVISIBLE);
        //sikdBtn.setVisibility(View.INVISIBLE);

        cevapcounter++;





        Integer cevap = Integer.parseInt(sikaBtn.getText().toString());
        Integer num1 = Integer.parseInt(sayi1View.getText().toString());
        Integer num2 = Integer.parseInt(sayi2View.getText().toString());
        Integer num3=num1+num2;

        if (cevap.equals(num3)) {

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

            scorecounter=scorecounter-50;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter+50, scorecounter);
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

            sikaBtn.setBackgroundResource(R.drawable.hatalisik);
            yanliscounter++;
            if (Integer.parseInt(sikbBtn.getText().toString())==dogrucevap) {sikbBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikcBtn.getText().toString())==dogrucevap) {sikcBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikdBtn.getText().toString())==dogrucevap) {sikdBtn.setBackgroundResource(R.drawable.siklarclicked);}




        }

    }

    public void clicksikb (View view) {
        sikaBtn = (Button)findViewById(R.id.sikaBtn);
        sikbBtn = (Button)findViewById(R.id.sikbBtn);
        sikcBtn = (Button)findViewById(R.id.sikcBtn);
        sikdBtn = (Button)findViewById(R.id.sikdBtn);
        skorTxv = (TextView)findViewById(R.id.skorTxv);
        sayi1View=(TextView)findViewById(R.id.sayi1Txtv);
        sayi2View=(TextView)findViewById(R.id.sayi2Txtv);


        sikbBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);

        cevapcounter++;



        Integer cevap = Integer.parseInt(sikbBtn.getText().toString());
        Integer num1 = Integer.parseInt(sayi1View.getText().toString());
        Integer num2 = Integer.parseInt(sayi2View.getText().toString());
        Integer num3=num1+num2;

        if (cevap.equals(num3)) {

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
            sikbBtn.setBackgroundResource(R.drawable.hatalisik);

            optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            scorecounter=scorecounter-50;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter+50, scorecounter);
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

            yanliscounter++;

            if (Integer.parseInt(sikaBtn.getText().toString())==dogrucevap) {sikaBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikcBtn.getText().toString())==dogrucevap) {sikcBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikdBtn.getText().toString())==dogrucevap) {sikdBtn.setBackgroundResource(R.drawable.siklarclicked);}




        }

    }

    public void clicksikc (View view) {
        sikaBtn = (Button)findViewById(R.id.sikaBtn);
        sikbBtn = (Button)findViewById(R.id.sikbBtn);
        sikcBtn = (Button)findViewById(R.id.sikcBtn);
        sikdBtn = (Button)findViewById(R.id.sikdBtn);
        skorTxv = (TextView)findViewById(R.id.skorTxv);
        sayi1View=(TextView)findViewById(R.id.sayi1Txtv);
        sayi2View=(TextView)findViewById(R.id.sayi2Txtv);


        sikcBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);

        cevapcounter++;



        Integer cevap = Integer.parseInt(sikcBtn.getText().toString());
        Integer num1 = Integer.parseInt(sayi1View.getText().toString());
        Integer num2 = Integer.parseInt(sayi2View.getText().toString());
        Integer num3=num1+num2;

        if (cevap.equals(num3)) {

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

            scorecounter=scorecounter-50;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter+50, scorecounter);
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
            sikcBtn.setBackgroundResource(R.drawable.hatalisik);
            yanliscounter++;

            if (Integer.parseInt(sikbBtn.getText().toString())==dogrucevap) {sikbBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikaBtn.getText().toString())==dogrucevap) {sikaBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikdBtn.getText().toString())==dogrucevap) {sikdBtn.setBackgroundResource(R.drawable.siklarclicked);}




        }

    }

    public void clicksikd (View view) {
        sikaBtn = (Button)findViewById(R.id.sikaBtn);
        sikbBtn = (Button)findViewById(R.id.sikbBtn);
        sikcBtn = (Button)findViewById(R.id.sikcBtn);
        sikdBtn = (Button)findViewById(R.id.sikdBtn);
        skorTxv = (TextView)findViewById(R.id.skorTxv);
        sayi1View=(TextView)findViewById(R.id.sayi1Txtv);
        sayi2View=(TextView)findViewById(R.id.sayi2Txtv);


        sikdBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);

        cevapcounter++;



        Integer cevap = Integer.parseInt(sikdBtn.getText().toString());
        Integer num1 = Integer.parseInt(sayi1View.getText().toString());
        Integer num2 = Integer.parseInt(sayi2View.getText().toString());
        Integer num3=num1+num2;

        if (cevap.equals(num3)) {

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

            scorecounter=scorecounter-50;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter+50, scorecounter);
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

            sikdBtn.setBackgroundResource(R.drawable.hatalisik);
            yanliscounter++;
            if (Integer.parseInt(sikbBtn.getText().toString())==dogrucevap) {sikbBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikcBtn.getText().toString())==dogrucevap) {sikcBtn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikaBtn.getText().toString())==dogrucevap) {sikaBtn.setBackgroundResource(R.drawable.siklarclicked);}


        }

    }

    public void nextquestion(View view) {
        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        sikaBtn = (Button)findViewById(R.id.sikaBtn);
        sikbBtn = (Button)findViewById(R.id.sikbBtn);
        sikcBtn = (Button)findViewById(R.id.sikcBtn);
        sikdBtn = (Button)findViewById(R.id.sikdBtn);

        questioncounter++;
        soruView.setText(getString(R.string.questionleft) +" "+ Integer.toString(questioncounter));


        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
            sorugovde.startAnimation(AnimationUtils.loadAnimation(ToplamaActivity.this, R.anim.fadein_out));


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


           soruhazırlama();



    }

    public void tamamla(View view) {

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
            //ara
            int x=yanliscounter+dogrucounter;
            boscounter=questioncounter-x;
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

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.count1);
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





        } else {
            Toast.makeText(ToplamaActivity.this,getString(R.string.toasthicsorucevaplamadın),Toast.LENGTH_SHORT).show();

            boscounter=questioncounter;


            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(questioncounter));
            bossayisi.setText(getString(R.string.unanswered) + String.valueOf(questioncounter));
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

      //  Intent myIntent = new Intent(ToplamaActivity.this, MainmenuActivity.class);
      //  myIntent.putExtra("toplamascore", scorecounter);
      //  startActivity(myIntent);
       // Log.i("toplamaskorilk",String.valueOf(scorecounter));




    }

    public void cikis(View view) {




        Intent i = new Intent(ToplamaActivity.this,TrainingActivity.class);startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(i);




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

        }
    }


}





