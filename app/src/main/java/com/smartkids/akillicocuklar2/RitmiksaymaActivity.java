package com.smartkids.akillicocuklar2;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
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

/**
 * Created by erdem.salgin on 12.12.2017.
 */

public class RitmiksaymaActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private FluidSlider fluidSlider;
    private AlertDialog alertDialog;
    private int gameDifficulty = 0;


    private List<Integer> numbers = new ArrayList<>();

    private AppCompatButton sayi1Btn, sayi2Btn, sayi3Btn, sayi4Btn, sayi5Btn, sayi6Btn, sayi7Btn, sayi8Btn;
    private List<AppCompatButton> numberBtns = new ArrayList<>();
    private int dogrucevap;


    TextView sayi1View, sayi2View, sayi3View, sayi4View, sayi5View, sayi6View, cevapView, skorTxv, dogruView, yanlisView, sorusayisiView, soruView, puanView, bossayisi;
    Button artirBtn, eksiltBtn, cvpBtn, testcikisBtn, tamamlaBtn, nextquestionBtn;
    Integer answer;
    MediaPlayer cevapmusic, countmusic;

    int questioncounter = 1;
    int cevapcounter;
    int scorecounter = 0;
    int dogrucounter = 0;
    int yanliscounter = 0;
    int boscounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ritmik_oruntu_layout);

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

        numberBtns.add(sayi1Btn);
        numberBtns.add(sayi2Btn);
        numberBtns.add(sayi3Btn);
        numberBtns.add(sayi4Btn);
        numberBtns.add(sayi5Btn);
        numberBtns.add(sayi6Btn);
        numberBtns.add(sayi7Btn);
        numberBtns.add(sayi8Btn);

        prepareQuestion();






       /* RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);

        sorugovde.setVisibility(View.VISIBLE);
        sonuclar.setVisibility(View.GONE);



        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        soruView.setText(getString(R.string.questionleft) +" 1");

        //soruyu yazıyorum

        int range1;
        int range2;
        int aralik;



        Random random = new Random();
        aralik = random.nextInt(5-1+1)+1;
        range1 = random.nextInt(20-0+1)+1;
        range2 = range1+35;
        //Log.i("range1", String.valueOf(range1));
        //Log.i("range2", String.valueOf(range2));
        //Log.i("aralik", String.valueOf(aralik));


        ArrayList<Integer> dizi = new ArrayList<Integer>(6);
        for (int i=range1;i<range2;i=i+aralik) dizi.add(i);
        //Log.i("dizi",dizi.toString());

        cevapView.setText(""+dizi.get(0));

        sayi1View.setText(""+dizi.get(0));
        sayi2View.setText(""+dizi.get(1));
        sayi3View.setText(""+dizi.get(2));
        sayi5View.setText(""+dizi.get(4));
        sayi6View.setText(""+dizi.get(5));


         answer = Integer.valueOf(dizi.get(3));
         //Log.i("dogrucevap",answer.toString());*/
    }






    private List<Integer> getNumbers(int random) {
        numbers.clear();
        Random rnd = new Random();
        int ilksayi = rnd.nextInt(20 - 1) + 1;
        int artis = rnd.nextInt(16 - 1) + 1;
        numbers.add(ilksayi);

        Log.i("prepareQuestion", "İlk Sayı:" + ilksayi + " " + "Artiş:" + artis + " " + "Random:" + random);

        switch (random) {
            case 0:
                for (int i = 0; i < 7; i++) {
                    numbers.add(numbers.get(i) + artis);
                }
                break;

            case 1:
                numbers.set(0, numbers.get(0) + artis * 9);
                for (int i = 0; i < 7; i++) {
                    numbers.add(numbers.get(i) - artis);
                }
                break;

            case 2:
                for (int i = 0; i < 7; i++) {
                    numbers.add(numbers.get(i) * 2);
                }
                break;

            case 3:
                int constant2 = rnd.nextInt(5 - 1) + 1;
                for (int i = 0; i < 7; i++) {
                    if (i % 2 == 0) {
                        numbers.add(numbers.get(i) + artis + constant2);
                    } else {
                        numbers.add(numbers.get(i) - artis);
                    }
                }
                break;

            case 4:
                int constant = rnd.nextInt(4 - 1) + 1;
                for (int i = 0; i < 7; i++) {
                    numbers.add(numbers.get(i) + (constant * (i + 1)));
                }
                break;
        }

        Log.i("prepareQuestion", numbers.toString());


        return numbers;

    }

    private void prepareQuestion() {

        fluidSlider.setPosition(0.0f);


        Random rndm = new Random();
        int getNumber_method = rndm.nextInt(5);

        getNumbers(getNumber_method);


        Random random = new Random();
        int hangisayi = random.nextInt(8);


        for (int i = 0; i < numberBtns.size(); i++) {
            if (i != hangisayi) {
                numberBtns.get(i).setText(String.valueOf(numbers.get(i)));
                numberBtns.get(i).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textdark));
            } else {
                numberBtns.get(i).setText("?");
                numberBtns.get(i).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                dogrucevap = numbers.get(i);

            }

        }

        Log.i("dogruCevap", String.valueOf(dogrucevap));


    }


    public void artir(View view) {

        cevapView = (TextView) findViewById(R.id.cevapTxtv);
        artirBtn = (Button) findViewById(R.id.artitBtn);
        eksiltBtn = (Button) findViewById(R.id.eksiltBtn);


        Integer i = Integer.valueOf(cevapView.getText().toString());
        i++;
        //Log.i("artit",i.toString());
        cevapView.setText("" + i);
    }

    public void eksilt(View view) {

        cevapView = (TextView) findViewById(R.id.cevapTxtv);
        artirBtn = (Button) findViewById(R.id.artitBtn);
        eksiltBtn = (Button) findViewById(R.id.eksiltBtn);


        Integer i = Integer.valueOf(cevapView.getText().toString());
        i--;
        //Log.i("artit",i.toString());
        cevapView.setText("" + i);
    }

    public void cevapla(View view) {
        cvpBtn = (Button) findViewById(R.id.cvpBtn);
        cevapView = (TextView) findViewById(R.id.cevapTxtv);
        skorTxv = (TextView) findViewById(R.id.skorTxv);
        sayi1View = (TextView) findViewById(R.id.sayi1Txtv);
        sayi2View = (TextView) findViewById(R.id.sayi2Txtv);
        dogruView = (TextView) findViewById(R.id.dogrusayisiTxtv);
        yanlisView = (TextView) findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView = (TextView) findViewById(R.id.sorusayisiTxtv);
        artirBtn = (Button) findViewById(R.id.artitBtn);
        eksiltBtn = (Button) findViewById(R.id.eksiltBtn);

        tamamlaBtn = (Button) findViewById(R.id.tamamlaBtn);
        nextquestionBtn = findViewById(R.id.nextquestionBtn);

        artirBtn.setEnabled(false);
        eksiltBtn.setEnabled(false);
        cvpBtn.setEnabled(false);
        tamamlaBtn.setEnabled(false);
        nextquestionBtn.setEnabled(false);

        cevapcounter++;
        sorusayisiView.setText("Cevaplanan Soru: " + Integer.toString(cevapcounter));

        Integer cvp = Integer.valueOf(cevapView.getText().toString());
        Integer a = Integer.valueOf(answer);
        //Log.i("cevap",cvp.toString());  Log.i("answer",a.toString());
        if (cvp == a) {

            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soruhazirla();
                }
            }, 1100);

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
            } catch (Exception e) {
                e.printStackTrace();
            }

            cevapView.setBackgroundResource(R.drawable.siklarclicked);
            scorecounter = scorecounter + 100;

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter - 100, scorecounter);
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
            dogrucounter++;


            //Toast.makeText(RitmiksaymaActivity.this,"Bravo",Toast.LENGTH_SHORT).show();
        } else {
            cevapView.setBackgroundResource(R.drawable.hatalisik);
            cevapView.setTextColor(0xFFFFFFFF);

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
            } catch (Exception e) {
                e.printStackTrace();
            }
            scorecounter = scorecounter - 50;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter + 50, scorecounter);
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
            //Toast.makeText(RitmiksaymaActivity.this,"Zort",Toast.LENGTH_SHORT).show();


        }
    }


    public void nextquestion(View view) {


        /////////////////////////reklamlar//////////////////////////////////////////////////////////

        if (questioncounter == 10 || questioncounter == 20 || questioncounter == 30 || questioncounter == 40 || questioncounter == 50) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

        /////////////////////////reklamlar//////////////////////////////////////////////////////////

        prepareQuestion();


    }

    public void tamamla(View view) {

        /////////////////////reklam///////////////////////////////

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        /////////////////////reklam///////////////////////////////

        dogruView = (TextView) findViewById(R.id.dogrusayisiTxtv);
        yanlisView = (TextView) findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView = (TextView) findViewById(R.id.sorusayisiTxtv);
        bossayisi = (TextView) findViewById(R.id.bossayisiTxtv);
        puanView = (TextView) findViewById(R.id.toplampuanTxtv);
        testcikisBtn = (Button) findViewById(R.id.testcikisBtn);


        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        LinearLayout sonuclar = (LinearLayout) findViewById(R.id.sonuclarlayout);
        final TextView bossayisi = (TextView) findViewById(R.id.bossayisiTxtv);
        testcikisBtn = (Button) findViewById(R.id.testcikisBtn);


        testcikisBtn.setEnabled(false);

        sorugovde.setVisibility(View.GONE);
        sonuclar.setVisibility(View.VISIBLE);

        if (cevapcounter > 0) {
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
            int x = yanliscounter + dogrucounter;
            boscounter = questioncounter - x;
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

                countmusic = MediaPlayer.create(this, R.raw.count1);
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


        } else { //çıkış yaptır!!!!!!
            Toast.makeText(RitmiksaymaActivity.this, getString(R.string.toasthicsorucevaplamadın), Toast.LENGTH_SHORT).show();
            boscounter = questioncounter;
            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(questioncounter));
            bossayisi.setText(getString(R.string.unanswered) + String.valueOf(questioncounter));
            testcikisBtn.setEnabled(true);

        }

        /////////////////skor bilgileri burada///////////////////////////////

        Integer intritmiksoru, intritmikdogru, intritmikyanlis, intritmikbos, intritmikkumulatif;
        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        intritmiksoru = sp.getInt("ritmiksoru", 0);
        intritmikdogru = sp.getInt("ritmikdogru", 0);
        intritmikyanlis = sp.getInt("ritmikyanlis", 0);
        intritmikbos = sp.getInt("ritmikbos", 0);
        intritmikkumulatif = sp.getInt("ritmikkumulatif", 0);


        spEditor.putInt("ritmiksoru", (questioncounter + intritmiksoru));
        spEditor.putInt("ritmikdogru", (dogrucounter + intritmikdogru));
        spEditor.putInt("ritmikyanlis", (yanliscounter + intritmikyanlis));
        spEditor.putInt("ritmikbos", (boscounter + intritmikbos));
        spEditor.putInt("ritmikleader", (scorecounter));
        spEditor.putInt("ritmikkumulatif", (scorecounter + intritmikkumulatif));
        spEditor.commit();

        ////////////////////////SKOR BİLGİLERİ BİTTİ////////////////////////

        // Intent myIntent = new Intent(RitmiksaymaActivity.this, MainmenuActivity.class);
        //   myIntent.putExtra("ritmikscore", scorecounter);
        //  startActivity(myIntent);

    }

    public void cikis(View view) {



      /*  Intent i = new Intent(RitmiksaymaActivity.this,MathgamesActivity.class);startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(i);this.finish();*/

    }

    @Override
    public void onBackPressed() {


        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        if (sorugovde.getVisibility() == View.VISIBLE) {
            Toast.makeText(RitmiksaymaActivity.this, getString(R.string.quittoast), Toast.LENGTH_SHORT).show();
        }

    }

    public void soruhazirla() {

        soruView = (TextView) findViewById(R.id.sorunumarasıTxtv);
        puanView = (TextView) findViewById(R.id.toplampuanTxtv);
        cevapView = (TextView) findViewById(R.id.cevapTxtv);

        cevapView.setBackgroundResource(R.drawable.siklar);
        cevapView.setTextColor(0xFF000000);

        questioncounter++;
        soruView.setText(getString(R.string.questionleft) + " " + Integer.toString(questioncounter));

        if (questioncounter == 5 || questioncounter == 10 || questioncounter == 15 || questioncounter == 20 || questioncounter == 25) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        sorugovde.startAnimation(AnimationUtils.loadAnimation(RitmiksaymaActivity.this, R.anim.fadein_out));

        artirBtn = (Button) findViewById(R.id.artitBtn);
        eksiltBtn = (Button) findViewById(R.id.eksiltBtn);
        cvpBtn = (Button) findViewById(R.id.cvpBtn);

        artirBtn.setEnabled(true);
        eksiltBtn.setEnabled(true);
        cvpBtn.setEnabled(true);

        int range1;
        int range2;
        int aralik;

        Random random = new Random();
        aralik = random.nextInt(5 - 1 + 1) + 1;
        range1 = random.nextInt(20 - 0 + 1) + 1;
        range2 = range1 + 35;
        //Log.i("range1", String.valueOf(range1));
        //Log.i("range2", String.valueOf(range2));
        //Log.i("aralik", String.valueOf(aralik));


        ArrayList<Integer> dizi = new ArrayList<Integer>(6);
        for (int i = range1; i < range2; i = i + aralik) dizi.add(i);
        //Log.i("dizi",dizi.toString());

        cevapView.setText("" + dizi.get(0));

        sayi1View.setText("" + dizi.get(0));
        sayi2View.setText("" + dizi.get(1));
        sayi3View.setText("" + dizi.get(2));
        sayi5View.setText("" + dizi.get(4));
        sayi6View.setText("" + dizi.get(5));


        answer = Integer.valueOf(dizi.get(3));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();


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
