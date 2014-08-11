package com.upiicsa.denuncia.controller;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.CatDenuncia;
import com.upiicsa.denuncia.service.Service;
import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.Util;

public class NewComplaintFragment extends Fragment {

	private Spinner spinner;
	private EditText address;
	private Service service;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_new_complaint,
				container, false);
		addItemsOnSpinner(view);
		addListenerOnFocus(view);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.sendNewComplaint) {
			addListenerOnButton();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	//TODO:
	public void addItemsOnSpinner(View view) {
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		/*ArrayAdapter<CatDenuncia> dataAdapter = new ArrayAdapter<CatDenuncia>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				Util.categories());

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);*/
	}

	private void addListenerOnButton() {
		// TODO:Envia peticion para obtener registros
		final ProgressDialog ringProgressDialog = ProgressDialog.show(
				getActivity(), "Por favor espere ...",
				"La denuncia se está registrando ...", true);
		ringProgressDialog.setCancelable(false);
		ringProgressDialog.setIndeterminate(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Here you should write your time consuming task...
					// Let the progress ring for 10 seconds...
					Thread.sleep(10000);
				} catch (Exception e) {

				}
				ringProgressDialog.dismiss();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								getActivity());
						builder.setTitle("Operación exitosa");
						builder.setMessage("La denuncia se ha registrado exitosamente.");
						builder.setPositiveButton("Continuar",
								new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
										Intent i = new Intent(getActivity(),
												MainMenuActivity.class);
										startActivity(i);

									}
								});
						builder.create();
						builder.show();

					}
				});
			}
		}).start();
	}

	public void addListenerOnFocus(View view) {
		address = (EditText) view.findViewById(R.id.addressEditText);
		address.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				showMap();
			}
		});
	}

	public void showMap() {
		checkConnectivity();
	}

	private void checkConnectivity() {
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isWiFiEnabled = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isAvailable();

		if (!isGPSEnabled) {
			showGPSAlert();
		} else {
			if (isWiFiEnabled) {
				Intent i = new Intent(getActivity(),
						SelectLocationActivity.class);
				startActivityForResult(i, Constants.RETURN_FROM_MAP);
			} else {
				showWiFiAlert();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 3:
			// fetch the message String
			String message = data.getStringExtra("ADDRESS");
			// Set the message string in textView
			address.setText(message);
			break;

		default:
			break;
		}
	}

	private void showGPSAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

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
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

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

	private void sendComplaintRequest() {
		if (Util.isConnected(getActivity())) {
			LocationManager lm = (LocationManager) getActivity()
					.getSystemService(Context.LOCATION_SERVICE);
			Location location = lm
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			boolean isGPSEnabled = lm
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (isGPSEnabled) {
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				Map<String, String> object = new HashMap<String, String>();
				String lat = String.valueOf(latitude);
				String lng = String.valueOf(longitude);
				try {
					// TODO: Verificar parametros
					object.put("i", "03");
					object.put("ic", "01");
					object.put("dc", "02");
					object.put("em", "02");
					object.put("im", "02");
					object.put("dd", "02");
					object.put("la", lat);
					object.put("lo", lng);
					// service.setRequest(object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				showGPSAlert();
			}

		} else {
			Toast.makeText(getActivity().getBaseContext(),
					Constants.CONNECTION_ERROR, Toast.LENGTH_LONG).show();
		}
	}

}
