package com.sample.placedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private Context context;
    private ArrayList<Place> items;

    public PlaceAdapter(Context context, ArrayList<Place> items) {
        this.context = context;
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
        PlaceViewHolder viewHolder = new PlaceViewHolder(context, view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PlaceViewHolder viewHolder, int position) {
    	Place place = items.get(position);
        viewHolder.tvPlaceName.setText(place.getPlaceName());
        viewHolder.tvPlaceAddress.setText(place.getPlaceAddress());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
