package com.upiicsa.denuncia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewComplaintActivity extends Activity {
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_complaint);
		context = this;
	}

	public void addListenerOnButton(View view) {
		// TODO:Envia peticion para obtener registros
		final ProgressDialog ringProgressDialog = ProgressDialog.show(
				NewComplaintActivity.this, "Por favor espere ...",
				"La denuncia se está registrando ...", true);
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
				NewComplaintActivity.this.runOnUiThread(new Runnable() {

					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								context);
						builder.setTitle("Operación exitosa");
						builder.setMessage("La denuncia se ha registrado exitosamente.");
						builder.setPositiveButton("Continuar",
								new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
										Intent i = new Intent(context,
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
}
