package com.seladanghijau.onebookresepi100.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.renderscript.Type;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
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
import com.seladanghijau.onebookresepi100.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi100.asynctask.SetupMainActivityListAsyncTask;
import com.seladanghijau.onebookresepi100.dto.Resepi;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;
import com.seladanghijau.onebookresepi100.provider.ILoader;

public class MainActivity extends AppCompatActivity implements ILoader, View.OnClickListener, OnItemClickListener {
    // views
    View actionbarView;
    ImageButton ibMenu, ibSearch;
    TextView tvTitle;
    ListView lvKategoriResepi, lvMenu;
    DrawerLayout drawer;

    // variables
    int[] resepiCount;
    String[] drawerMenuList, kategoriResepiList;
    ResepiManager resepiManager;

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

        // setup listener
        ibMenu.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
        lvKategoriResepi.setOnItemClickListener(this);

        lvKategoriResepi.invalidate();
        lvMenu.invalidate();
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
                Log.e("ibSearch: ", "Pressing search button");
                break;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lvMenu:
                finish();

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
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------

    // resepi loader interface ---------------------------------------------------------------------
    public void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList) {
        lvMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));

        this.drawerMenuList = drawerMenuList;
    }

    public void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList) {
        // setup listview adapter
        lvKategoriResepi.setAdapter(new KategoriResepiListAdapter(this, kategoriResepiList, imejKategoriResepiList, resepiCount));

        this.resepiCount = resepiCount;
        this.kategoriResepiList = kategoriResepiList;
    }

    public void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(Resepi resepiInfo) {}
    public void onLoad(String[] resepiNameList, Bitmap[] bgResepiList) {}
    // ---------------------------------------------------------------------------------------------

    // util  methods -------------------------------------------------------------------------------
    private void slideDrawer(DrawerLayout drawer) {
        if(drawer.isDrawerOpen(Gravity.LEFT))
            drawer.closeDrawer(Gravity.LEFT);
        else if(!drawer.isDrawerOpen(Gravity.LEFT))
            drawer.openDrawer(Gravity.LEFT);
    }
    // ---------------------------------------------------------------------------------------------
}
