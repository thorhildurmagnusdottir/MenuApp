package com.gunnarsturla.restaurantappgi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Dagny on 19.11.2014.
 */

public class CallWaiter extends Activity {

    public static void callme(final Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage("Vantar þig aðstoð?");
        alertDialog.setButton("Já", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity.getApplicationContext(), "Þjónn kemur skjótt", Toast.LENGTH_LONG).show();
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
