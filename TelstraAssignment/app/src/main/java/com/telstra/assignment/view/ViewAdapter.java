package com.telstra.assignment.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.telstra.assignment.R;
import com.telstra.assignment.model.AdapterRow;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter <ViewAdapter.DataHolder> {

    private List<AdapterRow> rowData;
    private Context context;

    public ViewAdapter(List<AdapterRow> rowData, HomeActivity homeActivity) {
        this.rowData = rowData;
        this.context = homeActivity;
    }


    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        AdapterRow row = rowData.get(position);
        holder.title.setText(row.getTitle());
        holder.description.setText(row.getDescription());
        Picasso.get().load(row.getImageHref()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(null !=rowData && !rowData.isEmpty())
            return rowData.size();
        return 0;
    }

    class DataHolder extends  RecyclerView.ViewHolder{
        private TextView title;
        private TextView description;
        private ImageView imageView;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            imageView = itemView.findViewById(R.id.iv_image);
        }
    }
}
