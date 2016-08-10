package com.seladanghijau.onebookresepi100.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.adapters.KategoriResepiListAdapter;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {
    // views
    ActionBar actionBar;
    ImageButton ibMenu, ibSearch;
    TextView tvTitle;
    ListView lvKategoriResepi, lvMenu;

    // variables
    int[] resepiCount;
    String[] drawerMenuList, kategoriResepiList;
    ResepiManager resepiManager;
    TypedArray ikonDrawerMenuList, imejKategoriResepiList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        tvTitle = (TextView) customActionBarView.findViewById(R.id.tvTitle);
        lvKategoriResepi = (ListView) findViewById(R.id.lvKategoriResepi);
        lvMenu = (ListView) findViewById(R.id.lvMenu);

        // setup listener
        ibMenu.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
        lvKategoriResepi.setOnItemClickListener(this);
    }

    private void initVars() {
        resepiManager = new ResepiManager(this);

        // get resources
        drawerMenuList = getResources().getStringArray(R.array.drawerMenu);
        ikonDrawerMenuList = getResources().obtainTypedArray(R.array.ikonDrawerMenu);
        kategoriResepiList = getResources().getStringArray(R.array.kategoriResepi);
        imejKategoriResepiList = getResources().obtainTypedArray(R.array.imejKategoriResepi);

        // calc resepi coutn for every category
        resepiCount = new int[kategoriResepiList.length];
        for(int x=0 ; x<kategoriResepiList.length ; x++) {
            int count;

            count = resepiManager.getResepiCount(resepiManager.getResepiCategoryId(kategoriResepiList[x]));
            resepiCount[x] = count;
        }

        // setup listview adapter
        lvMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));
        lvKategoriResepi.setAdapter(new KategoriResepiListAdapter(this, kategoriResepiList, imejKategoriResepiList, resepiCount));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMenu:
                Log.e("ibMenu: ", "Pressing menu button");
                break;
            case R.id.ibSearch:
                Log.e("ibSearch: ", "Pressing search button");
                break;
        }
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
            case R.id.lvKategoriResepi:
                startActivity(new Intent(this, ResepiList.class).putExtra("kategori_resepi", kategoriResepiList[position]));
                break;
        }
    }
}
