package id.co.myproject.resepmakanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.co.myproject.resepmakanapp.Adapter.Dashboard.MakananKategoriAdapter;
import id.co.myproject.resepmakanapp.Api.Api;
import id.co.myproject.resepmakanapp.Model.Makanan;

public class MakananActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Makanan> makananList;
    MakananKategoriAdapter adapter;
    ImageView iv_back;
    FrameLayout frameLayout;
    EditText edt_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan2);
        recyclerView = findViewById(R.id.rv_makanan);
        makananList = new ArrayList<>();
        iv_back = findViewById(R.id.back);
        edt_search = findViewById(R.id.edt_cari);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakananActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
//        Toast.makeText(getActivity(), ""+Common.id_kategori, Toast.LENGTH_SHORT).show();

    }

    private void loadData(){
        makananList.clear();
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Api.URL_MAKANAN, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Makanan makanan = new Makanan(
                                jsonObject.getString("id_makanan"),
                                jsonObject.getString("nama_makanan"),
                                jsonObject.getString("gambar"),
                                jsonObject.getString("deskripsi"),
                                jsonObject.getString("video"),
                                jsonObject.getString("resep"),
                                jsonObject.getString("rating"),
                                jsonObject.getString("total_pencet")
                        );
                        makananList.add(makanan);
                    } catch (JSONException e) {
                        Log.e("Volley", e.getMessage().toString());
                        Toast.makeText(MakananActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(MakananActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        Log.d(TAG, "onResponseMaknana: "+makananList.size());

        RequestQueue requestQueue1 = Volley.newRequestQueue(MakananActivity.this);
        requestQueue1.add(jsonArrayRequest1);


        adapter = new MakananKategoriAdapter(makananList, MakananActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MakananActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onPause() {
        super.onPause();
        makananList.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void filter(String text) {
        makananList.clear();
        adapter.notifyDataSetChanged();
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Api.URL_MAKANAN+"?"+"cari="+text, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Makanan makanan = new Makanan(
                                jsonObject.getString("id_makanan"),
                                jsonObject.getString("nama_makanan"),
                                jsonObject.getString("gambar"),
                                jsonObject.getString("deskripsi"),
                                jsonObject.getString("video"),
                                jsonObject.getString("resep"),
                                jsonObject.getString("rating"),
                                jsonObject.getString("total_pencet")
                        );
                        makananList.add(makanan);
                    } catch (JSONException e) {
                        Log.e("Volley", e.getMessage().toString());
                        Toast.makeText(MakananActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(MakananActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        Log.d(TAG, "onResponseMaknana: "+makananList.size());

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(jsonArrayRequest1);


        adapter = new MakananKategoriAdapter(makananList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

    }
}
