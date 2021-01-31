package com.joker.lpgo.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.databinding.ItemRecommendedBinding

class RecommendedProductAdapter (
    private val datas: ArrayList<Any>,
    private val listener: ListenerAdapter
) : RecyclerView.Adapter<RecommendedProductAdapter.DataViewHolder>() {

    interface ListenerAdapter {
        fun onClickRecomendedItem(view: View, data: Any)
    }

    inner class DataViewHolder(val bindingView: ItemRecommendedBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any, listener: ListenerAdapter) {
            if (data is Product) {
                bindingView.textView18.text = data.name
                bindingView.textView19.text = data.short_desc
                bindingView.includePrice.textView9.text = "${data.sale_price}"
                bindingView.includePrice.imageView5.setImageResource(R.drawable.ic_dollar)
                bindingView.includeQty.textView9.text = "${data.qty}"
                bindingView.includeQty.imageView5.setImageResource(R.drawable.ic_stack)
                Glide
                    .with(bindingView.imageView18)
                    .load(data.img_cover)
                    .centerCrop()
                    .placeholder(R.drawable.ic_no_image_default)
                    .error(R.drawable.ic_no_image_default)
                    .into(bindingView.imageView18)
            }

            itemView.setOnClickListener {
                listener.onClickRecomendedItem(it, data)
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

    fun addData(list: List<Product>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
