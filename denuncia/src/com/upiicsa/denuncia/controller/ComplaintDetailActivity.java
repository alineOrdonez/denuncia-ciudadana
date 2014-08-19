package com.upiicsa.denuncia.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.NetworkUtil;

public class ComplaintDetailActivity extends ActionBarActivity {
	private static final String LOG_TAG = "ComplaintDetailActivity";
	private IntentFilter mNetworkStateChangedFilter;
	private BroadcastReceiver mNetworkStateIntentReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_detail);
		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(Constants.ARG_ITEM_ID, getIntent()
					.getStringExtra(Constants.ARG_ITEM_ID));
			ComplaintDetailFragment fragment = new ComplaintDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.circle_detail_container, fragment).commit();
		}
		mNetworkStateChangedFilter = new IntentFilter();
		mNetworkStateChangedFilter
				.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		networkState();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
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
					}
				}
			}
		};
	}
}
