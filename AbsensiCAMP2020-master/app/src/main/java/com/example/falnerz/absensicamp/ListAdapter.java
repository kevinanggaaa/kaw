package com.example.falnerz.absensicamp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListAdapter extends BaseAdapter{

    private List<Pair<String,String>> datanya,datanya2;
    private List<String> toastnya,toastnya2;
    private LayoutInflater inflater=null;

    ListAdapter(Context showAct, List<Pair<String, String>> itemLV,List<String> toastItem){
        datanya=new ArrayList<>();
        toastnya=new ArrayList<>();
        datanya2=new ArrayList<>();
        toastnya2=new ArrayList<>();

        toastnya.addAll(toastItem);
        datanya.addAll(itemLV);
        toastnya2.addAll(toastItem);
        datanya2.addAll(itemLV);
        inflater = ( LayoutInflater ) showAct.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datanya.size();
    }

    @Override
    public Object getItem(int position) {
        String []temp = datanya.get(position).first.split("\n");
        return "Nama : "+toastnya.get(position)+"\nNRP : "+temp[1];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        @SuppressLint("ViewHolder") View rownya=inflater.inflate(R.layout.row_view,parent,false);
        TextView tv = rownya.findViewById(R.id.rowTextView);
        tv.setText(datanya.get(position).first);

        if(Objects.equals(datanya.get(position).second,"1")){
            tv.setTextColor(Color.GREEN);
        }
        else{
            tv.setTextColor(Color.RED);
        }
        return rownya;
    }

    public void filtering(CharSequence constraint) {
        int len = datanya2.size();
        datanya.clear();
        toastnya.clear();
        constraint = constraint.toString().toLowerCase();
        for (int i = 0; i < len; i++) {
            String dataNames = datanya2.get(i).first;
            if (dataNames.toLowerCase().contains(constraint.toString()))  {
                datanya.add(datanya2.get(i));
                toastnya.add(toastnya2.get(i));
            }
            else{
                dataNames = toastnya2.get(i);
                if (dataNames.toLowerCase().contains(constraint.toString()))  {
                    datanya.add(datanya2.get(i));
                    toastnya.add(toastnya2.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}