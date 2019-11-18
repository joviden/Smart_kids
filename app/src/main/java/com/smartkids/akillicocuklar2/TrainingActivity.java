package com.smartkids.akillicocuklar2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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
import com.smartkids.akillicocuklar2.adapters.TrainingListAdapter;
import com.smartkids.akillicocuklar2.models.Training;
import com.smartkids.akillicocuklar2.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class TrainingActivity extends AppCompatActivity  {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    private RecyclerView training_recycler;
    private List<Training> trainingList;
    private TrainingListAdapter trainingListAdapter;

    private int success_summation, success_extraction, success_division, success_multiplication;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_operations);

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

        trainingList.add(new Training(getString(R.string.konulartoplama),success_summation,R.drawable.icon_mental));
        trainingList.add(new Training(getString(R.string.konularcikarma),success_extraction,R.drawable.icon_mental));
        trainingList.add(new Training(getString(R.string.konularbolme),success_division,R.drawable.icon_mental));
        trainingList.add(new Training(getString(R.string.konularcarpma),success_multiplication,R.drawable.icon_mental));
        trainingListAdapter = new TrainingListAdapter(this,trainingList);


        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
        mlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        training_recycler.setLayoutManager(mlayoutManager);
        training_recycler.setItemAnimator(new DefaultItemAnimator());
        training_recycler.setAdapter(trainingListAdapter);


    }

    public void clickEvent(View view) {

        int which_one = Integer.parseInt(view.getTag().toString());

        switch (which_one) {
            case 0:
                Intent i = new Intent(TrainingActivity.this, ToplamaActivity.class);startActivity(i);
                break;
            case 1:
                Intent ii = new Intent(TrainingActivity.this, CikarmaActivity.class);startActivity(ii);
                break;
            case 2:
                Intent iii = new Intent(TrainingActivity.this, CarpmaActivity.class);startActivity(iii);
                break;
            case 3:
                Intent iiii = new Intent(TrainingActivity.this, BolmeActivity.class);startActivity(iiii);
                break;
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
        Intent i = new Intent(TrainingActivity.this, MainmenuActivity.class);startActivity(i);this.finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }


}

