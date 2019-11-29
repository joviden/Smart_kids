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
import android.view.animation.LinearInterpolator;
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
import com.smartkids.akillicocuklar2.utils.Constants;
import com.smartkids.akillicocuklar2.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TimechallengeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private AlertDialog alertDialog;
    private CheckBox checkbox_toplama, checkbox_cikarma, checkbox_carpma, checkbox_bolme;
    private boolean opt_toplama = false;
    private boolean opt_cikarma = false;
    private boolean opt_carpma = false;
    private boolean opt_bolme = false;
    private AppCompatButton goBtn, sembolBtn, levelnumarasıBtn, sikA_Btn, sikB_Btn, sikC_Btn, sikD_Btn, sayi1Btn, sayi2Btn, tamamlaBtn, skorBtn, pauseBtn, progressTxt;
    private CountDownTimer oyunsure, gerisayimcounter;
    private ConstraintLayout girisLayout, soru_main, sorusayilarlayout, siklarlayout, nextbutonslayout;
    private boolean isPaused = false;
    private ImageView chronoImg, life1, life2, life3;
    private MediaPlayer gamemusic, intromusic, levelUp, dogru_cevap_music, hatali_cevap_music;
    private List<Integer> colors = new ArrayList<>();
    private List<Integer> level_sureler = new ArrayList<>();
    private List<String> semboller = new ArrayList<>();
    private String operator, difficulty;
    private int rangeup, rangedown, range2up, range2down;
    private int sayi1, sayi2, dogrucevap;
    private int question_point;
    private int level, sorucounter, progress, levelsure, kalansure;
    private boolean user_answered = false;
    private SharedPrefManager sharedPrefManager;
    private ProgressBar timebar;


    private int questioncounter = 0;
    private int cevapcounter = 0;
    private int scorecounter = 0;
    private int dogrucounter = 0;
    private int yanliscounter = 0;
    private int boscounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_challange_layout);

        sharedPrefManager = new SharedPrefManager(this);


        goBtn = findViewById(R.id.goBtn);
        girisLayout = findViewById(R.id.girisLayout);
        soru_main = findViewById(R.id.soru_main);
        chronoImg = findViewById(R.id.chronoImg);
        sembolBtn = findViewById(R.id.sembolBtn);
        levelnumarasıBtn = findViewById(R.id.sorunumarasıBtn);
        sorusayilarlayout = findViewById(R.id.sorusayilarlayout);
        siklarlayout = findViewById(R.id.siklarlayout);
        sikA_Btn = findViewById(R.id.sikaBtn);
        sikB_Btn = findViewById(R.id.sikbBtn);
        sikC_Btn = findViewById(R.id.sikcBtn);
        sikD_Btn = findViewById(R.id.sikdBtn);
        sayi1Btn = findViewById(R.id.sayi1Btn);
        sayi2Btn = findViewById(R.id.sayi2Btn);
        pauseBtn = findViewById(R.id.pauseBtn);
        skorBtn = findViewById(R.id.skorBtn);
        tamamlaBtn = findViewById(R.id.tamamlaBtn);
        life1 = findViewById(R.id.life1);
        life2 = findViewById(R.id.life2);
        life3 = findViewById(R.id.life3);
        progressTxt = findViewById(R.id.progressTxt);
        timebar = findViewById(R.id.pointsbar);
        nextbutonslayout = findViewById(R.id.nextbutonslayout);

        girisLayout.setVisibility(View.VISIBLE);
        soru_main.setVisibility(View.GONE);

        colors.add(R.color.orange);
        colors.add(R.color.orange);
        colors.add(R.color.grey);
        colors.add(R.color.blue);
        colors.add(R.color.brown);


        for (int i = 20; i > 0; i--) {
            level_sureler.add(1000 * i);
        }


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


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opt_toplama || opt_cikarma || opt_carpma || opt_bolme) {
                    alertDialog.dismiss();
                    showDifficultyAlert();


                } else {
                    Toast.makeText(TimechallengeActivity.this, getString(R.string.switchcheck), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void showDifficultyAlert() {
        View view = getLayoutInflater().inflate(R.layout.alert_difficulty, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setView(alertDialog.getLayoutInflater().inflate(R.layout.alert_difficulty, null));
        alertDialog.show();
        alertDialog.setCancelable(false);


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

        final Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale);
        final Animation animationAkrep = AnimationUtils.loadAnimation(this, R.anim.rotate_seconds);


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
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }

                            goBtn.startAnimation(scaleAnim);
                            chronoImg.startAnimation(animationAkrep);
                            intromusic = MediaPlayer.create(TimechallengeActivity.this, R.raw.ticktock);
                            intromusic.start();

                            intromusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    intromusic.start();

                                }
                            });

                            if (opt_toplama) {
                                semboller.add("+");
                            }
                            if (opt_cikarma) {
                                semboller.add("-");
                            }
                            if (opt_bolme) {
                                semboller.add("÷");
                            }
                            if (opt_carpma) {
                                semboller.add("x");
                            }
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
                difficulty = getString(R.string.easy);
                break;

            case R.id.ortaBtn:
                difficulty = getString(R.string.moderate);
                break;

            case R.id.zorBtn:
                difficulty = getString(R.string.hard);
                break;
        }


    }

    public void goGame(View view) {

        goBtn.clearAnimation();


        if (intromusic != null) {
            intromusic.release();
        }

        intromusic = MediaPlayer.create(TimechallengeActivity.this, R.raw.start);


        gerisayimcounter = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                goBtn.setText("" + millisUntilFinished / 1000);
                intromusic.start();

            }

            @Override
            public void onFinish() {

                fadeIn_fadeOut_Animation(soru_main, girisLayout);
                preparePreQuestion();

                if (intromusic != null) {
                    intromusic.release();
                }


                gamemusic = MediaPlayer.create(TimechallengeActivity.this, R.raw.gamemusic1);
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

    private void preparePreQuestion() {

        isPaused = false;
        siklarlayout.setAlpha(1);
        sorusayilarlayout.setAlpha(1);
        //   soru_main.startAnimation(AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.fadein_out));

        sikA_Btn.setBackgroundResource(R.drawable.siklar);
        sikB_Btn.setBackgroundResource(R.drawable.siklar);
        sikC_Btn.setBackgroundResource(R.drawable.siklar);
        sikD_Btn.setBackgroundResource(R.drawable.siklar);

        sikA_Btn.setEnabled(true);
        sikB_Btn.setEnabled(true);
        sikC_Btn.setEnabled(true);
        sikD_Btn.setEnabled(true);


        if (levelUp == null) {
            levelUp = MediaPlayer.create(TimechallengeActivity.this, R.raw.levelup);
        }


        level = (dogrucounter / 5) + 1;
        levelnumarasıBtn.setText(getString(R.string.level) + " " + level);
        if (dogrucounter != 0 && dogrucounter % 5 == 0) {
            levelUp.start();
        }
        levelsure = level_sureler.get(level - 1);
        kalansure = level_sureler.get(level - 1);

        gamecounter();

        prepareQuestion();


    }

    private void prepareQuestion() {
        Collections.shuffle(semboller);
        sembolBtn.setText(semboller.get(0));
        operator = semboller.get(0);

        if (operator.equals(getString(R.string.konulartoplama)))
            question_point = Constants.toplama_puan;

        if (operator.equals(getString(R.string.konularcikarma)))
            question_point = Constants.cikarma_puan;

        if (operator.equals(getString(R.string.konularcarpma)))
            question_point = Constants.carpma_puan;

        if (operator.equals(getString(R.string.konularbolme)))
            question_point = Constants.bolme_puan;

        if (difficulty.equals(getString(R.string.easy))) {
            if (operator.equals(getString(R.string.konulartoplama))) {
                rangedown = 0;
                rangeup = 10;
            } else if (operator.equals(getString(R.string.konularcikarma))) {
                rangedown = 0;
                rangeup = 10;
                range2down = 0;
                range2up = 10;
            } else if (operator.equals(getString(R.string.konularcarpma))) {
                rangedown = 0;
                rangeup = 10;
            } else if (operator.equals(getString(R.string.konularbolme))) {
                rangeup = 10;
                rangedown = 1;
                range2up = 5;
                range2down = 1;
            }

        } else if (difficulty.equals(getString(R.string.moderate))) {

            if (operator.equals(getString(R.string.konulartoplama))) {
                rangedown = 15;
                rangeup = 50;
            } else if (operator.equals(getString(R.string.konularcikarma))) {
                rangedown = 30;
                rangeup = 50;
                range2down = 8;
                range2up = 35;
            } else if (operator.equals(getString(R.string.konularcarpma))) {
                rangedown = 8;
                rangeup = 15;
            } else if (operator.equals(getString(R.string.konularbolme))) {
                rangeup = 15;
                rangedown = 9;
                range2up = 10;
                range2down = 4;
            }
            question_point = (int) Math.round(question_point * 1.20);


        } else if (difficulty.equals(getString(R.string.hard))) {

            if (operator.equals(getString(R.string.konulartoplama))) {
                rangedown = 50;
                rangeup = 100;
            } else if (operator.equals(getString(R.string.konularcikarma))) {
                rangedown = 50;
                rangeup = 100;
                range2down = 35;
                range2up = 75;
            } else if (operator.equals(getString(R.string.konularcarpma))) {
                rangedown = 12;
                rangeup = 25;
            } else if (operator.equals(getString(R.string.konularbolme))) {
                rangeup = 20;
                rangedown = 12;
                range2up = 15;
                range2down = 8;
            }
            question_point = (int) Math.round(question_point * 1.50);


        }


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

    }

    private void getNumbersToplama() {
        Random random = new Random();
        sayi1 = random.nextInt(rangeup - rangedown + 1) + rangedown;
        sayi2 = random.nextInt(rangeup - rangedown + 1) + rangedown;
        dogrucevap = sayi1 + sayi2;

        List<Integer> presiklar = new ArrayList<Integer>();
        for (int i = 1; i < 6; i++) {
            presiklar.add(dogrucevap + i);
            if (dogrucevap - i > 0)
                presiklar.add(dogrucevap - i);
        }
        presiklar.add(dogrucevap + 10);
        presiklar.remove(Integer.valueOf(dogrucevap));
        Collections.shuffle(presiklar);
        presiklar.add(random.nextInt(4 - 0) + 0, dogrucevap);
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

        Random random = new Random();
        Integer a = random.nextInt(rangeup - rangedown + 1) + rangedown;
        Integer b = random.nextInt(range2up - range2down + 1) + range2down;

        if (a < b) {
            sayi1 = b;
            sayi2 = a;
        } else {
            sayi1 = a;
            sayi2 = b;
        }

        dogrucevap = sayi1 - sayi2;

        List<Integer> presiklar = new ArrayList<Integer>();
        for (int i = 1; i < 6; i++) {
            presiklar.add(dogrucevap + i);
            if (dogrucevap - i > 0)
                presiklar.add(dogrucevap - i);
        }
        presiklar.add(dogrucevap + 10);
        presiklar.remove(Integer.valueOf(dogrucevap));
        Collections.shuffle(presiklar);
        presiklar.add(random.nextInt(4 - 0) + 0, dogrucevap);
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

    private void getNumbersCarpma() {

        Random random = new Random();
        sayi1 = random.nextInt(rangeup - rangedown) + rangedown;
        sayi2 = random.nextInt(rangeup - rangedown) + rangedown;
        dogrucevap = sayi1 * sayi2;

        List<Integer> presiklar = new ArrayList<Integer>();
        for (int i = 1; i < 6; i++) {
            presiklar.add(dogrucevap + i);
            if (dogrucevap - i > 0)
                presiklar.add(dogrucevap - i);
        }
        presiklar.add(dogrucevap + 10);
        if ((dogrucevap - 10) > 0) {
            presiklar.add(dogrucevap - 10);
        }
        presiklar.remove(Integer.valueOf(dogrucevap));
        Collections.shuffle(presiklar);
        presiklar.add(random.nextInt(4 - 0) + 0, dogrucevap);
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

    private void getNumbersBolme() {

        Random random = new Random();
        dogrucevap = random.nextInt(rangeup - rangedown) + rangedown;
        sayi2 = random.nextInt(range2up - range2down) + range2down;
        sayi1 = sayi2 * dogrucevap;

        List<Integer> presiklar = new ArrayList<Integer>();
        for (int i = 1; i < 6; i++) {
            presiklar.add(dogrucevap + i);
            if (dogrucevap - i > 0)
                presiklar.add(dogrucevap - i);
        }
        presiklar.add(dogrucevap + 10);
        if ((dogrucevap - 10) > 0) {
            presiklar.add(dogrucevap - 10);
        }
        presiklar.remove(Integer.valueOf(dogrucevap));
        Collections.shuffle(presiklar);
        presiklar.add(random.nextInt(4 - 0) + 0, dogrucevap);
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


    public void cevapla(View view) {

        user_answered = true;
        questioncounter++;
        cevapcounter++;
        oyunsure.cancel();

        Animation anime = AnimationUtils.loadAnimation(TimechallengeActivity.this, R.anim.bounce);
        levelnumarasıBtn.startAnimation(anime);


        view.setBackgroundResource(R.drawable.siklarclicked);
        sikA_Btn.setEnabled(false);
        sikB_Btn.setEnabled(false);
        sikC_Btn.setEnabled(false);
        sikD_Btn.setEnabled(false);
        tamamlaBtn.setEnabled(false);
        pauseBtn.setEnabled(false);


        if (view.getTag().toString().equals(String.valueOf(dogrucevap))) {

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
                        pauseBtn.setEnabled(true);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            scorecounter = scorecounter + question_point;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter - question_point, scorecounter);
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
                    preparePreQuestion();
                }
            }, 1100);


        } else {

            try {
                if (hatali_cevap_music == null) {
                    hatali_cevap_music = MediaPlayer.create(this, R.raw.wronganswer);
                }
                hatali_cevap_music.start();
                hatali_cevap_music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        tamamlaBtn.setEnabled(true);
                        pauseBtn.setEnabled(true);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            scorecounter = scorecounter - question_point;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter + question_point, scorecounter);
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
            if (Integer.parseInt(sikA_Btn.getText().toString()) == dogrucevap) {
                sikA_Btn.setBackgroundResource(R.drawable.siklarclicked);
            }
            if (Integer.parseInt(sikB_Btn.getText().toString()) == dogrucevap) {
                sikB_Btn.setBackgroundResource(R.drawable.siklarclicked);
            }
            if (Integer.parseInt(sikC_Btn.getText().toString()) == dogrucevap) {
                sikC_Btn.setBackgroundResource(R.drawable.siklarclicked);
            }
            if (Integer.parseInt(sikD_Btn.getText().toString()) == dogrucevap) {
                sikD_Btn.setBackgroundResource(R.drawable.siklarclicked);
            }


            if (yanliscounter == 1) {
                life1.setVisibility(View.INVISIBLE);
                preparePreQuestion();
            }
            if (yanliscounter == 2) {
                life2.setVisibility(View.INVISIBLE);
                preparePreQuestion();
            }
            if (yanliscounter == 3) {
                life3.setVisibility(View.INVISIBLE);

                if (gamemusic != null) {
                    gamemusic.release();
                    gamemusic = null;
                }
                scoreboard();

            }


        }


    }


    public void cikis() {


        if (gamemusic != null) {
            gamemusic.release();
        }

        if (intromusic != null) {
            intromusic.release();
        }

        if (levelUp != null) {
            levelUp.release();
        }


        if (dogru_cevap_music != null) {
            dogru_cevap_music.release();
        }
        if (hatali_cevap_music != null) {
            hatali_cevap_music.release();
        }

        final Intent i = new Intent(TimechallengeActivity.this, TrainingActivity.class);
        i.putExtra("four_operation", true);
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

    private void showPauseAlert() {



        Log.i("pausecheck", "paused");
        View view = getLayoutInflater().inflate(R.layout.alert_pause, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(TimechallengeActivity.this);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setView(alertDialog.getLayoutInflater().inflate(R.layout.alert_pause, null));
        alertDialog.setCancelable(false);
        alertDialog.show();

        AppCompatButton resumeBtn = alertDialog.findViewById(R.id.resumeBtn);
        AppCompatButton exitBtn = alertDialog.findViewById(R.id.exitBtn);


        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                gamecounter();
                if (gamemusic == null) {
                    gamemusic = MediaPlayer.create(TimechallengeActivity.this, R.raw.gamemusic1);

                } else {
                    gamemusic.start();
                }
                pauseBtn.setText(getString(R.string.pause));
                sikA_Btn.setEnabled(true);
                sikB_Btn.setEnabled(true);
                sikC_Btn.setEnabled(true);
                sikD_Btn.setEnabled(true);
                sikA_Btn.setVisibility(View.VISIBLE);
                sikB_Btn.setVisibility(View.VISIBLE);
                sikC_Btn.setVisibility(View.VISIBLE);
                sikD_Btn.setVisibility(View.VISIBLE);
                soru_main.setAlpha(1);
                sorusayilarlayout.setVisibility(View.VISIBLE);
                siklarlayout.setVisibility(View.VISIBLE);
                nextbutonslayout.setVisibility(View.VISIBLE);
                isPaused = false;
                tamamlaBtn.setEnabled(true);
                pauseBtn.setEnabled(true);

            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cikis();
            }
        });


    }


    public void pause(View view) {
        if (oyunsure != null) {
            oyunsure.cancel();
        }

        showPauseAlert();


        if (gamemusic != null && gamemusic.isPlaying()) {
            gamemusic.pause();
        }
        Log.i("kalansure", String.valueOf(kalansure));
        sikA_Btn.setEnabled(false);
        sikB_Btn.setEnabled(false);
        sikC_Btn.setEnabled(false);
        sikD_Btn.setEnabled(false);
        sorusayilarlayout.setVisibility(View.GONE);
        siklarlayout.setVisibility(View.GONE);
        nextbutonslayout.setVisibility(View.GONE);
        soru_main.setAlpha((float) 0.4);
        isPaused = true;
        tamamlaBtn.setEnabled(false);
        pauseBtn.setEnabled(false);


        /////////////////////reklam///////////////////////////////

        if (mInterstitialAd.isLoaded()) {
            //  mInterstitialAd.show();
        }

        /////////////////////reklam///////////////////////////////


    }



    @Override
    public void onBackPressed() {

        Toast.makeText(TimechallengeActivity.this, getString(R.string.quittoast), Toast.LENGTH_LONG).show();

    }


    public void gamecounter() {


        oyunsure = new CountDownTimer(kalansure, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                timebar.setMax(levelsure);
                kalansure = (int) millisUntilFinished;

                if (isPaused) {
                    cancel();

                } else {

                    progressTxt.setText(String.valueOf(millisUntilFinished / 1000));

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
                    preparePreQuestion();

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
                    prepareQuestion();

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

        soru_main.setBackgroundResource(R.color.orange);

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

            int success_percent = (dogrucounter * 100) / questioncounter;

            Log.i("success_percent", "Success:" + success_percent + " " + "Question:" + questioncounter);

            if (success_percent == 100) {

                learnedStampBtn.setVisibility(View.INVISIBLE);
                final Animation stampAnim = AnimationUtils.loadAnimation(this, R.anim.wordcompleted);
                stampAnim.setFillAfter(true);
                learnedStampBtn.startAnimation(stampAnim);
            }


            progressBar.setProgress(0);
            progressBar.setMax(100 * 1000);
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, success_percent * 1000);
            animation.setDuration(2000); // 3.5 second
            animation.setInterpolator(new LinearInterpolator());
            animation.start();

            ValueAnimator animator_percent = new ValueAnimator();
            animator_percent.setObjectValues(0, success_percent);
            animator_percent.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    progressTxt.setText("%" + String.valueOf(animation.getAnimatedValue()));
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

        sharedPrefManager.putIntegertoSP(operator + "soru", questioncounter + sharedPrefManager.getIntegerFromSP(operator + "soru", 0));

        sharedPrefManager.putIntegertoSP(operator + "dogru", dogrucounter + sharedPrefManager.getIntegerFromSP(operator + "dogru", 0));

        sharedPrefManager.putIntegertoSP(operator + "yanlis", yanliscounter + sharedPrefManager.getIntegerFromSP(operator + "yanlis", 0));

        sharedPrefManager.putIntegertoSP(operator + "bos", boscounter + sharedPrefManager.getIntegerFromSP(operator + "bos", 0));

        sharedPrefManager.putIntegertoSP(operator + "skor", scorecounter + sharedPrefManager.getIntegerFromSP(operator + "skor", 0));


        sharedPrefManager.putIntegertoSP("soru_total", questioncounter + sharedPrefManager.getIntegerFromSP("soru_total", 0));
        sharedPrefManager.putIntegertoSP("dogru_total", dogrucounter + sharedPrefManager.getIntegerFromSP("dogru_total", 0));
        sharedPrefManager.putIntegertoSP("yanlis_total", yanliscounter + sharedPrefManager.getIntegerFromSP("yanlis_total", 0));
        sharedPrefManager.putIntegertoSP("bos_total", boscounter + sharedPrefManager.getIntegerFromSP("bos_total", 0));
        sharedPrefManager.putIntegertoSP("skor_total", scorecounter + sharedPrefManager.getIntegerFromSP("skor_total", 0));


    }


    public void onPause() {
        super.onPause();


        isPaused = true;


        try {
            if (gamemusic != null && gamemusic.isPlaying()) {
                gamemusic.pause();
            }
            pauseBtn.setText(getString(R.string.go));
            sikA_Btn.setEnabled(false);
            sikB_Btn.setEnabled(false);
            sikC_Btn.setEnabled(false);
            sikD_Btn.setEnabled(false);
            sikA_Btn.setVisibility(View.INVISIBLE);
            sikB_Btn.setVisibility(View.INVISIBLE);
            sikC_Btn.setVisibility(View.INVISIBLE);
            sikD_Btn.setVisibility(View.INVISIBLE);

            soru_main.setAlpha((float) 0.4);

        } catch (Exception e) {
            e.printStackTrace();
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
