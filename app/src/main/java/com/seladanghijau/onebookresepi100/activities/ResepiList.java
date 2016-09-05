package com.seladanghijau.onebookresepi100.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.adapters.ResepiListAdapter;
import com.seladanghijau.onebookresepi100.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi100.asynctask.SearchResepiNameAsyncTask;
import com.seladanghijau.onebookresepi100.asynctask.SetupResepiListAsyncTask;
import com.seladanghijau.onebookresepi100.dto.Resepi;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;
import com.seladanghijau.onebookresepi100.provider.ILoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ResepiList extends AppCompatActivity implements ILoader, View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    View actionbarView;
    DrawerLayout drawer;
    ImageButton ibMenu, ibSearch;
    ListView lvMenu, lvResepiList;
    EditText etSearch;
    ImageButton ibSearchButton;
    RelativeLayout rlSearchPanel;

    // variables
    int category;
    String categoryName;
    ResepiManager resepiManager;
    String[] drawerMenuList, resepiNameList;
    WeakReference<ListView> lvResepiWeakRef, lvMenuWeakRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resepi_list);

        initViews();
        initVars();
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() {
        // setup actionbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        actionbarView = getSupportActionBar().getCustomView();

        // setup views
        ibMenu = (ImageButton) actionbarView.findViewById(R.id.ibMenu);
        ibSearch = (ImageButton) actionbarView.findViewById(R.id.ibSearch);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        lvResepiList = (ListView) findViewById(R.id.lvResepiList);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        etSearch = (EditText) findViewById(R.id.etSearch);
        ibSearchButton = (ImageButton) findViewById(R.id.ibSearchButton);
        rlSearchPanel = (RelativeLayout) findViewById(R.id.rlSearchPanel);
        rlSearchPanel.setVisibility(View.GONE);

        // setup listener
        ibMenu.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
        lvResepiList.setOnItemClickListener(this);
        ibSearchButton.setOnClickListener(this);

        // setup weak ref
        lvMenuWeakRef = new WeakReference<>(lvMenu);
        lvResepiWeakRef = new WeakReference<>(lvResepiList);
    }

    private void initVars() {
        resepiManager = new ResepiManager(this);
        categoryName = getIntent().getStringExtra("kategori_resepi");

        new DrawerMenuListAsyncTask(this, this).execute();
        new SetupResepiListAsyncTask(this, this, resepiManager, categoryName).execute();
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
                String searchResepi = etSearch.getText().toString();

                new SearchResepiNameAsyncTask(this, this, resepiManager, categoryName, searchResepi).execute();
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
            case R.id.lvResepiList:
                startActivity(new Intent(this, ResepiInfo.class).putExtra("nama_resepi", resepiNameList[position]));
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------

    // interface methods ---------------------------------------------------------------------------
    public void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList) {
        ListView listViewMenu;

        listViewMenu = lvMenuWeakRef.get();
        listViewMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));

        lvMenu.invalidate();

        this.drawerMenuList = drawerMenuList;
    }

    public void onLoad(String[] resepiNameList, Bitmap[] bgResepiList) {
        ListView listViewResepi;

        listViewResepi = lvResepiWeakRef.get();
        listViewResepi.setAdapter(new ResepiListAdapter(this, resepiNameList, bgResepiList));

        lvResepiList.invalidate();

        this.resepiNameList = resepiNameList;
    }

    public void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList) {
        ListView listViewResepi;

        listViewResepi = lvResepiWeakRef.get();
        listViewResepi.setAdapter(new ResepiListAdapter(this, resepiNameList, bgResepiList));

        lvResepiList.invalidate();

        this.category = category;
        this.resepiNameList = resepiNameList;
    }

    public void onLoad(Resepi resepiInfo) {}
    public void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList) {}
    public void onLoad(int[] resepiCount, String[] kategoriResepiList, Bitmap[] imejKategoriResepiList) {}
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
