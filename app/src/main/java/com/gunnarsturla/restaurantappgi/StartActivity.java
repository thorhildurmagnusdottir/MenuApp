package com.gunnarsturla.restaurantappgi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Vector;

import menu.Item;
import menu.SubMenu;


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
        public GetMenuFromWebserviceTask(){
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
            final TextView infoText = (TextView) findViewById(R.id.oneMoment);
            infoText.setText("Búin að sækja matseðil, sæki nú myndir");
            super.onPostExecute(aVoid);
//            goToMainActivity();
            getPhotosForItems();
        }
    }
    public void getPhotosForItems(){
        String submenuPrinting = "";
        if (null != W8r.getW8rMenu()){
        Vector<SubMenu> w8rMenu = W8r.getW8rMenu();
        SubMenu testSubmenu = w8rMenu.get(0);
        String testSubmenuUrl = testSubmenu.getPicture();
            new GetPNGFromWebTask().execute(testSubmenuUrl, "submenu.png");
        Item testItem = w8rMenu.get(0).get(0);
        String testUrl = testItem.getThumbBigUrl();
        Log.i(testItem.getThumbBigUrl(), "has or hasn't");
        new GetPNGFromWebTask().execute(testUrl, "test.png");
            File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File file = new File(path, "test.png");
            File submenufile = new File(path, "submenu.png");
            //=======================================================
            for (SubMenu sm : w8rMenu) {
                InputStream sis;
                try {
                    sis = new FileInputStream(submenufile);
                    Bitmap bm = BitmapFactory.decodeStream(sis, null, null);
                    sm.setBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String submenuName = sm.getName();
                submenuPrinting = submenuPrinting + submenuName + " has picture: \n";
                String submenuPic = sm.getPicture();
                submenuPrinting = submenuPrinting + submenuPic + "\n";
                for (Item i : sm.getItems()) {
                    InputStream is;
                    try {
                        is = new FileInputStream(file);
                        Bitmap bm = BitmapFactory.decodeStream(is, null, null);
                        i.setThumbBig(bm);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String itemName = i.getName();
                    String itemThumbBig, itemThumbSmall;
                    submenuPrinting = submenuPrinting + itemName +  "\n";
                    if (i.getThumbBigUrl() != null) {   itemThumbBig = " has picture: " + i.getThumbBigUrl();}
                    else {  itemThumbBig = " has no picture"; }
                    submenuPrinting = submenuPrinting + itemThumbBig + "\n";
                    if (i.getThumbSmallUrl() != null) {  itemThumbSmall = " has picture: " + i.getThumbSmallUrl();}
                    else { itemThumbSmall = " has no picture"; }
                        submenuPrinting = submenuPrinting + itemThumbSmall + "\n";
                }
            }
        }
        submenuPrinting = submenuPrinting + "submenu er null";
        Log.i("getPhotosForItems", submenuPrinting);


    }
    //    Usage:    new GetPNGFromWebTask().execute(imgUrl, filename);
//    Pre:      'imgUrl' is a String representing the url to download a png from
//    Post:     The PNG image has been saved to the app-private directory 'Pictures'
//              with the 'filename'
    public class GetPNGFromWebTask extends AsyncTask<String, Void, Void>{
        Date before = new Date();
        @Override
        protected Void doInBackground(String... params) {
            byte[] bytes;
            try {
                URL url = new URL(params[0]);
                InputStream is = (InputStream) url.getContent();
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = is.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                bytes = output.toByteArray();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
//            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            picture.setImageBitmap(bm);
            File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File file = new File(path, params[1]);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Date after = new Date();
            long diffTime = before.getTime()-after.getTime();
            Log.i("Asynctask finished", "time it took " + diffTime);
            goToMainActivity();
        }
    }
	public void goToMainActivity() {
		Intent myIntent = new Intent(this, MainActivity.class);
		startActivity(myIntent);
	}

}
