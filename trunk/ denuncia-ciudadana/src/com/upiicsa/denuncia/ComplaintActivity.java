package com.upiicsa.denuncia;

import java.util.ArrayList;
import java.util.List;

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

public class ComplaintActivity extends Activity {

	private Spinner spinner;
	private Button findButton;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint);
		context = this;
		addItemsOnSpinner();
		addListenerOnButton();
	}

	// add items into spinner dynamically
	public void addItemsOnSpinner() {

		spinner = (Spinner) findViewById(R.id.selectCategory);
		List<String> list = new ArrayList<String>();
		list.add("Fuga de agua");
		list.add("Incendio");
		list.add("Robo");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}

	// get the selected dropdown list value
	public void addListenerOnButton() {
		spinner = (Spinner) findViewById(R.id.selectCategory);
		findButton = (Button) findViewById(R.id.findButton);
		findButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO:Envia peticion para obtener registros
				final ProgressDialog ringProgressDialog = ProgressDialog.show(
						ComplaintActivity.this, "Por favor espere ...",
						"Buscando registros ...", true);
				ringProgressDialog.setCancelable(false);
				ringProgressDialog.setIndeterminate(true);
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
						Intent i = new Intent(context,
								ComplaintListActivity.class);
						startActivity(i);
					}
				}).start();
			}
		});
	}
}
