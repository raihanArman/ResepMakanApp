package id.co.myproject.resepmakanapp.Fragment.Kategori;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
import id.co.myproject.resepmakanapp.MainActivity;
import id.co.myproject.resepmakanapp.Model.Makanan;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.Util.Common;

/**
 * A simple {@link Fragment} subclass.
 */
public class KetegoriFragment extends Fragment {

    RecyclerView recyclerView;
    List<Makanan> makananList;
    MakananKategoriAdapter adapter;
    TextView tv_nama_kategori;
    ImageView iv_back;
    FrameLayout frameLayout;
    EditText edt_search;
    public KetegoriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ketegori, container, false);
        recyclerView = view.findViewById(R.id.rv_makanan_kategori);
        frameLayout = getActivity().findViewById(R.id.frame_kategori);
        makananList = new ArrayList<>();
        tv_nama_kategori = view.findViewById(R.id.tv_nama_kategori);
        iv_back = view.findViewById(R.id.back);
        edt_search = view.findViewById(R.id.edt_cari);
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
        final String back = getArguments().getString("key");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (back.equals("0")) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else if (back.equals("1")){
                    KategoriMoreFragment fragment = new KategoriMoreFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(frameLayout.getId(), fragment);
                    transaction.commit();
                }
            }
        });
//        Toast.makeText(getActivity(), ""+Common.id_kategori, Toast.LENGTH_SHORT).show();


//        loadData();
        return view;
    }

    private void filter(String text) {
        makananList.clear();
        adapter.notifyDataSetChanged();
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Api.URL_MAKANAN+"?id_kategori="+Common.id_kategori+"&cari="+text, new Response.Listener<JSONArray>() {
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
                        tv_nama_kategori.setText(jsonObject.getString("nama_kategori"));
                        makananList.add(makanan);
                    } catch (JSONException e) {
                        Log.e("Volley", e.getMessage().toString());
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        Log.d(TAG, "onResponseMaknana: "+makananList.size());

        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        requestQueue1.add(jsonArrayRequest1);


        adapter = new MakananKategoriAdapter(makananList, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    private void loadData(){
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Api.URL_MAKANAN+"?id_kategori="+Common.id_kategori, new Response.Listener<JSONArray>() {
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
                        tv_nama_kategori.setText(jsonObject.getString("nama_kategori"));
                        makananList.add(makanan);
                    } catch (JSONException e) {
                        Log.e("Volley", e.getMessage().toString());
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        Log.d(TAG, "onResponseMaknana: "+makananList.size());

        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        requestQueue1.add(jsonArrayRequest1);


        adapter = new MakananKategoriAdapter(makananList, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
}
