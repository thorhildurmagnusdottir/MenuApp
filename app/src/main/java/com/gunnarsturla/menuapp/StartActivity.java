package com.gunnarsturla.menuapp;

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
import java.util.ArrayList;
import java.util.Date;

import data.W8r;
import menu.Item;
import menu.SubMenu;

/**
 * @author Þórhildur Magnúsdóttir
 * @since 8.10.14
 * Fyrsta valmyndin sem ræsist. Hún sér um að sækja matseðilinn
 * í formi xml skrár, sækja myndir og senda allar upplýsingar
 * á rétta staði.
 */

public class StartActivity extends Activity {
    public static int itemCount = 0;
    public static int photoCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new GetImageFromWebTask().execute(Constants.submenuImageUrl, Constants.submmenuimageFile);

        new GetImageFromWebTask().execute(Constants.imageURL, Constants.imageFile);

        startMenu(Constants.menuUrl);
    }
    protected void startMenu(String menu){
        URL menuUrl = null;
        try {
            menuUrl = new URL(menu);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new GetMenuFromWebserviceTask().execute(menuUrl);
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

//    This class gets the menu from the internet and saves locally
    public class GetMenuFromWebserviceTask extends AsyncTask<URL, Void, Void> {
        public GetMenuFromWebserviceTask(){
        }
        @Override
        protected Void doInBackground(URL... params) {
            try {
                Log.i("Getting menu from url; ", params[0].toString());
                URL url = params[0];
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

//                The file used to store and read from the XML menu
                File sdcard = Environment.getExternalStorageDirectory();
                File file = new File(sdcard,Constants.menuFile);
                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = urlConnection.getInputStream();

                int totalSize = urlConnection.getContentLength();
                int downloadedSize = 0;

                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    fileOutput.write(buffer, 0, bufferLength);

                    downloadedSize += bufferLength;
                }
                fileOutput.close();

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
            infoText.setText("Matseðill hefur verið sóttur, sæki nú myndir fyrir rétti");
            super.onPostExecute(aVoid);
            new GetPhotosForItemsTask().execute();
        }
    }
//    This class instantiates an AsyncTask to download pictures for all items
    public class GetPhotosForItemsTask extends AsyncTask<String, Void, Void>{
        ArrayList<SubMenu> w8rMenu = W8r.getW8rMenu();
        @Override
        protected Void doInBackground(String... params) {
            itemCount = W8r.getItemCount();
            Log.i("AsyncGetAllPhotos", "running and itemCount is " + itemCount);
            for (SubMenu sm : w8rMenu){
                String sfilename = sm.getImghash() + "submenu.png";
                String submenuImageUrl;
                        if( "" == sm.getPicture()){
                            submenuImageUrl = Constants.submenuImageUrl;
                        }
                else submenuImageUrl = sm.getPicture();

                Log.i("getting image", sfilename + " at url: " + submenuImageUrl);
                new GetImageFromWebTask().execute(submenuImageUrl, sfilename);
                for (Item i : sm.getItems()){

                    String ifilename = i.getId() + "item.png" ;
                    String itemImageurl;
                        if (i.getThumbBigUrl() == null){
                            Log.i("thumb", i.getName() + "has no thumb");
                            itemImageurl = Constants.imageURL;
                        }
                    else {
                        itemImageurl = i.getThumbBigUrl();
                        Log.i("itemurl", i.getThumbBigUrl());
                        }
                    new GetImageFromWebTask().execute(itemImageurl, ifilename);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
//    Sets all saved photos to items
    public void setPhotosToItems(){
        String submenuPrinting = "";
        if (null != W8r.getW8rMenu()){
            ArrayList<SubMenu> w8rMenu = W8r.getW8rMenu();
            File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

 //           File path = Environment.getExternalStorageDirectory();
            for (SubMenu sm : w8rMenu) {
//                Load the picture for the submenu
                InputStream sis;
                String submenuFileName = sm.getImghash() + "submenu.png";
                File submenuFile = new File(path, submenuFileName);
//                File submenuFile = new File(sdcard, submenuFileName);

                if (!submenuFile.exists()){
                   submenuFile = new File(path, Constants.submmenuimageFile);
                }
                try {
                    sis = new FileInputStream(submenuFile);
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
//                    Load the picture for the item
                    InputStream is;
                    String itemFileName = i.getId() + "item.png";
//                    File itemFile = new File(path, itemFileName);

                    File itemFile = new File(path, itemFileName);
                    if (!itemFile.exists()){
                        itemFile = new File(path, Constants.imageFile);
                    }
                    try {
                        is = new FileInputStream(itemFile);
                        Bitmap bm = BitmapFactory.decodeStream(is, null, null);
                        i.setThumbBig(bm);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String itemName = i.getName();
                    String itemThumbBig;
                    submenuPrinting = submenuPrinting + itemName +  "\n";
                    if (i.getThumbBigUrl() != null) {   itemThumbBig = " has picture: " + i.getThumbBigUrl();}
                    else {  itemThumbBig = " has no picture"; }
                    submenuPrinting = submenuPrinting + itemThumbBig + "\n";
                }
            }
        }
        submenuPrinting = submenuPrinting + "submenu er null";
        Log.i("setPhotosToItems", submenuPrinting);
        goToMainActivity();
    }

//    Usage:    new GetImageFromWebTask().execute(imgUrl, filename);
//    Pre:      'imgUrl' is a String representing the url to download a png from
//    Post:     The PNG image has been saved to the app-private directory 'Pictures'
//              with the 'filename'
    public class GetImageFromWebTask extends AsyncTask<String, Void, Void>{

    @Override
        protected Void doInBackground(String... params) {
            Date now = new Date();
            Log.i("getting photo for item " + params[1], now.toString());
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
//            File path = getExternalFilesDir(null);
            File path = Environment.getExternalStorageDirectory();
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
            photoCount += 1;
            Log.i("photoCount is ", photoCount + "");
            super.onPostExecute(aVoid);
            if (photoCount == itemCount){
//                When finished getting all photos we will set them to items.
                Log.i("Got photos for all items", "let's set them to items");
                setPhotosToItems();
            }
        }
    }
	public void goToMainActivity() {
		Intent myIntent = new Intent(this, MainActivity.class);
		startActivity(myIntent);
	}

}
