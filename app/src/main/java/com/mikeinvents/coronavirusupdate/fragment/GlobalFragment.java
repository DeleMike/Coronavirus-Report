package com.mikeinvents.coronavirusupdate.fragment;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mikeinvents.coronavirusupdate.R;
import com.mikeinvents.coronavirusupdate.adapter.GlobalAdapter;
import com.mikeinvents.coronavirusupdate.model.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class GlobalFragment extends Fragment {
   // private static final String TAG = "GlobalFragment";

    private RecyclerView recyclerView;
    private GlobalAdapter adapter;
    private ArrayList<Global> globalArrayList;
    private RequestQueue mRequestQueue;
    private ProgressBar progressBar;

    public GlobalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_global, container, false);
        recyclerView = rootView.findViewById(R.id.global_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = rootView.findViewById(R.id.global_progress_bar);

        mRequestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        globalArrayList = new ArrayList<>();
        getGlobalRecord();
        return rootView;
    }

    private void getGlobalRecord() {
        //build url or link to database
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("coronavirus-19-api.herokuapp.com")
                .appendPath("all");

        final String url = builder.build().toString();

        //make request to online API
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String cases = response.get("cases").toString();
                            //Log.i(TAG, "onResponse: Cases = " + cases);
                            globalArrayList.add(new Global("COVID-19 Cases",formatString(cases)));

                            String deaths = response.get("deaths").toString();
                            //Log.i(TAG, "onResponse: Deaths = "+deaths);
                            globalArrayList.add(new Global("COVID-19 Deaths",formatString(deaths)));

                            String recovered = response.get("recovered").toString();
                            //Log.i(TAG, "onResponse: Recovered = " +recovered);
                            globalArrayList.add(new Global("COVID-19 Recoveries",formatString(recovered)));

                            adapter = new GlobalAdapter(getContext(), globalArrayList);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.GONE);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            //listens for error
            @Override
            public void onErrorResponse(VolleyError error) {
                responseForError(error);
            }
        });

        mRequestQueue.add(request);
    }

    /**Used to check for error while parsing data.*/
    private void responseForError(VolleyError error){

        if(error instanceof NoConnectionError){
            //thrown if there is no network connection
            Toast.makeText(getContext(), getString(R.string.error_network), Toast.LENGTH_LONG).show();
        }else{
            //thrown if there connection timed out due to slow parsing of data or slow network connection
            Toast.makeText(getContext(), getString(R.string.error_time_out), Toast.LENGTH_LONG).show();
        }
    }

    private String formatString(String string) {
        try{
            return NumberFormat.getNumberInstance(Locale.UK).format(Long.parseLong(string));
        }catch(NumberFormatException e){
            System.out.println("COUNTRIES_TAG = " +e);
            return "Not Available";
        }
    }

}
