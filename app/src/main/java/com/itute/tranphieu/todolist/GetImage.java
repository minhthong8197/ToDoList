package com.itute.tranphieu.todolist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImage extends AsyncTask<String, Void, Bitmap> {
    Bitmap bitmap = null;

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);

            InputStream inputStream = url.openConnection().getInputStream();

            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            Log.d("\nURL", "");
        } catch (IOException e) {
            //e.printStackTrace();
            Log.d("\nopenConnection", "");
        }
        return bitmap;
    }
}
