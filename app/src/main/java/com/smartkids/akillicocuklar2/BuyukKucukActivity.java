package com.smartkids.akillicocuklar2;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.ClipData;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;


/**
 * Created by joviden on 22.12.2017.
 */

public class BuyukKucukActivity extends AppCompatActivity {

  //  private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    TextView skorTxv,dogruView,yanlisView,sorusayisiView,soruView,puanView,bossayisi;
    Button kolayBtn,ortaBtn,zorBtn,cvpBtn,testcikisBtn,tamamlaBtn,nextquestionBtn;
    MediaPlayer cevapsound,countmusic;

    int hatali=0;
    int basarili=0;
    int rangedown=0;
    int rangeup=10;
    int questioncounter = 1;
    int cevapcounter;
    int scorecounter=0;
    int dogrucounter=0;
    int yanliscounter=0;
    int boscounter;
    int draggeditem,a,b,c,d,cvp1,cvp2,cvp3,cvp4,dogrucevap1,dogrucevap2,dogrucevap3,dogrucevap4;
    String name;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyukkucuklayout);

        /////////////////////reklamlar////////////////////////////

        /*
        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        */

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

        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);

        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        soruView.setText(getString(R.string.questionleft) +" 1");

        sorugovde.setVisibility(View.GONE);
        zorluk.setVisibility(View.VISIBLE);
        sonuclar.setVisibility(View.GONE);
//objects to drag!!!
        final TextView sayi1view = (TextView)findViewById(R.id.sayi1Txtv);
        final TextView sayi2view = (TextView)findViewById(R.id.sayi2Txtv);
        final TextView sayi3view = (TextView)findViewById(R.id.sayi3Txtv);
        final TextView sayi4view = (TextView)findViewById(R.id.sayi4Txtv);
// place drop on to!!!
        final TextView cvp1view = (TextView)findViewById(R.id.cvp1Txtv);
        final TextView cvp2view = (TextView)findViewById(R.id.cvp2Txtv);
        final TextView cvp3view = (TextView)findViewById(R.id.cvp3Txtv);
        final TextView cvp4view = (TextView)findViewById(R.id.cvp4Txtv);



