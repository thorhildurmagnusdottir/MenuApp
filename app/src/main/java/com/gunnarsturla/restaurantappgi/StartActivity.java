package com.gunnarsturla.restaurantappgi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class StartActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        URL menuUrl = null;
        try {
            menuUrl = new URL(Constants.menuUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        GetMenuFromWebserviceTask getMenuTask = new GetMenuFromWebserviceTask();

        getMenuTask.execute(menuUrl);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class GetMenuFromWebserviceTask extends AsyncTask<URL, Void, Void> {

//        public MainActivity mainActivity;
        public GetMenuFromWebserviceTask(){
            //mainActivity = new MainActivity();
        }
        @Override
        protected Void doInBackground(URL... params) {
            try {
                URL url = params[0];
                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                Log.i("doinbackground", "connecting");
                //set the path where we want to save the file
                //in this case, going to save it on the root directory of the
                //sd card.
                File SDCardRoot = Environment.getExternalStorageDirectory();
                //create a new file, specifying the path, and the filename
                //which we want to save the file as.
                File file = new File(SDCardRoot, Constants.menuFile);
                //this will be used to write the downloaded data into the file we created
                FileOutputStream fileOutput = new FileOutputStream(file);
                //this will be used in reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();
                //this is the total size of the file
                int totalSize = urlConnection.getContentLength();
//            progressDialog.setMax(totalSize);
                Log.i("doInBackgournd file size is: ", Integer.toString(totalSize));
                //variable to store total downloaded bytes
                int downloadedSize = 0;
                //create a buffer...
                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer
                //now, read through the input buffer and write the contents to the file
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength);
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;
                }
                //close the output stream when done
                fileOutput.close();
                //catch some possible errors...
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            W8r.build();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // create items
            Log.i("onpostExecute", "running");
            //
			goToMainActivity();
            super.onPostExecute(aVoid);
        }
    }

	public void goToMainActivity() {
		Intent myIntent = new Intent(this, MainActivity.class);
		startActivity(myIntent);
//            mainActivity.displayMenu();
	}

}
