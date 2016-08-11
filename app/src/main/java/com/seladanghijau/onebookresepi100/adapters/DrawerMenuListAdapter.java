package com.seladanghijau.onebookresepi100.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;

/**
 * Created by seladanghijau on 4/8/2016.
 */
public class DrawerMenuListAdapter extends BaseAdapter {
    private static LayoutInflater layoutInflater = null;
    private String[] menuList;
    private TypedArray menuIconList;
    private Context context;

    public DrawerMenuListAdapter(Context context, String[] menuList, TypedArray menuIconList) {
        this.context = context;
        this.menuIconList = menuIconList;
        this.menuList = menuList;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return menuList.length; }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;

        viewHolder = new Holder();
        viewHolder.rowView = layoutInflater.inflate(R.layout.layout_drawer_menu, null);

        viewHolder.tempImageView = (ImageView) viewHolder.rowView.findViewById(R.id.ivMenuIcon);
        viewHolder.tempTextView = (TextView) viewHolder.rowView.findViewById(R.id.tvMenuTitle);

        viewHolder.tempImageView.setBackground(menuIconList.getDrawable(position));
        viewHolder.tempTextView.setText(menuList[position]);

        return viewHolder.rowView;
    }

    static class Holder {
        View rowView;
        TextView tempTextView;
        ImageView tempImageView;
    }
}
