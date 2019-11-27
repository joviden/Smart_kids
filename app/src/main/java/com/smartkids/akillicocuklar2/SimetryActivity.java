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

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SimetryActivity extends AppCompatActivity {


    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private SharedPrefManager sharedPrefManager;
    private MediaPlayer dogru_cevap_music, hatali_cevap_music;
    private AppCompatButton tamamlaBtn, nextquestionBtn, skorBtn, answerBtn,sorunumarasiBtn;
    private ViewGroup viewGroup_question,viewGroup_answer;
    private AlertDialog alertDialog;
    private int difficulty_box_range_up = 0;
    private int difficulty_box_range_down = 0;
    private int question_point;
    private int question_box_size;

    private boolean user_answered;
    private Random random;
    private HashMap<Integer, List<Integer>> tags_hash = new HashMap<>();

    private List<Integer> tags = new ArrayList<>();
    private List<Integer> tags_temp = new ArrayList<>();
    private List<Integer> box_tags_question = new ArrayList<>();
    private List<Integer> boxes_clicked = new ArrayList<>();



    private MediaPlayer cevapmusic, countmusic;

    private int questioncounter = 0;
    private int cevapcounter = 0;
    private int scorecounter = 0;
    private int dogrucounter = 0;
    private int yanliscounter = 0;
    private int boscounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simetri_layout);
        sharedPrefManager = new SharedPrefManager(this);

        random = new Random();
        question_point=Constants.simetri_puan;

        nextquestionBtn = findViewById(R.id.nextquestionBtn);
        tamamlaBtn = findViewById(R.id.tamamlaBtn);
        answerBtn = findViewById(R.id.answerBtn);
        skorBtn = findViewById(R.id.skorBtn);
        sorunumarasiBtn = findViewById(R.id.sorunumarasıBtn);

        viewGroup_question = findViewById(R.id.sorugridlayout);
        viewGroup_answer = findViewById(R.id.cevapgridlayout);

        createAds();


        dogru_cevap_music = MediaPlayer.create(this, R.raw.rightanswer);
        hatali_cevap_music = MediaPlayer.create(this, R.raw.wronganswer);

        showDifficultyAlert();


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
                difficulty_box_range_up = 5;
                difficulty_box_range_down = 3;
                break;

            case R.id.ortaBtn:
                difficulty_box_range_up = 6;
                difficulty_box_range_down = 4;
                question_point = (int )Math.round(question_point*1.20);

                break;

            case R.id.zorBtn:
                difficulty_box_range_up = 8;
                difficulty_box_range_down = 5;
                question_point = (int) Math.round(question_point * 1.50);
                break;
        }


    }

    private void prepareQuestion() {

        user_answered = false;
        boxes_clicked.clear();
        answerBtn.setEnabled(true);

        sorunumarasiBtn.setText(getString(R.string.question) + " " + String.valueOf(questioncounter+1));

        if (questioncounter != 0 && questioncounter % 5 == 0) {
            if (mInterstitialAd.isLoaded()) {
                //  mInterstitialAd.show();
            }
        }


        for (int i = 0; i < 25; i++) {

            tags_temp.clear();

            if (i % 5 == 0) {
                tags_temp.add(i + 1);
                tags_temp.add(i - 5);
                tags_temp.add(i + 5);
                tags_temp.add(i - 4);
                tags_temp.add(i + 6);
            } else if ((i + 1) % 5 == 0) {
                tags_temp.add(i - 1);
                tags_temp.add(i - 6);
                tags_temp.add(i + 4);
                tags_temp.add(i - 5);
                tags_temp.add(i + 5);
            } else {
                tags_temp.add(i + 1);
                tags_temp.add(i - 1);
                tags_temp.add(i - 5);
                tags_temp.add(i + 5);
                tags_temp.add(i - 6);
                tags_temp.add(i - 4);
                tags_temp.add(i + 6);
                tags_temp.add(i + 4);
            }


            for (int j = 0; j < tags_temp.size(); j++) {

                if (tags_temp.get(j) >= 0 && tags_temp.get(j) < 24) {
                    tags.add(tags_temp.get(j));
                }
            }
            Log.i("checkNumbers2", tags.toString());
            Collections.shuffle(tags);
            List<Integer> temp = new ArrayList<>();
            temp.addAll(tags);

            tags_hash.put(i, temp);
            tags.clear();
        }

        for (Integer keyset : tags_hash.keySet()) {
            Log.i("checkNumbers3", tags_hash.get(keyset).toString());
        }

        int ilk_sayi = random.nextInt(25);
         question_box_size = random.nextInt(difficulty_box_range_up - difficulty_box_range_down) + difficulty_box_range_down;
        box_tags_question.clear();

        for (int i = 0; i < question_box_size; i++) {
            if (!box_tags_question.contains(tags_hash.get(ilk_sayi).get(0))) {
                box_tags_question.add(tags_hash.get(ilk_sayi).get(0));
                ilk_sayi = tags_hash.get(ilk_sayi).get(0);
            } else if (!box_tags_question.contains(tags_hash.get(ilk_sayi).get(1))) {
                box_tags_question.add(tags_hash.get(ilk_sayi).get(1));
                ilk_sayi = tags_hash.get(ilk_sayi).get(1);
            } else if (!box_tags_question.contains(tags_hash.get(ilk_sayi).get(2))) {
                box_tags_question.add(tags_hash.get(ilk_sayi).get(2));
                ilk_sayi = tags_hash.get(ilk_sayi).get(2);
            } else if (tags_hash.get(ilk_sayi).size() > 3 && !box_tags_question.contains(tags_hash.get(ilk_sayi).get(3))) {
                box_tags_question.add(tags_hash.get(ilk_sayi).get(3));
                ilk_sayi = tags_hash.get(ilk_sayi).get(3);
            }
        }
        Log.i("checkNumbers4", box_tags_question.toString());



        for (int i = 0; i < 25; i++) {
            ImageView box_question = viewGroup_question.findViewWithTag(String.valueOf(i));
            ImageView box_answer = viewGroup_answer.findViewWithTag(String.valueOf(i));
            box_question.setImageResource(R.drawable.simetrydef);
            box_answer.setImageResource(R.drawable.simetrydef);
        }

        for (int i = 0; i < box_tags_question.size(); i++) {
            ImageView box = viewGroup_question.findViewWithTag(String.valueOf(box_tags_question.get(i)));
            box.setImageResource(R.drawable.simetryclicked);
        }


    }


    public void nextquestion(View view) {

        if (!user_answered) {
            boscounter++;
            questioncounter++;
        }


        prepareQuestion();


    }

    public void boxClick (View view) {
        ImageView boxClicked = viewGroup_answer.findViewWithTag(view.getTag().toString());

        if (!boxes_clicked.contains(Integer.parseInt(view.getTag().toString()))) {
            boxClicked.setImageResource(R.drawable.simetryclicked);
            boxes_clicked.add(Integer.parseInt(view.getTag().toString()));
        }else {
            boxClicked.setImageResource(R.drawable.simetrydef);
            boxes_clicked.remove(boxes_clicked.indexOf(Integer.parseInt(view.getTag().toString())));
        }


    }


    public void cevapla(View view) {

        if (question_box_size!=boxes_clicked.size()) {
            Toast.makeText(getApplicationContext(), getText(R.string.simetrierror), Toast.LENGTH_SHORT).show();
        }else {
            user_answered = true;
            questioncounter++;
            cevapcounter++;
            answerBtn.setEnabled(false);

           if (box_tags_question.containsAll(boxes_clicked)) {
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
                       prepareQuestion();
                   }
               }, 1100);


               for (int i = 0; i <boxes_clicked.size() ; i++) {
                   ImageView box_question = viewGroup_question.findViewWithTag(String.valueOf(boxes_clicked.get(i)));
                   ImageView box_answer = viewGroup_answer.findViewWithTag(String.valueOf(boxes_clicked.get(i)));
                   box_question.setImageResource(R.drawable.simetrybravo);
                   box_answer.setImageResource(R.drawable.simetrybravo);
               }

            }else {



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
               yanliscounter++;

               for (int i = 0; i <box_tags_question.size() ; i++) {
                   ImageView box_question = viewGroup_question.findViewWithTag(String.valueOf(box_tags_question.get(i)));
                   ImageView box_answer = viewGroup_answer.findViewWithTag(String.valueOf(box_tags_question.get(i)));
                   box_question.setImageResource(R.drawable.simetrybravo);
                   box_answer.setImageResource(R.drawable.simetrybravo);
               }

           }



        }






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

        sharedPrefManager.putIntegertoSP(getString(R.string.konularsimetry)+"soru",questioncounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry)+"soru",0));

        sharedPrefManager.putIntegertoSP(getString(R.string.konularsimetry)+"dogru",dogrucounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry)+"dogru",0));

        sharedPrefManager.putIntegertoSP(getString(R.string.konularsimetry)+"yanlis",yanliscounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry)+"yanlis",0));

        sharedPrefManager.putIntegertoSP(getString(R.string.konularsimetry)+"bos",boscounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry)+"bos",0));

        sharedPrefManager.putIntegertoSP(getString(R.string.konularsimetry)+"skor",scorecounter+sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry)+"skor",0));



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

        final Intent i = new Intent(SimetryActivity.this, TrainingActivity.class);
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
        super.onBackPressed();
        Intent k = new Intent(SimetryActivity.this, TrainingActivity.class);
        k.putExtra("four_operation", true);
        startActivity(k);
        this.finish();
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
