package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.service.Service;
import com.upiicsa.denuncia.service.TaskCompleted;
import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.Util;

public class MainMenuActivity extends Activity implements TaskCompleted {

	private Spinner category, consultRange;
	private Button consulta, denuncia;
	private List<String> consultList, rangeList;
	private int operacion;
	Context context;
	private Service service;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		context = this;
		consultList = new ArrayList<String>();
		rangeList = new ArrayList<String>();
		loadConfiguration();
	}

	@Override
	public void onBackPressed() {
	}

	// add items into spinner dynamically

	public void addCategoriesOnSpinner(View view, int id) {
		category = (Spinner) view.findViewById(id);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, consultList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(dataAdapter);

	}

	public void addRangeOnSpinner(View view) {
		consultRange = (Spinner) view.findViewById(R.id.consultRange);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, rangeList);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		consultRange.setAdapter(dataAdapter);
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
											// TODO:
											Thread.sleep(6000);
										} catch (Exception e) {

										}
										ringProgressDialog.dismiss();
										MainMenuActivity.this
												.runOnUiThread(new Runnable() {

													public void run() {
														/*
														 * Intent i = new
														 * Intent( context,
														 * ComplaintListActivity
														 * .class);
														 * startActivity(i);
														 */
														findComplaints();

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
		addCategoriesOnSpinner(promptsView, R.id.selectCategory);
	}

	public void consultComplaint(View view) {
		// get prompts.xml view
		LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View promptsView = li.inflate(R.layout.consult_complaint_prompt, null);

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
										.show(context,
												"Por favor espere ...",
												"Buscando incidencias cercanas ...",
												true);
								ringProgressDialog.setCancelable(false);
								ringProgressDialog.setIndeterminate(true);
								new Thread(new Runnable() {
									@Override
									public void run() {
										try {
											Thread.sleep(6000);
										} catch (Exception e) {

										}
										ringProgressDialog.dismiss();
										MainMenuActivity.this
												.runOnUiThread(new Runnable() {

													public void run() {
														findComplaints();
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
		addCategoriesOnSpinner(promptsView, R.id.consultCategory);
		addRangeOnSpinner(promptsView);
	}

	public void loadConfiguration() {
		if (Util.isConnected(context)) {
			Toast.makeText(getBaseContext(), "Cargando configuración.",
					Toast.LENGTH_LONG).show();
			try {
				operacion = 1;
				service = new Service(this);
				service.configService();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getBaseContext(), Constants.CONNECTION_ERROR,
					Toast.LENGTH_LONG).show();
		}
	}

	private void findComplaints() {
		if (Util.isConnected(context)) {
			LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location location = lm
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			boolean isGPSEnabled = lm
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (isGPSEnabled) {
				operacion = 2;
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				Map<String, String> object = new HashMap<String, String>();
				String lat = String.valueOf(latitude);
				String lng = String.valueOf(longitude);
				try {
					object.put("i", "02");
					object.put("ic", "02");
					object.put("dc", "02");
					object.put("la", lat);
					object.put("lo", lng);
					// service.setRequest(object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				showGPSAlert();
			}

		} else {
			Toast.makeText(getBaseContext(), Constants.CONNECTION_ERROR,
					Toast.LENGTH_LONG).show();
		}
	}

	private void showGPSAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle("Configuración del GPS");
		alertDialog.setMessage("Es necesaria su localización para"
				+ " obtener la lista de denuncias cercanas."
				+ "¿Desea activar el GPS?");
		alertDialog.setPositiveButton("Activar GPS",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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

	@Override
	public void onTaskComplete(String result) throws JSONException {
		System.out.println("Result:" + result);

		switch (operacion) {
		case 1:
			consulta = (Button) findViewById(R.id.consulta);
			denuncia = (Button) findViewById(R.id.denuncia);
			consulta.setEnabled(true);
			denuncia.setEnabled(true);
			Singleton s = Singleton.getInstance();
			break;
		case 2:

			break;

		default:
			break;
		}

	}
}