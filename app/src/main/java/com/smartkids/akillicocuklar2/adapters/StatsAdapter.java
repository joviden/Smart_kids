package com.smartkids.akillicocuklar2.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkids.akillicocuklar2.R;
import com.smartkids.akillicocuklar2.models.Stats;

import java.util.List;



public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {

    private Activity context;
    private List<Stats> stats;

    public StatsAdapter(Activity context, List<Stats> stats) {
        this.context = context;
        this.stats = stats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {




    }

    @Override
    public int getItemCount() {

        return stats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
