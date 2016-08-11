package com.seladanghijau.onebookresepi100.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.adapters.ResepiLangkahAdapter;
import com.seladanghijau.onebookresepi100.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi100.asynctask.ResepiInfoAsyncTask;
import com.seladanghijau.onebookresepi100.dto.Resepi;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;
import com.seladanghijau.onebookresepi100.provider.ILoader;

public class ResepiInfo extends AppCompatActivity implements ILoader, View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    View actionbarView;
    ImageButton ibMenu, ibSearch;
    ListView lvMenu, lvBahan, lvLangkah;
    ImageView ivResepiImg;
    TabHost thResepiInfo;
    RelativeLayout rlBottomPanel;
    DrawerLayout drawer;
    TextView tvResepiName, tvRingkasan, tvTitle;
    LinearLayout llFavourite, llShare;

    // variables
    int resepiId;
    String namaResepi;
    String[] drawerMenuList;
    ResepiManager resepiManager;
    TabHost.TabSpec tsRingkasan, tsBahan, tsLangkah;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resepi_info);

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
        tvTitle = (TextView) actionbarView.findViewById(R.id.tvTitle);
        ivResepiImg = (ImageView) findViewById(R.id.ivResepiImg);
        tvResepiName = (TextView) findViewById(R.id.tvResepiName);
        tvRingkasan = (TextView) findViewById(R.id.tvRingkasan);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        lvBahan = (ListView) findViewById(R.id.lvBahan);
        lvLangkah = (ListView) findViewById(R.id.lvLangkah);
        thResepiInfo = (TabHost) findViewById(R.id.thResepiInfo);
        rlBottomPanel = (RelativeLayout) findViewById(R.id.rlBottomPanel);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        llFavourite = (LinearLayout) findViewById(R.id.llFavourite);
        llShare = (LinearLayout) findViewById(R.id.llShare);

        // setup listener
        ibMenu.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
        llFavourite.setOnClickListener(this);
        llShare.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);

        lvLangkah.invalidate();
        lvBahan.invalidate();
        lvMenu.invalidate();
    }

    private void initVars() {
        resepiManager = new ResepiManager(this);
        namaResepi = getIntent().getStringExtra("nama_resepi");

        setupTabhost(); // setup tabhost
        new DrawerMenuListAsyncTask(this, this).execute();
        new ResepiInfoAsyncTask(this, this, resepiManager, namaResepi).execute(); // setup ui
        setupBottomSheetBehaviour(rlBottomPanel);
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMenu:
                slideDrawer(drawer);
                break;
            case R.id.llFavourite:
                resepiManager.addFavorite(resepiId);
                Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llShare:
                break;
        }
    }

    public void onBackPressed() {
        slide(rlBottomPanel);
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
                else if((position >= 3) || (position <= 22))
                    startActivity(new Intent(this, ResepiList.class).putExtra("kategori_resepi", drawerMenuList[position]));
                else if(position == 23)
                    startActivity(new Intent(this, Cabutan.class));
                else if(position == 24)
                    startActivity(new Intent(this, TentangKami.class));
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------

    // interface methods ---------------------------------------------------------------------------
    public void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList) {
        lvMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));

        this.drawerMenuList = drawerMenuList;
    }

    public void onLoad(Resepi resepiInfo) {
        resepiId = resepiInfo.getId();

        ivResepiImg.setImageBitmap(resepiInfo.getResepiImg());
        tvResepiName.setText(resepiInfo.getName());
        tvRingkasan.setText(resepiInfo.getRingkasan());
        lvLangkah.setAdapter(new ResepiLangkahAdapter(this, resepiInfo.getLangkah()));
        // lvBahan.setAdapter(); FIXME: tunjuk list of bahan dalam tab bahan
    }

    public void onLoad(String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList) {}
    public void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList) {}
    // ---------------------------------------------------------------------------------------------

    // util methods --------------------------------------------------------------------------------
    private void slide(View v) {
        final BottomSheetBehavior bottomSheetBehavior;

        bottomSheetBehavior = BottomSheetBehavior.from(v);
        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
            finish();
        else
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void setupBottomSheetBehaviour(View v) {
        final BottomSheetBehavior bottomSheetBehavior;

        bottomSheetBehavior = BottomSheetBehavior.from(v);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            public void onSlide(View bottomSheet, float slideOffset) {}
            public void onStateChanged(View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_DRAGGING)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    private void slideDrawer(DrawerLayout drawer) {
        if(drawer.isDrawerOpen(Gravity.LEFT))
            drawer.closeDrawer(Gravity.LEFT);
        else if(!drawer.isDrawerOpen(Gravity.LEFT))
            drawer.openDrawer(Gravity.LEFT);
    }
    // ---------------------------------------------------------------------------------------------

    // other methods -------------------------------------------------------------------------------
    private void setupTabhost() {
        // setup tabspec
        thResepiInfo.setup();
        tsRingkasan = thResepiInfo.newTabSpec("Ringkasan"); // ringkasan tab -----------------------
        tsRingkasan.setIndicator(LayoutInflater.from(this).inflate(R.layout.tab_ringkasan, null));
        tsRingkasan.setContent(R.id.tabRingkasan);
        tsBahan = thResepiInfo.newTabSpec("Bahan"); // bahan tab -----------------------------------
        tsBahan.setIndicator(LayoutInflater.from(this).inflate(R.layout.tab_bahan, null));
        tsBahan.setContent(R.id.tabBahan);
        tsLangkah = thResepiInfo.newTabSpec("Langkah"); // langkah tab -----------------------------
        tsLangkah.setIndicator(LayoutInflater.from(this).inflate(R.layout.tab_langkah, null));
        tsLangkah.setContent(R.id.tabLangkah);

        // add tab to tab host
        thResepiInfo.addTab(tsRingkasan);
        thResepiInfo.addTab(tsBahan);
        thResepiInfo.addTab(tsLangkah);
    }
    // ---------------------------------------------------------------------------------------------
}
