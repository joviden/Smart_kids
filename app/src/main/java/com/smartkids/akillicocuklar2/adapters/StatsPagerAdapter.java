package com.smartkids.akillicocuklar2.adapters;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import com.smartkids.akillicocuklar2.R;
import com.smartkids.akillicocuklar2.models.Stats;

import java.util.List;


public class StatsPagerAdapter extends androidx.viewpager.widget.PagerAdapter {


    private Context context;

    private List<Stats> stats;


    public StatsPagerAdapter(Context context, List<Stats> stats) {
        this.context = context;
        this.stats = stats;
    }

    @Override
    public int getCount() {
        return stats.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.item_stats_pager, container, false);
        }



        final TextView sorusayisi = view.findViewById(R.id.sorusayisi);
        final TextView dogrusayisi = view.findViewById(R.id.dogrusayisi);
        TextView yanlissayisi = view.findViewById(R.id.yanlissayisi);
        TextView bossayisi = view.findViewById(R.id.bossayisi);
        TextView toplampuan = view.findViewById(R.id.toplampuan);
        ProgressBar pointsbar = view.findViewById(R.id.pointsbar);
        final AppCompatButton progressTxt = view.findViewById(R.id.progressTxt);



        sorusayisi.setText(String.valueOf(stats.get(position).getSoru()));
        sorusayisi.setText(String.valueOf(stats.get(position).getSoru()));
        dogrusayisi.setText(String.valueOf(stats.get(position).getDogru()));
        yanlissayisi.setText(String.valueOf(stats.get(position).getYanlis()));
        bossayisi.setText(String.valueOf(stats.get(position).getBos()));
        toplampuan.setText(String.valueOf(stats.get(position).getPoints()));
        int success_percent = 0;
        try {
            success_percent = (stats.get(position).getDogru() * 100) / stats.get(position).getSoru();
        } catch (Exception e) {
            e.printStackTrace();
        }


        pointsbar.setProgress(0);
        pointsbar.setMax(100 * 1000);
        ObjectAnimator animation = ObjectAnimator.ofInt(pointsbar, "progress", 0, success_percent * 1000);
        animation.setDuration(2000); // 3.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();

        ValueAnimator animator_percent = new ValueAnimator();
        animator_percent.setObjectValues(0, success_percent);
        animator_percent.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                progressTxt.setText("%" + String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator_percent.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator_percent.setDuration(2000);
        animator_percent.start();


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);


        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}

