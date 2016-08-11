package com.seladanghijau.onebookresepi100.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;

/**
 * Created by seladanghijau on 11/8/2016.
 */
public class ResepiLangkahAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] step;

    public ResepiLangkahAdapter(Context context, String[] step) {
        this.context = context;
        this.step = step;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return step.length; }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;

        viewHolder = new Holder();
        viewHolder.rowView = layoutInflater.inflate(R.layout.layout_resepi_langkah, null);

        viewHolder.tvStep = (TextView) viewHolder.rowView.findViewById(R.id.tvStep);
        viewHolder.tvDesc = (TextView) viewHolder.rowView.findViewById(R.id.tvDesc);

        viewHolder.tvStep.setText("Step " + (position+1) + " :");
        viewHolder.tvDesc.setText(step[position]);

        return viewHolder.rowView;
    }

    static class Holder {
        View rowView;
        TextView tvStep, tvDesc;
    }
}