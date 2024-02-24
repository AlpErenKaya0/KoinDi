package com.alperen.koindi.repository

import com.alperen.koindi.model.CryptoModel
import com.alperen.koindi.service.CryptoAPI
import com.alperen.koindi.util.Resource

class CryptoDownloadImpl (private val api:CryptoAPI):CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {

      return try{
          val response =  api.getData()
          if (response.isSuccessful) {
            response.body()?.let {
                return@let Resource.success(it)
            }?:Resource.error("Error",null)
          }else {
              Resource.error("Error",null)
          }
      } catch (e:java.lang.Exception){
          Resource.error("no data",null)
      }

    }
}