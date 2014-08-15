package com.upiicsa.denuncia.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.DenunciaContent;
import com.upiicsa.denuncia.service.Callback;
import com.upiicsa.denuncia.util.Constants;

public class ComplaintListActivity extends ActionBarActivity implements
		Callback {
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_list);

		if (savedInstanceState == null) {
			if (getIntent().getExtras() != null) {
				String lista = getIntent().getStringExtra(Constants.EXTRA_LIST);
				new DenunciaContent(lista);
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
	public void onItemSelected(int id) {
		String idStr = String.valueOf(id);
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(Constants.ARG_ITEM_ID, idStr);
			ComplaintDetailFragment fragment = new ComplaintDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.circle_detail_container, fragment).commit();

		} else {
			Intent detailIntent = new Intent(this,
					ComplaintDetailActivity.class);
			detailIntent.putExtra(Constants.ARG_ITEM_ID, idStr);
			startActivity(detailIntent);
		}
	}
}
