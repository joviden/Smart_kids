package com.smartkids.akillicocuklar2.adapters;


import android.animation.ObjectAnimator;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.smartkids.akillicocuklar2.R;
import com.smartkids.akillicocuklar2.models.Training;
import com.smartkids.akillicocuklar2.utils.Constants;

import java.util.List;


public class TrainingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int BANNER_AD_VIEW_TYPE = 1;


    private final Context context;
    private final List<Object> recyclerViewItems;


    public TrainingListAdapter(Context context, List<Object> recyclerViewItems) {
        this.context = context;
        this.recyclerViewItems = recyclerViewItems;
    }


    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView konubaslikTxt,progressTxt;
        private ImageView iconImg;
        private ProgressBar progressBar;

        MenuItemViewHolder(View view) {
            super(view);
            konubaslikTxt = itemView.findViewById(R.id.konubaslikTxt);
            iconImg = view.findViewById(R.id.iconImg);
            progressBar = view.findViewById(R.id.pointsbar);
            progressTxt = view.findViewById(R.id.progressTxt);

        }
    }

    /**
     * The {@link AdViewHolder} class.
     */
    public class AdViewHolder extends RecyclerView.ViewHolder {

        AdViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    /**
     * Determines the view type for the given position.
     */
    @Override
    public int getItemViewType(int position) {
        if (recyclerViewItems.get(position) instanceof AdView) {
            return BANNER_AD_VIEW_TYPE;
        }else {
            return MENU_ITEM_VIEW_TYPE;
        }

       /* return (position % Constants.items_Per_Ad == 0) ? BANNER_AD_VIEW_TYPE
                : MENU_ITEM_VIEW_TYPE;*/
    }

    /**
     * Creates a new view for a menu item view or a banner ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.item_training, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);
            case BANNER_AD_VIEW_TYPE:
                // fall through
            default:
                View bannerLayoutView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.native_ad_container,
                        viewGroup, false);
                return new AdViewHolder(bannerLayoutView);
        }
    }

    /**
     * Replaces the content in the views that make up the menu item view and the
     * banner ad view. This method is invoked by the layout manager.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case MENU_ITEM_VIEW_TYPE:
                    MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
                    Training training = (Training) recyclerViewItems.get(position);



                    menuItemHolder.konubaslikTxt.setText(training.getName());
                    menuItemHolder.iconImg.setImageResource(training.getImage());
                    menuItemHolder.progressTxt.setText(training.getSuccess()+"%");


                    menuItemHolder.progressBar.setTag(training.getName());
                    menuItemHolder.konubaslikTxt.setTag(training.getName());
                    menuItemHolder.iconImg.setTag(training.getName());
                    menuItemHolder.progressTxt.setTag(training.getName());


                    menuItemHolder.progressBar.setProgress(0);
                    menuItemHolder.progressBar.setMax(100);
                    ObjectAnimator animation = ObjectAnimator.ofInt(menuItemHolder.progressBar, "progress", 0, training.getSuccess() );
                    animation.setDuration(2000); // 3.5 second
                    animation.setInterpolator(new DecelerateInterpolator());
                    animation.start();


                    break;
                case BANNER_AD_VIEW_TYPE:
                    // fall through

                default:
                    AdViewHolder bannerHolder = (AdViewHolder) holder;
                    AdView adView = (AdView) recyclerViewItems.get(position);
                    ViewGroup adCardView = (ViewGroup) bannerHolder.itemView;
                    // The AdViewHolder recycled by the RecyclerView may be a different
                    // instance than the one used previously for this position. Clear the
                    // AdViewHolder of any subviews in case it has a different
                    // AdView associated with it, and make sure the AdView for this position doesn't
                    // already have a parent of a different recycled AdViewHolder.
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((ViewGroup) adView.getParent()).removeView(adView);
                    }

                    // Add the banner ad to the ad view.
                    adCardView.addView(adView);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }


    }

}