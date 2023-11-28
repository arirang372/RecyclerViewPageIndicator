package com.john.recyclerviewpageindicator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.john.recyclerviewpageindicator.data.model.ApiUser
import com.john.recyclerviewpageindicator.databinding.ItemLayoutBinding

class ApiUserAdapter(
    private val users: ArrayList<ApiUser>
) : RecyclerView.Adapter<ApiUserAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ApiUser) {
            binding.textViewUserName.text = user.name
            binding.textViewUserEmail.text = user.email
            Glide.with(binding.imageViewAvatar.context)
                .load(user.avatar)
                .into(binding.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<ApiUser>) {
        users.addAll(list)
    }

}