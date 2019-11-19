package com.smartkids.akillicocuklar2.adapters;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartkids.akillicocuklar2.MainmenuActivity;
import com.smartkids.akillicocuklar2.R;
import com.smartkids.akillicocuklar2.models.Character;
import com.smartkids.akillicocuklar2.models.SmartGames;

import java.util.ArrayList;
import java.util.List;


public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {


    private Context context;
    private List<SmartGames> smartGames;



    public PagerAdapter(Context context, List<SmartGames> smartGames) {
        this.context = context;
        this.smartGames = smartGames;
    }

    @Override
    public int getCount() {
        return smartGames.size();
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
            view = layoutInflater.inflate(R.layout.item_slider,container,false);
        }


        TextView konubaslikTxt =  view.findViewById(R.id.konubaslikTxt);
        TextView konuaciklamaTxt =  view.findViewById(R.id.konuaciklamaTxt);
        ImageView imageImg = view.findViewById(R.id.iconImg);


        konubaslikTxt.setText(smartGames.get(position).getName());
        konuaciklamaTxt.setText(smartGames.get(position).getDescription());
        imageImg.setImageResource(smartGames.get(position).getImage());




        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);




        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
