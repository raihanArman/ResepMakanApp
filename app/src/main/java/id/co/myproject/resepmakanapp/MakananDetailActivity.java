package id.co.myproject.resepmakanapp;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.co.myproject.resepmakanapp.Api.Api;
import id.co.myproject.resepmakanapp.Database.Database;
import id.co.myproject.resepmakanapp.Fragment.Makanan.DeskripsiFragment;
import id.co.myproject.resepmakanapp.Fragment.Makanan.ResepFragment;
import id.co.myproject.resepmakanapp.Fragment.YouTubePlayFragment;
import id.co.myproject.resepmakanapp.Util.Common;

public class MakananDetailActivity extends AppCompatActivity implements RatingDialogListener {

    int item;
    Toolbar toolbar;
    TabLayout tabs;
    ViewPager pager;
    ViewPagerAdapter adapter;
    ImageView iv_makanan;
    FloatingActionButton btn_rating;
    YouTubePlayerView youTubePlayerView;
    Button btn_play;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    RatingBar ratingBar;

    Database localDB;

    float rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan);
        adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        tabs = (TabLayout) findViewById(R.id.tab_layout);
        pager = (ViewPager) findViewById(R.id.pager);
        iv_makanan = findViewById(R.id.iv_makanan);
        btn_rating = findViewById(R.id.btnRating);
        btn_play = findViewById(R.id.btn_play_video);
        item = pager.getCurrentItem();

        localDB = new Database(this);


        if (localDB.cekRating(Common.common_makanan.getId_makanan())) {
            btn_rating.setImageResource(R.drawable.ic_star_black_24dp);
        }else {
            btn_rating.setImageResource(R.drawable.ic_star_border_black_24dp);
            btn_rating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shoeDialogRating();
                }
            });
        }
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                YouTubePlayFragment fragment = new YouTubePlayFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.youtube_fragment, fragment)
                        .addToBackStack(null)
                        .commit();
                btn_play.setVisibility(View.INVISIBLE);
            }
        });
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Picasso.with(this).load(Common.common_makanan.getGambar()).into(iv_makanan);


    }

    private void shoeDialogRating() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Beri rating");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialog_rating = inflater.inflate(R.layout.dialog_rating, null);
        builder.setView(dialog_rating);
        ratingBar = (AppCompatRatingBar)dialog_rating.findViewById(R.id.ratingBar);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateRating(ratingBar);
                pager.setAdapter(adapter);
                tabs.setupWithViewPager(pager);
                localDB.tambahRating(Common.common_makanan.getId_makanan(), String.valueOf(rate));
                btn_rating.setImageResource(R.drawable.ic_star_black_24dp);
                pager.getAdapter().notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void updateRating(RatingBar ratingBar) {
        final float rate = ratingBar.getRating();
        StringRequest request = new StringRequest(Request.Method.POST, Api.URL_RATING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(MakananDetailActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MakananDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MakananDetailActivity.this, "Gagal Update", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("rating", String.valueOf(rate));
                map.put("id_makanan", Common.common_makanan.getId_makanan());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        if (item == 0)
            pager.setCurrentItem(0);
        else if (item == 1)
            pager.setCurrentItem(1);
    }

    public void loadData(){
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Api.URL_MAKANAN+"?id_makanan="+Common.common_makanan.getId_makanan(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        rate = Float.parseFloat(jsonObject.getString("rating"));
                        ratingBar.setRating(rate);
                    } catch (JSONException e) {
                        Log.e("Volley", e.getMessage().toString());
                        Toast.makeText(MakananDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(MakananDetailActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        Log.d(TAG, "onResponseMaknana: "+makananList.size());

        RequestQueue requestQueue1 = Volley.newRequestQueue(MakananDetailActivity.this);
        requestQueue1.add(jsonArrayRequest1);
    }


    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String s) {

    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> title;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            fragments.add(new DeskripsiFragment());
            fragments.add(new ResepFragment());

            title = new ArrayList<>();
            title.add("Deskripsi");
            title.add("Resep");
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (btn_play.getVisibility() == View.INVISIBLE){
            btn_play.setVisibility(View.VISIBLE);
        }
    }
}
