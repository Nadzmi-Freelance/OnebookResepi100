package com.seladanghijau.onebookresepi100.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.provider.ILoader;

/**
 * Created by seladanghijau on 1/9/2016.
 */
public class TipsMasakanAsyncTask extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    private ILoader loader;
    private String[] tipsMasakan;

    public TipsMasakanAsyncTask(Context context, ILoader loader) {
        this.context = context;
        this.loader = loader;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoad(tipsMasakan);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected Void doInBackground(Void... params) {
        tipsMasakan = context.getResources().getStringArray(R.array.tipsMasakan);

        return null;
    }
}
