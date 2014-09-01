package com.upiicsa.denuncia.controller;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.service.Callback;
import com.upiicsa.denuncia.util.Constant;

public class ComplaintListActivity extends ActionBarActivity implements
		Callback {
	private boolean mTwoPane;
	private Singleton singleton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_list);

		if (savedInstanceState == null) {
			if (getIntent().getExtras() != null) {
				singleton = Singleton.getInstance();
				if (singleton.getITEMS().isEmpty()) {
					String lista = getIntent().getStringExtra(
							Constant.EXTRA_LIST);
					try {
						if (lista != null) {
							singleton.listaDeDenuncias(lista);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (findViewById(R.id.circle_detail_container) != null) {
			mTwoPane = true;
			((ComplaintListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.circle_list))
					.setActivateOnItemClick(true);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.complaint_list_menu, menu);

		return super.onCreateOptionsMenu(menu);
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

	@Override
	public void onItemSelected(int id) {
		String idStr = String.valueOf(id);
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(Constant.ARG_ITEM_ID, idStr);
			ComplaintDetailFragment fragment = new ComplaintDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.circle_detail_container, fragment).commit();

		} else {
			Intent detailIntent = new Intent(this,
					ComplaintDetailActivity.class);
			detailIntent.putExtra(Constant.ARG_ITEM_ID, idStr);
			startActivity(detailIntent);
		}
	}

	public void nuevaDenuncia() {
		Intent i = new Intent(this, NewComplaintActivity.class);
		startActivity(i);
	}
}
