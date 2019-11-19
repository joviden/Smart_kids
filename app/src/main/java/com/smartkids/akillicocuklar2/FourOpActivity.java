package com.smartkids.akillicocuklar2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.smartkids.akillicocuklar2.utils.Constants;
import com.smartkids.akillicocuklar2.utils.SharedPrefManager;

public class FourOpActivity extends AppCompatActivity {

    private String operator = "";
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private SharedPrefManager sharedPrefManager;
    private String difficulty = "";
    private  AlertDialog alert_difficulty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.four_opt_layout);
        operator = getIntent().getStringExtra("op");
        Log.i("getIntent",operator);

        createAds();
        showDifficultyAlert();


    }

    private void showDifficultyAlert() {
        View view = getLayoutInflater().inflate(R.layout.alert_difficulty, null);
        androidx.appcompat.widget.AppCompatButton kolayBtn = view.findViewById(R.id.kolayBtn);
        androidx.appcompat.widget.AppCompatButton ortaBtn = view.findViewById(R.id.ortaBtn);
        androidx.appcompat.widget.AppCompatButton zorBtn = view.findViewById(R.id.zorBtn);

        final AlertDialog.Builder builder2 = new AlertDialog.Builder(FourOpActivity.this);
        builder2.setView(view);
        alert_difficulty = builder2.create();

        try {
            alert_difficulty.show();
            alert_difficulty.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    public void difficultyClick(View view) {

        Animation animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        view.startAnimation(animBlink);

        animBlink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (alert_difficulty!=null){
                    alert_difficulty.dismiss();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        switch (view.getId()) {
            case R.id.kolayBtn:
                difficulty = "easy";
                break;

            case R.id.ortaBtn:
                difficulty = "moderate";
                break;

            case R.id.zorBtn:
                difficulty = "hard";
                break;
        }


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent k = new Intent(FourOpActivity.this, TrainingActivity.class);
        k.putExtra("four_operation", true);
        startActivity(k);
        this.finish();
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

    private void createAds() {

        MobileAds.initialize(this, Constants.adMobId);

        FrameLayout ad_container = findViewById(R.id.ad_container);
        mAdView = new AdView(this);
        mAdView.setAdUnitId(Constants.bannerTestId);  //DEGISTIR
        ad_container.addView(mAdView);


        mAdView.setAdSize(getAdSize());

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
}
