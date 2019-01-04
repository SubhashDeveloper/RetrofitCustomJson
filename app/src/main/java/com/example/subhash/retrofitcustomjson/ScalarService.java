package com.example.subhash.retrofitcustomjson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ScalarService {
    @GET()
    Call<String> getStringResponse(@Url String url);
}
