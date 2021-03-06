package com.joker.lpgo.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.databinding.ItemNearyByBinding

class NearByProductAdapter (
    private val datas: ArrayList<Any>,
    private val listener: ListenerAdapter
) : RecyclerView.Adapter<NearByProductAdapter.DataViewHolder>() {

    interface ListenerAdapter {
        fun onClickNearByItem(view: View, data: Any)
    }

    inner class DataViewHolder(val bindingView: ItemNearyByBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any, listener: ListenerAdapter) {
            if (data is Product) {
                bindingView.textView18.text = data.name
                bindingView.textView19.text = data.short_desc
                bindingView.price.textView9.text = "${data.sale_price}"
                bindingView.price.imageView5.setImageResource(R.drawable.ic_dollar)
                bindingView.qty.textView9.text = "${data.qty}"
                bindingView.qty.imageView5.setImageResource(R.drawable.ic_stack)
                Glide
                    .with(bindingView.imageView18)
                    .load(data.img_cover)
                    .centerCrop()
                    .placeholder(R.drawable.ic_no_image_default)
                    .error(R.drawable.ic_no_image_default)
                    .into(bindingView.imageView18)
            }

            itemView.setOnClickListener {
                listener.onClickNearByItem(it, data)
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
