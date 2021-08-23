package com.example.quizapp.data

import com.example.quizapp.BuildConfig

interface NetworkConfig {
    val baseUrl: String
    val authUrl: String
    val connectTimeoutInMs: Long
    val readTimeoutInMs: Long
    val writeTimeoutInMs: Long
}

class DefaultNetworkConfig : NetworkConfig {
    override val baseUrl = BuildConfig.API_BASE_URL
    override val authUrl = BuildConfig.API_AUTH_URL
    override val connectTimeoutInMs = DEFAULT_TIMEOUT_MS
    override val readTimeoutInMs = DEFAULT_TIMEOUT_MS
    override val writeTimeoutInMs = DEFAULT_TIMEOUT_MS

    companion object {
        private const val DEFAULT_TIMEOUT_MS: Long = 60
    }
}
