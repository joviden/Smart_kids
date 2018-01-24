package com.smartkids;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.smartkids.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class TekCiftActivity extends AppCompatActivity {

    private AdView mAdView;





    int a =0;
    int balonsayisi = 0;
    int dogrubalon = 0;
    int hatalibalon =0;
    int scorecounter =0;


    @Override

    protected void onCreate(Bundle savedInstance) {


        super.onCreate(savedInstance);
        setContentView(R.layout.tekciftlayout);

        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RelativeLayout boardlayout = (RelativeLayout)findViewById(R.id.boardlayout);
        final RelativeLayout baslatlayout = (RelativeLayout)findViewById(R.id.baslatlayout);
        final LinearLayout sonuclarlayout = (LinearLayout)findViewById(R.id.sonuclarlayout);
        boardlayout.setVisibility(View.GONE);
        baslatlayout.setVisibility(View.VISIBLE);
        sonuclarlayout.setVisibility(View.GONE);




    }

    public void basla (View view) {

        final Button balon1 = (Button) findViewById(R.id.balonview1);
        final Button balon2 = (Button) findViewById(R.id.balonview2);
        final Button balon3 = (Button) findViewById(R.id.balonview3);
        final Button balon4 = (Button) findViewById(R.id.balonview4);
        final Button balon5 = (Button) findViewById(R.id.balonview5);
        final Button balon6 = (Button) findViewById(R.id.balonview6);
        final Button balon7 = (Button) findViewById(R.id.balonview7);
        final Button balon8 = (Button) findViewById(R.id.balonview8);
        final Button balon9 = (Button) findViewById(R.id.balonview9);
        final Button balon10 = (Button) findViewById(R.id.balonview10);
        final Button tekraroynaBtn = (Button)findViewById(R.id.tekraroynaBtn);
        final Button konularadonBtn = (Button)findViewById(R.id.testcikisBtn);
        final TextView toplambalonview = (TextView)findViewById(R.id.toplambalonTxtv);
        final TextView toplampuanview = (TextView)findViewById(R.id.toplampuanTxtv);
        final TextView dogrubalonview = (TextView)findViewById(R.id.dogrubalonTxtv);
        final TextView hatalibalonview = (TextView)findViewById(R.id.hatalibalonTxtv);
        final TextView patlatılanbalonview = (TextView)findViewById(R.id.patlatılanbalonTxtv);
        final TextView skorview = (TextView)findViewById(R.id.skorTxv);
        final LinearLayout sonuclarlayout = (LinearLayout)findViewById(R.id.sonuclarlayout);
        final RelativeLayout baslatlayout = (RelativeLayout)findViewById(R.id.baslatlayout);
        final RelativeLayout boardlayout = (RelativeLayout)findViewById(R.id.boardlayout);
        final Button sureTxv =findViewById(R.id.sureTxtv);
        final Button cikis = (Button) findViewById(R.id.cikisBtn);

        scorecounter=0;
        dogrubalon=0;
        hatalibalon=0;
        balonsayisi=0;



        skorview.setText(String.valueOf(scorecounter));


        baslatlayout.setVisibility(View.GONE);
        boardlayout.setVisibility(View.VISIBLE);
        sonuclarlayout.setVisibility(View.GONE);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;
        final float q = height+300;
        //Log.i("ekran x",String.valueOf(width));
        //Log.i("ekran y",String.valueOf(height));
        //Log.i("ekran q",String.valueOf(q));



        final ArrayList<Button> balonlar = new ArrayList<Button>();
        balonlar.add(balon1); balonlar.add(balon2);  balonlar.add(balon3);  balonlar.add(balon4);  balonlar.add(balon5);
        balonlar.add(balon6); balonlar.add(balon7);  balonlar.add(balon8);  balonlar.add(balon9);  balonlar.add(balon10);

        Collections.shuffle(balonlar);


        final Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)

                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {



                                              balon1.setY(q); balon2.setY(q); balon3.setY(q); balon4.setY(q); balon5.setY(q); balon6.setY(q); balon7.setY(q); balon8.setY(q); balon9.setY(q); balon10.setY(q);




                                              ArrayList<Integer> backgrounds = new ArrayList<Integer>();
                                              backgrounds.add(R.drawable.balon5); backgrounds.add(R.drawable.balon1); backgrounds.add(R.drawable.balon2); backgrounds.add(R.drawable.balon3);
                                              backgrounds.add(R.drawable.balon4); backgrounds.add(R.drawable.balon5); backgrounds.add(R.drawable.balon6); backgrounds.add(R.drawable.balon7);

                                              Collections.shuffle(backgrounds);



                                              if (a>9) {
                                                  a=0;

                                              }else {

                                                  Button hrkbalon = (Button) balonlar.get(a);
                                                  hrkbalon.setBackgroundResource(backgrounds.get(0));
                                                  hrkbalon.setVisibility(View.VISIBLE);


                                                  Random rnd = new Random();
                                                  int sayi = rnd.nextInt(0+100);
                                                  hrkbalon.setText(String.valueOf(sayi));


                                                  Log.i("Tekrar Sayısı",String.valueOf(a));

                                                  int[] location = new int[2];
                                                  hrkbalon.getLocationOnScreen(location);

                                                  int balonx = location[0];
                                                  int balony = location[1];
                                                  //Log.i("balon x",String.valueOf(balonx));
                                                  //Log.i("balon y",String.valueOf(balony));

                                                  //Log.i("balon1 konum",String.valueOf(balon1.getY()));
                                                  //Log.i("balon2 konum",String.valueOf(balon2.getY()));
                                                  //Log.i("balon3 konum",String.valueOf(balon3.getY()));
                                                  //Log.i("hrk konum",String.valueOf(balony));
                                                  //Log.i("hrk id",String.valueOf(hrkbalon.getId()));

                                                  Random random = new Random();
                                                  float x = random.nextInt(((width-300)-100)+100)+100;
                                                  hrkbalon.setX(x);

                                                  //Log.i(" set x",String.valueOf((int)x));


                                                  //balon1.setY(q); balon2.setY(q); balon3.setY(q); balon4.setY(q); balon5.setY(q); balon6.setY(q); balon7.setY(q); balon8.setY(q); balon9.setY(q); balon10.setY(q);


                                                  int duration = ((int)q*2/280)*1000;


                                                  // hrkbalon.animate().translationYBy(-height).setDuration(2500);



                                                  final ObjectAnimator mover = ObjectAnimator.ofFloat(hrkbalon, "translationY", 0,-(q+400));
                                                  mover.setDuration(6000);
                                                  mover.start();
                                                  //hrkbalon.setVisibility(View.VISIBLE);
                                                  hrkbalon.setEnabled(true);

                                                  a++;
                                                  balonsayisi++;


                                              }

                                          }
                                      });
                                  }
                              },
