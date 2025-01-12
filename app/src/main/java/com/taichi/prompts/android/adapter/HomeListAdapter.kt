package com.taichi.prompts.android.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.taichi.prompts.android.R
import com.taichi.prompts.android.databinding.ItemItemBinding
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.android.repository.data.UserRecVO
import com.taichi.prompts.base.adapter.BaseRecyclerAdapter
import com.taichi.prompts.base.adapter.BaseViewHolder


class HomeListAdapter :
    BaseRecyclerAdapter<UserRecVO, HomeListAdapter.ItemViewHolder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(getBinding(parent, R.layout.item_item))
    }

    override fun bindHolder(holder: ItemViewHolder, position: Int) {
        val item = getDataList()?.get(position)

        holder.binding.itemData = item
        holder.binding.userNickName.text = item?.userNickName + "," + item?.age.toString()
        holder.binding.city.text = item?.liveCity
        holder.binding.question.text = "好吗"
        holder.binding.answer.text = "好的"
        val imageUrl : String = item?.headImgUrl.toString()
        Glide.with(holder.binding.root.context)
            .load(imageUrl)
            .placeholder(R.drawable.default_profile)
            .error(R.drawable.default_profile)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions().fitCenter())
            .into(holder.binding.headImg)
    }

    class ItemViewHolder(itemBinding: ItemItemBinding) :
        BaseViewHolder<ItemItemBinding>(itemBinding)
}