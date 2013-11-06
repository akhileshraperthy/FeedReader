package com.araperth.xmlfeedreader;

import com.araperth.xmlfeedreader.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogBox {
    // Class is used when there is any error messages to display in the form of Alerts.
    @SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
            Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
  
        // Title
        alertDialog.setTitle(title);
  
        // Message
        alertDialog.setMessage(message);
  
        if(status != null)
            // status icons
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.failure);
  
        // Event Listeners
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            }
        });
  
        // Show 
        alertDialog.show();
    }
}
