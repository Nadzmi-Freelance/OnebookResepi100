package com.seladanghijau.onebookresepi100.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;
import com.seladanghijau.onebookresepi100.provider.ILoader;

/**
 * Created by seladanghijau on 11/8/2016.
 */
public class SetupMainActivityListAsyncTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ProgressDialog progressDialog;
    private ILoader loader;

    private int[] resepiCount;
    private String[] kategoriResepiList;
    private ResepiManager resepiManager;
    private TypedArray imejKategoriResepiList;

    public SetupMainActivityListAsyncTask(Context context, ILoader loader, ResepiManager resepiManager) {
        this.context = context;
        this.loader = loader;
        this.resepiManager = resepiManager;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoad(resepiCount, kategoriResepiList, imejKategoriResepiList); // load kategori list

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected Void doInBackground(Void... params) {
        // get resources
        kategoriResepiList = context.getResources().getStringArray(R.array.kategoriResepi);
        imejKategoriResepiList = context.getResources().obtainTypedArray(R.array.imejKategoriResepi);

        // calc resepi count for every category
        resepiCount = new int[kategoriResepiList.length];
        for(int x=0 ; x<kategoriResepiList.length ; x++) {
            resepiCount[x] = resepiManager.getResepiCount(resepiManager.getResepiCategoryId(kategoriResepiList[x]));
        }

        return null;
    }
}
