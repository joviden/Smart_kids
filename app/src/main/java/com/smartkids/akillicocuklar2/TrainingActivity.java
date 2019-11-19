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
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.smartkids.akillicocuklar2.adapters.TrainingListAdapter;
import com.smartkids.akillicocuklar2.models.Training;
import com.smartkids.akillicocuklar2.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class TrainingActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Context mcontext;
    private boolean four_operation;


    private RecyclerView training_recycler;
    private List<Object> trainingList;
    private TrainingListAdapter trainingListAdapter2;

    private int success_summation, success_extraction, success_division, success_multiplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_layout);

         four_operation = getIntent().getExtras().getBoolean("four_operation");

        mcontext = TrainingActivity.this;

        setupListAdapter();

        createAds();


    }

    private void createAds() {

        MobileAds.initialize(this, Constants.adMobId);

        FrameLayout ad_container = findViewById(R.id.ad_container);
        mAdView = new AdView(this);
        mAdView.setAdUnitId(Constants.bannerTestId);  //DEGISTIR
        ad_container.addView(mAdView);

        AdSize adSize = getAdSize();
        mAdView.setAdSize(adSize);

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

    private void setupListAdapter() {
        training_recycler = findViewById(R.id.choose_train_recycler);

        success_summation = 30;
        success_extraction = 30;
        success_division = 30;
        success_multiplication = 30;


        trainingList = new ArrayList<>();

        if (four_operation){
            trainingList.add(new Training(getString(R.string.konulartoplama), success_summation, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.konularcikarma), success_extraction, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.konularbolme), success_division, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.konularcarpma), success_multiplication, R.drawable.icon_mental));
        }else {
            trainingList.add(new Training(getString(R.string.konularritmik), success_summation, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.konularbuyukkucuk), success_extraction, R.drawable.icon_mental));
            trainingList.add(new Training(getString(R.string.simetry), success_division, R.drawable.icon_mental));
        }




        trainingListAdapter2 = new TrainingListAdapter(this, trainingList);


        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
        mlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        training_recycler.setLayoutManager(mlayoutManager);
        training_recycler.setItemAnimator(new DefaultItemAnimator());
        training_recycler.setAdapter(trainingListAdapter2);

        training_recycler.post(new Runnable() {
            @Override
            public void run() {
                addBannerAds();
                loadBannerAd(1);
            }
        });


    }


    private void addBannerAds() {
        for (int i = 0; i < trainingList.size(); i += Constants.items_Per_Ad) {
            final AdView adView = new AdView(TrainingActivity.this);

                trainingList.add(i+1, adView);
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
                    trainingListAdapter2.notifyDataSetChanged();
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

        }catch (Exception e) {
            e.printStackTrace();
        }


    }



    public void clickEvent(View view) {



        if (four_operation){

            Intent i = new Intent(TrainingActivity.this, FourOpActivity.class);
            i.putExtra("op",view.getTag().toString());
            startActivity(i);
        }else {



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
        Intent i = new Intent(TrainingActivity.this, MainmenuActivity.class);
        startActivity(i);
        this.finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }




}

