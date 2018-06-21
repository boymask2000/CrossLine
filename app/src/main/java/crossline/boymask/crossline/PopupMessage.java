package crossline.boymask.crossline;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;

public class PopupMessage {
	public static void info(Context context, String text) {
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		alertDialogBuilder
		.setMessage(text)
		.setCancelable(true)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		  });/*
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});*/

		// create alert dialog



		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});


		/*activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show();
			}
		});*/
}}
