package com.example.edu.weatherapiuse;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mButtonLondon=findViewById(R.id.buttonLondon);
        Button mButtonSeoul=findViewById(R.id.buttonSeoul);
        mTextView=findViewById(R.id.textViewResult);
        mButtonLondon.setOnClickListener(this);
        mButtonSeoul.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLondon:
                OpenWeatherApiTask task = new OpenWeatherApiTask();

                try {
                    String weather=task.execute("London").get();
                    mTextView.setText(weather);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    private class OpenWeatherApiTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String  id=params[0];
            String weather=null;
            String urlString="https://api.openweathermap.org/data/2.5/weather"+ "?q="+ id + "&appid=c0390267f0b62061d26f07fcbacfbd80";
            try {
                URL url=new URL(urlString);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                byte[] buffer=new byte[1000];
                in.read(buffer);
                 weather= new String(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weather;
        }
    }
}
