package com.joker.lpgo.ui.address.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Address
import com.joker.lpgo.databinding.ItemAddressBinding

class AddressAdapter (
    private val datas: ArrayList<Any>
) : RecyclerView.Adapter<AddressAdapter.DataViewHolder>() {

    inner class DataViewHolder(val bindingView: ItemAddressBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(datas[position])

    fun addData(list: MutableList<Address>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
