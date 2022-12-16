package com.example.mymarvelapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.mymarvelapp.Activities.CharacterDetailsActivity;
import com.example.mymarvelapp.Models.Comics;
import com.example.mymarvelapp.Models.MatchData;
import com.example.mymarvelapp.R;

import java.util.ArrayList;
import java.util.List;

public class ComicAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflate;


    List<Comics> comicsUrls;

    // Constructor
    public ComicAdapter(Context c, List<Comics> comicsUrls) {
        this.mContext = c;

        this.comicsUrls = comicsUrls;
        this.mLayoutInflate = LayoutInflater.from(mContext);
    }



    @Override
    public int getCount() {
        if(comicsUrls != null) return  comicsUrls.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(comicsUrls != null && comicsUrls.size() > position) return  comicsUrls.get(position);

        return null;
    }

    @Override
    public long getItemId(int position) {
        if(comicsUrls != null && comicsUrls.size() > position) return  comicsUrls.get(position).getId();
        return 0;
    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
      if (convertView == null) {

            viewHolder = new ViewHolder();

          convertView = mLayoutInflate.inflate(R.layout.comic_image, parent,
                    false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String url = comicsUrls.get(position).getPath() + "." + comicsUrls.get(position).getExtension();
      Log.e("api", "Response: " + url);


        if(comicsUrls != null) {
            Glide
                    .with(mContext)
                    .load(url)
                    .centerCrop()
                    .into(viewHolder.imageView);
        }




        return convertView;
    }

    private class ViewHolder {
        public ImageView imageView;
    }

}
