package com.joker.lpgo.mobile.module.order_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Category
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.data.model.Order
import com.joker.lpgo.mobile.databinding.ItemCategoryBinding
import com.joker.lpgo.mobile.databinding.ItemOrderBinding
import com.joker.lpgo.ui.category.adapter.CategoryAdapter

class OrderAdapter (private val context: Context,
                    private val datas: ArrayList<Any>,
                    private val listener: ListenerAdapter
) : RecyclerView.Adapter<OrderAdapter.DataViewHolder>() {

    interface ListenerAdapter {
        fun onClickOrderItem(view: View, data: Any)
    }

    inner class DataViewHolder(val bindingView: ItemOrderBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any, listener: ListenerAdapter, context: Context) {
            if (data is Order) {
                bindingView?.textView7.text = data.order_number
                bindingView.textView8.text = data.created_at
                bindingView.textView10.text = "Rp. ${data.total}"
            }

            itemView.setOnClickListener {
                listener.onClickOrderItem(it, data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(datas[position], listener, context)

    fun addData(list: List<Order>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
