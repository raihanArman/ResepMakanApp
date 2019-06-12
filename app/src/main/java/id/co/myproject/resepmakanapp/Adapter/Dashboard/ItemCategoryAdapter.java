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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.myproject.resepmakanapp.KategoriActivity;
import id.co.myproject.resepmakanapp.Model.Kategori;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.Util.Common;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.ViewHolder> {

    List<Kategori> kategoriList;
    Context context;

    public ItemCategoryAdapter(List<Kategori> kategoriList, Context context) {
        this.kategoriList = kategoriList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_kategori_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemCategoryAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tv_nama_kategori.setText(kategoriList.get(i).getNama_kategori());
        viewHolder.tv_id_kategori.setText(kategoriList.get(i).getId());
        Glide.with(context).load(kategoriList.get(i).getGambar_kategori()).into(viewHolder.iv_gambar_kategori);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, KategoriActivity.class);
                intent.putExtra("code", 1);
                Common.id_kategori = kategoriList.get(i).getId();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (kategoriList.size() > 4)
            return 4;
        else
            return kategoriList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nama_kategori;
        TextView tv_id_kategori;
        ImageView iv_gambar_kategori;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id_kategori = itemView.findViewById(R.id.tv_id_kategori);
            tv_nama_kategori = itemView.findViewById(R.id.tv_kategori);
            iv_gambar_kategori = itemView.findViewById(R.id.iv_kategori);
        }
    }
}
