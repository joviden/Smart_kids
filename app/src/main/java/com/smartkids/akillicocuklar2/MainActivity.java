package com.smartkids.akillicocuklar2;

import android.content.Intent;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;


public class MainActivity extends AppCompatActivity {
    MediaPlayer introSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        introSound = MediaPlayer.create(this,R.raw.intromusic);
        introSound.start();
        introSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                introSound.release();
                startActivity(new Intent(MainActivity.this, MainmenuActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });

        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");


        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            GoogleSignInOptions signInOptions =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                            .requestScopes(Games.SCOPE_GAMES, Games.SCOPE_GAMES_LITE)
                            .build();
            GoogleSignInClient signInClient = GoogleSignIn.getClient(MainActivity.this, signInOptions);
         //   signInClient.signOut();
        }



    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (introSound!=null && !introSound.isPlaying()){
            Log.i("introSound","Paused");
            introSound.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



}
