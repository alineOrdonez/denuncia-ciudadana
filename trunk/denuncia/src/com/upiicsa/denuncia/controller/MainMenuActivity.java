package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.CatDenuncia;
import com.upiicsa.denuncia.common.CatIntTiempo;
import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.service.Service;
import com.upiicsa.denuncia.service.TaskCompleted;
import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.CustomAlertDialog;
import com.upiicsa.denuncia.util.NetworkUtil;
import com.upiicsa.denuncia.util.Util;

public class MainMenuActivity extends Activity implements TaskCompleted {
	private static final String LOG_TAG = "MainMenuActivity";
	private IntentFilter mNetworkStateChangedFilter;
	private BroadcastReceiver mNetworkStateIntentReceiver;
	private LocationManager mLocationState;
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
		mLocationState = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
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
					null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
			alertDialogBuilder.setView(promptsView);
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Buscar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									final ProgressDialog ringProgressDialog = ProgressDialog
											.show(context,
													"Por favor espere ...",
													"Buscando registros ...",
													true);
									ringProgressDialog.setCancelable(false);
									ringProgressDialog.setIndeterminate(true);
									new Thread(new Runnable() {
										@Override
										public void run() {
											try {
												findComplaints();
											} catch (Exception e) {

											}
											ringProgressDialog.dismiss();
										}
									}).start();
								}
							})
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			addCategoriesOnSpinner(promptsView, R.id.selectCategory);
		} else {
			showGPSAlert();
		}

	}

	public void consultComplaint(View view) {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = mLocationState
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isGPSEnabled) {
			LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View promptsView = li.inflate(R.layout.consult_complaint_prompt,
					null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			alertDialogBuilder.setView(promptsView);

			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Buscar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									final ProgressDialog ringProgressDialog = ProgressDialog
											.show(context,
													"Por favor espere ...",
													"Buscando incidencias cercanas ...",
													true);
									ringProgressDialog.setCancelable(false);
									ringProgressDialog.setIndeterminate(true);
									new Thread(new Runnable() {
										@Override
										public void run() {
											try {
												consultAccordingToCategoryAndTime();
											} catch (Exception e) {

											}
											ringProgressDialog.dismiss();
										}
									}).start();
								}
							})
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			addCategoriesOnSpinner(promptsView, R.id.consultCategory);
			addRangeOnSpinner(promptsView);
		} else {
			showGPSAlert();
		}
	}

	public void loadConfiguration() {
		Toast.makeText(getBaseContext(), "Cargando configuración.",
				Toast.LENGTH_LONG).show();
		try {
			operacion = 1;
			service = new Service(this);
			service.configService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findComplaints() {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		Location location = lm.getLastKnownLocation(lm.getBestProvider(
				criteria, false));
		operacion = 2;
		double longitude = 19.4282476;// location.getLongitude();
		double latitude = -99.1920397;// location.getLatitude();
		int idCategoria = catDen.getIdCatDenuncia();
		String descripcion = catDen.getDescripcion();
		try {
			Denuncia denuncia = new Denuncia(idCategoria, descripcion,
					latitude, longitude);
			service.findComplaintsService(denuncia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void consultAccordingToCategoryAndTime() {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		Location location = lm.getLastKnownLocation(lm.getBestProvider(
				criteria, false));
		operacion = 5;
		double longitude = 19.4282476;// location.getLongitude();
		double latitude = -99.1920397;// location.getLatitude();
		int idCategoria = catDen.getIdCatDenuncia();
		int idIntervalo = catIntT.getIdCatIntTiempo();
		String descripcion = catDen.getDescripcion();
		try {
			Denuncia denuncia = new Denuncia(idCategoria, idIntervalo,
					descripcion, latitude, longitude);
			service.consultComplaintService(denuncia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showGPSAlert() {
		String title = "Configuración del GPS";
		String message = "Es necesaria su localización para"
				+ " obtener la lista de denuncias cercanas."
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

	@Override
	public void onTaskComplete(String result) throws JSONException {
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
						loadConfiguration();
					}
				}
			}
		};
	}

}