package com.example.winecellarapp.REST;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Patricia Poracova
 * Connection client with the server through the ngrok(Free provider for forwarding localhost request)
 */
public class CellarClient {

    //TODO: add base URL
   private static final String BASE_URL = "http://2b5d0170.ngrok.io/testrest_war_exploded/api/";    //you need to get your own ngrok instance ...check tutorial https://markzfilter.com/connect-ios-android-app-local-host-server/


    /**
     * Get cellar client
     * @return Retrofit client
     */
    public static Retrofit getCellarClient()
    {

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(provideOkHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Create new logging interceptor
     * @return new Interceptor
     */
    private static Interceptor provideLoggingInterceptor()
    {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * Return new Http client with all necessary parameters
     * @return new HTTP client
     */
    private static OkHttpClient provideOkHttp()
    {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(provideLoggingInterceptor())
                .build();
    }
}
