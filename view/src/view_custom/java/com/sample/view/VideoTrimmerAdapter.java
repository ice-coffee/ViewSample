package com.sample.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class VideoTrimmerAdapter extends RecyclerView.Adapter {
    private Context context;
    private float itemWidth;
    private LayoutInflater mInflater;
    private List<Bitmap> mBitmaps = new ArrayList<>();

    public VideoTrimmerAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrimmerViewHolder(mInflater.inflate(R.layout.video_thumb_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TrimmerViewHolder) holder).thumbImageView.setImageBitmap(mBitmaps.get(position));
    }

    @Override
    public int getItemCount() {
        return mBitmaps.size();
    }

    public void clearBitmaps() {
        if (null != mBitmaps) {
            mBitmaps.clear();
        }
    }
    public void addBitmaps(Bitmap bitmap) {
        if (null != mBitmaps) {
            mBitmaps.add(bitmap);
            notifyDataSetChanged();
        }
    }

    public void setItemWidth(float itemWidth) {
        this.itemWidth = itemWidth;
    }

    private final class TrimmerViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbImageView;

        TrimmerViewHolder(View itemView) {
            super(itemView);
            thumbImageView = itemView.findViewById(R.id.thumb);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) thumbImageView.getLayoutParams();
            layoutParams.width = (int) itemWidth;
            thumbImageView.setLayoutParams(layoutParams);
        }
    }
}
