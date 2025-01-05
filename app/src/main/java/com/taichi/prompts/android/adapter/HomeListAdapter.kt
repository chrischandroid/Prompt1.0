package com.taichi.prompts.android.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.taichi.prompts.android.R
import com.taichi.prompts.android.databinding.ItemItemBinding
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.base.adapter.BaseRecyclerAdapter
import com.taichi.prompts.base.adapter.BaseViewHolder


class HomeListAdapter :
    BaseRecyclerAdapter<UserProfileMatchVOList, HomeListAdapter.ItemViewHolder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(getBinding(parent, R.layout.item_item))
    }

    override fun bindHolder(holder: ItemViewHolder, position: Int) {
        val item = getDataList()?.get(position)

        holder.binding.itemData = item
        holder.binding.userNickName.text = item?.userBaseVO?.userNickName + "," + item?.userBaseVO?.age.toString()
        holder.binding.city.text = item?.userBaseVO?.city
        val firstNonNullEntry: Map.Entry<String?, String?>? = item?.userProfileVO?.questionnaireMap?.entries?.firstOrNull {
            it.key != null && it.value != null
        }
        if (firstNonNullEntry != null) {
            holder.binding.question.text = firstNonNullEntry.key
            holder.binding.answer.text = firstNonNullEntry.value
        }
        val imageUrl : String = item?.userBaseVO?.headImgUrl.toString()
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