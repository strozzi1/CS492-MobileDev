package com.example.android.basicweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.TodoViewHolder> {
    private ArrayList<String> mWeatherList;

    String[] weatherStrings = {
            "Mon February 1 - Sunny and warm - 75",
            "Tues February 2 - Sunny and warm - 75",
            "Wed February 3 - Sunny and warm - 75",
            "Thur February 4 - Sunny and warm - 75",
            "Fri February 5 - Sunny and warm - 75",
            "Sat February 6 - Sunny and warm - 75",
            "Sun February 7 - Sunny and warm - 75",
            "Mon February 8 - Sunny and warm - 75",
            "Tues February 9 - Sunny and warm - 75",
            "Wed February 10 - Sunny and warm - 75"
    };

    public WeatherAdapter() {
        mWeatherList = new ArrayList<>();
        for(String i : weatherStrings){
            mWeatherList.add(i);
        }
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.weather_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        String todo = mWeatherList.get(position);
        holder.bind(todo);
    }


    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView mWeatherTV;

        public TodoViewHolder(final View itemView) {
            super(itemView);
            mWeatherTV = itemView.findViewById(R.id.tv_weather_text);


        }


        void bind(String newTodoText) {
            mWeatherTV.setText(newTodoText);
        }
    }
}










/*package com.example.android.basicweather;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WeatherAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WeatherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view

        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View itemView = inflater.inflate(R.layout.tv_weather_text, parent, false);
        //return new MyViewHolder(itemView);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}*/
