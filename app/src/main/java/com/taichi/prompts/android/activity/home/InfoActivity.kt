package com.taichi.prompts.android.activity.home

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import androidx.compose.ui.graphics.Color
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.base.BaseActivity
import com.taichi.prompts.android.databinding.ActivityInfoBinding
import com.taichi.prompts.android.repository.data.UserAdmireRequest
import com.blankj.utilcode.util.SPUtils
import com.taichi.prompts.android.repository.data.UserSocialLikeRequest

class InfoActivity : BaseActivity<ActivityInfoBinding, InfoViewModel>()  {

    lateinit var myId : String
    lateinit var hisId : String

    override fun getLayoutId(): Int {
        return R.layout.activity_info
    }

    override fun getViewModelId(): Int {
        return BR.infoVm
    }

    override fun initViewData() {
        hisId = intent.getStringExtra("userId").toString()
        myId = SPUtils.getInstance().getString(Constants.SP_USER_ID)
        binding?.name?.setText(intent.getStringExtra("name"))
        binding?.careerTag?.setText(intent.getStringExtra("career"))
        binding?.schoolId?.setText(intent.getStringExtra("school"))
        binding?.hometag?.setText(intent.getStringExtra("hometown"))
        binding?.holdtag?.setText(intent.getStringExtra("asset"))
        binding?.mbti?.setText(intent.getStringExtra("mbti"))
        binding?.slender?.setText(intent.getStringExtra("weight")+"kg/"+ intent.getStringExtra("height") +"cm")
        binding?.age?.setText(intent.getStringExtra("age"))
        binding?.introduction?.setText(intent.getStringExtra("about"))
        Glide.with(this)
            .load(intent.getStringExtra("headImg"))
            .placeholder(R.drawable.default_img)
            .error(R.drawable.default_img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions().fitCenter())
            .into(binding!!.imageView)
        binding?.back?.setOnClickListener{
            finish()
        }
        binding?.leftImageView?.setOnClickListener{
            val dialog = Dialog(this)

            dialog.setContentView(R.layout.photo_comment_layout)
            dialog.window?.setGravity(Gravity.CENTER)

            val dialogImage = dialog.findViewById<ImageView>(R.id.dialog_image)
            val closeButton = dialog.findViewById<ImageView>(R.id.close_button)
            val dialogButton = dialog.findViewById<Button>(R.id.saveButton)

            Glide.with(this)
                .load(intent.getStringExtra("headImg"))
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().fitCenter())
                .into(dialogImage)
            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogButton.setOnClickListener {
                viewModel?.admire(UserSocialLikeRequest(1, 1, hisId, ""))
                //dialog.dismiss()
            }
            dialog.show()
        }
        binding?.rightImageView?.setOnClickListener{
            val dialog = Dialog(this)

            dialog.setContentView(R.layout.photo_comment_layout)
            dialog.window?.setGravity(Gravity.CENTER)

            val dialogImage = dialog.findViewById<ImageView>(R.id.dialog_image)
            val closeButton = dialog.findViewById<ImageView>(R.id.close_button)
            val dialogButton = dialog.findViewById<Button>(R.id.saveButton)

            Glide.with(this)
                .load(intent.getStringExtra("headImg"))
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().fitCenter())
                .into(dialogImage)
            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogButton.setOnClickListener {
                viewModel?.admire(UserSocialLikeRequest(2, 1, hisId, "good"))
                //dialog.dismiss()
            }
            dialog.show()
        }
        binding?.leftImageView1?.setOnClickListener{
            val dialog = Dialog(this)

            dialog.setContentView(R.layout.aboutme_comment_layout)
            dialog.window?.setGravity(Gravity.CENTER)

            val closeButton = dialog.findViewById<ImageView>(R.id.close_button1)
            val dialogButton = dialog.findViewById<Button>(R.id.saveButton1)

            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogButton.setOnClickListener {
                viewModel?.admire(UserSocialLikeRequest(1, 3, hisId, ""))
                //dialog.dismiss()
            }
            dialog.show()
        }
        binding?.rightImageView1?.setOnClickListener{
            val dialog = Dialog(this)

            dialog.setContentView(R.layout.aboutme_comment_layout)
            dialog.window?.setGravity(Gravity.CENTER)

            val closeButton = dialog.findViewById<ImageView>(R.id.close_button1)
            val dialogButton = dialog.findViewById<Button>(R.id.saveButton1)

            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogButton.setOnClickListener {
                viewModel?.admire(UserSocialLikeRequest(2, 3, hisId, "true"))
                //dialog.dismiss()
            }
            dialog.show()
        }
    }
}