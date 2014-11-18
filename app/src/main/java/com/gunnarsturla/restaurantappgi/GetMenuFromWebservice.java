package com.gunnarsturla.restaurantappgi;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by thorhildur on 13.11.2014.
 * This
 */
public class GetMenuFromWebservice extends AsyncTask<URL, Void, Void> {

    public static MainActivity mainActivity;
    public GetMenuFromWebservice(MainActivity theActivity){
        mainActivity = theActivity;
    }
    @Override
    protected Void doInBackground(URL... params) {
        try {
            URL url = params[0];
            //create the new connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();
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
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // create items
        Log.i("onpostExecute", "running");
//        W8r.build();
        //
        super.onPostExecute(aVoid);
    }
}
