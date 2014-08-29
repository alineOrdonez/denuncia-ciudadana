package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.upiicsa.denuncia.service.RequestMessage;
import com.upiicsa.denuncia.util.Constant;
import com.upiicsa.denuncia.util.CustomAlertDialog;
import com.upiicsa.denuncia.util.NetworkUtil;
import com.upiicsa.denuncia.util.Util;

public class MainMenuActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, OnResponseListener {

	private static final String LOG_TAG = "MainMenuActivity";
	private android.app.ProgressDialog progressDialog;
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
	private RequestMessage service;
	Singleton singleton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		context = this;
		singleton = Singleton.getInstance();
		service = new RequestMessage(this);
		mNetworkStateChangedFilter = new IntentFilter();
		mNetworkStateChangedFilter
				.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		networkState();
		mLocationClient = new LocationClient(this, this, this);
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
		mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		mLocationClient.disconnect();
		super.onStop();
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
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
		System.out.println("Result:" + result);
		JSONObject json;
		String ld;
		switch (operacion) {
		case 1:
			// Proceso para agregar las listas al singleton
			List<Map<String, Object>> categorias = new ArrayList<Map<String, Object>>();
			categorias = Util.jsonToList(result, "ld");
			fillCatalog(categorias);
			List<Map<String, Object>> intTiempo = new ArrayList<Map<String, Object>>();
			intTiempo = Util.jsonToList(result, "lt");
			fillCatalog(intTiempo);
			break;
		case 2:
			result = Util.complaintResult();
			json = new JSONObject(result);
			ld = json.getJSONObject("ld").toString();
			addExtraToIntent(ComplaintListActivity.class, ld);
			break;
		case 5:
			result = Util.complaintResult();
			json = new JSONObject(result);
			ld = json.getJSONObject("ld").toString();
			addExtraToIntent(MapActivity.class, ld);
			break;

		default:
			break;
		}
	}

	@Override
	public void onFailure(String message) {
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
		String title = getString(R.string.error);
		String btnTitle = getString(R.string.aceptar);
		CustomAlertDialog.decisionAlert(context, title, message, btnTitle,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});

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
				.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
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
				.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
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

			String title = getString(R.string.seleccione_categoria);
			String btnTitle = "Buscar";
			CustomAlertDialog.promptAlert(context, title, promptsView,
					btnTitle, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,
								int i) {
							progressDialog = new ProgressDialog(context, 1);
							progressDialog.show();
							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										findComplaints();
									} catch (Exception e) {

									}
								}
							}).start();
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

			String title = getString(R.string.select_category_and_period);
			String btnTitle = "Buscar";
			CustomAlertDialog.promptAlert(context, title, promptsView,
					btnTitle, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,
								int i) {
							progressDialog = new ProgressDialog(context, 1);
							progressDialog.show();
							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										consultAccordingToCategoryAndTime();
									} catch (Exception e) {

									}
								}
							}).start();
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
			progressDialog = new ProgressDialog(context, 1);
			progressDialog.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						operacion = 1;
						service.configService();
					} catch (Exception e) {

					}
				}
			}).start();
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
			service.findComplaintsService(denuncia);
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
			service.consultComplaintService(denuncia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showGPSAlert() {
		String title = getString(R.string.gps_title);
		String message = getString(R.string.gps_message);
		String btnTitle = getString(R.string.gps_btn_title);
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

	private void fillCatalog(List<Map<String, Object>> mapList) {
		List<CatDenuncia> catDenList = new ArrayList<CatDenuncia>();
		List<CatIntTiempo> catIntT = new ArrayList<CatIntTiempo>();
		for (Map<String, Object> map : mapList) {
			if (map.containsKey("va")) {
				CatIntTiempo catInt = new CatIntTiempo(map);
				catIntT.add(catInt);
			} else {
				CatDenuncia catDen = new CatDenuncia(map);
				catDenList.add(catDen);
			}
		}

		if (catIntT != null) {
			singleton.setIntervalos(catIntT);
		}
		if (catDenList != null) {
			singleton.setDenuncias(catDenList);
		}
	}

	public void networkState() {
		mNetworkStateIntentReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(
						ConnectivityManager.CONNECTIVITY_ACTION)) {
					int connection = NetworkUtil.getConnectivityStatus(context);
					String status = null;

					if (connection == Constant.TYPE_NO_CONNECCTION) {
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

}