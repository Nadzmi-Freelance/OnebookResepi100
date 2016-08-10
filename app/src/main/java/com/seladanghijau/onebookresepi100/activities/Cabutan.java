package com.seladanghijau.onebookresepi100.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi100.adapters.KategoriResepiListAdapter;

public class Cabutan extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    ListView lvMenu;
    ActionBar actionBar;
    TextView tvWebAdddress;
    Button btnMuatTurun;

    // variables
    String webAddress, app200Address;
    String[] drawerMenuList;
    TypedArray ikonDrawerMenuList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabutan);

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
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        tvWebAdddress = (TextView) findViewById(R.id.tvWebAdddress);
        btnMuatTurun = (Button) findViewById(R.id.btnMuatTurun);

        // setup listener
        lvMenu.setOnItemClickListener(this);
        tvWebAdddress.setOnClickListener(this);
        btnMuatTurun.setOnClickListener(this);
    }

    private void initVars() {
        webAddress = "https://www.onebook.com.my";
        app200Address = "https://play.google.com/store?hl=en"; // testing purpose

        // get resources
        drawerMenuList = getResources().getStringArray(R.array.drawerMenu);
        ikonDrawerMenuList = getResources().obtainTypedArray(R.array.ikonDrawerMenu);

        // setup listview adapter
        lvMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvWebAdddress:
                Uri webUri = Uri.parse(webAddress).buildUpon().build();
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, webUri), "Choose web browser"));
                break;
            case R.id.btnMuatTurun:
                Uri app200Uri = Uri.parse(app200Address).buildUpon().build();
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
                else if((position >= 3) || (position <= 22))
                    startActivity(new Intent(this, ResepiList.class).putExtra("kategori_resepi", drawerMenuList[position]));
                else if(position == 23)
                    startActivity(new Intent(this, Cabutan.class));
                else if(position == 24)
                    startActivity(new Intent(this, TentangKami.class));
                break;
        }
    }
}
