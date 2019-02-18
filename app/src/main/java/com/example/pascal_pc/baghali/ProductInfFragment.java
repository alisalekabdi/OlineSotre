package com.example.pascal_pc.baghali;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.model.*;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductInfFragment extends Fragment {

    private static final String PRODUCT_ID_KEY ="product_id";
    private int mProductId;
    private ImagesAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mTitle,mPrice,mDescription,mTotalSales,mRatingCount,mColorTv;
    private RatingBar mRatingBar;


    public static ProductInfFragment newInstance(int productid) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID_KEY,productid);

        ProductInfFragment fragment = new ProductInfFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductInfFragment() {
    }

    private void findItem(View view) {
        mRecyclerView = view.findViewById(R.id.img_view_recycler);
        mTitle = view.findViewById(R.id.title_tv);
        mPrice = view.findViewById(R.id.price_tv);
        mTotalSales = view.findViewById(R.id.total_sell_tv);
        mRatingCount=view.findViewById(R.id.rating_count_tv);
        mRatingBar = view.findViewById(R.id.product_ratingBar);
        mDescription = view.findViewById(R.id.description_tv);
        mColorTv=view.findViewById(R.id.color_tv);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getInt(PRODUCT_ID_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_product_info, container, false);
        findItem(view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
//        RetrofitClientInstance.getRetrofitInstance()
//                .create(Api.class)
//                .getProduct(mProductId)
//                .enqueue(new Callback<Product>() {
//                    @Override
//                    public void onResponse(Call<Product> call, Response<Product> response) {
//                        if(response.isSuccessful()){
//                            Product product=response.body();
//                            if(mAdapter==null){
//                                mAdapter=new ImagesAdapter(product.getImages());
//                            }else{
//                                mAdapter.setImageList(product.getImages());
//                                mAdapter.notifyDataSetChanged();
//                            }
//                            mTitle.setText("Title :\t"+product.getName());
//                            mPrice.setText("Price :"+product.getPrice());
////                            mColorTv.setText("Color: "+product.get);
//                            // TODO: 2/18/2019 get colorList and set them with suitable component
//                            mTotalSales.setText("Total sells :\t"+product.getTotal_sales());
//                            mRatingCount.setText("Rating count: \t"+product.getRating_count());
//                            mRatingBar.setRating(Float.valueOf(product.getAverage_rating()));
//                            mDescription.setText("Description :\t"+product.getDescription());


//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Product> call, Throwable t) {
//                        final Snackbar snackbar=Snackbar.make(view,
//                                getResources().getString(R.string.failed_message), Snackbar.LENGTH_INDEFINITE);
//                        snackbar.setAction("OK", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                snackbar.dismiss();
//                            }
//                        });
//                        snackbar.show();
//                    }
//                });

        return view;
    }

    private class ImagesHolder extends RecyclerView.ViewHolder{

        private ImageView mProductImageView;

        public ImagesHolder(View itemView) {
            super(itemView);
            mProductImageView=itemView.findViewById(R.id.product_item_imageView);
        }

        public void bind(Image image) {
            Picasso.get().load(image.getPath()).into(mProductImageView);
        }
    }

    private class ImagesAdapter extends RecyclerView.Adapter<ImagesHolder>{
        List<Image> mImageList;

        public ImagesAdapter(List<Image> imageList) {
            mImageList = imageList;
        }

        public void setImageList(List<Image> imageList) {
            mImageList = imageList;
        }

        @Override
        public ImagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.image_item_view,parent);
            ImagesHolder holder=new ImagesHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ImagesHolder holder, int position) {
            Image image=mImageList.get(position);
            holder.bind(image);
        }

        @Override
        public int getItemCount() {
            return mImageList.size();
        }
    }
}
