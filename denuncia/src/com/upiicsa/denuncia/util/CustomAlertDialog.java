package com.upiicsa.denuncia.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.upiicsa.denuncia.R;

public class CustomAlertDialog {

	public static void decisionAlert(Context context, String title,
			String message, String positiveButton,
			DialogInterface.OnClickListener posCallback) {
		showAlertDialog(context, title, message, positiveButton, posCallback);
	}

	public static void showAlertDialog(Context ctx, String title,
			String message, String positiveButton,
			DialogInterface.OnClickListener posCallback) {
		if (title == null)
			title = ctx.getResources().getString(R.string.app_name);

		final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setPositiveButton(positiveButton, posCallback);
		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
}