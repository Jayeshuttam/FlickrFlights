package com.example.flickr;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flickr.models.User;
import com.example.flickr.nodejs.IMyService;
import com.example.flickr.nodejs.RetroFitClient;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
Button btn_login,btn_signup;
TextInputLayout email,password;
IMyService iMyService;

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initViews(){
        btn_login=findViewById(R.id.btn_login);
        btn_signup=findViewById(R.id.btn_new_signup);
        email=findViewById(R.id.tv_email);
        password=findViewById(R.id.tv_password);
    }

    private void initService(){
        Retrofit retrofitClient= RetroFitClient.getInstance();
        iMyService=retrofitClient.create((IMyService.class));
    }

    private void initOnClickListeners(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(email.getEditText().getText().toString(),password.getEditText().getText().toString());

            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email , String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Email cannot be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Password cannot be empty",Toast.LENGTH_LONG).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();

        map.put("email", email);
        map.put("password", password);

        Call<User> call= iMyService.loginUser(map);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    Log.e("Login STATUS => ", "Login success");

                } else {
                    Log.e("Login STATUS => ", "Login failed");
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(),
                            Toast.LENGTH_LONG).show();
            }
        });

        };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initService();
        initViews();
        initOnClickListeners();



    }
}