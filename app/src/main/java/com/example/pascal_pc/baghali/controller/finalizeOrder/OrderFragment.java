package com.example.pascal_pc.baghali.controller.finalizeOrder;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
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

    public static OrderFragment newInstance() {
        
        Bundle args = new Bundle();
        
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private EditText mCityED,mCountryET,mPhoneNumET,mAddressET,mPostalCodeET,mPasswordET,mCouponET;
    private CheckBox mCouponCheckBox;
    private TextView mConfirmBtn;
    private TextInputLayout mCouponLayout;
    private boolean mCouponUsed=false;
    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order, container, false);
        findItem(view);
        mCouponCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponUsed=isChecked;
                if(isChecked){
                    mCouponET.setVisibility(View.VISIBLE);
                    mCouponLayout.setVisibility(View.VISIBLE);
                }else {
                    mCouponET.setVisibility(View.INVISIBLE);
                    mCouponLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPasswordET.getText().toString().equals(UserPrefrences.getPrefUserPassword(getActivity()))){

                    if(mCityED.getText().length()>0&&mCountryET.getText().length()>0&&
                            mPhoneNumET.getText().toString().length()>0 &&
                            mPostalCodeET.getText().toString().length()>0){

                        String name=UserPrefrences.getPrefFirstName(getActivity());
                        String lastName=UserPrefrences.getPrefLastName(getActivity());
                        String emailAddress=UserPrefrences.getPrefUserEmail(getActivity());

                        String phoneNumber=mPhoneNumET.getText().toString();
                        String city=mCityED.getText().toString();
                        String country=mCountryET.getText().toString();
                        String address=UserPrefrences.getPrefUserEmail(getActivity());
                        String postalCode=mPostalCodeET.getText().toString();

                        Billing billing=new Billing(name,lastName,"maktab",address,"Hamedan",city,
                                "New yurk",postalCode,country,emailAddress,phoneNumber);
                        List<Order> orders=new ArrayList<>();
                        List<Cart> cartList=CartLab.getInstance().getCarts();

                        for (int i = 0; i <cartList.size() ; i++) {
                            Order order=new Order(cartList.get(i).getMProductId(),cartList.get(i).getMProductCount());
                            orders.add(order);
                        }


                        if(mCouponUsed&&mCouponET.getText().length()<1)
                            Toast.makeText(getActivity(), "Coupon is invalid ", Toast.LENGTH_SHORT).show();

                        OrderJsonBody orderJsonBody=new OrderJsonBody(billing,orders,UserPrefrences.getPrefCustomerId(getActivity()));

                        RetrofitClientInstance.getRetrofitInstance()
                                .create(Api.class)
                                .sendOrder(orderJsonBody)
                                .enqueue(new Callback<Customer>() {
                                    @Override
                                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                                        if(response.isSuccessful()){
                                            // TODO: 4/28/2019 we can get order id

                                            Toast.makeText(getActivity(), "The purchase has been finalized "+response.body().getId()+"", Toast.LENGTH_SHORT).show();
                                            Log.e("alisalek", "onResponse: "+response.body().getId());
                                            CartLab.getInstance().deleteCarts();
                                            getActivity().finish();

                                        }else{
                                            Toast.makeText(getActivity(), response.message()+"Please try again", Toast.LENGTH_SHORT).show();
                                            Log.e("alisalek", "sikdi: "+response.message() );
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Customer> call, Throwable t) {

                                        Toast.makeText(getActivity(), t.getMessage()+"Try again", Toast.LENGTH_SHORT).show();
                                    }
                                });




                    }else {
                        Toast.makeText(getActivity(), "You must fill all blanks", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(), "Password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void findItem(View view) {
        mCityED=view.findViewById(R.id.city_et);
        mCountryET=view.findViewById(R.id.country_et);
        mPhoneNumET=view.findViewById(R.id.phone_et);
        mPostalCodeET=view.findViewById(R.id.postal_code_et);
        mAddressET=view.findViewById(R.id.address_et);
        mAddressET.setVisibility(View.INVISIBLE);
        mPasswordET=view.findViewById(R.id.password_finalize);
        mCouponET=view.findViewById(R.id.coupon_et);
        mCouponCheckBox=view.findViewById(R.id.use_coupon_check_box);
        mConfirmBtn=view.findViewById(R.id.confrim_order_btn);
        mCouponLayout=view.findViewById(R.id.coupon_layout);
        mCouponET.setVisibility(View.INVISIBLE);
        mCouponLayout.setVisibility(View.INVISIBLE);
    }

}
