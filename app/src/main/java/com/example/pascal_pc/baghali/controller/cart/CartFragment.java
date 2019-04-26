package com.example.pascal_pc.baghali.controller.cart;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.dataBase.CartLab;
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


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.cart_view, container, false);

        mFinalizePurchaseBtn=view.findViewById(R.id.finalize_your_purchase_btn);
        mTotalCost=view.findViewById(R.id.total_cost_tv);
        mCartRV=view.findViewById(R.id.cart_item_rv);
        mAdapter=new CartAdapter(CartLab.getInstance().getCarts());
        mCartRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private class CartHoler extends RecyclerView.ViewHolder{
        private Cart mCart;
        private TextView mProductPrice,mTotalCost,mProductName;
        private ImageView mProductImgView;
        private Spinner mProCount;
        private Button mDltBtn;

        public CartHoler(@NonNull View itemView) {
            super(itemView);
            mProductImgView=itemView.findViewById(R.id.cart_item_img_view);
            mTotalCost=itemView.findViewById(R.id.cart_item_total_cost);
            mProductPrice=itemView.findViewById(R.id.cart_item_price);
            mProductName=itemView.findViewById(R.id.cart_item_name);
            mDltBtn=itemView.findViewById(R.id.cart_item_dlt_btn);
            mProCount=itemView.findViewById(R.id.price_spinner);

            mDltBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartLab.getInstance().dltCart(mCart);
                }
            });
        }

        public void Bind(Cart cart) {
            mCart=cart;
            Integer mProductId=cart.getMProductId();
            RetrofitClientInstance.getRetrofitInstance()
                    .create(Api.class)
                    .getProduct(12)
                    .enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            if(response.isSuccessful()){
                                Product product=response.body();
                                Picasso.get().load(product.getImages().get(0).getPath()).into(mProductImgView);
                                mProductPrice.setText(product.getPrice());
                                mProductName.setText(product.getName());

                            }

                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {

                        }
                    });
        }
    }

    private class CartAdapter extends RecyclerView.Adapter<CartHoler>{
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
            View view=LayoutInflater.from(getContext()).inflate(R.layout.cart_item_view,viewGroup,false);
            CartHoler cartHoler=new CartHoler(view);
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
