package com.example.client.di.tag

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokeRetrofit


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptorAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenInterceptorAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainThreadScope


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OnlineInterceptorAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OfflineInterceptorAnnotation