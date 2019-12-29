package com.example.vancouvertourismappfinal.CurrencyConverterClasses.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Danilo Lemes @ Happe on 29/09/2017.
 */

public interface CurrencyConverterService {

    @GET("latest?access_key=1a543e6202e37f41f6e6a0f9c0b962c8")
    Call<ResponseBody> getCurrency(@Query("base") String from);

}
