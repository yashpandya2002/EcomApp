package com.example.ecommerceapp1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.graphics.pdf.PdfDocument;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductBaseAdapter extends BaseAdapter {
    ArrayList<AllProducts> arrayList;
    Context context;

    public ProductBaseAdapter(ArrayList<AllProducts> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public AllProducts getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.activity_product_layout_raw, null);
        String name = arrayList.get(i).getName();
        int price = arrayList.get(i).getPrice();
        int productId = arrayList.get(i).getProductID();
        TextView txt_name = view.findViewById(R.id.product_name);
        TextView txt_price = view.findViewById(R.id.product_price);
//        TextView txt_id=view.findViewById(R.id.product_id);
        ImageView imageView = view.findViewById(R.id.product_image);
        txt_name.setText(name);
        txt_price.setText(String.valueOf(price));
        Button btn_add = view.findViewById(R.id.btn_add);
        Button btn_delete = view.findViewById(R.id.btn_delete);

        SharedPreferences sharedPreferences = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("authToken", "");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Task added", name + " added!");
                RequestQueue requestQueue = Volley.newRequestQueue(context);


                String url = "https://healthy-me-rest-api.herokuapp.com/addtocart/" + authToken + "/" + String.valueOf(productId);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray;
                        try {
                            if (response.getInt("status") == 200) {
                                Log.e("Added to cart online", name + productId);
                            } else {
                                Toast.makeText(context, response.getString("msg") + "!", Toast.LENGTH_SHORT).show();
                                Log.e("Error", response.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });


                requestQueue.add(request);

                RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                String url1 = "https://healthy-me-rest-api.herokuapp.com/carts/" + authToken;
                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray;

                        try {
                            if (response.getInt("status") == 200) {

                                jsonArray = response.getJSONArray("data");
                                int length = jsonArray.length();
                                Log.e("authToken", authToken);
                                Log.e("Json array for ", name + jsonArray.toString());
                                Log.e("Add clicked and Length of array is", String.valueOf(length));

//                                Page2.updateCount();
//

                            } else {
                                Toast.makeText(context, response.getString("msg") + "!", Toast.LENGTH_SHORT).show();
                                Log.e("Error", response.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                });

                requestQueue1.add(request1);
            }

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String url1 = "https://healthy-me-rest-api.herokuapp.com/carts/" + authToken;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    JSONArray jsonArray;

                    try {
                        if (response.getInt("status") == 200) {

                            jsonArray = response.getJSONArray("data");
                            int length = jsonArray.length();
                            Page2.updateCount(length);
//

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }

            });

        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Task added", name + " deleted!");
                final boolean[] itemFound = {false};
                final int[] cartId = new int[1];
                RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                String url1 = "https://healthy-me-rest-api.herokuapp.com/carts/" + authToken;
                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray;

                        try {
                            if (response.getInt("status") == 200) {

                                jsonArray = response.getJSONArray("data");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                                    if(productId==jsonObject.getInt("productId"))
                                    {
                                        cartId[0] =jsonObject.getInt("cartId");
                                        itemFound[0] =true;
                                        break;
                                    }
                                }
                                int length = jsonArray.length();
                                Log.e("authToken", authToken);
                                Log.e("Json array for ", name + jsonArray.toString());
                                Log.e("Delete clicked and Length of array is", String.valueOf(length));

//

                            } else {
                                Toast.makeText(context, response.getString("msg") + "!", Toast.LENGTH_SHORT).show();
                                Log.e("Error", response.getString("msg"));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(!itemFound[0])
                        {
                            Log.e("",name +" with id"+productId+" not found");
                        }
                        else
                        {
                            Log.e("",name +" with id"+productId+" found with cart ID "+cartId[0]);
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                });
                requestQueue1.add(request1);

                //Code for deleting of a product using its cart id
                RequestQueue requestQueue= Volley.newRequestQueue(context);
                String url="https://healthy-me-rest-api.herokuapp.com/carts/"+cartId[0];
                JsonObjectRequest request=new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(JSONObject response){

                        JSONArray jsonArray;

                        try {
                            if(response.getInt("status")==200) {


                                Log.e("Deleted item from api","Cart id:"+String.valueOf(cartId[0])+" and product deleted:"+name);

//

                            }

                            else
                            {
                                Toast.makeText(context, response.getString("msg") +"!", Toast.LENGTH_SHORT).show();
                                Log.e("Error", response.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                });
                requestQueue.add(request);



            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Clicked",name+" clicked!");
            }
        });
//        txt_id.setText(String.valueOf(productId));
        return view;
    }
}
