package com.smartkids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.ads.MobileAds;




public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView logo = (ImageView)findViewById(R.id.logo);
        ImageView logo2 = (ImageView)findViewById(R.id.logo2);
        ImageView stickman = (ImageView)findViewById(R.id.stickman);
        final Button girisBtn = (Button)findViewById(R.id.girisBtn);

        logo.animate().alpha(1f).setDuration(4000);
        logo2.animate().alpha(1f).setDuration(4000);
        stickman.animate().alpha(0.6f).setDuration(3000);

        girisBtn.setVisibility(View.INVISIBLE);
        girisBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                girisBtn.setVisibility(View.VISIBLE);
            }
        },3000);

        MobileAds.initialize(this, "ca-app-pub-4100535460120599~1018273710");




        TypeWriter tw =findViewById(R.id.tv);
        //tw.setText(R.string.karsilamametin);
        tw.setCharacterDelay(100);


        tw.animateText(getText(R.string.karsilamametin));



        //tw.animateText(""+R.string.karsilamametin);


    }

    public void giris(View view) {
        Button girisBtn = (Button)findViewById(R.id.girisBtn);

        Intent i = new Intent(MainActivity.this,HomeActivity.class); startActivity(i);


    }


}
