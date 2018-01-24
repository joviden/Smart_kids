package com.smartkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;



import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.smartkids.R;

/**
 * Created by erdem.salgin on 21.12.2017.
 */

public class LooseAnimeActivity extends Activity {


    pl.droidsonroids.gif.GifImageView gifRAndom;
    Integer i,z;
    MediaPlayer loosersound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.looseanimegiflayout);

        List<Integer> drawables = Arrays.asList(R.drawable.img_l0,R.drawable.img_l1,R.drawable.img_l2,R.drawable.img_l3,R.drawable.img_l4,R.drawable.img_l5,R.drawable.img_l6,R.drawable.img_l7,R.drawable.img_l8 ,R.drawable.img_l9
                ,R.drawable.img_l10 ,R.drawable.img_l11,R.drawable.img_l12,R.drawable.img_l13,R.drawable.img_l14,R.drawable.img_l15,R.drawable.img_l16,R.drawable.img_l17);

        List<Integer> raw = Arrays.asList(R.raw.loosesound_0,R.raw.loosesound_1,R.raw.loosesound_2,R.raw.loosesound_3);



        final pl.droidsonroids.gif.GifImageView gifRandom = (pl.droidsonroids.gif.GifImageView)findViewById(R.id.gifRandom);


        Random random = new Random();
        i = random.nextInt(18-0)+0;
        //Log.i("random",i.toString());

        gifRandom.setBackgroundResource(drawables.get(i));

        Random randomsound = new Random();
        z = random.nextInt(4-0)+0;
        loosersound = MediaPlayer.create(this,raw.get(z));
        loosersound.start();
        new CountDownTimer(2800, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                loosersound.stop();
                loosersound.release();
            }
        }.start();



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);
    }


}



//-------------------------



