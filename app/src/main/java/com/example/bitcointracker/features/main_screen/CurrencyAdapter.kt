package com.example.bitcointracker.features.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcointracker.R
import com.example.bitcointracker.databinding.RecyclerRowBinding
import com.example.bitcointracker.model.entities.Currency
import com.example.bitcointracker.model.entities.CurrencyType
import com.example.bitcointracker.model.entities.PopulateData

class CurrencyAdapter(private var data: List<PopulateData>, val listener: DataTransfer) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            RecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        if (position == 0)
            holder.binding.date.text = holder.itemView.context.getString(R.string.today)
        else
            holder.binding.date.text =
                data[position].currencies.first { it.currencyType == CurrencyType.EUR }.date
        holder.binding.currencyRate.text =
            data[position].currencies.first { it.currencyType == CurrencyType.EUR }.rate.toString()
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData: List<PopulateData>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class CurrencyViewHolder(val binding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root){
            init {
                binding.rowContainer.setOnClickListener {
                    listener.onItemClick(data = data[absoluteAdapterPosition])
                }
            }
        }
}

interface DataTransfer {
    fun onItemClick(data: PopulateData)
}