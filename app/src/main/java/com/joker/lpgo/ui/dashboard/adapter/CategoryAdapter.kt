package com.joker.lpgo.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Category
import com.joker.lpgo.databinding.ItemCategoryBinding

class CategoryAdapter (
    private val datas: ArrayList<Any>,
    private val listener: ListenerAdapter
) : RecyclerView.Adapter<CategoryAdapter.DataViewHolder>() {

    interface ListenerAdapter {
        fun onClickCategoryItem(view: View)
    }

    inner class DataViewHolder(val bindingView: ItemCategoryBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any, listener: ListenerAdapter) {
            itemView.setOnClickListener {
                listener.onClickCategoryItem(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(datas[position], listener)

    fun addData(list: MutableList<Category>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
