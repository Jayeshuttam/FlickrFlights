package com.example.flickr;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flickr.nodejs.IMyService;
import com.example.flickr.nodejs.RetroFitClient;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {
    Button btn_login,btn_signup;
    TextInputLayout first_name,last_name,email,password,phone;

    CompositeDisposable compositeDisposable=new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void initViews(){
        btn_login=findViewById(R.id.btn_old_login);
        btn_signup=findViewById(R.id.btn_signup);
        email=findViewById(R.id.tv_email);
        password=findViewById(R.id.tv_password);
        first_name=findViewById(R.id.tv_firstName);
        last_name=findViewById(R.id.tv_lastName);
        phone=findViewById(R.id.tv_phone);
    }

    private void initService(){
        Retrofit retrofitClient= RetroFitClient.getInstance();
        iMyService=retrofitClient.create((IMyService.class));
    }

    private void initOnClickListeners(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(getApplicationContext(),LoginActivity.class);
               startActivity(i);

            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Signup_button","CLICKED");
                signUpUser(first_name.getEditText().getText().toString()
                        ,last_name.getEditText().getText().toString()
                        ,email.getEditText().getText().toString()
                        ,Long.parseLong(phone.getEditText().getText().toString())
                        ,password.getEditText().getText().toString());
            }
        });
    }

    private void signUpUser(String first_name, String last_name, String email, long phone, String password) {
        if (TextUtils.isEmpty(first_name))
        {
            this.first_name.getEditText().setError("First name should not be empty");
        }
        if (TextUtils.isEmpty(last_name))
        {
            this.first_name.getEditText().setError("Last name should not be empty");
        }
        if (TextUtils.isEmpty(email))
        {
            this.first_name.getEditText().setError("Email should not be empty");
        }

        if (TextUtils.isEmpty(password))
        {
            this.first_name.getEditText().setError("Password should not be empty");
        }
        Call<Void> call=iMyService.registerUser(first_name,last_name,email,phone,password);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    Log.e("SIGNUP_STATUS","SUCCESS");
                    Toast.makeText(getApplicationContext(),"Signup Successfull",Toast.LENGTH_LONG).show();
                }
                else{
                    Log.e("SIGNUP_STATUS","UNSUCCESSFULL");
                    Toast.makeText(getApplicationContext(),"Signup unsuccessfull",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initService();
        initViews();
        initOnClickListeners();
    }
}