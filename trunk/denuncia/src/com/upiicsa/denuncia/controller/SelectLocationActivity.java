package com.upiicsa.denuncia.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
import com.upiicsa.denuncia.util.Constant;
import com.upiicsa.denuncia.util.GeocodeJSONParser;

public class SelectLocationActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	private static final String TAG = "SelectLocationActivity";
	private String selectedAddress;
	private double latitude = 0;
	private double longitude = 0;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_aceptar) {
			Intent i = new Intent();
			String lat = String.valueOf(latitude);
			String lng = String.valueOf(longitude);
			String[] address = new String[] { selectedAddress, lat, lng };
			i.putExtra("ADDRESS", address);
			setResult(2, i);
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
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

					View v = getLayoutInflater().inflate(
							R.layout.info_window_layout,
							new LinearLayout(SelectLocationActivity.this),
							false);
					LatLng latLng = marker.getPosition();

					latitude = latLng.latitude;
					longitude = latLng.longitude;
					try {
						Geocoder myLocation = new Geocoder(
								getApplicationContext(), Locale.getDefault());
						List<Address> myList = myLocation.getFromLocation(
								latitude, longitude, 1);

						if (myList.size() > 0) {
							Address address = myList.get(0);
							String line0 = address.getThoroughfare();
							String line1 = address.getAddressLine(1);
							String line2 = address.getAddressLine(2);
							selectedAddress = line0 + ", " + line1 + ", "
									+ line2;

							TextView tvLat = (TextView) v
									.findViewById(R.id.tv_lat);
							TextView tvLng = (TextView) v
									.findViewById(R.id.tv_lng);
							tvLat.setText(line0 + ", " + line1);
							tvLng.setText(line2);
						}
						String url = Constant.MAP_URL;
						// Instantiating DownloadTask to get places from Google
						// Geocoding service
						// in a non-ui thread
						DownloadTask downloadTask = new DownloadTask();
						// Start downloading the geocoding places
						downloadTask.execute(url);
					} catch (Exception ex) {
						Log.e(TAG, "Impossible to connect to Geocoder", ex);
					}
					return v;
				}
			});
		} catch (Exception e) {
			Log.d(TAG, e.toString());
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
		double longitude = 0;
		double latitude = 0;

		if (location == null) {
			latitude = 19.3952204;
			longitude = -99.0907235;
		} else {
			longitude = location.getLongitude();
			latitude = location.getLatitude();
		}
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(15).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			data = sb.toString();
			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;
	}

	private class DownloadTask extends AsyncTask<String, Integer, String> {
		String data = null;

		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			ParserTask parserTask = new ParserTask();
			parserTask.execute(result);
		}
	}

	class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {
		JSONObject jObject;

		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {
			List<HashMap<String, String>> places = null;
			GeocodeJSONParser parser = new GeocodeJSONParser();
			try {
				jObject = new JSONObject(jsonData[0]);
				places = parser.parse(jObject);
			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<HashMap<String, String>> list) {
			final HashMap<String, String> county = list.get(3);
			String[] cStr = county.get("formatted_address").split(", ");
			selectedAddress = selectedAddress + ", " + cStr[0];
			Log.d(TAG, selectedAddress);
		}
	}
}