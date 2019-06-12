package id.co.myproject.resepmakanapp.Adapter.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;

import id.co.myproject.resepmakanapp.KategoriActivity;
import id.co.myproject.resepmakanapp.Model.Home;
import id.co.myproject.resepmakanapp.Model.Kategori;
import id.co.myproject.resepmakanapp.Model.Makanan;
import id.co.myproject.resepmakanapp.Model.TipsTrick;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.MakananActivity;
import id.co.myproject.resepmakanapp.TipsTrickListActivity;

public class HomeAdapter extends RecyclerView.Adapter {

    List<Home> homeList;
    Context context;

    public HomeAdapter(List<Home> homeList, Context context) {
        this.homeList = homeList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homeList.get(position).getType()){
            case 0 : return Home.BANNER_LIST;
            case 1 : return Home.KATEGORI_LIST;
            case 2 : return Home.MAKANAN_LIST;
            case 3 : return Home.TIPS_LIST;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case Home.BANNER_LIST:
                View bannerView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_banner_layout, viewGroup, false);
                return new BannerSliderViewHolder(bannerView);
            case Home.KATEGORI_LIST:
                View kategoriView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kategori_layout, viewGroup, false);
                return new kategoriListViewHolder(kategoriView);
            case Home.MAKANAN_LIST:
                View makananView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.makanan_layout, viewGroup, false);
                return new MakananListViewHolder(makananView);
            case Home.TIPS_LIST:
                View tipsView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tips_trick_layout, viewGroup, false);
                return new TipsViewHolder(tipsView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (homeList.get(i).getType()){
            case Home.BANNER_LIST:
                List<Makanan> bannerList = homeList.get(i).getBannerList();
                ((BannerSliderViewHolder)viewHolder).setBannerSliderViewPager(bannerList);
                break;
            case Home.KATEGORI_LIST:
                List<Kategori> kategoriList = homeList.get(i).getKategoriList();
                ((kategoriListViewHolder)viewHolder).setKategoriList(kategoriList);
                break;
            case Home.MAKANAN_LIST:
                List<Makanan> makananList = homeList.get(i).getMakananList();
                ((MakananListViewHolder)viewHolder).setMakananList(makananList);
                break;
            case Home.TIPS_LIST:
                List<TipsTrick> tipsTrickList = homeList.get(i).getTipsTrickList();
                ((TipsViewHolder)viewHolder).setTipsList(tipsTrickList);
                break;
            default:return;
        }
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager bannerSliderViewPager;
        LinearLayout sliderDotspanel;
        private ImageView[] dots;
        private int dotscount;
        private int currentPage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIODE_TIME = 3000;
        private List<Makanan> arrangedList;
        SliderAdapter adapter;
        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager);
            sliderDotspanel = (LinearLayout) itemView.findViewById(R.id.SliderDots);
        }

        private void setBannerSliderViewPager(final List<Makanan> sliderModelList){
            bannerSliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    for(int i = 0; i< dotscount; i++){
                        dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_selector));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            adapter = new SliderAdapter(sliderModelList, context);

            bannerSliderViewPager.setAdapter(adapter);

            dotscount = adapter.getCount();
            dots = new ImageView[dotscount];

            for(int i = 0; i < dotscount; i++){

                dots[i] = new ImageView(context);
                dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                sliderDotspanel.addView(dots[i], params);

            }

        }


    }


    class kategoriListViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv_kategori_list;
        private TextView tv_lihat_lainnya;
        public kategoriListViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_kategori_list = itemView.findViewById(R.id.home_kategori_recycler);
            tv_lihat_lainnya = itemView.findViewById(R.id.tv_lihat_lainnya);
        }
        public void setKategoriList(List<Kategori> kategoriList){
            if (kategoriList.size() > 4) {
                tv_lihat_lainnya.setVisibility(View.VISIBLE);
                tv_lihat_lainnya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, KategoriActivity.class);
                        intent.putExtra("code", 2);
                        context.startActivity(intent);
                    }
                });
            }else {
                tv_lihat_lainnya.setVisibility(View.INVISIBLE);
            }
            ItemCategoryAdapter itemCategoryAdapter = new ItemCategoryAdapter(kategoriList, context);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_kategori_list.setLayoutManager(layoutManager);
            rv_kategori_list.setAdapter(itemCategoryAdapter);
            itemCategoryAdapter.notifyDataSetChanged();
        }
    }

    class MakananListViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv_makanan_list;
        private TextView tv_lainnya;
        public MakananListViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_makanan_list = itemView.findViewById(R.id.home_makanan_recycler);
            tv_lainnya = itemView.findViewById(R.id.tv_lihat_lainnya);
        }
        public void setMakananList(List<Makanan> makananList){
            if (makananList.size() > 8){
                tv_lainnya.setVisibility(View.VISIBLE);
                tv_lainnya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, MakananActivity.class);
                        context.startActivity(intent);
                    }
                });
            }

            ItemMakananAdapter itemMakananAdapter = new ItemMakananAdapter(makananList, context);
            rv_makanan_list.setLayoutManager(new GridLayoutManager(context, 2));
            rv_makanan_list.setAdapter(itemMakananAdapter);
            itemMakananAdapter.notifyDataSetChanged();
        }
    }

    class TipsViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv_tips;
        private TextView tv_lainnya;
        public TipsViewHolder(View itemView) {
            super(itemView);
            rv_tips = itemView.findViewById(R.id.rv_tips_trick);
            tv_lainnya = itemView.findViewById(R.id.tv_lihat_lainnya);
        }
        public void setTipsList(List<TipsTrick> tipsTrickList){
            if (tipsTrickList.size() > 3){
                tv_lainnya.setVisibility(View.VISIBLE);
                tv_lainnya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TipsTrickListActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
            TipsTrickAdapter adapter = new TipsTrickAdapter(tipsTrickList, context);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_tips.setLayoutManager(layoutManager);
            rv_tips.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
