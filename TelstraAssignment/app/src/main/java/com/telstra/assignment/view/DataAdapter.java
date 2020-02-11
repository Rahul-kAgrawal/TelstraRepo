package com.telstra.assignment.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.telstra.assignment.R;
import com.telstra.assignment.model.DataListItem;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter <DataAdapter.DataViewHolder> {
    private Activity mContext;
    private List<DataListItem> mItemList;

    public void setData(List<DataListItem> itemList) {
        mItemList = itemList;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private ImageView mImage;
        private TextView mDescription;
        LinearLayout rowItem;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mImage = itemView.findViewById(R.id.iv_image);
            mDescription = itemView.findViewById(R.id.tv_description);
            rowItem = itemView.findViewById(R.id.rowitem);
        }

        public void setTitle(String title){
            mTitle.setText(title);
        }

        public void setDescription(String description){
            mDescription.setText(description);
        }

        public void setImage(String imgUrl){
            Picasso.get().load(imgUrl).placeholder(R.drawable.no_image_icon).error(R.drawable.no_image_icon).into(mImage);
        }
    }

    public DataAdapter(List<DataListItem> itemList, Activity context) {
        mContext = context;
        mItemList = itemList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_layout, parent, false);
// set the view's size, margins, paddings and layout parameters
        DataViewHolder vh = new DataViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DataListItem row = mItemList.get(position);
        if(TextUtils.isEmpty(row.getTitle())){
            holder.rowItem.setVisibility(View.GONE);
        } else {
            holder.rowItem.setVisibility(View.VISIBLE);
            holder.setTitle(row.getTitle());
            holder.setDescription(row.getDescription());
            holder.setImage(row.getUrl());
        }
    }

    @Override
    public int getItemCount() {
        if(null !=mItemList && !mItemList.isEmpty())
            return mItemList.size();
        return 0;
    }
}
