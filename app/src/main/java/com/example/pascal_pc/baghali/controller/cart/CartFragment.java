package com.example.pascal_pc.baghali.controller.cart;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.finalizeOrder.OrderActivity;
import com.example.pascal_pc.baghali.dataBase.CartLab;
import com.example.pascal_pc.baghali.dataBase.prefs.UserPrefrences;
import com.example.pascal_pc.baghali.model.dataBaseModel.Cart;
import com.example.pascal_pc.baghali.model.product.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private Button mFinalizePurchaseBtn;
    private RecyclerView mCartRV;
    private TextView mTotalCost;
    private CartAdapter mAdapter;

    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cart_view, container, false);

        mTotalCost = view.findViewById(R.id.total_cost_tv);
        setTotalPRice();
        mFinalizePurchaseBtn = view.findViewById(R.id.finalize_your_purchase_btn);
        mFinalizePurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserPrefrences.getPrefLastName(getActivity()).length() > 0) {
                    Intent intent = OrderActivity.newIntent(getActivity());
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "You must first register", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCartRV = view.findViewById(R.id.cart_item_rv);
        Toast.makeText(getActivity(), CartLab.getInstance().getCarts().size() + "", Toast.LENGTH_SHORT).show();
        mAdapter = new CartAdapter(CartLab.getInstance().getCarts());
        mCartRV.setLayoutManager(new
                LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mCartRV.setAdapter(mAdapter);

        return view;
    }

    private void setTotalPRice() {
        mTotalCost.setText(CartLab.getInstance().getTotalCost() + "" + "Rial");
    }

    private class CartHoler extends RecyclerView.ViewHolder {
        private Cart mCart;
        private TextView mProductPrice, mTotalCost, mProductName;
        private ImageView mProductImgView;
        private Button mDltBtn;
        private Spinner mCountSpinner;
        private ArrayAdapter<CharSequence> mCountAdapter;

        public CartHoler(@NonNull View itemView) {
            super(itemView);
            mProductImgView = itemView.findViewById(R.id.cart_item_img_view);
            mTotalCost = itemView.findViewById(R.id.cart_item_total_cost);
            mProductPrice = itemView.findViewById(R.id.cart_item_price);
            mProductName = itemView.findViewById(R.id.cart_item_name);
            mDltBtn = itemView.findViewById(R.id.cart_item_dlt_btn);
            mCountSpinner = itemView.findViewById(R.id.price_spinner);

            mCountAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.counter_spinner, R.layout.support_simple_spinner_dropdown_item);
            mCountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            mCountSpinner.setAdapter(mCountAdapter);
            mCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mCart.setMProductCount(position + 1);
                    CartLab.getInstance().updateProductCount(mCart);
                    mTotalCost.setText(mCart.getMPrice() * (position + 1) + "" + "Rial");
                    setTotalPRice();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            mDltBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartLab.getInstance().dltCart(mCart);
                    Toast.makeText(getActivity(), "Delete" + mCart.getMName() + "from your cart", Toast.LENGTH_SHORT).show();
                    List<Cart> list = CartLab.getInstance().getCarts();
                    setTotalPRice();
                    mAdapter.setCarts(list);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        public void Bind(Cart cart) {
            mCart = cart;
            mCountSpinner.setSelection(mCart.getMProductCount() - 1);
            Integer mProductId = cart.getMProductId();
            RetrofitClientInstance.getRetrofitInstance()
                    .create(Api.class)
                    .getProduct(cart.getMProductId())
                    .enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            if (response.isSuccessful()) {
                                Product product = response.body();
                                Picasso.get().load(product.getImages().get(0).getPath()).into(mProductImgView);
                                mProductPrice.setText(product.getPrice());
                                mProductName.setText(product.getName());
                                mTotalCost.setText(mCart.getMPrice() * mCart.getMProductCount() + "" + "Rial");

                            }

                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {

                        }
                    });
        }
    }

    private class CartAdapter extends RecyclerView.Adapter<CartHoler> {
        private List<Cart> mCarts;

        public CartAdapter(List<Cart> carts) {
            mCarts = carts;
        }

        public void setCarts(List<Cart> carts) {
            mCarts = carts;
        }

        @NonNull
        @Override
        public CartHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cart_item_view, viewGroup, false);
            CartHoler cartHoler = new CartHoler(view);
            return cartHoler;
        }

        @Override
        public void onBindViewHolder(@NonNull CartHoler cartHoler, int i) {
            cartHoler.Bind(mCarts.get(i));
        }

        @Override
        public int getItemCount() {
            return mCarts.size();
        }
    }

}
