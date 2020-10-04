package com.example.android.connectedweather;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.connectedweather.Utils.WeatherUtils;
import com.example.android.connectedweather.data.WeatherData;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastItemViewHolder> {

    //private ArrayList<String> mForecastData;
    //private ArrayList<String> mDetailedForecastData;
    private OnForecastItemClickListener mOnForecastItemClickListener;
    public ArrayList<WeatherData> myForecastData;

    public ForecastAdapter(OnForecastItemClickListener onForecastItemClickListener) {
        mOnForecastItemClickListener = onForecastItemClickListener;
    }




    public void updateForecastData(ArrayList<WeatherData> forecastData) {
        myForecastData = forecastData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (myForecastData != null) {
            return myForecastData.size() - 1;
            //return 40;
        } else {
            return 0;
        }
    }

    @Override
    public ForecastItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastItemViewHolder holder, int position) {
        holder.bind(myForecastData.get(position));
    }

    public interface OnForecastItemClickListener {
        //void onForecastItemClick(String detailedForecast);
        void onForecastItemClick(WeatherData weather);
    }

    class ForecastItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mForecastTextView;
        private ImageView mIconIV;

        public ForecastItemViewHolder(View itemView) {
            super(itemView);
            mForecastTextView = itemView.findViewById(R.id.tv_forecast_text);
            mIconIV = itemView.findViewById(R.id.iv_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //String detailedForecast = mDetailedForecastData.get(getAdapterPosition());
                    WeatherData weatherData = myForecastData.get(getAdapterPosition());
                    //mOnForecastItemClickListener.onForecastItemClick(detailedForecast);
                    mOnForecastItemClickListener.onForecastItemClick(weatherData);
                }
            });
        }



        public void bind(WeatherData weatherData) {
            mForecastTextView.setText(weatherData.dt_txt + " - " + weatherData.main.temp + " - " + weatherData.weather.get(0).main);
            //mForecastTextView.setText("Your mom");
            Picasso.get().load(WeatherUtils.buildIconURL( weatherData.weather.get(0).icon) ).into(mIconIV);
        }
    }
    /*public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }*/
}
