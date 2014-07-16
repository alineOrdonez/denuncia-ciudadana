package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.List;

import com.upiicsa.denuncia.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ConsultActivity extends Activity {

	private Spinner category, period;
	private Button findButton;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult);
		context = this;
		addItemsOnSpinner();
		addListenerOnButton();
	}

	public void addItemsOnSpinner() {

		category = (Spinner) findViewById(R.id.categorySpinner);
		List<String> categoryList = new ArrayList<String>();
		categoryList.add("Fuga de agua");
		categoryList.add("Incendio");
		categoryList.add("Robo");
		ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, categoryList);
		categoryDataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(categoryDataAdapter);

		period = (Spinner) findViewById(R.id.periodSpinner);
		List<String> periodList = new ArrayList<String>();
		periodList.add("Hace 2 hrs.");
		periodList.add("Hace 4 hrs.");
		periodList.add("Hace 12 hrs.");
		ArrayAdapter<String> periodDataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, periodList);
		periodDataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		period.setAdapter(periodDataAdapter);
	}

	// get the selected dropdown list value
	public void addListenerOnButton() {
		category = (Spinner) findViewById(R.id.selectCategory);
		period = (Spinner) findViewById(R.id.periodSpinner);
		findButton = (Button) findViewById(R.id.continueButton);
		findButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO:Envia peticion para obtener registros
				final ProgressDialog ringProgressDialog = ProgressDialog.show(
						ConsultActivity.this, "Por favor espere ...",
						"Buscando incidencias cercanas ...", true);
				ringProgressDialog.setCancelable(false);
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
						// TODO:Cambiar clase
						Intent i = new Intent(context, MapActivity.class);
						startActivity(i);
					}
				}).start();
			}
		});
	}
}
