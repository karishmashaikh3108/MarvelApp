package com.example.mymarvelapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mymarvelapp.Adapter.ComicAdapter;
import com.example.mymarvelapp.Adapter.TempAdapter;
import com.example.mymarvelapp.Helper.GenerateMD5Hash;
import com.example.mymarvelapp.Models.Comics;
import com.example.mymarvelapp.Models.MatchData;
import com.example.mymarvelapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CharacterDetailsActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView descTextView;
    ImageView charImageView;
    GridView gridView;
    String api;
    ArrayList<Comics> comicsUrls;
    ComicAdapter comicAdapter;
    ProgressDialog progress;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        gridView = (GridView) findViewById(R.id.grid_View1);

        progress = new ProgressDialog(this);
        comicsUrls = new ArrayList<Comics>();
        long charId = getIntent().getLongExtra("charId", 0);

        String MARVEL_PRIVATE_API_KEY = "9729e975544ce801d758a9227bafb427d7b19fa9";
        String MARVEL_PUBLIC_API_KEY = "44f29e802dd281f1fe404e263da01a00";
        String timeStamp = new Timestamp(System.currentTimeMillis()) + "";
        timeStamp = timeStamp.replaceAll("\\s", "");

        String FinalAPIHashingString = timeStamp+MARVEL_PRIVATE_API_KEY+MARVEL_PUBLIC_API_KEY;

        String hashedValue = GenerateMD5Hash.digest(FinalAPIHashingString);
        api = "https://gateway.marvel.com/v1/public/characters/" + charId + "/comics?limit=5&ts="+timeStamp+"&apikey=44f29e802dd281f1fe404e263da01a00&hash="+hashedValue;
        api = api.replaceAll("\\s", "%20");

        gridView.setAdapter(comicAdapter);

        comicAdapter = new ComicAdapter(this,comicsUrls);
        //Log.e("api", "Response: " + getIntent().getLongExtra("charId", 0));



        showData();
        getCharDetails();
       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                //android.R.layout.simple_list_item_1,comicsUrls);

    }

    public void showData() {
        nameTextView = findViewById(R.id.nameText);
        descTextView = findViewById(R.id.descriptionText);
        charImageView = findViewById(R.id.imgDetailsView);

        String charName = getIntent().getStringExtra("name");
        String descText = getIntent().getStringExtra("desc");
        String url = getIntent().getStringExtra("image");

        //Log.e("api", "Response: " + descText);
        nameTextView.setText(charName);

        if (descText.isEmpty()) {
            descTextView.setText("Description not available");
        } else {
            descTextView.setText(descText);
        }

        if (url != null) {

            Glide.with(this)
                    .load(url)
                    .into(charImageView);
        } else {
            charImageView.setImageDrawable(null);
        }
    }

    private void getCharDetails() {

        progress.setMessage("Loading");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, api, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object1 = response.getJSONObject("data");
                            JSONArray array1 = object1.getJSONArray("results");
                             // Log.e("api", "Response: " + array1.toString());
                            for (int i = 0; i < array1.length(); i++) {
                                JSONObject singleObject = array1.getJSONObject(i);

                                Comics comics = new Comics(
                                        singleObject.getLong("id"),
                                        singleObject.getLong("digitalId"),
                                        singleObject.getString("title"),
                                        singleObject.getString("description"),
                                        singleObject.getJSONObject("thumbnail").getString("path"),
                                        singleObject.getJSONObject("thumbnail").getString("extension")
                                );
                                //Log.e("api", "Response: " + comics.getThumbnail().getPath());

                                comicsUrls.add(comics);
                            }

                           // comicAdapter = new ComicAdapter(CharacterDetailsActivity.this ,comicsUrls);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gridView.setAdapter(new ComicAdapter(CharacterDetailsActivity.this, comicsUrls));
                                    comicAdapter.notifyDataSetChanged();
                                    progress.dismiss();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api","onerrorresponse"+ error.getLocalizedMessage());
            }
        });

        queue.add(stringRequest);

    }
}