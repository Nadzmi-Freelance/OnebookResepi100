package com.seladanghijau.onebookresepi100.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Pair;

import com.seladanghijau.onebookresepi100.manager.ResepiManager;
import com.seladanghijau.onebookresepi100.provider.ILoader;

import java.util.ArrayList;

/**
 * Created by seladanghijau on 12/8/2016.
 */
public class SearchResepiCategoryAsyncTask extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    private ILoader loader;
    private ResepiManager resepiManager;

    private String category;
    private int[] resepiCount;
    private String[] kategoriResepiList;
    private Bitmap[] imejKategoriResepilist;

    public SearchResepiCategoryAsyncTask(Context context, ILoader loader, ResepiManager resepiManager, String category) {
        this.context = context;
        this.loader = loader;
        this.resepiManager = resepiManager;
        this.category = category;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoad(resepiCount, kategoriResepiList, imejKategoriResepilist);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected Void doInBackground(Void... params) {
        ArrayList<Pair<String, Bitmap>> resepiCategoryList;

        resepiCategoryList = resepiManager.getResepiCategoryListWithImg(resepiManager.getResepiCategoryId(category));

        resepiCount = new int[resepiCategoryList.size()];
        for (int x=0 ; x<resepiCategoryList.size() ; x++) {
            kategoriResepiList[x] = resepiCategoryList.get(x).first;
            imejKategoriResepilist[x] = resepiCategoryList.get(x).second;
            resepiCount[x] = resepiManager.getResepiCount(resepiManager.getResepiCategoryId(kategoriResepiList[x]));
        }
        return null;
    }
}
