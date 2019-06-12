package id.co.myproject.resepmakanapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.co.myproject.resepmakanapp.Util.Common;

public class TipsTrickActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView iv_tips;
    TextView tv_judul;
    TextView tv_isi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_trick);
        iv_tips = findViewById(R.id.iv_tips);
        tv_judul = findViewById(R.id.tv_judul);
        tv_isi = findViewById(R.id.tv_isi);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setTitle("Tips dan Trik");

        tv_judul.setText(Common.common_tips.getJudul());
        tv_isi.setText(Common.common_tips.getIsi());
        Picasso.with(this).load(Common.common_tips.getGambar()).into(iv_tips);
    }
}
