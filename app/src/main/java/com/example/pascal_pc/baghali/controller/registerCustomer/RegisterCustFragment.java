package com.example.pascal_pc.baghali.controller.registerCustomer;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.dataBase.prefs.UserPrefrences;
import com.example.pascal_pc.baghali.model.customer.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCustFragment extends Fragment {

    private Button mRegisterBtn;
    private LottieAnimationView mDoneAnimationView;

    public static RegisterCustFragment newInstance() {

        Bundle args = new Bundle();

        RegisterCustFragment fragment = new RegisterCustFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public RegisterCustFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        final EditText name = view.findViewById(R.id.add_user_first_name);
        final EditText lastName = view.findViewById(R.id.add_user_last_name);
        final EditText userName = view.findViewById(R.id.add_user_name);
        final EditText email = view.findViewById(R.id.add_user_email);
        final EditText password = view.findViewById(R.id.add_user_password);
        mRegisterBtn=view.findViewById(R.id.register_btn);
        mDoneAnimationView=view.findViewById(R.id.register_done);
        mDoneAnimationView.setVisibility(View.INVISIBLE);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() > 0
                        && lastName.getText().toString().length() > 0
                        && userName.getText().toString().length() > 0
                        && email.getText().toString().length() > 0
                        && password.getText().toString().length() > 0) {
                    UserPrefrences.setPrefFirstName(getActivity(), name.getText().toString());
                    UserPrefrences.setPrefLastName(getActivity(), lastName.getText().toString());
                    UserPrefrences.setPrefUserName(getActivity(), userName.getText().toString());
                    UserPrefrences.setPrefUserEmail(getActivity(), email.getText().toString());
                    UserPrefrences.setPrefUserPassword(getActivity(), password.getText().toString());

                    registerCustomer(name.getText().toString(),lastName.getText().toString(),
                            userName.getText().toString(),email.getText().toString());


                } else {
                    Toast.makeText(getActivity(), "You should fill all blanks", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }
    private void registerCustomer(String name,String lastName,String userName,String email) {
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .createCustomer(name,lastName,
                        userName,email)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        Toast.makeText(getActivity(), "You are registered"+response.message(), Toast.LENGTH_SHORT).show();
                        if(response.isSuccessful()){
                            //mDoneAnimationView.setVisibility(View.VISIBLE);
                            Log.e("alisalek", "onResponse: You are registered"+response.body().getId());
                            Toast.makeText(getActivity(), "You are registered successfully", Toast.LENGTH_SHORT).show();
                            UserPrefrences.setPrefCustomerId(getActivity(),response.body().getId());
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().finish();
                                }
                            },400);

                        }else{
                            Log.e("alisalek", "onResponse: You aren't registered"+response.message());
                            Toast.makeText(getActivity(), "Username and email already has been used!! change them", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        Log.e("alisalek", "onResponse: You aren't registered"+t.getMessage());
                        Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}