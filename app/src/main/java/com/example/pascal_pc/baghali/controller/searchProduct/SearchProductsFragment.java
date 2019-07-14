package com.example.pascal_pc.baghali.controller.searchProduct;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.model.product.Attributes;
import com.example.pascal_pc.baghali.model.product.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductsFragment extends Fragment {

    private List<Product> mProductList = new ArrayList<>();
    private List<Product> mAuxiliaryProductList;
    private List<Product> productList2 = new ArrayList<>();
    private ProgressDialog progressDialog;

    private boolean mAttributeSelected = false;

    public SearchProductsFragment() {
        // Required empty public constructor
    }

    public static SearchProductsFragment newInstance() {
        SearchProductsFragment fragment = new SearchProductsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private SearchView mSearchView;

    private Spinner sortSpinner;
    private Spinner filterSpinner;
    private ArrayAdapter<CharSequence> mSortAdapter;
    private ArrayAdapter<CharSequence> mFilterAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_products, container, false);

        findItem(view);
        setSpinner();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchString = query;

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Wait while loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                RetrofitClientInstance.getRetrofitInstance()
                        .create(Api.class)
                        .getProductWithSearch(searchString)
                        .enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if (response.isSuccessful()) {
                                    mProductList = response.body();
                                    mAdapter = new SearchAdapter(mProductList);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    sortSpinner.setSelection(0);
                                    filterSpinner.setSelection(0);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                progressDialog.dismiss();
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

    private void setSpinner() {
        mSortAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort_product, R.layout.support_simple_spinner_dropdown_item);
        mSortAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(mSortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String sortItemText = sortSpinner.getSelectedItem().toString();
                mAuxiliaryProductList = new ArrayList<>();
                if (productList2.size() > 0) {
                    mAuxiliaryProductList = productList2;
                } else {
                    mAuxiliaryProductList = mProductList;
                }

                switch (position) {
                    case 0:
                        break;
                    case 1:
                        Collections.sort(mAuxiliaryProductList, new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                if(Integer.parseInt(o1.getTotal_sales())>Integer.parseInt(o2.getTotal_sales()))
                                    return -1;
                                else if(Integer.parseInt(o1.getTotal_sales())<Integer.parseInt(o2.getTotal_sales()))
                                    return 1;
                                else
                                    return 0;

                            }
                        });
                        break;
                    case 2:
                        Collections.sort(mAuxiliaryProductList, new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                if(Integer.parseInt(o1.getPrice())>Integer.parseInt(o2.getPrice()))
                                    return -1;
                                else if(Integer.parseInt(o1.getPrice())<Integer.parseInt(o2.getPrice()))
                                    return 1;
                                else
                                    return 0;
                            }
                        });
                        break;
                    case 3:
                        Collections.sort(mAuxiliaryProductList, new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                if(Integer.parseInt(o1.getPrice())>Integer.parseInt(o2.getPrice()))
                                    return 1;
                                else if(Integer.parseInt(o1.getPrice())<Integer.parseInt(o2.getPrice()))
                                    return -1;
                                else
                                    return 0;
                            }
                        });
                        break;
                    case 4:
                        Collections.sort(mAuxiliaryProductList, new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                return o1.getCreatedDate().compareToIgnoreCase(o2.getCreatedDate());
                            }
                        });
                        break;
                }
                if (position == 0) {
                    mAuxiliaryProductList = mProductList;
                }
                if (mAdapter != null) {
                    mAdapter.setProducts(mAuxiliaryProductList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFilterAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.filter_by_color, R.layout.support_simple_spinner_dropdown_item);
        mFilterAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        filterSpinner.setAdapter(mFilterAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = null;
                productList2.clear();
                switch (position) {
                    case 0:
//                        view.setBackgroundColor(Color.WHITE);
                        color = null;
                        break;
                    case 1:
                        color = "مشکی";
                        mAttributeSelected = true;
                        view.setBackgroundColor((Color.BLACK));
                        break;
                    case 2:
                        color = "سفید";
                        mAttributeSelected = true;
                        view.setBackgroundColor(Color.WHITE);
//                        parent.getChildAt(2).setBackgroundColor(Color.WHITE);
                        break;
                    case 3:
                        color = "نقره ای";
                        mAttributeSelected = true;
                        view.setBackgroundColor(R.color.Silver);
//                        parent.getChildAt(3).setBackgroundColor(R.color.Silver);
                        break;
                    case 4:
//                        parent.getChildAt(4).setBackgroundColor(R.color.Green);
                        color = "سبز";
                        mAttributeSelected = true;
                        view.setBackgroundColor(Color.GREEN);
                        break;
                    case 5:
//                        parent.getChildAt(5).setBackgroundColor(R.color.Yellow);
                        color = "زرد";
                        mAttributeSelected = true;
                        view.setBackgroundColor(Color.YELLOW);
                        break;
                    case 6:
                        color = "خاکستری";
                        mAttributeSelected = true;
                        view.setBackgroundColor(Color.GRAY);
//                        parent.getChildAt(6).setBackgroundColor(Color.GRAY);
                        break;
                    case 7:
//                        parent.getChildAt(7).setBackgroundColor(Color.RED);
                        color = "قرمز";
                        mAttributeSelected = true;
                        view.setBackgroundColor(Color.RED);
                        break;
                    case 8:
//                        parent.getChildAt(8).setBackgroundColor(Color.BLUE);
                        color = "آبی";
                        mAttributeSelected = true;
                        view.setBackgroundColor(Color.BLUE);
                        break;
                }
                if (color == null) {
                    productList2 = mProductList;
                } else {
                    for (int i = 0; i < mProductList.size(); i++) {
                        List<Attributes> options = mProductList.get(i).getAttributes();
                        outer:
                        for (Attributes attributes : options) {
                            if (attributes.getOptions().contains(color)) {
                                productList2.add(mProductList.get(i));

                            }
                        }
                    }
                }
                Log.e("alisalek", "onItemSelected: " + mAuxiliaryProductList.size() + "");
                if (mAdapter != null) {
                    mAdapter.setProducts(productList2);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.getChildAt(0).setBackgroundColor(Color.BLACK);

            }
        });
    }

    private void findItem(View view) {
        mSearchView = view.findViewById(R.id.search_product);
        EditText searchEditText = view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);
        mRecyclerView = view.findViewById(R.id.search_view_rv);
        sortSpinner = view.findViewById(R.id.sort_spinner);
        filterSpinner = view.findViewById(R.id.filter_spinner);
    }

    private class SearchHolder extends RecyclerView.ViewHolder {
        private Product mProduct;
        private ImageView mProductImg;
        private TextView mTitle;
        private TextView mPrice;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            mProductImg = itemView.findViewById(R.id.search_product_item_imageView);
            mTitle = itemView.findViewById(R.id.search_product_item_title_tv);
            mPrice = itemView.findViewById(R.id.search_product_item_price_tv);
        }

        public void bind(Product product) {
            progressDialog.dismiss();
            mProduct = product;
            mTitle.setText(mProduct.getName());
            mPrice.setText(mProduct.getPrice());
            if (mProduct.getImages() != null && mProduct.getImages().size() > 0) {
                Picasso.get().load(mProduct.getImages().get(0).getPath()).into(mProductImg);
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.search_item_view, viewGroup, false);
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
