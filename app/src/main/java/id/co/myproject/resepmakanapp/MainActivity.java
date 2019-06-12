package id.co.myproject.resepmakanapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import id.co.myproject.resepmakanapp.Adapter.Dashboard.HomeAdapter;
import id.co.myproject.resepmakanapp.Adapter.Dashboard.SliderAdapter;
import id.co.myproject.resepmakanapp.Api.Api;
import id.co.myproject.resepmakanapp.Model.Home;
import id.co.myproject.resepmakanapp.Model.Kategori;
import id.co.myproject.resepmakanapp.Model.Makanan;
import id.co.myproject.resepmakanapp.Model.TipsTrick;
import id.co.myproject.resepmakanapp.Util.CustomVolleyRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int NUM_PAGES = 0;
    String url = "http://192.168.43.22/resepadmin/_api/kategori.php";
    String url_makan = "http://192.168.43.22/resepadmin/_api/makanan.php";
    RecyclerView rv_home;
    List<Kategori> kategoriList;
    List<Makanan> makananList;
    List<Home> homeList;
    List<Makanan> bannerList;
    List<TipsTrick> tipsTrickList;
    HomeAdapter adapter;
    FrameLayout frameLayout;
    HashMap<String, String> image_list;

    ProgressDialog progressDialog;

    private static int currentPage = 0;

    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    ViewPager viewPager;
    SliderAdapter sliderAdapter;
    boolean cek1 = false, cek2 = false, cek3 = false, cek4 = false;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        rv_home = findViewById(R.id.home_recycler);
        homeList = new ArrayList<>();
        makananList = new ArrayList<>();
        kategoriList = new ArrayList<>();
        bannerList = new ArrayList<>();
        image_list = new HashMap<>();
        tipsTrickList = new ArrayList<>();
        loadDataBanner();
        loadDataKategori();
        loadDataMakanan();
        loadDataTips();
        homeList.add(new Home(1, kategoriList));
        homeList.add(new Home(2, 0, makananList));
        homeList.add(new Home(3, 0,"", tipsTrickList));
        adapter = new HomeAdapter(homeList, MainActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_home.setLayoutManager(layoutManager);
        rv_home.setAdapter(adapter);

        image_list = new HashMap<>();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadDataTips() {
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
                        cek4 = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                cek4 = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataMakanan();
    }

    private void loadDataMakanan() {
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
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "onResponseMaknana: "+makananList.size());

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(jsonArrayRequest1);
    }

    private void loadDataKategori() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Api.URL_KATEGORI, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Kategori kategori = new Kategori();
                        JSONObject jsonObject = response.getJSONObject(i);
                        kategori.setId(jsonObject.getString("id_kategori"));
                        kategori.setNama_kategori(jsonObject.getString("nama_kategori"));
                        kategori.setGambar_kategori(jsonObject.getString("gambar"));
                        kategoriList.add(kategori);
                        cek2 = true;
                    } catch (JSONException e) {
                        Log.e("Volley", e.getMessage().toString());
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
                cek2 = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    private void loadDataBanner() {
        JsonArrayRequest jsonArrayRequestBanners = new JsonArrayRequest(Request.Method.GET, Api.URL_MAKANAN, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){

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
                        cek1 = true;
                        bannerList.add(makanan);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                sliderAdapter = new SliderAdapter(bannerList, MainActivity.this);

                viewPager.setAdapter(sliderAdapter);

                dotscount = sliderAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(MainActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));
                NUM_PAGES =bannerList.size();

                // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 1000, 3000);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequestBanners);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialog_rating = inflater.inflate(R.layout.about_layout, null);
            builder.setView(dialog_rating);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_makanan) {
            Intent intent = new Intent(MainActivity.this, MakananActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_kategori){
            Intent intent = new Intent(this, KategoriActivity.class);
            intent.putExtra("code", 2);
            startActivity(intent);
        } else if (id == R.id.nav_tips){
            Intent intent = new Intent(this, TipsTrickListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
