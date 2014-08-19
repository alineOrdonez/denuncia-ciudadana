package com.upiicsa.denuncia.controller;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.NetworkUtil;

public class NewComplaintActivity extends ActionBarActivity {
	private static final String LOG_TAG = "NewComplaintActivity";
	private IntentFilter mNetworkStateChangedFilter;
	private BroadcastReceiver mNetworkStateIntentReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_complaint);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.newComplaintContainer, new NewComplaintFragment())
					.commit();
		}
		mNetworkStateChangedFilter = new IntentFilter();
		mNetworkStateChangedFilter
				.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		networkState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
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

	private void networkState() {
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
						showWiFiAlert();
					}
				}
			}
		};
	}

	private void showWiFiAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle("Configuracion del Wi-Fi");
		alertDialog.setMessage("La opcion de localizacion funciona"
				+ "mejor cuando el Wi-Fi esta habilitado.\n¿Desea activarlo?");
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
