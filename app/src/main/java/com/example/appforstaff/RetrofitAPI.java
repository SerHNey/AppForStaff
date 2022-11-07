package com.example.appforstaff;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface RetrofitAPI {
    @POST("Staffs")

        Call<Staff> createPost(@Body Staff staff);

    @PUT("Staffs/{id}")
    Call<Staff> putPust(@Path("id") int id, @Body Staff staff);

    @PATCH("Staffs/{id}")
    Call<Staff> patchPust(@Path("id") int id, @Body Staff staff);
}

