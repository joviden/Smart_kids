package com.smartkids.akillicocuklar2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
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
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.smartkids.akillicocuklar2.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TimechallengeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private AlertDialog alertDialog;
    private CheckBox checkbox_toplama, checkbox_cikarma, checkbox_carpma, checkbox_bolme;
    private boolean opt_toplama = false;
    private  boolean opt_cikarma = false;
    private boolean opt_carpma = false;
    private boolean opt_bolme = false;
    private AppCompatButton goBtn;
    private CountDownTimer oyunsure, gerisayimcounter;
    private ConstraintLayout girisLayout,soru_main;
    private boolean isPaused = false;
    private ImageView chronoImg;
    private MediaPlayer gamemusic,intromusic;

    RelativeLayout sorugovde, gerisayim;
    LinearLayout sonuclar;
    Integer sayi1, sayi2, level, cevap, sorucounter, dogrucounter, yanliscounter, scorecounter, progress, levelsure, cevapcounter, kalansure, sorupuani;
    TextView sayi1View, sayi2View;

    ProgressBar timebar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_challange_layout);


        goBtn = findViewById(R.id.goBtn);
        girisLayout = findViewById(R.id.girisLayout);
        soru_main = findViewById(R.id.soru_main);
        chronoImg = findViewById(R.id.chronoImg);

        sorucounter = 0;
        yanliscounter = 0;
        dogrucounter = 0;
        scorecounter = 0;
        level = 0;
        progress = 0;
        cevapcounter = 0;
        kalansure = 0;
        levelsure = 20000;

        opt_toplama = false;
        opt_cikarma = false;
        opt_carpma = false;
        opt_bolme = false;


        createAds();
        showOptAlert();






    }

    private void showOptAlert() {
        View view = getLayoutInflater().inflate(R.layout.alert_opt_choose, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setView(alertDialog.getLayoutInflater().inflate(R.layout.alert_opt_choose, null));
        alertDialog.show();
        alertDialog.setCancelable(false);


        checkbox_toplama = alertDialog.findViewById(R.id.checkbox_toplama);
        checkbox_cikarma = alertDialog.findViewById(R.id.checkbox_cikarma);
        checkbox_carpma = alertDialog.findViewById(R.id.checkbox_carpma);
        checkbox_bolme = alertDialog.findViewById(R.id.checkbox_bolme);
        AppCompatButton okBtn = alertDialog.findViewById(R.id.okBtn);

        checkbox_toplama.setOnCheckedChangeListener(this);
        checkbox_cikarma.setOnCheckedChangeListener(this);
        checkbox_carpma.setOnCheckedChangeListener(this);
        checkbox_bolme.setOnCheckedChangeListener(this);
        final Animation scaleAnim = AnimationUtils.loadAnimation(this,R.anim.scale);
        final Animation animationAkrep = AnimationUtils.loadAnimation(this,R.anim.rotate_seconds);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opt_toplama || opt_cikarma || opt_carpma || opt_bolme) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    goBtn.startAnimation(scaleAnim);
                    chronoImg.startAnimation(animationAkrep);
                    intromusic = MediaPlayer.create(TimechallengeActivity.this,R.raw.ticktock);
                    intromusic.start();

                    intromusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            intromusic.start();

                        }
                    });


                } else {
                    Toast.makeText(TimechallengeActivity.this, getString(R.string.switchcheck), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



    public void goGame(View view) {

        goBtn.clearAnimation();


        if (intromusic!=null) {
            intromusic.release();
        }

        intromusic = MediaPlayer.create(TimechallengeActivity.this,R.raw.start);




        gerisayimcounter = new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                goBtn.setText("" + millisUntilFinished / 1000);
                intromusic.start();

            }

            @Override
            public void onFinish() {

               fadeIn_fadeOut_Animation(soru_main,girisLayout);
               prepareQuestion();

                if (intromusic!=null) {
                    intromusic.release();
                }



                gamemusic = MediaPlayer.create(TimechallengeActivity.this,R.raw.gamemusic1);
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

    private void prepareQuestion() {
    }

    public void cevapla(View view) {
        final Button sikaBtn = (Button) findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button) findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button) findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button) findViewById(R.id.sikdBtn);
        final TextView skorTxv = (TextView) findViewById(R.id.skorTxv);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);
        TextView baslik = findViewById(R.id.sorunumarasıTxtv);

        view.setBackgroundResource(R.drawable.siklarclicked);
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);
        sorucounter++;
        cevapcounter++;
        oyunsure.cancel();
