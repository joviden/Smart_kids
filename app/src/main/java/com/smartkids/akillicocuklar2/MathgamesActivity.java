package com.smartkids.akillicocuklar2;

/**
 * Created by joviden on 03.02.2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;




public class MathgamesActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private AdView mAdView;
    private ProgressBar toplamabar = null; private ProgressBar cikarmabar = null;private ProgressBar carpmabar = null;private ProgressBar bolmebar = null;private ProgressBar ritmikbar = null;
    private ProgressBar buyukkucukbar= null;private ProgressBar tekciftbar = null;private ProgressBar simetribar= null;

    private GoogleApiClient mGoogleApiClient;
    float topbasari;
    Integer intboldogru;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mathgameslayout);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //Toast.makeText(getApplicationContext(),"sorunvar",Toast.LENGTH_SHORT).show();
                    }
                }).build();






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
        ritmikscore.setText(getString(R.string.bolumbasari)+String.valueOf((int)ritmikbasari));
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
        tekciftscore.setText(getString(R.string.bolumbasari)+String.valueOf((int)tekciftbasari));
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
        simetriscore.setText(getString(R.string.bolumbasari)+String.valueOf((int)simetribasari));
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
        buyukkucukscore.setText(getString(R.string.bolumbasari)+String.valueOf((int)buyukkucukbasari));



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

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }





    public void onResume() {
        super.onResume();









        final Integer inttopsoru,inttopdogru,inttopyanlis,topsorucounter,topdogrucounter,topyanliscounter,
                intbolsoru,intboldogru,intbolyanlis,bolsorucounter,boldogrucounter,bolyanliscounter,
                inttoplamaleader,intcikarmaleader,intcarpmaleader,intbolmeleader,intritmikleader,intbuyukkucukleader,inttekciftleader,intsimetrileader,
                carpmasorucounter,carpmadogrucounter,carpmayanliscounter,intcarpmadogru,intcarpmayanlis,intcarpmasoru,
                cikarmayanliscounter,cikarmasorucounter,cikarmadogrucounter,intcikarmasoru,intcikarmadogru,intcikarmayanlis,
                buyukucuksorucounter,buyukucukdogrucounter,buyukucukyanliscounter,intbuyukkucuksoru,intbuyukkucukdogru,intbuyukkucukyanlis,
                ritmiksorucounter,ritmikdogrucounter,ritmikyanliscounter,intritmiksoru,intritmikdogru,intritmikyanlis,
                simetrisorucounter,simetridogrucounter,simetriyanliscounter,intsimetrisoru,intsimetridogru,intsimetriyanlis,
                baloncounter,dogrubaloncounter,yanlisbaloncounter,inttoplambalon,intdogrubalon,intyanlisbalon,inttopbos,intbolbos,intcarpmabos,intcikarmabos,intbuyukkucukbos,intritmikbos,intsimetribos,bolboscounter,
                simetriboscounter,topboscounter,buyukkucukboscounter,carpmaboscounter,cikarmaboscounter,ritmikboscounter,
                toplamaleader,cikarmaleader,carpmaleader,bolmeleader,ritmikleader,buyukkucukleader,tekciftleader,simetrileader;


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





        //////////////////RİTMİK SKOR BİLGİLERİ//////////////////

        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        intritmiksoru = sp.getInt("ritmiksoru",0);
        intritmikdogru= sp.getInt("ritmikdogru",0);
        intritmikyanlis = sp.getInt("ritmikyanlis",0);
        intritmikbos = sp.getInt("ritmikbos",0);
        intritmikleader = sp.getInt("ritmikleader",0);

        ritmiktoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intritmiksoru));
        ritmikdogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intritmikdogru));
        ritmikyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intritmikyanlis));
        ritmikbos.setText(getString(R.string.unanswered)+String.valueOf(intritmikbos));

        /////////////////leader board
        Button ritmikleaderbtn = (Button)findViewById(R.id.ritmikleader);
        ritmikleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoogleApiClient.isConnected()){
                    //         Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
                    Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_serials_stars),intritmikleader);
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,getString(R.string.leaderboard_serials_stars)),0);
                }else {
                    mGoogleApiClient.connect();
                    //         Toast.makeText(getApplicationContext(),"baglı degil",Toast.LENGTH_SHORT).show();

                }}
        });



        //////////////RİTMİK BİTTİ////////////////////


//////////////////BÜYÜKKÜCÜK SKOR BİLGİLERİ//////////////////
        intbuyukkucuksoru = sp.getInt("buyukkucuksoru",0);
        intbuyukkucukdogru= sp.getInt("buyukkucukdogru",0);
        intbuyukkucukyanlis = sp.getInt("buyukkucukyanlis",0);
        intbuyukkucukbos = sp.getInt("buyukkucukbos",0);
        intbuyukkucukleader = sp.getInt("buyukkucukleader",0);

        buyukkucuktoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intbuyukkucuksoru));
        buyukkucukdogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intbuyukkucukdogru));
        buyukkucukyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intbuyukkucukyanlis));
        buyukkucukbos.setText(getString(R.string.unanswered)+String.valueOf(intbuyukkucukbos));

        /////////////////leader board
        Button buyukkucukleaderbtn = (Button)findViewById(R.id.buyukkucukleader);
        buyukkucukleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoogleApiClient.isConnected()){
                    //        Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
                    Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_greater__lesser_stars),intbuyukkucukleader);
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,getString(R.string.leaderboard_greater__lesser_stars)),0);
                }else {
                    mGoogleApiClient.connect();
                    //           Toast.makeText(getApplicationContext(),"baglı degil",Toast.LENGTH_SHORT).show();

                }}
        });




        //////////////////BÜYÜKKÜCÜK  BİTTİ//////////////////

//////////////////TEK ÇİFT SKOR BİLGİLERİ//////////////////

        inttoplambalon = sp.getInt("toplambalon",0);
        intdogrubalon= sp.getInt("dogrubalon",0);
        intyanlisbalon = sp.getInt("yanlisbalon",0);
        inttekciftleader = sp.getInt("balonleader",0);


        toplambalon.setText(getString(R.string.patlatilanbalonh)+String.valueOf(inttoplambalon));
        dogrubalon.setText(getString(R.string.dogrubalonh)+String.valueOf(intdogrubalon));
        yanlisbalon.setText(getString(R.string.hatalibalonh)+String.valueOf(intyanlisbalon));

        /////////////////leader board
        Button tekciftleaderbtn = (Button)findViewById(R.id.tekciftleader);
        tekciftleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoogleApiClient.isConnected()){
                    //           Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
                    Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_odd__even_stars),inttekciftleader);
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,getString(R.string.leaderboard_odd__even_stars)),0);
                }else {
                    mGoogleApiClient.connect();
                    //            Toast.makeText(getApplicationContext(),"baglı degil",Toast.LENGTH_SHORT).show();

                }}
        });



        //////////////////TEK ÇİFT  BİTTİ//////////////////

        //////////////////SİMETRİ SKOR BİLGİLERİ//////////////////


        intsimetrisoru = sp.getInt("simetrisoru",0);
        intsimetridogru= sp.getInt("simetridogru",0);
        intsimetriyanlis = sp.getInt("simetriyanlis",0);
        intsimetribos = sp.getInt("simetribos",0);
        intsimetrileader = sp.getInt("simetrileader",0);


        simetritoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intsimetrisoru));
        simetridogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intsimetridogru));
        simetriyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intsimetriyanlis));
        simetribos.setText(getString(R.string.unanswered)+String.valueOf(intsimetribos));

        /////////////////leader board
        Button simetrileaderbtn = (Button)findViewById(R.id.simetrileader);
        simetrileaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoogleApiClient.isConnected()){
                    //          Toast.makeText(getApplicationContext(),"baglı",Toast.LENGTH_SHORT).show();
                    Games.Leaderboards.submitScore(mGoogleApiClient,getString(R.string.leaderboard_symmetry_stars),intsimetrileader);
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,getString(R.string.leaderboard_symmetry_stars)),0);
                }else {
                    mGoogleApiClient.connect();
                    //          Toast.makeText(getApplicationContext(),"baglı degil",Toast.LENGTH_SHORT).show();

                }}
        });




        ////////////////// SİMETRİ  BİTTİ//////////////////





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
        ritmikscore.setText(getString(R.string.bolumbasari)+String.valueOf((int)ritmikbasari));
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
        tekciftscore.setText(getString(R.string.bolumbasari)+String.valueOf((int)tekciftbasari));
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
        simetriscore.setText(getString(R.string.bolumbasari)+String.valueOf((int)simetribasari));
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
        buyukkucukscore.setText(getString(R.string.bolumbasari)+String.valueOf((int)buyukkucukbasari));



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

        //if (topbasari>0) {
        //   Games.Leaderboards.submitScore(apiClient,getString(R.string.leaderboard_score_table),(int)topbasari);

        // Button topleaderbtn = (Button)findViewById(R.id.toplamaleader);
        //topleaderbtn.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //public void onClick(View view) {
        //  startActivityForResult(Games.Leaderboards.getLeaderboardIntent(apiClient,getString(R.string.leaderboard_score_table)),0);
        //}
        //});
        //}

/////////////////////////////////////

    }







    public void ritmikact(View view) {
        Intent i = new Intent(MathgamesActivity.this, RitmiksaymaActivity.class);startActivity(i);
    }

    public void buyukkucuk(View view) {
        Intent i = new Intent(MathgamesActivity.this, BuyukKucukActivity.class);startActivity(i);
    }
    public void tekcift(View view) {
        Intent i = new Intent(MathgamesActivity.this, TekCiftActivity.class);startActivity(i);
    }
    public void simetry(View view) {
        Intent i = new Intent(MathgamesActivity.this, SimetryActivity.class);startActivity(i);
    }











    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MathgamesActivity.this, MainmenuActivity.class);startActivity(i);

    }



}


