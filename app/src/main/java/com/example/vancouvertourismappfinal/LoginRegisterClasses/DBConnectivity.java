package com.example.vancouvertourismappfinal.LoginRegisterClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.MainActivity_1;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class DBConnectivity extends BroadcastReceiver {

    private TextView result;
    String password,role,fName,lName;
    String password1;
    String role1;
    String loginorreg;
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    private TableLayout tbl;
    private Context context;
    Context c;
    public static final String STATUS_DONE = "ALL_DONE";

    public DBConnectivity(String password, String role, Context context) {
        this.password=password;
        this.role=role;
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        role1=prefs.getString("role", "No fullName defined");
        password1=prefs.getString("pass", "No fullName defined");
        loginorreg=prefs.getString("loginOrRegister", "register");
        Log.d("Biggest test", "onReceive: "+loginorreg);
        if (intent.getAction().equals(STATUS_DONE)) {

           if(loginorreg.equals("login"))
           {
               String text = intent.getStringExtra("output_data");
               String columns[] = {"Password","Role","FirstName","LastName"};
               Log.d("DB - onReceive",text);


               try {
                   Log.d("data",text);
                   JSONArray ar = new JSONArray(text);
                   JSONObject jobj;
                   for (int x=0; x < ar.length(); x++) {
                       jobj = ar.getJSONObject(x);
                       role=jobj.getString("Role");
                       password=jobj.getString("Password");
                       fName=jobj.getString("FirstName");
                       lName=jobj.getString("LastName");
                       SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                       editor.putString("FirstName_s", fName);
                       editor.putString("LastName_s", lName);
                       editor.putString("Role_s", role);
                       editor.apply();
                       Log.d("Biggest test", "onReceive: "+role + " === "+password);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }

               if(role1.equals(role)&&password1.equals(password)) {
                   Toast.makeText(context,"Login Successful", Toast.LENGTH_SHORT).show();



                   Intent i=new Intent(context, MainActivity_1.class);
                   context.startActivity(i);
               }
               else
                   Toast.makeText(context,"Username or password or Role incorrect", Toast.LENGTH_SHORT).show();
           }
           else
           {

               String text = intent.getStringExtra("output_data");
               if(text.trim().equals("Success")) {
                   Toast.makeText(context,"Registration Successful", Toast.LENGTH_SHORT).show();

               }
               else
               {
                   Toast.makeText(context,"Failed to add user", Toast.LENGTH_SHORT).show();
               }

           }
        }
    }
}
