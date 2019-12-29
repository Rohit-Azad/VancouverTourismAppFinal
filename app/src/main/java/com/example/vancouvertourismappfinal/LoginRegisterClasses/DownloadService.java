package com.example.vancouvertourismappfinal.LoginRegisterClasses;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/*
 IntentService is a base class for Services that handle asynchronous requests (expressed as Intents)
 on demand. Clients send requests through startService(Intent) calls
 */
/*
In AndroidManifest.xml
<service
    android:name=".DownloadService"
    android:exported="false"></service>
 */
public class DownloadService extends IntentService {
    String params;

    String site="http://ec2-54-175-146-67.compute-1.amazonaws.com:8000";


    // required to have a constructor
    public DownloadService() {
        super(DownloadService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {


        String loginorreg=intent.getStringExtra("loginOrRegister");
        if(loginorreg.equals("login")) {
            String fields[] = {"UserName", "loginOrRegister"};
            params = putParamTogether(fields,intent);
        }else
        {
            String fields[] = {"UserName","Password","FirstName","LastName","Role","loginOrRegister"};
            params = putParamTogether(fields,intent);
        }
     //   String params = putParamTogether(fields,intent);
        Log.d("how params look like",params);

        // do some work
      //  String site = "http://ec2-100-26-208-129.compute-1.amazonaws.com:8000";


        String results = getRemoteData(site,params);

        //Broadcast the result
        Intent broadcast = new Intent();

        // An action name, such as ACTION_VIEW.
        // Application-specific actions should be prefixed with the vendor's package name.
        broadcast.setAction(DBConnectivity.STATUS_DONE);
       // Log.d("id",results);
        broadcast.putExtra("output_data",results);
        sendBroadcast(broadcast);
    }

    private String putParamTogether(String f[], Intent i) {
        StringBuilder sb = new StringBuilder();
        String value = null ;

        // the key must have the same spelling and case when extracted by the web app
        for (String k : f) {
            try {
                // https://developer.android.com/reference/java/net/URLEncoder
                value = URLEncoder.encode(i.getStringExtra(k), "UTF-8");
            } catch (UnsupportedEncodingException e) {

            }

            if (sb.length() > 0)
                sb.append("&"); // to separate each entry

            sb.append(k).append("=").append(value);
        }
        return sb.toString();
    }

    private String getRemoteData(String site, String params) {

        HttpURLConnection c = null;
        try {
            URL u = new URL(site);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoInput(true);            // receiving data from the web
            c.setDoOutput(true);            // application sending data to the web

            //passing the data to the server
            OutputStreamWriter writer = new OutputStreamWriter(c.getOutputStream()); // similar to writing to a text file
            writer.write(params);
            writer.flush();
            writer.close();

            c.connect();
            int status = c.getResponseCode();
            Log.d("code",status + "");
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
                case 404: return "Cant find";
            }

        } catch (Exception ex) {
            return ex.toString();
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    //disconnect error
                }
            }
        }
        return null;

    }
}
