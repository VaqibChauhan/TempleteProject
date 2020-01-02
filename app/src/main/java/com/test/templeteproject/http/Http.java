package com.test.templeteproject.http;


import android.util.Log;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.test.templeteproject.BuildConfig;
import com.test.templeteproject.base.ProApplication;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vaqib
 */

public class Http {

    private static final String CURL_LOG_TAG = "cURL";
    private static OkHttpClient client;
    private static ApiService apiService;
    private static volatile Retrofit retrofit;


    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }



    /**
     * 设置缓存
     */
    private static Interceptor addCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkAvailable(ProApplication.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkAvailable(ProApplication.getContext())) {
                    int maxAge = 0;
                    // There is a network to set the cache timeout time 0 hours, meaning that does not read the cache data, only useful to the post, post no buffer
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit") //Clear the header information, because if the server does not support, will return some interference information, do not clear the following can not take effect
                            .build();
                } else {
                    // When there is no network, set the timeout to 4 weeks only for get useful, post no buffer
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                    maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Http.class) {
                if (retrofit == null) {
                    LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder()
                            .loggable(BuildConfig.DEBUG)
                            .setLevel(Level.BODY)
                            .log(Platform.INFO)
                            .request("Request")
                            .response("Response")
                            .addHeader("version", BuildConfig.VERSION_NAME)
                            .build();

                    File cacheFile = new File(ProApplication.getContext().getCacheDir(), "cache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

                    client = new OkHttpClient
                            .Builder()
                            .addInterceptor(httpLoggingInterceptor)
                            .cache(cache)
                            .connectTimeout(60L, TimeUnit.SECONDS)
                            .readTimeout(60L, TimeUnit.SECONDS)
                            .writeTimeout(60L, TimeUnit.SECONDS)
                            .build();

                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(ApiConstants.BASE_URL_DEV)
                            .client(client)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
