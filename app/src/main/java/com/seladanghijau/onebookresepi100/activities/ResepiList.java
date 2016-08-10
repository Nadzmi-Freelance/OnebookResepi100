package com.seladanghijau.onebookresepi100.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.adapters.ResepiListAdapter;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;

import java.util.ArrayList;

public class ResepiList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // views
    ActionBar actionBar;
    ImageButton ibMenu, ibSearch;
    ListView lvMenu, lvResepiList;

    // variables
    ResepiManager resepiManager;
    int category;
    String[] drawerMenuList, resepiNameList;
    Bitmap[] bgResepiList;
    TypedArray ikonDrawerMenuList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resepi_list);

        initViews();
        initVars();
    }

    private void initViews() {
        LayoutInflater actionBarInflater;
        View customActionBarView;

        // setup custom actionbar
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBarInflater = LayoutInflater.from(this);
        customActionBarView = actionBarInflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(customActionBarView);
        actionBar.setDisplayShowCustomEnabled(true);

        // setup views
        ibMenu = (ImageButton) customActionBarView.findViewById(R.id.ibMenu);
        ibSearch = (ImageButton) customActionBarView.findViewById(R.id.ibSearch);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        lvResepiList = (ListView) findViewById(R.id.lvResepiList);

        // setup listener
        lvMenu.setOnItemClickListener(this);
        lvResepiList.setOnItemClickListener(this);
    }

    private void initVars() {
        ArrayList<Pair<String, Bitmap>> resepiNameListWithImg;
        String categoryName;

        resepiManager = new ResepiManager(this);

        categoryName = getIntent().getStringExtra("kategori_resepi");
        category = resepiManager.getResepiCategoryId(categoryName);

        // get resources
        drawerMenuList = getResources().getStringArray(R.array.drawerMenu);
        ikonDrawerMenuList = getResources().obtainTypedArray(R.array.ikonDrawerMenu);
        // resepi namelist with bg
        resepiNameListWithImg = resepiManager.getResepiNameListWithImg(category);
        resepiNameList = new String[resepiNameListWithImg.size()];
        bgResepiList = new Bitmap[resepiNameListWithImg.size()];
        for(int x=0 ; x<resepiNameListWithImg.size() ; x++) {
            Pair<String, Bitmap> stringBitmapPair;
            String resepiName;
            Bitmap resepiBg;

            stringBitmapPair = resepiNameListWithImg.get(x);
            resepiName = stringBitmapPair.first;
            resepiBg = stringBitmapPair.second;

            resepiNameList[x] = resepiName;
            bgResepiList[x] = resepiBg;
        }

        // setup listview adapter
        lvMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));
        lvResepiList.setAdapter(new ResepiListAdapter(this, resepiNameList, bgResepiList));
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lvMenu:
                if(position == 0)
                    startActivity(new Intent(this, MainActivity.class));
                else if(position == 1)
                    startActivity(new Intent(this, TipMasakan.class));
                else if(position == 2)
                    startActivity(new Intent(this, Favorite.class));
                else if((position >= 3) || (position <= 22))
                    startActivity(new Intent(this, ResepiList.class).putExtra("kategori_resepi", drawerMenuList[position]));
                else if(position == 23)
                    startActivity(new Intent(this, Cabutan.class));
                else if(position == 24)
                    startActivity(new Intent(this, TentangKami.class));
                break;
            case R.id.lvResepiList:
                startActivity(new Intent(this, ResepiInfo.class).putExtra("nama_resepi", resepiNameList[position]));
                break;
        }
    }
}
