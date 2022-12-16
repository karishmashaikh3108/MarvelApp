package com.example.mymarvelapp.Activities;

import static com.example.mymarvelapp.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymarvelapp.Helper.GenerateMD5Hash;
import com.example.mymarvelapp.Models.MatchData;
import com.example.mymarvelapp.R;
import com.example.mymarvelapp.Adapter.TempAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<MatchData> tempList;
    RecyclerView rcvMain;
    String api;
    JSONObject characterDetailsObject;
    String description;
    SearchView searchView;
    TempAdapter tempAdapter;
    ProgressDialog progress;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        rcvMain = findViewById(R.id.rcvMain);
        searchView = findViewById(R.id.search_view);
        rcvMain.setLayoutManager(new LinearLayoutManager(this));
        tempList = new ArrayList<>();
        rcvMain.setAdapter(new TempAdapter(MainActivity.this, tempList));
        tempAdapter = new TempAdapter(this,tempList);
        progress = new ProgressDialog(this);





        String MARVEL_PRIVATE_API_KEY = "9729e975544ce801d758a9227bafb427d7b19fa9";
        String MARVEL_PUBLIC_API_KEY = "44f29e802dd281f1fe404e263da01a00";
        String timeStamp = new Timestamp(System.currentTimeMillis()) + "";
        timeStamp = timeStamp.replaceAll("\\s", "");

        String FinalAPIHashingString = timeStamp+MARVEL_PRIVATE_API_KEY+MARVEL_PUBLIC_API_KEY;

        String hashedValue = GenerateMD5Hash.digest(FinalAPIHashingString);
        api = "https://gateway.marvel.com/v1/public/characters?&ts="+timeStamp+"&apikey=44f29e802dd281f1fe404e263da01a00&hash="+hashedValue;
        api = api.replaceAll("\\s", "%20");

        getData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<MatchData> filteredList = new ArrayList<>();
        for(MatchData item : tempList){
            if(item.getName().toLowerCase().startsWith(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }


        tempAdapter.filterList(filteredList);
        rcvMain.setAdapter(tempAdapter);

    }

    private void getData() {

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
                                JSONObject singleObject= array1.getJSONObject(i);


                                MatchData singleModel = new MatchData(
                                        singleObject.getInt("id"),
                                        singleObject.getString("name"),
                                        singleObject.getString("description"),
                                        singleObject.getString("modified"),
                                        singleObject.getJSONObject("thumbnail").getString("path"),
                                        singleObject.getJSONObject("thumbnail").getString("extension"),
                                        singleObject.getString("resourceURI")
                                );
                                tempList.add(singleModel);
                            }
                            rcvMain.setAdapter(new TempAdapter(MainActivity.this, tempList));
                            progress.dismiss();

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