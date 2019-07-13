package com.example.pascal_pc.baghali.controller.productInfo.cmAndAttribute;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.registerCustomer.RegisterCustActivity;
import com.example.pascal_pc.baghali.dataBase.prefs.UserPrefrences;
import com.example.pascal_pc.baghali.model.customer.Customer;
import com.example.pascal_pc.baghali.model.product.Product;
import com.example.pascal_pc.baghali.model.product.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    private static final String EXTRA_CM_PRODUCT_ID = "com.example.pascal_pc.baghali.controller.productInfo.cmAndAttribute.cm_product_id";

    public static Intent newIntent(Context context, int productId) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(EXTRA_CM_PRODUCT_ID, productId);
        return intent;
    }

    private TextView mProductName, mAvgRating, mRatingCount;
    private RatingBar mProductRb;
    private FloatingActionButton mAddCmFAB;
    private RecyclerView mRecyclerView;
    private int mProductId;
    private Product mProduct;
    private CmAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mProductId = getIntent().getIntExtra(EXTRA_CM_PRODUCT_ID, 87);
        findItem();
        getProduct();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getProductReviewsById(mProductId)
                .enqueue(new Callback<List<Review>>() {
                    @Override
                    public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                        if (response.isSuccessful()) {
                            List<Review> reviews = response.body();
                            mAdapter = new CmAdapter(reviews);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Review>> call, Throwable t) {

                    }
                });

        mAddCmFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserPrefrences.getPrefUserName(CommentActivity.this).length()>0){
                final EditText editText=new EditText(CommentActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                builder.setTitle("Add comment")
                        .setIcon(R.drawable.ic_comment_dark)
                        .setView(editText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=UserPrefrences.getPrefFirstName(CommentActivity.this);
                                String lastName=UserPrefrences.getPrefLastName(CommentActivity.this);
                                String email=UserPrefrences.getPrefUserEmail(CommentActivity.this);
                                RetrofitClientInstance.getRetrofitInstance()
                                        .create(Api.class)
                                        .sendReview(mProductId,editText.getText().toString(),
                                                name+" "+lastName,email)
                                        .enqueue(new Callback<Customer>() {
                                            @Override
                                            public void onResponse(Call<Customer> call, Response<Customer> response) {
                                                if(response.isSuccessful()){
                                                    Toast.makeText(CommentActivity.this, "Your comment is registered", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Customer> call, Throwable t) {

                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                }else {
                    Toast.makeText(CommentActivity.this, "You should first register", Toast.LENGTH_SHORT).show();
                    Intent intent=RegisterCustActivity.newIntent(CommentActivity.this);
                    startActivity(intent);
                }

            }
        });
    }

    private void getProduct() {
        RetrofitClientInstance.getRetrofitInstance().create(Api.class).getProduct(mProductId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    mProduct = response.body();
                    mProductName.setText(mProduct.getName());
                    mAvgRating.setText(mProduct.getAverage_rating()+"/5");
                    mRatingCount.setText(mProduct.getRating_count()+" people have rated");
                    mProductRb.setRating(Float.parseFloat(mProduct.getAverage_rating()));

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(CommentActivity.this, "Connection is failed ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findItem() {
        mProductName = findViewById(R.id.cm_item_view);
        mAvgRating = findViewById(R.id.cm_average_rating_tv);
        mRatingCount = findViewById(R.id.cm_count_rating_tv);
        mProductRb = findViewById(R.id.cm_rating_bar);
        mAddCmFAB = findViewById(R.id.add_cm_fab);
        mRecyclerView = findViewById(R.id.cm_item_rv);
    }

    private class CmViewHolder extends RecyclerView.ViewHolder {
        private TextView mCmViewer, mCm;

        public CmViewHolder(@NonNull View itemView) {
            super(itemView);
            mCmViewer = itemView.findViewById(R.id.cm_reviewer);
            mCm = itemView.findViewById(R.id.cm_item_tv);
        }

        public void bind(Review cm) {
            mCmViewer.setText(cm.getReviewer_name());
            String review=cm.getReview();
            if (review.length()>0){
                int length=review.length()-5;
                review=review.substring(3,length);
            }
            mCm.setText(review);
        }
    }

    private class CmAdapter extends RecyclerView.Adapter<CmViewHolder> {
        private List<Review> mReviewList;

        public CmAdapter(List<Review> reviewList) {
            mReviewList = reviewList;
        }

        public void setReviewList(List<Review> reviewList) {
            mReviewList = reviewList;
        }

        @NonNull
        @Override
        public CmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(CommentActivity.this).inflate(R.layout.comment, viewGroup, false);
            return new CmViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CmViewHolder cmViewHolder, int i) {
            cmViewHolder.bind(mReviewList.get(i));
        }

        @Override
        public int getItemCount() {
            return mReviewList.size();
        }
    }
}
