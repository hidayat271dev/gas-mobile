package com.joker.lpgo.ui.order_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Address
import com.joker.lpgo.databinding.ItemOrderBinding
import com.joker.lpgo.databinding.ItemProductBinding

class OrderAdapter (
    private val datas: ArrayList<Any>
) : RecyclerView.Adapter<OrderAdapter.DataViewHolder>() {

    inner class DataViewHolder(val bindingView: ItemOrderBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
