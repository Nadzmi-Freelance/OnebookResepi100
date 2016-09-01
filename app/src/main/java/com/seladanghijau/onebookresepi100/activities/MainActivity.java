package com.seladanghijau.onebookresepi100.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.adapters.KategoriResepiListAdapter;
import com.seladanghijau.onebookresepi100.adapters.SearchKategoriResepiListAdapter;
import com.seladanghijau.onebookresepi100.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi100.asynctask.SearchResepiCategoryAsyncTask;
import com.seladanghijau.onebookresepi100.asynctask.SetupMainActivityListAsyncTask;
import com.seladanghijau.onebookresepi100.dto.Resepi;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;
import com.seladanghijau.onebookresepi100.provider.ILoader;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements ILoader, View.OnClickListener, OnItemClickListener {
    // views
    View actionbarView;
    ImageButton ibMenu, ibSearch, ibSearchButton;
    TextView tvTitle;
    ListView lvKategoriResepi, lvMenu;
    DrawerLayout drawer;
    RelativeLayout rlSearchPanel;
    EditText etSearch;

    // variables
    int[] resepiCount;
    String[] drawerMenuList, kategoriResepiList;
    ResepiManager resepiManager;
    WeakReference<ListView> lvMenuWeakRef, lvKategoriResepiWeakRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initVars();
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        actionbarView = getSupportActionBar().getCustomView();

        // setup views
        ibMenu = (ImageButton) actionbarView.findViewById(R.id.ibMenu);
        ibSearch = (ImageButton) actionbarView.findViewById(R.id.ibSearch);
        tvTitle = (TextView) actionbarView.findViewById(R.id.tvTitle);
        lvKategoriResepi = (ListView) findViewById(R.id.lvKategoriResepi);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        etSearch = (EditText) findViewById(R.id.etSearch);
        ibSearchButton = (ImageButton) findViewById(R.id.ibSearchButton);
        rlSearchPanel = (RelativeLayout) findViewById(R.id.rlSearchPanel);
        rlSearchPanel.setVisibility(View.GONE);

        tvTitle.setText("Home");

        // setup listener
        ibMenu.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
        ibSearchButton.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
        lvKategoriResepi.setOnItemClickListener(this);

        lvMenuWeakRef = new WeakReference<>(lvMenu);
        lvKategoriResepiWeakRef = new WeakReference<>(lvKategoriResepi);
    }

    private void initVars() {
        resepiManager = new ResepiManager(this);

        new DrawerMenuListAsyncTask(this, this).execute();
        new SetupMainActivityListAsyncTask(this, this, resepiManager).execute();
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onBackPressed() {
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMenu:
                slideDrawer(drawer);
                break;
            case R.id.ibSearch:
                toggelSearchPanel(rlSearchPanel);
                break;
            case R.id.ibSearchButton:
                String searchItem = etSearch.getText().toString();

                new SearchResepiCategoryAsyncTask(this, this, resepiManager, searchItem).execute();
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
                else if((position >= 3) && (position <= 22))
                    startActivity(new Intent(this, ResepiList.class).putExtra("kategori_resepi", drawerMenuList[position]));
                else if(position == 23 )
                    startActivity(new Intent(this, Cabutan.class));
                else if(position == 24)
                    startActivity(new Intent(this, TentangKami.class));
                break;
            case R.id.lvKategoriResepi:
                startActivity(new Intent(this, ResepiList.class).putExtra("kategori_resepi", kategoriResepiList[position]));
                tvTitle.setText(kategoriResepiList[position]);
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------

    // resepi loader interface ---------------------------------------------------------------------
    public void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList) {
        ListView listViewMenu;

        listViewMenu = lvMenuWeakRef.get();
        listViewMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));

        lvMenu.invalidate();

        this.drawerMenuList = drawerMenuList;
    }

    public void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList) {
        ListView listViewKategoriResepi;

        listViewKategoriResepi = lvKategoriResepiWeakRef.get();
        listViewKategoriResepi.setAdapter(new KategoriResepiListAdapter(this, kategoriResepiList, imejKategoriResepiList, resepiCount));

        lvKategoriResepi.invalidate();

        this.resepiCount = resepiCount;
        this.kategoriResepiList = kategoriResepiList;
    }

    public void onLoad(int[] resepiCount, String[] kategoriResepiList, Bitmap[] imejKategoriResepiList) {
        ListView listViewKategoriResepi;

        listViewKategoriResepi = lvKategoriResepiWeakRef.get();
        listViewKategoriResepi.setAdapter(new SearchKategoriResepiListAdapter(this, kategoriResepiList, imejKategoriResepiList, resepiCount));

        lvKategoriResepi.invalidate();

        this.resepiCount = resepiCount;
        this.kategoriResepiList = kategoriResepiList;
    }

    public void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(Resepi resepiInfo) {}
    public void onLoad(String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(String[] tipsMasakan) {}
    // ---------------------------------------------------------------------------------------------

    // util  methods -------------------------------------------------------------------------------
    private void slideDrawer(DrawerLayout drawer) {
        if(drawer.isDrawerOpen(Gravity.LEFT))
            drawer.closeDrawer(Gravity.LEFT);
        else if(!drawer.isDrawerOpen(Gravity.LEFT))
            drawer.openDrawer(Gravity.LEFT);
    }

    private void toggelSearchPanel(View v) {
        if(v.getVisibility() == View.GONE) {
            v.setVisibility(View.VISIBLE);
            ibSearch.setBackgroundResource(R.drawable.ic_cancel_black_24dp);
        } else if(v.getVisibility() == View.VISIBLE) {
            v.setVisibility(View.GONE);
            ibSearch.setBackgroundResource(R.drawable.ic_search_black_24dp);
        }

        etSearch.setText(null);
    }
    // ---------------------------------------------------------------------------------------------
}
