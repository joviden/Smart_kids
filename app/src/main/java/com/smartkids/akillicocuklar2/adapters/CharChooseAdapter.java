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
import java.util.List;

public class CharChooseAdapter extends RecyclerView.Adapter<CharChooseAdapter.ViewHolder> {

    private Activity context;
    private List<Character> characters;
    private int level;
    private int icon_choosen;



    public CharChooseAdapter(Activity context, List<Character> characters, int level, int icon_choosen) {
        this.context = context;
        this.characters = characters;
        this.level = level;
        this.icon_choosen = icon_choosen;

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

        int tag = position*4;


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

        holder.icon1.setTag(String.valueOf(4*position));
        holder.icon2.setTag(String.valueOf(4*position+1));
        holder.icon3.setTag(String.valueOf(4*position+2));
        holder.icon4.setTag(String.valueOf(4*position+3));

        holder.name1.setTag(String.valueOf(4*position)+"txt");
        holder.name2.setTag(String.valueOf(4*position+1)+"txt");
        holder.name3.setTag(String.valueOf(4*position+2)+"txt");
        holder.name4.setTag(String.valueOf(4*position+3)+"txt");

        holder.levelTxt.setText(characters.get(position).getLevel());
        holder.name1.setText(characters.get(4*position).getNames());
        holder.name2.setText(characters.get(4*position+1).getNames());
        holder.name3.setText(characters.get(4*position+2).getNames());
        holder.name4.setText(characters.get(4*position+3).getNames());


        holder.icon1.setImageResource(characters.get(4*position).getImage());
        holder.icon2.setImageResource(characters.get(4*position+1).getImage());
        holder.icon3.setImageResource(characters.get(4*position+2).getImage());
        holder.icon4.setImageResource(characters.get(4*position+3).getImage());

        if (tag==icon_choosen) {
            holder.icon1.setBackgroundResource(R.drawable.cerceve_button_selected);
            holder.name1.setBackgroundResource(R.drawable.cerceve_button_selected);
        }else if (tag+1==icon_choosen) {
            holder.icon2.setBackgroundResource(R.drawable.cerceve_button_selected);
            holder.name2.setBackgroundResource(R.drawable.cerceve_button_selected);
        }else if (tag+2==icon_choosen) {
            holder.icon3.setBackgroundResource(R.drawable.cerceve_button_selected);
            holder.name3.setBackgroundResource(R.drawable.cerceve_button_selected);
        }else if (tag+3==icon_choosen) {
            holder.icon4.setBackgroundResource(R.drawable.cerceve_button_selected);
            holder.name4.setBackgroundResource(R.drawable.cerceve_button_selected);
        }






    }

    @Override
    public int getItemCount() {

        return characters.size()/4;
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
