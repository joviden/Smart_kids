package com.smartkids;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.smartkids.R;
import android.widget.ProgressBar;


/**
 * Created by joviden on 03.12.2017.
 */

public class HomeActivity  extends Activity {

    private AdView mAdView;
    private ProgressBar toplamabar = null; private ProgressBar cikarmabar = null;private ProgressBar carpmabar = null;private ProgressBar bolmebar = null;private ProgressBar ritmikbar = null;
    private ProgressBar buyukkucukbar= null;private ProgressBar tekciftbar = null;private ProgressBar simetribar= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konularmain);

        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Integer inttopsoru,inttopdogru,inttopyanlis,topsorucounter,topdogrucounter,topyanliscounter,
         intbolsoru,intboldogru,intbolyanlis,bolsorucounter,boldogrucounter,bolyanliscounter,
        carpmasorucounter,carpmadogrucounter,carpmayanliscounter,intcarpmadogru,intcarpmayanlis,intcarpmasoru,
        cikarmayanliscounter,cikarmasorucounter,cikarmadogrucounter,intcikarmasoru,intcikarmadogru,intcikarmayanlis,
        buyukucuksorucounter,buyukucukdogrucounter,buyukucukyanliscounter,intbuyukkucuksoru,intbuyukkucukdogru,intbuyukkucukyanlis,
        ritmiksorucounter,ritmikdogrucounter,ritmikyanliscounter,intritmiksoru,intritmikdogru,intritmikyanlis,
        simetrisorucounter,simetridogrucounter,simetriyanliscounter,intsimetrisoru,intsimetridogru,intsimetriyanlis,
        baloncounter,dogrubaloncounter,yanlisbaloncounter,inttoplambalon,intdogrubalon,intyanlisbalon,inttopbos,intbolbos,intcarpmabos,intcikarmabos,intbuyukkucukbos,intritmikbos,intsimetribos,bolboscounter,
        simetriboscounter,topboscounter,buyukkucukboscounter,carpmaboscounter,cikarmaboscounter,ritmikboscounter;

        TextView toplamasorusayisi = (TextView)findViewById(R.id.toplamatoplamsoruTxt);
        TextView toplamadogru =(TextView)findViewById(R.id.toplamadogruTxt);
        TextView toplamayanlis =(TextView)findViewById(R.id.toplamayanlisTxt);
        TextView toplamabos =(TextView)findViewById(R.id.toplamabosTxt);
        TextView bolmetoplamsoru =(TextView)findViewById(R.id.bolmetoplamsoruTxt);
        TextView bolmedogrusayisi =(TextView)findViewById(R.id.bolmedogrusoruTxt);
        TextView bolmeyanlissayisi =(TextView)findViewById(R.id.bolmeyanlissoruTxt);
        TextView bolmebos =(TextView)findViewById(R.id.bolmebossoruTxt);
        TextView carpmatoplamsoru =(TextView)findViewById(R.id.carpmatoplamsoruTxt);
        TextView carpmadogrusayisi =(TextView)findViewById(R.id.carpmadogrusoruTxt);
        TextView carpmayanlissayisi =(TextView)findViewById(R.id.carpmayanlissoruTxt);
        TextView carpmabos =(TextView)findViewById(R.id.carpmabossoruTxt);
        TextView cikarmatoplamsoru =(TextView)findViewById(R.id.cikramatoplamsoruTxt);
        TextView cikarmadogrusayisi =(TextView)findViewById(R.id.cikramadogrusoruTxt);
        TextView cikarmayanlissayisi =(TextView)findViewById(R.id.cikarmayanlissoruTxt);
        TextView cikarmabos =(TextView)findViewById(R.id.cikarmabossoruTxt);
        TextView buyukkucuktoplamsoru =(TextView)findViewById(R.id.buyukkucuktoplamsoruTxt);
        TextView buyukkucukdogrusayisi =(TextView)findViewById(R.id.buyukkucukdogrusoruTxt);
        TextView buyukkucukyanlissayisi =(TextView)findViewById(R.id.buyukkucukyanlissoruTxt);
        TextView buyukkucukbos =(TextView)findViewById(R.id.buyukkucukbossoruTxt);
        TextView ritmiktoplamsoru =(TextView)findViewById(R.id.ritmiktoplamsoruTxt);
        TextView ritmikdogrusayisi =(TextView)findViewById(R.id.ritmikdogrusoruTxt);
        TextView ritmikyanlissayisi =(TextView)findViewById(R.id.ritmikyanlissoruTxt);
        TextView ritmikbos =(TextView)findViewById(R.id.ritmikbossoruTxt);
        TextView simetritoplamsoru =(TextView)findViewById(R.id.simetritoplamsoruTxt);
        TextView simetridogrusayisi =(TextView)findViewById(R.id.simetridogrusoruTxt);
        TextView simetriyanlissayisi =(TextView)findViewById(R.id.simetriyanlissoruTxt);
        TextView simetribos =(TextView)findViewById(R.id.simetribossoruTxt);
        TextView toplambalon =(TextView)findViewById(R.id.toplambalonTxt);
        TextView dogrubalon =(TextView)findViewById(R.id.dogrubalonTxt);
        TextView yanlisbalon =(TextView)findViewById(R.id.yanlisbalonTxt);



        SharedPreferences sp = getSharedPreferences("skorbilgileri", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        sp = getSharedPreferences("skorbilgileri", Activity.MODE_PRIVATE);
        inttopsoru = sp.getInt("toplamasoru",0);
        inttopdogru= sp.getInt("toplamadogru",0);
        inttopyanlis = sp.getInt("toplamayanlis",0);
        inttopbos = sp.getInt("toplamabos",0);
        intbolsoru= sp.getInt("bolmesoru",0);
        intboldogru= sp.getInt("bolmedogru",0);
        intbolyanlis= sp.getInt("bolmeyanlis",0);
        intbolbos = sp.getInt("bolmebos",0);
        intcarpmasoru= sp.getInt("carpmasoru",0);
        intcarpmadogru= sp.getInt("carpmadogru",0);
        intcarpmayanlis= sp.getInt("carpmayanlis",0);
        intcarpmabos = sp.getInt("carpmabos",0);
        intcikarmasoru= sp.getInt("cikarmasoru",0);
        intcikarmadogru= sp.getInt("cikarmadogru",0);
        intcikarmayanlis= sp.getInt("cikarmayanlis",0);
        intcikarmabos = sp.getInt("cikarmabos",0);
        intbuyukkucuksoru= sp.getInt("buyukkucuksoru",0);
        intbuyukkucukdogru= sp.getInt("buyukkucukdogru",0);
        intbuyukkucukyanlis= sp.getInt("buyukkucukyanlis",0);
        intbuyukkucukbos = sp.getInt("buyukkucukbos",0);
        intritmiksoru= sp.getInt("ritmiksoru",0);
        intritmikdogru= sp.getInt("ritmikdogru",0);
        intritmikyanlis= sp.getInt("ritmikyanlis",0);
        intritmikbos = sp.getInt("ritmikbos",0);
        intsimetrisoru= sp.getInt("simetrisoru",0);
        intsimetridogru= sp.getInt("simetridogru",0);
        intsimetriyanlis= sp.getInt("simetriyanlis",0);
        intsimetribos = sp.getInt("simetribos",0);
        inttoplambalon= sp.getInt("toplambalon",0);
        intdogrubalon= sp.getInt("dogrubalon",0);
        intyanlisbalon= sp.getInt("yanlisbalon",0);


        toplamasorusayisi.setText(getString(R.string.totalquestionh)+String.valueOf(inttopsoru));
        toplamadogru.setText(getString(R.string.rightanswersh)+String.valueOf(inttopdogru));
        toplamayanlis.setText(getString(R.string.wronganswersh)+String.valueOf(inttopyanlis));
        toplamabos.setText(getString(R.string.unanswered)+String.valueOf(inttopbos));
        bolmetoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intbolsoru));
        bolmedogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intboldogru));
        bolmeyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intbolyanlis));
        bolmebos.setText(getString(R.string.unanswered)+String.valueOf(intbolbos));
        carpmatoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intcarpmasoru));
        carpmadogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intcarpmadogru));
        carpmayanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intcarpmayanlis));
        carpmabos.setText(getString(R.string.unanswered)+String.valueOf(intcarpmabos));
        carpmabos.setText(getString(R.string.unanswered)+String.valueOf(intbolbos));
        cikarmatoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intcikarmasoru));
        cikarmadogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intcikarmadogru));
        cikarmayanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intcikarmayanlis));
        cikarmabos.setText(getString(R.string.unanswered)+String.valueOf(intcikarmabos));
        buyukkucuktoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intbuyukkucuksoru));
        buyukkucukdogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intbuyukkucukdogru));
        buyukkucukyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intbuyukkucukyanlis));
        buyukkucukbos.setText(getString(R.string.unanswered)+String.valueOf(intbuyukkucukbos));
        ritmiktoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intritmiksoru));
        ritmikdogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intritmikdogru));
        ritmikyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intritmikyanlis));
        ritmikbos.setText(getString(R.string.unanswered)+String.valueOf(intritmikbos));
        simetritoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intsimetrisoru));
        simetridogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intsimetridogru));
        simetriyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intsimetriyanlis));
        simetribos.setText(getString(R.string.unanswered)+String.valueOf(intsimetribos));
        toplambalon.setText(getString(R.string.patlatilanbalonh)+String.valueOf(inttoplambalon));
        dogrubalon.setText(getString(R.string.dogrubalonh)+String.valueOf(intdogrubalon));
        yanlisbalon.setText(getString(R.string.hatalibalonh)+String.valueOf(intyanlisbalon));


        Intent myIntent = getIntent();
        topsorucounter = myIntent.getIntExtra("toplamasorusayisi", 0);
        topdogrucounter =  myIntent.getIntExtra("toplamadogrusayisi", 0);
        topyanliscounter = myIntent.getIntExtra("toplamayanlissayisi",0);
        topboscounter =  myIntent.getIntExtra("toplamabossayisi", 0);
        bolsorucounter= myIntent.getIntExtra("bolmesorusayisi", 0);
        boldogrucounter= myIntent.getIntExtra("bolmedogrusayisi", 0);
        bolyanliscounter= myIntent.getIntExtra("bolmeayanlissayisi", 0);
        bolboscounter = myIntent.getIntExtra("bolmeabossayisi", 0);
        carpmasorucounter= myIntent.getIntExtra("carpmasorusayisi", 0);
        carpmadogrucounter= myIntent.getIntExtra("carpmadogrusayisi", 0);
        carpmayanliscounter= myIntent.getIntExtra("carpmayanlissayisi", 0);
        carpmaboscounter = myIntent.getIntExtra("carpmabossayisi", 0);
        cikarmasorucounter= myIntent.getIntExtra("cikarmasorusayisi", 0);
        cikarmadogrucounter= myIntent.getIntExtra("cikarmadogrusayisi", 0);
        cikarmayanliscounter= myIntent.getIntExtra("cikarmayanlissayisi", 0);
        cikarmaboscounter= myIntent.getIntExtra("cikarmabossayisi", 0);
        buyukucuksorucounter= myIntent.getIntExtra("buyukkucuksorusayisi", 0);
        buyukucukdogrucounter= myIntent.getIntExtra("buyukkucukdogrusayisi", 0);
        buyukucukyanliscounter= myIntent.getIntExtra("buyukkucukyanlissayisi", 0);
        buyukkucukboscounter = myIntent.getIntExtra("buyukkucukbossayisi", 0);
        ritmiksorucounter= myIntent.getIntExtra("ritmiksorusayisi", 0);
        ritmikdogrucounter= myIntent.getIntExtra("ritmikdogrusayisi", 0);
        ritmikyanliscounter= myIntent.getIntExtra("ritmikyanlissayisi", 0);
        ritmikboscounter= myIntent.getIntExtra("ritmikbossayisi", 0);
        simetrisorucounter= myIntent.getIntExtra("simetrisorusayisi", 0);
        simetridogrucounter= myIntent.getIntExtra("simetridogrusayisi", 0);
        simetriyanliscounter= myIntent.getIntExtra("simetriyanlissayisi", 0);
        simetriboscounter= myIntent.getIntExtra("simetribossayisi", 0);
        baloncounter= myIntent.getIntExtra("toplambalonsayisi", 0);
        dogrubaloncounter= myIntent.getIntExtra("dogrubalonsayisi", 0);
        yanlisbaloncounter= myIntent.getIntExtra("yanlisbalonsayisi", 0);





        toplamabar = (ProgressBar)findViewById(R.id.toplamaBar);
        toplamabar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#C0D000"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        int x = sp.getInt("toplamadogru",0);
        int y = sp.getInt("toplamasoru",0);
        float topbasari;
        if (y ==0) {
             topbasari = 0;
        }else{
            topbasari = x*100/y;

        }
        toplamabar.setMax(100);
        toplamabar.setProgress((int)topbasari);

        TextView toplamascore = (TextView)findViewById(R.id.scoretoplamaTxt);
        toplamascore.setText(getString(R.string.bolumpuani)+String.valueOf((int)topbasari));
        /////////////////////////////////////////////////
        cikarmabar = (ProgressBar)findViewById(R.id.cikarmaBar);
        cikarmabar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xcikarma = sp.getInt("cikarmadogru",0);
        int ycikarma = sp.getInt("cikarmasoru",0);
        float cikarmabasari;
        if (ycikarma ==0) {
            cikarmabasari = 0;
        }else{
            cikarmabasari = xcikarma*100/ycikarma;
        }
        cikarmabar.setMax(100);
        cikarmabar.setProgress((int)cikarmabasari);

        TextView cikarmascore = (TextView)findViewById(R.id.scorecikarmaTxt);
        cikarmascore.setText(getString(R.string.bolumpuani)+String.valueOf((int)cikarmabasari));
        /////////////////////////////////////////////////
        carpmabar = (ProgressBar)findViewById(R.id.carpmaBar);
        carpmabar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xcarpma = sp.getInt("carpmadogru",0);
        int ycarpma = sp.getInt("carpmasoru",0);
        float carpmabasari;
        if (ycarpma ==0) {
            carpmabasari= 0;
        }else{
            carpmabasari = xcarpma*100/ycarpma;
        }
        carpmabar.setMax(100);
        carpmabar.setProgress((int)carpmabasari);

        TextView carpmascore = (TextView)findViewById(R.id.scorecarpmaTxt);
        carpmascore.setText(getString(R.string.bolumpuani)+String.valueOf((int)carpmabasari));
        /////////////////////////////////////////////////
        bolmebar = (ProgressBar)findViewById(R.id.bolmeBar);
        bolmebar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xbolme = sp.getInt("bolmedogru",0);
        int ybolme = sp.getInt("bolmesoru",0);
        float bolmebasari;
        if (ybolme ==0) {
            bolmebasari= 0;
        }else{
            bolmebasari = xbolme*100/ybolme;
        }
        bolmebar.setMax(100);
        bolmebar.setProgress((int)bolmebasari);

        TextView bolmescore = (TextView)findViewById(R.id.scorebolmeTxt);
        bolmescore.setText(getString(R.string.bolumpuani)+String.valueOf((int)bolmebasari));
        /////////////////////////////////////////////////
        ritmikbar = (ProgressBar)findViewById(R.id.ritmikBar);
        ritmikbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xritmik = sp.getInt("ritmikdogru",0);
        int yritmik = sp.getInt("ritmiksoru",0);
        float ritmikbasari;
        if (yritmik ==0) {
            ritmikbasari= 0;
        }else{
            ritmikbasari = xritmik*100/yritmik;
        }
        ritmikbar.setMax(100);
        ritmikbar.setProgress((int)ritmikbasari);

        TextView ritmikscore = (TextView)findViewById(R.id.scoreritmikTxt);
        ritmikscore.setText(getString(R.string.bolumpuani)+String.valueOf((int)ritmikbasari));
        /////////////////////////////////////////////////
        tekciftbar = (ProgressBar)findViewById(R.id.tekciftBar);
        tekciftbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xtekcift = sp.getInt("dogrubalon",0);
        int ytekcift = sp.getInt("toplambalon",0);
        float tekciftbasari;
        if (ytekcift ==0) {
            tekciftbasari= 0;
        }else{
            tekciftbasari = xtekcift*100/ytekcift;
        }
        tekciftbar.setMax(100);
        tekciftbar.setProgress((int)tekciftbasari);

        TextView tekciftscore = (TextView)findViewById(R.id.scoretekciftTxt);
        tekciftscore.setText(getString(R.string.bolumpuani)+String.valueOf((int)tekciftbasari));
        /////////////////////////////////////////////////
        simetribar = (ProgressBar)findViewById(R.id.simetriBar);
        simetribar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xsimetri = sp.getInt("simetridogru",0);
        int ysimetri = sp.getInt("simetrisoru",0);
        float simetribasari;
        if (ysimetri ==0) {
            simetribasari= 0;
        }else{
            simetribasari = xsimetri*100/ysimetri;
        }
        simetribar.setMax(100);
        simetribar.setProgress((int)simetribasari);

        TextView simetriscore = (TextView)findViewById(R.id.scoresimetriTxt);
        simetriscore.setText(getString(R.string.bolumpuani)+String.valueOf((int)simetribasari));
        /////////////////////////////////////////////////
        buyukkucukbar = (ProgressBar)findViewById(R.id.buyukkucukBar);
        buyukkucukbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xbuyukkucuk = sp.getInt("buyukkucukdogru",0);
        int ybuyukkucuk = sp.getInt("buyukkucuksoru",0);
        float buyukkucukbasari;
        if (ybuyukkucuk ==0) {
            buyukkucukbasari= 0;
        }else{
            buyukkucukbasari = xbuyukkucuk*100/ybuyukkucuk;
        }
        buyukkucukbar.setMax(100);
        buyukkucukbar.setProgress((int)buyukkucukbasari);

        TextView buyukkucukscore = (TextView)findViewById(R.id.scorebuyukkucukTxt);
        buyukkucukscore.setText(getString(R.string.bolumpuani)+String.valueOf((int)buyukkucukbasari));

        ImageView toplamayildiz1 = (ImageView)findViewById(R.id.gifRandom11);
        ImageView toplamayildiz2 = (ImageView)findViewById(R.id.gifRandom12);
        ImageView toplamayildiz3 = (ImageView)findViewById(R.id.gifRandom13);
        ImageView toplamayildiz4 = (ImageView)findViewById(R.id.gifRandom14);
        ImageView toplamayildiz5 = (ImageView)findViewById(R.id.gifRandom15);

        ImageView cikarmayildiz1 = (ImageView)findViewById(R.id.gifRandom21);
        ImageView cikarmayildiz2 = (ImageView)findViewById(R.id.gifRandom22);
        ImageView cikarmayildiz3 = (ImageView)findViewById(R.id.gifRandom23);
        ImageView cikarmayildiz4 = (ImageView)findViewById(R.id.gifRandom24);
        ImageView cikarmayildiz5 = (ImageView)findViewById(R.id.gifRandom25);

        ImageView bolmeyildiz1 = (ImageView)findViewById(R.id.gifRandom31);
        ImageView bolmeyildiz2 = (ImageView)findViewById(R.id.gifRandom32);
        ImageView bolmeyildiz3 = (ImageView)findViewById(R.id.gifRandom33);
        ImageView bolmeyildiz4 = (ImageView)findViewById(R.id.gifRandom34);
        ImageView bolmeyildiz5 = (ImageView)findViewById(R.id.gifRandom35);

        ImageView carpmayildiz1 = (ImageView)findViewById(R.id.gifRandom41);
        ImageView carpmayildiz2 = (ImageView)findViewById(R.id.gifRandom42);
        ImageView carpmayildiz3 = (ImageView)findViewById(R.id.gifRandom43);
        ImageView carpmayildiz4 = (ImageView)findViewById(R.id.gifRandom44);
        ImageView carpmayildiz5 = (ImageView)findViewById(R.id.gifRandom45);

        ImageView ritmikyildiz1 = (ImageView)findViewById(R.id.gifRandom51);
        ImageView ritmikyildiz2 = (ImageView)findViewById(R.id.gifRandom52);
        ImageView ritmikyildiz3 = (ImageView)findViewById(R.id.gifRandom53);
        ImageView ritmikyildiz4 = (ImageView)findViewById(R.id.gifRandom54);
        ImageView ritmikyildiz5 = (ImageView)findViewById(R.id.gifRandom55);

        ImageView buyukkucukyildiz1 = (ImageView)findViewById(R.id.gifRandom61);
        ImageView buyukkucukyildiz2 = (ImageView)findViewById(R.id.gifRandom62);
        ImageView buyukkucukyildiz3 = (ImageView)findViewById(R.id.gifRandom63);
        ImageView buyukkucukyildiz4 = (ImageView)findViewById(R.id.gifRandom64);
        ImageView buyukkucukyildiz5 = (ImageView)findViewById(R.id.gifRandom65);

        ImageView tekciftyildiz1 = (ImageView)findViewById(R.id.gifRandom71);
        ImageView tekciftyildiz2 = (ImageView)findViewById(R.id.gifRandom72);
        ImageView tekciftyildiz3 = (ImageView)findViewById(R.id.gifRandom73);
        ImageView tekciftyildiz4 = (ImageView)findViewById(R.id.gifRandom74);
        ImageView tekciftyildiz5 = (ImageView)findViewById(R.id.gifRandom75);

        ImageView simetriyildiz1 = (ImageView)findViewById(R.id.gifRandom81);
        ImageView simetriyildiz2 = (ImageView)findViewById(R.id.gifRandom82);
        ImageView simetriyildiz3 = (ImageView)findViewById(R.id.gifRandom83);
        ImageView simetriyildiz4 = (ImageView)findViewById(R.id.gifRandom84);
        ImageView simetriyildiz5 = (ImageView)findViewById(R.id.gifRandom85);

        if (topbasari<20){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.INVISIBLE);
            toplamayildiz3.setVisibility(View.INVISIBLE);
            toplamayildiz4.setVisibility(View.INVISIBLE);
            toplamayildiz5.setVisibility(View.INVISIBLE);
        }
        if (topbasari>20 && topbasari<40){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.VISIBLE);
            toplamayildiz3.setVisibility(View.INVISIBLE);
            toplamayildiz4.setVisibility(View.INVISIBLE);
            toplamayildiz5.setVisibility(View.INVISIBLE);
        }
        if (topbasari>40 && topbasari<60){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.VISIBLE);
            toplamayildiz3.setVisibility(View.VISIBLE);
            toplamayildiz4.setVisibility(View.INVISIBLE);
            toplamayildiz5.setVisibility(View.INVISIBLE);
        }
        if (topbasari>60 && topbasari<80){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.VISIBLE);
            toplamayildiz3.setVisibility(View.VISIBLE);
            toplamayildiz4.setVisibility(View.VISIBLE);
            toplamayildiz5.setVisibility(View.INVISIBLE);
        }
        if (topbasari>80 ){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.VISIBLE);
            toplamayildiz3.setVisibility(View.VISIBLE);
            toplamayildiz4.setVisibility(View.VISIBLE);
            toplamayildiz5.setVisibility(View.VISIBLE);
        }

        if (cikarmabasari<20){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.INVISIBLE);
            cikarmayildiz3.setVisibility(View.INVISIBLE);
            cikarmayildiz4.setVisibility(View.INVISIBLE);
            cikarmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (cikarmabasari>20 && cikarmabasari<40){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.VISIBLE);
            cikarmayildiz3.setVisibility(View.INVISIBLE);
            cikarmayildiz4.setVisibility(View.INVISIBLE);
            cikarmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (cikarmabasari>40 && cikarmabasari<60){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.VISIBLE);
            cikarmayildiz3.setVisibility(View.VISIBLE);
            cikarmayildiz4.setVisibility(View.INVISIBLE);
            cikarmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (cikarmabasari>60 && cikarmabasari<80){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.VISIBLE);
            cikarmayildiz3.setVisibility(View.VISIBLE);
            cikarmayildiz4.setVisibility(View.VISIBLE);
            cikarmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (cikarmabasari>80 ){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.VISIBLE);
            cikarmayildiz3.setVisibility(View.VISIBLE);
            cikarmayildiz4.setVisibility(View.VISIBLE);
            cikarmayildiz5.setVisibility(View.VISIBLE);
        }




        if (bolmebasari<20){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.INVISIBLE);
            bolmeyildiz3.setVisibility(View.INVISIBLE);
            bolmeyildiz4.setVisibility(View.INVISIBLE);
            bolmeyildiz5.setVisibility(View.INVISIBLE);
        }
        if (bolmebasari>20 && bolmebasari<40){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.VISIBLE);
            bolmeyildiz3.setVisibility(View.INVISIBLE);
            bolmeyildiz4.setVisibility(View.INVISIBLE);
            bolmeyildiz5.setVisibility(View.INVISIBLE);
        }
        if (bolmebasari>40 && bolmebasari<60){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.VISIBLE);
            bolmeyildiz3.setVisibility(View.VISIBLE);
            bolmeyildiz4.setVisibility(View.INVISIBLE);
            bolmeyildiz5.setVisibility(View.INVISIBLE);
        }
        if (bolmebasari>60 && bolmebasari<80){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.VISIBLE);
            bolmeyildiz3.setVisibility(View.VISIBLE);
            bolmeyildiz4.setVisibility(View.VISIBLE);
            bolmeyildiz5.setVisibility(View.INVISIBLE);
        }
        if (bolmebasari>80){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.VISIBLE);
            bolmeyildiz3.setVisibility(View.VISIBLE);
            bolmeyildiz4.setVisibility(View.VISIBLE);
            bolmeyildiz5.setVisibility(View.VISIBLE);
        }



        if (carpmabasari<20){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.INVISIBLE);
            carpmayildiz3.setVisibility(View.INVISIBLE);
            carpmayildiz4.setVisibility(View.INVISIBLE);
            carpmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (carpmabasari>20 && carpmabasari<40){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.VISIBLE);
            carpmayildiz3.setVisibility(View.INVISIBLE);
            carpmayildiz4.setVisibility(View.INVISIBLE);
            carpmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (carpmabasari>40 && carpmabasari<60){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.VISIBLE);
            carpmayildiz3.setVisibility(View.VISIBLE);
            carpmayildiz4.setVisibility(View.INVISIBLE);
            carpmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (carpmabasari>60 && carpmabasari<80){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.VISIBLE);
            carpmayildiz3.setVisibility(View.VISIBLE);
            carpmayildiz4.setVisibility(View.VISIBLE);
            carpmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (carpmabasari>80 ){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.VISIBLE);
            carpmayildiz3.setVisibility(View.VISIBLE);
            carpmayildiz4.setVisibility(View.VISIBLE);
            carpmayildiz5.setVisibility(View.VISIBLE);
        }


        if (buyukkucukbasari<20){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.INVISIBLE);
            buyukkucukyildiz3.setVisibility(View.INVISIBLE);
            buyukkucukyildiz4.setVisibility(View.INVISIBLE);
            buyukkucukyildiz5.setVisibility(View.INVISIBLE);
       }
        if (buyukkucukbasari>20 && buyukkucukbasari<40){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.VISIBLE);
            buyukkucukyildiz3.setVisibility(View.INVISIBLE);
            buyukkucukyildiz4.setVisibility(View.INVISIBLE);
            buyukkucukyildiz5.setVisibility(View.INVISIBLE);
        }
        if (buyukkucukbasari>40 && buyukkucukbasari<60){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.VISIBLE);
            buyukkucukyildiz3.setVisibility(View.VISIBLE);
            buyukkucukyildiz4.setVisibility(View.INVISIBLE);
            buyukkucukyildiz5.setVisibility(View.INVISIBLE);
        }
        if (buyukkucukbasari>60 && buyukkucukbasari<80){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.VISIBLE);
            buyukkucukyildiz3.setVisibility(View.VISIBLE);
            buyukkucukyildiz4.setVisibility(View.VISIBLE);
            buyukkucukyildiz5.setVisibility(View.INVISIBLE);
        }
        if (buyukkucukbasari>80){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.VISIBLE);
            buyukkucukyildiz3.setVisibility(View.VISIBLE);
            buyukkucukyildiz4.setVisibility(View.VISIBLE);
            buyukkucukyildiz5.setVisibility(View.VISIBLE);
        }



        if (ritmikbasari<20){
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.INVISIBLE);
            ritmikyildiz3.setVisibility(View.INVISIBLE);
            ritmikyildiz4.setVisibility(View.INVISIBLE);
            ritmikyildiz5.setVisibility(View.INVISIBLE);
       }
        if (ritmikbasari>20 && ritmikbasari<40 ){
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.VISIBLE);
            ritmikyildiz3.setVisibility(View.INVISIBLE);
            ritmikyildiz4.setVisibility(View.INVISIBLE);
            ritmikyildiz5.setVisibility(View.INVISIBLE);
        }
        if (ritmikbasari>40 && ritmikbasari<60 ){
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.VISIBLE);
            ritmikyildiz3.setVisibility(View.VISIBLE);
            ritmikyildiz4.setVisibility(View.INVISIBLE);
            ritmikyildiz5.setVisibility(View.INVISIBLE);
        }
        if (ritmikbasari>60 && ritmikbasari<80 ){
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.VISIBLE);
            ritmikyildiz3.setVisibility(View.VISIBLE);
            ritmikyildiz4.setVisibility(View.VISIBLE);
            ritmikyildiz5.setVisibility(View.INVISIBLE);
        }
        if (ritmikbasari>80) {
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.VISIBLE);
            ritmikyildiz3.setVisibility(View.VISIBLE);
            ritmikyildiz4.setVisibility(View.VISIBLE);
            ritmikyildiz5.setVisibility(View.VISIBLE);
        }

        if (tekciftbasari<20){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.INVISIBLE);
            tekciftyildiz3.setVisibility(View.INVISIBLE);
            tekciftyildiz4.setVisibility(View.INVISIBLE);
            tekciftyildiz5.setVisibility(View.INVISIBLE);
       }
        if (tekciftbasari>20 && tekciftbasari<40){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.VISIBLE);
            tekciftyildiz3.setVisibility(View.INVISIBLE);
            tekciftyildiz4.setVisibility(View.INVISIBLE);
            tekciftyildiz5.setVisibility(View.INVISIBLE);
        }
        if (tekciftbasari>40 && tekciftbasari<60){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.VISIBLE);
            tekciftyildiz3.setVisibility(View.VISIBLE);
            tekciftyildiz4.setVisibility(View.INVISIBLE);
            tekciftyildiz5.setVisibility(View.INVISIBLE);
        }
        if (tekciftbasari>60 && tekciftbasari<80){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.VISIBLE);
            tekciftyildiz3.setVisibility(View.VISIBLE);
            tekciftyildiz4.setVisibility(View.VISIBLE);
            tekciftyildiz5.setVisibility(View.INVISIBLE);
        }
        if (tekciftbasari>80){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.VISIBLE);
            tekciftyildiz3.setVisibility(View.VISIBLE);
            tekciftyildiz4.setVisibility(View.VISIBLE);
            tekciftyildiz5.setVisibility(View.VISIBLE);
        }
        if (simetribasari<20){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.INVISIBLE);
            simetriyildiz3.setVisibility(View.INVISIBLE);
            simetriyildiz4.setVisibility(View.INVISIBLE);
            simetriyildiz5.setVisibility(View.INVISIBLE);
       }
        if (simetribasari>20 && simetribasari<40){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.VISIBLE);
            simetriyildiz3.setVisibility(View.INVISIBLE);
            simetriyildiz4.setVisibility(View.INVISIBLE);
            simetriyildiz5.setVisibility(View.INVISIBLE);
        }
        if (simetribasari>40 && simetribasari<60){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.VISIBLE);
            simetriyildiz3.setVisibility(View.VISIBLE);
            simetriyildiz4.setVisibility(View.INVISIBLE);
            simetriyildiz5.setVisibility(View.INVISIBLE);
        }
        if (simetribasari>60 && simetribasari<80){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.VISIBLE);
            simetriyildiz3.setVisibility(View.VISIBLE);
            simetriyildiz4.setVisibility(View.VISIBLE);
            simetriyildiz5.setVisibility(View.INVISIBLE);
        }
        if (simetribasari>80 ){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.VISIBLE);
            simetriyildiz3.setVisibility(View.VISIBLE);
            simetriyildiz4.setVisibility(View.VISIBLE);
            simetriyildiz5.setVisibility(View.VISIBLE);
        }


    }
    public void onResume() {
        super.onResume();

        Integer inttopsoru,inttopdogru,inttopyanlis,topsorucounter,topdogrucounter,topyanliscounter,
                intbolsoru,intboldogru,intbolyanlis,bolsorucounter,boldogrucounter,bolyanliscounter,
                carpmasorucounter,carpmadogrucounter,carpmayanliscounter,intcarpmadogru,intcarpmayanlis,intcarpmasoru,
                cikarmayanliscounter,cikarmasorucounter,cikarmadogrucounter,intcikarmasoru,intcikarmadogru,intcikarmayanlis,
                buyukucuksorucounter,buyukucukdogrucounter,buyukucukyanliscounter,intbuyukkucuksoru,intbuyukkucukdogru,intbuyukkucukyanlis,
                ritmiksorucounter,ritmikdogrucounter,ritmikyanliscounter,intritmiksoru,intritmikdogru,intritmikyanlis,
                simetrisorucounter,simetridogrucounter,simetriyanliscounter,intsimetrisoru,intsimetridogru,intsimetriyanlis,
                baloncounter,dogrubaloncounter,yanlisbaloncounter,inttoplambalon,intdogrubalon,intyanlisbalon,inttopbos,intbolbos,intcarpmabos,intcikarmabos,intbuyukkucukbos,intritmikbos,intsimetribos,bolboscounter,
                simetriboscounter,topboscounter,buyukkucukboscounter,carpmaboscounter,cikarmaboscounter,ritmikboscounter;

        TextView toplamasorusayisi = (TextView)findViewById(R.id.toplamatoplamsoruTxt);
        TextView toplamadogru =(TextView)findViewById(R.id.toplamadogruTxt);
        TextView toplamayanlis =(TextView)findViewById(R.id.toplamayanlisTxt);
        TextView toplamabos =(TextView)findViewById(R.id.toplamabosTxt);
        TextView bolmetoplamsoru =(TextView)findViewById(R.id.bolmetoplamsoruTxt);
        TextView bolmedogrusayisi =(TextView)findViewById(R.id.bolmedogrusoruTxt);
        TextView bolmeyanlissayisi =(TextView)findViewById(R.id.bolmeyanlissoruTxt);
        TextView bolmebos =(TextView)findViewById(R.id.bolmebossoruTxt);
        TextView carpmatoplamsoru =(TextView)findViewById(R.id.carpmatoplamsoruTxt);
        TextView carpmadogrusayisi =(TextView)findViewById(R.id.carpmadogrusoruTxt);
        TextView carpmayanlissayisi =(TextView)findViewById(R.id.carpmayanlissoruTxt);
        TextView carpmabos =(TextView)findViewById(R.id.carpmabossoruTxt);
        TextView cikarmatoplamsoru =(TextView)findViewById(R.id.cikramatoplamsoruTxt);
        TextView cikarmadogrusayisi =(TextView)findViewById(R.id.cikramadogrusoruTxt);
        TextView cikarmayanlissayisi =(TextView)findViewById(R.id.cikarmayanlissoruTxt);
        TextView cikarmabos =(TextView)findViewById(R.id.cikarmabossoruTxt);
        TextView buyukkucuktoplamsoru =(TextView)findViewById(R.id.buyukkucuktoplamsoruTxt);
        TextView buyukkucukdogrusayisi =(TextView)findViewById(R.id.buyukkucukdogrusoruTxt);
        TextView buyukkucukyanlissayisi =(TextView)findViewById(R.id.buyukkucukyanlissoruTxt);
        TextView buyukkucukbos =(TextView)findViewById(R.id.buyukkucukbossoruTxt);
        TextView ritmiktoplamsoru =(TextView)findViewById(R.id.ritmiktoplamsoruTxt);
        TextView ritmikdogrusayisi =(TextView)findViewById(R.id.ritmikdogrusoruTxt);
        TextView ritmikyanlissayisi =(TextView)findViewById(R.id.ritmikyanlissoruTxt);
        TextView ritmikbos =(TextView)findViewById(R.id.ritmikbossoruTxt);
        TextView simetritoplamsoru =(TextView)findViewById(R.id.simetritoplamsoruTxt);
        TextView simetridogrusayisi =(TextView)findViewById(R.id.simetridogrusoruTxt);
        TextView simetriyanlissayisi =(TextView)findViewById(R.id.simetriyanlissoruTxt);
        TextView simetribos =(TextView)findViewById(R.id.simetribossoruTxt);
        TextView toplambalon =(TextView)findViewById(R.id.toplambalonTxt);
        TextView dogrubalon =(TextView)findViewById(R.id.dogrubalonTxt);
        TextView yanlisbalon =(TextView)findViewById(R.id.yanlisbalonTxt);



        SharedPreferences sp = getSharedPreferences("skorbilgileri", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        sp = getSharedPreferences("skorbilgileri", Activity.MODE_PRIVATE);
        inttopsoru = sp.getInt("toplamasoru",0);
        inttopdogru= sp.getInt("toplamadogru",0);
        inttopyanlis = sp.getInt("toplamayanlis",0);
        inttopbos = sp.getInt("toplamabos",0);
        intbolsoru= sp.getInt("bolmesoru",0);
        intboldogru= sp.getInt("bolmedogru",0);
        intbolyanlis= sp.getInt("bolmeyanlis",0);
        intbolbos = sp.getInt("bolmebos",0);
        intcarpmasoru= sp.getInt("carpmasoru",0);
        intcarpmadogru= sp.getInt("carpmadogru",0);
        intcarpmayanlis= sp.getInt("carpmayanlis",0);
        intcarpmabos = sp.getInt("carpmabos",0);
        intcikarmasoru= sp.getInt("cikarmasoru",0);
        intcikarmadogru= sp.getInt("cikarmadogru",0);
        intcikarmayanlis= sp.getInt("cikarmayanlis",0);
        intcikarmabos = sp.getInt("cikarmabos",0);
        intbuyukkucuksoru= sp.getInt("buyukkucuksoru",0);
        intbuyukkucukdogru= sp.getInt("buyukkucukdogru",0);
        intbuyukkucukyanlis= sp.getInt("buyukkucukyanlis",0);
        intbuyukkucukbos = sp.getInt("buyukkucukbos",0);
        intritmiksoru= sp.getInt("ritmiksoru",0);
        intritmikdogru= sp.getInt("ritmikdogru",0);
        intritmikyanlis= sp.getInt("ritmikyanlis",0);
        intritmikbos = sp.getInt("ritmikbos",0);
        intsimetrisoru= sp.getInt("simetrisoru",0);
        intsimetridogru= sp.getInt("simetridogru",0);
        intsimetriyanlis= sp.getInt("simetriyanlis",0);
        intsimetribos = sp.getInt("simetribos",0);
        inttoplambalon= sp.getInt("toplambalon",0);
        intdogrubalon= sp.getInt("dogrubalon",0);
        intyanlisbalon= sp.getInt("yanlisbalon",0);

        Intent myIntent = getIntent();
        topsorucounter = myIntent.getIntExtra("toplamasorusayisi", 0);
        topdogrucounter =  myIntent.getIntExtra("toplamadogrusayisi", 0);
        topyanliscounter = myIntent.getIntExtra("toplamayanlissayisi",0);
        topboscounter =  myIntent.getIntExtra("toplamabossayisi", 0);
        bolsorucounter= myIntent.getIntExtra("bolmesorusayisi", 0);
        boldogrucounter= myIntent.getIntExtra("bolmedogrusayisi", 0);
        bolyanliscounter= myIntent.getIntExtra("bolmeayanlissayisi", 0);
        bolboscounter = myIntent.getIntExtra("bolmeabossayisi", 0);
        carpmasorucounter= myIntent.getIntExtra("carpmasorusayisi", 0);
        carpmadogrucounter= myIntent.getIntExtra("carpmadogrusayisi", 0);
        carpmayanliscounter= myIntent.getIntExtra("carpmayanlissayisi", 0);
        carpmaboscounter = myIntent.getIntExtra("carpmabossayisi", 0);
        cikarmasorucounter= myIntent.getIntExtra("cikarmasorusayisi", 0);
        cikarmadogrucounter= myIntent.getIntExtra("cikarmadogrusayisi", 0);
        cikarmayanliscounter= myIntent.getIntExtra("cikarmayanlissayisi", 0);
        cikarmaboscounter= myIntent.getIntExtra("cikarmabossayisi", 0);
        buyukucuksorucounter= myIntent.getIntExtra("buyukkucuksorusayisi", 0);
        buyukucukdogrucounter= myIntent.getIntExtra("buyukkucukdogrusayisi", 0);
        buyukucukyanliscounter= myIntent.getIntExtra("buyukkucukyanlissayisi", 0);
        buyukkucukboscounter = myIntent.getIntExtra("buyukkucukbossayisi", 0);
        ritmiksorucounter= myIntent.getIntExtra("ritmiksorusayisi", 0);
        ritmikdogrucounter= myIntent.getIntExtra("ritmikdogrusayisi", 0);
        ritmikyanliscounter= myIntent.getIntExtra("ritmikyanlissayisi", 0);
        ritmikboscounter= myIntent.getIntExtra("ritmikbossayisi", 0);
        simetrisorucounter= myIntent.getIntExtra("simetrisorusayisi", 0);
        simetridogrucounter= myIntent.getIntExtra("simetridogrusayisi", 0);
        simetriyanliscounter= myIntent.getIntExtra("simetriyanlissayisi", 0);
        simetriboscounter= myIntent.getIntExtra("simetribossayisi", 0);
        baloncounter= myIntent.getIntExtra("toplambalonsayisi", 0);
        dogrubaloncounter= myIntent.getIntExtra("dogrubalonsayisi", 0);
        yanlisbaloncounter= myIntent.getIntExtra("yanlisbalonsayisi", 0);


        toplamasorusayisi.setText(getString(R.string.totalquestionh)+String.valueOf(inttopsoru+topsorucounter));
        toplamadogru.setText(getString(R.string.rightanswersh)+String.valueOf(inttopdogru+topdogrucounter));
        toplamayanlis.setText(getString(R.string.wronganswersh)+String.valueOf(inttopyanlis+topyanliscounter));
        toplamabos.setText(getString(R.string.unanswered)+String.valueOf(inttopbos+topboscounter));
        bolmetoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intbolsoru+bolsorucounter));
        bolmedogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intboldogru+boldogrucounter));
        bolmeyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intbolyanlis+bolyanliscounter));
        bolmebos.setText(getString(R.string.unanswered)+String.valueOf(intbolbos+bolboscounter));
        carpmatoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intcarpmasoru+carpmasorucounter));
        carpmadogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intcarpmadogru+carpmadogrucounter));
        carpmayanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intcarpmayanlis+carpmayanliscounter));
        carpmabos.setText(getString(R.string.unanswered)+String.valueOf(intbolbos+carpmaboscounter));
        cikarmatoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intcikarmasoru+cikarmasorucounter));
        cikarmadogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intcikarmadogru+cikarmadogrucounter));
        cikarmayanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intcikarmayanlis+cikarmayanliscounter));
        cikarmabos.setText(getString(R.string.unanswered)+String.valueOf(intcikarmabos+cikarmaboscounter));
        buyukkucuktoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intbuyukkucuksoru+buyukucuksorucounter));
        buyukkucukdogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intbuyukkucukdogru+buyukucukdogrucounter));
        buyukkucukyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intbuyukkucukyanlis+buyukucukyanliscounter));
        buyukkucukbos.setText(getString(R.string.unanswered)+String.valueOf(intbuyukkucukbos+buyukkucukboscounter));
        ritmiktoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intritmiksoru+ritmiksorucounter));
        ritmikdogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intritmikdogru+ritmikdogrucounter));
        ritmikyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intritmikyanlis+ritmikyanliscounter));
        ritmikbos.setText(getString(R.string.unanswered)+String.valueOf(intritmikbos+ritmikboscounter));
        simetritoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intsimetrisoru+simetrisorucounter));
        simetridogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intsimetridogru+simetridogrucounter));
        simetriyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intsimetriyanlis+simetriyanliscounter));
        simetribos.setText(getString(R.string.unanswered)+String.valueOf(intsimetribos+simetriboscounter));
        toplambalon.setText(getString(R.string.patlatilanbalonh)+String.valueOf(inttoplambalon+baloncounter));
        dogrubalon.setText(getString(R.string.dogrubalonh)+String.valueOf(intdogrubalon+dogrubaloncounter));
        yanlisbalon.setText(getString(R.string.hatalibalonh)+String.valueOf(intyanlisbalon+yanlisbaloncounter));


        sp = getSharedPreferences("skorbilgileri", Activity.MODE_PRIVATE);
        spEditor = sp.edit();

        spEditor.putInt("toplamasoru", topsorucounter+inttopsoru);
        spEditor.putInt("toplamadogru",topdogrucounter+inttopdogru);
        spEditor.putInt("toplamayanlis",topyanliscounter+inttopyanlis);
        spEditor.putInt("toplamabos",topboscounter+inttopbos);
        spEditor.putInt("bolmesoru", bolsorucounter+intbolsoru);
        spEditor.putInt("bolmedogru",boldogrucounter+intboldogru);
        spEditor.putInt("bolmeyanlis",bolyanliscounter+intbolyanlis);
        spEditor.putInt("bolmebos",bolboscounter+intbolbos);
        spEditor.putInt("carpmasoru", carpmasorucounter+intcarpmasoru);
        spEditor.putInt("carpmadogru",carpmadogrucounter+intcarpmadogru);
        spEditor.putInt("carpmayanlis",carpmayanliscounter+intcarpmayanlis);
        spEditor.putInt("carpmabos",carpmaboscounter+intcarpmabos);
        spEditor.putInt("cikarmasoru", cikarmasorucounter+intcikarmasoru);
        spEditor.putInt("cikarmadogru",cikarmadogrucounter+intcikarmadogru);
        spEditor.putInt("cikarmayanlis",cikarmayanliscounter+intcikarmayanlis);
        spEditor.putInt("cikarmabos",cikarmaboscounter+intcikarmabos);
        spEditor.putInt("buyukkucuksoru", buyukucuksorucounter+intbuyukkucuksoru);
        spEditor.putInt("buyukkucukdogru",buyukucukdogrucounter+intbuyukkucukdogru);
        spEditor.putInt("buyukkucukyanlis",buyukucukyanliscounter+intbuyukkucukyanlis);
        spEditor.putInt("buyukkucukbos",buyukkucukboscounter+intbuyukkucukbos);
        spEditor.putInt("ritmiksoru", ritmiksorucounter+intritmiksoru);
        spEditor.putInt("ritmikdogru",ritmikdogrucounter+intritmikdogru);
        spEditor.putInt("ritmikyanlis",ritmikyanliscounter+intritmikyanlis);
        spEditor.putInt("ritmikbos",ritmikboscounter+intritmikbos);
        spEditor.putInt("simetrisoru", simetrisorucounter+intsimetrisoru);
        spEditor.putInt("simetridogru",simetridogrucounter+intsimetridogru);
        spEditor.putInt("simetriyanlis",simetriyanliscounter+intsimetriyanlis);
        spEditor.putInt("simetribos",simetriboscounter+intsimetribos);
        spEditor.putInt("toplambalon", baloncounter+inttoplambalon);
        spEditor.putInt("dogrubalon",dogrubaloncounter+intdogrubalon);
        spEditor.putInt("yanlisbalon",yanlisbaloncounter+intyanlisbalon);
        spEditor.commit();


        toplamabar = (ProgressBar)findViewById(R.id.toplamaBar);
        toplamabar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#C0D000"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        int x = sp.getInt("toplamadogru",0);
        int y = sp.getInt("toplamasoru",0);
        float topbasari;
        if (y ==0) {
            topbasari = 0;
        }else{
            topbasari = x*100/y;

        }
        toplamabar.setMax(100);
        toplamabar.setProgress((int)topbasari);

        TextView toplamascore = (TextView)findViewById(R.id.scoretoplamaTxt);
        toplamascore.setText(getString(R.string.bolumpuani)+String.valueOf((int)topbasari));
        /////////////////////////////////////////////////
        cikarmabar = (ProgressBar)findViewById(R.id.cikarmaBar);
        cikarmabar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xcikarma = sp.getInt("cikarmadogru",0);
        int ycikarma = sp.getInt("cikarmasoru",0);
        float cikarmabasari;
        if (ycikarma ==0) {
            cikarmabasari = 0;
        }else{
            cikarmabasari = xcikarma*100/ycikarma;
        }
        cikarmabar.setMax(100);
        cikarmabar.setProgress((int)cikarmabasari);

        TextView cikarmascore = (TextView)findViewById(R.id.scorecikarmaTxt);
        cikarmascore.setText(getString(R.string.bolumpuani)+String.valueOf((int)cikarmabasari));
        /////////////////////////////////////////////////
        carpmabar = (ProgressBar)findViewById(R.id.carpmaBar);
        carpmabar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xcarpma = sp.getInt("carpmadogru",0);
        int ycarpma = sp.getInt("carpmasoru",0);
        float carpmabasari;
        if (ycarpma ==0) {
            carpmabasari= 0;
        }else{
            carpmabasari = xcarpma*100/ycarpma;
        }
        carpmabar.setMax(100);
        carpmabar.setProgress((int)carpmabasari);

        TextView carpmascore = (TextView)findViewById(R.id.scorecarpmaTxt);
        carpmascore.setText(getString(R.string.bolumpuani)+String.valueOf((int)carpmabasari));
        /////////////////////////////////////////////////
        bolmebar = (ProgressBar)findViewById(R.id.bolmeBar);
        bolmebar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xbolme = sp.getInt("bolmedogru",0);
        int ybolme = sp.getInt("bolmesoru",0);
        float bolmebasari;
        if (ybolme ==0) {
            bolmebasari= 0;
        }else{
            bolmebasari = xbolme*100/ybolme;
        }
        bolmebar.setMax(100);
        bolmebar.setProgress((int)bolmebasari);

        TextView bolmescore = (TextView)findViewById(R.id.scorebolmeTxt);
        bolmescore.setText(getString(R.string.bolumpuani)+String.valueOf((int)bolmebasari));
        /////////////////////////////////////////////////
        ritmikbar = (ProgressBar)findViewById(R.id.ritmikBar);
        ritmikbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xritmik = sp.getInt("ritmikdogru",0);
        int yritmik = sp.getInt("ritmiksoru",0);
        float ritmikbasari;
        if (yritmik ==0) {
            ritmikbasari= 0;
        }else{
            ritmikbasari = xritmik*100/yritmik;
        }
        ritmikbar.setMax(100);
        ritmikbar.setProgress((int)ritmikbasari);

        TextView ritmikscore = (TextView)findViewById(R.id.scoreritmikTxt);
        ritmikscore.setText(getString(R.string.bolumpuani)+String.valueOf((int)ritmikbasari));
        /////////////////////////////////////////////////
        tekciftbar = (ProgressBar)findViewById(R.id.tekciftBar);
        tekciftbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xtekcift = sp.getInt("dogrubalon",0);
        int ytekcift = sp.getInt("toplambalon",0);
        float tekciftbasari;
        if (ytekcift ==0) {
            tekciftbasari= 0;
        }else{
            tekciftbasari = xtekcift*100/ytekcift;
        }
        tekciftbar.setMax(100);
        tekciftbar.setProgress((int)tekciftbasari);

        TextView tekciftscore = (TextView)findViewById(R.id.scoretekciftTxt);
        tekciftscore.setText(getString(R.string.bolumpuani)+String.valueOf((int)tekciftbasari));
        /////////////////////////////////////////////////
        simetribar = (ProgressBar)findViewById(R.id.simetriBar);
        simetribar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xsimetri = sp.getInt("simetridogru",0);
        int ysimetri = sp.getInt("simetrisoru",0);
        float simetribasari;
        if (ysimetri ==0) {
            simetribasari= 0;
        }else{
            simetribasari = xsimetri*100/ysimetri;
        }
        simetribar.setMax(100);
        simetribar.setProgress((int)simetribasari);

        TextView simetriscore = (TextView)findViewById(R.id.scoresimetriTxt);
        simetriscore.setText(getString(R.string.bolumpuani)+String.valueOf((int)simetribasari));
        /////////////////////////////////////////////////
        buyukkucukbar = (ProgressBar)findViewById(R.id.buyukkucukBar);
        buyukkucukbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1aff05"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        int xbuyukkucuk = sp.getInt("buyukkucukdogru",0);
        int ybuyukkucuk = sp.getInt("buyukkucuksoru",0);
        float buyukkucukbasari;
        if (ybuyukkucuk ==0) {
            buyukkucukbasari= 0;
        }else{
            buyukkucukbasari = xbuyukkucuk*100/ybuyukkucuk;
        }
        buyukkucukbar.setMax(100);
        buyukkucukbar.setProgress((int)buyukkucukbasari);

        TextView buyukkucukscore = (TextView)findViewById(R.id.scorebuyukkucukTxt);
        buyukkucukscore.setText(getString(R.string.bolumpuani)+String.valueOf((int)buyukkucukbasari));

        ImageView toplamayildiz1 = (ImageView)findViewById(R.id.gifRandom11);
        ImageView toplamayildiz2 = (ImageView)findViewById(R.id.gifRandom12);
        ImageView toplamayildiz3 = (ImageView)findViewById(R.id.gifRandom13);
        ImageView toplamayildiz4 = (ImageView)findViewById(R.id.gifRandom14);
        ImageView toplamayildiz5 = (ImageView)findViewById(R.id.gifRandom15);

        ImageView cikarmayildiz1 = (ImageView)findViewById(R.id.gifRandom21);
        ImageView cikarmayildiz2 = (ImageView)findViewById(R.id.gifRandom22);
        ImageView cikarmayildiz3 = (ImageView)findViewById(R.id.gifRandom23);
        ImageView cikarmayildiz4 = (ImageView)findViewById(R.id.gifRandom24);
        ImageView cikarmayildiz5 = (ImageView)findViewById(R.id.gifRandom25);

        ImageView bolmeyildiz1 = (ImageView)findViewById(R.id.gifRandom31);
        ImageView bolmeyildiz2 = (ImageView)findViewById(R.id.gifRandom32);
        ImageView bolmeyildiz3 = (ImageView)findViewById(R.id.gifRandom33);
        ImageView bolmeyildiz4 = (ImageView)findViewById(R.id.gifRandom34);
        ImageView bolmeyildiz5 = (ImageView)findViewById(R.id.gifRandom35);

        ImageView carpmayildiz1 = (ImageView)findViewById(R.id.gifRandom41);
        ImageView carpmayildiz2 = (ImageView)findViewById(R.id.gifRandom42);
        ImageView carpmayildiz3 = (ImageView)findViewById(R.id.gifRandom43);
        ImageView carpmayildiz4 = (ImageView)findViewById(R.id.gifRandom44);
        ImageView carpmayildiz5 = (ImageView)findViewById(R.id.gifRandom45);

        ImageView ritmikyildiz1 = (ImageView)findViewById(R.id.gifRandom51);
        ImageView ritmikyildiz2 = (ImageView)findViewById(R.id.gifRandom52);
        ImageView ritmikyildiz3 = (ImageView)findViewById(R.id.gifRandom53);
        ImageView ritmikyildiz4 = (ImageView)findViewById(R.id.gifRandom54);
        ImageView ritmikyildiz5 = (ImageView)findViewById(R.id.gifRandom55);

        ImageView buyukkucukyildiz1 = (ImageView)findViewById(R.id.gifRandom61);
        ImageView buyukkucukyildiz2 = (ImageView)findViewById(R.id.gifRandom62);
        ImageView buyukkucukyildiz3 = (ImageView)findViewById(R.id.gifRandom63);
        ImageView buyukkucukyildiz4 = (ImageView)findViewById(R.id.gifRandom64);
        ImageView buyukkucukyildiz5 = (ImageView)findViewById(R.id.gifRandom65);

        ImageView tekciftyildiz1 = (ImageView)findViewById(R.id.gifRandom71);
        ImageView tekciftyildiz2 = (ImageView)findViewById(R.id.gifRandom72);
        ImageView tekciftyildiz3 = (ImageView)findViewById(R.id.gifRandom73);
        ImageView tekciftyildiz4 = (ImageView)findViewById(R.id.gifRandom74);
        ImageView tekciftyildiz5 = (ImageView)findViewById(R.id.gifRandom75);

        ImageView simetriyildiz1 = (ImageView)findViewById(R.id.gifRandom81);
        ImageView simetriyildiz2 = (ImageView)findViewById(R.id.gifRandom82);
        ImageView simetriyildiz3 = (ImageView)findViewById(R.id.gifRandom83);
        ImageView simetriyildiz4 = (ImageView)findViewById(R.id.gifRandom84);
        ImageView simetriyildiz5 = (ImageView)findViewById(R.id.gifRandom85);

        if (topbasari<20){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.INVISIBLE);
            toplamayildiz3.setVisibility(View.INVISIBLE);
            toplamayildiz4.setVisibility(View.INVISIBLE);
            toplamayildiz5.setVisibility(View.INVISIBLE);
        }
        if (topbasari>20 && topbasari<40){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.VISIBLE);
            toplamayildiz3.setVisibility(View.INVISIBLE);
            toplamayildiz4.setVisibility(View.INVISIBLE);
            toplamayildiz5.setVisibility(View.INVISIBLE);
        }
        if (topbasari>40 && topbasari<60){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.VISIBLE);
            toplamayildiz3.setVisibility(View.VISIBLE);
            toplamayildiz4.setVisibility(View.INVISIBLE);
            toplamayildiz5.setVisibility(View.INVISIBLE);
        }
        if (topbasari>60 && topbasari<80){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.VISIBLE);
            toplamayildiz3.setVisibility(View.VISIBLE);
            toplamayildiz4.setVisibility(View.VISIBLE);
            toplamayildiz5.setVisibility(View.INVISIBLE);
        }
        if (topbasari>80 ){
            toplamayildiz1.setVisibility(View.VISIBLE);
            toplamayildiz2.setVisibility(View.VISIBLE);
            toplamayildiz3.setVisibility(View.VISIBLE);
            toplamayildiz4.setVisibility(View.VISIBLE);
            toplamayildiz5.setVisibility(View.VISIBLE);
        }

        if (cikarmabasari<20){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.INVISIBLE);
            cikarmayildiz3.setVisibility(View.INVISIBLE);
            cikarmayildiz4.setVisibility(View.INVISIBLE);
            cikarmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (cikarmabasari>20 && cikarmabasari<40){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.VISIBLE);
            cikarmayildiz3.setVisibility(View.INVISIBLE);
            cikarmayildiz4.setVisibility(View.INVISIBLE);
            cikarmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (cikarmabasari>40 && cikarmabasari<60){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.VISIBLE);
            cikarmayildiz3.setVisibility(View.VISIBLE);
            cikarmayildiz4.setVisibility(View.INVISIBLE);
            cikarmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (cikarmabasari>60 && cikarmabasari<80){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.VISIBLE);
            cikarmayildiz3.setVisibility(View.VISIBLE);
            cikarmayildiz4.setVisibility(View.VISIBLE);
            cikarmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (cikarmabasari>80 ){
            cikarmayildiz1.setVisibility(View.VISIBLE);
            cikarmayildiz2.setVisibility(View.VISIBLE);
            cikarmayildiz3.setVisibility(View.VISIBLE);
            cikarmayildiz4.setVisibility(View.VISIBLE);
            cikarmayildiz5.setVisibility(View.VISIBLE);
        }




        if (bolmebasari<20){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.INVISIBLE);
            bolmeyildiz3.setVisibility(View.INVISIBLE);
            bolmeyildiz4.setVisibility(View.INVISIBLE);
            bolmeyildiz5.setVisibility(View.INVISIBLE);
        }
        if (bolmebasari>20 && bolmebasari<40){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.VISIBLE);
            bolmeyildiz3.setVisibility(View.INVISIBLE);
            bolmeyildiz4.setVisibility(View.INVISIBLE);
            bolmeyildiz5.setVisibility(View.INVISIBLE);
        }
        if (bolmebasari>40 && bolmebasari<60){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.VISIBLE);
            bolmeyildiz3.setVisibility(View.VISIBLE);
            bolmeyildiz4.setVisibility(View.INVISIBLE);
            bolmeyildiz5.setVisibility(View.INVISIBLE);
        }
        if (bolmebasari>60 && bolmebasari<80){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.VISIBLE);
            bolmeyildiz3.setVisibility(View.VISIBLE);
            bolmeyildiz4.setVisibility(View.VISIBLE);
            bolmeyildiz5.setVisibility(View.INVISIBLE);
        }
        if (bolmebasari>80){
            bolmeyildiz1.setVisibility(View.VISIBLE);
            bolmeyildiz2.setVisibility(View.VISIBLE);
            bolmeyildiz3.setVisibility(View.VISIBLE);
            bolmeyildiz4.setVisibility(View.VISIBLE);
            bolmeyildiz5.setVisibility(View.VISIBLE);
        }



        if (carpmabasari<20){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.INVISIBLE);
            carpmayildiz3.setVisibility(View.INVISIBLE);
            carpmayildiz4.setVisibility(View.INVISIBLE);
            carpmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (carpmabasari>20 && carpmabasari<40){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.VISIBLE);
            carpmayildiz3.setVisibility(View.INVISIBLE);
            carpmayildiz4.setVisibility(View.INVISIBLE);
            carpmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (carpmabasari>40 && carpmabasari<60){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.VISIBLE);
            carpmayildiz3.setVisibility(View.VISIBLE);
            carpmayildiz4.setVisibility(View.INVISIBLE);
            carpmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (carpmabasari>60 && carpmabasari<80){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.VISIBLE);
            carpmayildiz3.setVisibility(View.VISIBLE);
            carpmayildiz4.setVisibility(View.VISIBLE);
            carpmayildiz5.setVisibility(View.INVISIBLE);
        }
        if (carpmabasari>80 ){
            carpmayildiz1.setVisibility(View.VISIBLE);
            carpmayildiz2.setVisibility(View.VISIBLE);
            carpmayildiz3.setVisibility(View.VISIBLE);
            carpmayildiz4.setVisibility(View.VISIBLE);
            carpmayildiz5.setVisibility(View.VISIBLE);
        }


        if (buyukkucukbasari<20){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.INVISIBLE);
            buyukkucukyildiz3.setVisibility(View.INVISIBLE);
            buyukkucukyildiz4.setVisibility(View.INVISIBLE);
            buyukkucukyildiz5.setVisibility(View.INVISIBLE);
        }
        if (buyukkucukbasari>20 && buyukkucukbasari<40){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.VISIBLE);
            buyukkucukyildiz3.setVisibility(View.INVISIBLE);
            buyukkucukyildiz4.setVisibility(View.INVISIBLE);
            buyukkucukyildiz5.setVisibility(View.INVISIBLE);
        }
        if (buyukkucukbasari>40 && buyukkucukbasari<60){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.VISIBLE);
            buyukkucukyildiz3.setVisibility(View.VISIBLE);
            buyukkucukyildiz4.setVisibility(View.INVISIBLE);
            buyukkucukyildiz5.setVisibility(View.INVISIBLE);
        }
        if (buyukkucukbasari>60 && buyukkucukbasari<80){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.VISIBLE);
            buyukkucukyildiz3.setVisibility(View.VISIBLE);
            buyukkucukyildiz4.setVisibility(View.VISIBLE);
            buyukkucukyildiz5.setVisibility(View.INVISIBLE);
        }
        if (buyukkucukbasari>80){
            buyukkucukyildiz1.setVisibility(View.VISIBLE);
            buyukkucukyildiz2.setVisibility(View.VISIBLE);
            buyukkucukyildiz3.setVisibility(View.VISIBLE);
            buyukkucukyildiz4.setVisibility(View.VISIBLE);
            buyukkucukyildiz5.setVisibility(View.VISIBLE);
        }



        if (ritmikbasari<20){
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.INVISIBLE);
            ritmikyildiz3.setVisibility(View.INVISIBLE);
            ritmikyildiz4.setVisibility(View.INVISIBLE);
            ritmikyildiz5.setVisibility(View.INVISIBLE);
        }
        if (ritmikbasari>20 && ritmikbasari<40 ){
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.VISIBLE);
            ritmikyildiz3.setVisibility(View.INVISIBLE);
            ritmikyildiz4.setVisibility(View.INVISIBLE);
            ritmikyildiz5.setVisibility(View.INVISIBLE);
        }
        if (ritmikbasari>40 && ritmikbasari<60 ){
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.VISIBLE);
            ritmikyildiz3.setVisibility(View.VISIBLE);
            ritmikyildiz4.setVisibility(View.INVISIBLE);
            ritmikyildiz5.setVisibility(View.INVISIBLE);
        }
        if (ritmikbasari>60 && ritmikbasari<80 ){
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.VISIBLE);
            ritmikyildiz3.setVisibility(View.VISIBLE);
            ritmikyildiz4.setVisibility(View.VISIBLE);
            ritmikyildiz5.setVisibility(View.INVISIBLE);
        }
        if (ritmikbasari>80) {
            ritmikyildiz1.setVisibility(View.VISIBLE);
            ritmikyildiz2.setVisibility(View.VISIBLE);
            ritmikyildiz3.setVisibility(View.VISIBLE);
            ritmikyildiz4.setVisibility(View.VISIBLE);
            ritmikyildiz5.setVisibility(View.VISIBLE);
        }

        if (tekciftbasari<20){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.INVISIBLE);
            tekciftyildiz3.setVisibility(View.INVISIBLE);
            tekciftyildiz4.setVisibility(View.INVISIBLE);
            tekciftyildiz5.setVisibility(View.INVISIBLE);
        }
        if (tekciftbasari>20 && tekciftbasari<40){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.VISIBLE);
            tekciftyildiz3.setVisibility(View.INVISIBLE);
            tekciftyildiz4.setVisibility(View.INVISIBLE);
            tekciftyildiz5.setVisibility(View.INVISIBLE);
        }
        if (tekciftbasari>40 && tekciftbasari<60){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.VISIBLE);
            tekciftyildiz3.setVisibility(View.VISIBLE);
            tekciftyildiz4.setVisibility(View.INVISIBLE);
            tekciftyildiz5.setVisibility(View.INVISIBLE);
        }
        if (tekciftbasari>60 && tekciftbasari<80){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.VISIBLE);
            tekciftyildiz3.setVisibility(View.VISIBLE);
            tekciftyildiz4.setVisibility(View.VISIBLE);
            tekciftyildiz5.setVisibility(View.INVISIBLE);
        }
        if (tekciftbasari>80){
            tekciftyildiz1.setVisibility(View.VISIBLE);
            tekciftyildiz2.setVisibility(View.VISIBLE);
            tekciftyildiz3.setVisibility(View.VISIBLE);
            tekciftyildiz4.setVisibility(View.VISIBLE);
            tekciftyildiz5.setVisibility(View.VISIBLE);
        }
        if (simetribasari<20){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.INVISIBLE);
            simetriyildiz3.setVisibility(View.INVISIBLE);
            simetriyildiz4.setVisibility(View.INVISIBLE);
            simetriyildiz5.setVisibility(View.INVISIBLE);
        }
        if (simetribasari>20 && simetribasari<40){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.VISIBLE);
            simetriyildiz3.setVisibility(View.INVISIBLE);
            simetriyildiz4.setVisibility(View.INVISIBLE);
            simetriyildiz5.setVisibility(View.INVISIBLE);
        }
        if (simetribasari>40 && simetribasari<60){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.VISIBLE);
            simetriyildiz3.setVisibility(View.VISIBLE);
            simetriyildiz4.setVisibility(View.INVISIBLE);
            simetriyildiz5.setVisibility(View.INVISIBLE);
        }
        if (simetribasari>60 && simetribasari<80){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.VISIBLE);
            simetriyildiz3.setVisibility(View.VISIBLE);
            simetriyildiz4.setVisibility(View.VISIBLE);
            simetriyildiz5.setVisibility(View.INVISIBLE);
        }
        if (simetribasari>80 ){
            simetriyildiz1.setVisibility(View.VISIBLE);
            simetriyildiz2.setVisibility(View.VISIBLE);
            simetriyildiz3.setVisibility(View.VISIBLE);
            simetriyildiz4.setVisibility(View.VISIBLE);
            simetriyildiz5.setVisibility(View.VISIBLE);
        }

