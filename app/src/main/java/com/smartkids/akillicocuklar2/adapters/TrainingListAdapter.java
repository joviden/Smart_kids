package com.smartkids.akillicocuklar2.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkids.akillicocuklar2.R;
import com.smartkids.akillicocuklar2.models.Training;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.ViewHolder> {

    private Activity context;
    private List<Training> trainingList;





    public TrainingListAdapter(Activity context, List<Training> trainingList) {
        this.context = context;
        this.trainingList = trainingList;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_training, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.konubaslikTxt.setText(trainingList.get(position).getName());
        holder.iconImg.setImageResource(trainingList.get(position).getImage());
        holder.progressTxt.setText(trainingList.get(position).getSuccess()+"%");


        holder.progressBar.setTag(String.valueOf(position));
        holder.konubaslikTxt.setTag(String.valueOf(position));
        holder.iconImg.setTag(String.valueOf(position));
        holder.progressTxt.setTag(String.valueOf(position));













    }

    @Override
    public int getItemCount() {

        return trainingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView konubaslikTxt,progressTxt;
        private ImageView iconImg;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            konubaslikTxt = itemView.findViewById(R.id.konubaslikTxt);
            iconImg = itemView.findViewById(R.id.iconImg);
            progressBar = itemView.findViewById(R.id.pointsbar);
            progressTxt = itemView.findViewById(R.id.progressTxt);

        }
    }
}
