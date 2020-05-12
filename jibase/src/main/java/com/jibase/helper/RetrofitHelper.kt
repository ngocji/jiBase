package com.jibase.helper

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitHelper {
    private val DEFAULT_TIME_OUT = 60L
    private val hasRetrofit = mutableMapOf<String, Retrofit>()

    /**
     *  Create instance retrofit
     *  @param baseUrl: base url
     *  @param serviceClass: class interface request api
     *  @param gSonFactory: Gson use parse
     *  @param timeOut: time out
     *  @return :  serviceClass or null
     */
    fun <T> create(
        baseUrl: String,
        serviceClass: Class<T>,
        gSonFactory: GsonConverterFactory = GsonConverterFactory.create(),
        timeOut: Long = DEFAULT_TIME_OUT,
        vararg interceptors: Interceptor
    ): T {
        val retrofit = hasRetrofit[baseUrl]
        return if (retrofit != null) {
            retrofit.create(serviceClass)
        } else {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gSonFactory)
                .client(
                    createClient(
                        timeOut,
                        interceptors
                    )
                )
                .build()
                .run {
                    hasRetrofit[baseUrl] = this
                    create(serviceClass)
                }
        }
    }

    private fun createClient(
        timeOut: Long,
        interceptors: Array<out Interceptor>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptors(interceptors)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun OkHttpClient.Builder.addInterceptors(
        interceptors: Array<out Interceptor>
    ): OkHttpClient.Builder {
        // add custom interceptors
        interceptors.forEach {
            addInterceptor(it)
        }

//        // add logging
//        addInterceptor(createHttpLogging())

        return this
    }

//    private fun createHttpLogging(): Interceptor {
//        return LoggingInterceptor.Builder()
//            .loggable(true)
//            .setLevel(Level.BASIC)
//            .log(Platform.INFO)
//            .request("Request")
//            .response("Response")
//            .build()
//    }
}