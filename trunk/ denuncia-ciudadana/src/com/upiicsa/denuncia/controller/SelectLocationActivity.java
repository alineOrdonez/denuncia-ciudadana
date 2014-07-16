package com.upiicsa.denuncia.controller;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.upiicsa.denuncia.R;

public class SelectLocationActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
 
        return super.onCreateOptionsMenu(menu);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_location);

		try {
			// Loading map
			initilizeMap();

			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// Showing / hiding your current location
			googleMap.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(false);

			// Enable / Disable my location button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);

			// Setting a click event handler for the map
			googleMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng latLng) {

					// Clears the previously touched position
					googleMap.clear();

					// Creating a marker
					MarkerOptions markerOptions = new MarkerOptions();

					// Setting the position for the marker
					markerOptions.position(latLng);

					// Animating to the touched position
					googleMap.animateCamera(CameraUpdateFactory
							.newLatLng(latLng));
					// Adding marker on the GoogleMap
					Marker marker = googleMap.addMarker(markerOptions);

					// Showing InfoWindow on the GoogleMap
					marker.showInfoWindow();
				}
			});

			googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {

				@Override
				public View getInfoWindow(Marker marker) {
					return null;
				}

				@Override
				public View getInfoContents(Marker marker) {
					// Getting view from the layout file info_window_layout
					View v = getLayoutInflater().inflate(
							R.layout.info_window_layout, null);
					// Getting the position from the marker
					LatLng latLng = marker.getPosition();

					double latitude = latLng.latitude;
					double longitude = latLng.longitude;
					try {
						Geocoder myLocation = new Geocoder(
								getApplicationContext(), Locale.getDefault());
						List<Address> myList = myLocation.getFromLocation(
								latitude, longitude, 1);

						if (myList.size() > 0) {
							Address address = myList.get(0);
							String line0 = address.getAddressLine(0);
							String line1 = address.getAddressLine(1);
							String line2 = address.getAddressLine(2);
							String line3 = address.getAddressLine(3);

							// Getting reference to the TextView to set latitude
							TextView tvLat = (TextView) v
									.findViewById(R.id.tv_lat);
							// Getting reference to the TextView to set
							// longitude
							TextView tvLng = (TextView) v
									.findViewById(R.id.tv_lng);
							// Setting the latitude
							tvLat.setText(line0 + ", " + line1);
							// Setting the longitude
							tvLng.setText(line2 + ", " + line3);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					// Returning the view containing InfoWindow contents
					return v;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
		moveMapToMyCurrentLocation();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private void moveMapToMyCurrentLocation() {

		LocationManager manager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();

		Location location = manager.getLastKnownLocation(manager
				.getBestProvider(criteria, false));

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(location.getLatitude(), location
						.getLongitude())).zoom(15).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

	}

}