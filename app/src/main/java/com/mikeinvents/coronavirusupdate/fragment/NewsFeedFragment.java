package com.mikeinvents.coronavirusupdate.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
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
import com.bumptech.glide.Glide;
import com.mikeinvents.coronavirusupdate.R;
import com.mikeinvents.coronavirusupdate.adapter.NewsFeedAdapter;
import com.mikeinvents.coronavirusupdate.model.NewsFeed;
import com.mikeinvents.coronavirusupdate.ui.RecyclerTouchListener;
import com.mikeinvents.coronavirusupdate.ui.WebActivity;
import com.mikeinvents.coronavirusupdate.ui.onItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class NewsFeedFragment extends Fragment {
    private static final String TAG = "NewsFeedFragment";

    private static final String API_KEY = "545460f9b68e461cbe050a704f280bcb";
    private static final String BBC_NEWS_SOURCE = "bbc-news";
    private static final String BBC_SPORT_SOURCE = "bbc-sport";
    private static final String CNN_SOURCE = "cnn";
    private static final String NIGERIA_CODE_SOURCE = "ng";
    private static final String MEDICAL_NEWS_SOURCE = "medical-news";
    private static final String TECH_CRUNCH_SOURCE = "techCrunch";
    private static final String FOUR_FOUR_TWO_SOURCE = "four-four-two";
    private static final String ENTERTAINMENT_WEEKLY = "entertainment-weekly";
    private static final String ESPN_SOURCE = "espn";
    private static final String NEWS_SCIENTIST_SOURCE = "new-scientist";

    public static final String URL_TO_WEB = "url";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RequestQueue mRequestQueue;
    private NewsFeedAdapter newsFeedAdapter;
    private ArrayList<NewsFeed> newsFeedArrayList;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        recyclerView = rootView.findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = rootView.findViewById(R.id.news_progress_bar);
        mRequestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        newsFeedArrayList = new ArrayList<>();

        getNewsFeedData();
        recyclerViewClick();

        return rootView;
    }

    /**
     * returns the news gotten from the JSON string
     *
     * @link newsapi.org
     */
    private void getNewsFeedData() {
        getVirusTopHeadline();
    }

    private void recyclerViewClick() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), new onItemClickListener() {
            @Override
            public void onClick(View view, int index) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra(URL_TO_WEB, newsFeedArrayList.get(index).getWebsiteUrl());
                startActivity(intent);
                //Log.i(TAG, "recyclerViewClick() : url = "+newsFeedArrayList.get(index).getWebsiteUrl());
            }
        }));

    }


    private String buildUrl() {
        String url;

        //build url or link to database
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("newsapi.org")
                .appendPath("v2")
                .appendPath("top-headlines")
                .appendQueryParameter("q", "coronavirus")
                .appendQueryParameter("language", "en")
                .appendQueryParameter("apiKey", API_KEY);

        url = builder.build().toString();

        return url;
    }

    private void processJSONRequest(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String imageUrl, newsTitle, newsContent, newsSourceName, newsUrl;
                        try {
                            //getting results from articles array
                            JSONArray jsonArray = response.getJSONArray("articles");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                //properties to be used
                                imageUrl = jsonObject.getString("urlToImage");
                                newsTitle = jsonObject.getString("title");
                                newsContent = jsonObject.getString("content");
                                // newsSourceName = jsonObject.getString("name");
                                newsUrl = jsonObject.getString("url");

                                JSONObject object = jsonObject.getJSONObject("source");
                                newsSourceName = object.getString("name");

                                if (newsContent.equalsIgnoreCase("null")) {
                                    newsContent = "...";
                                }

                                CharSequence formatTitle = Html.fromHtml(newsTitle);
                                CharSequence formatContent = Html.fromHtml(newsContent);
                                CharSequence formatSourceName = Html.fromHtml(newsSourceName);

                                newsTitle = formatTitle.toString();
                                newsContent = formatContent.toString();
                                newsSourceName = formatSourceName.toString();

                                newsFeedArrayList.add(new NewsFeed(imageUrl, newsTitle, newsContent, newsSourceName, newsUrl));

                                newsFeedAdapter = new NewsFeedAdapter(getContext(), shuffleNews(newsFeedArrayList));
                                recyclerView.setAdapter(newsFeedAdapter);
                               // Log.i(TAG, " Adapter ser");
                                progressBar.setVisibility(View.GONE);
                               // Log.i(TAG, "progress bar gone");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseForError(error);
                newsFeedArrayList.add(
                        new NewsFeed(
                                "https://cdn.pixabay.com/photo/2014/11/23/10/49/accidental-slip-542551_960_720.jpg",
                                "Error",
                                "Something happened, please request a review.",
                                "Error", "https://www.google.com"));
                newsFeedAdapter = new NewsFeedAdapter(getContext(), newsFeedArrayList);
                recyclerView.setAdapter(newsFeedAdapter);
                progressBar.setVisibility(View.GONE);
            }


        });
        mRequestQueue.add(request);
    }

    /**
     * Used to check for error while parsing data.
     */
    private void responseForError(VolleyError error) {

        if (error instanceof NoConnectionError) {
            //thrown if there is no network connection
            Toast.makeText(getContext(), getString(R.string.error_network), Toast.LENGTH_LONG).show();
        } else {
            //thrown if there connection timed out due to slow parsing of data or slow network connection
            Toast.makeText(getContext(), getString(R.string.error_time_out), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Used to shuffle the multiple options
     */
    private ArrayList<NewsFeed> shuffleNews(ArrayList<NewsFeed> newsFeeds) {
        Collections.shuffle(newsFeeds);
        // Log.i(TAG, "shuffleNews: Shuffled arrayList");
        return newsFeeds;


    }

    private void getVirusTopHeadline() {
        processJSONRequest(buildUrl());
        Log.i(TAG, "getVirusTopHeadlines: gotten");
    }

    private void getBBCNewsData() {
        // String url = buildUrl(BBC_NEWS_SOURCE);
//        processJSONRequest(url);
        // Log.i(TAG, "getBBCNewsData: gotten");
    }

    private void getBBCSportData() {
        // String url = buildUrl(BBC_SPORT_SOURCE);
        //  processJSONRequest(url);
        //Log.i(TAG, "getBBCSportData: gotten");
    }

    private void getCnnData() {
//        String url = buildUrl(CNN_SOURCE);
//        processJSONRequest(url);
        //Log.i(TAG, "getCnnData: gotten");
    }

    private void getNigeriaNewsData() {
//        Uri.Builder builder = new Uri.Builder();
//        builder.scheme("https")
//                .authority("newsapi.org")
//                .appendPath("v2")
//                .appendPath("top-headlines")
//                .appendQueryParameter("country", NIGERIA_CODE_SOURCE)
//                .appendQueryParameter("apiKey", API_KEY);
//
//        String url = builder.build().toString();
//        processJSONRequest(url);


        //Log.i(TAG, "getNigeriaNewsData: gotten");
    }

    private void getMedicalNewsData() {
//        String url = buildUrl(MEDICAL_NEWS_SOURCE);
//        processJSONRequest(url);
        //Log.i(TAG, "getMedicalNewsData: gotten");
    }

    private void getTechCrunchData() {
//        String url = buildUrl(TECH_CRUNCH_SOURCE);
//        processJSONRequest(url);
        //Log.i(TAG, "getTechCrunchData: gotten");
    }

    private void getFourFourTwoData() {
//        String url = buildUrl(FOUR_FOUR_TWO_SOURCE);
//        processJSONRequest(url);
        // Log.i(TAG, "getFourFourTwoData: gotten");
    }

    private void getEntertainmentWeeklyData() {
//        String url = buildUrl(ENTERTAINMENT_WEEKLY);
//        processJSONRequest(url);
        //Log.i(TAG, "getEntertainmentWeeklyData: gotten");

    }

    private void getEspnData() {
//        String url = buildUrl(ESPN_SOURCE);
//        processJSONRequest(url);
        // Log.i(TAG, "getEspnData: gotten");

    }

    private void getNewScientistData() {
//        String url = buildUrl(NEWS_SCIENTIST_SOURCE);
//        processJSONRequest(url);
        //Log.i(TAG, "getNewScientistData: gotten");

    }

    private void clearCache(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Glide.get(Objects.requireNonNull(getContext())).clearDiskCache();
                }catch (NullPointerException e){
                    System.out.println(e);
                }

            }
        }).start();

        Glide.get(Objects.requireNonNull(getContext())).clearMemory();
    }
    
    class DataRunnable implements Runnable{

        @Override
        public void run() {
            getNewsFeedData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearCache();
        //Log.i(TAG, "onDestroyView: Cleared Cache Memory");
    }

}
