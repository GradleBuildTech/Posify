package com.example.client.providers.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import java.util.Date

object MoshiBuilderProvider {
    val moshiBuilder: Moshi.Builder
        get() = Moshi.Builder()
            .add(MyKotlinJsonAdapterFactory())
            .add(MyStandardJsonAdapters.FACTORY)
            .add(Date::class.java, Rfc3339DateJsonAdapter())
}