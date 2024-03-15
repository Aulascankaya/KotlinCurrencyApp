package com.ulas.kotlincurrencyapp.adapter

import android.graphics.Color
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ulas.kotlincurrencyapp.databinding.RowLayoutBinding
import com.ulas.kotlincurrencyapp.model.CryptoModel

class RecyclerViewAdapter(private val currencyList: List<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(currencyModel: CryptoModel)
    }

   // private val colors: Array<String> = arrayOf("#e2ecec","#cfdfdf","#bcd2d2","#a0c0c0","#607373")
    private val colors: Array<String> = arrayOf("#7f8f8f","#a0c0c0","#bcd2d2","#cfdfdf","#e2ecec")

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        if (position < currencyList.size) {
            val currencyModel = currencyList[position]
            val currencyDataList = currencyModel.data.entries.toList()

            if (currencyDataList.isNotEmpty()) {
                val currencyData = currencyDataList[position % currencyDataList.size]
                val (currency, price) = currencyData
                holder.binding.textName.text = currency
                holder.binding.textPrice.text = price.toString()
            }

            holder.itemView.setOnClickListener {
                listener.onItemClick(currencyModel)
            }

            holder.itemView.setBackgroundColor(Color.parseColor(colors[position % colors.size]))
        }
    }





}






