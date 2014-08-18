package com.upiicsa.denuncia.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

	public static int getConnectivityStatus(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
		if (activeNetwork != null) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return Constants.TYPE_WIFI;
			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return Constants.TYPE_MOBILE;
		}
		return Constants.TYPE_NO_CONNECCTION;
	}
}
