package com.mikeinvents.coronavirusupdate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikeinvents.coronavirusupdate.R;
import com.mikeinvents.coronavirusupdate.model.Country;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
   // private static final String TAG = "CountriesAdapter";
    private Context mContext;
    private ArrayList<Country> mArrayList;

    public CountriesAdapter(Context context, ArrayList<Country> arrayList){
        mContext = context;
        mArrayList = arrayList;
    }


    /**Initialize view - country card here */
    @NonNull
    @Override
    public CountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.country_card, parent, false);
        return new ViewHolder(view);
    }

    /**Bind your data here */
    @Override
    public void onBindViewHolder(@NonNull CountriesAdapter.ViewHolder holder, int position) {
        Country country = mArrayList.get(position);
        holder.mCardName.setText(country.getCountryName());
        holder.mNumOfCases.append(" "+country.getNumOfCases());
        holder.mNumOfActiveCases.append(" "+country.getNumOfActiveCases());
        holder.mNumOfTodayCases.append(" "+country.getNumOfTodayCases());
        holder.mNumOfDeaths.append(" "+country.getNumOfDeaths());
        holder.mNumOfTodayDeaths.append(" "+country.getNumOfTodayDeaths());
        holder.mNumOfRecoveries.append(" "+country.getRecoveries());

        holder.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public void filterList(ArrayList<Country> filteredList) {
        mArrayList = filteredList;
        notifyDataSetChanged();
    }

    /**A class to hold all views on Country card */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCardName, mNumOfCases, mNumOfActiveCases, mNumOfTodayCases, mNumOfDeaths
                ,mNumOfTodayDeaths, mNumOfRecoveries;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardName = itemView.findViewById(R.id.countries_card_name);
            mNumOfCases = itemView.findViewById(R.id.countries_card_cases);
            mNumOfActiveCases = itemView.findViewById(R.id.countries_card_active_cases);
            mNumOfTodayCases = itemView.findViewById(R.id.countries_card_today_cases);
            mNumOfDeaths = itemView.findViewById(R.id.countries_card_deaths);
            mNumOfTodayDeaths = itemView.findViewById(R.id.countries_card_today_deaths);
            mNumOfRecoveries = itemView.findViewById(R.id.countries_card_recoveries);
        }
    }
}
