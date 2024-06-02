package com.ibmareducationalapp;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Credentials;


public class RetrofitClient {

    private static final String BASE_URL = "https://ibmbackendarapp-ibmbackendarapp.azuremicroservices.io";
    private static RetrofitClient instance;
    private ApiService api;

    private RetrofitClient() {
        // Build OkHttpClient and add the Basic Auth interceptor
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    String credentials = Credentials.basic("admin", "secret");
                    Request.Builder builder = originalRequest.newBuilder()
                            .header("Authorization", credentials);

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
               })
                .build();

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ApiService getApi() {
        return api;
    }
}
