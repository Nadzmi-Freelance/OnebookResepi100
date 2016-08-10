package com.seladanghijau.onebookresepi100.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.dto.Resepi;
import com.seladanghijau.onebookresepi100.manager.ResepiManager;

public class ResepiInfo extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    ActionBar actionBar;
    ImageButton ibMenu, ibSearch;
    ListView lvMenu, lvBahan, lvLangkah;
    ImageView ivResepiImg;
    TabHost thResepiInfo;
    TextView tvResepiName, tvRingkasan;

    // variables
    int resepiId;
    String[] drawerMenuList;
    Resepi resepiInfo;
    TypedArray ikonDrawerMenuList;
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
        ivResepiImg = (ImageView) findViewById(R.id.ivResepiImg);
        tvResepiName = (TextView) findViewById(R.id.tvResepiName);
        tvRingkasan = (TextView) findViewById(R.id.tvRingkasan);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        lvBahan = (ListView) findViewById(R.id.lvBahan);
        lvLangkah = (ListView) findViewById(R.id.lvLangkah);
        thResepiInfo = (TabHost) findViewById(R.id.thResepiInfo);

        // setup listener
        lvMenu.setOnItemClickListener(this);
    }

    private void initVars() {
        String namaResepi;

        // init vars
        resepiManager = new ResepiManager(this);
        namaResepi = getIntent().getStringExtra("nama_resepi");
        resepiId = resepiManager.getResepiId(namaResepi);
        resepiInfo = resepiManager.getResepiInfo(resepiId);

        // get resources
        drawerMenuList = getResources().getStringArray(R.array.drawerMenu);
        ikonDrawerMenuList = getResources().obtainTypedArray(R.array.ikonDrawerMenu);

        // setup listview adapter
        lvMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));

        setupTabhost();
        setupUI();
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
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
                break;
        }
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

    private void setupUI() {
        ivResepiImg.setImageBitmap(resepiInfo.getResepiImg());
        tvResepiName.setText(resepiInfo.getName());
        tvRingkasan.setText(resepiInfo.getRingkasan());
        // lvBahan.setAdapter(); FIXME: REMAKE
        // lvLangkah.setAdapter(); FIXME: REMAKE
    }
    // ---------------------------------------------------------------------------------------------
}
