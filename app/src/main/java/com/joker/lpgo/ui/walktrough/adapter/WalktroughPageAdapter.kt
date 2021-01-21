package com.joker.lpgo.ui.walktrough.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.data.model.Walktrough
import com.joker.lpgo.databinding.ItemLayoutBinding
import com.joker.lpgo.databinding.ItemWalkthroughBinding

class WalktroughPageAdapter(private var ctx: Context?, private var dataList: List<Any>?) :
    RecyclerView.Adapter<WalktroughPageAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ItemWalkthroughBinding) : RecyclerView.ViewHolder(itemBinding.root){
        private var binding : ItemWalkthroughBinding? = null
        init {
            this.binding = itemBinding
        }

        fun bind(data: Any) {
            if (data is Walktrough) {
                this.binding?.imageView3?.setImageResource(data.image)
                this.binding?.textView2?.text = data?.title
                this.binding?.textView3?.text = data?.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemWalkthroughBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        dataList?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList?.let {
            holder.bind(it[position])
        }
    }

}