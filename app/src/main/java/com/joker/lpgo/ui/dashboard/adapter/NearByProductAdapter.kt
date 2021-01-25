package com.joker.lpgo.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.databinding.ItemNearyByBinding

class NearByProductAdapter (
    private val datas: ArrayList<Any>
) : RecyclerView.Adapter<NearByProductAdapter.DataViewHolder>() {

    inner class DataViewHolder(val bindingView: ItemNearyByBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemNearyByBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(datas[position])

    fun addData(list: MutableList<Product>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
