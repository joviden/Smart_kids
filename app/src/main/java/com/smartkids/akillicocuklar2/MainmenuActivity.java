package com.smartkids.akillicocuklar2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
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
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
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
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.smartkids.akillicocuklar2.adapters.CharChooseAdapter;
import com.smartkids.akillicocuklar2.adapters.KonularPagerAdapter;
import com.smartkids.akillicocuklar2.adapters.StatsPagerAdapter;
import com.smartkids.akillicocuklar2.models.Character;
import com.smartkids.akillicocuklar2.models.SmartGames;
import com.smartkids.akillicocuklar2.models.Stats;
import com.smartkids.akillicocuklar2.utils.Constants;
import com.smartkids.akillicocuklar2.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MainmenuActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private SharedPrefManager sharedPrefManager;
    private int user_level;
    private boolean char_just_choosen = false;
    private List<Character> characters;
    private List<SmartGames> smartGames;
    private List<Stats> stats;
    private int pagenumber, pagenumberStats;
    private List<ImageView> dots, dots2;
    private LeaderboardsClient mLeaderboardsClient;
    private AlertDialog alert_Exit;


    ViewPager viewPager, viewpagerStats;
    KonularPagerAdapter konularPagerAdapter;
    StatsPagerAdapter statsAdapter;


    private static final int RC_LEADERBOARD_UI = 9004;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient signInClient;

    private CharChooseAdapter chooseAdapter;

    private RecyclerView char_recyclerView;

    private ConstraintLayout mainmenu_layout, charchooselayout,statslayout;
    private ImageView characterview;

    private AppCompatButton profileBtn, leaderboardBtn, achievementBtn, rateappBtn, shareBtn;


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

        statslayout = findViewById(R.id.statslayout);
        profileBtn = findViewById(R.id.profileBtn);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        achievementBtn = findViewById(R.id.achievementBtn);
        rateappBtn = findViewById(R.id.rateappBtn);
        shareBtn = findViewById(R.id.shareBtn);
        characterview = findViewById(R.id.characterview);



        statslayout.setVisibility(View.GONE);

        user_level = sharedPrefManager.getIntegerFromSP("level", 1);

        if (isSignedIn()) {
            updateLeaderboards();
            checkAchievements();
            Log.i("checkSign", "Not null");
        } else {
            Log.i("checkSign", " null");
            connectGoogleGames();

        }


        setLevel();
        setListeners();
        inflatePager();
        inflateStatsPager();
        setCharacterAdapter();


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

        progressTxt.setText(getText(R.string.level) + " " + user_level);

        if (user_level != sharedPrefManager.getIntegerFromSP("level", 1)) {
            newLevelReached();
            Log.i("total_Score", "New Level:" + user_level);
        } else {

            int percantage = (sharedPrefManager.getIntegerFromSP("skor_total", 0) * 100) / Constants.levels.get(user_level);
            progressBar.setProgress(0);
            progressBar.setMax(100 * 1000);
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, percantage * 1000);
            animation.setDuration(2000);
            animation.setInterpolator(new LinearInterpolator());
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
                fadeIn_fadeOut_Animation(statslayout, mainmenu_layout);
            }
        });
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this) != null) {
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
                if (GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this) != null) {
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

    private void inflateStatsPager() {

        stats = new ArrayList<>();
        final List<String> konular = new ArrayList<>(Arrays.asList(getString(R.string.konulartoplama), getString(R.string.konularcikarma), getString(R.string.konularcarpma), getString(R.string.konularbolme)));

        final TextView konubaslik_stats = findViewById(R.id.konubaslik_stats);

        konubaslik_stats.setText(konular.get(0));


        for (int i = 0; i < konular.size(); i++) {

            stats.add(new Stats(konular.get(i), sharedPrefManager.getIntegerFromSP(konular.get(i) + "soru", 0),
                    sharedPrefManager.getIntegerFromSP(konular.get(i) + "dogru", 0),
                    sharedPrefManager.getIntegerFromSP(konular.get(i) + "yanlis", 0),
                    sharedPrefManager.getIntegerFromSP(konular.get(i) + "bos", 0),
                    sharedPrefManager.getIntegerFromSP(konular.get(i) + "skor", 0),
                    R.drawable.icon_mental));


        }

        stats.add(new Stats(getString(R.string.total),
                sharedPrefManager.getIntegerFromSP("soru_total", 0),
                sharedPrefManager.getIntegerFromSP("dogru_total", 0),
                sharedPrefManager.getIntegerFromSP("yanlis_total", 0),
                sharedPrefManager.getIntegerFromSP("bos_total", 0),
                sharedPrefManager.getIntegerFromSP("skor_total", 0), R.drawable.icon_mental));


        final ImageView dot1 = findViewById(R.id.dot1d);
        final ImageView dot2 = findViewById(R.id.dot2d);
        final ImageView dot3 = findViewById(R.id.dot3d);
        final ImageView dot4 = findViewById(R.id.dot4d);
        final ImageView dot5 = findViewById(R.id.dot5d);

        dots2 = new ArrayList<>();
        dots2.add(dot1);
        dots2.add(dot2);
        dots2.add(dot3);
        dots2.add(dot4);
        dots2.add(dot5);
        try {

            viewpagerStats = findViewById(R.id.viewpagerStats);


            statsAdapter = new StatsPagerAdapter(this, stats);
            viewpagerStats.setAdapter(statsAdapter);

            konular.add(getString(R.string.total));

            viewpagerStats.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                }

                @Override
                public void onPageSelected(int position) {

                    pagenumberStats = position;
                    Log.i("position", String.valueOf(pagenumberStats));

                    konubaslik_stats.setText(konular.get(position));

                    for (int i = 0; i < stats.size(); i++) {
                        if (pagenumberStats != i) {
                            dots2.get(i).setBackgroundResource(R.drawable.dot_unselected);
                        } else {
                            dots2.get(i).setBackgroundResource(R.drawable.dot_selected);
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


        AppCompatButton statscikisBtn = findViewById(R.id.statscikisBtn);

        statscikisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn_fadeOut_Animation(mainmenu_layout, statslayout);
            }
        });


    }

    private void inflatePager() {


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


            konularPagerAdapter = new KonularPagerAdapter(this, smartGames);
            viewPager.setAdapter(konularPagerAdapter);
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

        Log.i("adapterTag", "Tag:" + tag + "Tag/4" + tag / 4);


        if (view.getAlpha() != 1) {
            Toast.makeText(getApplicationContext(), getString(R.string.levelyetersiz), Toast.LENGTH_SHORT).show();
        } else if (!char_just_choosen) {

            Animation animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
            Animation vibrate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate);

            RecyclerView.ViewHolder viewHolder_new = char_recyclerView.findViewHolderForAdapterPosition((tag / 4));
            RecyclerView.ViewHolder viewHolder_ex = char_recyclerView.findViewHolderForAdapterPosition((sharedPrefManager.getIntegerFromSP("avatar_chosen", 0) / 4));
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


    private void updateLeaderboards() {

        Log.i("checkLeader", "sent");

        if (GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this) != null) {
            mLeaderboardsClient = Games.getLeaderboardsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)));
            mLeaderboardsClient.submitScore(Constants.leaderboard_total, sharedPrefManager.getIntegerFromSP("skor_total", 0));
        }

    }

    private void checkAchievements() {
        if (GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this) != null) {


            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartoplama) + "dogru", 0) > 9) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_good_starter_summation));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcikarma) + "dogru", 0) > 9) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_good_starter_subtraction));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcarpma) + "dogru", 0) > 9) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_good_starter_multiplication));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbolme) + "dogru", 0) > 9) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_good_starter_division));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbuyukkucuk) + "dogru", 0) > 9) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_good_starter_greaterlesser));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik) + "dogru", 0) > 9) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_good_starter_serials));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry) + "dogru", 0) > 9) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_good_starter_symmetry));
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartoplama) + "dogru", 0) > 49) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_clever_kid_summation));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcikarma) + "dogru", 0) > 49) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_clever_kid_subtraction));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcarpma) + "dogru", 0) > 49) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_clever_kid_multiplication));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbolme) + "dogru", 0) > 49) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_clever_kid_division));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbuyukkucuk) + "dogru", 0) > 49) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_clever_kid_greaterlesser));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik) + "dogru", 0) > 49) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_clever_kid_serials));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry) + "dogru", 0) > 49) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_clever_kid_symmetry));
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartoplama) + "dogru", 0) > 199) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_summation_expert));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcikarma) + "dogru", 0) > 199) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_subtraction_expert));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcarpma) + "dogru", 0) > 199) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_multiplication_expert));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbolme) + "dogru", 0) > 199) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_division_expert));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbuyukkucuk) + "dogru", 0) > 199) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_greater_or_lesser_expert));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik) + "dogru", 0) > 199) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_serials_expert));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry) + "dogru", 0) > 199) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_symmetry_expert));
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartoplama) + "dogru", 0) > 499) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_summation_proffesor));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcikarma) + "dogru", 0) > 499) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_subtraction_proffessor));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularcarpma) + "dogru", 0) > 499) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_multiplication_proffessor));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbolme) + "dogru", 0) > 499) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_division_proffessor));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularbuyukkucuk) + "dogru", 0) > 499) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_greater_of_lesser_proffessor));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularritmik) + "dogru", 0) > 499) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_serials_proffessor));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konularsimetry) + "dogru", 0) > 499) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_symmetry_proffessor));
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (sharedPrefManager.getIntegerFromSP("skor_total", 0) > 4999) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_5000_smart_points));
            }
            if (sharedPrefManager.getIntegerFromSP("skor_total", 0) > 9999) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_10000_smart_points));
            }
            if (sharedPrefManager.getIntegerFromSP("skor_total", 0) > 24999) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_25000_smart_points));
            }
            if (sharedPrefManager.getIntegerFromSP("skor_total", 0) > 39999) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_40000_smart_points));
            }
            if (sharedPrefManager.getIntegerFromSP("skor_total", 0) > 79999) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_80000_smart_points));
            }
            if (sharedPrefManager.getIntegerFromSP("skor_total", 0) > 99999) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_100000_smart_points));
            }
            if (sharedPrefManager.getIntegerFromSP("skor_total", 0) > 149999) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_150000_smart_points));
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartimechallange) + "skor", 0) > 4999) {
                Log.i("checkUnlock", "unlocked");
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_time_challenge_shooter));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartimechallange) + "skor", 0) > 9999) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_time_challenge_crusher));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartimechallange) + "skor", 0) > 49999) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_time_challenge_expert));
            }
            if (sharedPrefManager.getIntegerFromSP(getString(R.string.konulartimechallange) + "skor", 0) > 99999) {
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this)).unlock(getString(R.string.achievement_time_challenge_professor));
            }

        }


    }


    public void onResume() {
        super.onResume();


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
                Uri uri = Uri.parse("market://search?q=pub:kodemachines");
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

    public void setPageStats(View view) {

        switch (view.getId()) {
            case R.id.dot1d:
                viewpagerStats.setCurrentItem(0);
                break;
            case R.id.dot2d:
                viewpagerStats.setCurrentItem(1);
                break;
            case R.id.dot3d:
                viewpagerStats.setCurrentItem(2);
                break;
            case R.id.dot4d:
                viewpagerStats.setCurrentItem(3);
                break;
            case R.id.dot5d:
                viewpagerStats.setCurrentItem(4);
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


    private void connectGoogleGames() {

        Log.i("checkSign", "connectGoogleGames");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                        .requestScopes(Games.SCOPE_GAMES, Games.SCOPE_GAMES_LITE)
                        .build();


        signInClient = GoogleSignIn.getClient(MainmenuActivity.this, signInOptions);

        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);


    }

    private void showLeaderBoard() {
        Games.getLeaderboardsClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                .getLeaderboardIntent(Constants.leaderboard_total)
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


        View view = getLayoutInflater().inflate(R.layout.alert_bye_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alert_Exit = builder.create();
        alert_Exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert_Exit.setView(view);
        alert_Exit.show();
        alert_Exit.setCancelable(true);



        AppCompatButton appcikisBtn = view.findViewById(R.id.appcikisBtn);
        AppCompatButton rateBtn = view.findViewById(R.id.rateBtn);


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


        if (charchooselayout.getVisibility() == View.VISIBLE) {
            fadeIn_fadeOut_Animation(mainmenu_layout, charchooselayout);

        } else if (statslayout.getVisibility() == View.VISIBLE) {
            fadeIn_fadeOut_Animation(mainmenu_layout, statslayout);

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

                GoogleSignInAccount signedInAccount = result.getSignInAccount();
                // The signed in account is stored in the result.
                Log.i("checkSign", "Signed Result");

                GamesClient gamesClient = Games.getGamesClient(getApplicationContext(), GoogleSignIn.getLastSignedInAccount(MainmenuActivity.this));
                gamesClient.setViewForPopups(getWindow().getDecorView().findViewById(android.R.id.content));
                gamesClient.setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

                updateLeaderboards();
                checkAchievements();


            } else {

                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.playgamesmassage);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


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