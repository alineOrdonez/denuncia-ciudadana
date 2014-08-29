package com.upiicsa.denuncia.controller;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.CatDenuncia;
import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.util.Constant;

public class MapActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	private Singleton singleton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		if (savedInstanceState == null) {
			singleton = Singleton.getInstance();
			if (getIntent().getExtras() != null) {
				String lista = getIntent().getStringExtra(Constant.EXTRA_LIST);
				try {
					singleton.listaDeDenuncias(lista);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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

			// lets place some 10 random markers
			int size = singleton.getITEMS().size();
			for (int i = 0; i < size; i++) {
				Denuncia denuncia = singleton.getITEMS().get(i);
				double latitude = denuncia.getLatitud();
				double longitude = denuncia.getLongitud();

				// Adding a marker
				MarkerOptions marker = new MarkerOptions().position(
						new LatLng(latitude, longitude)).title(
						denuncia.getDescripcion());

				Log.e("Random", "> " + latitude + ", " + longitude);

				// TODO:
				int idCategoria = 2;// denuncia.getIdCategoria();
				CatDenuncia categoria = singleton.getDenuncias().get(
						idCategoria);
				String descripcion = categoria.getDescripcion().toLowerCase();

				if (descripcion.contains("accidente")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.accidente_vi));
				} else if (descripcion.contains("cable")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.cables_caidos));
				} else if (descripcion.contains("derrumbe")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.derrumbe));
				} else if (descripcion.contains("agua")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.fuga_agua));
				} else if (descripcion.contains("gas")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.fuga_gas));
				} else if (descripcion.contains("huelga")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.huelga));
				} else if (descripcion.contains("incendio")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.incendio));
				} else if (descripcion.contains("inundacion")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.inundacion));
				} else if (descripcion.contains("cadaver")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.rescate_cadaver));
				} else if (descripcion.contains("robo")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.robo));
				} else if (descripcion.contains("arbol")) {
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.seccionar_arbol));
				}
				googleMap.addMarker(marker);

				// Move the camera to last position with a zoom level
				if (i == size) {
					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(latitude, longitude)).zoom(15)
							.build();

					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
				}
			}

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
}
