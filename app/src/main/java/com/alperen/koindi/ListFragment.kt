package com.alperen.koindi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperen.koindi.databinding.FragmentListBinding
import com.alperen.koindi.model.CryptoModel
import com.alperen.koindi.view.RecyclerViewAdapter
import com.alperen.koindi.viewModel.CryptoViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment(), RecyclerViewAdapter.Listener {
private var _binding:FragmentListBinding?=null
    private  var cryptoAdapter = RecyclerViewAdapter(arrayListOf(),this)
    private val  viewModel by viewModel<CryptoViewModel>()

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

        viewModel.getDataFromAPI()
observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer {cryptos ->
            cryptos?.let {
                binding.recyclerView.visibility = View.VISIBLE
                cryptoAdapter = RecyclerViewAdapter(ArrayList(cryptos.data?:arrayListOf()),this@ListFragment)
                binding.recyclerView.adapter =cryptoAdapter
            }
        })
        viewModel.cryptoError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it.data==true) {
                    binding.cryptoErrorText.visibility = View.VISIBLE
                } else {
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }
        })
        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it.data==true) {
                    binding.cryptoProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE
                } else {
                    binding.cryptoProgressBar.visibility = View.GONE
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
         _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
Toast.makeText(requireContext(),"clicked : ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }
}