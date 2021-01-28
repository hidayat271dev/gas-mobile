package com.joker.lpgo.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.databinding.ItemNearyByBinding

class NearByProductAdapter (
    private val datas: ArrayList<Any>,
    private val listener: ListenerAdapter
) : RecyclerView.Adapter<NearByProductAdapter.DataViewHolder>() {

    interface ListenerAdapter {
        fun onClickNearByItem(view: View)
    }

    inner class DataViewHolder(val bindingView: ItemNearyByBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any, listener: ListenerAdapter) {
            if (data is Product) {
                bindingView.textView18.text = data.name
                bindingView.textView19.text = data.short_desc
                bindingView.price.textView9.text = "${data.sale_price}"
                bindingView.qty.textView9.text = "${99}"
            }

            itemView.setOnClickListener {
                listener.onClickNearByItem(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemNearyByBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(datas[position], listener)

    fun addData(list: List<Product>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
