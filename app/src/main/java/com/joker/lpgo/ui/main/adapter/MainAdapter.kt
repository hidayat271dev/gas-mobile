package com.joker.lpgo.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joker.lpgo.R
import com.joker.lpgo.data.model.User
import com.joker.lpgo.databinding.ItemLayoutBinding

class MainAdapter(
    private val users: ArrayList<User>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    inner class DataViewHolder(val itemViewBindingA: ItemLayoutBinding) : RecyclerView.ViewHolder(itemViewBindingA.root) {
        fun bind(user: User) {
//            itemViewBindingA.textViewUserName.text = user.name
            itemViewBindingA.textViewUserEmail.text = user.email
//            Glide.with(itemViewBindingA.imageViewAvatar.context)
//                .load(user.avatar)
//                .into(itemViewBindingA.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<User>) {
        users.addAll(list)
    }
}
