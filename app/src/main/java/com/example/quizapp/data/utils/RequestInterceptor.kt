package com.example.quizapp.data.utils

import com.example.quizapp.data.prefstore.PrefsStore
import kotlinx.coroutines.flow.collect
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class RequestInterceptor @Inject constructor(private val prefsStore: PrefsStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var token = ""
        suspend {
            prefsStore.getAuthToken().collect {
                token = it
            }
        }
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", token).build()
        return chain.proceed(request)
    }
}
