package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.CatDenuncia;
import com.upiicsa.denuncia.common.CatIntTiempo;
import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.service.OnResponseListener;
import com.upiicsa.denuncia.service.Service;
import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.CustomAlertDialog;
import com.upiicsa.denuncia.util.NetworkUtil;
import com.upiicsa.denuncia.util.Util;

public class MainMenuActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, OnResponseListener {
	private static final String LOG_TAG = "MainMenuActivity";
	private IntentFilter mNetworkStateChangedFilter;
	private BroadcastReceiver mNetworkStateIntentReceiver;
	private LocationClient mLocationClient;
	private Spinner category, consultRange;
	private CatDenuncia catDen;
	private CatIntTiempo catIntT;
	private List<String> consultList, rangeList;
	private int operacion;
	private boolean isGPSEnabled;
	Context context;
	private Service service;
	Singleton singleton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		context = this;
		singleton = Singleton.getInstance();
		mNetworkStateChangedFilter = new IntentFilter();
		mNetworkStateChangedFilter
				.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		networkState();
		mLocationClient = new LocationClient(this, this, this);
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	protected void onResume() {
		Log.d(LOG_TAG, "onResume");
		super.onResume();
		registerReceiver(mNetworkStateIntentReceiver,
				mNetworkStateChangedFilter);
	}

