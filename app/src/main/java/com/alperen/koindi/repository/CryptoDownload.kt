package com.alperen.koindi.repository

import com.alperen.koindi.model.CryptoModel
import com.alperen.koindi.util.Resource

interface CryptoDownload {
    suspend fun  downloadCryptos():Resource<List<CryptoModel>>
}