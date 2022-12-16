package com.example.mymarvelapp.Adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymarvelapp.Activities.CharacterDetailsActivity;
import com.example.mymarvelapp.Activities.MainActivity;
import com.example.mymarvelapp.Models.MatchData;
import com.example.mymarvelapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.TempHolder> {
    MainActivity mainActivity;
    List<MatchData> tempList;

    public TempAdapter(MainActivity mainActivity, List<MatchData> tempList) {
        this.mainActivity = mainActivity;
        this.tempList = tempList;
    }


    @NonNull
    public TempHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new TempAdapter.TempHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TempHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemText.setText(tempList.get(position).getName());

        String url = tempList.get(position).getResourceURI() + "." + tempList.get(position).getPath();

        if (tempList.get(position).getPath() != null) {

            Glide.with(mainActivity)
                    .load(url)
                    .into(holder.imgView);
        } else {
            holder.imgView.setImageDrawable(null);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CharacterDetailsActivity.class);
                intent.putExtra("name", tempList.get(position).getName());
                intent.putExtra("desc", tempList.get(position).getDescription());
                intent.putExtra("image", url);
                intent.putExtra("charId",tempList.get(position).getID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }


    public void filterList(List<MatchData> filteredList){
        tempList = filteredList;
        notifyDataSetChanged();

    }

    public class TempHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        ImageView imgView;

        public TempHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.itemText);
            imgView = itemView.findViewById(R.id.imgView);
        }
    }
}