//soruyu yazıyorum!

        ArrayList<Integer> dizi = new ArrayList<>();
        for (int i=rangedown;i<rangeup;i++) dizi.add(i);
        Collections.shuffle(dizi);


        Random random = new Random();

        a = dizi.get(0);
        b = dizi.get(1);
        c = dizi.get(2);
        d = dizi.get(3);
        //Log.i("sayi1",Integer.valueOf(a).toString());
        //Log.i("sayi2",Integer.valueOf(b).toString());
        //Log.i("sayi3",Integer.valueOf(c).toString());
        //Log.i("sayi4",Integer.valueOf(d).toString());
        sayi1view.setText(Integer.valueOf(a).toString());
        sayi2view.setText(Integer.valueOf(b).toString());
        sayi3view.setText(Integer.valueOf(c).toString());
        sayi4view.setText(Integer.valueOf(d).toString());




        ArrayList<Integer> dizisirali = new ArrayList<>();
        dizisirali.add(a);
        dizisirali.add(b);
        dizisirali.add(c);
        dizisirali.add(d);
        Collections.sort(dizisirali,Collections.<Integer>reverseOrder());
        //Log.i(" dizi",dizi.toString());
        //Log.i("sirali dizi",dizisirali.toString());
        dogrucevap1 = dizisirali.get(0);
        dogrucevap2 = dizisirali.get(1);
        dogrucevap3 = dizisirali.get(2);
        dogrucevap4 = dizisirali.get(3);



  try {


      sayi1view.setOnTouchListener(new OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {

              ClipData dragdata = ClipData.newPlainText("", "");
              View.DragShadowBuilder golge = new View.DragShadowBuilder(sayi1view);
              view.startDrag(dragdata, golge, null, 0);
              //Log.i("sayi1",dragdata.toString());
              draggeditem = Integer.valueOf(sayi1view.getText().toString());
              return true;

          }
      });
      sayi2view.setOnTouchListener(new OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {

              ClipData dragdata = ClipData.newPlainText("text", "");
              View.DragShadowBuilder golge = new View.DragShadowBuilder(sayi2view);
              view.startDrag(dragdata, golge, null, 0);
              draggeditem = Integer.valueOf(sayi2view.getText().toString());
              return true;

          }
      });
      sayi3view.setOnTouchListener(new OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {

              ClipData dragdata = ClipData.newPlainText("text", "");
              View.DragShadowBuilder golge = new View.DragShadowBuilder(sayi3view);
              view.startDrag(dragdata, golge, null, 0);
              draggeditem = Integer.valueOf(sayi3view.getText().toString());
              return true;

          }
      });
      sayi4view.setOnTouchListener(new OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent motionEvent) {

              ClipData dragdata = ClipData.newPlainText("datalabel", "text");
              View.DragShadowBuilder golge = new View.DragShadowBuilder(sayi4view);
              v.startDrag(dragdata, golge, null, 0);
              draggeditem = Integer.valueOf(sayi4view.getText().toString());
              return true;

          }
      });


      cvp1view.setOnDragListener(new View.OnDragListener() {
          @Override
          public boolean onDrag(View v, DragEvent dragEvent) {

              final int action = dragEvent.getAction();
              switch (action) {
                  case DragEvent.ACTION_DROP: {
                      View view1 = (View) dragEvent.getLocalState();
                      cvp1view.setText("" + draggeditem);
                      break;
                  }

                  case DragEvent.ACTION_DRAG_ENDED: {


                      break;
                  }
              }
              return true;
          }
      });

      cvp2view.setOnDragListener(new View.OnDragListener() {
          @Override
          public boolean onDrag(View v, DragEvent dragEvent) {

              final int action = dragEvent.getAction();
              switch (action) {
                  case DragEvent.ACTION_DROP: {
                      View view1 = (View) dragEvent.getLocalState();
                      cvp2view.setText("" + draggeditem);
                      break;
                  }

                  case DragEvent.ACTION_DRAG_ENDED: {
                      break;
                  }
              }
              return true;
          }
      });

      cvp3view.setOnDragListener(new View.OnDragListener() {
          @Override
          public boolean onDrag(View v, DragEvent dragEvent) {

              final int action = dragEvent.getAction();
              switch (action) {
                  case DragEvent.ACTION_DROP: {
                      View view1 = (View) dragEvent.getLocalState();
                      cvp3view.setText("" + draggeditem);
                      break;
                  }

                  case DragEvent.ACTION_DRAG_ENDED: {
                      break;
                  }
              }
              return true;
          }
      });

      cvp4view.setOnDragListener(new View.OnDragListener() {
          @Override
          public boolean onDrag(View v, DragEvent dragEvent) {

              final int action = dragEvent.getAction();
              switch (action) {
                  case DragEvent.ACTION_DROP: {
                      View view1 = (View) dragEvent.getLocalState();
                      cvp4view.setText("" + draggeditem);
                      break;
                  }

                  case DragEvent.ACTION_DRAG_ENDED: {
                      break;
                  }
              }
              return true;
          }
      });

  }catch (Exception e ) {
      e.printStackTrace();
  }



    }

    public void kolay (View view) {
        kolayBtn=(Button)findViewById(R.id.kolayBtn);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);

        zorluk.setVisibility(View.GONE);
        sorugovde.setVisibility(View.VISIBLE);
    }

    public void zor (View view){
        zorBtn = (Button)findViewById(R.id.zorBtn);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        rangeup=300;
        rangedown=200;
        zorluk.setVisibility(View.GONE);
        sorugovde.setVisibility(View.VISIBLE);
    }

    public void orta (View view) {
        ortaBtn= (Button)findViewById(R.id.ortaBtn);
        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        rangedown=10;
        rangeup=50;
        zorluk.setVisibility(View.GONE);
        sorugovde.setVisibility(View.VISIBLE);
    }

    public void nextquestion(View view) {




        ////////////////////////reklamlar//////////////////////////////////////////////////////////

        if (questioncounter==10 || questioncounter==20 || questioncounter==30 || questioncounter==40 ||questioncounter==50) {  if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } }

        /////////////////////////reklamlar//////////////////////////////////////////////////////////

        soruhazirla();

    }

    public void cevapla(View view){


        cvpBtn = (Button)findViewById(R.id.cvpBtn);
        tamamlaBtn=(Button)findViewById(R.id.tamamlaBtn);
        nextquestionBtn = findViewById(R.id.nextquestionBtn);

        skorTxv = (TextView)findViewById(R.id.skorTxv);

        dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(TextView)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(TextView)findViewById(R.id.sorusayisiTxtv);
        TextView cvp1view = (TextView)findViewById(R.id.cvp1Txtv);
        TextView cvp2view = (TextView)findViewById(R.id.cvp2Txtv);
        TextView cvp3view = (TextView)findViewById(R.id.cvp3Txtv);
        TextView cvp4view = (TextView)findViewById(R.id.cvp4Txtv);


        cvpBtn.setEnabled(false);
        tamamlaBtn.setEnabled(false);
        nextquestionBtn.setEnabled(false);

        if ((cvp1view.getText().toString().equals("?")) || (cvp2view.getText().toString().equals("?")) ||
                (cvp3view.getText().toString().equals("?")) || (cvp4view.getText().toString().equals("?"))) {
            Toast.makeText(BuyukKucukActivity.this,getString(R.string.toasttumsayılarıyerlestir),Toast.LENGTH_SHORT).show();
            cvpBtn.setEnabled(true);
        } else {

            cevapcounter++;
            sorusayisiView.setText("Cevaplanan Soru: " + Integer.toString(cevapcounter));

            cvp1=Integer.valueOf(cvp1view.getText().toString());
            cvp2=Integer.valueOf(cvp2view.getText().toString());
            cvp3=Integer.valueOf(cvp3view.getText().toString());
            cvp4=Integer.valueOf(cvp4view.getText().toString());




            if ((dogrucevap1==cvp1) && (dogrucevap2==cvp2) && (dogrucevap3==cvp3) && (dogrucevap4==cvp4)){

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        soruhazirla();
                    }
                }, 1100);

                try {


                    cevapsound = MediaPlayer.create(this, R.raw.rightanswer);
                    cevapsound.start();
                    cevapsound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            cevapsound.release();
                            tamamlaBtn.setEnabled(true);
                            nextquestionBtn.setEnabled(true);
                        }
                    });
                }catch (Exception e) {e.printStackTrace();}

                cvp1view.setBackgroundResource(R.drawable.siklarclicked);
                cvp2view.setBackgroundResource(R.drawable.siklarclicked);
                cvp3view.setBackgroundResource(R.drawable.siklarclicked);
                cvp4view.setBackgroundResource(R.drawable.siklarclicked);

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
                dogrucounter++;



            } else {
                cvp1view.setBackgroundResource(R.drawable.hatalisik);
                cvp2view.setBackgroundResource(R.drawable.hatalisik);
                cvp3view.setBackgroundResource(R.drawable.hatalisik);
                cvp4view.setBackgroundResource(R.drawable.hatalisik);

                try {
                    cevapsound = MediaPlayer.create(this, R.raw.wronganswer);
                    cevapsound.start();
                    cevapsound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            cevapsound.release();
                            tamamlaBtn.setEnabled(true);
                            nextquestionBtn.setEnabled(true);
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
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
                //Toast.makeText(RitmiksaymaActivity.this,"Zort",Toast.LENGTH_SHORT).show();


            }


        }



    }



    public void tamamla(View view) {

        /////////////////////reklam///////////////////////////////

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        /////////////////////reklam///////////////////////////////


        dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(TextView)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(TextView)findViewById(R.id.sorusayisiTxtv);
        bossayisi=(TextView)findViewById(R.id.bossayisiTxtv);
        puanView = (TextView)findViewById(R.id.toplampuanTxtv);
        testcikisBtn = (Button)findViewById(R.id.testcikisBtn);




        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);
        final TextView bossayisi=(TextView)findViewById(R.id.bossayisiTxtv);
        testcikisBtn = (Button)findViewById(R.id.testcikisBtn);


        testcikisBtn.setEnabled(false);

        sorugovde.setVisibility(View.GONE);
        sonuclar.setVisibility(View.VISIBLE);

        if (cevapcounter >0) {
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(0, scorecounter);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    puanView.setText(getString(R.string.totalpoints)  + String.valueOf(animation.getAnimatedValue()));
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
                    dogruView.setText(getString(R.string.rightanswers)  + String.valueOf(animatordogru.getAnimatedValue()));
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
                    sorusayisiView.setText(getString(R.string.totalquestion)  + String.valueOf(animatorsoru.getAnimatedValue()));
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
                    bossayisi.setText(getString(R.string.unanswered)  + String.valueOf(animatorbossoru.getAnimatedValue()));
                }
            });
            animatorbossoru.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animatorbossoru.setDuration(2000);
            animatorbossoru.start();
