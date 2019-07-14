package com.example.pascal_pc.baghali.controller.productInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;

import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.productInfo.cmAndAttribute.AttributeActivity;
import com.example.pascal_pc.baghali.controller.productInfo.cmAndAttribute.CommentActivity;
import com.example.pascal_pc.baghali.dataBase.CartLab;
import com.example.pascal_pc.baghali.model.product.Attributes;
import com.example.pascal_pc.baghali.model.product.Image;
import com.example.pascal_pc.baghali.model.product.Product;
import com.example.pascal_pc.baghali.model.dataBaseModel.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

import cz.intik.overflowindicator.OverflowPagerIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductInfFragment extends Fragment {

    private static final String PRODUCT_ID_KEY = "product_id";
    private int mProductId;
    private ImagesAdapter mAdapter;
    private Button mAddToCart;
    private ImageButton mExpandBtn,mCollapseBtn;
    private RecyclerView mRecyclerView;
    private TextView mTitle, mPrice, mDescription;
    private Product product;
    private Button mCmBtn,mAttributeBtn;
    private OverflowPagerIndicator mIndicator;
    private List<Attributes> mAttributes;
    private TableLayout mTableRow;
    private ProgressDialog progressDialog;


    public static ProductInfFragment newInstance(int productid) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID_KEY, productid);

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
        mDescription = view.findViewById(R.id.description_tv);
        mAddToCart = view.findViewById(R.id.add_to_cart);
        mIndicator=view.findViewById(R.id.product_img_indicator);
        mExpandBtn=view.findViewById(R.id.expand_des_tv);
        mCollapseBtn=view.findViewById(R.id.collapse_des_tv);
        mCmBtn=view.findViewById(R.id.cm_btn);
        mAttributeBtn=view.findViewById(R.id.attribute_btn);
        mDescription.setMaxLines(5);

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

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        findItem(view);

        mCmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=CommentActivity.newIntent(getActivity(),mProductId);
                startActivity(intent);
            }
        });
        mAttributeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=AttributeActivity.newIntent(getActivity(),mProductId);
                startActivity(intent);
            }
        });
        mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartLab.getInstance().checkCart(mProductId) == null) {
                    Cart cart = new Cart();
                    cart.setMProductId(mProductId);
                    cart.setMName(product.getName());
                    cart.setMPrice(Float.valueOf(product.getPrice()));
                    cart.setMImgPath(product.getImages().get(0).getPath());
                    cart.setMProductCount(1);
                    CartLab.getInstance().addCart(cart);
                    Toast.makeText(getActivity(), "Added to your cart", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "This product already has been added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCollapseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCollapseBtn.setVisibility(View.INVISIBLE);
                mExpandBtn.setVisibility(View.VISIBLE);
                mDescription.setMaxLines(5);
            }
        });
        mExpandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCollapseBtn.setVisibility(View.VISIBLE);
                mExpandBtn.setVisibility(View.INVISIBLE);
                mDescription.setMaxLines(Integer.MAX_VALUE);
            }
        });
        mRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getProduct(mProductId)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful()) {
                            product = response.body();
                            if (mAdapter == null) {
                                mAdapter = new ImagesAdapter(product.getImages());
                                mRecyclerView.setAdapter(mAdapter);
                                mIndicator.attachToRecyclerView(mRecyclerView);
                            } else {
                                mAdapter.setImageList(product.getImages());
                                mAdapter.notifyDataSetChanged();
                            }
                            mTitle.setText(product.getName());
                            mPrice.setText(product.getPrice()+" ريال");
//                            mColorTv.setText("Color: "+product.get);

                            mDescription.setText(product.getDescription());

                                                mAttributes = product.getAttributes();
//                                                Log.e("alisalek", "onResponse: " + mAttributes.size());
//                                                if (mAttributes != null) {
//                                                    for (int i = 0; i < mAttributes.size(); i++) {
//                                                        TableRow tableRow = new TableRow(getActivity());
//                                                        TextView column2 = new TextView(getActivity());
//                                                        TextView column1 = new TextView(getActivity());
//
//                                                        column1.setPadding(8, 8, 8, 8);
//                                                        column2.setPadding(8, 8, 8, 8);
//                                                        column1.setTextSize(10);
//                                                        column2.setTextSize(10);
//                                                        column1.setText("\t" + "\t" + mAttributes.get(i).getName());
//                                                        column2.setText(mAttributes.get(i).getOptions().get(0));
//                                                        tableRow.addView(column2);
//                                                        tableRow.addView(column1);
//                                                        mTableRow.addView(tableRow);
//
//                                                    }
//                                                }




                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        progressDialog.dismiss();
                        final Snackbar snackbar = Snackbar.make(view,
                                getResources().getString(R.string.failed_message), Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ProductInfFragment.this.onResume();
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                    }
                });

        return view;
    }

    private class ImagesHolder extends RecyclerView.ViewHolder {

        private ImageView mProductImageView;

        public ImagesHolder(View itemView) {
            super(itemView);
            mProductImageView = itemView.findViewById(R.id.product_item_imageView);
        }

        public void bind(Image image) {
            Picasso.get().load(image.getPath()).into(mProductImageView);
            progressDialog.dismiss();
        }
    }

    private class ImagesAdapter extends RecyclerView.Adapter<ImagesHolder> {
        List<Image> mImageList;

        public ImagesAdapter(List<Image> imageList) {
            mImageList = imageList;
        }

        public void setImageList(List<Image> imageList) {
            mImageList = imageList;
        }

        @Override
        public ImagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.image_item_view, parent, false);
            ImagesHolder holder = new ImagesHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ImagesHolder holder, int position) {
            Image image = mImageList.get(position);
            mIndicator.onPageSelected(holder.getAdapterPosition());
            holder.bind(image);

        }

        @Override
        public int getItemCount() {
            return mImageList.size();
        }
    }
}
