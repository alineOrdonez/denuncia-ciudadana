package com.upiicsa.denuncia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ComplaintDetailActivity extends Activity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_detail);
		context = this;
	}

	public void addListenerOnButton(View view) {
		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.prompts, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set prompts.xml to alertdialog builder
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
										.show(ComplaintDetailActivity.this,
												"Por favor espere ...",
												"La denuncia se está actualizando ...",
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
										ComplaintDetailActivity.this
												.runOnUiThread(new Runnable() {

													public void run() {
														AlertDialog.Builder builder = new AlertDialog.Builder(
																context);
														builder.setTitle("Operación exitosa");
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
																				context,
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
