package com.upiicsa.denuncia.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.util.CustomList;

public class ComplaintListFragment extends Fragment {
	ListView listView;
	List<Denuncia> denList;
	/*
	 * String[] values = new String[] { "Incendio en la casa del vecino.",
	 * "El basurero de la esquina se quema.", "Fuego en el parque." };
	 */
	String[] values;
	Integer[] imageId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras!= null) {
            if (extras.containsKey("COMPLAINT_LIST")) {
                try {
                    denList = (ArrayList<Denuncia>) extras.get("COMPLAINT_LIST");  //Syntax Error here
                } catch (ClassCastException e) {
                    Log.e("B_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
        }
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
		int size = denList.size();
		values = new String[size];
		imageId = new Integer[size];
		for (int i = 0; i < values.length; i++) {
			Denuncia d = denList.get(i);
			String string = d.getDescripcion();
			values[i] = string;
			imageId[i] = R.drawable.flame;
		}

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
