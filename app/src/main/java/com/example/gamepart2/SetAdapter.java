package com.example.gamepart2;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder>{
    public List<SetModel> set_list;


    public SetAdapter(List<SetModel> set_list) {
        this.set_list = set_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String title = set_list.get(position).getName();
        holder.setData(title,position,this);
    }

    @Override
    public int getItemCount() {

        return set_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView setName;
        private Dialog loadingDialog;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            setName = itemView.findViewById(R.id.setName);
            loadingDialog = new Dialog(itemView.getContext());
            loadingDialog.setContentView(R.layout.loading_progressbar);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        public void setData(String title, int position, SetAdapter setAdapter) {

            setName.setText(title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SetsActivity.selected_set_index = position;
                    Intent intent = new Intent(itemView.getContext(),CategoryActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
