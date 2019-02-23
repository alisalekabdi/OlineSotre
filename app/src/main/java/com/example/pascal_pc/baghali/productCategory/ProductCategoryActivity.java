package com.example.pascal_pc.baghali.productCategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.model.category.ProductCatergory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryActivity extends AppCompatActivity {

    private static final String TAG ="tag";
    private ViewPager mViewPager;
    private List<ProductCatergory> prCatList;
    private TabLayout mTabLayout;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ProductCategoryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout_category);


        RetrofitClientInstance
                .getRetrofitInstance()
                .create(Api.class)
                .getProductCategory()
                .enqueue(new Callback<List<ProductCatergory>>() {
                    @Override
                    public void onResponse(Call<List<ProductCatergory>> call, Response<List<ProductCatergory>> response) {

                        if (response.isSuccessful()) {
                            prCatList =response.body();
                            Log.i(TAG, "catListSize\t"+String.valueOf(prCatList.size()));

                            Log.i(TAG, "catListName\t"+prCatList.get(0).getName()+"\n"
                                    +prCatList.get(1).getName()+"\n"
                                    +prCatList.get(2).getName()+"\n"
                                    +prCatList.get(3).getName()+"\n"
                                    +prCatList.get(4).getName()+"\n"
                                    +prCatList.get(5).getName()+"\n"
                                    +prCatList.get(6).getName()+"\n"
                                    +prCatList.get(7).getName()+"\n"
                                    +prCatList.get(8).getName()+"\n"
                            );

                            mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                                @Override
                                public Fragment getItem(int i) {
                                    return ProductCatFragment.newInstance(prCatList.get(i).getId());
                                }

                                @Nullable
                                @Override
                                public CharSequence getPageTitle(int position) {
                                    return prCatList.get(position).getName();
                                }

                                @Override
                                public int getCount() {
                                    return prCatList.size();
                                }
                            });
                            mTabLayout.setTabTextColors(R.color.White, R.color.Red);
                            mTabLayout.setupWithViewPager(mViewPager);
                        }


                    }

                    @Override
                    public void onFailure(Call<List<ProductCatergory>> call, Throwable t) {
                        Toast.makeText(ProductCategoryActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "ERROR PRODUCT LIST\n"+t.getMessage());

                    }
                });
    }
}
