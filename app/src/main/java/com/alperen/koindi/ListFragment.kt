package com.alperen.koindi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperen.koindi.databinding.FragmentListBinding
import com.alperen.koindi.model.CryptoModel
import com.alperen.koindi.service.CryptoAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class ListFragment : Fragment() {
private var _binding:FragmentListBinding?=null
    private val binding get() = _binding!!
    private val BASE_URL="https://raw.githubusercontent.com"
    private var cryptoModels: ArrayList<CryptoModel>?=null
    private var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
println("Error : ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
val layoutManager= LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        loadData()

    }
    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
        job = CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = retrofit.getData()
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                response.body()?.let{
                    println(it)
                }
            }
        }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
         _binding = null
        job?.cancel()
    }
}