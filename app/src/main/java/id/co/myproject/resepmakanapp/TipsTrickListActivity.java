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

import id.co.myproject.resepmakanapp.Adapter.Dashboard.TipsTrickAdapter;
import id.co.myproject.resepmakanapp.Adapter.Dashboard.TipsTrickListAdapter;
import id.co.myproject.resepmakanapp.Api.Api;
import id.co.myproject.resepmakanapp.Model.TipsTrick;

public class TipsTrickListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<TipsTrick> tipsTrickList;
    TipsTrickListAdapter adapter;
    ImageView iv_back;
    EditText edt_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_trick_list);
        recyclerView = findViewById(R.id.rv_tips_list);
        tipsTrickList = new ArrayList<>();
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
                Intent intent = new Intent(TipsTrickListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        JsonArrayRequest request = new JsonArrayRequest(Api.URL_TIPS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        TipsTrick tipsTrick = new TipsTrick(
                                jsonObject.getString("id_tips"),
                                jsonObject.getString("judul"),
                                jsonObject.getString("isi"),
                                jsonObject.getString("gambar")
                        );
                        tipsTrickList.add(tipsTrick);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        adapter = new TipsTrickListAdapter(tipsTrickList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(TipsTrickListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    private void filter(String text) {
        tipsTrickList.clear();
        adapter.notifyDataSetChanged();
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Api.URL_TIPS+"?"+"cari="+text, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        TipsTrick tipsTrick = new TipsTrick(
                                jsonObject.getString("id_tips"),
                                jsonObject.getString("judul"),
                                jsonObject.getString("isi"),
                                jsonObject.getString("gambar")
                        );
                        tipsTrickList.add(tipsTrick);
                    } catch (JSONException e) {
                        Log.e("Volley", e.getMessage().toString());
                        Toast.makeText(TipsTrickListActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(TipsTrickListActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        Log.d(TAG, "onResponseMaknana: "+makananList.size());

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(jsonArrayRequest1);


        adapter = new TipsTrickListAdapter(tipsTrickList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

    }


}
