package com.smartkids.akillicocuklar2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
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


/**
 * Created by joviden on 03.12.2017.
 */

public class TrainingActivity extends AppCompatActivity  {

    private AdView mAdView;
    private ProgressBar toplamabar = null; private ProgressBar cikarmabar = null;private ProgressBar carpmabar = null;private ProgressBar bolmebar = null;private ProgressBar ritmikbar = null;
    private ProgressBar buyukkucukbar= null;private ProgressBar tekciftbar = null;private ProgressBar simetribar= null;

    private static final int RC_SIGN_IN = 9001;
    private static final int RC_LEADERBOARD_UI = 9004;


    float topbasari;
    Integer intboldogru;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traininglayout);


        signInSilently();



/*
        Button topleaderbtn = (Button)findViewById(R.id.toplamaleader);
        topleaderbtn.setOnClickListener(new View.OnClickListener() {
          @Override
        public void onClick(View view) {


        }
        });

*/

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




        toplamabar = (ProgressBar)findViewById(R.id.toplamaBar);
        toplamabar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#C0D000"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        int x = sp.getInt("toplamadogru",0);
        int y = sp.getInt("toplamasoru",0);

        if (y ==0) {
             topbasari = 0;
        }else{
            topbasari = x*100/y;

        }
        toplamabar.setMax(100);
        toplamabar.setProgress((int)topbasari);

        TextView toplamascore = (TextView)findViewById(R.id.scoretoplamaTxt);
        toplamascore.setText(getString(R.string.bolumbasari)+String.valueOf((int)topbasari));
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
        cikarmascore.setText(getString(R.string.bolumbasari)+String.valueOf((int)cikarmabasari));
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
        carpmascore.setText(getString(R.string.bolumbasari)+String.valueOf((int)carpmabasari));
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
        bolmescore.setText(getString(R.string.bolumbasari)+String.valueOf((int)bolmebasari));
        /////////////////////////////////////////////////




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
    }

    @Override
    protected void onStart() {
        super.onStart();

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



        /////////////TOPLAMA SKOR BİLGİLERİ//////////////////


        SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();


        inttopsoru = sp.getInt("toplamasoru",0);
        inttopdogru= sp.getInt("toplamadogru",0);
        inttopyanlis = sp.getInt("toplamayanlis",0);
        inttopbos = sp.getInt("toplamabos",0);
        inttoplamaleader=sp.getInt("toplamaleader",0);

        toplamasorusayisi.setText(getString(R.string.totalquestionh)+String.valueOf(inttopsoru));
        toplamadogru.setText(getString(R.string.rightanswersh)+String.valueOf(inttopdogru));
        toplamayanlis.setText(getString(R.string.wronganswersh)+String.valueOf(inttopyanlis));
        toplamabos.setText(getString(R.string.unanswered)+String.valueOf(inttopbos));
//////////leaderboard
        Button topleaderbtn = (Button)findViewById(R.id.toplamaleader);
        topleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isSignedIn()) {
                    Games.getLeaderboardsClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                            .getLeaderboardIntent(getString(R.string.leaderboard_summation_stars))
                            .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                @Override
                                public void onSuccess(Intent intent) {
                                    startActivityForResult(intent, RC_LEADERBOARD_UI);
                                }
                            });
                }else {
                    startSignInIntent();

                    Toast.makeText(getApplicationContext(),"Google play is not connected or installed!",Toast.LENGTH_SHORT).show();

                }



            }
        });



        ////////////////////TOPLAMA BİTTİ////////////////////


        //////////////////ÇIKARMA SKOR BİLGİLERİ//////////////////

        intcikarmasoru = sp.getInt("cikarmasoru",0);
        intcikarmadogru= sp.getInt("cikarmadogru",0);
        intcikarmayanlis = sp.getInt("cikarmayanlis",0);
        intcikarmabos = sp.getInt("cikarmabos",0);
        intcikarmaleader=sp.getInt("cikarmaleader",0);

        cikarmatoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intcikarmasoru));
        cikarmadogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intcikarmadogru));
        cikarmayanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intcikarmayanlis));
        cikarmabos.setText(getString(R.string.unanswered)+String.valueOf(intcikarmabos));

        /////////////////leader board
        Button cikarmaleaderbtn = (Button)findViewById(R.id.cikarmaleader);
        cikarmaleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSignedIn()) {
                    Games.getLeaderboardsClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                            .getLeaderboardIntent(getString(R.string.leaderboard_subtraction_stars))
                            .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                @Override
                                public void onSuccess(Intent intent) {
                                    startActivityForResult(intent, RC_LEADERBOARD_UI);
                                }
                            });
                }else {
                    startSignInIntent();

                    Toast.makeText(getApplicationContext(),"Google play is not connected or installed!",Toast.LENGTH_SHORT).show();

                }


               }
        });



        //////////////CİKARMA BİTTİ////////////////////


        //////////////////ÇARPMA SKOR BİLGİLERİ//////////////////
        intcarpmasoru = sp.getInt("carpmasoru",0);
        intcarpmadogru= sp.getInt("carpmadogru",0);
        intcarpmayanlis = sp.getInt("carpmayanlis",0);
        intcarpmabos = sp.getInt("carpmabos",0);
        intcarpmaleader = sp.getInt("carpmaleader",0);

        carpmatoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intcarpmasoru));
        carpmadogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intcarpmadogru));
        carpmayanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intcarpmayanlis));
        carpmabos.setText(getString(R.string.unanswered)+String.valueOf(intcarpmabos));

        /////////////////leader board
        Button carpmaleaderbtn = (Button)findViewById(R.id.carpmaleader);
        carpmaleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isSignedIn()) {
                    Games.getLeaderboardsClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                            .getLeaderboardIntent(getString(R.string.leaderboard_multiplication_stars))
                            .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                @Override
                                public void onSuccess(Intent intent) {
                                    startActivityForResult(intent, RC_LEADERBOARD_UI);
                                }
                            });
                }else {
                    startSignInIntent();

                    Toast.makeText(getApplicationContext(),"Google play is not connected or installed!",Toast.LENGTH_SHORT).show();

                }
                }
        });


        //////////////ÇARPMA BİTTİ////////////////////

        //////////////////BÖLME SKOR BİLGİLERİ//////////////////

        intbolsoru = sp.getInt("bolmesoru",0);
        intboldogru= sp.getInt("bolmedogru",0);
        intbolyanlis = sp.getInt("bolmeyanlis",0);
        intbolbos = sp.getInt("bolmebos",0);
        intbolmeleader= sp.getInt("bolmeleader",0);

        bolmetoplamsoru.setText(getString(R.string.totalquestionh)+String.valueOf(intbolsoru));
        bolmedogrusayisi.setText(getString(R.string.rightanswersh)+String.valueOf(intboldogru));
        bolmeyanlissayisi.setText(getString(R.string.wronganswersh)+String.valueOf(intbolyanlis));
        bolmebos.setText(getString(R.string.unanswered)+String.valueOf(intbolbos));

        /////////////////leader board
        Button bolmeleaderbtn = (Button)findViewById(R.id.bolmeleader);
        bolmeleaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSignedIn()) {
                    Games.getLeaderboardsClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                            .getLeaderboardIntent(getString(R.string.leaderboard_division_stars))
                            .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                @Override
                                public void onSuccess(Intent intent) {
                                    startActivityForResult(intent, RC_LEADERBOARD_UI);
                                }
                            });
                }else {
                    startSignInIntent();

                    Toast.makeText(getApplicationContext(),"Google play is not connected or installed!",Toast.LENGTH_SHORT).show();

                }
               }
        });

        //////////////BÖLME BİTTİ////////////////////






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
        toplamascore.setText(getString(R.string.bolumbasari)+String.valueOf((int)topbasari));
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
        cikarmascore.setText(getString(R.string.bolumbasari)+String.valueOf((int)cikarmabasari));
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
        carpmascore.setText(getString(R.string.bolumbasari)+String.valueOf((int)carpmabasari));
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
        bolmescore.setText(getString(R.string.bolumbasari)+String.valueOf((int)bolmebasari));
        /////////////////////////////////////////////////


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

        System.gc();

    }





    public void toplamaact(View view) {
        Intent i = new Intent(TrainingActivity.this, ToplamaActivity.class);startActivity(i);
    }


    public void cikarma(View view) {
        Intent i = new Intent(TrainingActivity.this, CikarmaActivity.class);startActivity(i);
    }
    public void carpma(View view) {
        Intent i = new Intent(TrainingActivity.this, CarpmaActivity.class);startActivity(i);
    }
    public void bolme(View view) {
        Intent i = new Intent(TrainingActivity.this, BolmeActivity.class);startActivity(i);
    }


    private void signInSilently() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                            GoogleSignInAccount signedInAccount = task.getResult();
                        } else {
                            // Player will need to sign-in explicitly using via UI
                        }
                    }
                });
    }

    private void startSignInIntent() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
            } else {
                /*
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.playgamesmassage);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
                */
            }
        }
    }

    private boolean isSignedIn () {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(TrainingActivity.this, MainmenuActivity.class);startActivity(i);this.finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();

    }


}

