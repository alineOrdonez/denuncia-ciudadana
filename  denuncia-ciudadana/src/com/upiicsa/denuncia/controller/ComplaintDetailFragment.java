package com.upiicsa.denuncia.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.upiicsa.denuncia.R;

public class ComplaintDetailFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_complaint_detail,
				container, false);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.complaint_detail_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.sendExistingComplaint) {
			addListenerOnButton();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	public void addListenerOnButton() {
		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(getActivity());
		View promptsView = li.inflate(R.layout.prompts, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set prompts.xml to alert dialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.emailEditText);

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Enviar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								final ProgressDialog ringProgressDialog = ProgressDialog
										.show(getActivity(),
												"Por favor espere ...",
												"La denuncia se est� actualizando ...",
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
											Thread.sleep(10000);
										} catch (Exception e) {

										}
										ringProgressDialog.dismiss();
										getActivity().runOnUiThread(
												new Runnable() {

													public void run() {
														AlertDialog.Builder builder = new AlertDialog.Builder(
																getActivity());
														builder.setTitle("Operaci�n exitosa");
														builder.setMessage("La denuncia se ha realizado exitosamente.");
														builder.setPositiveButton(
																"Continuar",
																new OnClickListener() {

																	@Override
																	public void onClick(
																			DialogInterface dialog,
																			int which) {
																		dialog.cancel();
																		Intent i = new Intent(
																				getActivity(),
																				MainMenuActivity.class);
																		startActivity(i);

																	}
																});
														builder.create();
														builder.show();

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
	}

}
