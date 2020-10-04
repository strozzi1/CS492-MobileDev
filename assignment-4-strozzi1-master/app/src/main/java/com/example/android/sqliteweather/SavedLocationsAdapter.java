package com.example.android.sqliteweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sqliteweather.data.SavedLocationKey;

import java.util.List;


public class SavedLocationsAdapter extends RecyclerView.Adapter<SavedLocationsAdapter.SavedLocationsViewHolder> {
    private OnSearchResultClickedListener mResultClickListener;
    private List<SavedLocationKey> mSavedLocations;

    interface OnSearchResultClickedListener {
        void onSearchResultClicked(SavedLocationKey locationName);
    }

    public SavedLocationsAdapter(OnSearchResultClickedListener listener){
        mResultClickListener =listener;
    }

    public void updateSearchResults(List<SavedLocationKey> savedLocations){
        mSavedLocations = savedLocations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mSavedLocations != null){
            return mSavedLocations.size();
        }
        else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SavedLocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.saved_location_item, parent, false);
        return new SavedLocationsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SavedLocationsViewHolder holder, int position){
        holder.bind(mSavedLocations.get(position));
    }

    class SavedLocationsViewHolder extends RecyclerView.ViewHolder {
        private TextView mSearchResultTV;

        SavedLocationsViewHolder(View itemView){
            super(itemView);
            mSearchResultTV = itemView.findViewById(R.id.saved_location_listing);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mResultClickListener.onSearchResultClicked(
                            mSavedLocations.get(getAdapterPosition())
                    );
                }
            });
        }
        void bind(SavedLocationKey locationName){
            mSearchResultTV.setText(locationName.locationName);
        }
    }
}
