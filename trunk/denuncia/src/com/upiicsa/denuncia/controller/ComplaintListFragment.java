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
import android.widget.ListView;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.util.CustomList;

public class ComplaintListFragment extends Fragment {
	ListView listView;
	String[] values = new String[] { "Incendio en la casa del vecino.",
			"El basurero de la esquina se quema.", "Fuego en el parque." };
	Integer[] imageId = { R.drawable.flame, R.drawable.flame, R.drawable.flame };

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

		CustomList adapter = new CustomList(getActivity(), values, imageId);
		listView = (ListView) view.findViewById(R.id.complaintList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(),
						ComplaintDetailActivity.class);
				String descripcion = values[position];
				i.putExtra("DESCRIPTION", descripcion);
				startActivity(i);
			}

		});
	}

	public void nuevaDenuncia() {
		Intent i = new Intent(getActivity(), NewComplaintActivity.class);
		startActivity(i);
	}

}
