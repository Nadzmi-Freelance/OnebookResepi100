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
import android.widget.ListView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi100.R;
import com.seladanghijau.onebookresepi100.adapters.DrawerMenuListAdapter;

public class TentangKami extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    TextView tvWebPage, tvEmail;
    ActionBar actionBar;
    ListView lvMenu;

    // variables
    String webAddress, emailAddress;
    String[] drawerMenuList;
    TypedArray ikonDrawerMenuList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_kami);

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
        tvWebPage = (TextView) findViewById(R.id.tvWebPage);
        tvEmail = (TextView) findViewById(R.id.tvEmail);

        // setup listener
        lvMenu.setOnItemClickListener(this);
        tvWebPage.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
    }

    private void initVars() {
        webAddress = "https://www.onebook.com.my";
        emailAddress = "onepage2u@gmail.com";

        // get resources
        drawerMenuList = getResources().getStringArray(R.array.drawerMenu);
        ikonDrawerMenuList = getResources().obtainTypedArray(R.array.ikonDrawerMenu);

        // setup listview adapter
        lvMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));
    }

    public void onClick(View v) {
        switch (v.getId()) {
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
