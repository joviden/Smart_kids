package com.smartkids.akillicocuklar2;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class SimetryActivity extends AppCompatActivity {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    Button kolayBtn,zorBtn,ortaBtn,testcikisBtn,tamamlaBtn,nextquestionBtn;
    RelativeLayout sorugovdelayout;
    GridLayout sorugridlayout,cevapgridlayout;
    Integer width,height,x,y,z,t,q;
    MediaPlayer cevapmusic,countmusic;

    int questioncounter = 1;
    int cevapcounter;
    int scorecounter=0;
    int dogrucounter=0;
    int yanliscounter=0;
    int boscounter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simetrylayout);
        RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
        LinearLayout sonuclar = (LinearLayout)findViewById(R.id.sonuclarlayout);

        sorugovde.setVisibility(View.VISIBLE);
        sonuclar.setVisibility(View.GONE);

        /////////////////////reklamlar////////////////////////////


        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        TextView soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        soruView.setText(getString(R.string.questionleft) + " 1");


        Display display = getWindowManager().getDefaultDisplay();
         width = display.getWidth();
         height = display.getHeight();
         //Log.i("width",width.toString());

        ImageView img00 = (ImageView)findViewById(R.id.img00);ImageView img10 = (ImageView)findViewById(R.id.img10);ImageView img20 = (ImageView)findViewById(R.id.img20);ImageView img30 = (ImageView)findViewById(R.id.img30);
        ImageView img40 = (ImageView)findViewById(R.id.img40);ImageView img01 = (ImageView)findViewById(R.id.img01);ImageView img11 = (ImageView)findViewById(R.id.img11);ImageView img21 = (ImageView)findViewById(R.id.img21);
        ImageView img31 = (ImageView)findViewById(R.id.img31);ImageView img41 = (ImageView)findViewById(R.id.img41);ImageView img02 = (ImageView)findViewById(R.id.img02);ImageView img12 = (ImageView)findViewById(R.id.img12);
        ImageView img22 = (ImageView)findViewById(R.id.img22);ImageView img32 = (ImageView)findViewById(R.id.img32);ImageView img42 = (ImageView)findViewById(R.id.img42);ImageView img03 = (ImageView)findViewById(R.id.img03);
        ImageView img13 = (ImageView)findViewById(R.id.img13);ImageView img23 = (ImageView)findViewById(R.id.img23);ImageView img33 = (ImageView)findViewById(R.id.img33);ImageView img43 = (ImageView)findViewById(R.id.img43);
        ImageView img04 = (ImageView)findViewById(R.id.img04);ImageView img14 = (ImageView)findViewById(R.id.img14);ImageView img24 = (ImageView)findViewById(R.id.img24);ImageView img34 = (ImageView)findViewById(R.id.img34);
        ImageView img44 = (ImageView)findViewById(R.id.img44);

        ImageView ansimg00 = (ImageView)findViewById(R.id.ansimg00);ImageView ansimg10 = (ImageView)findViewById(R.id.ansimg10);ImageView ansimg20 = (ImageView)findViewById(R.id.ansimg20);ImageView ansimg30 = (ImageView)findViewById(R.id.ansimg30);
        ImageView ansimg40 = (ImageView)findViewById(R.id.ansimg40);ImageView ansimg01 = (ImageView)findViewById(R.id.ansimg01);ImageView ansimg11 = (ImageView)findViewById(R.id.ansimg11);ImageView ansimg21 = (ImageView)findViewById(R.id.ansimg21);
        ImageView ansimg31 = (ImageView)findViewById(R.id.ansimg31);ImageView ansimg41 = (ImageView)findViewById(R.id.ansimg41);ImageView ansimg02 = (ImageView)findViewById(R.id.ansimg02);ImageView ansimg12 = (ImageView)findViewById(R.id.ansimg12);
        ImageView ansimg22 = (ImageView)findViewById(R.id.ansimg22);ImageView ansimg32 = (ImageView)findViewById(R.id.ansimg32);ImageView ansimg42 = (ImageView)findViewById(R.id.ansimg42);ImageView ansimg03 = (ImageView)findViewById(R.id.ansimg03);
        ImageView ansimg13 = (ImageView)findViewById(R.id.ansimg13);ImageView ansimg23 = (ImageView)findViewById(R.id.ansimg23);ImageView ansimg33 = (ImageView)findViewById(R.id.ansimg33);ImageView ansimg43 = (ImageView)findViewById(R.id.ansimg43);
        ImageView ansimg04 = (ImageView)findViewById(R.id.ansimg04);ImageView ansimg14 = (ImageView)findViewById(R.id.ansimg14);ImageView ansimg24 = (ImageView)findViewById(R.id.ansimg24);ImageView ansimg34 = (ImageView)findViewById(R.id.ansimg34);
        ImageView ansimg44 = (ImageView)findViewById(R.id.ansimg44);  ImageView dividerbar = (ImageView)findViewById(R.id.dividerbar);


        ArrayList<ImageView> soru = new ArrayList<ImageView>();
        soru.add(img00);soru.add(img01);soru.add(img02);soru.add(img03);soru.add(img04);
        soru.add(img10);soru.add(img11);soru.add(img12);soru.add(img13);soru.add(img14);
        soru.add(img20);soru.add(img21);soru.add(img22);soru.add(img23);soru.add(img24);
        soru.add(img30);soru.add(img31);soru.add(img32);soru.add(img33);soru.add(img34);
        soru.add(img40);soru.add(img41);soru.add(img42);soru.add(img43);soru.add(img44);

        ArrayList cevap = new ArrayList();
        cevap.add(ansimg00);cevap.add(ansimg01);cevap.add(ansimg02);cevap.add(ansimg03);cevap.add(ansimg04);
        cevap.add(ansimg10);cevap.add(ansimg11);cevap.add(ansimg12);cevap.add(ansimg13);cevap.add(ansimg14);
        cevap.add(ansimg20);cevap.add(ansimg21);cevap.add(ansimg22);cevap.add(ansimg23);cevap.add(ansimg24);
        cevap.add(ansimg30);cevap.add(ansimg31);cevap.add(ansimg32);cevap.add(ansimg33);cevap.add(ansimg34);
        cevap.add(ansimg40);cevap.add(ansimg41);cevap.add(ansimg42);cevap.add(ansimg43);cevap.add(ansimg44);

        //Log.d("cevaplist",String.valueOf(cevap));
       // Log.d("sorulist",String.valueOf(soru));


        for (int i =0; i<25;i++) {

            ImageView imgsoru = (ImageView) soru.get(i);
            imgsoru.getLayoutParams().width=(width-60)/10;
            imgsoru.getLayoutParams().height=(width-60)/10;

            ImageView imgcevap = (ImageView) cevap.get(i);
            imgcevap.getLayoutParams().width=(width-60)/10;
            imgcevap.getLayoutParams().height=(width-60)/10;
        }

        dividerbar.getLayoutParams().height = (width-60)/2;
        /////////////////////////
        ///soruyu yazıyorum///
        /////////////////////////

        ArrayList<Integer> komsu0 = new ArrayList<Integer>();ArrayList<Integer> komsu1 = new ArrayList<Integer>();ArrayList<Integer> komsu2 = new ArrayList<Integer>();ArrayList<Integer> komsu3 = new ArrayList<Integer>();ArrayList<Integer> komsu4 = new ArrayList<Integer>();ArrayList<Integer> komsu5 = new ArrayList<Integer>();
        ArrayList<Integer> komsu6 = new ArrayList<Integer>();ArrayList<Integer> komsu7 = new ArrayList<Integer>();ArrayList<Integer> komsu8 = new ArrayList<Integer>();ArrayList<Integer> komsu9 = new ArrayList<Integer>();ArrayList<Integer> komsu10 = new ArrayList<Integer>();ArrayList<Integer> komsu11 = new ArrayList<Integer>();
        ArrayList<Integer> komsu12 = new ArrayList<Integer>();ArrayList<Integer> komsu13 = new ArrayList<Integer>();ArrayList<Integer> komsu14 = new ArrayList<Integer>();ArrayList<Integer> komsu15 = new ArrayList<Integer>();ArrayList<Integer> komsu16 = new ArrayList<Integer>();ArrayList<Integer> komsu17 = new ArrayList<Integer>();
        ArrayList<Integer> komsu18 = new ArrayList<Integer>();ArrayList<Integer> komsu19 = new ArrayList<Integer>();ArrayList<Integer> komsu20 = new ArrayList<Integer>();ArrayList<Integer> komsu21 = new ArrayList<Integer>();ArrayList<Integer> komsu22 = new ArrayList<Integer>();
        ArrayList<Integer> komsu23 = new ArrayList<Integer>();ArrayList<Integer> komsu24 = new ArrayList<Integer>();ArrayList<Integer> komsu25 = new ArrayList<Integer>();

        komsu0.add(5);komsu0.add(1);komsu0.add(6);
        komsu1.add(5);komsu1.add(6);komsu1.add(2);komsu1.add(7);
        komsu2.add(6);komsu2.add(7);komsu2.add(8);komsu2.add(3);komsu2.add(1);
        komsu3.add(4);komsu3.add(2);komsu3.add(7);komsu3.add(8);komsu3.add(9);
        komsu4.add(3);komsu4.add(9);komsu4.add(8);
        komsu5.add(0);komsu5.add(5);komsu5.add(1);komsu5.add(6);komsu5.add(10);komsu5.add(11);
        komsu6.add(0);komsu6.add(1);komsu6.add(2);komsu6.add(5);komsu6.add(7);komsu6.add(10);komsu6.add(11);komsu6.add(12);
        komsu7.add(1);komsu7.add(2);komsu7.add(3);komsu7.add(6);komsu7.add(8);komsu7.add(11);komsu7.add(12);komsu7.add(13);
        komsu8.add(2);komsu8.add(3);komsu8.add(4);komsu8.add(7);komsu8.add(9);komsu8.add(14);komsu8.add(13);komsu8.add(12);
        komsu9.add(3);komsu9.add(4);komsu9.add(8);komsu9.add(13);komsu9.add(14);
        komsu10.add(5);komsu10.add(6);komsu10.add(11);komsu10.add(16);komsu10.add(15);
        komsu11.add(5);komsu11.add(6);komsu11.add(7);komsu11.add(10);komsu11.add(12);komsu11.add(17);komsu11.add(16);komsu11.add(15);
        komsu12.add(6);komsu12.add(7);komsu12.add(8);komsu12.add(11);komsu12.add(13);komsu12.add(16);komsu12.add(17);komsu12.add(18);
        komsu13.add(7);komsu13.add(8);komsu13.add(9);komsu13.add(12);komsu13.add(14);komsu13.add(17);komsu13.add(18);komsu13.add(19);
        komsu14.add(8);komsu14.add(9);komsu14.add(13);komsu14.add(18);komsu14.add(19);
        komsu15.add(10);komsu15.add(11);komsu15.add(16);komsu15.add(20);komsu15.add(21);
        komsu16.add(10);komsu16.add(11);komsu16.add(12);komsu16.add(15);komsu16.add(17);komsu16.add(20);komsu16.add(21);komsu16.add(22);
        komsu17.add(11);komsu17.add(12);komsu17.add(13);komsu17.add(16);komsu17.add(18);komsu17.add(21);komsu17.add(22);komsu17.add(23);
        komsu18.add(12);komsu17.add(13);komsu17.add(14);komsu17.add(17);komsu17.add(19);komsu17.add(22);komsu17.add(23);komsu17.add(24);
        komsu19.add(13);komsu19.add(14);komsu19.add(18);komsu19.add(23);komsu19.add(24);
        komsu20.add(15);komsu20.add(16);komsu20.add(21);
        komsu21.add(15);komsu21.add(16);komsu21.add(17);komsu21.add(20);komsu21.add(22);
        komsu22.add(16);komsu22.add(17);komsu22.add(18);komsu22.add(21);komsu22.add(23);
        komsu23.add(17);komsu23.add(18);komsu23.add(19);komsu23.add(22);komsu23.add(24);
        komsu24.add(18);komsu24.add(19);komsu24.add(23);
        Collections.shuffle(komsu0);
        Collections.shuffle(komsu1);
        Collections.shuffle(komsu2);
        Collections.shuffle(komsu3);
        Collections.shuffle(komsu4);
        Collections.shuffle(komsu5);
        Collections.shuffle(komsu6);
        Collections.shuffle(komsu7);
        Collections.shuffle(komsu8);
        Collections.shuffle(komsu9);
        Collections.shuffle(komsu10);
        Collections.shuffle(komsu11);
        Collections.shuffle(komsu12);
        Collections.shuffle(komsu13);
        Collections.shuffle(komsu14);
        Collections.shuffle(komsu15);
        Collections.shuffle(komsu16);
        Collections.shuffle(komsu17);
        Collections.shuffle(komsu18);
        Collections.shuffle(komsu19);
        Collections.shuffle(komsu20);
        Collections.shuffle(komsu21);
        Collections.shuffle(komsu22);
        Collections.shuffle(komsu23);
        Collections.shuffle(komsu24);



        ArrayList<ArrayList<Integer>> soruparent = new ArrayList<ArrayList<Integer>>();
        soruparent.add(komsu0);soruparent.add(komsu1);soruparent.add(komsu2);soruparent.add(komsu3);soruparent.add(komsu4);soruparent.add(komsu5);soruparent.add(komsu6);soruparent.add(komsu7);soruparent.add(komsu8);soruparent.add(komsu9);
        soruparent.add(komsu10);soruparent.add(komsu11);soruparent.add(komsu12);soruparent.add(komsu13);soruparent.add(komsu14);soruparent.add(komsu15);soruparent.add(komsu16);soruparent.add(komsu17);soruparent.add(komsu18);soruparent.add(komsu19);
        soruparent.add(komsu20);soruparent.add(komsu21);soruparent.add(komsu22);soruparent.add(komsu23);soruparent.add(komsu24);

        Random rnd = new Random();
        x= rnd.nextInt(25-0)+0;



        ArrayList<Integer> sorupaketi = new ArrayList<Integer>();
        sorupaketi.addAll(soruparent.get(x));
        y=sorupaketi.get(0);

        sorupaketi.removeAll(soruparent.get(x));
        sorupaketi.addAll(soruparent.get(y));
        z=sorupaketi.get(0);
        if (z==x && sorupaketi.size()>1) {
            z=sorupaketi.get(1);
        }

        sorupaketi.removeAll(soruparent.get(y));
        sorupaketi.addAll(soruparent.get(z));
        t=sorupaketi.get(0);
        if (t==y && sorupaketi.size()>1) {
            t=sorupaketi.get(1);
        }

        sorupaketi.removeAll(soruparent.get(z));
        sorupaketi.addAll(soruparent.get(t));
        q=sorupaketi.get(0);
        if (q==z && sorupaketi.size()>1) {
            q=sorupaketi.get(1);
        }



        //Log.i("sayix",String.valueOf(x));
        //Log.i("sayiy",String.valueOf(y));
        //Log.i("sayiz",String.valueOf(z));
        //Log.i("sayit",String.valueOf(t));
        //Log.i("sayiq",String.valueOf(q));



        ImageView imgsoru1 = (ImageView)soru.get(x);
        imgsoru1.setBackgroundResource(R.drawable.simetryclicked);

        ImageView imgsoru2 = (ImageView)soru.get(y);
        imgsoru2.setBackgroundResource(R.drawable.simetryclicked);

        ImageView imgsoru3 = (ImageView)soru.get(z);
        imgsoru3.setBackgroundResource(R.drawable.simetryclicked);

        ImageView imgsoru4 = (ImageView)soru.get(t);
        imgsoru4.setBackgroundResource(R.drawable.simetryclicked);

        ImageView imgsoru5 = (ImageView)soru.get(q);
        imgsoru5.setBackgroundResource(R.drawable.simetryclicked);
        /////////////////////////
        //////////////////////////
        ////soruyu yazdım/////////
        //////////////////////////
        /////////////////////////
        ansimg00.setTag(1);ansimg10.setTag(1);ansimg20.setTag(1);ansimg30.setTag(1);ansimg40.setTag(1);
        ansimg01.setTag(1);ansimg11.setTag(1);ansimg21.setTag(1);ansimg31.setTag(1);ansimg41.setTag(1);
        ansimg02.setTag(1);ansimg12.setTag(1);ansimg22.setTag(1);ansimg32.setTag(1);ansimg42.setTag(1);
        ansimg03.setTag(1);ansimg13.setTag(1);ansimg23.setTag(1);ansimg33.setTag(1);ansimg43.setTag(1);
        ansimg04.setTag(1);ansimg14.setTag(1);ansimg24.setTag(1);ansimg34.setTag(1);ansimg44.setTag(1);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((Integer)view.getTag()==1){

                    view.setBackgroundResource(R.drawable.simetryclicked);
                    view.setTag(0);

                } else {
                    view.setBackgroundResource(R.drawable.simetrydef);
                    view.setTag(1);
                }
            }
        };

        ansimg00.setOnClickListener(listener);ansimg10.setOnClickListener(listener);ansimg20.setOnClickListener(listener);ansimg30.setOnClickListener(listener);ansimg40.setOnClickListener(listener);
        ansimg01.setOnClickListener(listener);ansimg11.setOnClickListener(listener);ansimg21.setOnClickListener(listener);ansimg31.setOnClickListener(listener);ansimg41.setOnClickListener(listener);
        ansimg02.setOnClickListener(listener);ansimg12.setOnClickListener(listener);ansimg22.setOnClickListener(listener);ansimg32.setOnClickListener(listener);ansimg42.setOnClickListener(listener);
        ansimg03.setOnClickListener(listener);ansimg13.setOnClickListener(listener);ansimg23.setOnClickListener(listener);ansimg33.setOnClickListener(listener);ansimg43.setOnClickListener(listener);
        ansimg04.setOnClickListener(listener);ansimg14.setOnClickListener(listener);ansimg24.setOnClickListener(listener);ansimg34.setOnClickListener(listener);ansimg44.setOnClickListener(listener);
    }



    public void cevapla (View view) {

        cevapcounter++;


        tamamlaBtn=(Button)findViewById(R.id.tamamlaBtn);
        nextquestionBtn = findViewById(R.id.nextquestionBtn);

        tamamlaBtn.setEnabled(false);
        nextquestionBtn.setEnabled(false);



        Button cevapla = (Button)findViewById(R.id.cvpBtn);
        cevapla.setEnabled(false);
        final TextView skorTxv = (TextView)findViewById(R.id.skorTxv);



        ImageView img00 = (ImageView)findViewById(R.id.img00);ImageView img10 = (ImageView)findViewById(R.id.img10);ImageView img20 = (ImageView)findViewById(R.id.img20);ImageView img30 = (ImageView)findViewById(R.id.img30);
        ImageView img40 = (ImageView)findViewById(R.id.img40);ImageView img01 = (ImageView)findViewById(R.id.img01);ImageView img11 = (ImageView)findViewById(R.id.img11);ImageView img21 = (ImageView)findViewById(R.id.img21);
        ImageView img31 = (ImageView)findViewById(R.id.img31);ImageView img41 = (ImageView)findViewById(R.id.img41);ImageView img02 = (ImageView)findViewById(R.id.img02);ImageView img12 = (ImageView)findViewById(R.id.img12);
        ImageView img22 = (ImageView)findViewById(R.id.img22);ImageView img32 = (ImageView)findViewById(R.id.img32);ImageView img42 = (ImageView)findViewById(R.id.img42);ImageView img03 = (ImageView)findViewById(R.id.img03);
        ImageView img13 = (ImageView)findViewById(R.id.img13);ImageView img23 = (ImageView)findViewById(R.id.img23);ImageView img33 = (ImageView)findViewById(R.id.img33);ImageView img43 = (ImageView)findViewById(R.id.img43);
        ImageView img04 = (ImageView)findViewById(R.id.img04);ImageView img14 = (ImageView)findViewById(R.id.img14);ImageView img24 = (ImageView)findViewById(R.id.img24);ImageView img34 = (ImageView)findViewById(R.id.img34);
        ImageView img44 = (ImageView)findViewById(R.id.img44);

        ImageView ansimg00 = (ImageView)findViewById(R.id.ansimg00);ImageView ansimg10 = (ImageView)findViewById(R.id.ansimg10);ImageView ansimg20 = (ImageView)findViewById(R.id.ansimg20);ImageView ansimg30 = (ImageView)findViewById(R.id.ansimg30);
        ImageView ansimg40 = (ImageView)findViewById(R.id.ansimg40);ImageView ansimg01 = (ImageView)findViewById(R.id.ansimg01);ImageView ansimg11 = (ImageView)findViewById(R.id.ansimg11);ImageView ansimg21 = (ImageView)findViewById(R.id.ansimg21);
        ImageView ansimg31 = (ImageView)findViewById(R.id.ansimg31);ImageView ansimg41 = (ImageView)findViewById(R.id.ansimg41);ImageView ansimg02 = (ImageView)findViewById(R.id.ansimg02);ImageView ansimg12 = (ImageView)findViewById(R.id.ansimg12);
        ImageView ansimg22 = (ImageView)findViewById(R.id.ansimg22);ImageView ansimg32 = (ImageView)findViewById(R.id.ansimg32);ImageView ansimg42 = (ImageView)findViewById(R.id.ansimg42);ImageView ansimg03 = (ImageView)findViewById(R.id.ansimg03);
        ImageView ansimg13 = (ImageView)findViewById(R.id.ansimg13);ImageView ansimg23 = (ImageView)findViewById(R.id.ansimg23);ImageView ansimg33 = (ImageView)findViewById(R.id.ansimg33);ImageView ansimg43 = (ImageView)findViewById(R.id.ansimg43);
        ImageView ansimg04 = (ImageView)findViewById(R.id.ansimg04);ImageView ansimg14 = (ImageView)findViewById(R.id.ansimg14);ImageView ansimg24 = (ImageView)findViewById(R.id.ansimg24);ImageView ansimg34 = (ImageView)findViewById(R.id.ansimg34);
        ImageView ansimg44 = (ImageView)findViewById(R.id.ansimg44);


        ArrayList<ImageView> soru = new ArrayList<ImageView>();
        soru.add(img00);soru.add(img01);soru.add(img02);soru.add(img03);soru.add(img04);
        soru.add(img10);soru.add(img11);soru.add(img12);soru.add(img13);soru.add(img14);
        soru.add(img20);soru.add(img21);soru.add(img22);soru.add(img23);soru.add(img24);
        soru.add(img30);soru.add(img31);soru.add(img32);soru.add(img33);soru.add(img34);
        soru.add(img40);soru.add(img41);soru.add(img42);soru.add(img43);soru.add(img44);


        ArrayList cevap = new ArrayList();
        cevap.add(ansimg40);
        cevap.add(ansimg41);
        cevap.add(ansimg42);
        cevap.add(ansimg43);
        cevap.add(ansimg44);
        cevap.add(ansimg30);
        cevap.add(ansimg31);
        cevap.add(ansimg32);
        cevap.add(ansimg33);
        cevap.add(ansimg34);
        cevap.add(ansimg20);
        cevap.add(ansimg21);
        cevap.add(ansimg22);
        cevap.add(ansimg23);
        cevap.add(ansimg24);
        cevap.add(ansimg10);
        cevap.add(ansimg11);
        cevap.add(ansimg12);
        cevap.add(ansimg13);
        cevap.add(ansimg14);
        cevap.add(ansimg00);
        cevap.add(ansimg01);
        cevap.add(ansimg02);
        cevap.add(ansimg03);
        cevap.add(ansimg04);

        ArrayList<Integer> useranswer = new ArrayList<>();


        int i;

        for (i=0;i<25;i++) {

            ImageView imgcevap = (ImageView) cevap.get(i);




            if ((Integer)imgcevap.getTag()==0) {
                useranswer.add(i);
            }



        }


        List<Integer> dogrucevaplarr = new ArrayList<Integer>();
        dogrucevaplarr.add(x);dogrucevaplarr.add(y);dogrucevaplarr.add(z);dogrucevaplarr.add(t);dogrucevaplarr.add(q);
        LinkedHashSet<Integer> lhs = new LinkedHashSet<>();
        lhs.addAll(dogrucevaplarr);
        dogrucevaplarr.clear();
        dogrucevaplarr.addAll(lhs);

        Collections.sort(useranswer);
        Collections.sort(dogrucevaplarr);


        //Log.d("useranswersayi",String.valueOf(useranswer));
        //Log.d("dogrucevaplarsayi",String.valueOf(dogrucevaplarr));



        if (useranswer.size()!=dogrucevaplarr.size()) {
            Toast.makeText(getApplicationContext(),getText(R.string.simetrierror),Toast.LENGTH_SHORT).show();
            cevapla.setEnabled(true);
        }else if (useranswer.containsAll(dogrucevaplarr)&&dogrucevaplarr.containsAll(useranswer)){

            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soruhazirlama();
                }
            }, 1100);

            dogrucounter++;
            int n;

            for (n=0;n<useranswer.size();n++) {

                ImageView imganswer = (ImageView) cevap.get(useranswer.get(n));
                imganswer.setBackgroundResource(R.drawable.simetrybravo);

                ImageView imgquestion = (ImageView) soru.get(dogrucevaplarr.get(n));
                imgquestion.setBackgroundResource(R.drawable.simetrybravo);
            }

            for (int d=0;d<25;d++) {

                ImageView imganswer = (ImageView) cevap.get(d);
                imganswer.setEnabled(false);
            }

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
            }catch (Exception e) {e.printStackTrace();}

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


        }else {

            yanliscounter++;

            for (int d=0;d<25;d++) {

                ImageView imganswer = (ImageView) cevap.get(d);
                imganswer.setEnabled(false);
            }

            for (int n=0;n<useranswer.size();n++) {

                ImageView imganswer = (ImageView) cevap.get(dogrucevaplarr.get(n));
                imganswer.setBackgroundResource(R.drawable.simetrybravo);

                ImageView imgquestion = (ImageView) soru.get(dogrucevaplarr.get(n));
                imgquestion.setBackgroundResource(R.drawable.simetrybravo);
            }
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
            }catch (Exception e ) {
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

        }






    }

    public void nextquestion (View view) {

        Button cevapla = (Button)findViewById(R.id.cvpBtn);
        cevapla.setEnabled(true);

        /////////////////////////reklamlar//////////////////////////////////////////////////////////

        if (questioncounter==10 || questioncounter==20 || questioncounter==30 || questioncounter==40 ||questioncounter==50) {  if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } }

        /////////////////////////reklamlar//////////////////////////////////////////////////////////

        soruhazirlama();


       //



    }

    public void tamamla (View view) {

        /////////////////////reklam///////////////////////////////

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        /////////////////////reklam///////////////////////////////

        TextView bossayisi=(TextView)findViewById(R.id.bossayisiTxtv);
        TextView sorusayisiView = (TextView)findViewById(R.id.sorusayisiTxtv);
        TextView dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        TextView yanlisView = (TextView) findViewById(R.id.yanlissayisiTxtv);
         testcikisBtn = findViewById(R.id.testcikisBtn);
        TextView puanView = (TextView)findViewById(R.id.toplampuanTxtv);
        LinearLayout sonuclarlayout = (LinearLayout) findViewById(R.id.sonuclarlayout);
        RelativeLayout sorugovdelayout = (RelativeLayout) findViewById(R.id.sorugovdelayout);


        dogruView=(TextView)findViewById(R.id.dogrusayisiTxtv);
        yanlisView=(TextView)findViewById(R.id.yanlissayisiTxtv);
        sorusayisiView=(TextView)findViewById(R.id.sorusayisiTxtv);
        bossayisi=(TextView)findViewById(R.id.bossayisiTxtv);
        puanView = (TextView)findViewById(R.id.toplampuanTxtv);

        testcikisBtn.setEnabled(false);
        sorugovdelayout.setVisibility(View.GONE);
        sonuclarlayout.setVisibility(View.VISIBLE);

        if (cevapcounter >0) {


            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(0, scorecounter);
            final TextView finalPuanView = puanView;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    finalPuanView.setText(getString(R.string.totalpoints) + String.valueOf(animation.getAnimatedValue()));
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
            final TextView finalYanlisView = yanlisView;
            animatoryanlis.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    finalYanlisView.setText(getString(R.string.wronganswers) + String.valueOf(animatoryanlis.getAnimatedValue()));
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
            final TextView finalDogruView = dogruView;
            animatordogru.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    finalDogruView.setText(getString(R.string.rightanswers) + String.valueOf(animatordogru.getAnimatedValue()));
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
            animatorsoru.setObjectValues(0, (questioncounter));
            final TextView finalSorusayisiView = sorusayisiView;
            animatorsoru.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    finalSorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(animatorsoru.getAnimatedValue()));
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
            boscounter= (questioncounter) - x;
            final ValueAnimator animatorbossoru = new ValueAnimator();
            animatorbossoru.setObjectValues(0, boscounter);
            final TextView finalBossayisi = bossayisi;
            animatorbossoru.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    finalBossayisi.setText(getString(R.string.unanswered) + String.valueOf(animatorbossoru.getAnimatedValue()));
                }
            });
            animatorbossoru.setEvaluator(new TypeEvaluator<Integer>() {
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return Math.round(startValue + (endValue - startValue) * fraction);
                }
            });
            animatorbossoru.setDuration(2000);
            animatorbossoru.start();

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

        }else {
            Toast.makeText(SimetryActivity.this,getString(R.string.toasthicsorucevaplamadın),Toast.LENGTH_SHORT).show();
            boscounter=questioncounter;
            puanView.setText(getString(R.string.totalpoints) + String.valueOf(0));
            yanlisView.setText(getString(R.string.wronganswers) + String.valueOf(0));
            dogruView.setText(getString(R.string.rightanswers) + String.valueOf(0));
            sorusayisiView.setText(getString(R.string.totalquestion) + String.valueOf(questioncounter));
            bossayisi.setText(getString(R.string.unanswered) + String.valueOf(questioncounter));
            testcikisBtn.setEnabled(true);
        }

        /////////////////skor bilgileri burada///////////////////////////////

        Integer intsimetrisoru,intsimetridogru,intsimetriyanlis,intsimetribos,intsimetrikumulatif;
        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        intsimetrisoru = sp.getInt("simetrisoru",0);
        intsimetridogru= sp.getInt("simetridogru",0);
        intsimetriyanlis = sp.getInt("simetriyanlis",0);
        intsimetribos = sp.getInt("simetribos",0);
        intsimetrikumulatif = sp.getInt("simetrikumulatif",0);


        spEditor.putInt("simetrisoru", (questioncounter+intsimetrisoru));
        spEditor.putInt("simetridogru",(dogrucounter+intsimetridogru));
        spEditor.putInt("simetriyanlis",(yanliscounter+intsimetriyanlis));
        spEditor.putInt("simetribos",(boscounter+intsimetribos));
        spEditor.putInt("simetrileader",(scorecounter));
        spEditor.putInt("simetrikumulatif",(scorecounter+intsimetrikumulatif));
        spEditor.commit();

        ////////////////////////SKOR BİLGİLERİ BİTTİ////////////////////////



    }

    public void cikis(View view) {

       // Intent myIntent = new Intent(SimetryActivity.this, MainmenuActivity.class);
       // myIntent.putExtra("simetriscore", scorecounter);
      //  startActivity(myIntent);


        Intent i = new Intent(SimetryActivity.this,MathgamesActivity.class);startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(i);this.finish();


    }

    @Override
    public void onBackPressed() {


        RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
        if (sorugovde.getVisibility()==View.VISIBLE){
            Toast.makeText(SimetryActivity.this,getString(R.string.quittoast),Toast.LENGTH_SHORT).show();
        }

    }

    public void soruhazirlama() {

        TextView soruView = (TextView)findViewById(R.id.sorunumarasıTxtv);
        questioncounter++;

        if (questioncounter==5||questioncounter==10||questioncounter==15||questioncounter==20||questioncounter==25||questioncounter==30) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        soruView.setText(getString(R.string.questionleft) +" "+ Integer.toString(questioncounter));


        Button cevapla = (Button)findViewById(R.id.cvpBtn);
        cevapla.setEnabled(true);

        /////////////////////////////////SORU///////////////////////////

        ImageView img00 = (ImageView)findViewById(R.id.img00);ImageView img10 = (ImageView)findViewById(R.id.img10);ImageView img20 = (ImageView)findViewById(R.id.img20);ImageView img30 = (ImageView)findViewById(R.id.img30);
        ImageView img40 = (ImageView)findViewById(R.id.img40);ImageView img01 = (ImageView)findViewById(R.id.img01);ImageView img11 = (ImageView)findViewById(R.id.img11);ImageView img21 = (ImageView)findViewById(R.id.img21);
        ImageView img31 = (ImageView)findViewById(R.id.img31);ImageView img41 = (ImageView)findViewById(R.id.img41);ImageView img02 = (ImageView)findViewById(R.id.img02);ImageView img12 = (ImageView)findViewById(R.id.img12);
        ImageView img22 = (ImageView)findViewById(R.id.img22);ImageView img32 = (ImageView)findViewById(R.id.img32);ImageView img42 = (ImageView)findViewById(R.id.img42);ImageView img03 = (ImageView)findViewById(R.id.img03);
        ImageView img13 = (ImageView)findViewById(R.id.img13);ImageView img23 = (ImageView)findViewById(R.id.img23);ImageView img33 = (ImageView)findViewById(R.id.img33);ImageView img43 = (ImageView)findViewById(R.id.img43);
        ImageView img04 = (ImageView)findViewById(R.id.img04);ImageView img14 = (ImageView)findViewById(R.id.img14);ImageView img24 = (ImageView)findViewById(R.id.img24);ImageView img34 = (ImageView)findViewById(R.id.img34);
        ImageView img44 = (ImageView)findViewById(R.id.img44);

        ImageView ansimg00 = (ImageView)findViewById(R.id.ansimg00);ImageView ansimg10 = (ImageView)findViewById(R.id.ansimg10);ImageView ansimg20 = (ImageView)findViewById(R.id.ansimg20);ImageView ansimg30 = (ImageView)findViewById(R.id.ansimg30);
        ImageView ansimg40 = (ImageView)findViewById(R.id.ansimg40);ImageView ansimg01 = (ImageView)findViewById(R.id.ansimg01);ImageView ansimg11 = (ImageView)findViewById(R.id.ansimg11);ImageView ansimg21 = (ImageView)findViewById(R.id.ansimg21);
        ImageView ansimg31 = (ImageView)findViewById(R.id.ansimg31);ImageView ansimg41 = (ImageView)findViewById(R.id.ansimg41);ImageView ansimg02 = (ImageView)findViewById(R.id.ansimg02);ImageView ansimg12 = (ImageView)findViewById(R.id.ansimg12);
        ImageView ansimg22 = (ImageView)findViewById(R.id.ansimg22);ImageView ansimg32 = (ImageView)findViewById(R.id.ansimg32);ImageView ansimg42 = (ImageView)findViewById(R.id.ansimg42);ImageView ansimg03 = (ImageView)findViewById(R.id.ansimg03);
        ImageView ansimg13 = (ImageView)findViewById(R.id.ansimg13);ImageView ansimg23 = (ImageView)findViewById(R.id.ansimg23);ImageView ansimg33 = (ImageView)findViewById(R.id.ansimg33);ImageView ansimg43 = (ImageView)findViewById(R.id.ansimg43);
        ImageView ansimg04 = (ImageView)findViewById(R.id.ansimg04);ImageView ansimg14 = (ImageView)findViewById(R.id.ansimg14);ImageView ansimg24 = (ImageView)findViewById(R.id.ansimg24);ImageView ansimg34 = (ImageView)findViewById(R.id.ansimg34);
        ImageView ansimg44 = (ImageView)findViewById(R.id.ansimg44);  ImageView dividerbar = (ImageView)findViewById(R.id.dividerbar);


        ArrayList<ImageView> soru = new ArrayList<ImageView>();
        soru.add(img00);soru.add(img01);soru.add(img02);soru.add(img03);soru.add(img04);
        soru.add(img10);soru.add(img11);soru.add(img12);soru.add(img13);soru.add(img14);
        soru.add(img20);soru.add(img21);soru.add(img22);soru.add(img23);soru.add(img24);
        soru.add(img30);soru.add(img31);soru.add(img32);soru.add(img33);soru.add(img34);
        soru.add(img40);soru.add(img41);soru.add(img42);soru.add(img43);soru.add(img44);

        ArrayList cevap = new ArrayList();
        cevap.add(ansimg00);cevap.add(ansimg01);cevap.add(ansimg02);cevap.add(ansimg03);cevap.add(ansimg04);
        cevap.add(ansimg10);cevap.add(ansimg11);cevap.add(ansimg12);cevap.add(ansimg13);cevap.add(ansimg14);
        cevap.add(ansimg20);cevap.add(ansimg21);cevap.add(ansimg22);cevap.add(ansimg23);cevap.add(ansimg24);
        cevap.add(ansimg30);cevap.add(ansimg31);cevap.add(ansimg32);cevap.add(ansimg33);cevap.add(ansimg34);
        cevap.add(ansimg40);cevap.add(ansimg41);cevap.add(ansimg42);cevap.add(ansimg43);cevap.add(ansimg44);

        ArrayList<Integer> komsu0 = new ArrayList<Integer>();ArrayList<Integer> komsu1 = new ArrayList<Integer>();ArrayList<Integer> komsu2 = new ArrayList<Integer>();ArrayList<Integer> komsu3 = new ArrayList<Integer>();ArrayList<Integer> komsu4 = new ArrayList<Integer>();ArrayList<Integer> komsu5 = new ArrayList<Integer>();
        ArrayList<Integer> komsu6 = new ArrayList<Integer>();ArrayList<Integer> komsu7 = new ArrayList<Integer>();ArrayList<Integer> komsu8 = new ArrayList<Integer>();ArrayList<Integer> komsu9 = new ArrayList<Integer>();ArrayList<Integer> komsu10 = new ArrayList<Integer>();ArrayList<Integer> komsu11 = new ArrayList<Integer>();
        ArrayList<Integer> komsu12 = new ArrayList<Integer>();ArrayList<Integer> komsu13 = new ArrayList<Integer>();ArrayList<Integer> komsu14 = new ArrayList<Integer>();ArrayList<Integer> komsu15 = new ArrayList<Integer>();ArrayList<Integer> komsu16 = new ArrayList<Integer>();ArrayList<Integer> komsu17 = new ArrayList<Integer>();
        ArrayList<Integer> komsu18 = new ArrayList<Integer>();ArrayList<Integer> komsu19 = new ArrayList<Integer>();ArrayList<Integer> komsu20 = new ArrayList<Integer>();ArrayList<Integer> komsu21 = new ArrayList<Integer>();ArrayList<Integer> komsu22 = new ArrayList<Integer>();
        ArrayList<Integer> komsu23 = new ArrayList<Integer>();ArrayList<Integer> komsu24 = new ArrayList<Integer>();ArrayList<Integer> komsu25 = new ArrayList<Integer>();

        komsu0.add(5);komsu0.add(1);komsu0.add(6);
        komsu1.add(5);komsu1.add(6);komsu1.add(2);komsu1.add(7);
        komsu2.add(6);komsu2.add(7);komsu2.add(8);komsu2.add(3);komsu2.add(1);
        komsu3.add(4);komsu3.add(2);komsu3.add(7);komsu3.add(8);komsu3.add(9);
        komsu4.add(3);komsu4.add(9);komsu4.add(8);
        komsu5.add(0);komsu5.add(5);komsu5.add(1);komsu5.add(6);komsu5.add(10);komsu5.add(11);
        komsu6.add(0);komsu6.add(1);komsu6.add(2);komsu6.add(5);komsu6.add(7);komsu6.add(10);komsu6.add(11);komsu6.add(12);
        komsu7.add(1);komsu7.add(2);komsu7.add(3);komsu7.add(6);komsu7.add(8);komsu7.add(11);komsu7.add(12);komsu7.add(13);
        komsu8.add(2);komsu8.add(3);komsu8.add(4);komsu8.add(7);komsu8.add(9);komsu8.add(14);komsu8.add(13);komsu8.add(12);
        komsu9.add(3);komsu9.add(4);komsu9.add(8);komsu9.add(13);komsu9.add(14);
        komsu10.add(5);komsu10.add(6);komsu10.add(11);komsu10.add(16);komsu10.add(15);
        komsu11.add(5);komsu11.add(6);komsu11.add(7);komsu11.add(10);komsu11.add(12);komsu11.add(17);komsu11.add(16);komsu11.add(15);
        komsu12.add(6);komsu12.add(7);komsu12.add(8);komsu12.add(11);komsu12.add(13);komsu12.add(16);komsu12.add(17);komsu12.add(18);
        komsu13.add(7);komsu13.add(8);komsu13.add(9);komsu13.add(12);komsu13.add(14);komsu13.add(17);komsu13.add(18);komsu13.add(19);
        komsu14.add(8);komsu14.add(9);komsu14.add(13);komsu14.add(18);komsu14.add(19);
        komsu15.add(10);komsu15.add(11);komsu15.add(16);komsu15.add(20);komsu15.add(21);
        komsu16.add(10);komsu16.add(11);komsu16.add(12);komsu16.add(15);komsu16.add(17);komsu16.add(20);komsu16.add(21);komsu16.add(22);
        komsu17.add(11);komsu17.add(12);komsu17.add(13);komsu17.add(16);komsu17.add(18);komsu17.add(21);komsu17.add(22);komsu17.add(23);
        komsu18.add(12);komsu17.add(13);komsu17.add(14);komsu17.add(17);komsu17.add(19);komsu17.add(22);komsu17.add(23);komsu17.add(24);
        komsu19.add(13);komsu19.add(14);komsu19.add(18);komsu19.add(23);komsu19.add(24);
        komsu20.add(15);komsu20.add(16);komsu20.add(21);
        komsu21.add(15);komsu21.add(16);komsu21.add(17);komsu21.add(20);komsu21.add(22);
        komsu22.add(16);komsu22.add(17);komsu22.add(18);komsu22.add(21);komsu22.add(23);
        komsu23.add(17);komsu23.add(18);komsu23.add(19);komsu23.add(22);komsu23.add(24);
        komsu24.add(18);komsu24.add(19);komsu24.add(23);
        Collections.shuffle(komsu0);
        Collections.shuffle(komsu1);
        Collections.shuffle(komsu2);
        Collections.shuffle(komsu3);
        Collections.shuffle(komsu4);
        Collections.shuffle(komsu5);
        Collections.shuffle(komsu6);
        Collections.shuffle(komsu7);
        Collections.shuffle(komsu8);
        Collections.shuffle(komsu9);
        Collections.shuffle(komsu10);
        Collections.shuffle(komsu11);
        Collections.shuffle(komsu12);
        Collections.shuffle(komsu13);
        Collections.shuffle(komsu14);
        Collections.shuffle(komsu15);
        Collections.shuffle(komsu16);
        Collections.shuffle(komsu17);
        Collections.shuffle(komsu18);
        Collections.shuffle(komsu19);
        Collections.shuffle(komsu20);
        Collections.shuffle(komsu21);
        Collections.shuffle(komsu22);
        Collections.shuffle(komsu23);
        Collections.shuffle(komsu24);

        ////////////////

        for (int i =0; i<25;i++) {


            ImageView imgcevap = (ImageView) cevap.get(i);
            imgcevap.setBackgroundResource(R.drawable.simetrydef);
            imgcevap.setTag(1);


            ImageView imgsoru = (ImageView) soru.get(i);
            imgsoru.setBackgroundResource(R.drawable.simetrydef);
        }

        for (int d=0;d<25;d++) {

            ImageView imganswer = (ImageView) cevap.get(d);
            imganswer.setEnabled(true);
        }

        ///////////////////
        RelativeLayout sorugovde = (RelativeLayout) findViewById(R.id.sorugovdelayout);
        sorugovde.startAnimation(AnimationUtils.loadAnimation(SimetryActivity.this, R.anim.fadein_out));



        ArrayList<ArrayList<Integer>> soruparent = new ArrayList<ArrayList<Integer>>();
        soruparent.add(komsu0);soruparent.add(komsu1);soruparent.add(komsu2);soruparent.add(komsu3);soruparent.add(komsu4);soruparent.add(komsu5);soruparent.add(komsu6);soruparent.add(komsu7);soruparent.add(komsu8);soruparent.add(komsu9);
        soruparent.add(komsu10);soruparent.add(komsu11);soruparent.add(komsu12);soruparent.add(komsu13);soruparent.add(komsu14);soruparent.add(komsu15);soruparent.add(komsu16);soruparent.add(komsu17);soruparent.add(komsu18);soruparent.add(komsu19);
        soruparent.add(komsu20);soruparent.add(komsu21);soruparent.add(komsu22);soruparent.add(komsu23);soruparent.add(komsu24);

        Random rnd = new Random();
        x= rnd.nextInt(25-0)+0;



        ArrayList<Integer> sorupaketi = new ArrayList<Integer>();
        sorupaketi.addAll(soruparent.get(x));
        y=sorupaketi.get(0);

        sorupaketi.removeAll(soruparent.get(x));
        sorupaketi.addAll(soruparent.get(y));
        z=sorupaketi.get(0);
        //if (z==x) {                z=sorupaketi.get(1);            }

        sorupaketi.removeAll(soruparent.get(y));
        sorupaketi.addAll(soruparent.get(z));
        t=sorupaketi.get(0);
        //if (t==y) {                t=sorupaketi.get(1);            }

        sorupaketi.removeAll(soruparent.get(z));
        sorupaketi.addAll(soruparent.get(t));
        q=sorupaketi.get(0);
        //if (q==z) {                q=sorupaketi.get(1);            }


        ImageView imgsoru1 = (ImageView)soru.get(x);
        imgsoru1.setBackgroundResource(R.drawable.simetryclicked);

        ImageView imgsoru2 = (ImageView)soru.get(y);
        imgsoru2.setBackgroundResource(R.drawable.simetryclicked);

        ImageView imgsoru3 = (ImageView)soru.get(z);
        imgsoru3.setBackgroundResource(R.drawable.simetryclicked);

        ImageView imgsoru4 = (ImageView)soru.get(t);
        imgsoru4.setBackgroundResource(R.drawable.simetryclicked);

        ImageView imgsoru5 = (ImageView)soru.get(q);
        imgsoru5.setBackgroundResource(R.drawable.simetryclicked);
        ///////////////////////////////////////////////////////////////////


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();


    }


}
