package com.upiicsa.denuncia.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.upiicsa.denuncia.R;

public class NewComplaintActivity extends ActionBarActivity {
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_complaint);
		context = this;

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.complaintDetailContainer,
							new NewComplaintFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.new_complaint_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	public void showMap(View view) {
		checkConnectivity();
	}

	private void checkConnectivity() {
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		boolean isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isWiFiEnabled = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isAvailable();

		if (!isGPSEnabled) {
			showGPSAlert();
		} else {
			if (isWiFiEnabled) {
				Intent i = new Intent(context, SelectLocationActivity.class);
				startActivity(i);
			} else {
				showWiFiAlert();
			}
		}
	}

	private void showGPSAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle("Configuración del GPS");
		alertDialog.setMessage("Para obtener su localización es necesario"
				+ "tener habilitado el GPS.\n¿Desea activarlo?");
		alertDialog.setPositiveButton("Activar GPS",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(i);
					}
				});

		alertDialog.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();

					}
				});

		alertDialog.show();
	}

	private void showWiFiAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle("Configuración del Wi-Fi");
		alertDialog.setMessage("La opción de localización funciona"
				+ "mejor cuando el Wi-Fi está habilitado.\n¿Desea activarlo?");
		alertDialog.setPositiveButton("Activar Wi-Fi",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
						startActivity(i);
					}
				});

		alertDialog.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();

					}
				});

		alertDialog.show();
	}
}