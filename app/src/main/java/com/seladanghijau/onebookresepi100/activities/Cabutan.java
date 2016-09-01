package com.seladanghijau.onebookresepi100.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.adapters.KategoriResepiListAdapter;
import com.seladanghijau.onebookresepi100.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi100.dto.Resepi;
import com.seladanghijau.onebookresepi100.provider.ILoader;

import java.lang.ref.WeakReference;

public class Cabutan extends AppCompatActivity implements ILoader, View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    ListView lvMenu;
    View actionbarView;
    ImageButton ibMenu, ibSearch;
    TextView tvWebAdddress, tvTitle;
    Button btnMuatTurun;
    DrawerLayout drawer;

    // variables
    String webAddress, app200Address;
    String[] drawerMenuList;
    WeakReference<ListView> lvMenuWeakRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabutan);

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
        ibSearch.setVisibility(View.GONE);
        tvTitle = (TextView) actionbarView.findViewById(R.id.tvTitle);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        tvWebAdddress = (TextView) findViewById(R.id.tvWebAdddress);
        btnMuatTurun = (Button) findViewById(R.id.btnMuatTurun);
        drawer = (DrawerLayout) findViewById(R.id.drawer);

        tvTitle.setText("Cabutan");

        // setup listener
        ibMenu.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
        tvWebAdddress.setOnClickListener(this);
        btnMuatTurun.setOnClickListener(this);

        lvMenuWeakRef = new WeakReference<>(lvMenu);
    }

    private void initVars() {
        webAddress = "https://www.onebook.com.my/v1/cabutan.html";
        app200Address = "https://play.google.com/store?hl=en"; // testing purpose

        new DrawerMenuListAsyncTask(this, this).execute();

        lvMenu.invalidate();
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
            case R.id.tvWebAdddress:
                Uri webUri = Uri.parse(webAddress).buildUpon().build();

                buttonEffect(tvWebAdddress);
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, webUri), "Choose web browser"));
                break;
            case R.id.btnMuatTurun:
                Uri app200Uri = Uri.parse(app200Address).buildUpon().build();

                buttonEffect(btnMuatTurun);
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, app200Uri), "Choose web browser"));
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
        }
    }
    // ---------------------------------------------------------------------------------------------

    // loader interface methods --------------------------------------------------------------------
    public void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList) {
        ListView listViewMenu;

        listViewMenu = lvMenuWeakRef.get();
        listViewMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));

        this.drawerMenuList = drawerMenuList;
    }

    public void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList) {}
    public void onLoad(int[] resepiCount, String[] kategoriResepiList, Bitmap[] imejKategoriResepiList) {}
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

    private void buttonEffect(View view) {
        AlphaAnimation buttonClick = new AlphaAnimation(1f, 0.5f);

        view.startAnimation(buttonClick);
    }
    // ---------------------------------------------------------------------------------------------
}
