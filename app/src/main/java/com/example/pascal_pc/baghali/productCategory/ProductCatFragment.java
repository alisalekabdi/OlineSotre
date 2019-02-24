package com.example.pascal_pc.baghali.productCategory;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.controller.productInfo.ProductInfoActivity;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.model.product.Product;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCatFragment extends Fragment {

    private static final String CATEGORY_ID = "category_id";
    private String mCategoryId;
    private CatAdapter adapter;
    private RecyclerView mRecyclerView;
    private IndefinitePagerIndicator mRecyclerViewIndicator;


    public static ProductCatFragment newInstance(String catId) {

        Bundle args = new Bundle();
        args.putString(CATEGORY_ID, catId);
        ProductCatFragment fragment = new ProductCatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ProductCatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getString(CATEGORY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_cat, container, false);
        mRecyclerView = view.findViewById(R.id.specific_cat_recycler_view);
        mRecyclerViewIndicator=view.findViewById(R.id.img_indicator_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerViewIndicator.attachToRecyclerView(mRecyclerView);
        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getProductWithCategory(mCategoryId)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {

//                            Log.i(TAG, "catListSize\t" + String.valueOf(mCategoryId));
                            if (adapter == null) {
                                adapter = new CatAdapter(response.body());
                                mRecyclerView.setAdapter(adapter);
                            }else{
                                adapter.setProducts(response.body());
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(getActivity(), "Request is failed", Toast.LENGTH_SHORT).show();
                    }
                });


        return view;
    }

    private class CatViewHolder extends RecyclerView.ViewHolder {
        private Product mProduct;
        private ImageView mProductItemImgView;
        private TextView mProductItemTitleTv, mProductItemPriceTv;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductItemImgView = itemView.findViewById(R.id.product_item_imageView);
            mProductItemTitleTv = itemView.findViewById(R.id.product_item_title_tv);
            mProductItemPriceTv = itemView.findViewById(R.id.product_item_price_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductInfoActivity.newIntent(getActivity(), mProduct.getId());
                    startActivity(intent);
                }
            });

        }

        public void bind(Product product) {
            mProduct = product;
            mProductItemTitleTv.setText("Name : " + mProduct.getName());
            mProductItemPriceTv.setText("Price : " + mProduct.getPrice());
            if (mProduct.getImages().get(0).getPath() != null && mProduct.getImages().size() > 0) {
                Picasso.get().load(product.getImages().get(0).getPath()).fit().centerCrop().into(mProductItemImgView);

            }
        }
    }

    private class CatAdapter extends RecyclerView.Adapter<CatViewHolder> {
        private List<Product> mProducts;

        public CatAdapter(List<Product> products) {
            mProducts = products;
        }

        public void setProducts(List<Product> products) {
            mProducts = products;
        }

        @NonNull
        @Override
        public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_item_view, viewGroup, false);
            return new CatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CatViewHolder catViewHolder, int i) {
            catViewHolder.bind(mProducts.get(i));
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

}
