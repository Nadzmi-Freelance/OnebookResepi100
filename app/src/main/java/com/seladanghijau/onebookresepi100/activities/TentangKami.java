package com.seladanghijau.onebookresepi100.activities;

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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi100.dto.Resepi;
import com.seladanghijau.onebookresepi100.provider.ILoader;

import java.lang.ref.WeakReference;

public class TentangKami extends AppCompatActivity implements ILoader, View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    View actionbarView;
    ImageButton ibMenu, ibSearch;
    TextView tvWebPage, tvEmail, tvTitle;
    ListView lvMenu;
    DrawerLayout drawer;

    // variables
    String webAddress, emailAddress;
    String[] drawerMenuList;
    WeakReference<ListView> lvMenuWeakRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_kami);

        initViews();
        initVars();
    }

    // initialize ----------------------------------------------------------------------------------
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
        ibSearch.setVisibility(View.GONE);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        tvWebPage = (TextView) findViewById(R.id.tvWebPage);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        drawer = (DrawerLayout) findViewById(R.id.drawer);

        tvTitle.setText("Tentang Kami");

        // setup listener
        ibMenu.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
        tvWebPage.setOnClickListener(this);
        tvEmail.setOnClickListener(this);

        lvMenuWeakRef = new WeakReference<>(lvMenu);
    }

    private void initVars() {
        webAddress = "https://www.onebook.com.my";
        emailAddress = "onepage2u@gmail.com";

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
            case R.id.tvWebPage:
                Uri webUri = Uri.parse(webAddress).buildUpon().build();
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, webUri), "Choose web browser"));
                break;
            case R.id.tvEmail:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailAddress });

                if(emailIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(emailIntent);
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
