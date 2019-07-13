package com.example.pascal_pc.baghali.controller.finalizeOrder;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.cart.CartActivity;
import com.example.pascal_pc.baghali.dataBase.CartLab;
import com.example.pascal_pc.baghali.dataBase.prefs.UserPrefrences;
import com.example.pascal_pc.baghali.model.customer.Coupon;
import com.example.pascal_pc.baghali.model.customer.Customer;
import com.example.pascal_pc.baghali.model.customer.Billing;
import com.example.pascal_pc.baghali.model.customer.Order;
import com.example.pascal_pc.baghali.model.customer.OrderJsonBody;
import com.example.pascal_pc.baghali.model.dataBaseModel.Cart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private static final String TAG = "alisalekabdi";
    private EditText mCityED, mCountryET, mPhoneNumET, mAddressET, mPostalCodeET, mCouponET, mFinalCostAfterCoupon;
    private CheckBox mCouponCheckBox;
    private LinearLayout mCouponLayout;
    private TextView mConfirmBtn;
    private List<String> mCouponsCode = new ArrayList<>();
    private List<Coupon> coupons;
    private boolean mCouponUsed = false;


    public static OrderFragment newInstance() {

        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        findItem(view);

        mCouponCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponUsed = isChecked;
                if (isChecked) {
                    mCouponET.setVisibility(View.VISIBLE);
                    mFinalCostAfterCoupon.setVisibility(View.VISIBLE);
                    mCouponLayout.setVisibility(View.VISIBLE);
                } else {
                    mCouponET.setVisibility(View.INVISIBLE);
                    mFinalCostAfterCoupon.setVisibility(View.INVISIBLE);
                    mCouponLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        getCoupons();
        mCouponET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for (int i = 0; i < coupons.size(); i++) {
                    if (coupons.get(i).getCode().equals(s.toString())) {
                        Toast.makeText(getContext(), " کد تخفیف " + coupons.get(i).getAmount() + " درصد اعمال شد", Toast.LENGTH_LONG).show();
                        String finalCost=(1-Float.valueOf(coupons.get(i).getAmount())/100)*CartLab.getInstance().getTotalCost()+"";
                        mFinalCostAfterCoupon.setText(finalCost+"ریال");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCityED.getText().length() > 0 && mCountryET.getText().length() > 0 &&
                        mPhoneNumET.getText().toString().length() > 0 &&
                        mPostalCodeET.getText().toString().length() > 0) {


                    String name = UserPrefrences.getPrefFirstName(getActivity());
                    String lastName = UserPrefrences.getPrefLastName(getActivity());
                    String emailAddress = UserPrefrences.getPrefUserEmail(getActivity());

                    String phoneNumber = mPhoneNumET.getText().toString();
                    String city = mCityED.getText().toString();
                    String country = mCountryET.getText().toString();
                    String address1 = UserPrefrences.getPrefUserEmail(getActivity());
                    String address2 = mAddressET.getText().toString();
                    String postalCode = mPostalCodeET.getText().toString();

                    Billing billing = new Billing(name, lastName, "", address1, address2, city,
                            "", postalCode, country, emailAddress, phoneNumber);
                    List<Order> orders = new ArrayList<>();
                    List<Cart> cartList = CartLab.getInstance().getCarts();
                    List<Coupon> userCoupon = new ArrayList<>();

//                        if ((mCouponUsed&& !mCouponsCode.contains(mCouponET.getText().toString()))
//                                || !mCouponsCode.contains(mCouponET.getText().toString())) {
//                            Toast.makeText(getActivity(), "Coupon is invalid", Toast.LENGTH_SHORT).show();
//                            return;
//                        }else
                    if (mCouponsCode.contains(mCouponET.getText().toString())) {
                        for (int i = 0; i < coupons.size(); i++) {
                            if (coupons.get(i).getCode().equals(mCouponET.getText().toString())) {
                                userCoupon.add(coupons.get(i));
                                Toast.makeText(getContext(), "Discount code " + coupons.get(i).getAmount() + " is used", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    for (int i = 0; i < cartList.size(); i++) {
                        Order order = new Order(cartList.get(i).getMProductId(), cartList.get(i).getMProductCount());
                        orders.add(order);
                    }


                    OrderJsonBody orderJsonBody = new OrderJsonBody(orders, userCoupon, billing, UserPrefrences.getPrefCustomerId(getActivity()));
                    RetrofitClientInstance.getRetrofitInstance()
                            .create(Api.class)
                            .sendOrder(orderJsonBody)
                            .enqueue(new Callback<Customer>() {
                                @Override
                                public void onResponse(Call<Customer> call, Response<Customer> response) {
                                    if (response.isSuccessful()) {
                                        // TODO: 4/28/2019 we can get order id

                                        Toast.makeText(getActivity(), "The purchase has been finalized " + response.body().getId() + "", Toast.LENGTH_SHORT).show();
                                        Log.e("alisalek", "onResponse: " + response.body().getId());
                                        CartLab.getInstance().deleteCarts();
                                        Intent resultIntent=new Intent();
                                        getActivity().setResult(Activity.RESULT_OK,resultIntent);
                                        getActivity().finish();

                                    } else {
                                        Toast.makeText(getActivity(), response.message() + "Please try again", Toast.LENGTH_SHORT).show();
                                        Log.e("alisalek", "sikdi: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Customer> call, Throwable t) {

                                    Toast.makeText(getActivity(), t.getMessage() + "Try again", Toast.LENGTH_SHORT).show();
                                }
                            });


                } else {
                    Toast.makeText(getActivity(), "You must fill all blanks", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    private void getCoupons() {
        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getCoupons()
                .enqueue(new Callback<List<Coupon>>() {
                    @Override
                    public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                        if (response.isSuccessful()) {
                            coupons = response.body();
                            for (int i = 0; i < coupons.size(); i++) {
                                mCouponsCode.add(coupons.get(i).getCode());
                            }
                            Log.e(TAG, "coupon list has received");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Coupon>> call, Throwable t) {

                    }
                });
    }

    private void findItem(View view) {
        mCityED = view.findViewById(R.id.city_et);
        mCountryET = view.findViewById(R.id.country_et);
        mPhoneNumET = view.findViewById(R.id.phone_et);
        mPostalCodeET = view.findViewById(R.id.postal_code_et);
        mAddressET = view.findViewById(R.id.address_et);
        mCouponET = view.findViewById(R.id.coupon_et);
        mFinalCostAfterCoupon =view.findViewById(R.id.final_cost_after_coupon);
        mCouponCheckBox = view.findViewById(R.id.use_coupon_check_box);
        mConfirmBtn = view.findViewById(R.id.confrim_order_btn);
        mCouponLayout = view.findViewById(R.id.linearLayout_coupon);
        mCouponET.setVisibility(View.INVISIBLE);
        mCouponLayout.setVisibility(View.INVISIBLE);
    }

}
