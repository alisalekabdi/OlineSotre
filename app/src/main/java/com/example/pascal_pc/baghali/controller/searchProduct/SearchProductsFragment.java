package com.example.pascal_pc.baghali.controller.searchProduct;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.model.product.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductsFragment extends Fragment {


    public SearchProductsFragment() {
        // Required empty public constructor
    }

    public static SearchProductsFragment newInstance() {
        SearchProductsFragment fragment = new SearchProductsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_products, container, false);

        mSearchView = view.findViewById(R.id.searchView);
        mRecyclerView = view.findViewById(R.id.search_view_rv);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchString = query;
                RetrofitClientInstance.getRetrofitInstance()
                        .create(Api.class)
                        .getProductWithSearch(searchString)
                        .enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if (response.isSuccessful()) {
                                    List<Product> products = response.body();
                                    mAdapter=new SearchAdapter(products);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }

    private class SearchHolder extends RecyclerView.ViewHolder {
        private Product mProduct;
        private ImageView mProductImg;
        private TextView mTitle;
        private TextView mPrice;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            mProductImg = itemView.findViewById(R.id.product_item_imageView);
            mTitle = itemView.findViewById(R.id.product_item_title_tv);
            mPrice = itemView.findViewById(R.id.product_item_price_tv);
        }

        public void bind(Product product) {
            mProduct = product;
            mTitle.setText(mProduct.getName());
            mPrice.setText(mProduct.getPrice());
            if (mProduct.getImages() != null && mProduct.getImages().size() > 0) {
                Picasso.get().load(mProduct.getImages().get(0).getPath()).fit().centerCrop().into(mProductImg);
            }
        }
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {
        private List<Product> mProducts;

        public SearchAdapter(List<Product> products) {
            mProducts = products;
        }

        public void setProducts(List<Product> products) {
            mProducts = products;
        }

        @NonNull
        @Override
        public SearchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_item_view, viewGroup, false);
            return new SearchHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchHolder searchHolder, int i) {
            Product product = mProducts.get(i);
            searchHolder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

}
