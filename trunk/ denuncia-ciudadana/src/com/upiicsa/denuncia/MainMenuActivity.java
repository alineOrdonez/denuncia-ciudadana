package com.upiicsa.denuncia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity {

	Button denunciaButton, consultaButton;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		context = this;
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		denunciaButton = (Button) findViewById(R.id.denuncia);
		consultaButton = (Button) findViewById(R.id.consulta);

		denunciaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(context, ComplaintActivity.class);
				startActivity(i);
			}
		});

		consultaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(context, ConsultActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public void onBackPressed() {
	}
}
