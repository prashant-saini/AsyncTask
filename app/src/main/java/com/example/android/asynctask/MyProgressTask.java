package com.example.android.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyProgressTask extends AsyncTask<String,Integer,Bitmap> {

    Context ctx;
    ProgressDialog progressDialog;

    public MyProgressTask(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        String s1 = strings[0];
        Log.d("Test",s1);
        try{
            URL url = new URL(s1);
            HttpsURLConnection myConn = (HttpsURLConnection) url.openConnection();
            myConn.setReadTimeout(10000);
            myConn.setConnectTimeout(10000);
            myConn.setRequestMethod("GET");
            myConn.connect();

            InputStream in = myConn.getInputStream();
            Bitmap bm = BitmapFactory.decodeStream(in);
            return bm;

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Downloading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setButton(progressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Bitmap bm) {
        if (bm != null){
            super.onPostExecute(bm);
            Toast.makeText(ctx,"Successful",Toast.LENGTH_SHORT).show();
            MainActivity.imageView.setImageBitmap(bm);
            progressDialog.dismiss();
        }
        else{
            Toast.makeText(ctx,"Incorrect Image Adress",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }

}
