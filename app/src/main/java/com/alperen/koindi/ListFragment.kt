package com.alperen.koindi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperen.koindi.databinding.FragmentListBinding
import com.alperen.koindi.model.CryptoModel
import com.alperen.koindi.service.CryptoAPI
import com.alperen.koindi.view.RecyclerViewAdapter
import com.alperen.koindi.viewModel.cryptoViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class ListFragment : Fragment(), RecyclerViewAdapter.Listener {
private var _binding:FragmentListBinding?=null
    private  var cryptoAdapter = RecyclerViewAdapter(arrayListOf(),this)
    private lateinit var viewModel:cryptoViewModel
    private val binding get() = _binding!!

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
viewModel = ViewModelProvider(this).get(cryptoViewModel::class.java)
        viewModel.getDataFromAPI()

    }


    override fun onDestroyView() {
        super.onDestroyView()
         _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
Toast.makeText(requireContext(),"clicked : ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }
}