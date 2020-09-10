package com.mikeinvents.coronavirusupdate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikeinvents.coronavirusupdate.R;
import com.mikeinvents.coronavirusupdate.model.Global;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GlobalAdapter extends RecyclerView.Adapter<GlobalAdapter.ViewHolder> {
    //private static final String TAG = "GlobalAdapter";
    private Context mContext;
    private ArrayList<Global> mGlobalList;

    public GlobalAdapter(Context context, ArrayList<Global> globalList){
        mContext = context;
        mGlobalList = globalList;
    }

    @NonNull
    @Override
    public GlobalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.global_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalAdapter.ViewHolder holder, int position) {
        Global globalModel = mGlobalList.get(position);
        holder.mGlobalHeader.setText(globalModel.getHeader());
        holder.mGlobalCount.setText(globalModel.getCount());
        //Log.i(TAG, "onBindViewHolder: CardView Set ");
        
        
    }

    @Override
    public int getItemCount() {
        //Log.i(TAG, "getItemCount: ArrayList count = "+mGlobalList.size());
        return mGlobalList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mGlobalHeader;
        private TextView mGlobalCount;
//        private TextView mIncreaseCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mGlobalHeader = itemView.findViewById(R.id.global_header);
            mGlobalCount = itemView.findViewById(R.id.global_count);
//            mIncreaseCount = itemView.findViewById(R.id.increase_case_count);

            //Log.i(TAG, "ViewHolder: All views in adapter initialized");
        }
    }
}
