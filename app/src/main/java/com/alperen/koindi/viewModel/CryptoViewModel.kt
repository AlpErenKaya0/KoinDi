package com.alperen.koindi.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperen.koindi.model.CryptoModel
import com.alperen.koindi.repository.CryptoDownload
import com.alperen.koindi.service.CryptoAPI
import com.alperen.koindi.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel(private val cryptoDownloadRepository: CryptoDownload
): ViewModel() {
    val cryptoList = MutableLiveData<Resource<List<CryptoModel>>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()
    private var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
        cryptoError.value = Resource.error(throwable.localizedMessage?:"error",data = true)

    }

    fun getDataFromAPI() {
        cryptoLoading.value = Resource.loading(data = true)

         val BASE_URL = "https://raw.githubusercontent.com"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
           val resource= cryptoDownloadRepository.downloadCryptos()
            val response = retrofit.getData()
            withContext(Dispatchers.Main) {
              resource.data?.let {
                  cryptoList.value = resource
                  cryptoLoading.value = Resource.loading(data = false)
                  cryptoError.value = Resource.error("",data = false)
              }
                    }

                }
            }
        }




