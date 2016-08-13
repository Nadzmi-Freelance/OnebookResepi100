package com.seladanghijau.onebookresepi100.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.provider.ILoader;

/**
 * Created by seladanghijau on 11/8/2016.
 */
public class DrawerMenuListAsyncTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ILoader loader;
    private TypedArray ikonDrawerMenuList;
    private String[] drawerMenuList;
    private ProgressDialog progressDialog;

    public DrawerMenuListAsyncTask(Context context, ILoader loader) {
        this.context = context;
        this.loader = loader;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoadMenuDrawer(drawerMenuList, ikonDrawerMenuList);
    }

    protected Void doInBackground(Void... params) {
        drawerMenuList = context.getResources().getStringArray(R.array.drawerMenu);
        ikonDrawerMenuList = context.getResources().obtainTypedArray(R.array.ikonDrawerMenu);

        return null;
    }
}
