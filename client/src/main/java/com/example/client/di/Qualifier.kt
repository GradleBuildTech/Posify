package com.example.client.di

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