//mediaplayer here

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


        } else { //çıkış yaptır!!!!!!
            Toast.makeText(BuyukKucukActivity.this,getString(R.string.toasthicsorucevaplamadın),Toast.LENGTH_SHORT).show();
            boscounter=questioncounter;

            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(questioncounter));
            bossayisi.setText(getString(R.string.unanswered) + String.valueOf(questioncounter));
            testcikisBtn.setEnabled(true);
        }

        /////////////////skor bilgileri burada///////////////////////////////

        Integer intbuyukkucuksoru,intbuyukkucukdogru,intbuyukkucukyanlis,intbuyukkucukbos,intbuyukkucukkumulatif;
        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        intbuyukkucuksoru = sp.getInt("buyukkucuksoru",0);
        intbuyukkucukdogru= sp.getInt("buyukkucukdogru",0);
        intbuyukkucukyanlis = sp.getInt("buyukkucukyanlis",0);
        intbuyukkucukbos = sp.getInt("buyukkucukbos",0);
        intbuyukkucukkumulatif = sp.getInt("buyukkucukkumulatif",0);


        spEditor.putInt("buyukkucuksoru", (questioncounter+intbuyukkucuksoru));
        spEditor.putInt("buyukkucukdogru",(dogrucounter+intbuyukkucukdogru));
        spEditor.putInt("buyukkucukyanlis",(yanliscounter+intbuyukkucukyanlis));
        spEditor.putInt("buyukkucukbos",(boscounter+intbuyukkucukbos));
        spEditor.putInt("buyukkucukleader",(scorecounter));
        spEditor.putInt("buyukkucukkumulatif",(scorecounter+intbuyukkucukkumulatif));
        spEditor.commit();

        ////////////////////////SKOR BİLGİLERİ BİTTİ////////////////////////
       // Intent myIntent = new Intent(BuyukKucukActivity.this, MainmenuActivity.class);
      //  myIntent.putExtra("buyukkucukscore", scorecounter);
      //  startActivity(myIntent);

    }
    public void cikis(View view) {


       /* Intent i = new Intent(BuyukKucukActivity.this,MathgamesActivity.class);startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(i);this.finish();*/

    }

    @Override
    public void onBackPressed() {

        LinearLayout zorluk = (LinearLayout)findViewById(R.id.zorluklayout);
        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        if (sorugovde.getVisibility()==View.VISIBLE){
            Toast.makeText(BuyukKucukActivity.this,getString(R.string.quittoast),Toast.LENGTH_SHORT).show();
        }
        if (zorluk.getVisibility()==View.VISIBLE){
         //   Intent myIntent = new Intent(BuyukKucukActivity.this, MathgamesActivity.class);
        //    startActivity(myIntent);this.finish();

        }
    }

    public void soruhazirla() {

        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        puanView = (TextView)findViewById(R.id.toplampuanTxtv);
        final TextView sayi1view = (TextView)findViewById(R.id.sayi1Txtv);
        final TextView sayi2view = (TextView)findViewById(R.id.sayi2Txtv);
        final TextView sayi3view = (TextView)findViewById(R.id.sayi3Txtv);
        final TextView sayi4view = (TextView)findViewById(R.id.sayi4Txtv);
// place drop on to!!!
        final TextView cvp1view = (TextView)findViewById(R.id.cvp1Txtv);
        final TextView cvp2view = (TextView)findViewById(R.id.cvp2Txtv);
        final TextView cvp3view = (TextView)findViewById(R.id.cvp3Txtv);
        final TextView cvp4view = (TextView)findViewById(R.id.cvp4Txtv);

        questioncounter++;
        if (questioncounter==5 || questioncounter==10 || questioncounter==15 || questioncounter==20 ||questioncounter==25) {  if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } }
        soruView.setText(getString(R.string.questionleft) +" "+ Integer.toString(questioncounter));




        RelativeLayout sorugovde = findViewById(R.id.sorugovdelayout);
        sorugovde.startAnimation(AnimationUtils.loadAnimation(BuyukKucukActivity.this, R.anim.fadein_out));

        cvp1view.setBackgroundResource(R.drawable.sorunumarasi);
        cvp2view.setBackgroundResource(R.drawable.sorunumarasi);
        cvp3view.setBackgroundResource(R.drawable.sorunumarasi);
        cvp4view.setBackgroundResource(R.drawable.sorunumarasi);
        cvpBtn = (Button)findViewById(R.id.cvpBtn);
        cvp1view.setText("?");
        cvp2view.setText("?");
        cvp3view.setText("?");
        cvp4view.setText("?");

        cvpBtn.setEnabled(true);

