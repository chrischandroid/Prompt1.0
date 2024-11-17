package com.taichi.prompts.android.adapter

import android.view.ViewGroup
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
        holder.binding.headImgUrl.text = item?.base?.headImgUrl
        holder.binding.userNickName.text = item?.base?.userNickName
        holder.binding.age.text = item?.base?.age as CharSequence?
        holder.binding.birthday.text = item?.base?.birthDay
        holder.binding.city.text = item?.base?.city
    }

    class ItemViewHolder(itemBinding: ItemItemBinding) :
        BaseViewHolder<ItemItemBinding>(itemBinding)
}