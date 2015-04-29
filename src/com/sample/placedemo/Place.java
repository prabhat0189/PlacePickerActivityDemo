package com.sample.placedemo;

public class Place {

	private String mPlaceName;
	private String mPlaceAddress;

	private double mLat;
	private double mLong;
	public String getPlaceName() {
		return mPlaceName;
	}
	public void setPlaceName(String mPlaceName) {
		this.mPlaceName = mPlaceName;
	}
	public String getPlaceAddress() {
		return mPlaceAddress;
	}
	public void setPlaceAddress(String mPlaceAddress) {
		this.mPlaceAddress = mPlaceAddress;
	}
	public double getLat() {
		return mLat;
	}
	public void setLat(double mLat) {
		this.mLat = mLat;
	}
	public double getLong() {
		return mLong;
	}
	public void setLong(double mLong) {
		this.mLong = mLong;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Place)) return false;
		Place secondPlace = (Place)o;
		return this.mLat == secondPlace.getLat() && this.mLong == secondPlace.getLong();
	}

}
