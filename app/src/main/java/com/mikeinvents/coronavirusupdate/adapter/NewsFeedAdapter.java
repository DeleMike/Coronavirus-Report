package com.mikeinvents.coronavirusupdate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikeinvents.coronavirusupdate.R;
import com.mikeinvents.coronavirusupdate.model.NewsFeed;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private ArrayList<NewsFeed> mNewsFeedArrayList;
    private Context mContext;

    public NewsFeedAdapter(Context context, ArrayList<NewsFeed>newsFeedArrayList){
        mContext = context;
        mNewsFeedArrayList = newsFeedArrayList;
    }

    @NonNull
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.news_feed_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedAdapter.ViewHolder holder, int position) {
        NewsFeed newsFeed = mNewsFeedArrayList.get(position);
        holder.newsTitle.setText(newsFeed.getNewsTitle());
        holder.newsContent.setText(newsFeed.getNewsContent());
        holder.newsName.setText(newsFeed.getNewsName());

        //load image
        Glide.with(mContext)
                .load(newsFeed.getImageUrl())
                .placeholder(R.drawable.update_pic)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .override(600,150)
                .fallback(R.drawable.update_pic)
                .into(holder.newsImage);


        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mNewsFeedArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsImage;
        private TextView newsTitle;
        private TextView newsContent;
        private TextView newsName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.news_card_picture);
            newsTitle = itemView.findViewById(R.id.news_card_title);
            newsContent = itemView.findViewById(R.id.news_card_content);
            newsName = itemView.findViewById(R.id.news_card_source_name);
        }
    }
}
