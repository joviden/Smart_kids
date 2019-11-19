package com.smartkids.akillicocuklar2;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.smartkids.akillicocuklar2.utils.Constants;
import com.smartkids.akillicocuklar2.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FourOpActivity extends AppCompatActivity {

    private String operator = "";
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private SharedPrefManager sharedPrefManager;
    private String difficulty = "";
    private AlertDialog alert_difficulty;


    private int questioncounter = 0;
    private int cevapcounter = 0;
    private int scorecounter = 0;
    private int dogrucounter = 0;
    private int yanliscounter = 0;
    private int boscounter = 0;
    private int sayi1, sayi2,dogrucevap;
    int rangeup,rangedown,range2up,range2down;
    private boolean user_answered;
    private MediaPlayer dogru_cevap_music,hatali_cevap_music;

    AppCompatButton sikA_Btn, sikB_Btn,sikC_Btn,sikD_Btn,sayi1Btn,sayi2Btn,sembolBtn,sorunumarasıBtn,tamamlaBtn,skorBtn,nextquestionBtn;


    private ConstraintLayout sorusayilarlayout, siklarlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.four_opt_layout);
        operator = getIntent().getStringExtra("op");
        Log.i("getIntent", operator);

        sorusayilarlayout = findViewById(R.id.sorusayilarlayout);
        siklarlayout = findViewById(R.id.siklarlayout);
        sikA_Btn = findViewById(R.id.sikaBtn);
        sikB_Btn = findViewById(R.id.sikbBtn);
        sikC_Btn = findViewById(R.id.sikcBtn);
        sikD_Btn = findViewById(R.id.sikdBtn);
        sayi1Btn = findViewById(R.id.sayi1Btn);
        sayi2Btn = findViewById(R.id.sayi2Btn);
        sembolBtn = findViewById(R.id.sembolBtn);
        sorunumarasıBtn = findViewById(R.id.sorunumarasıBtn);
        nextquestionBtn = findViewById(R.id.nextquestionBtn);
        tamamlaBtn = findViewById(R.id.tamamlaBtn);
        skorBtn = findViewById(R.id.skorBtn);

        dogru_cevap_music = MediaPlayer.create(this, R.raw.rightanswer);
        hatali_cevap_music = MediaPlayer.create(this, R.raw.wronganswer);


        createAds();
        showDifficultyAlert();


    }

    private void showDifficultyAlert() {
        View view = getLayoutInflater().inflate(R.layout.alert_difficulty, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alert_difficulty = builder.create();
        alert_difficulty.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert_difficulty.setView(alert_difficulty.getLayoutInflater().inflate(R.layout.alert_difficulty, null));
        alert_difficulty.show();
        alert_difficulty.setCancelable(false);


    }

    public void difficultyClick(View view) {

        Animation animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        view.startAnimation(animBlink);


        final MediaPlayer selectAvatarSound = MediaPlayer.create(this, R.raw.levelup);
        selectAvatarSound.start();
        selectAvatarSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                selectAvatarSound.release();
            }
        });


        animBlink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (alert_difficulty != null) {
                            alert_difficulty.dismiss();
                            prepareQuestion();
                        }
                    }
                }, 900);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        switch (view.getId()) {
            case R.id.kolayBtn:
                if (operator.equals(getString(R.string.konulartoplama))) {
                    rangedown=0;
                    rangeup=10;
                } else if (operator.equals(getString(R.string.konularcikarma))) {
                    rangedown=0;
                    rangeup=10;
                    range2down=0;
                    range2up=10;
                } else if (operator.equals(getString(R.string.konularcarpma))) {
                    rangedown=0;
                    rangeup=10;
                } else if (operator.equals(getString(R.string.konularbolme))) {
                    rangeup=10;
                    rangedown=1;
                    range2up=5;
                    range2down=1;
                }

                break;

            case R.id.ortaBtn:
                if (operator.equals(getString(R.string.konulartoplama))) {
                    rangedown=15;
                    rangeup=50;
                } else if (operator.equals(getString(R.string.konularcikarma))) {
                    rangedown=30;
                    rangeup=50;
                    range2down=8;
                    range2up=35;
                } else if (operator.equals(getString(R.string.konularcarpma))) {
                    rangedown=8;
                    rangeup=15;
                } else if (operator.equals(getString(R.string.konularbolme))) {
                    rangeup=15;
                    rangedown=9;
                    range2up=10;
                    range2down=4;
                }
                break;

            case R.id.zorBtn:
                if (operator.equals(getString(R.string.konulartoplama))) {
                    rangedown=50;
                    rangeup=100;
                } else if (operator.equals(getString(R.string.konularcikarma))) {
                    rangedown=50;
                    rangeup=100;
                    range2down=35;
                    range2up=75;
                } else if (operator.equals(getString(R.string.konularcarpma))) {
                    rangedown=12;
                    rangeup=25;
                } else if (operator.equals(getString(R.string.konularbolme))) {
                    rangeup=20;
                    rangedown=12;
                    range2up=15;
                    range2down=8;
                }
                break;
        }


    }

    private void prepareQuestion() {

        sikA_Btn.setBackgroundResource(R.drawable.siklar);
        sikB_Btn.setBackgroundResource(R.drawable.siklar);
        sikC_Btn.setBackgroundResource(R.drawable.siklar);
        sikD_Btn.setBackgroundResource(R.drawable.siklar);

        sikA_Btn.setEnabled(true);
        sikB_Btn.setEnabled(true);
        sikC_Btn.setEnabled(true);
        sikD_Btn.setEnabled(true);

        questioncounter++;
        sorunumarasıBtn.setText(getString(R.string.question)+" "+questioncounter);

        if (operator.equals(getString(R.string.konulartoplama))) {
            sembolBtn.setText("+");
            getNumbersToplama();
        } else if (operator.equals(getString(R.string.konularcikarma))) {
            sembolBtn.setText("-");
            getNumbersCikarma();
        } else if (operator.equals(getString(R.string.konularcarpma))) {
            sembolBtn.setText("x");
            getNumbersCarpma();
        } else if (operator.equals(getString(R.string.konularbolme))) {
            sembolBtn.setText("÷");
            getNumbersBolme();
        }

        sorusayilarlayout.startAnimation(AnimationUtils.loadAnimation(FourOpActivity.this, R.anim.slide_in));
        siklarlayout.startAnimation(AnimationUtils.loadAnimation(FourOpActivity.this, R.anim.slide_in));

        try {
            final MediaPlayer gecis = MediaPlayer.create(this, R.raw.sorugecisi);
            gecis.start();
            gecis.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    gecis.release();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void cevapla (View view) {

        user_answered = true;
        cevapcounter++;

        view.setBackgroundResource(R.drawable.siklarclicked);
        sikA_Btn.setEnabled(false);
        sikB_Btn.setEnabled(false);
        sikC_Btn.setEnabled(false);
        sikD_Btn.setEnabled(false);
        tamamlaBtn.setEnabled(false);
        nextquestionBtn.setEnabled(false);


        if (view.getTag().toString().equals(String.valueOf(dogrucevap))) {

            dogrucounter++;

            try {
                if (dogru_cevap_music==null){
                    dogru_cevap_music = MediaPlayer.create(this, R.raw.rightanswer);
                }
                dogru_cevap_music.start();
                dogru_cevap_music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        tamamlaBtn.setEnabled(true);
                        nextquestionBtn.setEnabled(true);
                    }
                });
            }catch (Exception e) {e.printStackTrace();}



            scorecounter=scorecounter+Constants.toplama_puan;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-Constants.toplama_puan, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorBtn.setText(String.valueOf(animation.getAnimatedValue()));
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
                    prepareQuestion();
                }
            }, 1100);



        } else {

            try {
                if (hatali_cevap_music==null){
                    hatali_cevap_music = MediaPlayer.create(this, R.raw.wronganswer);
                }
                hatali_cevap_music.start();
                hatali_cevap_music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        tamamlaBtn.setEnabled(true);
                        nextquestionBtn.setEnabled(true);
                    }
                });
            }catch (Exception e) {e.printStackTrace();}

            scorecounter=scorecounter-Constants.toplama_puan;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter+Constants.toplama_puan, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorBtn.setText(String.valueOf(animation.getAnimatedValue()));
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
            if (Integer.parseInt(sikA_Btn.getText().toString())==dogrucevap) {sikA_Btn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikB_Btn.getText().toString())==dogrucevap) {sikB_Btn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikC_Btn.getText().toString())==dogrucevap) {sikC_Btn.setBackgroundResource(R.drawable.siklarclicked);}
            if (Integer.parseInt(sikD_Btn.getText().toString())==dogrucevap) {sikD_Btn.setBackgroundResource(R.drawable.siklarclicked);}


        }



    }

    public void nextquestion (View view) {

        if (!user_answered) {boscounter++;}

        prepareQuestion();
    }

    private void getNumbersToplama() {
        Random random = new Random();
        sayi1 = random.nextInt(rangeup-rangedown+1)+rangedown;
        sayi2 = random.nextInt(rangeup-rangedown+1)+rangedown;
        dogrucevap = sayi1+sayi2;

        List<Integer> presiklar = new ArrayList<Integer>();
        for (int i = 1; i <6 ; i++) {
            presiklar.add(dogrucevap+i);
            if (dogrucevap-i>0)
                presiklar.add(dogrucevap-i);
        }
        presiklar.add(dogrucevap + 10);
        presiklar.remove(Integer.valueOf(dogrucevap));
        Collections.shuffle(presiklar);
        presiklar.add(random.nextInt(4 - 0)+0,dogrucevap);
        sikA_Btn.setText(String.valueOf(presiklar.get(0)));
        sikB_Btn.setText(String.valueOf(presiklar.get(1)));
        sikC_Btn.setText(String.valueOf(presiklar.get(2)));
        sikD_Btn.setText(String.valueOf(presiklar.get(3)));
        sayi1Btn.setText(String.valueOf(sayi1));
        sayi2Btn.setText(String.valueOf(sayi2));

        sikA_Btn.setTag(String.valueOf(presiklar.get(0)));
        sikB_Btn.setTag(String.valueOf(presiklar.get(1)));
        sikC_Btn.setTag(String.valueOf(presiklar.get(2)));
        sikD_Btn.setTag(String.valueOf(presiklar.get(3)));


    }

    private void getNumbersCikarma() {


    }

    private void getNumbersCarpma() {


    }

    private void getNumbersBolme() {


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent k = new Intent(FourOpActivity.this, TrainingActivity.class);
        k.putExtra("four_operation", true);
        startActivity(k);
        this.finish();
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private void createAds() {

        MobileAds.initialize(this, Constants.adMobId);

        FrameLayout ad_container = findViewById(R.id.ad_container);
        mAdView = new AdView(this);
        mAdView.setAdUnitId(Constants.bannerTestId);  //DEGISTIR
        ad_container.addView(mAdView);


        mAdView.setAdSize(getAdSize());

        AdRequest adRequest = new AdRequest.Builder().build();
        if (mAdView != null) {
            mAdView.loadAd(adRequest);
        }

        mInterstitialAd = new InterstitialAd(this);
        // mInterstitialAd.setAdUnitId(Constants.interstitialId);  DEGISTIR
        mInterstitialAd.setAdUnitId(Constants.interstitialTestId);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
    }
}
