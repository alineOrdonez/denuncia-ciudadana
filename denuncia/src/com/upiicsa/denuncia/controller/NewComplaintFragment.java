package com.upiicsa.denuncia.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.upiicsa.denuncia.util.CustomAdapter;
import com.upiicsa.denuncia.util.CustomAlertDialog;

public class NewComplaintFragment extends Fragment implements
		OnResponseListener, ConnectionCallbacks, OnConnectionFailedListener {
	private static final String LOG_TAG = "NewComplaintFragment";
	private android.app.ProgressDialog progressDialog;
	private LocationClient mLocationClient;
	private Spinner spinner;
	private EditText address;
	private EditText description;
	private EditText email;
	private RequestMessage service;
	private String direccion;
	private String correo;
	private String denDesc;
	private String lat;
	private String lng;
	private CatDenuncia catDen;
	private Singleton singleton;
	private List<String> list;
	public String imgString;
	private String name = "";
	// SocialAuth Components
	private static SocialAuthAdapter adapter;

	// Android Components
	private ListView listview;
	private AlertDialog dialog;
	private TextView title;
	private ProgressDialog mDialog;

	// Variables
	private String providerName;
	public static int pos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		singleton = Singleton.getInstance();
		mLocationClient = new LocationClient(getActivity(), this, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_new_complaint,
				container, false);
		email = (EditText) view.findViewById(R.id.newComplaintEmailEditText);
		address = (EditText) view.findViewById(R.id.addressEditText);
		description = (EditText) view.findViewById(R.id.descEditText);
		name = Environment.getExternalStorageDirectory() + "/DenunciaImg.jpg";
		addItemsOnSpinner(view);
		addListenerOnFocus(view);

		final Button selectImg = (Button) view.findViewById(R.id.btnImg);
		selectImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addListenerOnButtonForCamera();
			}
		});
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.new_complaint_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.sendNewComplaint) {

			if (lee(email) && lee(address) && lee(description)) {
				correo = email.getText().toString();
				denDesc = description.getText().toString();
				addListenerOnButton();
			} else {

			}

			return true;
		} else if (id == R.id.share) {
			LayoutInflater li = LayoutInflater.from(getActivity());
			View view = li.inflate(R.layout.main, new LinearLayout(
					getActivity()), false);
			// Adapter initialization
			adapter = new SocialAuthAdapter(new ResponseListener());
			listview = (ListView) view.findViewById(R.id.listview);
			listview.setAdapter(new CustomAdapter(getActivity(), adapter));
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	public static SocialAuthAdapter getSocialAuthAdapter() {
		return adapter;
	}

	private boolean lee(EditText editText) {
		String valor = editText.getText().toString().trim();
		boolean b = true;
		if (valor.isEmpty()) {
			editText.setError("Valor incorrecto.");
			b = false;
		}
		return b;
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

	public void addItemsOnSpinner(View view) {
		list = new ArrayList<String>();
		for (CatDenuncia cat : singleton.getDenuncias()) {
			list.add(cat.getDescripcion());
		}
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner.setAdapter(dataAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String strItem = spinner.getItemAtPosition(position).toString();
				System.out.println(strItem);

				for (CatDenuncia catDenuncia : singleton.getDenuncias()) {
					if (catDenuncia.getDescripcion().equals(strItem)) {
						catDen = new CatDenuncia();
						catDen = catDenuncia;
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public void addListenerOnButtonForCamera() {
		LayoutInflater li = LayoutInflater.from(getActivity());
		View promptsView = li.inflate(R.layout.select_image, new LinearLayout(
				getActivity()), false);

		final RadioButton rbtnFull = (RadioButton) promptsView
				.findViewById(R.id.radbtnFull);
		final RadioButton rbtnGallery = (RadioButton) promptsView
				.findViewById(R.id.radbtnGall);

		String btnTitle = "Aceptar";
		CustomAlertDialog.promptAlert(getActivity(), null, promptsView,
				btnTitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						int code = Constant.TAKE_PICTURE;
						if (rbtnFull.isChecked()) {
							Uri output = Uri.fromFile(new File(name));
							intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
						} else if (rbtnGallery.isChecked()) {
							intent = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
							code = Constant.SELECT_PICTURE;
						}
						startActivityForResult(intent, code);
					}
				});
	}

	private void addListenerOnButton() {
		progressDialog = new ProgressDialog(getActivity(), 1);
		progressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendComplaintRequest();
				} catch (Exception e) {

				}
			}
		}).start();
	}

	public void addListenerOnFocus(View view) {
		address.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showMap();
				}
			}
		});
	}

	public void showMap() {
		Intent i = new Intent(getActivity(), SelectLocationActivity.class);
		startActivityForResult(i, Constant.RETURN_FROM_MAP);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bitMap = null;
		byte[] bytes;
		switch (requestCode) {
		case 1:
			bitMap = BitmapFactory.decodeFile(name);
			bytes = getBytesFromBitmap(bitMap);
			imgString = Base64.encodeToString(bytes, Base64.NO_WRAP);
			new MediaScannerConnectionClient() {
				private MediaScannerConnection msc = null;
				{
					msc = new MediaScannerConnection(getActivity(), this);
					msc.connect();
				}

				public void onMediaScannerConnected() {
					msc.scanFile(name, null);
				}

				public void onScanCompleted(String path, Uri uri) {
					msc.disconnect();
				}
			};
			break;
		case 2:
			Uri selectedImage = data.getData();
			InputStream is;
			try {
				is = getActivity().getContentResolver().openInputStream(
						selectedImage);
				BufferedInputStream bis = new BufferedInputStream(is);
				bitMap = BitmapFactory.decodeStream(bis);
				bytes = getBytesFromBitmap(bitMap);
				imgString = Base64.encodeToString(bytes, Base64.NO_WRAP);
				Log.d(LOG_TAG, "***[" + imgString + "]***");

			} catch (FileNotFoundException e) {
			}
			break;
		case 3:
			String[] array = data.getStringArrayExtra("ADDRESS");
			direccion = array[0];
			address.setText(direccion);
			lat = array[1];
			lng = array[2];
			break;

		default:
			break;
		}
	}

	private void showGPSAlert() {
		String title = getString(R.string.gps_title);
		String message = getString(R.string.gps_message);
		String positiveBtnTitle = getString(R.string.gps_btn_title);
		CustomAlertDialog.decisionAlert(getActivity(), title, message,
				positiveBtnTitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						if (DialogInterface.BUTTON_POSITIVE == i) {
							Intent intent = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(intent);
						} else {

						}
					}
				});

	}

	private void sendComplaintRequest() {
		LocationManager lm = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		boolean isGPSEnabled = lm
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isGPSEnabled) {
			double longitude = Double.valueOf(lng);
			double latitude = Double.valueOf(lat);
			int idCategoria = catDen.getIdCatDenuncia();
			// String descripcion = catDen.getDescripcion();
			try {
				Denuncia denuncia = new Denuncia(idCategoria, denDesc, correo,
						imgString, direccion, latitude, longitude);
				singleton.addItems(denuncia);
				singleton.setImage(imgString);
				service = new RequestMessage(this);
				service.newComplaintService(denuncia, "Enviando denuncia...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			showGPSAlert();
		}
	}

	public byte[] getBytesFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 70, stream);
		return stream.toByteArray();
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

	@Override
	public void onSuccess(String result) {
		System.out.println(result);
		if (this.progressDialog.isShowing()) {
			this.progressDialog.dismiss();
		}
		/*
		 * try { JSONObject json = new JSONObject(result);
		 * singleton.setImage(json.getString("im")); } catch (JSONException e) {
		 * e.printStackTrace(); }
		 */

		String title = getString(R.string.successful);
		String message = getString(R.string.successful_message);
		String btnTitle = getString(R.string.btn_continuar);
		CustomAlertDialog.decisionAlert(getActivity(), title, message,
				btnTitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						progressDialog.hide();
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

	// To receive the response after authentication
	private final class ResponseListener implements DialogListener {

		@Override
		public void onComplete(Bundle values) {

			Log.d("Custom-UI", "Successful");

			// Changing Sign In Text to Sign Out
			View v = listview.getChildAt(pos
					- listview.getFirstVisiblePosition());
			TextView pText = (TextView) v.findViewById(R.id.signstatus);
			pText.setText("Sign Out");

			// Get the provider
			providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("Custom-UI", "providername = " + providerName);

			Toast.makeText(getActivity(), "Esta conectado a " + providerName,
					Toast.LENGTH_SHORT).show();
			Events(providerName);
		}

		public void Events(final String provider) {
			if (provider.equalsIgnoreCase("facebook")) {
				try {
					adapter.updateStory(
							"Hello SocialAuth Android"
									+ System.currentTimeMillis(),
							"Google SDK for Android",
							"Build great social apps and get more installs.",
							"The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.",
							"https://www.facebook.com",
							"http://carbonfreepress.gr/images/facebook.png",
							new MessageListener());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// To get status of message after authentication
		private final class MessageListener implements
				SocialAuthListener<Integer> {
			@Override
			public void onExecute(String provider, Integer t) {
				Integer status = t;
				if (status.intValue() == 200 || status.intValue() == 201
						|| status.intValue() == 204)
					Toast.makeText(getActivity(),
							"El mensaje ha sido posteado" + provider,
							Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getActivity(),
							"Error al postear el mensaje" + provider,
							Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(SocialAuthError e) {

			}
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("Custom-UI", "Error");
			error.printStackTrace();
		}

		@Override
		public void onCancel() {
			Log.d("Custom-UI", "Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Custom-UI", "Dialog Closed by pressing Back Key");

		}
	}
}
