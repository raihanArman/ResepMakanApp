package id.co.myproject.resepmakanapp.Adapter.Dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.co.myproject.resepmakanapp.Fragment.Kategori.KetegoriFragment;
import id.co.myproject.resepmakanapp.Model.Kategori;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.Util.Common;

import android.support.v4.app.Fragment;

import com.squareup.picasso.Picasso;

public class KategoriMoreAdapter extends RecyclerView.Adapter<KategoriMoreAdapter.ViewHolder> {

    List<Kategori> kategoriList;
    Context context;

    public KategoriMoreAdapter(List<Kategori> kategoriList, Context context) {
        this.kategoriList = kategoriList;
        this.context = context;
    }

    @NonNull
    @Override
    public KategoriMoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kategori_more_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final KategoriMoreAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tv_nama_kategori.setText(kategoriList.get(i).getNama_kategori());
        Picasso.with(context).load(kategoriList.get(i).getGambar_kategori()).into(viewHolder.iv_kategori);
        viewHolder.tv_jumlah_makanan.setText(kategoriList.get(i).getJumlah_makanan());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("key", "1");
                String aa[] = kategoriList.get(i).getJumlah_makanan().split(" ");
                if (Integer.parseInt(aa[0]) > 0) {
                    Common.id_kategori = kategoriList.get(i).getId();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new KetegoriFragment();
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_kategori, fragment).addToBackStack(null).commit();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Belum ada makanan di kategori ini");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_kategori;
        TextView tv_nama_kategori;
        TextView tv_jumlah_makanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_kategori = itemView.findViewById(R.id.iv_kategori);
            tv_nama_kategori = itemView.findViewById(R.id.tv_kategori);
            tv_jumlah_makanan = itemView.findViewById(R.id.tv_jumlah_makanan);
        }
    }
}
