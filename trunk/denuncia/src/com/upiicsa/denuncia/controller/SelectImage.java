package com.upiicsa.denuncia.controller;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.upiicsa.denuncia.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

public class SelectImage extends Activity {
	/*
	 * Constantes según elija el Usuario
	 */
	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 2;
	public String imgString;
	
	/*
	 * Variable que almacena el nombre para el archivo donde escribiremos
	 * la foto
	 */
	private String name = "";
	
    /* Este método es llamado cuando la actividad es creada */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_image);
        
        name = Environment.getExternalStorageDirectory() + "/DenunciaImg.jpg";

        Button btnAction = (Button)findViewById(R.id.btnPic);
        btnAction.setOnClickListener(new OnClickListener() {       		
       		@Override
       		public void onClick(View v) {
       			/*
       			  Obtenemos los botones de las opciones para revisar su estatus
       			 */
       			RadioButton rbtnFull = (RadioButton)findViewById(R.id.radbtnFull);
       			RadioButton rbtnGallery = (RadioButton)findViewById(R.id.radbtnGall);
       			
       			/*
       			 * Para todos los casos es necesario un intent, si accesamos a la c‡mara con la acci—n
       			 * ACTION_IMAGE_CAPTURE, si accesamos la galer’a con la acci—n ACTION_PICK. 
       			 * En el caso de la vista previa (thumbnail) no se necesita m‡s que el intent, 
       			 * el c—digo e iniciar la actividad. Por eso inicializamos las variables intent y
       			 * code con los valores necesarios para el caso del thumbnail, as’ si ese es el
       			 * bot—n seleccionado no validamos nada en un if. 
       			 */
       			Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
       			int code = TAKE_PICTURE;
       			
       			/*
       			 * Si la opc seleccionada es cmara, necesitamos un archivo donde  guardarla
       			 */
       			if (rbtnFull.isChecked()) {					
       				Uri output = Uri.fromFile(new File(name));
       		    	intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
       			/*
       			 * Si la opción seleccionada es ir a la galería, el intent es diferente y el código también
       			 */       		    	
       			} else if (rbtnGallery.isChecked()){       				
       				intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
       				code = SELECT_PICTURE;
       			}
       			startActivityForResult(intent, code);				
       		}
       	});        
    }
    
    /*
     Función que se ejecuta cuando concluye el intent en el que se solicita una imagen
      ya sea de la c‡mara o de la galer’a
     */
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	/*
    	 * Validamos como fue tomada la imagen
    	 */
    	if (requestCode == TAKE_PICTURE) {
    			/*
    			 * A partir del nombre del archivo ya definido lo buscamos y creamos el bitmap
    			 * para el ImageView
    			 */
    		Bitmap bitmap = BitmapFactory.decodeFile(name); 
    		imgString = Base64.encodeToString(getBytesFromBitmap(bitmap), 
                    Base64.NO_WRAP);
    				
    			/*
    			 * Guardamos las Imagen en la Galeria
    			 */
    			new MediaScannerConnectionClient() {
    				private MediaScannerConnection msc = null; {
    					msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
    				}
    				public void onMediaScannerConnected() { 
    					msc.scanFile(name, null);
    				}
    				public void onScanCompleted(String path, Uri uri) { 
    					msc.disconnect();
    				} 
    			};				
    		//}
    	} else if (requestCode == SELECT_PICTURE){
    		Uri selectedImage = data.getData();
    		InputStream is;
    		try {
    			is = getContentResolver().openInputStream(selectedImage);
    	    	BufferedInputStream bis = new BufferedInputStream(is);
    	    	Bitmap bitmap = BitmapFactory.decodeStream(bis); 
    	    	imgString = Base64.encodeToString(getBytesFromBitmap(bitmap), 
                        Base64.NO_WRAP);
    	    						
    		} catch (FileNotFoundException e) {}
    	}
    	
    	Intent i = new Intent();
    	i.putExtra("Imagen", imgString);
    	setResult(RESULT_OK, i);
    	finish();
    }
					
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

}