	@Override
	protected void onPause() {
		Log.d(LOG_TAG, "onPause");
		super.onPause();
		unregisterReceiver(mNetworkStateIntentReceiver);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	public void addCategoriesOnSpinner(View view, int id) {
		category = (Spinner) view.findViewById(id);
		consultList = new ArrayList<String>();
		for (CatDenuncia cat : singleton.getDenuncias()) {
			consultList.add(cat.getDescripcion());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, consultList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(dataAdapter);
		category.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String strItem = category.getItemAtPosition(position)
						.toString();
				System.out.println(strItem);

				for (CatDenuncia catDenuncia : singleton.getDenuncias()) {
					if (catDenuncia.getDescripcion().equals(strItem)) {
						catDen = new CatDenuncia();
						catDen = catDenuncia;
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	public void addRangeOnSpinner(View view) {
		consultRange = (Spinner) view.findViewById(R.id.consultRange);
		rangeList = new ArrayList<String>();
		for (CatIntTiempo cat : singleton.getIntervalos()) {
			rangeList.add(cat.getDescripcion());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, rangeList);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		consultRange.setAdapter(dataAdapter);
		consultRange.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String strItem = consultRange.getItemAtPosition(position)
						.toString();
				System.out.println(strItem);

				for (CatIntTiempo catInt : singleton.getIntervalos()) {
					if (catInt.getDescripcion().equals(strItem)) {
						catIntT = new CatIntTiempo();
						catIntT = catInt;
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public void showPrompt(View view) {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isGPSEnabled) {
			LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View promptsView = li.inflate(R.layout.select_complaint_prompt,
					new LinearLayout(context), false);

			String btnTitle = "Buscar";
			CustomAlertDialog.promptAlert(context, null, promptsView, btnTitle,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,
								int i) {
							findComplaints();
						}
					});
			addCategoriesOnSpinner(promptsView, R.id.selectCategory);
		} else {
			showGPSAlert();
		}

	}

	public void consultComplaint(View view) {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isGPSEnabled) {
			LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View promptsView = li.inflate(R.layout.consult_complaint_prompt,
					new LinearLayout(context), false);

			String btnTitle = "Buscar";
			CustomAlertDialog.promptAlert(context, null, promptsView, btnTitle,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,
								int i) {
							consultAccordingToCategoryAndTime();
						}
					});
			addCategoriesOnSpinner(promptsView, R.id.consultCategory);
			addRangeOnSpinner(promptsView);
		} else {
			showGPSAlert();
		}
	}

	public void loadConfiguration() {
		try {
			operacion = 1;
			service = new Service(this, context);
			service.configService("Cargando configuracion");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findComplaints() {
		Location mCurrentLocation;
		mCurrentLocation = mLocationClient.getLastLocation();
		operacion = 2;
		double longitude = mCurrentLocation.getLongitude();
		double latitude = mCurrentLocation.getLatitude();
		int idCategoria = catDen.getIdCatDenuncia();
		String descripcion = catDen.getDescripcion();
		try {
			Denuncia denuncia = new Denuncia(idCategoria, descripcion,
					latitude, longitude);
			service.findComplaintsService(denuncia, "Buscando denuncias...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void consultAccordingToCategoryAndTime() {
		Location mCurrentLocation;
		mCurrentLocation = mLocationClient.getLastLocation();
		operacion = 5;
		double longitude = mCurrentLocation.getLongitude();
		double latitude = mCurrentLocation.getLatitude();
		int idCategoria = catDen.getIdCatDenuncia();
		int idIntervalo = catIntT.getIdCatIntTiempo();
		String descripcion = catDen.getDescripcion();
		try {
			Denuncia denuncia = new Denuncia(idCategoria, idIntervalo,
					descripcion, latitude, longitude);
			service.consultComplaintService(denuncia, "Buscando registros...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showGPSAlert() {
		String title = "Configuracion del GPS";
		String message = "La aplicacion requere su localicazion."
				+ "¿Desea activar el GPS?";
		String btnTitle = "Activar GPS";
		CustomAlertDialog.decisionAlert(context, title, message, btnTitle,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
					}
				});
	}

	private void addExtraToIntent(Class<?> clase, String list) {
		Intent intent = new Intent(context, clase);
		intent.putExtra("COMPLAINT_LIST", list);
		startActivity(intent);
	}

	private void denuncias(List<String> stringList) {
		List<CatDenuncia> catalogo = new ArrayList<CatDenuncia>();
		for (int i = 0; i < stringList.size(); i++) {
			List<String> list = Util.detailList(stringList.get(i));
			int id = Integer.valueOf(list.get(0));
			CatDenuncia denuncias = new CatDenuncia();
			denuncias.setIdCatDenuncia(id);
			denuncias.setDescripcion(list.get(1));
			catalogo.add(denuncias);
		}
		singleton.setDenuncias(catalogo);
	}

	private void intervalos(List<String> stringList) {
		List<CatIntTiempo> catalogo = new ArrayList<CatIntTiempo>();
		for (int i = 0; i < stringList.size(); i++) {
			List<String> list = Util.detailList(stringList.get(i));
			int id = Integer.valueOf(list.get(0));
			CatIntTiempo intervalos = new CatIntTiempo();
			intervalos.setIdCatIntTiempo(id);
			intervalos.setDescripcion(list.get(1));
			catalogo.add(intervalos);
		}
		singleton.setIntervalos(catalogo);
	}

	public void networkState() {
		mNetworkStateIntentReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(
						ConnectivityManager.CONNECTIVITY_ACTION)) {
					int connection = NetworkUtil.getConnectivityStatus(context);
					String status = null;

					if (connection == Constants.TYPE_NO_CONNECCTION) {
						status = "El dispositivo no tiene accesso a Internet.";
						Toast.makeText(context, status, Toast.LENGTH_LONG)
								.show();
					} else {
						if (singleton.getDenuncias().isEmpty()
								&& singleton.getImage().isEmpty()) {
							loadConfiguration();
						}
					}
				}
			}
		};
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Log.d(LOG_TAG, "onConnectionFailed");
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.d(LOG_TAG, "onConnected");
	}

	@Override
	public void onDisconnected() {
		Log.d(LOG_TAG, "onDisconnected");

	}

	@Override
	public void onSuccess(String result) throws JSONException {
		System.out.println("Result:" + result);
		JSONObject json;
		String ld;
		switch (operacion) {
		case 1:
			result = Util.configResult();
			json = new JSONObject(result);
			List<String> descripcion = new ArrayList<String>();
			List<String> intervalo = new ArrayList<String>();
			ld = json.getString("ld");
			descripcion = Util.stringToList(ld);
			denuncias(descripcion);
			String lt = json.getString("lt");
			intervalo = Util.stringToList(lt);
			intervalos(intervalo);
			break;
		case 2:
			result = Util.complaintResult();
			json = new JSONObject(result);
			ld = json.getString("ld");
			addExtraToIntent(ComplaintListActivity.class, ld);
			break;
		case 5:
			result = Util.complaintResult();
			json = new JSONObject(result);
			ld = json.getString("ld");
			addExtraToIntent(MapActivity.class, ld);
			break;

		default:
			break;
		}
	}

	@Override
	public void onFailure(String message) {
		String title = "Error";
		String btnTitle = "Aceptar";
		CustomAlertDialog.decisionAlert(context, title, message, btnTitle,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});

	}

}