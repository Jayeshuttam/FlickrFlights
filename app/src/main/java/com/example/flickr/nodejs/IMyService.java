package com.example.flickr.nodejs;

import com.example.flickr.models.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyService {
    @POST("/users/signup_verify")
    @FormUrlEncoded
    Call<Void> registerUser(@Field("first_name")String first_name, @Field("last_name")String last_name, @Field
    ("email")String email, @Field("phone")long phone, @Field("password")String password);



    @POST("/users/login_verify")

    Call<User> loginUser(@Body HashMap<String,String> map);
}

