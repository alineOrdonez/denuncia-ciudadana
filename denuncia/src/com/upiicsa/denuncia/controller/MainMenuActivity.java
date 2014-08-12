package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.CatDenuncia;
import com.upiicsa.denuncia.common.CatIntTiempo;
import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.service.Service;
import com.upiicsa.denuncia.service.TaskCompleted;
import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.Util;

public class MainMenuActivity extends Activity implements TaskCompleted {

	private Spinner category, consultRange;
	private Button consulta, denuncia;
	private CatDenuncia catDen;
	private CatIntTiempo catIntT;
	private List<String> consultList, rangeList;
	private int operacion;
	Context context;
	private Service service;
	Singleton s;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		s = Singleton.getInstance();
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
		for (CatDenuncia cat : s.getDenuncias()) {
			consultList.add(cat.getDescripcion());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, consultList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(dataAdapter);
		category.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				// do some work, update UI or reset qty and discount spinner or
				// whatever
				String strItem = category.getItemAtPosition(position)
						.toString();
				System.out.println(strItem);

				for (CatDenuncia catDenuncia : s.getDenuncias()) {
					if (catDenuncia.getDescripcion().equals(strItem)) {
						catDen = new CatDenuncia();
						catDen = catDenuncia;
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void addRangeOnSpinner(View view) {
		consultRange = (Spinner) view.findViewById(R.id.consultRange);
		for (CatIntTiempo cat : s.getIntervalos()) {
			rangeList.add(cat.getDescripcion());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, rangeList);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		consultRange.setAdapter(dataAdapter);
		consultRange.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// do some work, update UI or reset qty and discount spinner or
				// whatever
				String strItem = category.getItemAtPosition(position)
						.toString();
				System.out.println(strItem);

				for (CatIntTiempo catInt : s.getIntervalos()) {
					if (catInt.getDescripcion().equals(strItem)) {
						catIntT = new CatIntTiempo();
						catIntT = catInt;
					}
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
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
				// TODO:
				operacion = 2;
				double longitude = 19.4282476;// location.getLongitude();
				double latitude = -99.1920397;// location.getLatitude();
				int idCategoria = catDen.getIdCatDenuncia();
				String descripcion = catDen.getDescripcion();
				try {
					Denuncia denuncia = new Denuncia(idCategoria, descripcion,
							latitude, longitude);
					service.findComplaintsService(denuncia);
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
		JSONObject json;
		switch (operacion) {
		case 1:
			result = Util.configResult();
			json = new JSONObject(result);
			consulta = (Button) findViewById(R.id.consulta);
			denuncia = (Button) findViewById(R.id.denuncia);
			consulta.setEnabled(true);
			denuncia.setEnabled(true);
			List<String> descripcion = new ArrayList<String>();
			List<String> intervalo = new ArrayList<String>();
			descripcion = Util.formatConfigList(json.getString("ld"));
			denuncias(descripcion);
			intervalo = Util.formatConfigList(json.getString("lt"));
			intervalos(intervalo);
			break;
		case 2:
			result = Util.complaintResult();
			json = new JSONObject(result);
			List<String> d = new ArrayList<String>();
			d = Util.formatConfigList(json.getString("ld"));
			List<Denuncia> lista = listaD(d);
			Intent intent = new Intent(context, ComplaintListActivity.class);
			intent.putExtra("COMPLAINT_LIST", (ArrayList<Denuncia>) lista);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void denuncias(List<String> stringList) {
		List<CatDenuncia> catalogo = new ArrayList<CatDenuncia>();
		for (int i = 0; i < stringList.size(); i++) {
			CatDenuncia denuncias = new CatDenuncia();
			int id = Integer.valueOf(stringList.get(0));
			denuncias.setIdCatDenuncia(id);
			denuncias.setDescripcion(stringList.get(1));
			catalogo.add(denuncias);
		}
		s.setDenuncias(catalogo);
	}

	private void intervalos(List<String> stringList) {
		List<CatIntTiempo> catalogo = new ArrayList<CatIntTiempo>();
		for (int i = 0; i < stringList.size(); i++) {
			CatIntTiempo catIntTiempo = new CatIntTiempo();
			int id = Integer.valueOf(stringList.get(0));
			catIntTiempo.setIdCatIntTiempo(id);
			catIntTiempo.setDescripcion(stringList.get(1));
			catalogo.add(catIntTiempo);
		}
		s.setIntervalos(catalogo);
	}

	private List<Denuncia> listaD(List<String> stringList) {
		List<Denuncia> catalogo = new ArrayList<Denuncia>();
		for (int i = 0; i < stringList.size(); i++) {
			Denuncia denuncia = new Denuncia();
			int id = Integer.valueOf(stringList.get(0));
			denuncia.setIdDenuncia(id);
			denuncia.setDescripcion(stringList.get(1));
			denuncia.setCorreo(stringList.get(2));
			denuncia.setDireccion(stringList.get(3));
			catalogo.add(denuncia);
		}
		return catalogo;
	}
}