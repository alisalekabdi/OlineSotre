package com.example.pascal_pc.baghali.controller;



import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.productCategory.ProductCategoryActivity;


public class BaghalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int REQ_ID_NEWEST_PRODUCT = 1;
    public static final int REQ_ID_POPULAR_PRODUCT = 2;
    public static final int REQ_ID_MOST_SELL_PRODUCT = 3;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,BaghalActivity.class);
        return intent;
    }

    // TODO: 2/17/2019 use progress bar or progress dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.newest_products_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.newest_products_container,
                            ProductListViewFragment.newInstance(REQ_ID_NEWEST_PRODUCT))
                    .commit();
        }
        if (fragmentManager.findFragmentById(R.id.popular_products_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.popular_products_container,
                            ProductListViewFragment.newInstance(REQ_ID_POPULAR_PRODUCT))
                    .commit();
        }
        if (fragmentManager.findFragmentById(R.id.most_sell_products_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.most_sell_products_container,
                            ProductListViewFragment.newInstance(REQ_ID_MOST_SELL_PRODUCT))
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();

        switch (menuItemId){
            case R.id.nav_product_list :
                Intent intent=ProductCategoryActivity.newIntent(BaghalActivity.this);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
