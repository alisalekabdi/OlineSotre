package com.example.pascal_pc.baghali.controller.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.productInfo.ProductInfoActivity;
import com.example.pascal_pc.baghali.model.ProductList;
import com.example.pascal_pc.baghali.model.product.Product;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListViewFragment extends Fragment {


    private static final String REQ_ID = "req_id";
    private static final String TAG = "tag";
    private int REQUEST_CODE;
    private RecyclerView mProductRecyclerView;
    private ListAdapter mAdapter;

    public static ProductListViewFragment newInstance(int reqId) {

        Bundle args = new Bundle();
        args.putInt(REQ_ID, reqId);

        ProductListViewFragment fragment = new ProductListViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        REQUEST_CODE = getArguments().getInt(REQ_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list_view, container, false);
        mProductRecyclerView = view.findViewById(R.id.product_list_recycler_view);
        mProductRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        /**
         * req_id=1-->for newest list
         * req_id=2-->for popular list
         * req_id=3-->for bestSell list
         */
        try {
            switch (REQUEST_CODE) {

                case 1:
                    List<Product> mRecentProductList = ProductList.getOurInstance().getmRecentProducts();
                    mAdapter = new ListAdapter(mRecentProductList);
                    mProductRecyclerView.setAdapter(mAdapter);
                    break;
                case 2:
                    List<Product> mPopularProductList = ProductList.getOurInstance().getmPopularProducts();
                    mAdapter = new ListAdapter(mPopularProductList);
                    mProductRecyclerView.setAdapter(mAdapter);
                    break;
                case 3:
                    List<Product> mBestSellersList = ProductList.getOurInstance().getmBestSellerProducts();
                    mAdapter = new ListAdapter(mBestSellersList);
                    mProductRecyclerView.setAdapter(mAdapter);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Check your connection and try again", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private class ListHolder extends RecyclerView.ViewHolder {
        private ImageView mProductItemImgView;
        private TextView mProductItemTitleTv, mProductItemPriceTv;
        private Product mProduct;

        public ListHolder(@NonNull View itemView) {
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
            mProductItemTitleTv.setText(mProduct.getName());
            mProductItemPriceTv.setText(mProduct.getPrice() + " Rial");
            if (mProduct.getImages() != null && mProduct.getImages().size() > 0) {
                Picasso.get().load(product.getImages().get(0).getPath()).into(mProductItemImgView);
            }
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ListHolder> {
        private List<Product> mProductList;

        public ListAdapter(List<Product> products) {
            mProductList = products;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).
                    inflate(R.layout.product_item_view, viewGroup, false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder listHolder, int i) {
            Product product = mProductList.get(i);
            listHolder.bind(product);
        }

        @Override
        public int getItemCount() {
            if (mProductList == null) {
                return 0;
            } else
                return mProductList.size();
        }
    }

}
