package com.upiicsa.denuncia.controller;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.service.Callback;
import com.upiicsa.denuncia.service.OnResponseListener;
import com.upiicsa.denuncia.service.RequestMessage;
import com.upiicsa.denuncia.util.Constant;
import com.upiicsa.denuncia.util.CustomAlertDialog;

public class ComplaintListFragment extends ListFragment implements
		OnResponseListener {
	private static final String LOG_TAG = "ComplaintListFragment";
	private android.app.ProgressDialog progressDialog;
	private int mActivatedPosition = ListView.INVALID_POSITION;
	private Callback mCallbacks;
	private Singleton singleton;
	private int currentPosition;

	private static Callback sCallbacks = new Callback() {
		@Override
		public void onItemSelected(int id) {
		}
	};

	public ComplaintListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		singleton = Singleton.getInstance();
		setListAdapter(new ArrayAdapter<Denuncia>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, singleton.getITEMS()));
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState
						.containsKey(Constant.STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(Constant.STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callback)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callback) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = sCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		// TODO:
		currentPosition = singleton.getITEMS().get(position).getIdDenuncia();
		showDetail();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			outState.putInt(Constant.STATE_ACTIVATED_POSITION,
					mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}
		mActivatedPosition = position;
	}

	private void showDetail() {
		progressDialog = new ProgressDialog(getActivity(), 1);
		progressDialog.show();
		final RequestMessage request = new RequestMessage(this);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Denuncia denuncia = new Denuncia();
					denuncia.setIdDenuncia(currentPosition);
					denuncia.setIdOperacion(6);
					request.showDetailService(denuncia);
				} catch (Exception e) {

				}
			}
		}).start();
	}

	@Override
	public void onSuccess(String message) throws JSONException {
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
		JSONObject json = new JSONObject(message);
		if (json.getString("im") != null) {
			String imagen = json.getString("im");
			Singleton singleton = Singleton.getInstance();
			singleton.setImage(imagen);
		}

		mCallbacks.onItemSelected(currentPosition);
	}

	@Override
	public void onFailure(String message) throws JSONException {
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
		String title = getString(R.string.error);
		String btnTitle = getString(R.string.aceptar);
		CustomAlertDialog.decisionAlert(getActivity(), title, message,
				btnTitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
	}

}
