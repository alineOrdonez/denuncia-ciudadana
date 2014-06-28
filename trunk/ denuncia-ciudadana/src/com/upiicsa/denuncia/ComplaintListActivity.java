package com.upiicsa.denuncia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ComplaintListActivity extends Activity {
	ListView listView;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_list);
		context = this;
		defineListView();
	}

	public void defineListView() {
		// Get ListView object from xml
		listView = (ListView) findViewById(R.id.complaintList);
		// Defined Array values to show in ListView
		String[] values = new String[] { "Incendio en la casa del vecino.",
				"El basurero de la esquina está quemandose.",
				"Fuego en el parque." };

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(context, ComplaintDetailActivity.class);
				startActivity(i);
			}

		});
	}

	public void nuevaDenuncia(View view) {
		Intent i = new Intent(context, NewComplaintActivity.class);
		startActivity(i);
	}
}
