package com.joker.lpgo.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.databinding.ItemRecommendedBinding

class RecommendedProductAdapter (
    private val datas: ArrayList<Any>,
    private val listener: ListenerAdapter
) : RecyclerView.Adapter<RecommendedProductAdapter.DataViewHolder>() {

    interface ListenerAdapter {
        fun onClickRecomendedItem(view: View)
    }

    inner class DataViewHolder(val bindingView: ItemRecommendedBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any, listener: ListenerAdapter) {
            itemView.setOnClickListener {
                listener.onClickRecomendedItem(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemRecommendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(datas[position], listener)

    fun addData(list: MutableList<Product>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
