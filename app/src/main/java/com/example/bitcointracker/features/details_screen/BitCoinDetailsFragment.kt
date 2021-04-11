package com.example.bitcointracker.features.details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bitcointracker.databinding.FragmentBitCoinDetailsBinding
import com.example.bitcointracker.features.SharedViewModel
import com.example.bitcointracker.model.entities.CurrencyType

class BitCoinDetailsFragment : Fragment() {
    lateinit var binding: FragmentBitCoinDetailsBinding
    lateinit var viewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBitCoinDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel.bitCoinPriceDetails.observe(viewLifecycleOwner, { data ->
            binding.dateTV.text = data.currencies.first().date
            binding.dollarCurrency.text = data.currencies.first { it.currencyType == CurrencyType.USD }.rate.toString()
            binding.euroCurrency.text = data.currencies.first { it.currencyType == CurrencyType.EUR }.rate.toString()
            binding.gbpCurrency.text = data.currencies.first { it.currencyType == CurrencyType.GBP }.rate.toString()
        })
    }
}