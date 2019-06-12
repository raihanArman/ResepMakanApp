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

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.myproject.resepmakanapp.MakananDetailActivity;
import id.co.myproject.resepmakanapp.Model.Makanan;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.Util.Common;

public class ItemMakananAdapter extends RecyclerView.Adapter<ItemMakananAdapter.ViewHolder> {

    List<Makanan> makananList;
    Context context;

    public ItemMakananAdapter(List<Makanan> makananList, Context context) {
        this.makananList = makananList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemMakananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_makanan_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMakananAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tv_nama_makanan.setText(makananList.get(i).getNama_makanan());
        Picasso.with(context).load(makananList.get(i).getGambar()).into(viewHolder.iv_makanan);
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
        if (makananList.size() > 8)
            return 8;
        else
            return makananList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_makanan;
        TextView tv_nama_makanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_makanan = itemView.findViewById(R.id.iv_makanan);
            tv_nama_makanan = itemView.findViewById(R.id.tv_makanan);
        }
    }
}
