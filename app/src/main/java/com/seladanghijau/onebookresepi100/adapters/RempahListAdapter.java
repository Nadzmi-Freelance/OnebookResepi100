package com.seladanghijau.onebookresepi100.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.activities.RempahFullScreen;

/**
 * Created by seladanghijau on 18/9/2016.
 */
public class RempahListAdapter extends BaseAdapter {
    private Context context;
    private TypedArray rempahList;

    public RempahListAdapter(Context context, TypedArray rempahList) {
        this.context = context;
        this.rempahList = rempahList;
    }

    public int getCount() { return rempahList.length(); }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        holder = new ViewHolder();
        if(convertView == null) {
            holder.imageView = new ImageView(context);
            holder.imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else
            holder.imageView = (ImageView) convertView;

        holder.imageView.setImageDrawable(rempahList.getDrawable(position));
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return holder.imageView;
    }

    class ViewHolder {
        private ImageView imageView;
    }
}
