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



import java.util.ArrayList;
import java.util.Random;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by erdem.salgin on 12.12.2017.
 */

public class RitmiksaymaActivity extends Activity {

    private AdView mAdView;

    Integer sayi1,sayi2,sayi3,sayi4,sayi5,sayi6;
    TextView sayi1View,sayi2View,sayi3View,sayi4View,sayi5View,sayi6View,cevapView,skorTxv,dogruView,yanlisView,sorusayisiView,soruView,puanView,bossayisi;
    Button sikaBtn,sikbBtn,sikcBtn,sikdBtn,artirBtn,eksiltBtn,cvpBtn,testcikisBtn;
    Integer answer;
    MediaPlayer optionclick,ticking;

    int questioncounter = 1;
    int cevapcounter;
    int scorecounter=0;
    int dogrucounter=0;
    int yanliscounter=0;
    int boscounter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ritmiklayout);

        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sayi1View = (TextView)findViewById(R.id.sayi1Txtv);
        sayi2View = (TextView)findViewById(R.id.sayi2Txtv);
        sayi3View = (TextView)findViewById(R.id.sayi3Txtv);
        sayi4View = (TextView)findViewById(R.id.sayi4Txtv);
        sayi5View = (TextView)findViewById(R.id.sayi5Txtv);
        sayi6View = (TextView)findViewById(R.id.sayi6Txtv);
        cevapView = (TextView)findViewById(R.id.cevapTxtv);

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
         //Log.i("dogrucevap",answer.toString());
    }
    //soru bitti

    public void artir (View view) {

        cevapView = (TextView)findViewById(R.id.cevapTxtv);
        artirBtn = (Button)findViewById(R.id.artitBtn);
        eksiltBtn = (Button)findViewById(R.id.eksiltBtn);


        Integer i = Integer.valueOf(cevapView.getText().toString());
        i++;
        //Log.i("artit",i.toString());
        cevapView.setText(""+i);
    }
    public void eksilt (View view) {

        cevapView = (TextView)findViewById(R.id.cevapTxtv);
        artirBtn = (Button)findViewById(R.id.artitBtn);
        eksiltBtn = (Button)findViewById(R.id.eksiltBtn);


        Integer i = Integer.valueOf(cevapView.getText().toString());
        i--;
        //Log.i("artit",i.toString());
        cevapView.setText(""+i);
    }

    public void cevapla(View view){
        cvpBtn = (Button)findViewById(R.id.cvpBtn);
        cevapView = (TextView)findViewById(R.id.cevapTxtv);
        skorTxv = (TextView)findViewById(R.id.skorTxv);
        sayi1View=(TextView)findViewById(R.id.sayi1Txtv);
        sayi2View=(TextView)findViewById(R.id.sayi2Txtv);
        dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(TextView)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(TextView)findViewById(R.id.sorusayisiTxtv);
        artirBtn = (Button)findViewById(R.id.artitBtn);
        eksiltBtn = (Button)findViewById(R.id.eksiltBtn);

        artirBtn.setEnabled(false);
        eksiltBtn.setEnabled(false);
        cvpBtn.setEnabled(false);

        cevapcounter++;
        sorusayisiView.setText("Cevaplanan Soru: " + Integer.toString(cevapcounter));

        Integer cvp = Integer.valueOf(cevapView.getText().toString());
        Integer a = Integer.valueOf(answer);
        //Log.i("cevap",cvp.toString());  Log.i("answer",a.toString());
        if (cvp==a) {

            optionclick = MediaPlayer.create(this,R.raw.rightanswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            cevapView.setBackgroundResource(R.drawable.siklarclicked);
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

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(RitmiksaymaActivity.this, WinAnimegifsActivity.class);
                    startActivity(i);
                }
            },1000);
            //Toast.makeText(RitmiksaymaActivity.this,"Bravo",Toast.LENGTH_SHORT).show();
        } else {
            cevapView.setBackgroundResource(R.drawable.hatalisik);
            cevapView.setTextColor(0xFFFFFFFF);

            optionclick = MediaPlayer.create(this,R.raw.wronganswer);
            optionclick.start();
            optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    optionclick.release();
                }
            });

            yanliscounter++;
            //Toast.makeText(RitmiksaymaActivity.this,"Zort",Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(RitmiksaymaActivity.this, LooseAnimeActivity.class);
                    startActivity(i);
                }
            },1000);
        }
    }
    public void nextquestion(View view) {
        soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        puanView = (TextView)findViewById(R.id.toplampuanTxtv);
        cevapView = (TextView)findViewById(R.id.cevapTxtv);

        cevapView.setBackgroundResource(R.drawable.siklar);
        cevapView.setTextColor(0xFF000000);




        questioncounter++;
        soruView.setText(getString(R.string.questionleft) +" "+ Integer.toString(questioncounter));




            LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
            sorugovde.startAnimation(AnimationUtils.loadAnimation(RitmiksaymaActivity.this, R.anim.fadein_out));

            artirBtn = (Button)findViewById(R.id.artitBtn);
            eksiltBtn = (Button)findViewById(R.id.eksiltBtn);
            cvpBtn=(Button)findViewById(R.id.cvpBtn);

            artirBtn.setEnabled(true);
            eksiltBtn.setEnabled(true);
            cvpBtn.setEnabled(true);

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

    }
    public void tamamla(View view) {

        dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(TextView)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(TextView)findViewById(R.id.sorusayisiTxtv);
        bossayisi=(TextView)findViewById(R.id.bossayisiTxtv);
        puanView = (TextView)findViewById(R.id.toplampuanTxtv);
        testcikisBtn = (Button)findViewById(R.id.testcikisBtn);



        LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
        LinearLayout nextbuttons = (LinearLayout)findViewById(R.id.nextbutonslayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);
        LinearLayout cikis = (LinearLayout)findViewById(R.id.cikislayout);
        final TextView bossayisi=(TextView)findViewById(R.id.bossayisiTxtv);
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
                    yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(animatoryanlis.getAnimatedValue()));
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
                    dogruView.setText(getString(R.string.rightanswers) + String.valueOf(animatordogru.getAnimatedValue()));
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
                    sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(animatorsoru.getAnimatedValue()));
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
//mediaplayer here
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
            Toast.makeText(RitmiksaymaActivity.this,getString(R.string.toasthicsorucevaplamadın),Toast.LENGTH_SHORT).show();
            boscounter=questioncounter;
            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(questioncounter));
            bossayisi.setText(getString(R.string.unanswered) + String.valueOf(questioncounter));
            testcikisBtn.setEnabled(true);

        }

    }

    public void cikis(View view) {
        Intent i = new Intent(RitmiksaymaActivity.this,HomeActivity.class);startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        Intent myIntent = new Intent(RitmiksaymaActivity.this, HomeActivity.class);
        myIntent.putExtra("ritmiksorusayisi", questioncounter);
        myIntent.putExtra("ritmikdogrusayisi", dogrucounter);
        myIntent.putExtra("ritmikyanlissayisi", yanliscounter);
        myIntent.putExtra("ritmikbossayisi", boscounter);
        startActivity(myIntent);
    }
    @Override
    public void onBackPressed() {


        LinearLayout sorugovde = (LinearLayout)findViewById(R.id.sorugovdelayout);
        if (sorugovde.getVisibility()==View.VISIBLE){
            Toast.makeText(RitmiksaymaActivity.this,getString(R.string.quittoast),Toast.LENGTH_SHORT).show();
        }

    }


}