package com.upiicsa.denuncia.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.upiicsa.denuncia.R;

public class ComplaintDetailActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_detail);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.complaintDetailContainer,
							new ComplaintDetailFragment()).commit();
		}
	}
}
