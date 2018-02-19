package com.smartkids.akillicocuklar2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer optionclick = MediaPlayer.create(this,R.raw.intromusic);
        optionclick.start();
        optionclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                optionclick.release();
            }
        });




        ImageView logo = (ImageView) findViewById(R.id.logo);

        ImageView stickman = (ImageView)findViewById(R.id.stickman);
        final ImageView entrygif = (ImageView)findViewById(R.id.entrygif);

        final Button girisBtn = (Button)findViewById(R.id.girisBtn);



        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        entrygif.startAnimation(fadeInAnimation);

        girisBtn.setVisibility(View.INVISIBLE);
        girisBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                entrygif.setVisibility(View.GONE);
                girisBtn.setVisibility(View.VISIBLE);
            }
        },3200);


        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");




       // TypeWriter tw =findViewById(R.id.tv);
        //tw.setText(R.string.karsilamametin);
       // tw.setCharacterDelay(100);


       // tw.animateText(getText(R.string.karsilamametin));



        //tw.animateText(""+R.string.karsilamametin);


    }

    public void giris(View view) {
        Button girisBtn = (Button)findViewById(R.id.girisBtn);

        Intent i = new Intent(MainActivity.this,MainmenuActivity.class); startActivity(i);


    }


}
