package id.co.myproject.resepmakanapp.Fragment.Makanan;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.myproject.resepmakanapp.Api.Api;
import id.co.myproject.resepmakanapp.MakananActivity;
import id.co.myproject.resepmakanapp.Model.Makanan;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.Util.Common;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeskripsiFragment extends Fragment {

    TextView tv_dekripsi, tv_rating, tv_feedback;
    public DeskripsiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deskripsi, container, false);
        tv_dekripsi = view.findViewById(R.id.tv_dekripsi);
        tv_rating = view.findViewById(R.id.tv_rating);
        tv_feedback = view.findViewById(R.id.tv_feedback);

        tv_dekripsi.setText(Common.common_makanan.getDeskripsi());
        tv_rating.setText(Common.common_makanan.getRating());
        tv_feedback.setText(Common.common_makanan.getFeedback());
        return view;
    }
}
