package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.upiicsa.denuncia.R;

public class MainMenuActivity extends Activity {

	private Spinner spinner;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		context = this;
	}

	public void addListenerOnButton(View view) {
		Intent i = new Intent(context, ConsultActivity.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
	}

	// add items into spinner dynamically

	public void addItemsOnSpinner(View view) {
		spinner = (Spinner) view.findViewById(R.id.selectCategory);
		List<String> list = new ArrayList<String>();
		list.add("Fuga de agua");
		list.add("Incendio");
		list.add("Robo");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}

	public void showPrompt(View view) {
		// get prompts.xml view
		LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View promptsView = li.inflate(R.layout.select_complaint_prompt, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set prompts.xml to alert dialog builder
		alertDialogBuilder.setView(promptsView);
		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Buscar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								final ProgressDialog ringProgressDialog = ProgressDialog
										.show(context, "Por favor espere ...",
												"Buscando registros ...", true);
								ringProgressDialog.setCancelable(false);
								ringProgressDialog.setIndeterminate(true);
								new Thread(new Runnable() {
									@Override
									public void run() {
										try {
											// Here you should write
											// your time consuming
											// task...
											// Let the progress ring for
											// 10 seconds...
											Thread.sleep(6000);
										} catch (Exception e) {

										}
										ringProgressDialog.dismiss();
										MainMenuActivity.this
												.runOnUiThread(new Runnable() {

													public void run() {
														Intent i = new Intent(
																context,
																ComplaintListActivity.class);
														startActivity(i);

													}
												});
									}
								}).start();

							}
						})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();

		addItemsOnSpinner(promptsView);
	}
}
