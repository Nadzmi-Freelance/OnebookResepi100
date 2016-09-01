package com.seladanghijau.onebookresepi100.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;

import java.util.ArrayList;

/**
 * Created by seladanghijau on 31/8/2016.
 */
public class ResepiBahanAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Pair<String, String>> bahan;
    private Bitmap[] bahanImg;
    private String currDesc, prevDesc;

    public ResepiBahanAdapter(Context context, ArrayList<Pair<String, String>> bahan, Bitmap[] bahanImg) {
        this.context = context;
        this.bahan = bahan;
        this.bahanImg = bahanImg;
        currDesc = "";
        prevDesc = "";

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return bahan.size(); }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        Holder viewHolder;

        rowView = convertView;
        if(convertView == null) {
            rowView = layoutInflater.inflate(R.layout.layout_resepi_bahan, null);

            viewHolder = new Holder();
            viewHolder.tvBahanDesc = (TextView) rowView.findViewById(R.id.tvBahanDesc);
            viewHolder.tvBahanName = (TextView) rowView.findViewById(R.id.tvBahanName);
            viewHolder.ivBahanImg = (ImageView) rowView.findViewById(R.id.ivBahanImg);

            rowView.setTag(viewHolder);
        } else
            viewHolder = (Holder) rowView.getTag();

        currDesc = bahan.get(position).first;
        if(position != 0)
            if(currDesc.equalsIgnoreCase(prevDesc))
                viewHolder.tvBahanDesc.setVisibility(View.GONE);
            else
                viewHolder.tvBahanDesc.setVisibility(View.VISIBLE);
        else
            viewHolder.tvBahanDesc.setVisibility(View.VISIBLE);

        prevDesc = currDesc;
        currDesc = "";

        viewHolder.tvBahanDesc.setText("Bahan " + bahan.get(position).first);
        viewHolder.tvBahanName.setText(bahan.get(position).second);
        viewHolder.ivBahanImg.setImageBitmap(bahanImg[position]);

        return rowView;
    }

    class Holder {
        TextView tvBahanDesc, tvBahanName;
        ImageView ivBahanImg;
    }
}
