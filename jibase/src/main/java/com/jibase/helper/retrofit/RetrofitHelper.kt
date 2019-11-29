package com.jibase.helper.retrofit

import com.jibase.utils.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitHelper {
    private const val DEFAULT_TIME_OUT = 60L
    private val hasRetrofit = mutableMapOf<String, Retrofit>()

    /**
     *  Create instance retrofit
     *  @param baseUrl: base url
     *  @param serviceClass: class interface request api
     *  @param gSonFactory: Gson use parse
     *  @param timeOut: time out
     *  @return :  serviceClass or null
     */
    fun <T> create(baseUrl: String,
                   serviceClass: Class<T>,
                   gSonFactory: GsonConverterFactory = GsonConverterFactory.create(),
                   timeOut: Long = DEFAULT_TIME_OUT): T {
        val retrofit = hasRetrofit[baseUrl]
        return if (retrofit != null) {
            retrofit.create(serviceClass)
        } else {
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(gSonFactory)
                    .client(createClient(timeOut))
                    .build()
                    .run {
                        hasRetrofit[baseUrl] = this
                        create(serviceClass)
                    }
        }
    }

    private fun createClient(timeOut: Long): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(createHttpLogging())
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
    }

    private fun createHttpLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor { message ->
            log("RETROFIT: $message")
        }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}