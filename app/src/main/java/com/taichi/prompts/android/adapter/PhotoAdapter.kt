package com.taichi.prompts.android.adapter

import com.taichi.prompts.android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taichi.prompts.android.repository.data.UserSeedVO

class PhotoAdapter(private val photoList: MutableList<UserSeedVO> = mutableListOf()) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private var onItemClickListener: ((UserSeedVO) -> Unit)? = null

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.photoImageView)
        val titleView: TextView = itemView.findViewById(R.id.photoTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount() = photoList.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photoList[position]
        holder.titleView.text = photo.nickName + "," + photo.age

        Glide.with(holder.itemView.context)
            .load(photo.headImgUrl)
            .into(holder.imageView)

        // 设置点击事件
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(photo)
        }
    }

    fun updateData(newPhotos: List<UserSeedVO>) {
        photoList.clear()
        photoList.addAll(newPhotos)
        notifyDataSetChanged()
    }

    // 设置点击监听器的方法
    fun setOnItemClickListener(listener: (UserSeedVO) -> Unit) {
        this.onItemClickListener = listener
    }
}