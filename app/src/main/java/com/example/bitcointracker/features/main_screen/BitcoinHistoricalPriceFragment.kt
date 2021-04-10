package com.example.bitcointracker.features.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitcointracker.R
import com.example.bitcointracker.databinding.FragmentBitcoinHistoricalPriceBinding
import com.example.bitcointracker.features.SharedViewModel
import com.example.bitcointracker.features.details_screen.BitCoinDetailsFragment
import com.example.bitcointracker.model.entities.Currency
import com.example.bitcointracker.model.entities.Error
import com.example.bitcointracker.model.entities.Loading
import com.example.bitcointracker.model.entities.Success
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BitcoinHistoricalPriceFragment : Fragment(), DataTransfer {
    private lateinit var binding: FragmentBitcoinHistoricalPriceBinding
    private lateinit var currencyAdapter: CurrencyAdapter
    private val viewModel by viewModels<CurrencyViewModel>()
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBitcoinHistoricalPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyAdapter = CurrencyAdapter(mutableListOf(), this)
        binding.historicalRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currencyAdapter
        }
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        loadData()
        binding.retryButton.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getHistoricalData().observe(requireActivity(), { response ->
            when (response) {
                is Loading -> handleViewState(
                    progressVisibility = View.VISIBLE,
                    recyclerVisibility = View.GONE,
                    noInternetVisibility = View.GONE
                )
                is Success -> {
                    currencyAdapter.setData(response.data.days.values.toList())
                    handleViewState(
                        progressVisibility = View.GONE,
                        recyclerVisibility = View.VISIBLE,
                        noInternetVisibility = View.GONE
                    )
                }
                is Error -> handleViewState(
                    progressVisibility = View.GONE,
                    recyclerVisibility = View.GONE,
                    noInternetVisibility = View.VISIBLE
                )
            }
        })
    }

    private fun handleViewState(
        progressVisibility: Int,
        recyclerVisibility: Int,
        noInternetVisibility: Int
    ) {
        binding.apply {
            progressBar.visibility = progressVisibility
            historicalRecycler.visibility = recyclerVisibility
            noInternet.visibility = noInternetVisibility
        }
    }

    override fun onItemClick(data: List<Currency>) {
        sharedViewModel.setData(data)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, BitCoinDetailsFragment()).addToBackStack(null).commit()
    }
}
