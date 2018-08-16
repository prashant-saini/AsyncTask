package com.example.android.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    static ImageView imageView;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    RequestQueue queue;
    ImageRequest imageRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        imageView = (ImageView) findViewById(R.id.image_view);
        queue = Volley.newRequestQueue(this);


    }

    public void doSomething(View view) {

        if(networkInfo != null && networkInfo.isConnected()){

            MyProgressTask task = new MyProgressTask(this);
            task.execute("https://www.planwallpaper.com/static/images/7004205-cool-black-backgrounds-27640_lhK8IKI.jpg");

//            String str = "https://www.planwallpaper.com/static/images/7004205-cool-black-backgrounds-27640_lhK8IKI.jpg";
//            imageRequest = new ImageRequest(str,new ResponseListener(),0,0,null,new ErrorListener());
//            queue.add(imageRequest);
        }
        else
        {
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

    }

    private class ResponseListener implements Response.Listener<Bitmap>{

        @Override
        public void onResponse(Bitmap bm) {
            imageView.setImageBitmap(bm);
        }
    }

    private class ErrorListener implements Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this,"Incorrect Image Adress",Toast.LENGTH_SHORT).show();
        }
    }


}
