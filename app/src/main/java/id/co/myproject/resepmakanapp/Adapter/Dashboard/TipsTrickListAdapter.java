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

import id.co.myproject.resepmakanapp.Model.TipsTrick;
import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.TipsTrickActivity;
import id.co.myproject.resepmakanapp.Util.Common;

public class TipsTrickListAdapter extends RecyclerView.Adapter<TipsTrickListAdapter.ViewHolder> {
    List<TipsTrick> tipsTrickList;
    Context context;

    public TipsTrickListAdapter(List<TipsTrick> tipsTrickList, Context context) {
        this.tipsTrickList = tipsTrickList;
        this.context = context;
    }

    @NonNull
    @Override
    public TipsTrickListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tips_trick_layout_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipsTrickListAdapter.ViewHolder holder, final int position) {
        Picasso.with(context).load(tipsTrickList.get(position).getGambar()).into(holder.iv_tips);
        holder.tv_judul.setText(tipsTrickList.get(position).getJudul());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TipsTrickActivity.class);
                Common.common_tips = tipsTrickList.get(position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tipsTrickList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_tips;
        TextView tv_judul;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_tips = itemView.findViewById(R.id.iv_tips);
            tv_judul = itemView.findViewById(R.id.tv_judul);
        }
    }
}
