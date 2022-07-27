package com.example.ecommerceapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
EditText edt_password,edt_username,edt_email;
TextView txt_heading,txt_down;
Button btn_signup;
boolean isSigningUp=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_username=findViewById(R.id.signup_username);
        edt_email=findViewById(R.id.signup_email);
        edt_password=findViewById(R.id.signup_password);
        txt_heading=findViewById(R.id.txt_head);
        btn_signup=findViewById(R.id.signup_btn);
        txt_down=findViewById(R.id.signup_login_text);
        txt_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSigningUp)
                {
                    edt_username.setVisibility(View.VISIBLE);
                    txt_heading.setText("Signup");
                    txt_down.setText("Already have an Account ?");
                    isSigningUp=false;
                    btn_signup.setText("SignUp");
                }
                else
                {
                    edt_username.setVisibility(View.GONE);
                    txt_heading.setText("Login");
                    txt_down.setText("Create an Account?");
                    isSigningUp=true;
                    btn_signup.setText("LogIn");
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSigningUp)
                {
                    authenticateUser();
                }
                else
                {
                    addUser();
                }
            }
        });

    }

    private void authenticateUser() {
        RequestQueue requestQueue1= Volley.newRequestQueue(MainActivity.this);

        String email=edt_email.getText().toString().trim();
        String password=edt_password.getText().toString().trim();
        String authToken= UUID.randomUUID().toString();
        String url="https://healthy-me-rest-api.herokuapp.com/authenticate2?email="+email+"&password="+password;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject= null;
                String message = null;
                try {
                    if(response.getInt("status")!=-1) {
                        jsonObject = (JSONObject) response.getJSONObject("data");
                        String fname = jsonObject.getString("firstName");
                        String femail = jsonObject.getString("email");
                        String fpassword = jsonObject.getString("password");
                        String authToken = jsonObject.getString("authToken");
                        int status = response.getInt("status");
                        message = response.getString("msg");
                        Log.e("Response", response.toString());
                        Log.e("Login Response ellaborated", fname + "--" + femail + "--" + fpassword);
                        Log.e("Status:", String.valueOf(status));
                        SharedPreferences sharedPreferences=getSharedPreferences("myapp",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("authToken",authToken);
                        myEdit.putString("email",email);
                        myEdit.putString("firstName",fname);
                        myEdit.commit();
                        Intent i=new Intent(MainActivity.this,Page2.class);
                        i.putExtra("authToken",authToken);
                        startActivity(i);
                        
                        
                    }
                    
                    else
                    {
                        Toast.makeText(MainActivity.this, response.getString("msg") +"!", Toast.LENGTH_SHORT).show();
                        Log.e("Error", response.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue1.add(request);
    }

    private void addUser() {
        RequestQueue requestQueue1= Volley.newRequestQueue(MainActivity.this);
        String username=edt_username.getText().toString().trim();
        String email=edt_email.getText().toString().trim();
        String password=edt_password.getText().toString().trim();
        String authToken= UUID.randomUUID().toString();
        String url="https://healthy-me-rest-api.herokuapp.com/saveuser2?firstName="+username+"&email="+email+"&password="+password;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject= null;
                try {
                    if(response.getInt("status")==200) {
                        jsonObject = (JSONObject) response.getJSONObject("data");
                        String fname = jsonObject.getString("firstName");
                        String femail = jsonObject.getString("email");
                        String fpassword = jsonObject.getString("password");
                        Log.e("Response", response.toString());
                        Log.e("Signup Response ellaborated", fname + "--" + femail + "--" + fpassword);
                        Toast.makeText(MainActivity.this, "Login Now!", Toast.LENGTH_SHORT).show();
                        edt_username.setVisibility(View.GONE);
                        txt_heading.setText("Login");
                        txt_down.setText("Create an Account?");
                        isSigningUp=true;
                        btn_signup.setText("LogIn");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue1.add(request);
    }
}

