package com.joker.lpgo.mobile.module.order_list.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.data.model.Address
import com.joker.lpgo.mobile.databinding.ItemAddressBinding
import com.joker.lpgo.mobile.databinding.ItemOrderBinding
import com.joker.lpgo.mobile.util.extension.getAddressInfo
import com.joker.lpgo.mobile.util.extension.show

class AddressAdapter (private val context: Context,
                    private val datas: ArrayList<Any>,
                    private val listener: ListenerAdapter
) : RecyclerView.Adapter<AddressAdapter.DataViewHolder>() {

    interface ListenerAdapter {
        fun onClickAddressItem(view: View, data: Any)
        fun onLongPressAddressItem(view: View, data: Any)
    }

    inner class DataViewHolder(val bindingView: ItemAddressBinding) : RecyclerView.ViewHolder(bindingView.root) {
        fun bind(data: Any, listener: ListenerAdapter, context: Context) {
            if (data is Address) {
                bindingView.textView7.text = data.name
                bindingView.textView8.text = "".getAddressInfo(context, data.lat, data.long)
                Log.e("RESULT", "bind: " )
                Log.e("RESULT", data.toString() )
                if (data.is_primary == "1") {
                    bindingView.status.imageView5.setImageResource(R.drawable.ic_charging_circle)
                    bindingView.status.textView9.text = "Primary"
                    bindingView.status.container.show(true)
                } else {
                    bindingView.status.container.show(false)
                }

                itemView.setOnClickListener {
                    listener.onClickAddressItem(it, data)
                }

                itemView.setOnLongClickListener {
                    listener.onLongPressAddressItem(it, data)
                    false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(datas[position], listener, context)

    fun addData(list: List<Address>) {
        datas.clear()
        datas.addAll(list)
        notifyDataSetChanged()
    }
}
