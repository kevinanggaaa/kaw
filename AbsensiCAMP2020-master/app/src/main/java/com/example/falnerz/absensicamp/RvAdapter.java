package com.example.falnerz.absensicamp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.falnerz.absensicamp.server.PesertaModel;

import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder>{
    private List<PesertaModel> peserta;
    Context context;
    private List<PesertaModel> itemCopy = new ArrayList<>();
    private String kondisi;

    public RvAdapter(Context context){
        this.context = context;
    }

    public void setGangguan(List<PesertaModel> peserta, String eventya){
        this.peserta = peserta;
        this.kondisi = eventya;
        if (itemCopy.isEmpty()){
            this.itemCopy.addAll(peserta);
        }
        //notifyDataSetChanged();
    }

    public void filter(String text){
        if (text.isEmpty()){
            clear();
            peserta.addAll(itemCopy);
        }else{
            clear();
            text = text.toLowerCase();
            for (PesertaModel pesert : itemCopy){
                if ((pesert.getNrp().toLowerCase().contains(text))){
                    peserta.add(pesert);
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absen, parent, false);
        return new ViewHolder(view);
    }

    public List<PesertaModel> checker(){
        return peserta;
    }
    public void clear() { peserta.clear(); }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PesertaModel satu = peserta.get(position);
        if (kondisi.equals("opening")){
            if (satu.getOpening().equals("1")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.GREEN);
            }else{
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.RED);
            }
        }else if(kondisi.equals("closing")){
            if (satu.getClosing().equals("1")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.GREEN);
            }else if(satu.getClosing().equals("0")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.RED);
            }
        }
        else if(kondisi.equals("worship_night")){
            if (satu.getWorshipNight().equals("1")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.GREEN);
            }else if(satu.getWorshipNight().equals("0")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.RED);
            }
        }
        else if(kondisi.equals("ibadah_minggu")){
            if (satu.getIbadahMinggu().equals("1")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.GREEN);
            }else if(satu.getIbadahMinggu().equals("0")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.RED);
            }
        }
        else if(kondisi.equals("sesi_1")){
            if (satu.getSesi1().equals("1")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.GREEN);
            }else if(satu.getSesi1().equals("0")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.RED);
            }
        }
        else if(kondisi.equals("sesi_2")){
            if (satu.getSesi2().equals("1")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.GREEN);
            }else if(satu.getSesi2().equals("0")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.RED);
            }
        }
        else if(kondisi.equals("sesi_3")){
            if (satu.getSesi3().equals("1")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.GREEN);
            }else if(satu.getSesi3().equals("0")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.RED);
            }
        }
        else if(kondisi.equals("sesi_4")){
            if (satu.getSesi4().equals("1")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.GREEN);
            }else if(satu.getSesi4().equals("0")){
                holder.nrp.setText(satu.getNrp());
                holder.nrp.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public int getItemCount() {
        return peserta.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nrp;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nrp = itemView.findViewById(R.id.nrp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
//                        PesertaModel amp = peserta.get(position);
//                        Intent i = new Intent(context, DetailGangguan.class);
//                        i.putExtra("ini_kunci", amp.getKode_pesertan());
//                        context.startActivity(i);
                    }
                }
            });
        }
    }
}
