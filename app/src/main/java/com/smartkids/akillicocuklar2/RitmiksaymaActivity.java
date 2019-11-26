package com.smartkids.akillicocuklar2;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.ramotion.fluidslider.FluidSlider;
import com.smartkids.akillicocuklar2.utils.Constants;
import com.smartkids.akillicocuklar2.utils.SharedPrefManager;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by erdem.salgin on 12.12.2017.
 */

public class RitmiksaymaActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private FluidSlider fluidSlider;
    private int userAnswer;
    private MediaPlayer dogru_cevap_music, hatali_cevap_music;
    private boolean user_answered;
    private List<Integer> numbers = new ArrayList<>();
    private AppCompatButton sayi1Btn, sayi2Btn, sayi3Btn, sayi4Btn, sayi5Btn, sayi6Btn, sayi7Btn, sayi8Btn,skorBtn,tamamlaBtn, sorunumarasiBtn, nextquestionBtn,answerBtn;
    private List<AppCompatButton> numberBtns = new ArrayList<>();
    private int dogrucevap;
    private ConstraintLayout soru_main,box_container;
    private int hangiKutu;
    private AlertDialog alertDialog;
    private SharedPrefManager sharedPrefManager;




    private int questioncounter = 0;
    private int cevapcounter = 0;
    private int scorecounter = 0;
    private int dogrucounter = 0;
    private int yanliscounter = 0;
    private int boscounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ritmik_oruntu_layout);

        sharedPrefManager = new SharedPrefManager(this);

        createAds();
        fluidSlider = findViewById(R.id.fluidSlider);

        sayi1Btn = findViewById(R.id.nmb1Btn);
        sayi2Btn = findViewById(R.id.nmb2Btn);
        sayi3Btn = findViewById(R.id.nmb3Btn);
        sayi4Btn = findViewById(R.id.nmb4Btn);
        sayi5Btn = findViewById(R.id.nmb5Btn);
        sayi6Btn = findViewById(R.id.nmb6Btn);
        sayi7Btn = findViewById(R.id.nmb7Btn);
        sayi8Btn = findViewById(R.id.nmb8Btn);
        skorBtn = findViewById(R.id.skorBtn);
        sorunumarasiBtn = findViewById(R.id.sorunumarasıBtn);
        box_container = findViewById(R.id.box_container);
        nextquestionBtn = findViewById(R.id.nextquestionBtn);
        tamamlaBtn = findViewById(R.id.tamamlaBtn);
        soru_main = findViewById(R.id.soru_main);
        answerBtn = findViewById(R.id.answerBtn);

        numberBtns.add(sayi1Btn);
        numberBtns.add(sayi2Btn);
        numberBtns.add(sayi3Btn);
        numberBtns.add(sayi4Btn);
        numberBtns.add(sayi5Btn);
        numberBtns.add(sayi6Btn);
        numberBtns.add(sayi7Btn);
        numberBtns.add(sayi8Btn);


        dogru_cevap_music = MediaPlayer.create(this, R.raw.rightanswer);
        hatali_cevap_music = MediaPlayer.create(this, R.raw.wronganswer);

        prepareQuestion();




    }


    private List<Integer> getNumbers(int random) {
        numbers.clear();
        Random rnd = new Random();
        int ilksayi = 0;
        int artis = 0;


        switch (random) {
            case 0:
                ilksayi = rnd.nextInt(20 - 1) + 1;
                artis = rnd.nextInt(16 - 1) + 1;
                numbers.add(ilksayi);
                for (int i = 0; i < 11; i++) {
                    numbers.add(numbers.get(i) + artis);
                }
                break;

            case 1:
                artis = rnd.nextInt(9 - 1) + 1;
                ilksayi = 12 * artis;
                numbers.add(ilksayi);
                for (int i = 0; i < 11; i++) {
                    numbers.add(numbers.get(i) - artis);
                }
                break;

            case 2:
                ilksayi = rnd.nextInt(4 - 1) + 1;
                numbers.add(ilksayi);

                for (int i = 0; i < 11; i++) {
                    if (i % 2 == 0) {
                        numbers.add(numbers.get(i) * 2);
                    } else {
                        numbers.add(numbers.get(i) + ilksayi);
                    }
                }
                break;

            case 3:
                ilksayi = rnd.nextInt(20 - 1) + 1;
                artis = rnd.nextInt(5 - 1) + 1;
                int constant2 = rnd.nextInt(5 - 1) + 1;
                numbers.add(ilksayi);
                for (int i = 0; i < 11; i++) {
                    if (i % 2 == 0) {
                        numbers.add(numbers.get(i) + artis + constant2);
                    } else {
                        numbers.add(numbers.get(i) - artis);
                    }
                }
                break;

            case 4:
                ilksayi = rnd.nextInt(10 - 1) + 1;
                int constant3 = rnd.nextInt(4 - 1) + 1;
                numbers.add(ilksayi);
                for (int i = 0; i < 11; i++) {
                    numbers.add(numbers.get(i) + (constant3 * (i + 1)));
                }
                break;
        }

        Log.i("prepareQuestion3", "İlk Sayı:" + ilksayi + " " + "Artiş:" + artis + " " + "Random:" + random);


        return numbers;

    }

    private void prepareQuestion() {

        user_answered=false;
        answerBtn.setEnabled(true);

        if (questioncounter!=0 && questioncounter%5==0){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

        sorunumarasiBtn.setText(getString(R.string.question) + " " + String.valueOf(questioncounter+1));


        Random rndm = new Random();
        int getNumber_method = rndm.nextInt(5);

        getNumbers(getNumber_method);


        Random random = new Random();
         hangiKutu = random.nextInt(8);

        for (int i = 0; i < numberBtns.size(); i++) {
            if (hangiKutu != i) {
                numberBtns.get(i).setText(String.valueOf(numbers.get(i + 2)));
                numberBtns.get(i).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textdark));
            } else {
                dogrucevap = numbers.get(i + 2);
                numberBtns.get(hangiKutu).setText("?");
                numberBtns.get(hangiKutu).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
            }

        }


        Log.i("prepareQuestion2", "Hangi Sayi:" + hangiKutu + " +" + "Dogru:" + dogrucevap + " " + numbers.toString());
        final int sliderMin, sliderMax;

        List<Integer> tempList = new ArrayList<>();

        tempList.add(numbers.get(2 + hangiKutu - 1));
        tempList.add(numbers.get(2 + hangiKutu - 2));
        tempList.add(numbers.get(2 + hangiKutu + 1));
        tempList.add(numbers.get(2 + hangiKutu + 2));

        Collections.sort(tempList);

        Log.i("prepareQuestion22", "Temp:" + tempList.toString());

        if (dogrucevap > tempList.get(1)) {
            sliderMin = tempList.get(1);
        } else {
            sliderMin = tempList.get(0);
        }

        if (dogrucevap > tempList.get(2)) {
            sliderMax = tempList.get(3);
        } else {
            sliderMax = tempList.get(2);
        }

        fluidSlider.setPosition(0.0f);
        fluidSlider.setBubbleText(String.valueOf(sliderMin));


        fluidSlider.setPositionListener(new Function1<Float, Unit>() {
            @Override
            public Unit invoke(Float aFloat) {
                String value = String.valueOf((int) (sliderMin + (aFloat * (sliderMax - sliderMin))));
                fluidSlider.setBubbleText(value);
                userAnswer = Integer.parseInt(value);
                return Unit.INSTANCE;
            }
        });


        box_container.startAnimation(AnimationUtils.loadAnimation(RitmiksaymaActivity.this, R.anim.slide_in));

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


    public void cevapla(View view) {
        answerBtn.setEnabled(false);
        user_answered = true;
        questioncounter++;
        cevapcounter++;


        if (userAnswer == dogrucevap) {

            dogrucounter++;

            try {
                if (dogru_cevap_music == null) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }

            scorecounter = scorecounter + Constants.ritmik_puan;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter - Constants.ritmik_puan, scorecounter);
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


        }else {

            yanliscounter++;

            numberBtns.get(hangiKutu).setText(String.valueOf(dogrucevap));
            numberBtns.get(hangiKutu).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));

            try {
                if (hatali_cevap_music == null) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }

            scorecounter = scorecounter - Constants.ritmik_puan;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter + Constants.ritmik_puan, scorecounter);
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

        }
    }


    public void nextquestion(View view) {

        if (!user_answered) {
            boscounter++;
            questioncounter++;
        }


        prepareQuestion();


    }

    public void tamamla(View viewClick) {

        View viewX = getLayoutInflater().inflate(R.layout.alert_results, null);

        final AppCompatButton dogruView = viewX.findViewById(R.id.dogrusayisiTxtv);
        final AppCompatButton yanlisView = viewX.findViewById(R.id.yanlissayisiTxtv);
        final AppCompatButton sorusayisiView = viewX.findViewById(R.id.sorusayisiTxtv);
        final AppCompatButton bossayisi = viewX.findViewById(R.id.bossayisiTxtv);
        final AppCompatButton puanView = viewX.findViewById(R.id.toplampuanTxtv);
        final AppCompatButton testcikisBtn = viewX.findViewById(R.id.testcikisBtn);
        final AppCompatButton progressTxt = viewX.findViewById(R.id.progressTxt);
        final Button learnedStampBtn = viewX.findViewById(R.id.learnedStampBtn);
        ProgressBar progressBar = viewX.findViewById(R.id.pointsbar);

        learnedStampBtn.setVisibility(View.INVISIBLE);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(viewX);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setView(viewX);
        alertDialog.show();
        alertDialog.setCancelable(false);



        testcikisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                cikis();
            }
        });





        if (cevapcounter > 0) {

            int success_percent = (dogrucounter*100)/questioncounter;

            Log.i("success_percent","Success:"+success_percent+" "+"Question:"+questioncounter);

            if (success_percent==100) {

                learnedStampBtn.setVisibility(View.INVISIBLE);
                final Animation stampAnim = AnimationUtils.loadAnimation(this,R.anim.wordcompleted);
                stampAnim.setFillAfter(true);
                learnedStampBtn.startAnimation(stampAnim);
            }





            progressBar.setProgress(0);
            progressBar.setMax(100*1000);
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, success_percent*1000 );
            animation.setDuration(2000); // 3.5 second
            animation.setInterpolator(new LinearInterpolator());
            animation.start();

            ValueAnimator animator_percent = new ValueAnimator();
            animator_percent.setObjectValues(0, success_percent);
            animator_percent.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    progressTxt.setText("%"+ String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator_percent.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator_percent.setDuration(2000);
            animator_percent.start();





            //  Progres animation = ObjectAnimator.ofInt(progressBar, "progress", 0, Math.round(dogrucounter/questioncounter*100));


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

            try {

                final MediaPlayer countmusic = MediaPlayer.create(this, R.raw.count1);
                countmusic.start();
                countmusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        countmusic.release();

                        testcikisBtn.setEnabled(true);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toasthicsorucevaplamadın), Toast.LENGTH_SHORT).show();

            boscounter = 0;
            questioncounter = 0;


            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(0));
            bossayisi.setText(getString(R.string.unanswered) + String.valueOf(0));
            testcikisBtn.setEnabled(true);
        }

        /////////////////skor bilgileri burada///////////////////////////////

        sharedPrefManager.putIntegertoSP(getString(R.string.konularritmik)+"soru",questioncounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik)+"soru",0));

        sharedPrefManager.putIntegertoSP(getString(R.string.konularritmik)+"dogru",dogrucounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik)+"dogru",0));

        sharedPrefManager.putIntegertoSP(getString(R.string.konularritmik)+"yanlis",yanliscounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik)+"yanlis",0));

        sharedPrefManager.putIntegertoSP(getString(R.string.konularritmik)+"bos",boscounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik)+"bos",0));

        sharedPrefManager.putIntegertoSP(getString(R.string.konularritmik)+"skor",scorecounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik)+"skor",0));



        sharedPrefManager.putIntegertoSP("soru_total",questioncounter+sharedPrefManager.getIntegerFromSP("soru_total",0));
        sharedPrefManager.putIntegertoSP("dogru_total",dogrucounter+sharedPrefManager.getIntegerFromSP("dogru_total",0));
        sharedPrefManager.putIntegertoSP("yanlis_total",yanliscounter+sharedPrefManager.getIntegerFromSP("yanlis_total",0));
        sharedPrefManager.putIntegertoSP("bos_total",boscounter+sharedPrefManager.getIntegerFromSP("bos_total",0));
        sharedPrefManager.putIntegertoSP("skor_total",scorecounter+sharedPrefManager.getIntegerFromSP("skor_total",0));










    }

    public void cikis() {


        if (dogru_cevap_music != null) {
            dogru_cevap_music.release();
        }
        if (hatali_cevap_music != null) {
            hatali_cevap_music.release();
        }

        final Intent i = new Intent(RitmiksaymaActivity.this, TrainingActivity.class);
        i.putExtra("four_operation", false);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    startActivity(i);
                    finish();
                }

            });


        } else {
            startActivity(i);
            finish();
        }


    }

    @Override
    public void onBackPressed() {

        Intent k = new Intent(RitmiksaymaActivity.this, TrainingActivity.class);
        k.putExtra("four_operation", true);
        startActivity(k);
        this.finish();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();



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
