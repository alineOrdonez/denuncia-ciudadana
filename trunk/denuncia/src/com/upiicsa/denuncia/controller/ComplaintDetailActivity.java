package com.upiicsa.denuncia.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.util.Constants;

public class ComplaintDetailActivity extends FragmentActivity {
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.complaint_detail_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}
}
