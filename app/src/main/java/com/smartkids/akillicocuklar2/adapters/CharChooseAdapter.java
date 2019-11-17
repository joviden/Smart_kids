package com.smartkids.akillicocuklar2.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartkids.akillicocuklar2.R;
import com.smartkids.akillicocuklar2.models.Character;
import com.smartkids.akillicocuklar2.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class CharChooseAdapter extends RecyclerView.Adapter<CharChooseAdapter.ViewHolder> {

    private Activity context;
    private List<Character> characters;
    private int level;


    public CharChooseAdapter(Activity context, List<Character> characters, int level) {
        this.context = context;
        this.characters = characters;
        this.level = level;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (position < level) {
            holder.icon1.setAlpha(1.0f);
            holder.icon2.setAlpha(1.0f);
            holder.icon3.setAlpha(1.0f);
            holder.icon4.setAlpha(1.0f);
            holder.name1.setAlpha(1.0f);
            holder.name2.setAlpha(1.0f);
            holder.name3.setAlpha(1.0f);
            holder.name4.setAlpha(1.0f);
        } else {
            holder.icon1.setAlpha(.6f);
            holder.icon2.setAlpha(.6f);
            holder.icon3.setAlpha(.6f);
            holder.icon4.setAlpha(.6f);
            holder.name1.setAlpha(.6f);
            holder.name2.setAlpha(.6f);
            holder.name3.setAlpha(.6f);
            holder.name4.setAlpha(.6f);
        }

        holder.icon1.setTag(String.valueOf(0));
        holder.icon2.setTag(String.valueOf(1));
        holder.icon3.setTag(String.valueOf(2));
        holder.icon4.setTag(String.valueOf(3));

        holder.name1.setTag(0 +"txt");
        holder.name2.setTag(1 +"txt");
        holder.name3.setTag(2 +"txt");
        holder.name4.setTag(3 +"txt");

        holder.levelTxt.setText(characters.get(position).getLevel());
        holder.name1.setText(characters.get(position).getNames().get(0));
        holder.name2.setText(characters.get(position).getNames().get(1));
        holder.name3.setText(characters.get(position).getNames().get(2));
        holder.name4.setText(characters.get(position).getNames().get(3));
        holder.icon1.setImageResource(characters.get(position).getImages().get(0));
        holder.icon2.setImageResource(characters.get(position).getImages().get(1));
        holder.icon3.setImageResource(characters.get(position).getImages().get(2));
        holder.icon4.setImageResource(characters.get(position).getImages().get(3));


    }

    @Override
    public int getItemCount() {

        return characters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView levelTxt, name1, name2, name3, name4;
        private ImageView icon1, icon2, icon3, icon4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            levelTxt = itemView.findViewById(R.id.levelTxt);
            name1 = itemView.findViewById(R.id.name1);
            name2 = itemView.findViewById(R.id.name2);
            name3 = itemView.findViewById(R.id.name3);
            name4 = itemView.findViewById(R.id.name4);
            icon1 = itemView.findViewById(R.id.icon1);
            icon2 = itemView.findViewById(R.id.icon2);
            icon3 = itemView.findViewById(R.id.icon3);
            icon4 = itemView.findViewById(R.id.icon4);
        }
    }
}
