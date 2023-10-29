package com.example.pdfapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleItemAdapt extends RecyclerView.Adapter<RecycleItemAdapt.ViiewHolder> {
    Context context;
    ArrayList<Items>arr;
    RecycleItemAdapt(Context context ,ArrayList<Items> arr){
        this.context=context;
        this.arr=arr;
    }

    @NonNull
    @Override
    public ViiewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //change attach to root to true in future
        View v= LayoutInflater.from(context).inflate(R.layout.paticulars_row,parent,false);
        ViiewHolder viewHolder=new ViiewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViiewHolder holder, int position) {
        holder.tvSrno.setText(Integer.toString(arr.get(position).id));
        holder.tvItems.setText(arr.get(position).itemName);
        holder.tvQu.setText(Integer.toString(arr.get(position).quant));
        holder.tvRate.setText(Integer.toString(arr.get(position).rate));
        holder.tvTotal.setText(Integer.toString(arr.get(position).total));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViiewHolder extends RecyclerView.ViewHolder{
    TextView tvSrno,tvItems,tvRate,tvQu,tvTotal;
    public ViiewHolder(@NonNull View itemView) {
        super(itemView);
        tvSrno=itemView.findViewById(R.id.tvSrno);
        tvItems=itemView.findViewById(R.id.tvItems);
        tvQu=itemView.findViewById(R.id.tvQu);
        tvRate=itemView.findViewById(R.id.tvRate);
        tvTotal=itemView.findViewById(R.id.tvTotal);
    }
}

}
