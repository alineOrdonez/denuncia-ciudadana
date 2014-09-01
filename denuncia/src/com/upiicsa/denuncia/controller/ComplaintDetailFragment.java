package com.upiicsa.denuncia.controller;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.CatDenuncia;
import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.service.OnResponseListener;
import com.upiicsa.denuncia.service.RequestMessage;
import com.upiicsa.denuncia.util.Constant;
import com.upiicsa.denuncia.util.CustomAlertDialog;
import com.upiicsa.denuncia.util.Util;

public class ComplaintDetailFragment extends Fragment implements
		ConnectionCallbacks, OnConnectionFailedListener, OnResponseListener {
	private final String LOG_TAG = "ComplaintDetailFragment";
	private android.app.ProgressDialog progressDialog;
	private LocationClient mLocationClient;
	private Denuncia mItem;
	private Singleton singleton;
	private int idDenuncia;
	private int idCategoria;
	private String email;
	private RequestMessage service;

	public ComplaintDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments().containsKey(Constant.ARG_ITEM_ID)) {
			String string = getArguments().getString(Constant.ARG_ITEM_ID);
			singleton = Singleton.getInstance();
			service = new RequestMessage(this);
			idDenuncia = Integer.valueOf(string);
			mItem = singleton.getItem_map().get(idDenuncia);
			mLocationClient = new LocationClient(getActivity(), this, this);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_circle_detail,
				container, false);

		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.complaint_description))
					.setText(mItem.getDescripcion());
			((TextView) rootView.findViewById(R.id.complaint_address))
					.setText(mItem.getDireccion());
			idCategoria = mItem.getIdCategoria();
			for (CatDenuncia cat : singleton.getDenuncias()) {
				if (idCategoria == cat.getIdCatDenuncia()) {
					((TextView) rootView.findViewById(R.id.complaint_category))
							.setText(cat.getDescripcion());
				}
			}
			// TODO: cambiar por la imagen que obtengo en la respuesta
			if (singleton.getImage() != null) {
				setImage(singleton.getImage(), rootView);
				scaleImage(rootView);
			}
		}

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.complaint_detail_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.sendExistingComplaint) {
			addListenerOnButton();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	public void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	public void addListenerOnButton() {

		LayoutInflater li = LayoutInflater.from(getActivity());
		View promptsView = li.inflate(R.layout.prompts, new LinearLayout(
				getActivity()), false);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.emailEditText);
		String title = getString(R.string.insert_email);
		String btnTitle = "Enviar";
		CustomAlertDialog.promptAlert(getActivity(), title, promptsView,
				btnTitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						email = userInput.getText().toString();
						if (Util.isValidEmail(email)) {
							progressDialog = new ProgressDialog(getActivity(),
									1);
							progressDialog.show();
							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										sendComplaint();
									} catch (Exception e) {

									}
								}
							}).start();
						} else {
							userInput
									.setError(getString(R.string.invalid_email));
						}
					}
				});
	}

	public void sendComplaint() {
		LocationManager lm = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		boolean isGPSEnabled = lm
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isGPSEnabled) {
			try {
				Denuncia denuncia = new Denuncia(idDenuncia, idCategoria, email);
				service.selectComplaintService(denuncia);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			showGPSAlert();
		}
	}

	private void showGPSAlert() {
		String title = getString(R.string.gps_title);
		String message = getString(R.string.gps_message);
		String btnTitle = getString(R.string.gps_btn_title);
		CustomAlertDialog.decisionAlert(getActivity(), title, message,
				btnTitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
					}
				});
	}

	private void setImage(String myImageData, View view) {
		ImageView image = (ImageView) view
				.findViewById(R.id.complaint_image_view);
		String strImg = singleton.getImage();
		byte[] imageAsBytes = Base64.decode(strImg, Base64.URL_SAFE);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(imageAsBytes, 0,
				imageAsBytes.length);
		image.setImageBitmap(decodedByte);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Log.d(LOG_TAG, "onConnectionFailed");
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.d(LOG_TAG, "onConnected");
	}

	@Override
	public void onDisconnected() {
		Log.d(LOG_TAG, "onDisconnected");
	}

	private void scaleImage(View view) {
		// Get the ImageView and its bitmap
		ImageView imageView = (ImageView) view
				.findViewById(R.id.complaint_image_view);
		Drawable drawing = imageView.getDrawable();
		if (drawing == null) {
			return; // Checking for null & return, as suggested in comments
		}
		Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

		// Get current dimensions AND the desired bounding box
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int bounding = dpToPx(250);
		Log.i("Test", "original width = " + Integer.toString(width));
		Log.i("Test", "original height = " + Integer.toString(height));
		Log.i("Test", "bounding = " + Integer.toString(bounding));

		// Determine how much to scale: the dimension requiring less scaling is
		// closer to the its side. This way the image always stays inside your
		// bounding box AND either x/y axis touches it.
		float xScale = ((float) bounding) / width;
		float yScale = ((float) bounding) / height;
		float scale = (xScale <= yScale) ? xScale : yScale;
		Log.i("Test", "xScale = " + Float.toString(xScale));
		Log.i("Test", "yScale = " + Float.toString(yScale));
		Log.i("Test", "scale = " + Float.toString(scale));

		// Create a matrix for the scaling and add the scaling data
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		// Create a new bitmap and convert it to a format understood by the
		// ImageView
		Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		width = scaledBitmap.getWidth(); // re-use
		height = scaledBitmap.getHeight(); // re-use
		BitmapDrawable result = new BitmapDrawable(
				getActivity().getResources(), scaledBitmap);
		Log.i("Test", "scaled width = " + Integer.toString(width));
		Log.i("Test", "scaled height = " + Integer.toString(height));

		// Apply the scaled bitmap
		imageView.setImageDrawable(result);

		// Now change ImageView's dimensions to match the scaled image
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView
				.getLayoutParams();
		params.width = width;
		params.height = height;
		imageView.setLayoutParams(params);

		Log.i("Test", "done");
	}

	private int dpToPx(int dp) {
		float density = getActivity().getResources().getDisplayMetrics().density;
		return Math.round((float) dp * density);
	}

	@Override
	public void onSuccess(String result) {
		System.out.println(result);
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
		String title = getString(R.string.successful);
		String message = getString(R.string.successful_message);
		String btnTitle = getString(R.string.btn_continuar);
		CustomAlertDialog.decisionAlert(getActivity(), title, message,
				btnTitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(getActivity(),
								MainMenuActivity.class);
						startActivity(intent);
					}
				});
	}

	@Override
	public void onFailure(String message) {
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