//////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////

        ArrayList<Integer> dizi = new ArrayList<>();
        for (int i=rangedown;i<rangeup;i++) dizi.add(i);
        Collections.shuffle(dizi);


        Random random = new Random();

        a = dizi.get(0);
        b = dizi.get(1);
        c = dizi.get(2);
        d = dizi.get(3);
        //Log.i("sayi1",Integer.valueOf(a).toString());
        //Log.i("sayi2",Integer.valueOf(b).toString());
        //Log.i("sayi3",Integer.valueOf(c).toString());
        //Log.i("sayi4",Integer.valueOf(d).toString());
        sayi1view.setText(Integer.valueOf(a).toString());
        sayi2view.setText(Integer.valueOf(b).toString());
        sayi3view.setText(Integer.valueOf(c).toString());
        sayi4view.setText(Integer.valueOf(d).toString());

        ArrayList<Integer> dizisirali = new ArrayList<>();
        dizisirali.add(a);
        dizisirali.add(b);
        dizisirali.add(c);
        dizisirali.add(d);
        Collections.sort(dizisirali,Collections.<Integer>reverseOrder());
        //Log.i(" dizi",dizi.toString());
        //Log.i("sirali dizi",dizisirali.toString());
        dogrucevap1 = dizisirali.get(0);
        dogrucevap2 = dizisirali.get(1);
        dogrucevap3 = dizisirali.get(2);
        dogrucevap4 = dizisirali.get(3);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();
        if (cevapsound != null) {
            cevapsound.release();
            cevapsound = null;
        }
    }



}


