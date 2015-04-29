package com.sample.placedemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class PlacePickerActivity extends Activity {

	private static final int PLACE_PICKER_REQUEST = 1;

	private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

	private RecyclerView recyclerView;
	private ArrayList<Place> mPlaces;
	private PlaceAdapter mPlaceAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher_activity);

		Button pickerButton = (Button) findViewById(R.id.pickerButton);
		pickerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
					intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
					Intent intent = intentBuilder.build(getApplicationContext());
					startActivityForResult(intent, PLACE_PICKER_REQUEST);

				} catch (GooglePlayServicesRepairableException e) {
					e.printStackTrace();
				} catch (GooglePlayServicesNotAvailableException e) {
					e.printStackTrace();
				}
			}
		});

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mPlaces = getSavedPlace();//new ArrayList<Place>();
		mPlaceAdapter = new PlaceAdapter(this, mPlaces);
		recyclerView.setAdapter(mPlaceAdapter);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {

			final com.google.android.gms.location.places.Place selectedPlace = PlacePicker.getPlace(data, this);
			final CharSequence name = selectedPlace.getName();
			final CharSequence address = selectedPlace.getAddress();
			LatLng latLong = selectedPlace.getLatLng();

			String attributions = PlacePicker.getAttributions(data);
			if (attributions == null) {
				attributions = "";
			}

			Place place = new Place();
			place.setPlaceName(name.toString());
			place.setPlaceAddress(address.toString());
			place.setLat(latLong.latitude);
			place.setLong(latLong.longitude);

			if (mPlaces.contains(place)) {
				mPlaces.remove(place);
			}
			mPlaces.add(place);
			mPlaceAdapter.notifyDataSetChanged();

			/*
			 * mName.setText(name); mAddress.setText(address);
			 * mAttributions.setText(Html.fromHtml(attributions));
			 */

		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	@Override
	protected void onDestroy() {
		savePlace();
		super.onDestroy();
	}

	private void savePlace() {
		int totalPlaces = mPlaces.size();
		int maxPlacesToSave = totalPlaces >= 3 ? 3 : totalPlaces;

		SharedPreferences preferences = getSharedPreferences("PlacesPreferece", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		for(int i = (totalPlaces - maxPlacesToSave), pos = 1;i<totalPlaces;++i, ++pos ) {
			Place place = mPlaces.get(i);
			editor.putString("Name"+pos, place.getPlaceName());
			editor.putString("Address"+pos, place.getPlaceAddress());
			editor.putString("Lat"+pos, String.valueOf(place.getLat()));
			editor.putString("Long"+pos, String.valueOf(place.getLong()));
		}
		editor.commit();
	}

	private ArrayList<Place> getSavedPlace() {
		ArrayList<Place> savedPlaces = new ArrayList<Place>();

		SharedPreferences preferences = getSharedPreferences("PlacesPreferece", Context.MODE_PRIVATE);
		if (preferences.contains("Name1")) {
			Place place = new Place();
			place.setPlaceName(preferences.getString("Name1", ""));
			place.setPlaceAddress(preferences.getString("Address1", ""));
			place.setLat(Double.valueOf(preferences.getString("Lat1", "")));
			place.setLong(Double.valueOf(preferences.getString("Long1", "")));
			savedPlaces.add(place);
		}
		if (preferences.contains("Name2")) {
			Place place = new Place();
			place.setPlaceName(preferences.getString("Name2", ""));
			place.setPlaceAddress(preferences.getString("Address2", ""));
			place.setLat(Double.valueOf(preferences.getString("Lat2", "")));
			place.setLong(Double.valueOf(preferences.getString("Long2", "")));
			savedPlaces.add(place);
		}
		if (preferences.contains("Name3")) {
			Place place = new Place();
			place.setPlaceName(preferences.getString("Name3", ""));
			place.setPlaceAddress(preferences.getString("Address3", ""));
			place.setLat(Double.valueOf(preferences.getString("Lat3", "")));
			place.setLong(Double.valueOf(preferences.getString("Long3", "")));
			savedPlaces.add(place);
		}

		return savedPlaces;
	}
}