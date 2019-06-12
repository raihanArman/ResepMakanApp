package id.co.myproject.resepmakanapp.Fragment.Kategori;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

import id.co.myproject.resepmakanapp.Adapter.Dashboard.KategoriMoreAdapter;
import id.co.myproject.resepmakanapp.Api.Api;
import id.co.myproject.resepmakanapp.MainActivity;
import id.co.myproject.resepmakanapp.Model.Kategori;
import id.co.myproject.resepmakanapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KategoriMoreFragment extends Fragment {


    RecyclerView recyclerView;
    List<Kategori> kategoriList;
    KategoriMoreAdapter adapter;
    ImageView iv_back;
    public KategoriMoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kategori_more, container, false);
        recyclerView = view.findViewById(R.id.rv_kategori_more);
        iv_back = view.findViewById(R.id.back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        kategoriList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Api.URL_KATEGORI, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Kategori kategori = new Kategori();
                        kategori.setId(jsonObject.getString("id_kategori"));
                        kategori.setNama_kategori(jsonObject.getString("nama_kategori"));
                        kategori.setGambar_kategori(jsonObject.getString("gambar"));
                        kategori.setJumlah_makanan(jsonObject.getString("total"));
                        kategoriList.add(kategori);
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

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonArrayRequest);

        adapter = new KategoriMoreAdapter(kategoriList, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
