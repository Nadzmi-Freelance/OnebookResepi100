package com.seladanghijau.onebookresepi100.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;

/**
 * Created by seladanghijau on 7/8/2016.
 */
public class KategoriResepiListAdapter extends BaseAdapter {
    private static LayoutInflater layoutInflater = null;
    private Context context;
    private String[] kategoriResepiList;
    private int[] resepiCount;
    private TypedArray imejKategoriResepiList;

    public KategoriResepiListAdapter(Context context, String[] kategoriResepiList, TypedArray imejKategoriResepiList, int[] resepiCount) {
        this.context = context;
        this.kategoriResepiList = kategoriResepiList;
        this.imejKategoriResepiList = imejKategoriResepiList;
        this.resepiCount = resepiCount;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return kategoriResepiList.length; }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        TextView tvKategoriResepiName, tvBilResepi;
        RelativeLayout rlKategoriBg;

        rowView = layoutInflater.inflate(R.layout.layout_kategori_resepi_list, null);

        tvKategoriResepiName = (TextView) rowView.findViewById(R.id.tvKategoriResepiName);
        tvBilResepi = (TextView) rowView.findViewById(R.id.tvBilResepi);
        rlKategoriBg = (RelativeLayout) rowView.findViewById(R.id.rlKategoriBg);

        tvKategoriResepiName.setText(kategoriResepiList[position]);
        tvBilResepi.setText(String.valueOf(resepiCount[position]));
        rlKategoriBg.setBackground(imejKategoriResepiList.getDrawable(position));

        return rowView;
    }
}