//Set how long before to start calling the TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                1200);



        final CountDownTimer gerisayim = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                sureTxv.setText("" + millisUntilFinished / 1000);

            }

            public void onFinish() {

                sureTxv.setText("süre bitti!");
                t.cancel();
                boardlayout.setVisibility(View.GONE);
                sonuclarlayout.setVisibility(View.VISIBLE);
                baslatlayout.setVisibility(View.GONE);
                ////////toplam balon animator
                ValueAnimator animator = new ValueAnimator();
                animator.setObjectValues(0, balonsayisi);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        toplambalonview.setText(getString(R.string.toplambalon) + String.valueOf(animation.getAnimatedValue()));
                    }
                });
                animator.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                animator.setDuration(3000);
                animator.start();
                //////////
                ////////////patlatılan balon
                ValueAnimator patlatılanbalon = new ValueAnimator();
                patlatılanbalon.setObjectValues(0, (dogrubalon+hatalibalon));
                patlatılanbalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        patlatılanbalonview.setText(getString(R.string.patlatilanbalon) + String.valueOf(animation.getAnimatedValue()));
                    }
                });
                patlatılanbalon.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                patlatılanbalon.setDuration(3000);
                patlatılanbalon.start();
                ///////////////////////
                ///////dogru balon animator
                ValueAnimator animatordogrubalon = new ValueAnimator();
                animatordogrubalon.setObjectValues(0,dogrubalon );
                animatordogrubalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        dogrubalonview.setText(getString(R.string.dogrubalon) + String.valueOf(animation.getAnimatedValue()));
                    }
                });
                animatordogrubalon.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                animatordogrubalon.setDuration(3000);
                animatordogrubalon.start();
                /////////////
                ///////////hatalı balon animator
                ValueAnimator animatorhatalibalon = new ValueAnimator();
                animatorhatalibalon.setObjectValues(0,hatalibalon );
                animatorhatalibalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        hatalibalonview.setText(getString(R.string.hatalibalon) + String.valueOf(animation.getAnimatedValue()));
                    }
                });
                animatorhatalibalon.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                animatorhatalibalon.setDuration(3000);
                animatorhatalibalon.start();
                //////////////////
                /////////////////toplampuan animator
                ValueAnimator toplampuananimator = new ValueAnimator();
                toplampuananimator.setObjectValues(0,scorecounter );
                toplampuananimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        toplampuanview.setText(getString(R.string.totalpoints) + String.valueOf(animation.getAnimatedValue()));
                    }
                });
                toplampuananimator.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                toplampuananimator.setDuration(3000);
                toplampuananimator.start();
                ///////////////////////
                //////////////media start
                final MediaPlayer countsound = MediaPlayer.create(TekCiftActivity.this,R.raw.count1);
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
                            konularadonBtn.setEnabled(true);
                        }
                    }
                };
                timer.start();
                //media finished
                //////////////////
            }
        }.start();

        ///////////////////////
        ///////////////////////
        //////////////////////

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.cancel();
                gerisayim.cancel();

                boardlayout.setVisibility(View.GONE);
                sonuclarlayout.setVisibility(View.VISIBLE);
                baslatlayout.setVisibility(View.GONE);

                ////////toplam balon animator
                ValueAnimator animator = new ValueAnimator();
                animator.setObjectValues(0, balonsayisi);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        toplambalonview.setText(getString(R.string.toplambalon) + String.valueOf(animation.getAnimatedValue()));

                    }
                });
                animator.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                animator.setDuration(3000);
                animator.start();
                //////////
                ////////////patlatılan balon
                ValueAnimator patlatılanbalon = new ValueAnimator();
                patlatılanbalon.setObjectValues(0, (dogrubalon+hatalibalon));
                patlatılanbalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        patlatılanbalonview.setText(getString(R.string.patlatilanbalon) + String.valueOf(animation.getAnimatedValue()));
                    }
                });
                patlatılanbalon.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                patlatılanbalon.setDuration(3000);
                patlatılanbalon.start();
                ///////////////////////
                ///////dogru balon animator
                ValueAnimator animatordogrubalon = new ValueAnimator();
                animatordogrubalon.setObjectValues(0,dogrubalon );
                animatordogrubalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        dogrubalonview.setText(getString(R.string.dogrubalon)+ String.valueOf(animation.getAnimatedValue()));
                    }
                });
                animatordogrubalon.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                animatordogrubalon.setDuration(3000);
                animatordogrubalon.start();
                /////////////
                ///////////hatalı balon animator
                ValueAnimator animatorhatalibalon = new ValueAnimator();
                animatorhatalibalon.setObjectValues(0,hatalibalon );
                animatorhatalibalon.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        hatalibalonview.setText(getString(R.string.hatalibalon)+ String.valueOf(animation.getAnimatedValue()));
                    }
                });
                animatorhatalibalon.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                animatorhatalibalon.setDuration(3000);
                animatorhatalibalon.start();
                //////////////////
                /////////////////toplampuan animator
                ValueAnimator toplampuananimator = new ValueAnimator();
                toplampuananimator.setObjectValues(0,scorecounter );
                toplampuananimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        toplampuanview.setText(getString(R.string.totalpoints) + String.valueOf(animation.getAnimatedValue()));
                    }
                });
                toplampuananimator.setEvaluator(new TypeEvaluator<Integer>() {
                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                        return Math.round(startValue + (endValue - startValue) * fraction);
                    }
                });
                toplampuananimator.setDuration(3000);
                toplampuananimator.start();
                ///////////////////////
                //////////////media start
                final MediaPlayer countsound = MediaPlayer.create(TekCiftActivity.this,R.raw.count1);
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
                            konularadonBtn.setEnabled(true);
                        }
                    }
                };timer.start();


            }
        });


        ////////////////////////
        ///////////////////////
        ///////////////////////
    }




    public void patlat1 (View view) {

        final Button balon1 = (Button) findViewById(R.id.balonview1);
        final TextView skorview = (TextView)findViewById(R.id.skorTxv);
        balon1.setEnabled(false);

        int logic1 = Integer.parseInt(balon1.getText().toString());

       // Log.i("balon1 clicked", String.valueOf(balon1.getId()));

        if ((logic1%2) == 0) {
            dogrubalon++;
            balon1.setBackgroundResource(R.drawable.booom);
            balon1.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.INVISIBLE);
                }
            }, 550);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {

                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    balon1.setVisibility(View.INVISIBLE);
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon1.setVisibility(View.GONE);



                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon1.setBackgroundResource(R.drawable.boombad);
            balon1.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon1.setVisibility(View.INVISIBLE);
                }
            }, 550);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    balon1.setVisibility(View.INVISIBLE);
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon1.setVisibility(View.GONE);
                }
            },500);

        }
    }

    public void patlat2 (View view) {
        final Button balon2 = (Button) findViewById(R.id.balonview2);
        int logic2 = Integer.parseInt(balon2.getText().toString());
       // Log.i("balon2 clicked", String.valueOf(balon2.getId()));
        balon2.setEnabled(false);


        if ((logic2%2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            balon2.setBackgroundResource(R.drawable.booom);
            balon2.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon2.setVisibility(View.INVISIBLE);
                }
            }, 550);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            ////media start
            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon2.setVisibility(View.GONE);
                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon2.setBackgroundResource(R.drawable.boombad);
            balon2.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon2.setVisibility(View.INVISIBLE);
                }
            }, 550);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon2.setVisibility(View.GONE);
                }
            },500);

        }
    }

    public void patlat3 (View view) {

        final Button balon3 = (Button) findViewById(R.id.balonview3);
        int logic3 = Integer.parseInt(balon3.getText().toString());
        //Log.i("balon3 clicked", String.valueOf(balon3.getId()));
        balon3.setEnabled(false);



        if ((logic3%2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            balon3.setBackgroundResource(R.drawable.booom);
            balon3.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon3.setVisibility(View.INVISIBLE);
                }
            }, 550);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();
            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon3.setVisibility(View.GONE);
                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon3.setBackgroundResource(R.drawable.boombad);
            balon3.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon3.setVisibility(View.INVISIBLE);
                }
            }, 550);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon3.setVisibility(View.GONE);
                }
            },500);

        }
    }

    public void patlat4 (View view) {

        final Button balon4 = (Button) findViewById(R.id.balonview4);
        //Log.i("balon4 clicked", String.valueOf(balon4.getId()));
        int logic4 = Integer.parseInt(balon4.getText().toString());
        balon4.setEnabled(false);

        if ((logic4 % 2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            balon4.setBackgroundResource(R.drawable.booom);
            balon4.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon4.setVisibility(View.INVISIBLE);
                }
            }, 550);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();


            ////media start
            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon4.setVisibility(View.GONE);
                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon4.setBackgroundResource(R.drawable.boombad);
            balon4.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon4.setVisibility(View.INVISIBLE);
                }
            }, 550);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon4.setVisibility(View.GONE);
                }
            },500);

        }
    }
    public void patlat5 (View view) {

        final Button balon5 = (Button) findViewById(R.id.balonview5);
        int logic5 = Integer.parseInt(balon5.getText().toString());
        balon5.setEnabled(false);

        if ((logic5 % 2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            balon5.setBackgroundResource(R.drawable.booom);
            balon5.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon5.setVisibility(View.INVISIBLE);
                }
            }, 550);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();


            ////media start
            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon5.setVisibility(View.GONE);
                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon5.setBackgroundResource(R.drawable.boombad);
            balon5.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon5.setVisibility(View.INVISIBLE);
                }
            }, 550);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon5.setVisibility(View.GONE);
                }
            },500);

        }
    }
    public void patlat6 (View view) {

        final Button balon6 = (Button) findViewById(R.id.balonview6);
        int logic6 = Integer.parseInt(balon6.getText().toString());
        balon6.setEnabled(false);

        if ((logic6 % 2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            balon6.setBackgroundResource(R.drawable.booom);
            balon6.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon6.setVisibility(View.INVISIBLE);
                }
            }, 550);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();


            ////media start
            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 10) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon6.setVisibility(View.GONE);
                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon6.setBackgroundResource(R.drawable.boombad);
            balon6.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon6.setVisibility(View.INVISIBLE);
                }
            }, 550);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();
            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon6.setVisibility(View.GONE);
                }
            },500);
        }
    }
    public void patlat7 (View view) {

        final Button balon7 = (Button) findViewById(R.id.balonview7);
        int logic7 = Integer.parseInt(balon7.getText().toString());
        balon7.setEnabled(false);

        if ((logic7 % 2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            balon7.setBackgroundResource(R.drawable.booom);
            balon7.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon7.setVisibility(View.INVISIBLE);
                }
            }, 550);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();


            ////media start
            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 10) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon7.setVisibility(View.GONE);
                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon7.setBackgroundResource(R.drawable.boombad);
            balon7.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon7.setVisibility(View.INVISIBLE);
                }
            }, 550);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon7.setVisibility(View.GONE);
                }
            },500);

        }
    }
    public void patlat8 (View view) {

        final Button balon8 = (Button) findViewById(R.id.balonview8);
        int logic8 = Integer.parseInt(balon8.getText().toString());
        balon8.setEnabled(false);

        if ((logic8 % 2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            balon8.setBackgroundResource(R.drawable.booom);
            balon8.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon8.setVisibility(View.INVISIBLE);
                }
            }, 550);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();


            ////media start
            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 10) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon8.setVisibility(View.GONE);
                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon8.setBackgroundResource(R.drawable.boombad);
            balon8.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon8.setVisibility(View.INVISIBLE);
                }
            }, 550);


            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon8.setVisibility(View.GONE);
                }
            },500);

        }
    }
    public void patlat9 (View view) {

        final Button balon9 = (Button) findViewById(R.id.balonview9);
        int logic9 = Integer.parseInt(balon9.getText().toString());
        balon9.setEnabled(false);

        if ((logic9 % 2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            balon9.setBackgroundResource(R.drawable.booom);
            balon9.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon9.setVisibility(View.INVISIBLE);
                }
            }, 550);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();


            ////media start
            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 10) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon9.setVisibility(View.GONE);
                }
            },500);
        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon9.setBackgroundResource(R.drawable.boombad);
            balon9.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon9.setVisibility(View.INVISIBLE);
                }
            }, 550);


            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();

            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon9.setVisibility(View.GONE);
                }
            },500);

        }
    }
    public void patlat10 (View view) {

        final Button balon10 = (Button) findViewById(R.id.balonview10);
        int logic10 = Integer.parseInt(balon10.getText().toString());
        balon10.setEnabled(false);

        if ((logic10 % 2) == 0) {
            dogrubalon++;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.siklarclicked);
            balon10.setBackgroundResource(R.drawable.booom);
            balon10.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon10.setVisibility(View.INVISIBLE);
                }
            }, 550);
            scorecounter = scorecounter+100;
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();


            ////media start
            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.rightanswer);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon10.setVisibility(View.GONE);
                }
            },500);

        }else {
            hatalibalon++;
            scorecounter = scorecounter-100;
            final TextView skorview = (TextView)findViewById(R.id.skorTxv);
            skorview.setBackgroundResource(R.drawable.hatalisik);
            balon10.setBackgroundResource(R.drawable.boombad);
            balon10.setText("");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    balon10.setVisibility(View.INVISIBLE);
                }
            }, 550);

            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(scorecounter-100, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    skorview.setText("Skor: " + String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animator.setDuration(1000);
            animator.start();
            // media

            final MediaPlayer countsound = MediaPlayer.create(this,R.raw.wrongtekcift);
            countsound.getDuration();
            countsound.start();
            countsound.setLooping(true);
            CountDownTimer timer = new CountDownTimer(1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // Nothing to do
                }

                @Override
                public void onFinish() {
                    if (countsound.isPlaying()) {
                        countsound.stop();
                        countsound.release();

                    }
                }
            };
            timer.start();
            //media finished
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balon10.setVisibility(View.GONE);
                }
            },500);

        }
    }
    public void tekraroyna (View view) {


        RelativeLayout boardlayout = (RelativeLayout)findViewById(R.id.boardlayout);
        final RelativeLayout baslatlayout = (RelativeLayout)findViewById(R.id.baslatlayout);
        final LinearLayout sonuclarlayout = (LinearLayout)findViewById(R.id.sonuclarlayout);
        boardlayout.setVisibility(View.GONE);
        baslatlayout.setVisibility(View.VISIBLE);
        sonuclarlayout.setVisibility(View.GONE);
    }
    public void konularadon (View view) {


        Intent i = new Intent(TekCiftActivity.this,HomeActivity.class);startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        Intent myIntent = new Intent(TekCiftActivity.this, HomeActivity.class);
        myIntent.putExtra("toplambalonsayisi",dogrubalon+hatalibalon);
        myIntent.putExtra("dogrubalonsayisi", dogrubalon);
        myIntent.putExtra("yanlisbalonsayisi", hatalibalon);
        startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {


        RelativeLayout baslatlayout = (RelativeLayout)findViewById(R.id.baslatlayout);
        if (baslatlayout.getVisibility()==View.VISIBLE){
            Toast.makeText(TekCiftActivity.this,getString(R.string.quittoast),Toast.LENGTH_SHORT).show();
        }


    }

}

