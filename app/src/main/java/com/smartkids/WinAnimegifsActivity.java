package com.smartkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;

import com.smartkids.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by erdem.salgin on 21.12.2017.
 */

public class WinAnimegifsActivity extends Activity {


    pl.droidsonroids.gif.GifImageView gifRAndom;
    MediaPlayer victorysound;
    Integer i,z;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winanimegiflayout);

        List<Integer> drawables = Arrays.asList(R.drawable.img_0,R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,R.drawable.img_5,R.drawable.img_6,R.drawable.img_7,R.drawable.img_8 ,R.drawable.img_9
                ,R.drawable.img_10 ,R.drawable.img_11,R.drawable.img_12,R.drawable.img_13,R.drawable.img_14,R.drawable.img_15,R.drawable.img_16,R.drawable.img_17,R.drawable.img_18,R.drawable.img_19,R.drawable.img_20
                ,R.drawable.img_21,R.drawable.img_22,R.drawable.img_23,R.drawable.img_24,R.drawable.img_25,R.drawable.img_26,R.drawable.img_27,R.drawable.img_28,R.drawable.img_29,R.drawable.img_30,R.drawable.img_31);

        List<Integer> raw = Arrays.asList(R.raw.victorsound_0,R.raw.victorsound_1,R.raw.victorsound_2,R.raw.victorsound_3,R.raw.victorsound_4,R.raw.victorsound_5);



        final pl.droidsonroids.gif.GifImageView gifRandom = (pl.droidsonroids.gif.GifImageView)findViewById(R.id.gifRandom);


        Random random = new Random();
        i = random.nextInt(32-0)+0;
        //Log.i("random",i.toString());

            gifRandom.setBackgroundResource(drawables.get(i));



        Random randomsound = new Random();
        z = random.nextInt(6-0)+0;
        victorysound = MediaPlayer.create(this,raw.get(z));
        victorysound.start();
        new CountDownTimer(2200, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                victorysound.stop();
                victorysound.release();
            }
        }.start();



        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);

    }


    }



//-------------------------



