package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
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
import com.upiicsa.denuncia.common.DenunciaService;

public class MainMenuActivity extends Activity {

	private DenunciaService service;
	private Spinner category, consultRange;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		context = this;
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	protected void onStart() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("i", "01");
		JSONObject jsonObject = service.setRequest(map);
		
	}

	// add items into spinner dynamically

	public void addCategoriesOnSpinner(View view, int id) {
		category = (Spinner) view.findViewById(id);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, categories());
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(dataAdapter);

	}

	public void addRangeOnSpinner(View view) {
		consultRange = (Spinner) view.findViewById(R.id.consultRange);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				MainMenuActivity.this,
				android.R.layout.simple_spinner_dropdown_item, range());

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		consultRange.setAdapter(dataAdapter);
	}

	private List<String> categories() {
		List<String> list = new ArrayList<String>();
		list.add("Fuga de agua");
		list.add("Incendio");
		list.add("Robo");
		return list;
	}

	private List<String> range() {
		List<String> list = new ArrayList<String>();
		list.add("Hace 2 hrs.");
		list.add("Hace 4 hrs.");
		list.add("Hace 12 hrs.");
		return list;
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
																MapActivity.class);
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
		addCategoriesOnSpinner(promptsView, R.id.consultCategory);
		addRangeOnSpinner(promptsView);
	}
}
