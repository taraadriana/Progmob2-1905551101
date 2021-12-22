package com.example.progmob2;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Drama> dramaArrayList;
    private int position;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int position);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public CustomAdapter(Context context, ArrayList<Drama> dramaArrayList){
        this.context = context;
        this.dramaArrayList = dramaArrayList;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_row, parent, false);
        return new CustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dramaIdtx.setText(dramaArrayList.get(position).getDramaId());
        holder.dramaTitletx.setText(dramaArrayList.get(position).getDramaTitle());
        holder.dramaYeartx.setText(dramaArrayList.get(position).getDramaYear());
    }

    @Override
    public int getItemCount() {
        return dramaArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dramaIdtx, dramaTitletx, dramaYeartx;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            dramaIdtx = itemView.findViewById(R.id.dramaId);
            dramaTitletx = itemView.findViewById(R.id.dramaTitle);
            dramaYeartx = itemView.findViewById(R.id.dramaYear);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog != null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
