package com.example.pascal_pc.baghali.controller.productInfo.cmAndAttribute;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.model.product.Attributes;
import com.example.pascal_pc.baghali.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttributeActivity extends AppCompatActivity {

    private static final String EXTRA_PRODUCT_ID = "com.example.pascal_pc.baghali.controller.productInfo.cmAndAttribute.product_id";

    public static Intent newIntent(Context context, int productId) {
        Intent intent = new Intent(context, AttributeActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    private TextView mNameTv;
    private int mProductId;
    private Product mProduct;
    private List<Attributes> mAttributes;
    private TableLayout mTableRow;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        mProductId = getIntent().getIntExtra(EXTRA_PRODUCT_ID, 87);
        mNameTv = findViewById(R.id.attr_product_name);
        mTableRow = findViewById(R.id.attrubite_coniner);

        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getProduct(mProductId)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful()) {
                            mProduct = response.body();
                            mNameTv.setText(mProduct.getName());
                            mAttributes = mProduct.getAttributes();
                            Log.e("alisalek", "onResponse: " + mAttributes.size());
                            if (mAttributes != null) {
                                for (int i = 0; i < mAttributes.size(); i++) {
                                    TableRow tableRow = new TableRow(AttributeActivity.this);
                                    TextView column2 = new TextView(AttributeActivity.this);
                                    TextView column1 = new TextView(AttributeActivity.this);

                                    column1.setPadding(8, 8, 8, 8);
                                    column2.setPadding(8, 8, 8, 8);
                                    column1.setTextSize(10);
                                    column2.setTextSize(10);
                                    column1.setText("\t" + "\t" + mAttributes.get(i).getName());
                                    column2.setText(mAttributes.get(i).getOptions().get(0));
                                    tableRow.addView(column2);
                                    tableRow.addView(column1);
                                    mTableRow.addView(tableRow);

                                }
                                progressDialog.dismiss();
                            }
                        } else
                            Toast.makeText(AttributeActivity.this, "Connection is failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });


    }
}
