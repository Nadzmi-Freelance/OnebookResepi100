package com.seladanghijau.onebookresepi100.provider;

import android.content.res.TypedArray;
import android.graphics.Bitmap;

import com.seladanghijau.onebookresepi100.dto.Resepi;

/**
 * Created by seladanghijau on 11/8/2016.
 */
public interface ILoader {
    void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList);
    void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList); // for main activity
    void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList); // for resepilist activity
    void onLoad(Resepi resepiInfo); // for resepiinfo activity
    void onLoad(String[] resepiNameList, Bitmap[] bgResepiList);
}
