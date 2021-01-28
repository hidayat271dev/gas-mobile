package com.joker.lpgo.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Category
import com.joker.lpgo.mobile.data.model.Cart
import com.joker.lpgo.mobile.databinding.ItemCartBinding

class CartAdapter(
    private val context: Context,
    private val datas: ArrayList<Cart>,
    private val listener: ListenerAdapter
) : RecyclerView.Adapter<CartAdapter.DataViewHolder>() {

    interface ListenerAdapter {
        fun removeItemCart(data: Any)
        fun incDecItemCart(data: Any, isIncrement: Boolean)
    }

    inner class DataViewHolder(val bindingView: ItemCartBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any, listener: ListenerAdapter, context: Context) {
            if (data is Cart) {
                bindingView.textView18.text = data.product
                bindingView.textView29.text = "${(data.qty * data.price)}"
                bindingView.avaQty.textView9.text = "${99}"
                bindingView.salePrice.textView9.text = "Rp. ${data.price}"
                bindingView.qty.setText("${data.qty}")

                bindingView.button6.setOnClickListener {
                    listener.incDecItemCart(data, true)
                }
                bindingView.button5.setOnClickListener {
                    listener.incDecItemCart(data, false)
                }
                bindingView.imageView12.setOnClickListener {
                    listener.removeItemCart(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(datas[position], listener, context)

    fun addData(list: List<Cart>) {
        datas.clear()
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
