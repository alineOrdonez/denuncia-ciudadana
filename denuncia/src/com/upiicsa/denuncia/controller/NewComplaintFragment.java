package com.upiicsa.denuncia.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.ConnectivityManager;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.upiicsa.denuncia.R;
import com.upiicsa.denuncia.common.CatDenuncia;
import com.upiicsa.denuncia.common.Denuncia;
import com.upiicsa.denuncia.common.Singleton;
import com.upiicsa.denuncia.service.Service;
import com.upiicsa.denuncia.util.Constants;
import com.upiicsa.denuncia.util.Util;

public class NewComplaintFragment extends Fragment {
	private static final String LOG_TAG = "NewComplaintFragment";
	private Spinner spinner;
	private EditText address;
	private EditText email;
	private Service service;
	private String image;
	private String direccion;
	private CatDenuncia catDen;
	private Singleton singleton;
	private List<String> list;
	public String imgString;
	private String name = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		singleton = Singleton.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_new_complaint,
				container, false);
		email = (EditText) view.findViewById(R.id.emailEditText);
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
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.sendNewComplaint) {
			addListenerOnButton();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
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
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
		View promptsView = li.inflate(R.layout.select_image, null);

		final RadioButton rbtnFull = (RadioButton) promptsView
				.findViewById(R.id.radbtnFull);
		final RadioButton rbtnGallery = (RadioButton) promptsView
				.findViewById(R.id.radbtnGall);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		alertDialogBuilder.setView(promptsView);

		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								int code = Constants.TAKE_PICTURE;
								if (rbtnFull.isChecked()) {
									Uri output = Uri.fromFile(new File(name));
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											output);
								} else if (rbtnGallery.isChecked()) {
									intent = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
									code = Constants.SELECT_PICTURE;
								}
								startActivityForResult(intent, code);
							}
						})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	private void addListenerOnButton() {
		final ProgressDialog ringProgressDialog = ProgressDialog.show(
				getActivity(), null, null, true);
		ringProgressDialog.setCancelable(false);
		ringProgressDialog.setIndeterminate(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendComplaintRequest();
					Thread.sleep(10000);
				} catch (Exception e) {

				}
				ringProgressDialog.dismiss();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								getActivity());
						builder.setTitle("Operación exitosa");
						builder.setMessage("La denuncia se ha registrado exitosamente.");
						builder.setPositiveButton("Continuar",
								new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
										Intent i = new Intent(getActivity(),
												MainMenuActivity.class);
										startActivity(i);

									}
								});
						builder.create();
						builder.show();
					}
				});
			}
		}).start();
	}

	public void addListenerOnFocus(View view) {
		address = (EditText) view.findViewById(R.id.addressEditText);
		address.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				showMap();
			}
		});
	}

	public void showMap() {
		Intent i = new Intent(getActivity(), SelectLocationActivity.class);
		startActivityForResult(i, Constants.RETURN_FROM_MAP);
	}

	private void checkConnectivity() {
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isWiFiEnabled = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isAvailable();

		if (!isGPSEnabled) {
			showGPSAlert();
		} else {
			if (isWiFiEnabled) {
				Intent i = new Intent(getActivity(),
						SelectLocationActivity.class);
				startActivityForResult(i, Constants.RETURN_FROM_MAP);
			} else {
				showWiFiAlert();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bitMap = null;
		switch (requestCode) {
		case 1:
			bitMap = BitmapFactory.decodeFile(name);
			imgString = Base64.encodeToString(getBytesFromBitmap(bitMap),
					Base64.NO_WRAP);
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
			// }
			break;
		case 2:
			Uri selectedImage = data.getData();
			InputStream is;
			try {
				is = getActivity().getContentResolver().openInputStream(
						selectedImage);
				BufferedInputStream bis = new BufferedInputStream(is);
				bitMap = BitmapFactory.decodeStream(bis);
				imgString = Base64.encodeToString(getBytesFromBitmap(bitMap),
						Base64.NO_WRAP);
				Log.d(LOG_TAG, "***[" + imgString + "]***");

			} catch (FileNotFoundException e) {
			}
			break;
		case 3:
			// fetch the message String
			direccion = data.getStringExtra("ADDRESS");
			address.setText(direccion);
			break;
		case 4:
			image = data.getStringExtra("IMAGEN");
			System.out.println("***[" + image + "]***");
			break;

		default:
			break;
		}
	}

	private void showGPSAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

		alertDialog.setTitle("Configuración del GPS");
		alertDialog.setMessage("Para obtener su localización es necesario"
				+ "tener habilitado el GPS.\n¿Desea activarlo?");
		alertDialog.setPositiveButton("Activar GPS",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(i);
					}
				});

		alertDialog.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();

					}
				});

		alertDialog.show();
	}

	private void showWiFiAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

		alertDialog.setTitle("Configuración del Wi-Fi");
		alertDialog.setMessage("La opción de localización funciona"
				+ "mejor cuando el Wi-Fi está habilitado.\n¿Desea activarlo?");
		alertDialog.setPositiveButton("Activar Wi-Fi",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
						startActivity(i);
					}
				});

		alertDialog.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();

					}
				});

		alertDialog.show();
	}

	private void sendComplaintRequest() {
		if (Util.isConnected(getActivity())) {
			LocationManager lm = (LocationManager) getActivity()
					.getSystemService(Context.LOCATION_SERVICE);
			Location location = lm
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			boolean isGPSEnabled = lm
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (isGPSEnabled) {
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				int idCategoria = catDen.getIdCatDenuncia();
				String descripcion = catDen.getDescripcion();
				String correo = email.getText().toString();
				try {
					Denuncia denuncia = new Denuncia(idCategoria, descripcion,
							correo, imgString, direccion, latitude, longitude);
					service.newComplaintService(denuncia);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				showGPSAlert();
			}

		} else {
			Toast.makeText(getActivity().getBaseContext(),
					Constants.CONNECTION_ERROR, Toast.LENGTH_LONG).show();
		}
	}

	public byte[] getBytesFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 70, stream);
		return stream.toByteArray();
	}
}
