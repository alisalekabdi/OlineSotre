package com.example.pascal_pc.baghali.controller.home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.cart.CartActivity;
import com.example.pascal_pc.baghali.controller.productCategory.ProductCategoryActivity;
import com.example.pascal_pc.baghali.controller.registerCustomer.RegisterCustActivity;
import com.example.pascal_pc.baghali.controller.searchProduct.SearchProductActivity;
import com.example.pascal_pc.baghali.controller.sortList.SortListActivity;
import com.example.pascal_pc.baghali.controller.viewPager.ViewPagerFragment;
import com.example.pascal_pc.baghali.dataBase.CartLab;
import com.example.pascal_pc.baghali.dataBase.prefs.UserPrefrences;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int REQ_ID_NEWEST_PRODUCT = 1;
    public static final int REQ_ID_POPULAR_PRODUCT = 2;
    public static final int REQ_ID_MOST_SELL_PRODUCT = 3;


    private DrawerLayout drawer;
    private NavigationView navigationView;

    private TextView mUserName, mPopullarListBtn, mNewestListBtn;
    private ImageView mAddUser;
    private ViewPager mSpecialVP;
    private TabLayout mSpecialTabLayout;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
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
        @SuppressLint("ResourceType")
        View navigationHeader = navigationView.getHeaderView(0);
        mUserName = navigationHeader.findViewById(R.id.user_name);
        mUserName.setText(UserPrefrences.getPrefFirstName(this));
        mSpecialVP = findViewById(R.id.special_img_view_pager);
        mSpecialTabLayout = findViewById(R.id.special_indicator);
        mAddUser = navigationHeader.findViewById(R.id.add_user);
        mNewestListBtn = findViewById(R.id.newest_list_tv);
        mPopullarListBtn = findViewById(R.id.popular_list_tv);

        mNewestListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = SortListActivity.newIntent(HomeActivity.this,
                        2);
                startActivity(intent3);
            }
        });

        mPopullarListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = SortListActivity.newIntent(HomeActivity.this,
                        3);
                startActivity(intent4);

            }
        });

        mSpecialVP.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return ViewPagerFragment.newInstance(i);
            }

            @Override
            public int getCount() {
                return 5;
            }

        });
        mSpecialTabLayout.setupWithViewPager(mSpecialVP);

        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=RegisterCustActivity.newIntent(HomeActivity.this);
                startActivity(intent);
            }
        });

        drawer = findViewById(R.id.drawer_layout);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.tab_search:
                Intent intent = SearchProductActivity.newIntent(this);
                startActivity(intent);
                return true;
            case R.id.tab_shopping:
                if (CartLab.getInstance().getCarts().size() > 0) {
                    Intent intent1 = CartActivity.newIntent(this);
                    startActivity(intent1);
                } else
                    Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();
        int REQ_KEY_DEFAUlT = 0;
        int REQ_KEY_BEST_SELL = 1;
        int REQ_KEY_RECENT_PRODUCT = 2;
        int REQ_KEY_POPULAR_PRODUCT = 3;

        switch (menuItemId) {
            case R.id.nav_product_list:
                Intent intent = ProductCategoryActivity.newIntent(HomeActivity.this,
                        REQ_KEY_DEFAUlT);
                startActivity(intent);
                break;
            case R.id.nav_home:
                Intent intent1 = HomeActivity.newIntent(this);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.nav_best_sell:
                Intent intent2 = SortListActivity.newIntent(HomeActivity.this,
                        REQ_KEY_BEST_SELL);
                startActivity(intent2);
                break;
            case R.id.nav_recent:
                Intent intent3 = SortListActivity.newIntent(HomeActivity.this,
                        REQ_KEY_RECENT_PRODUCT);
                startActivity(intent3);
                break;
            case R.id.nav_popular:
                Intent intent4 = SortListActivity.newIntent(HomeActivity.this,
                        REQ_KEY_POPULAR_PRODUCT);
                startActivity(intent4);
                break;
            case R.id.about_market:
                Toast.makeText(this, "Develop By Ali Salek", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tab_cart:
                if (CartLab.getInstance().getCarts().size() > 0) {
                    Intent intent5 = CartActivity.newIntent(HomeActivity.this);
                    startActivity(intent5);
                } else
                    Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
