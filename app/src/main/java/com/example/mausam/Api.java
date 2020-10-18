package com.example.mausam;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {



    @GET("weather")
    Call<PostList> getpostlist(@Query("q") String cityname,@Query("appid") String key);


}
