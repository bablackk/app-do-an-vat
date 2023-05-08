package edu.huflit.app_do_an_vat.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.huflit.app_do_an_vat.API.modelAPI.EditProfileRequest;
import edu.huflit.app_do_an_vat.API.modelAPI.EditProfileResponse;
import edu.huflit.app_do_an_vat.API.modelAPI.GetProfileResponse;
import edu.huflit.app_do_an_vat.API.modelAPI.LoginRequest;
import edu.huflit.app_do_an_vat.API.modelAPI.LoginResponse;
import edu.huflit.app_do_an_vat.API.modelAPI.RegisterRequest;
import edu.huflit.app_do_an_vat.API.modelAPI.RegisterResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface APIService {
    static final String baseURL = "https://2792-183-80-95-59.ngrok-free.app/";
    Gson gson = new GsonBuilder().setLenient().create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);
    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("/auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest loginRequest);
    @GET("/auth/get/profile")
    Call<GetProfileResponse> getProfile(@Header("Authorization") String token);
    @PUT("/auth/edit/profile")
    Call<EditProfileResponse> editProfile(@Header("Authorization") String token, @Body EditProfileRequest editProfileRequest);
}
