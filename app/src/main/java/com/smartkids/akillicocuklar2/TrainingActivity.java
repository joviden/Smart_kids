package com.smartkids.akillicocuklar2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.smartkids.akillicocuklar2.adapters.TrainingListAdapter;
import com.smartkids.akillicocuklar2.models.Training;
import com.smartkids.akillicocuklar2.utils.Constants;
import com.smartkids.akillicocuklar2.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;


public class TrainingActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Context mcontext;
    private boolean four_operation;
    private SharedPrefManager sharedPrefManager;


    private RecyclerView training_recycler;
    private List<Object> trainingList;
    private TrainingListAdapter trainingListAdapter2;

    private int success_summation, success_extraction, success_division, success_multiplication, success_ritmik,success_simetri,success_buyuk_kucuk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_layout);

        sharedPrefManager = new SharedPrefManager(this);

        four_operation = getIntent().getExtras().getBoolean("four_operation");

        mcontext = TrainingActivity.this;

        getScores();

        setupListAdapter();

        createAds();


    }

    private void getScores() {

        if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartoplama) + "soru", 1)!=0){
            success_summation = Math.round(sharedPrefManager.getIntegerFromSP(getString(R.string.konulartoplama) + "dogru", 0) * 100) /
                    (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartoplama) + "soru", 1));
        }else {
            success_summation = 0;
        }


        if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcikarma) + "soru", 1)!=0){
            success_extraction = Math.round(sharedPrefManager.getIntegerFromSP(getString(R.string.konularcikarma) + "dogru", 0) * 100) /
                    (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcikarma) + "soru", 1));
        }else {
            success_extraction = 0;
        }

        if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcarpma) + "soru", 1)!=0){
            success_multiplication = Math.round(sharedPrefManager.getIntegerFromSP(getString(R.string.konularcarpma) + "dogru", 0) * 100) /
                    (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcarpma) + "soru", 1));
        }else {
            success_multiplication = 0;
        }

        if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbolme) + "soru", 1)!=0){
            success_division = Math.round(sharedPrefManager.getIntegerFromSP(getString(R.string.konularbolme) + "dogru", 0) * 100) /
                    (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbolme) + "soru", 1));
        }else {
            success_division = 0;
        }

        if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik) + "soru", 1)!=0){
            success_ritmik = Math.round(sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik) + "dogru", 0) * 100) /
                    (sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik) + "soru", 1));
        }else {
            success_ritmik = 0;
        }


        if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry) + "soru", 1)!=0){
            success_simetri = Math.round(sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry) + "dogru", 0) * 100) /
                    (sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry) + "soru", 1));
        }else {
            success_simetri = 0;
        }

        Log.i("simetribasari","Basari:"+success_simetri);







    }

    private void createAds() {

        MobileAds.initialize(this, Constants.adMobId);



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

    private void setupListAdapter() {


        training_recycler = findViewById(R.id.choose_train_recycler);

      Log.i("succes","Success:"+success_summation+" "+success_extraction+" "+success_multiplication+" "+success_division);


        trainingList = new ArrayList<>();

        if (four_operation) {
            trainingList.add(new Training(getString(R.string.konulartoplama), success_summation, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.konularcikarma), success_extraction, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.konularbolme), success_division, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.konularcarpma), success_multiplication, R.drawable.icon_mental));
        } else {
            trainingList.add(new Training(getString(R.string.konularritmik), success_ritmik, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.konularsimetry), success_simetri, R.drawable.icon_mental));
        }


        trainingListAdapter2 = new TrainingListAdapter(this, trainingList);


        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
        mlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        training_recycler.setLayoutManager(mlayoutManager);
        training_recycler.setItemAnimator(new DefaultItemAnimator());

        addBannerAds();
        loadBannerAd(1);

        training_recycler.setAdapter(trainingListAdapter2);




    }


    private void addBannerAds() {
        for (int i = 0; i < trainingList.size(); i += Constants.items_Per_Ad) {
            final AdView adView = new AdView(TrainingActivity.this);

            trainingList.add(i + 1, adView);
            adView.setAdSize(getAdSize());
            adView.setAdUnitId(Constants.bannerTestId);

        }
    }

    private void loadBannerAd(final int index) {

        try {

            if (index >= trainingList.size()) {
                return;
            }

            Object item = trainingList.get(index);
            if (!(item instanceof AdView)) {
                throw new ClassCastException("Expected item at index " + index + " to be a banner ad"
                        + " ad.");
            }

            final AdView adView = (AdView) item;

            // Set an AdListener on the AdView to wait for the previous banner ad
            // to finish loading before loading the next ad in the items list.
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    // The previous banner ad loaded successfully, call this method again to
                    // load the next ad in the items list.
                  //  trainingListAdapter2.notifyDataSetChanged();
                    loadBannerAd(index + Constants.items_Per_Ad);
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // The previous banner ad failed to load. Call this method again to load
                    // the next ad in the items list.
                    Log.e("MainActivity", "The previous banner ad failed to load. Attempting to"
                            + " load the next banner ad in the items list.");
                    loadBannerAd(index + Constants.items_Per_Ad);
                }
            });

            // Load the banner ad.
            adView.loadAd(new AdRequest.Builder().build());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void clickEvent(View view) {


        if (four_operation) {

            Intent i = new Intent(TrainingActivity.this, FourOpActivity.class);
            i.putExtra("op", view.getTag().toString());
            startActivity(i);
        } else {

            if (view.getTag().toString().equals(getString(R.string.konularritmik))){
                Intent i = new Intent(TrainingActivity.this, RitmiksaymaActivity.class);
                startActivity(i);


            }else   if (view.getTag().toString().equals(getString(R.string.konularsimetry))){
                Intent i = new Intent(TrainingActivity.this, SimetryActivity.class);
                startActivity(i);

            }











        }


    }


    public void onResume() {
        super.onResume();


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(TrainingActivity.this, MainmenuActivity.class);
        startActivity(i);
        this.finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }


}

