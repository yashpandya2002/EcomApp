package com.example.ecommerceapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
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

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.UUID;

public class Page2 extends AppCompatActivity {


//    private static BreakIterator txt_cartCount;
    GridView gridView;
MenuItem menuItem;
String authToken;
 static TextView txt_cartCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        gridView=findViewById(R.id.grid_view);
        RequestQueue requestQueue= Volley.newRequestQueue(Page2.this);

        Intent i=getIntent();
        authToken=i.getStringExtra("authToken").toString();
        String url="https://healthy-me-rest-api.herokuapp.com/products";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(JSONObject response){
                ArrayList<AllProducts> arrayList=new ArrayList<AllProducts>();
                JSONArray jsonArray;
                String message = null;
                try {
                    if(response.getInt("status")==200) {

                        jsonArray = response.getJSONArray("data");
                        for(int j=0;j<jsonArray.length();j++)
                        {
                            JSONObject jsonObject=(JSONObject) jsonArray.getJSONObject(j);
                            arrayList.add(new AllProducts(jsonObject.getString("name"), jsonObject.getInt("qty"), jsonObject.getInt("price"), jsonObject.getInt("productId")));
                            Log.e("Name",jsonObject.getString("name"));


                        }
                        ProductBaseAdapter productBaseAdapter=new ProductBaseAdapter(arrayList,Page2.this);
                        gridView.setAdapter(productBaseAdapter);
//

                    }

                    else
                    {
                        Toast.makeText(Page2.this, response.getString("msg") +"!", Toast.LENGTH_SHORT).show();
                        Log.e("Error", response.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Page2.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cartcountmenu,menu);
        menuItem=menu.findItem(R.id.menu_cartcount);
        View view=menuItem.getActionView();
        if(view!=null) {
            txt_cartCount = view.findViewById(R.id.cart_count_number);
        }

        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_cartcount)
        {
            Log.e("Menu clicked Clicked",String.valueOf(item.getItemId()));
        }

        return super.onOptionsItemSelected(item);
    }
    public static void updateCount(int length)
    {



        if(length !=0 && txt_cartCount!=null) {
            txt_cartCount.setText(String.valueOf(length));
        }
        else if(length ==0 && txt_cartCount!=null)
        {
            txt_cartCount.setVisibility(View.GONE);
        }
    }


}