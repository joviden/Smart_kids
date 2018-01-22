package com.smartkids;


import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class BolmeActivity extends Activity {

    private AdView mAdView;

    Integer sayi1,sayi2,sayi3,sayi11,sayi22;
    TextView sayi1View,sayi2View,soruView,skorTxv,dogruView,yanlisView,sorusayisiView,puanView,bossayisi;
    Button sikaBtn,sikbBtn,sikcBtn,sikdBtn,kolayBtn,ortaBtn,zorBtn,testcikisBtn;
    MediaPlayer optionclick;
    Float sayi3f,tip;



    int questioncounter = 1;
    int range1up=100;
    int range1down=1;
    int range2up=15;
    int range2down=1;
    int cevapcounter;
    int scorecounter=0;
    int dogrucounter=0;
    int yanliscounter=0;
    int boscounter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.bolmelayout);

        LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
        LinearLayout nextbuttons = (LinearLayout)findViewById(R.id.nextbutonslayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);
        LinearLayout cikis = (LinearLayout)findViewById(R.id.cikislayout);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);

        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        soruView.setText(getString(R.string.questionleft) +" 1");




        sorugovde.setVisibility(View.GONE);
        nextbuttons.setVisibility(View.GONE);
        zorluk.setVisibility(View.VISIBLE);



        //soru bölümü//

        do {
            Random random = new Random();
            sayi11 = random.nextInt(range1up-range1down)+range1down;
            sayi22 = random.nextInt(range2up-range2down)+range2down;
            if (sayi11<sayi22) {
                sayi1 = sayi22;
                sayi2 = sayi11;
            } else {
                sayi1=sayi11;
                sayi2=sayi22;
            }

            sayi3 = sayi1/sayi2;
            sayi3f = (float)sayi1/sayi2;
            tip = sayi3f-sayi3;



        } while (tip!=0.0);



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


            Integer sayi4 = sayi3+6;
            Integer sayi5 = sayi3-6;

            //----------------------------
            ArrayList<Integer> presiklar = new ArrayList<Integer>();
            for (int i=sayi5;i<=sayi4;i=i+1) {
                if (i>=0) {
                    presiklar.add(i);
                }
            }
            presiklar.remove(Integer.valueOf(sayi3));
            Collections.shuffle(presiklar);
            //---------------------------
            ArrayList<Integer>siklar=new ArrayList<>();
            siklar.add(presiklar.get(0));
            siklar.add(presiklar.get(1));
            siklar.add(presiklar.get(2));
            siklar.add(sayi3);
            Collections.shuffle(siklar);



            sikaBtn.setText(""+siklar.get(0));
            sikbBtn.setText(""+siklar.get(1));
            sikcBtn.setText(""+siklar.get(2));
            sikdBtn.setText(""+siklar.get(3));


    }

    public void kolay (View view) {
        kolayBtn=(Button)findViewById(R.id.kolayBtn);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
        LinearLayout nextbuttons = (LinearLayout)findViewById(R.id.nextbutonslayout);

        zorluk.setVisibility(View.GONE);
        sorugovde.setVisibility(View.VISIBLE);
        nextbuttons.setVisibility(View.VISIBLE);
    }

    public void orta (View view){
        zorBtn = (Button)findViewById(R.id.zorBtn);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
        LinearLayout nextbuttons = (LinearLayout)findViewById(R.id.nextbutonslayout);
        range1up=1000;
        range1down=100;
        range2up=50;
        range2down=15;
        zorluk.setVisibility(View.GONE);
        sorugovde.setVisibility(View.VISIBLE);
        nextbuttons.setVisibility(View.VISIBLE);
    }

    public void zor (View view) {
        ortaBtn= (Button)findViewById(R.id.ortaBtn);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
        LinearLayout nextbuttons = (LinearLayout)findViewById(R.id.nextbutonslayout);
        range1up=5000;
        range1down=1000;
        range2up=100;
        range2down=50;
        zorluk.setVisibility(View.GONE);
        sorugovde.setVisibility(View.VISIBLE);
        nextbuttons.setVisibility(View.VISIBLE);
    }



    public void clicksika (View view) {
        sikaBtn = (Button)findViewById(R.id.sikaBtn);
        sikbBtn = (Button)findViewById(R.id.sikbBtn);
        sikcBtn = (Button)findViewById(R.id.sikcBtn);
        sikdBtn = (Button)findViewById(R.id.sikdBtn);
        skorTxv = (TextView)findViewById(R.id.skorTxv);
        sayi1View=(TextView)findViewById(R.id.sayi1Txtv);
        sayi2View=(TextView)findViewById(R.id.sayi2Txtv);
        dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(TextView)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(TextView)findViewById(R.id.sorusayisiTxtv);

        sikaBtn.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);


        cevapcounter++;
        sorusayisiView.setText("Cevaplanan Soru: " + Integer.toString(cevapcounter));




        Integer cevap = Integer.parseInt(sikaBtn.getText().toString());
        Integer num1 = Integer.parseInt(sayi1View.getText().toString());
        Integer num2 = Integer.parseInt(sayi2View.getText().toString());
        Integer num3=num1/num2;


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

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BolmeActivity.this, WinAnimegifsActivity.class);
                    startActivity(i);
                }
            },1000);

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
            yanlisView.setText("Yanlis: " + Integer.toString(yanliscounter));

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BolmeActivity.this, LooseAnimeActivity.class);
                    startActivity(i);
                }
            },1000);

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
        Integer num3=num1/num2;



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

            //Toast.makeText(toplamaActivity.this,"bravo",Toast.LENGTH_SHORT).show();
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

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BolmeActivity.this, WinAnimegifsActivity.class);
                    startActivity(i);
                }
            },1000);
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

            yanliscounter++;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BolmeActivity.this, LooseAnimeActivity.class);
                    startActivity(i);
                }
            },1000);

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
        Integer num3=num1/num2;




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

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BolmeActivity.this, WinAnimegifsActivity.class);
                    startActivity(i);
                }
            },1000);
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

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BolmeActivity.this, LooseAnimeActivity.class);
                    startActivity(i);
                }
            },1000);

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
        Integer num3=num1/num2;




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

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BolmeActivity.this, WinAnimegifsActivity.class);
                    startActivity(i);
                }
            },1000);
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


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BolmeActivity.this, LooseAnimeActivity.class);
                    startActivity(i);
                }
            },1000);

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


            LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
            sorugovde.startAnimation(AnimationUtils.loadAnimation(BolmeActivity.this, R.anim.fadein_out));


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


            do {
                Random random = new Random();
                sayi11 = random.nextInt(range1up-range1down)+range1down;
                sayi22 = random.nextInt(range2up-range2down)+range2down;
                if (sayi11<sayi22) {
                    sayi1 = sayi22;
                    sayi2 = sayi11;
                } else {
                    sayi1=sayi11;
                    sayi2=sayi22;
                }

                sayi3 = sayi1/sayi2;
                sayi3f = (float)sayi1/sayi2;
                tip = sayi3f-sayi3;



            } while (tip!=0.0);

            sayi1View = (TextView)findViewById(R.id.sayi1Txtv);
            sayi2View = (TextView)findViewById(R.id.sayi2Txtv);

            String stringsayi1 = String.valueOf(sayi1);
            sayi1View.setText(stringsayi1);


            String stringsayi2 = String.valueOf(sayi2);
            sayi2View.setText(stringsayi2);


            Integer sayi4 = sayi3+6;
            Integer sayi5 = sayi3-6;