/*
        if (sorucounter==5||sorucounter==15||sorucounter==25||sorucounter==35||sorucounter==45||sorucounter==55) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
*/

        if (view.getTag().toString().equals(Integer.toString(cevap))) {

            try {

                final MediaPlayer dogrusound = MediaPlayer.create(TimechallengeActivity.this, R.raw.rightanswer);
                dogrusound.start();
                dogrusound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        dogrusound.release();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            Animation anime = AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.bounce);
            baslik.startAnimation(anime);

            dogrucounter++;

            scorecounter = scorecounter + sorupuani;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter - sorupuani, scorecounter);
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

            soruhazirlama();

        } else {

            try {

                final MediaPlayer hatalisound = MediaPlayer.create(TimechallengeActivity.this, R.raw.wronganswer);
                hatalisound.start();
                hatalisound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        hatalisound.release();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            view.setBackgroundResource(R.drawable.hatalisik);
            if (Integer.parseInt(sikaBtn.getText().toString()) == cevap) {
                sikaBtn.setBackgroundResource(R.drawable.siklarclicked);
            }
            if (Integer.parseInt(sikbBtn.getText().toString()) == cevap) {
                sikbBtn.setBackgroundResource(R.drawable.siklarclicked);
            }
            if (Integer.parseInt(sikcBtn.getText().toString()) == cevap) {
                sikcBtn.setBackgroundResource(R.drawable.siklarclicked);
            }
            if (Integer.parseInt(sikdBtn.getText().toString()) == cevap) {
                sikdBtn.setBackgroundResource(R.drawable.siklarclicked);
            }
            yanliscounter++;
            if (yanliscounter == 1) {
                life1.setVisibility(View.INVISIBLE);
                soruhazirlama();
            }
            if (yanliscounter == 2) {
                life2.setVisibility(View.INVISIBLE);
                soruhazirlama();
            }
            if (yanliscounter == 3) {
                life3.setVisibility(View.GONE);

                if (gamemusic != null) {
                    gamemusic.release();
                    gamemusic = null;
                }
                scoreboard();

            }

        }

    }


    public void cikis(View view) {


        Intent i = new Intent(TimechallengeActivity.this, MainmenuActivity.class);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(i);
        this.finish();

    }

    public void backmenu(View view) {

        sorugovde = findViewById(R.id.sorugovdelayout);

        sonuclar = findViewById(R.id.sonuclarlayout);
        Button girisBtn = (Button) findViewById(R.id.girisBtn);
        final Button gerisayimbtn = (Button) findViewById(R.id.gerisayimbtn);
        final LinearLayout gerisayimlayout = (LinearLayout) findViewById(R.id.countdownlayout);

        final LinearLayout cikislayout = (LinearLayout) findViewById(R.id.cikislayout);
        final Button sikaBtn = (Button) findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button) findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button) findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button) findViewById(R.id.sikdBtn);
        final LinearLayout sorulayout = (LinearLayout) findViewById(R.id.sorusayilarlayout);


        if (gamemusic != null) {
            gamemusic.release();
            gamemusic = null;
        }

        cikislayout.setVisibility(View.GONE);
        sorugovde.setVisibility(View.GONE);
        //gerisayim.setVisibility(View.VISIBLE);

        gerisayimlayout.setVisibility(View.GONE);
        sonuclar.setVisibility(View.GONE);
        sorugovde.setAlpha(1);
        oyunsure.cancel();
        sikaBtn.setEnabled(true);
        sikbBtn.setEnabled(true);
        sikcBtn.setEnabled(true);
        sikdBtn.setEnabled(true);
        sikaBtn.setVisibility(View.VISIBLE);
        sikbBtn.setVisibility(View.VISIBLE);
        sikcBtn.setVisibility(View.VISIBLE);
        sikdBtn.setVisibility(View.VISIBLE);
        sorulayout.setVisibility(View.VISIBLE);


        scoreboard();


    }

    public void pause(View view) {
        Button pause = (Button) findViewById(R.id.nextquestionBtn);
        Button tamamlaaBtn = (Button) findViewById(R.id.tamamlaBtn);
        Button sikaBtn = (Button) findViewById(R.id.sikaBtn);
        Button sikbBtn = (Button) findViewById(R.id.sikbBtn);
        Button sikcBtn = (Button) findViewById(R.id.sikcBtn);
        Button sikdBtn = (Button) findViewById(R.id.sikdBtn);
        LinearLayout sorulayout = (LinearLayout) findViewById(R.id.sorusayilarlayout);
        RelativeLayout alphalayout = (RelativeLayout) findViewById(R.id.alphalayout);

        //Log.i("deneme",pause.getText().toString());


        if (!isPaused) {

            if (gamemusic != null && gamemusic.isPlaying()) {
                gamemusic.pause();
            }
            Log.i("kalansure", String.valueOf(kalansure));
            pause.setText(getString(R.string.go));
            sikaBtn.setEnabled(false);
            sikbBtn.setEnabled(false);
            sikcBtn.setEnabled(false);
            sikdBtn.setEnabled(false);
            sikaBtn.setVisibility(View.INVISIBLE);
            sikbBtn.setVisibility(View.INVISIBLE);
            sikcBtn.setVisibility(View.INVISIBLE);
            sikdBtn.setVisibility(View.INVISIBLE);
            sorulayout.setVisibility(View.INVISIBLE);
            alphalayout.setAlpha((float) 0.4);
            isPaused = true;
            tamamlaaBtn.setEnabled(false);

            /////////////////////reklam///////////////////////////////

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }

            /////////////////////reklam///////////////////////////////


        } else {

            gamecounter();
            if (gamemusic != null) {
                gamemusic.start();
            }
            pause.setText(getString(R.string.pause));
            sikaBtn.setEnabled(true);
            sikbBtn.setEnabled(true);
            sikcBtn.setEnabled(true);
            sikdBtn.setEnabled(true);
            sikaBtn.setVisibility(View.VISIBLE);
            sikbBtn.setVisibility(View.VISIBLE);
            sikcBtn.setVisibility(View.VISIBLE);
            sikdBtn.setVisibility(View.VISIBLE);
            sorulayout.setVisibility(View.VISIBLE);
            alphalayout.setAlpha(1);
            isPaused = false;
            tamamlaaBtn.setEnabled(true);
        }


    }

    public void tamamla(View view) {
        final LinearLayout cikislayout = (LinearLayout) findViewById(R.id.cikislayout);
        Button exitbutton = (Button) findViewById(R.id.exitBtn);
        Button geridon = (Button) findViewById(R.id.geridonBtn);
        final Button pauseBtn = (Button) findViewById(R.id.nextquestionBtn);
        final Button sikaBtn = (Button) findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button) findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button) findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button) findViewById(R.id.sikdBtn);
        final LinearLayout sorulayout = (LinearLayout) findViewById(R.id.sorusayilarlayout);
        cikislayout.setVisibility(View.VISIBLE);
        isPaused = true;
        if (gamemusic != null && gamemusic.isPlaying()) {
            gamemusic.pause();
        }
        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);
        sikaBtn.setVisibility(View.INVISIBLE);
        sikbBtn.setVisibility(View.INVISIBLE);
        sikcBtn.setVisibility(View.INVISIBLE);
        sikdBtn.setVisibility(View.INVISIBLE);
        sorulayout.setVisibility(View.INVISIBLE);
        sorugovde.setAlpha((float) 0.4);

        pauseBtn.setEnabled(false);


        geridon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cikislayout.setVisibility(View.INVISIBLE);
                sorugovde.setAlpha(1);
                isPaused = false;
                gamecounter();
                if (gamemusic != null) {
                    gamemusic.start();
                }
                sikaBtn.setEnabled(true);
                sikbBtn.setEnabled(true);
                sikcBtn.setEnabled(true);
                sikdBtn.setEnabled(true);
                sikaBtn.setVisibility(View.VISIBLE);
                sikbBtn.setVisibility(View.VISIBLE);
                sikcBtn.setVisibility(View.VISIBLE);
                sikdBtn.setVisibility(View.VISIBLE);
                sorulayout.setVisibility(View.VISIBLE);
                pauseBtn.setEnabled(true);
            }
        });


    }

    @Override
    public void onBackPressed() {

        Toast.makeText(TimechallengeActivity.this, getString(R.string.quittoast), Toast.LENGTH_LONG).show();

    }


    public void gamecounter() {
        final Button countertext = (Button) findViewById(R.id.progressTxt);
        final ImageView life1 = (ImageView) findViewById(R.id.life1);
        final ImageView life2 = (ImageView) findViewById(R.id.life2);
        final ImageView life3 = (ImageView) findViewById(R.id.life3);

        oyunsure = new CountDownTimer(kalansure, 100) {

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
                    try {
                        final MediaPlayer optionclicklife = MediaPlayer.create(TimechallengeActivity.this, R.raw.wronganswer);
                        optionclicklife.start();
                        optionclicklife.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                optionclicklife.release();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    soruhazirlama();

                } else if (life2.getVisibility() == View.VISIBLE && life3.getVisibility() == View.VISIBLE) {
                    life2.setVisibility(View.INVISIBLE);
                    try {
                        final MediaPlayer optionclicklife = MediaPlayer.create(TimechallengeActivity.this, R.raw.wronganswer);
                        optionclicklife.start();
                        optionclicklife.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                optionclicklife.release();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    soruhazirlama();

                } else {
                    life3.setVisibility(View.INVISIBLE);

                    /////////////////////////////////
                    try {

                        if (gamemusic != null) {

                            gamemusic.release();
                            gamemusic = null;

                        }
                        scoreboard();

                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }


                }

            }


        }.start();
    }

    public void scoreboard() {

        /////////////////////reklam///////////////////////////////

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        /////////////////////reklam///////////////////////////////


        sonuclar.setVisibility(View.VISIBLE);
        sonuclar.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.slide_in));
        sorugovde.setVisibility(View.GONE);

        final Button dogruView = (Button) findViewById(R.id.dogrusayisiTxtv);
        final Button yanlisView = (Button) findViewById(R.id.yanlissayisiTxtv);
        final Button sorusayisiView = (Button) findViewById(R.id.sorusayisiTxtv);
        final Button puanView = (Button) findViewById(R.id.toplampuanTxtv);
        final Button testcikisBtn = (Button) findViewById(R.id.testcikisBtn);
        RelativeLayout background = findViewById(R.id.background);


        background.setBackgroundResource(R.color.orange);


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
            animatorsoru.setObjectValues(0, sorucounter);
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


            final MediaPlayer countsound = MediaPlayer.create(TimechallengeActivity.this, R.raw.count1);
            countsound.start();
            CountDownTimer timer = new CountDownTimer(2000, 2000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound != null && countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();
                        testcikisBtn.setEnabled(true);

                    }
                }
            };
            timer.start();
            //media finished

        } else { //çıkış yaptır!!!!!!
            Toast.makeText(TimechallengeActivity.this, getString(R.string.toasthicsorucevaplamadın), Toast.LENGTH_SHORT).show();


            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(sorucounter));
            testcikisBtn.setEnabled(true);
        }

        /////////////////skor bilgileri burada///////////////////////////////

        Integer inttimesoru, inttimedogru, inttimemeyanlis, inttimescore, timescorekumulatif;
        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        inttimesoru = sp.getInt("timesoru", 0);
        inttimedogru = sp.getInt("timedogru", 0);
        inttimemeyanlis = sp.getInt("timeyanlis", 0);
        inttimescore = sp.getInt("timescore", 0);
        timescorekumulatif = sp.getInt("timescorekumulatif", 0);


        spEditor.putInt("timesoru", (sorucounter + inttimesoru));
        spEditor.putInt("timedogru", (dogrucounter + inttimedogru));
        spEditor.putInt("timeyanlis", (yanliscounter + inttimemeyanlis));
        spEditor.putInt("timescore", (scorecounter));
        spEditor.putInt("timescorekumulatif", (scorecounter + timescorekumulatif));
        spEditor.commit();


        timescorekumulatif = sp.getInt("timescorekumulatif", 0);

        //   Log.i("skorerdem",String.valueOf(timescorekumulatif));


    }

    public void soruhazirlama() {

        final Button sikaBtn = (Button) findViewById(R.id.sikaBtn);
        final Button sikbBtn = (Button) findViewById(R.id.sikbBtn);
        final Button sikcBtn = (Button) findViewById(R.id.sikcBtn);
        final Button sikdBtn = (Button) findViewById(R.id.sikdBtn);
        androidx.appcompat.widget.SwitchCompat toplamaswitch = findViewById(R.id.toplamaswitch);
        androidx.appcompat.widget.SwitchCompat cikarmaswitch = findViewById(R.id.cikarmaswitch);
        androidx.appcompat.widget.SwitchCompat bolmeswitch = findViewById(R.id.bolmeswitch);
        androidx.appcompat.widget.SwitchCompat carpmaswitch = findViewById(R.id.carpmaswitch);

        RelativeLayout alphalayout = (RelativeLayout) findViewById(R.id.alphalayout);
        LinearLayout sorulayout = (LinearLayout) findViewById(R.id.sorusayilarlayout);

        sikaBtn.setEnabled(false);
        sikbBtn.setEnabled(false);
        sikcBtn.setEnabled(false);
        sikdBtn.setEnabled(false);
        sikaBtn.setVisibility(View.VISIBLE);
        sikbBtn.setVisibility(View.VISIBLE);
        sikcBtn.setVisibility(View.VISIBLE);
        sikdBtn.setVisibility(View.VISIBLE);
        sorulayout.setVisibility(View.VISIBLE);


        alphalayout.setAlpha(1);
        isPaused = false;


        sikaBtn.setEnabled(true);
        sikbBtn.setEnabled(true);
        sikcBtn.setEnabled(true);
        sikdBtn.setEnabled(true);
        sikaBtn.setBackgroundResource(R.drawable.siklar);
        sikbBtn.setBackgroundResource(R.drawable.siklar);
        sikcBtn.setBackgroundResource(R.drawable.siklar);
        sikdBtn.setBackgroundResource(R.drawable.siklar);
        final RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
        final RelativeLayout background = (RelativeLayout) findViewById(R.id.background);
        TextView islemsembol = findViewById(R.id.islmesembol);
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
            sorugovde.setBackgroundResource(R.color.grey);
            background.setBackgroundResource(R.color.grey);
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
            sorugovde.setBackgroundResource(R.color.blue);
            background.setBackgroundResource(R.color.blue);
            islemsembol.setTextColor(Color.WHITE);
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
            sorugovde.setBackgroundResource(R.color.brown);
            background.setBackgroundResource(R.color.brown);
            islemsembol.setTextColor(Color.WHITE);
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
            if (level == 4) {
                if (gamemusic != null) {
                    gamemusic.release();
                    gamemusic = null;
                }
                gamemusic = MediaPlayer.create(TimechallengeActivity.this, R.raw.gamemusic2);
                gamemusic.start();
                gamemusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        gamemusic.start();
                    }
                });

            }
            level = 5;
            TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
            levelTxt.setText(getString(R.string.level) + " 5");
            sorugovde.setBackgroundResource(R.color.purple);
            background.setBackgroundResource(R.color.purple);
            islemsembol.setTextColor(Color.WHITE);

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
            sorugovde.setBackgroundResource(R.color.darkred);
            background.setBackgroundResource(R.color.darkred);
            islemsembol.setTextColor(Color.WHITE);
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
            sorugovde.setBackgroundResource(R.color.petrol);
            background.setBackgroundResource(R.color.petrol);
            islemsembol.setTextColor(Color.WHITE);
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
            sorugovde.setBackgroundResource(R.color.darkerblue);
            background.setBackgroundResource(R.color.darkerblue);
            islemsembol.setTextColor(Color.WHITE);
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
            sorugovde.setBackgroundResource(R.color.lightblack);
            background.setBackgroundResource(R.color.lightblack);
            islemsembol.setTextColor(Color.WHITE);
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
            sorugovde.setBackgroundResource(R.color.black);
            background.setBackgroundResource(R.color.black);
            islemsembol.setTextColor(Color.WHITE);
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

        if (dogrucounter > 50 && dogrucounter < 55) {
            level = 11;
            TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
            levelTxt.setText(getString(R.string.level) + " 11");
            sorugovde.setBackgroundResource(R.color.black);
            background.setBackgroundResource(R.color.black);
            islemsembol.setTextColor(Color.WHITE);
            if (dogrucounter == 51) {
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

        if (dogrucounter > 55 && dogrucounter < 60) {
            level = 12;
            TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
            levelTxt.setText(getString(R.string.level) + " 12");
            sorugovde.setBackgroundResource(R.color.black);
            background.setBackgroundResource(R.color.black);
            islemsembol.setTextColor(Color.WHITE);
            if (dogrucounter == 56) {
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

        if (dogrucounter > 60 && dogrucounter < 65) {
            level = 13;
            TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
            levelTxt.setText(getString(R.string.level) + " 13");
            sorugovde.setBackgroundResource(R.color.black);
            background.setBackgroundResource(R.color.black);
            islemsembol.setTextColor(Color.WHITE);
            if (dogrucounter == 61) {
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

        if (dogrucounter > 65 && dogrucounter < 70) {
            level = 14;
            TextView levelTxt = (TextView) findViewById(R.id.sorunumarasıTxtv);
            levelTxt.setText(getString(R.string.level) + " MAX");
            sorugovde.setBackgroundResource(R.color.black);
            background.setBackgroundResource(R.color.black);
            islemsembol.setTextColor(Color.WHITE);
            if (dogrucounter == 66) {
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
        if (level == 4) {
            kalansure = 15000;
            levelsure = 15000;
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
        if (level == 11) {
            kalansure = 3000;
            levelsure = 3000;
        }
        if (level > 11) {
            kalansure = 2000;
            levelsure = 2000;
        }


        Log.i("dogrusayisi", String.valueOf(dogrucounter));
        Log.i("levelsayisi", String.valueOf(level));

        gamecounter();


        sayi1View = (TextView) findViewById(R.id.sayi1Txtv);
        sayi2View = (TextView) findViewById(R.id.sayi2Txtv);
        Button sembol = (Button) findViewById(R.id.islmesembol);

        ArrayList<String> semboller = new ArrayList<>();

        if (toplamaswitch.isChecked()) {
            semboller.add("+");
        }
        if (cikarmaswitch.isChecked()) {
            semboller.add("-");
        }
        if (bolmeswitch.isChecked()) {
            semboller.add("÷");
        }
        if (carpmaswitch.isChecked()) {
            semboller.add("x");
        }

        //semboller.add("+");
        // semboller.add("-");
        // semboller.add("x");
        // semboller.add("÷");
        Collections.shuffle(semboller);
        sembol.setText(semboller.get(0));

        //Log.i("sembol",sembol.getText().toString());

        ///////////////////TOPLAMA////////////////////////////////

        if (sembol.getText() == "+") {
            sorupuani = 30;
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
            sikaBtn.setTag(siklar.get(0));
            sikbBtn.setText("" + siklar.get(1));
            sikbBtn.setTag(siklar.get(1));
            sikcBtn.setText("" + siklar.get(2));
            sikcBtn.setTag(siklar.get(2));
            sikdBtn.setText("" + siklar.get(3));
            sikdBtn.setTag(siklar.get(3));


        }

        //////////////////////TOPLAMA BİTTİ///////////////////////


        //////////////////////ÇIKARMA ///////////////////////

        if (sembol.getText() == "-") {
            sorupuani = 50;
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
            sikaBtn.setTag(siklar.get(0));
            sikbBtn.setText("" + siklar.get(1));
            sikbBtn.setTag(siklar.get(1));
            sikcBtn.setText("" + siklar.get(2));
            sikcBtn.setTag(siklar.get(2));
            sikdBtn.setText("" + siklar.get(3));
            sikdBtn.setTag(siklar.get(3));

        }

        //////////////////////ÇIKARMA BİTTİ ///////////////////////

        //////////////////////ÇARPMA ///////////////////////


        if (sembol.getText() == "x") {
            sorupuani = 75;
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
            sikaBtn.setTag(siklar.get(0));
            sikbBtn.setText("" + siklar.get(1));
            sikbBtn.setTag(siklar.get(1));
            sikcBtn.setText("" + siklar.get(2));
            sikcBtn.setTag(siklar.get(2));
            sikdBtn.setText("" + siklar.get(3));
            sikdBtn.setTag(siklar.get(3));
        }

        //////////////////////ÇARPMA BİTTİ///////////////////////

        //////////////////////BÖLME ///////////////////////

        if (sembol.getText() == "÷") {
            sorupuani = 100;
            Random random = new Random();
            sayi1 = random.nextInt(11 - 1 + 1) + 1;
            sayi2 = random.nextInt(11 - 1 + 1) + 1;

            int e = sayi1 * sayi2;

            sayi1View.setText(String.valueOf(e));
            sayi2View.setText(String.valueOf(sayi2));
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
            sikaBtn.setText(String.valueOf(siklar.get(0)));
            sikaBtn.setTag(String.valueOf(siklar.get(0)));
            sikbBtn.setText(String.valueOf(siklar.get(1)));
            sikbBtn.setTag(String.valueOf(siklar.get(1)));
            sikcBtn.setText(String.valueOf(siklar.get(2)));
            sikcBtn.setTag(String.valueOf(siklar.get(2)));
            sikdBtn.setText(String.valueOf(siklar.get(3)));
            sikdBtn.setTag(String.valueOf(siklar.get(3)));
        }


        ///////////////////////////////////////////TEKRAR SORU BİTTİ/////////////////


        Log.i("kalansure", String.valueOf(kalansure));
        Log.i("levelsure", String.valueOf(levelsure));
        Log.i("cevap", Integer.toString(cevap));
        Log.i("sika", sikaBtn.getTag().toString());
        Log.i("sikb", sikbBtn.getTag().toString());
        Log.i("sikc", sikcBtn.getTag().toString());
        Log.i("sikd", sikdBtn.getTag().toString());

    }

    public void onPause() {
        super.onPause();
        Button pause = (Button) findViewById(R.id.nextquestionBtn);
        Button sikaBtn = (Button) findViewById(R.id.sikaBtn);
        Button sikbBtn = (Button) findViewById(R.id.sikbBtn);
        Button sikcBtn = (Button) findViewById(R.id.sikcBtn);
        Button sikdBtn = (Button) findViewById(R.id.sikdBtn);
        LinearLayout sorulayout = (LinearLayout) findViewById(R.id.sorusayilarlayout);
        RelativeLayout alphalayout = (RelativeLayout) findViewById(R.id.alphalayout);
        sorugovde = findViewById(R.id.sorugovdelayout);
        RelativeLayout nextbuttons = findViewById(R.id.nextbutonslayout);

        //Log.i("deneme",pause.getText().toString());


        isPaused = true;
        if (sorugovde.getVisibility() == View.VISIBLE) {

            try {
                if (gamemusic != null && gamemusic.isPlaying()) {
                    gamemusic.pause();
                }
                pause.setText(getString(R.string.go));
                sikaBtn.setEnabled(false);
                sikbBtn.setEnabled(false);
                sikcBtn.setEnabled(false);
                sikdBtn.setEnabled(false);
                sikaBtn.setVisibility(View.INVISIBLE);
                sikbBtn.setVisibility(View.INVISIBLE);
                sikcBtn.setVisibility(View.INVISIBLE);
                sikdBtn.setVisibility(View.INVISIBLE);
                sorulayout.setVisibility(View.INVISIBLE);
                alphalayout.setAlpha((float) 0.4);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (gamemusic != null) {
            gamemusic.release();
            gamemusic = null;

            System.gc();
        }


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


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.checkbox_toplama:
                opt_toplama = isChecked;
                break;
            case R.id.checkbox_cikarma:
                opt_cikarma = isChecked;
                break;
            case R.id.checkbox_carpma:
                opt_carpma = isChecked;
                break;
            case R.id.checkbox_bolme:
                opt_bolme = isChecked;
                break;
        }

    }

    private void fadeIn_fadeOut_Animation(final View fadein, final View fadeout) {

        fadein.setAlpha(0f);
        fadein.setVisibility(View.VISIBLE);

        ObjectAnimator fadeOutAnim = ObjectAnimator.ofFloat(fadeout, View.ALPHA, 1f, 0f);

        ObjectAnimator fadeInAnim = ObjectAnimator.ofFloat(fadein, View.ALPHA, 0f, 1f);
        fadeInAnim.setInterpolator(new DecelerateInterpolator());
        fadeOutAnim.setInterpolator(new AccelerateInterpolator());

        AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fadeout.setVisibility(View.GONE);
                fadein.setVisibility(View.VISIBLE);
            }
        });


        mAnimationSet.setDuration(500);
        mAnimationSet.playTogether(fadeOutAnim, fadeInAnim);
        mAnimationSet.start();


    }
}
