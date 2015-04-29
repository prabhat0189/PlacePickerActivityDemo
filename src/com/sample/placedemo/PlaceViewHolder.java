package com.sample.placedemo;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Context context;
    public TextView tvPlaceName;
    public TextView tvPlaceAddress;

    public PlaceViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tvPlaceName = (TextView) itemView.findViewById(R.id.tvPlaceName);
        tvPlaceAddress = (TextView) itemView.findViewById(R.id.tvPlaceAddress);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, tvPlaceName.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
