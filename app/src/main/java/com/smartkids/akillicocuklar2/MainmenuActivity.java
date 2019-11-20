package com.smartkids.akillicocuklar2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.smartkids.akillicocuklar2.adapters.CharChooseAdapter;
import com.smartkids.akillicocuklar2.adapters.PagerAdapter;
import com.smartkids.akillicocuklar2.models.Character;
import com.smartkids.akillicocuklar2.models.SmartGames;
import com.smartkids.akillicocuklar2.utils.Constants;
import com.smartkids.akillicocuklar2.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;


public class MainmenuActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private SharedPrefManager sharedPrefManager;
    private int user_level;
    private boolean char_just_choosen = false;
    private List<Character> characters;
    private List<SmartGames> smartGames;
    private int pagenumber;
    List<ImageView> dots;


    Integer kumulatiftoplamscore, toplamascore, cikarmascore, carpmascore, bolmescore, ritmikscore, buyukkucukscore, simetriscore, totalscore,
            toplamaskorkumulatif, cikarmaskorkumulatif, carpmaskorkumulatif, bolmeskorkumulatif, ritmikskorkumulatif, buyukkucukskorkumulatif, simetriskorkumulatif,
            level, timescorekumulatif, timescore;


    ViewPager viewPager;
    PagerAdapter pagerAdapter;


    private static final int RC_LEADERBOARD_UI = 9004;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_SIGN_IN = 9001;

    private CharChooseAdapter chooseAdapter;

    private RecyclerView char_recyclerView;

    private ConstraintLayout mainmenu_layout, charchooselayout, byebyeLayout, sonuclarlayout;
    private ImageView characterview;

    private AppCompatButton profileBtn, leaderboardBtn, achievementBtn, rateappBtn, shareBtn;

    private GoogleSignInAccount signedInAccount;

    private ProgressBar progressBar;
    private TextView progressTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenulayout);

        sharedPrefManager = new SharedPrefManager(this);

        createAds();


        mainmenu_layout = findViewById(R.id.mainmenu_layout);
        charchooselayout = findViewById(R.id.charchooselayout);
        byebyeLayout = findViewById(R.id.byebyeLayout);
        sonuclarlayout = findViewById(R.id.sonuclarlayout);
        profileBtn = findViewById(R.id.profileBtn);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        achievementBtn = findViewById(R.id.achievementBtn);
        rateappBtn = findViewById(R.id.rateappBtn);
        shareBtn = findViewById(R.id.shareBtn);
        characterview = findViewById(R.id.characterview);


        byebyeLayout.setVisibility(View.GONE);
        sonuclarlayout.setVisibility(View.GONE);

        user_level = sharedPrefManager.getIntegerFromSP("level", 1);

        setLevel();
        setListeners();
        inflatePager();
        setCharacterAdapter();
        connectGoogleGames();


        if (sharedPrefManager.getIntegerFromSP("avatar_chosen", 100) == 100) {
            Log.i("check_Avatar", "Avatar:" + sharedPrefManager.getIntegerFromSP("avatar_chosen", 100));
            fadeIn_fadeOut_Animation(charchooselayout, mainmenu_layout);

        } else {
            Log.i("check_Avatar", "Avatar2:" + sharedPrefManager.getIntegerFromSP("avatar_chosen", 100));
            fadeIn_fadeOut_Animation(mainmenu_layout, charchooselayout);
            characterview.setImageResource(characters.get(sharedPrefManager.getIntegerFromSP("avatar_chosen", 0)).getImage());


        }


    }

    private void setLevel() {

        progressBar = findViewById(R.id.pointsbar);
        progressTxt = findViewById(R.id.progressTxt);


        for (int i = 1; i < Constants.levels.size(); i++) {
            if (sharedPrefManager.getIntegerFromSP("skor_total", 0) > Constants.levels.get(i)) {
                user_level = i + 1;
            }
        }

        if (user_level != sharedPrefManager.getIntegerFromSP("level", 1)) {
            newLevelReached();
            Log.i("total_Score", "New Level:" + user_level);
        } else {
            progressTxt.setText(getText(R.string.level) + " " + user_level);
            int percantage = (sharedPrefManager.getIntegerFromSP("skor_total", 0) * 100) / Constants.levels.get(user_level);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, percantage);
            animation.setDuration(2000); // 3.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
            Log.i("total_Score", "Level:" + user_level);
        }


    }

    private void newLevelReached() {

        sharedPrefManager.putIntegertoSP("level", user_level);

        fadeIn_fadeOut_Animation(charchooselayout, mainmenu_layout);

        final MediaPlayer selectAvatarSound = MediaPlayer.create(this, R.raw.levelup);
        selectAvatarSound.start();
        selectAvatarSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                selectAvatarSound.release();
            }
        });


    }

    private void setListeners() {

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStats();
            }
        });
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSignedIn()) {
                    showLeaderBoard();
                } else {
                    connectGoogleGames();

                    Toast.makeText(getApplicationContext(), "Google play is not connected or installed!", Toast.LENGTH_SHORT).show();

                }

            }
        });
        achievementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSignedIn()) {

                    showAchivement();
                } else {
                    connectGoogleGames();

                    Toast.makeText(getApplicationContext(), "Google Play SmartGames is not connected or installed!", Toast.LENGTH_SHORT).show();

                }

            }
        });
        rateappBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateapp();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sharebody = getString(R.string.sharebody);
                String sharesub = getString(R.string.sharesub);
                myIntent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
                myIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
                startActivity(Intent.createChooser(myIntent, "Share Smart Kids"));

            }
        });

        characterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeIn_fadeOut_Animation(charchooselayout, mainmenu_layout);
            }
        });


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

    public void inflatePager() {


        smartGames = new ArrayList<>();

        smartGames.add(new SmartGames(getString(R.string.twoplayers), getString(R.string.twoplayersexp), R.drawable.icon_ball));
        smartGames.add(new SmartGames(getString(R.string.timechallenge), getString(R.string.timechallenge_exp), R.drawable.icon_clock));
        smartGames.add(new SmartGames(getString(R.string.mathgames), getString(R.string.mathgamesexp), R.drawable.icon_mental));
        smartGames.add(new SmartGames(getString(R.string.fouroperations), getString(R.string.fouroperationsexp), R.drawable.icon_maths));
        smartGames.add(new SmartGames(getString(R.string.removeads), getString(R.string.removeadsexp), R.drawable.icon_unlock));
        smartGames.add(new SmartGames(getString(R.string.otherapps), getString(R.string.otherappsexp), R.drawable.icon_market));


        final ImageView dot1 = findViewById(R.id.dot1);
        final ImageView dot2 = findViewById(R.id.dot2);
        final ImageView dot3 = findViewById(R.id.dot3);
        final ImageView dot4 = findViewById(R.id.dot4);
        final ImageView dot5 = findViewById(R.id.dot5);
        final ImageView dot6 = findViewById(R.id.dot6);

        dots = new ArrayList<>();
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);
        dots.add(dot5);
        dots.add(dot6);


        try {

            viewPager = findViewById(R.id.viewpager);


            pagerAdapter = new PagerAdapter(this, smartGames);
            viewPager.setAdapter(pagerAdapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                }

                @Override
                public void onPageSelected(int position) {

                    pagenumber = position;
                    Log.i("position", String.valueOf(pagenumber));

                    for (int i = 0; i < smartGames.size(); i++) {
                        if (pagenumber != i) {
                            dots.get(i).setBackgroundResource(R.drawable.dot_unselected);
                        } else {
                            dots.get(i).setBackgroundResource(R.drawable.dot_selected);
                        }
                    }


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCharacterAdapter() {

        characters = new ArrayList<>();
        characters.add(new Character("Jack", "Level 1", R.drawable.icon1n1));
        characters.add(new Character("Jack", "Level 1", R.drawable.icon1n2));
        characters.add(new Character("Jack", "Level 1", R.drawable.icon1n3));
        characters.add(new Character("Jack", "Level 1", R.drawable.icon1n4));

        characters.add(new Character("Jack", "Level 2", R.drawable.icon2n1));
        characters.add(new Character("Jack", "Level 2", R.drawable.icon2n2));
        characters.add(new Character("Jack", "Level 2", R.drawable.icon2n3));
        characters.add(new Character("Jack", "Level 2", R.drawable.icon2n4));

        characters.add(new Character("Jack", "Level 3", R.drawable.icon3n1));
        characters.add(new Character("Jack", "Level 3", R.drawable.icon3n2));
        characters.add(new Character("Jack", "Level 3", R.drawable.icon3n3));
        characters.add(new Character("Jack", "Level 3", R.drawable.icon3n4));

        characters.add(new Character("Jack", "Level 4", R.drawable.icon4n1));
        characters.add(new Character("Jack", "Level 4", R.drawable.icon4n2));
        characters.add(new Character("Jack", "Level 4", R.drawable.icon4n3));
        characters.add(new Character("Jack", "Level 4", R.drawable.icon4n4));

        characters.add(new Character("Jack", "Level 5", R.drawable.icon5n1));
        characters.add(new Character("Jack", "Level 5", R.drawable.icon5n2));
        characters.add(new Character("Jack", "Level 5", R.drawable.icon5n3));
        characters.add(new Character("Jack", "Level 5", R.drawable.icon5n4));

        characters.add(new Character("Jack", "Level 6", R.drawable.icon6n1));
        characters.add(new Character("Jack", "Level 6", R.drawable.icon6n2));
        characters.add(new Character("Jack", "Level 6", R.drawable.icon6n3));
        characters.add(new Character("Jack", "Level 6", R.drawable.icon6n4));

        characters.add(new Character("Jack", "Level 7", R.drawable.icon7n1));
        characters.add(new Character("Jack", "Level 7", R.drawable.icon7n2));
        characters.add(new Character("Jack", "Level 7", R.drawable.icon7n3));
        characters.add(new Character("Jack", "Level 7", R.drawable.icon7n4));

        characters.add(new Character("Jack", "Level 8", R.drawable.icon8n1));
        characters.add(new Character("Jack", "Level 8", R.drawable.icon8n2));
        characters.add(new Character("Jack", "Level 8", R.drawable.icon8n3));
        characters.add(new Character("Jack", "Level 8", R.drawable.icon8n4));

        characters.add(new Character("Jack", "Level 9", R.drawable.icon9n1));
        characters.add(new Character("Jack", "Level 9", R.drawable.icon9n2));
        characters.add(new Character("Jack", "Level 9", R.drawable.icon9n3));
        characters.add(new Character("Jack", "Level 9", R.drawable.icon9n4));

        characters.add(new Character("Jack", "Level 10", R.drawable.icon10n1));
        characters.add(new Character("Jack", "Level 10", R.drawable.icon10n2));
        characters.add(new Character("Jack", "Level 10", R.drawable.icon11));
        characters.add(new Character("Jack", "Level 10", R.drawable.icon10n2));


        chooseAdapter = new CharChooseAdapter(this, characters, user_level, sharedPrefManager.getIntegerFromSP("avatar_chosen", 0));
        char_recyclerView = findViewById(R.id.char_recyclerView);

        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
        mlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        char_recyclerView.setLayoutManager(mlayoutManager);
        char_recyclerView.setItemAnimator(new DefaultItemAnimator());
        char_recyclerView.setAdapter(chooseAdapter);


    }


    public void selectCharacter(View view) {

        int tag = Integer.parseInt(view.getTag().toString());

        Log.i("adapterTag","Tag:"+tag+"Tag/4"+tag/4);




        if (view.getAlpha() != 1) {
            Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz), Toast.LENGTH_SHORT).show();
        } else if (!char_just_choosen) {

            Animation animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
            Animation vibrate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate);

            RecyclerView.ViewHolder viewHolder_new = char_recyclerView.findViewHolderForAdapterPosition((tag/4));
            RecyclerView.ViewHolder viewHolder_ex = char_recyclerView.findViewHolderForAdapterPosition((sharedPrefManager.getIntegerFromSP("avatar_chosen", 0)/4));
            View view_new = null;
            View view_ex = null;
            if (viewHolder_new != null && viewHolder_ex != null) {
                view_new = viewHolder_new.itemView;
                view_ex = viewHolder_ex.itemView;


                ImageView avatar_icon_ex = view_ex.findViewWithTag(String.valueOf(sharedPrefManager.getIntegerFromSP("avatar_chosen", 0)));
                TextView avatar_name_ex = view_ex.findViewWithTag(String.valueOf(sharedPrefManager.getIntegerFromSP("avatar_chosen", 0)) + "txt");
                ImageView avatar_icon = view_new.findViewWithTag(String.valueOf(tag));
                TextView avatar_name = view_new.findViewWithTag(String.valueOf(tag) + "txt");


                avatar_name_ex.setBackgroundResource(R.drawable.cerceve_button);
                avatar_name_ex.clearAnimation();
                avatar_icon_ex.setBackgroundResource(R.drawable.cerceve_button);
                avatar_icon_ex.clearAnimation();


                avatar_name.setBackgroundResource(R.drawable.cerceve_button_selected);
                avatar_icon.setBackgroundResource(R.drawable.cerceve_button_selected);


                sharedPrefManager.putIntegertoSP("avatar_chosen", tag);

                characterview.setImageResource(characters.get(sharedPrefManager.getIntegerFromSP("avatar_chosen", 0)).getImage());

                final MediaPlayer selectAvatarSound = MediaPlayer.create(this, R.raw.levelup);
                selectAvatarSound.start();
                selectAvatarSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        selectAvatarSound.release();
                    }
                });


                animBlink.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        char_just_choosen = true;

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fadeIn_fadeOut_Animation(mainmenu_layout, charchooselayout);
                                if (mInterstitialAd.isLoaded()) {
                                    //  mInterstitialAd.show();
                                }
                            }
                        }, 700);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                char_just_choosen = false;
                            }
                        }, 1200);


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                avatar_name.startAnimation(animBlink);
                avatar_icon.startAnimation(vibrate);
            }


        }
    }

    public void skoraktar() {
        ConstraintLayout sonuclarlayout = findViewById(R.id.sonuclarlayout);
        Button toplamapuanTxtv = findViewById(R.id.toplamapuanTxtv);
        Button cikarmapuanTxtv = findViewById(R.id.cikarmapuanTxtv);
        Button carpmapuanTxtv = findViewById(R.id.carpmapuanTxtv);
        Button bolmepuanTxtv = findViewById(R.id.bolmepuanTxtv);
        Button simetripuanTxtv = findViewById(R.id.simetripuanTxtv);
        Button serialspuanTxtv = findViewById(R.id.serialspuanTxtv);
        Button buyukkucukpuanTxtv = findViewById(R.id.buyukkucukpuanTxtv);
        Button timechallengepuanTxtv = findViewById(R.id.timechallengepuanTxtv);
        Button smarttotalpuanTxtv = findViewById(R.id.smarttotalpuanTxtv);
        Button levelTxtv = findViewById(R.id.levelTxtv);
        Button nextlevelptsTxtv = findViewById(R.id.nextlevelptsTxtv);
        Button progressTxt = findViewById(R.id.progressTxt);

        ProgressBar levelbar = findViewById(R.id.pointsbar);

        int seviye = levelbar.getProgress();
        int max = levelbar.getMax();
        int fark = max - seviye;


        toplamapuanTxtv.setText(getText(R.string.toplamapuan) + " " + String.valueOf(toplamaskorkumulatif));
        cikarmapuanTxtv.setText(getText(R.string.cikarmapuan) + " " + String.valueOf(cikarmaskorkumulatif));
        carpmapuanTxtv.setText(getText(R.string.carpmapuan) + " " + String.valueOf(carpmaskorkumulatif));
        bolmepuanTxtv.setText(getText(R.string.bolmepuan) + " " + String.valueOf(bolmeskorkumulatif));
        simetripuanTxtv.setText(getText(R.string.simetripuan) + " " + String.valueOf(simetriskorkumulatif));
        serialspuanTxtv.setText(getText(R.string.ritmikpuan) + " " + String.valueOf(ritmikskorkumulatif));
        buyukkucukpuanTxtv.setText(getText(R.string.buyukkucukpuan) + " " + String.valueOf(buyukkucukskorkumulatif));
        timechallengepuanTxtv.setText(getText(R.string.timechallengepuan) + " " + String.valueOf(timescorekumulatif));
        smarttotalpuanTxtv.setText(getText(R.string.totalsmartpuan) + " " + String.valueOf(kumulatiftoplamscore));
        levelTxtv.setText(getText(R.string.levelsayisi) + " " + progressTxt.getText().toString());
        nextlevelptsTxtv.setText(getText(R.string.nextlevelpts) + " " + String.valueOf(fark));


    }


    public void onResume() {
        super.onResume();

/*

        charchooselayout.setVisibility(View.GONE);

        final SharedPreferences sp = getSharedPreferences("skorbilgiler", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor spEditor = sp.edit();


        toplamascore = sp.getInt("toplamaleader", 0);
        cikarmascore = sp.getInt("cikarmaleader", 0);
        bolmescore = sp.getInt("bolmeleader", 0);
        carpmascore = sp.getInt("carpmaleader", 0);
        buyukkucukscore = sp.getInt("buyukkucukleader", 0);
        simetriscore = sp.getInt("simetrileader", 0);
        ritmikscore = sp.getInt("ritmikleader", 0);
        timescore = sp.getInt("timescore", 0);


        toplamaskorkumulatif = sp.getInt("toplamakumulatif", 0);
        cikarmaskorkumulatif = sp.getInt("cikarmakumulatif", 0);
        bolmeskorkumulatif = sp.getInt("bolmekumulatif", 0);
        carpmaskorkumulatif = sp.getInt("carpmakumulatif", 0);
        buyukkucukskorkumulatif = sp.getInt("buyukkucukkumulatif", 0);
        ritmikskorkumulatif = sp.getInt("ritmikkumulatif", 0);
        simetriskorkumulatif = sp.getInt("simetrikumulatif", 0);
        timescorekumulatif = sp.getInt("timescorekumulatif", 0);
        level = sp.getInt("level", 0);

*/
/*
        Log.i("toplamakumulatif",String.valueOf(toplamaskorkumulatif));
        Log.i("cikarmakumulatif",String.valueOf(cikarmaskorkumulatif));
        Log.i("carpmakumulatif",String.valueOf(carpmaskorkumulatif));
        Log.i("bolmekumulatif",String.valueOf(bolmeskorkumulatif));
        Log.i("ritmikkumulatif",String.valueOf(ritmikskorkumulatif));
        Log.i("buyukkucukkumulatif",String.valueOf(buyukkucukskorkumulatif));
        Log.i("simetrikumulatif",String.valueOf(simetriskorkumulatif));
        Log.i("timescorekumulatif",String.valueOf(timescorekumulatif));


        Log.i("skortoplama",String.valueOf(toplamascore));
        Log.i("skorcikarma",String.valueOf(cikarmascore));
        Log.i("skorcarpma",String.valueOf(carpmascore));
        Log.i("skorbolme",String.valueOf(bolmescore));
        Log.i("skorritmik",String.valueOf(ritmikscore));
        Log.i("skorbuyukkucuk",String.valueOf(buyukkucukscore));
        Log.i("skorsimetri",String.valueOf(simetriscore));
        Log.i("skortime",String.valueOf(timescore));
*//*


        kumulatiftoplamscore = toplamaskorkumulatif + cikarmaskorkumulatif + carpmaskorkumulatif + bolmeskorkumulatif + ritmikskorkumulatif +
                buyukkucukskorkumulatif + simetriskorkumulatif + timescorekumulatif;


        totalscore = sp.getInt("toplamskor", 0);


        //      Log.i("kumulatiftotal",String.valueOf(kumulatiftoplamscore));


        ///////////////////////////////avatar //////////////////////////////////

        SharedPreferences spavatarilk = getSharedPreferences("avatarbilgiler", Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditoravatarilk = spavatarilk.edit();
        Integer i = spavatarilk.getInt("avatarsecildimi", 0);
        Integer z = spavatarilk.getInt("secilenavatar", 0);

        //    Log.i("resimsecil di mi ilk ",String.valueOf(i));
        //    Log.i("secilen resim id",String.valueOf(z));


        if (i == 0) {
            charchooselayout.setVisibility(View.VISIBLE);
        } else {
            charchooselayout.setVisibility(View.GONE);
            characterview.setImageResource(z);
        }


///////////////////////////////avatar //////////////////////////////////

        ProgressBar toplamscorebar = (ProgressBar) findViewById(R.id.pointsbar);
        Button progrestxt = (Button) findViewById(R.id.progressTxt);
        toplamscorebar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#43ff50"), android.graphics.PorterDuff.Mode.SRC_ATOP);


*/




/*
        if (kumulatiftoplamscore <1000) {
            toplamscorebar.setMax(1000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore);
            level=1;
            spEditor.putInt("level", 1);
            spEditor.commit();




            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 0.6);icon2n2.setAlpha((float) 0.6);icon2n3.setAlpha((float) 0.6);icon2n4.setAlpha((float) 0.6);
            icon3n1.setAlpha((float) 0.6);icon3n2.setAlpha((float) 0.6);icon3n3.setAlpha((float) 0.6);icon3n4.setAlpha((float) 0.6);
            icon4n1.setAlpha((float) 0.6);icon4n2.setAlpha((float) 0.6);icon4n3.setAlpha((float) 0.6);icon4n4.setAlpha((float) 0.6);
            icon5n1.setAlpha((float) 0.6);icon5n2.setAlpha((float) 0.6);icon5n3.setAlpha((float) 0.6);icon5n4.setAlpha((float) 0.6);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);


        }

        if (kumulatiftoplamscore >999 && kumulatiftoplamscore <5000) {

            if (level==1) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=2;
                spEditor.putInt("level", level);
                spEditor.commit();


Media
                final Player victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }


            toplamscorebar.setMax(5000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -1000);
            progrestxt.setText("Level 2 ");


            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 0.6);icon3n2.setAlpha((float) 0.6);icon3n3.setAlpha((float) 0.6);icon3n4.setAlpha((float) 0.6);
            icon4n1.setAlpha((float) 0.6);icon4n2.setAlpha((float) 0.6);icon4n3.setAlpha((float) 0.6);icon4n4.setAlpha((float) 0.6);
            icon5n1.setAlpha((float) 0.6);icon5n2.setAlpha((float) 0.6);icon5n3.setAlpha((float) 0.6);icon5n4.setAlpha((float) 0.6);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

        }

        if (kumulatiftoplamscore >4999 && kumulatiftoplamscore <10000) {

            if (level==2) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=3;
                spEditor.putInt("level", level);
                spEditor.commit();



                final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }
            toplamscorebar.setMax(10000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -5000);
            progrestxt.setText("Level 3 ");



            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 0.6);icon4n2.setAlpha((float) 0.6);icon4n3.setAlpha((float) 0.6);icon4n4.setAlpha((float) 0.6);
            icon5n1.setAlpha((float) 0.6);icon5n2.setAlpha((float) 0.6);icon5n3.setAlpha((float) 0.6);icon5n4.setAlpha((float) 0.6);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);
        }
        if (kumulatiftoplamscore >9999 && kumulatiftoplamscore <15000) {
            if (level==3) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=4;
                spEditor.putInt("level", level);
                spEditor.commit();


                final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }
            toplamscorebar.setMax(15000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -10000);
            progrestxt.setText("Level 4 ");



            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 0.6);icon5n2.setAlpha((float) 0.6);icon5n3.setAlpha((float) 0.6);icon5n4.setAlpha((float) 0.6);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

        }
        if (kumulatiftoplamscore >14999 && kumulatiftoplamscore <25000) {

            if (level==4) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=5;
                spEditor.putInt("level", level);
                spEditor.commit();


                final  MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }
            toplamscorebar.setMax(25000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -15000);
            progrestxt.setText("Level 5 ");


            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 0.6);icon6n2.setAlpha((float) 0.6);icon6n3.setAlpha((float) 0.6);icon6n4.setAlpha((float) 0.6);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);



        }
        if (kumulatiftoplamscore >24999 && kumulatiftoplamscore <35000) {


            if (level==5) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=6;
                spEditor.putInt("level", level);
                spEditor.commit();


                final  MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }

            toplamscorebar.setMax(35000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -25000);
            progrestxt.setText("Level 6 ");



            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 0.6);icon7n2.setAlpha((float) 0.6);icon7n3.setAlpha((float) 0.6);icon7n4.setAlpha((float) 0.6);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

        }
        if (kumulatiftoplamscore >34999 && kumulatiftoplamscore <50000) {


            if (level==6) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=7;
                spEditor.putInt("level", level);
                spEditor.commit();


                final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }
            toplamscorebar.setMax(50000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -35000);
            progrestxt.setText("Level 7 ");



            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 0.6);icon8n2.setAlpha((float) 0.6);icon8n3.setAlpha((float) 0.6);icon8n4.setAlpha((float) 0.6);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

        }
        if (kumulatiftoplamscore >49999 && kumulatiftoplamscore <75000) {


            if (level==7) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=8;
                spEditor.putInt("level", level);
                spEditor.commit();


                final  MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }
            toplamscorebar.setMax(75000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -50000);
            progrestxt.setText("Level 8 ");



            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 1);icon8n2.setAlpha((float) 1);icon8n3.setAlpha((float) 1);icon8n4.setAlpha((float) 1);
            icon9n1.setAlpha((float) 0.6);icon9n2.setAlpha((float) 0.6);icon9n3.setAlpha((float) 0.6);icon9n4.setAlpha((float) 0.6);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);

        }
        if (kumulatiftoplamscore >74999 && kumulatiftoplamscore <100000) {


            if (level==8) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=9;
                spEditor.putInt("level", level);
                spEditor.commit();


                final  MediaPlayer   victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }

            toplamscorebar.setMax(100000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -75000);
            progrestxt.setText("Level 9 ");



            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 1);icon8n2.setAlpha((float) 1);icon8n3.setAlpha((float) 1);icon8n4.setAlpha((float) 1);
            icon9n1.setAlpha((float) 1);icon9n2.setAlpha((float) 1);icon9n3.setAlpha((float) 1);icon9n4.setAlpha((float) 1);
            icon10n1.setAlpha((float) 0.6);icon10n2.setAlpha((float) 0.6);
            icon11n1.setAlpha((float) 0.6);


        }

        if (kumulatiftoplamscore >99999 && kumulatiftoplamscore <125000) {


            if (level==9) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=10;
                spEditor.putInt("level", level);
                spEditor.commit();


                final  MediaPlayer    victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }

            toplamscorebar.setMax(125000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -100000);
            progrestxt.setText("Level 10 ");


            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 1);icon8n2.setAlpha((float) 1);icon8n3.setAlpha((float) 1);icon8n4.setAlpha((float) 1);
            icon9n1.setAlpha((float) 1);icon9n2.setAlpha((float) 1);icon9n3.setAlpha((float) 1);icon9n4.setAlpha((float) 1);
            icon10n1.setAlpha((float) 1);icon10n2.setAlpha((float) 1);
            icon11n1.setAlpha((float) 0.6);
        }

        if (kumulatiftoplamscore >124999) {


            if (level==10) {
                Toast.makeText(getApplicationContext(),getString(R.string.levelpass),Toast.LENGTH_LONG).show();
                charchooselayout.setVisibility(View.VISIBLE);
                level=11;
                spEditor.putInt("level", level);
                spEditor.commit();


                final MediaPlayer victory = MediaPlayer.create(this,R.raw.trumpet);
                victory.start();
                victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        victory.release();
                    }
                });
            }

            toplamscorebar.setMax(1250000);
            toplamscorebar.setProgress((int) kumulatiftoplamscore -125000);
            progrestxt.setText("Level 11 ");


            icon1n1.setAlpha((float) 1);icon1n2.setAlpha((float) 1);icon1n3.setAlpha((float) 1);icon1n4.setAlpha((float) 1);
            icon2n1.setAlpha((float) 1);icon2n2.setAlpha((float) 1);icon2n3.setAlpha((float) 1);icon2n4.setAlpha((float) 1);
            icon3n1.setAlpha((float) 1);icon3n2.setAlpha((float) 1);icon3n3.setAlpha((float) 1);icon3n4.setAlpha((float) 1);
            icon4n1.setAlpha((float) 1);icon4n2.setAlpha((float) 1);icon4n3.setAlpha((float) 1);icon4n4.setAlpha((float) 1);
            icon5n1.setAlpha((float) 1);icon5n2.setAlpha((float) 1);icon5n3.setAlpha((float) 1);icon5n4.setAlpha((float) 1);
            icon6n1.setAlpha((float) 1);icon6n2.setAlpha((float) 1);icon6n3.setAlpha((float) 1);icon6n4.setAlpha((float) 1);
            icon7n1.setAlpha((float) 1);icon7n2.setAlpha((float) 1);icon7n3.setAlpha((float) 1);icon7n4.setAlpha((float) 1);
            icon8n1.setAlpha((float) 1);icon8n2.setAlpha((float) 1);icon8n3.setAlpha((float) 1);icon8n4.setAlpha((float) 1);
            icon9n1.setAlpha((float) 1);icon9n2.setAlpha((float) 1);icon9n3.setAlpha((float) 1);icon9n4.setAlpha((float) 1);
            icon10n1.setAlpha((float) 1);icon10n2.setAlpha((float) 1);
            icon11n1.setAlpha((float) 1);

        }*/

        // level = sp.getInt("level",0);


///////////////////////////////////////


      /*  if (isSignedIn()) {


            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_smart_kids_kings), kumulatiftoplamscore);
            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_summation_stars), toplamascore);
            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_subtraction_stars), cikarmascore);
            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_division_stars), bolmescore);
            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_multiplication_stars), carpmascore);
            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_serials_stars), ritmikscore);
            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_greater__lesser_stars), buyukkucukscore);
            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_symmetry_stars), simetriscore);
            SmartGames.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_time_challenge_stars), timescore);


            if (toplamaskorkumulatif > 1000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_good_starter_summation));
            }
            if (cikarmaskorkumulatif > 1000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_good_starter_subtraction));
            }
            if (bolmeskorkumulatif > 1000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_good_starter_division));
            }
            if (carpmaskorkumulatif > 1000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_good_starter_multiplication));
            }
            if (buyukkucukskorkumulatif > 1000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_good_starter_greaterlesser));
            }
            if (ritmikskorkumulatif > 1000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_good_starter_serials));
            }
            if (simetriskorkumulatif > 1000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_good_starter_symmetry));
            }


            if (toplamaskorkumulatif > 20000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_clever_kid_summation));
            }
            if (cikarmaskorkumulatif > 20000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_clever_kid_subtraction));
            }
            if (carpmaskorkumulatif > 20000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_clever_kid_multiplication));
            }
            if (bolmeskorkumulatif > 20000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_clever_kid_division));
            }
            if (ritmikskorkumulatif > 20000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_clever_kid_serials));
            }
            if (simetriskorkumulatif > 20000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_clever_kid_symmetry));
            }

            if (toplamaskorkumulatif > 50000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_summation_proffesor));
            }
            if (cikarmaskorkumulatif > 50000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_subtraction_proffessor));
            }
            if (carpmaskorkumulatif > 50000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_multiplication_proffessor));
            }
            if (bolmeskorkumulatif > 50000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_division_proffessor));
            }
            if (buyukkucukskorkumulatif > 50000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_greater_of_lesser_proffessor));
            }
            if (ritmikskorkumulatif > 50000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_serials_proffessor));
            }
            if (simetriskorkumulatif > 50000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_symmetry_proffessor));
            }


            if (timescorekumulatif > 5000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_real_time_shooter));
            }
            if (timescorekumulatif > 10000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_real_time_crusher));
            }
            if (timescorekumulatif > 50000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_real_time_expert));
            }
            if (timescorekumulatif > 100000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_real_time_professor));
            }


            if (toplamaskorkumulatif > 5000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_5000_smart_points));
            }
            if (toplamaskorkumulatif > 10000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_10000_smart_points));
            }
            if (toplamaskorkumulatif > 25000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_25000_smart_points));
            }
            if (toplamaskorkumulatif > 40000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_40000_smart_points));
            }
            if (toplamaskorkumulatif > 80000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_80000_smart_points));
            }
            if (toplamaskorkumulatif > 100000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_100000_smart_points));
            }
            if (toplamaskorkumulatif > 150000) {
                SmartGames.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_150000_smart_points));
            }

        }*/


    }


    public void selectGame(View view) {

        switch (pagenumber) {
            case 0:
                Intent i = new Intent(MainmenuActivity.this, TwoplayerActivity.class);
                startActivity(i);
                this.finish();
                break;
            case 1:
                Intent j = new Intent(MainmenuActivity.this, TimechallengeActivity.class);
                startActivity(j);
                this.finish();
                break;
            case 2:
                Intent k = new Intent(MainmenuActivity.this, TrainingActivity.class);
                k.putExtra("four_operation", false);
                startActivity(k);
                this.finish();
                break;
            case 3:
                Intent l = new Intent(MainmenuActivity.this, TrainingActivity.class);
                l.putExtra("four_operation", true);
                startActivity(l);
                this.finish();
                break;
            case 4:
                removeads(view);
                break;
            case 5:
                Uri uri = Uri.parse("market://search?q=pub:Erdem+SALGIN");
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
                            Uri.parse("https://play.google.com/store/apps/developer?id=Erdem+SALGIN")));

                }
                break;
        }
    }

    public void setPage(View view) {

        switch (view.getId()) {
            case R.id.dot1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.dot2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.dot3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.dot4:
                viewPager.setCurrentItem(3);
                break;
            case R.id.dot5:
                viewPager.setCurrentItem(4);
                break;
            case R.id.dot6:
                viewPager.setCurrentItem(5);
                break;
        }


    }

    public void removeads(View view) {

        Uri uri = Uri.parse("market://details?id=com.deneme.erdemsalgin.smartkidspro");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {


            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {

                // startActivity(new Intent(Intent.ACTION_VIEW,
                //  Uri.parse("market://details?id=com.deneme.erdemsalgin.smartkidspro")));
                viewInBrowser(getApplicationContext(), "https://play.google.com/store/apps/details?id=com.deneme.erdemsalgin.smartkidspro");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void viewInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null != intent.resolveActivity(context.getPackageManager())) {
            context.startActivity(intent);
        }
    }


    public void leveldurumu(View view) {


        int fark = Constants.levels.get(user_level) - (sharedPrefManager.getIntegerFromSP("skor_total", 0));
        Toast.makeText(getApplicationContext(), fark + " " + getString(R.string.leveldurumu), Toast.LENGTH_SHORT).show();


    }

    private void showStats() {


    }

    private void connectGoogleGames() {

        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        signInClient.silentSignIn().addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                if (task.isSuccessful()) {
                    Log.i("SignDurum", "OK");
                    signedInAccount = task.getResult();

                } else {
                    Log.i("SignDurum", "PROBLEM");
                }

            }
        });

        signInClient.silentSignIn().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void showLeaderBoard() {
        Games.getLeaderboardsClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                .getLeaderboardIntent(getString(R.string.leaderboard_smart_kids_kings))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }

    private void showAchivement() {
        Games.getAchievementsClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                .getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                });
    }

    private void showByeScreen() {


        mainmenu_layout.setVisibility(View.GONE);
        byebyeLayout.setVisibility(View.VISIBLE);

        AppCompatButton appcikisBtn = findViewById(R.id.appcikisBtn);
        AppCompatButton rateBtn = findViewById(R.id.rateBtn);


        if (sharedPrefManager.getBooleanFromSP("rateclick", false)) {
            rateBtn.setVisibility(View.GONE);
        }

        appcikisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateapp();
            }
        });


    }

    private void fadeIn_fadeOut_Animation(final View fadein, final View fadeout) {

        fadein.setAlpha(0f);
        fadein.setVisibility(View.VISIBLE);

        ObjectAnimator fadeOutAnim = ObjectAnimator.ofFloat(fadeout, View.ALPHA, 1f, 0f);

        ObjectAnimator fadeInAnim = ObjectAnimator.ofFloat(fadein, View.ALPHA, 0f, 1f);
        fadeInAnim.setInterpolator(new DecelerateInterpolator());
        fadeOutAnim.setInterpolator(new AccelerateInterpolator());

        AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fadeout.setVisibility(View.GONE);
                fadein.setVisibility(View.VISIBLE);
            }
        });


        mAnimationSet.setDuration(500);
        mAnimationSet.playTogether(fadeOutAnim, fadeInAnim);
        mAnimationSet.start();


    }


    @Override
    public void onBackPressed() {


        if (byebyeLayout.getVisibility() == View.VISIBLE) {
            exitApp();
        } else if (charchooselayout.getVisibility() == View.VISIBLE) {
            fadeIn_fadeOut_Animation(mainmenu_layout, charchooselayout);

        } else {
            showByeScreen();
        }


    }

    public void rateapp() {

        sharedPrefManager.putBooleantoSP("rateclick", true);


        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

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


    public void appcikis(View view) {

        this.finishAffinity();

    }

    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();


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

    private void exitApp() {
        finish();
    }


}