package id.co.myproject.resepmakanapp.Adapter.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.myproject.resepmakanapp.MakananDetailActivity;
import id.co.myproject.resepmakanapp.Model.Makanan;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.Util.Common;

public class MakananKategoriAdapter extends RecyclerView.Adapter<MakananKategoriAdapter.ViewHolder> {
    List<Makanan> makananList;
    Context context;

    public MakananKategoriAdapter(List<Makanan> makananList, Context context) {
        this.makananList = makananList;
        this.context = context;
    }

    @NonNull
    @Override
    public MakananKategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.makanan_kategori_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MakananKategoriAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tv_makanan_kategori.setText(makananList.get(i).getNama_makanan());
        Picasso.with(context).load(makananList.get(i).getGambar()).into(viewHolder.iv_gambar_makanan_kategori);
        viewHolder.tv_rating.setText(makananList.get(i).getRating());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MakananDetailActivity.class);
                Common.common_makanan = makananList.get(i);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return makananList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_makanan_kategori;
        ImageView iv_gambar_makanan_kategori;
        TextView tv_rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_makanan_kategori = itemView.findViewById(R.id.tv_makanan_kategori);
            iv_gambar_makanan_kategori = itemView.findViewById(R.id.iv_makanan_kategori);
        }
    }
}
