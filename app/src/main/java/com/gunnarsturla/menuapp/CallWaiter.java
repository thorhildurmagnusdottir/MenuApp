package com.gunnarsturla.menuapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by: Dagny Lara
 * Date: 19.11.2014.
 * Sér um að birta AlertDialog þegar smellt er á takkann sem kallar á þjón
 */

public class CallWaiter  {

    public static void callme(final Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage("Vantar þig aðstoð frá þjóni?");
        alertDialog.setButton("Já", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity.getApplicationContext(), "Þjónn kemur skjótt", Toast.LENGTH_LONG).show();
				// TODO: Senda beiðni á vefþjónustu
            }
        });

        alertDialog.setButton2("Nei", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.show();
    }
}