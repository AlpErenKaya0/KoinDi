package com.alperen.koindi.di

import com.alperen.koindi.repository.CryptoDownload
import com.alperen.koindi.repository.CryptoDownloadImpl
import com.alperen.koindi.service.CryptoAPI
import com.alperen.koindi.viewModel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        val BASE_URL = "https://raw.githubusercontent.com"

              Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
    }
    //factory bu singleton gibi değil, her injecte ettiğinde yeni instance oluşturur
single<CryptoDownload> {
    CryptoDownloadImpl(get())
}
    viewModel{
        CryptoViewModel(get())
    }
}