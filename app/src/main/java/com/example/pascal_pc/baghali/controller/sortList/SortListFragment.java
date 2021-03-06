package com.example.pascal_pc.baghali.controller.sortList;


import android.app.ProgressDialog;
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
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.productInfo.ProductInfoActivity;
import com.example.pascal_pc.baghali.model.product.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortListFragment extends Fragment {

    private static final String REQ_KEY = "request_key_list";

    public static SortListFragment newInstance(int reqId) {

        Bundle args = new Bundle();
        args.putInt(REQ_KEY, reqId);
        SortListFragment fragment = new SortListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private TextView mTilteTV;
    private CatAdapter adapter;
    private ProgressDialog progressDialog;
    private int mReqId;

    public SortListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReqId=getArguments().getInt(REQ_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort_list, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        mRecyclerView = view.findViewById(R.id.sort_list_recycler_view);
        mRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mTilteTV = view.findViewById(R.id.title_text_view);

        switch (mReqId) {
            case 1:
                mTilteTV.setText("پرفروش ترین ها");
                RetrofitClientInstance.getRetrofitInstance()
                        .create(Api.class)
                        .getBestSellerList()
                        .enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if (adapter == null) {
                                    adapter = new CatAdapter(response.body());
                                    mRecyclerView.setAdapter(adapter);
                                } else {
                                    adapter.setProducts(response.body());
                                    adapter.notifyDataSetChanged();
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Request is failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case 2:
                mTilteTV.setText("جدیدترین ها");
                RetrofitClientInstance.getRetrofitInstance()
                        .create(Api.class)
                        .getProducts()
                        .enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if (adapter == null) {
                                    adapter = new CatAdapter(response.body());
                                    mRecyclerView.setAdapter(adapter);
                                } else {
                                    adapter.setProducts(response.body());
                                    adapter.notifyDataSetChanged();
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Request is failed", Toast.LENGTH_SHORT).show();

                            }
                        });
                break;
            case 3:
                mTilteTV.setText("پربازدیدترین ها");
                RetrofitClientInstance.getRetrofitInstance()
                        .create(Api.class)
                        .getPopularList()
                        .enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if (adapter == null) {
                                    adapter = new CatAdapter(response.body());
                                    mRecyclerView.setAdapter(adapter);
                                } else {
                                    adapter.setProducts(response.body());
                                    adapter.notifyDataSetChanged();
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Request is failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            default:
                break;
        }


        return view;
    }

    private class CatViewHolder extends RecyclerView.ViewHolder {
        private Product mProduct;
        private ImageView mProductItemImgView;
        private TextView mProductItemTitleTv, mProductItemPriceTv;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductItemImgView = itemView.findViewById(R.id.card_view_item_img_view);
            mProductItemTitleTv = itemView.findViewById(R.id.card_view_item_title);
            mProductItemPriceTv = itemView.findViewById(R.id.card_view_item_price);

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
            mProductItemTitleTv.setText( mProduct.getName());
            mProductItemPriceTv.setText(  mProduct.getPrice()+" ریال " );
            if (mProduct.getImages() != null && mProduct.getImages().size() > 0) {
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_item_list_view, viewGroup, false);
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