//
            ArrayList<Integer> presiklar = new ArrayList<Integer>();
            for (int i=sayi5;i<=sayi4;i=i+1) {
                if (i>=0) {
                    presiklar.add(i);
                }
            }
            presiklar.remove(Integer.valueOf(sayi3));
            Collections.shuffle(presiklar);
            //---------------------------
            ArrayList<Integer>siklar=new ArrayList<>();
            siklar.add(presiklar.get(0));
            siklar.add(presiklar.get(1));
            siklar.add(presiklar.get(2));
            siklar.add(sayi3);
            Collections.shuffle(siklar);




            sikaBtn.setText(""+siklar.get(0));
            sikbBtn.setText(""+siklar.get(1));
            sikcBtn.setText(""+siklar.get(2));
            sikdBtn.setText(""+siklar.get(3));


            //




    }

    public void tamamla(View view) {
        LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
        LinearLayout nextbuttons = (LinearLayout)findViewById(R.id.nextbutonslayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);
        LinearLayout cikis = (LinearLayout)findViewById(R.id.cikislayout);
        dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(TextView)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(TextView)findViewById(R.id.sorusayisiTxtv);
        bossayisi=(TextView)findViewById(R.id.bossayisiTxtv);
        puanView = (TextView)findViewById(R.id.toplampuanTxtv);
        testcikisBtn = (Button)findViewById(R.id.testcikisBtn);


        testcikisBtn.setEnabled(false);
        sorugovde.setVisibility(View.GONE);
        nextbuttons.setVisibility(View.GONE);
        sonuclar.setVisibility(View.VISIBLE);
        cikis.setVisibility(View.VISIBLE);

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
            animator.setDuration(3000);
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
            animatoryanlis.setDuration(3000);
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
            animatordogru.setDuration(3000);
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
            animatorsoru.setDuration(3000);
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
            animatorbossoru.setDuration(3000);
            animatorbossoru.start();

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.count1);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(3000, 3000) {

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
            Toast.makeText(BolmeActivity.this,getString(R.string.toasthicsorucevaplamadın),Toast.LENGTH_SHORT).show();
            boscounter=questioncounter;


            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(questioncounter));
            bossayisi.setText(getString(R.string.unanswered) + String.valueOf(questioncounter));
            testcikisBtn.setEnabled(true);
        }
//media player start


    }

    public void cikis(View view) {
        Intent i = new Intent(BolmeActivity.this,HomeActivity.class);startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        Intent myIntent = new Intent(BolmeActivity.this, HomeActivity.class);
        myIntent.putExtra("bolmesorusayisi", questioncounter);
        myIntent.putExtra("bolmedogrusayisi", dogrucounter);
        myIntent.putExtra("bolmeayanlissayisi", yanliscounter);
        myIntent.putExtra("bolmeabossayisi", boscounter);

        startActivity(myIntent);

    }

    @Override
    public void onBackPressed() {

        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
        if (sorugovde.getVisibility()==View.VISIBLE){
            Toast.makeText(BolmeActivity.this,getString(R.string.quittoast),Toast.LENGTH_SHORT).show();
        }
        if (zorluk.getVisibility()==View.VISIBLE){
            Intent myIntent = new Intent(BolmeActivity.this, HomeActivity.class);
            startActivity(myIntent);

        }
    }



}







