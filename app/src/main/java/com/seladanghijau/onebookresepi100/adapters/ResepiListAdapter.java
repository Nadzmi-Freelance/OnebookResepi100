package com.seladanghijau.onebookresepi100.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;

/**
 * Created by seladanghijau on 7/8/2016.
 */
public class ResepiListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] resepiNameList;
    private Bitmap[] resepiBg;

    public ResepiListAdapter(Context context, String[] resepiNameList, Bitmap[] resepiBg) {
        this.context = context;
        this.resepiNameList = resepiNameList;
        this.resepiBg = resepiBg;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return resepiNameList.length; }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;

        viewHolder = new Holder();
        viewHolder.rowView = layoutInflater.inflate(R.layout.layout_resepi_list, null);

        viewHolder.ivResepiListBg = (ImageView) viewHolder.rowView.findViewById(R.id.ivResepiListBg);
        viewHolder.tvNamaResepi = (TextView) viewHolder.rowView.findViewById(R.id.tvNamaResepi);

        viewHolder.ivResepiListBg.setImageBitmap(resepiBg[position]);
        viewHolder.tvNamaResepi.setText(resepiNameList[position]);

        return viewHolder.rowView;
    }

    static class Holder {
        View rowView;
        ImageView ivResepiListBg;
        TextView tvNamaResepi;
    }
}
