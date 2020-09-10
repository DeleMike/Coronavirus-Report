package com.mikeinvents.coronavirusupdate.fragment;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mikeinvents.coronavirusupdate.R;
import com.mikeinvents.coronavirusupdate.adapter.CountriesAdapter;
import com.mikeinvents.coronavirusupdate.model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class CountriesFragment extends Fragment {

    private static final String COUNTRY = "country";
    private static final String CASES = "cases";
    private static final String ACTIVE_CASES = "active";
    private static final String TODAYS_CASES = "todayCases";
    private static final String DEATHS = "deaths";
    private static final String TODAYS_DEATH = "todayDeaths";
    private static final String RECOVERED = "recovered";

    private ArrayList<Country> countryArrayList;
    private CountriesAdapter countriesAdapter;
    private RecyclerView recyclerView;
    private RequestQueue mRequestQueue;
    private ProgressBar progressBar;

    public CountriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_countries, container, false);
        recyclerView = rootView.findViewById(R.id.countries_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = rootView.findViewById(R.id.countries_progress_bar);
        mRequestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        countryArrayList = new ArrayList<>();
        EditText searchBox = rootView.findViewById(R.id.countries_search_view);

        getCountryData();

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return rootView;
    }

    private void filter(String string) {
        ArrayList<Country> filterList = new ArrayList<>();
        for(Country country : countryArrayList){
            if(country.getCountryName().toLowerCase().contains(string.toLowerCase())){
                filterList.add(country);
            }

            countriesAdapter.filterList(filterList);
        }
    }

    /**Getting data from
     * @link coronavirus-19-api.herokuapp.com */
    private void getCountryData(){
        //build url or link to database
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("coronavirus-19-api.herokuapp.com")
                .appendPath("countries");

        final String url = builder.build().toString();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String country, cases, active, todayCase, deaths, todayDeaths, recovered;
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        country = object.getString(COUNTRY);

                        cases = object.getString(CASES);
                        active = object.getString(ACTIVE_CASES);
                        todayCase = object.getString(TODAYS_CASES);
                        deaths = object.getString(DEATHS);
                        todayDeaths = object.getString(TODAYS_DEATH);
                        recovered = object.getString(RECOVERED);

                        countryArrayList.add(new Country(country, formatString(cases),
                                formatString(active), formatString(todayCase), formatString(deaths),
                                formatString(todayDeaths), formatString(recovered)));

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                countriesAdapter = new CountriesAdapter(getContext(), countryArrayList);
                recyclerView.setAdapter(countriesAdapter);
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
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

    /**Used to format string to add commas appropriately */
    private String formatString(String string) {
        try{
            return NumberFormat.getNumberInstance(Locale.UK).format(Long.parseLong(string));
        }catch(NumberFormatException e){
            System.out.println("COUNTRIES_TAG = " +e);
            return "Not Available";
        }

    }

}
