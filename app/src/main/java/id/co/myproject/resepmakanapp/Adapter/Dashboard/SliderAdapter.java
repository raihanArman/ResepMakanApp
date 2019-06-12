package id.co.myproject.resepmakanapp.Adapter.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.myproject.resepmakanapp.MakananDetailActivity;
import id.co.myproject.resepmakanapp.Model.Makanan;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.Util.Common;
import id.co.myproject.resepmakanapp.Util.CustomVolleyRequest;

public class SliderAdapter extends PagerAdapter {

    private List<Makanan> bannerList;
    Context context;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;

    public SliderAdapter(List<Makanan> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout, container, false);
        ImageView banner = view.findViewById(R.id.banner_slider);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(bannerList.get(position).getGambar(), ImageLoader.getImageListener(banner, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        ViewPager vp = (ViewPager) container;
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MakananDetailActivity.class);
                Common.common_makanan = bannerList.get(position);
                context.startActivity(intent);
            }
        });
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