/////////////////////////////

/////////////////////////////////////

    }



    public void toplamaact(View view) {
        Intent i = new Intent(HomeActivity.this, toplamaActivity.class);startActivity(i);
    }

    public void ritmikact(View view) {
        Intent i = new Intent(HomeActivity.this, RitmiksaymaActivity.class);startActivity(i);
    }
    public void cikarma(View view) {
        Intent i = new Intent(HomeActivity.this, CikarmaActivity.class);startActivity(i);
    }
    public void carpma(View view) {
        Intent i = new Intent(HomeActivity.this, CarpmaActivity.class);startActivity(i);
    }
    public void bolme(View view) {
        Intent i = new Intent(HomeActivity.this, BolmeActivity.class);startActivity(i);
    }
    public void buyukkucuk(View view) {
        Intent i = new Intent(HomeActivity.this, BuyukKucukActivity.class);startActivity(i);
    }
    public void tekcift(View view) {
        Intent i = new Intent(HomeActivity.this, TekCiftActivity.class);startActivity(i);
    }
    public void simetry(View view) {
        Intent i = new Intent(HomeActivity.this, SimetryActivity.class);startActivity(i);
    }

    @Override
    public void onBackPressed() {

        Button appcikisBtn = (Button)findViewById(R.id.appcikisBtn);
        ScrollView konular = (ScrollView)findViewById(R.id.konularlayout);


        RelativeLayout konularmainlayout = (RelativeLayout) findViewById(R.id.konularmainlayout);
        RelativeLayout byebyegif =  (RelativeLayout)findViewById(R.id.byebyegiflayout);
        final RelativeLayout gulegule =  (RelativeLayout)findViewById(R.id.gulegulelayout);
        final RelativeLayout disclaimerlayout = (RelativeLayout) findViewById(R.id.disclaimerlayout);
        Button disclaimerBtn = (Button)findViewById(R.id.disclaimerBtn);
        Button disclaimundstand = (Button)findViewById(R.id.understanddisclaim);

        appcikisBtn.setVisibility(View.VISIBLE);
        konular.setVisibility(View.GONE);
        byebyegif.setVisibility(View.VISIBLE);

         Button apprate = (Button) findViewById(R.id.rateBtn);

         SharedPreferences sp = getSharedPreferences("rateclick", Activity.MODE_PRIVATE);
        Integer i = sp.getInt("rateclick",0);
        Log.i("sayi",String.valueOf(i));
        if (i==0) {
            apprate.setVisibility(View.VISIBLE);
        }else {
            apprate.setVisibility(View.INVISIBLE);
            apprate.setEnabled(false);
        }

        disclaimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disclaimerlayout.setVisibility(View.VISIBLE);
                gulegule.setVisibility(View.GONE);
            }
        });

        disclaimundstand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disclaimerlayout.setVisibility(View.GONE);
                gulegule.setVisibility(View.VISIBLE);
            }
        });

    }



    public void appcikis (View view) {

        this.finishAffinity();
    }

    public void rateapp (View view) {

        SharedPreferences sp = getSharedPreferences("rateclick", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor = sp.edit();

        spEditor.putInt("rateclick",1);
        spEditor.commit();
        Integer ii = sp.getInt("rateclick",0);
        Log.i("sayi2",String.valueOf(ii));

        Uri uri = Uri.parse("market://details?id="+ getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));

        }
    }

}

