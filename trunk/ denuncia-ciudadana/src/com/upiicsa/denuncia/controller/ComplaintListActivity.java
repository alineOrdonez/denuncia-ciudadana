package com.upiicsa.denuncia.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.upiicsa.denuncia.R;

public class ComplaintListActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_list);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.complaintListContainer,
							new ComplaintListFragment()).commit();
		}
	}
}
