package com.upiicsa.denuncia.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.upiicsa.denuncia.R;

public class ComplaintListFragment extends Fragment {
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_complaint_list,
				container, false);
		defineListView(view);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.complaint_list_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.nuevaDenuncia) {
			nuevaDenuncia();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	public void defineListView(View view) {
		// Get ListView object from xml
		listView = (ListView) view.findViewById(R.id.complaintList);
		// Defined Array values to show in ListView
		String[] values = new String[] { "Incendio en la casa del vecino.",
				"El basurero de la esquina está quemandose.",
				"Fuego en el parque." };

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(),
						ComplaintDetailActivity.class);
				startActivity(i);
			}

		});
	}

	public void nuevaDenuncia() {
		Intent i = new Intent(getActivity(), NewComplaintActivity.class);
		startActivity(i);
	}
}
