package com.seladanghijau.onebookresepi100.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.dto.Resepi;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;
import com.seladanghijau.onebookresepi100.provider.ILoader;

/**
 * Created by seladanghijau on 11/8/2016.
 */
public class ResepiInfoAsyncTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ILoader loader;
    private ResepiManager resepiManager;

    private Resepi resepiInfo;
    private String namaResepi;
    private int resepiId;
    private TypedArray ikonDrawerMenuList;
    private String[] drawerMenuList;

    public ResepiInfoAsyncTask(Context context, ILoader loader, ResepiManager resepiManager, String namaResepi) {
        this.context = context;
        this.loader = loader;
        this.resepiManager = resepiManager;
        this.namaResepi = namaResepi;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoadMenuDrawer(drawerMenuList, ikonDrawerMenuList);
        loader.onLoad(resepiInfo);
    }

    protected Void doInBackground(Void... params) {
        resepiId = resepiManager.getResepiId(namaResepi);
        resepiInfo = resepiManager.getResepiInfo(resepiId);

        // get resources
        drawerMenuList = context.getResources().getStringArray(R.array.drawerMenu);
        ikonDrawerMenuList = context.getResources().obtainTypedArray(R.array.ikonDrawerMenu);

        return null;
    }
}
