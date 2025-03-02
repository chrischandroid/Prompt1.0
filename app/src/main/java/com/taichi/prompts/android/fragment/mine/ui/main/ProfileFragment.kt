package com.taichi.prompts.android.fragment.mine.ui.main

import android.content.Intent
import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.room.Room
import com.blankj.utilcode.util.SPUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.databinding.FragmentProfileviewBinding
import com.taichi.prompts.android.fragment.mine.ProfileViewModel
import com.taichi.prompts.android.repository.Repository.updateProfile
import com.taichi.prompts.base.AppDatabase
import com.taichi.prompts.base.AvatarEntity
import com.taichi.prompts.base.BaseFragment
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : BaseFragment<FragmentProfileviewBinding, ProfileViewModel>(){

    private lateinit var cardView: CardView
    private lateinit var imageView: ImageView
    private lateinit var database: AppDatabase

    private lateinit var rectangleAsset: View
    private lateinit var frameAsset: View
    private lateinit var textFold: TextView
    private lateinit var imgFold: ImageView
    private lateinit var groupFold: RelativeLayout

    private val PICK_IMAGE_REQUEST = 1
    override fun getLayoutId(): Int {
        return R.layout.fragment_profileview
    }

    override fun getViewModelId(): Int {
        return BR.profileVm
    }

    override fun initViewData() {
        initData()
        initClick()
    }

    fun initData() {
        val name = SPUtils.getInstance().getString("userNickName")
        if (name != null && name.isNotEmpty()) {
            val text1 = view?.findViewById<TextView>(R.id.text_name)
            text1?.text = name
        }
        val birth = SPUtils.getInstance().getString("birthDay")
        if (birth != null && birth.isNotEmpty()) {
            val text2 = view?.findViewById<TextView>(R.id.text_birth)
            text2?.text = birth
        }
        val height = SPUtils.getInstance().getString("height")
        if (height != null && height.isNotEmpty()) {
            val text3 = view?.findViewById<TextView>(R.id.text_height)
            text3?.text = height
        }
        val city = SPUtils.getInstance().getString("city")
        if (city != null && city.isNotEmpty()) {
            val text4 = view?.findViewById<TextView>(R.id.text_city)
            text4?.text = city
        }
        val school = SPUtils.getInstance().getString("school")
        if (school != null && school.isNotEmpty()) {
            val text5= view?.findViewById<TextView>(R.id.text_school)
            text5?.text = school
        }
        val career = SPUtils.getInstance().getString("career")
        if (career != null && career.isNotEmpty()) {
            val text6= view?.findViewById<TextView>(R.id.text_career)
            text6?.text = school
        }
        val hometown = SPUtils.getInstance().getString("hometown")
        if (hometown != null && hometown.isNotEmpty()) {
            val text7= view?.findViewById<TextView>(R.id.text_hometown)
            text7?.text = hometown
        }
        val asset = SPUtils.getInstance().getString("asset")
        if (asset != null && asset.isNotEmpty()) {
            val text8= view?.findViewById<TextView>(R.id.text_asset)
            text8?.text = asset
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private fun saveText(key: String, text: String) {
        SPUtils.getInstance().put(key, text)
        initData()
    }

    private fun showBottomSheetDialog(key : String) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_bottom_sheet, null)
        dialog.setContentView(view)

        // 获取屏幕高度
        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val editTextHeight = (screenHeight * 0.5).toInt()

        // 设置 EditText 的高度
        val editText = view.findViewById<EditText>(R.id.editText)
        editText.layoutParams.height = editTextHeight
        editText.requestLayout() // 刷新布局

        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val inputText = editText.text.toString()
            // 保存输入的文字
            saveText(key, inputText)
            // 关闭 Dialog
            dialog.dismiss()
        }

        dialog.show()
    }

    fun initClick() {
        val layout0 = view?.findViewById<ImageView>(R.id.frame_back)
        layout0?.setOnClickListener{
            viewModel?.updateProfile()
            parentFragmentManager.popBackStack()
            requireActivity().finish()
        }
        val layout1 = view?.findViewById<RelativeLayout>(R.id.frame_user)
        layout1?.setOnClickListener{
            showBottomSheetDialog("userNickName")
        }
        val layout2 = view?.findViewById<RelativeLayout>(R.id.frame_birth)
        layout2?.setOnClickListener{
            showBottomSheetDialog("birthDay")
        }
        val layout3 = view?.findViewById<RelativeLayout>(R.id.frame_height)
        layout3?.setOnClickListener{
            showBottomSheetDialog("height")
        }
        val layout4 = view?.findViewById<RelativeLayout>(R.id.frame_city)
        layout4?.setOnClickListener{
            showBottomSheetDialog("city")
        }
        val layout5 = view?.findViewById<RelativeLayout>(R.id.frame_school)
        layout5?.setOnClickListener{
            showBottomSheetDialog("school")
        }
        val layout6 = view?.findViewById<RelativeLayout>(R.id.frame_career)
        layout6?.setOnClickListener{
            showBottomSheetDialog("career")
        }
        val layout7 = view?.findViewById<RelativeLayout>(R.id.frame_hometown)
        layout7?.setOnClickListener{
            showBottomSheetDialog("hometown")
        }
        val layout8 = view?.findViewById<RelativeLayout>(R.id.frame_asset)
        layout8?.setOnClickListener{
            showBottomSheetDialog("asset")
        }
        val linearLayout = view?.findViewById<LinearLayout>(R.id.gallery1)
        linearLayout?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                linearLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val totalWidth = linearLayout.width
                val availableWidth = totalWidth - 30
                val relativeLayoutWidth = availableWidth / 3

                val relativeLayout1 = view?.findViewById<RelativeLayout>(R.id.photo1)
                val relativeLayout2 = view?.findViewById<RelativeLayout>(R.id.photo2)
                val relativeLayout3 = view?.findViewById<RelativeLayout>(R.id.photo3)

                val layoutParams = LinearLayout.LayoutParams(relativeLayoutWidth, relativeLayoutWidth)
                relativeLayout1?.layoutParams = layoutParams
                relativeLayout2?.layoutParams = layoutParams
                relativeLayout3?.layoutParams = layoutParams

                val relativeLayout4 = view?.findViewById<RelativeLayout>(R.id.photo4)
                val relativeLayout5 = view?.findViewById<RelativeLayout>(R.id.photo5)
                val relativeLayout6 = view?.findViewById<RelativeLayout>(R.id.photo6)
                relativeLayout4?.layoutParams = layoutParams
                relativeLayout5?.layoutParams = layoutParams
                relativeLayout6?.layoutParams = layoutParams

                relativeLayout1?.requestLayout()
                relativeLayout2?.requestLayout()
                relativeLayout3?.requestLayout()
            }
        })

        val text1 = view?.findViewById<TextView>(R.id.spanner1)
        val originalText = "封面照 *"
        val spannableString = SpannableString(originalText)
        val asteriskIndex = originalText.indexOf("*")
        if (asteriskIndex != -1) {
            spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                asteriskIndex,
                asteriskIndex + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text1?.text = spannableString

        val text2 = view?.findViewById<TextView>(R.id.spanner2)
        val originalText1 = "我觉得 *"
        val spannableString1 = SpannableString(originalText1)
        val asteriskIndex1 = originalText1.indexOf("*")
        if (asteriskIndex1 != -1) {
            spannableString1.setSpan(
                ForegroundColorSpan(Color.RED),
                asteriskIndex1,
                asteriskIndex1 + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text2?.text = spannableString1
        initHeadImg()


        rectangleAsset = view?.findViewById(R.id.rectangle_asset)!!
        frameAsset = view?.findViewById(R.id.frame_asset)!!
        textFold = view?.findViewById(R.id.text_fold)!!
        imgFold = view?.findViewById(R.id.img_fold)!!
        groupFold = view?.findViewById(R.id.group_fold)!!

        // 设置默认状态
        rectangleAsset.visibility = View.GONE
        frameAsset.visibility = View.GONE
        textFold.text = "展开"
        imgFold.setBackgroundResource(R.drawable.downarray)

        // 设置点击事件
        groupFold.setOnClickListener {
            toggleVisibility()
        }
    }
    private fun toggleVisibility() {
        if (rectangleAsset.visibility == View.GONE) {
            // 展开状态
            rectangleAsset.visibility = View.VISIBLE
            frameAsset.visibility = View.VISIBLE
            textFold.text = "收起"
            imgFold.setBackgroundResource(R.drawable.uparray)
        } else {
            // 收起状态
            rectangleAsset.visibility = View.GONE
            frameAsset.visibility = View.GONE
            textFold.text = "展开"
            imgFold.setBackgroundResource(R.drawable.downarray)
        }
    }

    fun initHeadImg() {
        cardView = view?.findViewById(R.id.img)!!
        imageView = view?.findViewById(R.id.headImg)!!
        database = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java,
            "image-database"
        ).build()

        loadAvatar()

        cardView.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            selectedImageUri?.let {
                val privateImagePath = copyImageToPrivateStorage(requireContext(), it)
                if (privateImagePath != null) {
                    // 保存新的图片路径到数据库
                    saveAvatarPathToDatabase(privateImagePath)

                    // 显示新的图片
                    imageView.setImageURI(Uri.fromFile(File(privateImagePath)))
                    SPUtils.getInstance().put("headImgUrl", Uri.fromFile(File(privateImagePath)).toString())
                }
            }
        }
    }
    
    fun copyImageToPrivateStorage(context: Context, uri: Uri): String? {
        try {
            // 获取应用的私有存储目录
            val privateDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            if (privateDir != null) {
                // 删除旧的图片文件
                val files = privateDir.listFiles()
                files?.forEach { it.delete() }

                // 创建新的图片文件
                val newFile = File(privateDir, "avatar.jpg")

                // 打开输入流读取选择的图片
                val inputStream = context.contentResolver.openInputStream(uri)
                // 打开输出流写入新的图片文件
                val outputStream = FileOutputStream(newFile)

                inputStream.use { input ->
                    outputStream.use { output ->
                        val buffer = ByteArray(4 * 1024) // 4KB 缓冲区
                        var bytesRead: Int
                        while (input?.read(buffer).also { bytesRead = it!! } != -1) {
                            output.write(buffer, 0, bytesRead)
                        }
                    }
                }

                return newFile.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun saveAvatarPathToDatabase(imagePath: String) {
        val avatarEntity = AvatarEntity(imagePath = imagePath)
        Thread {
            database.avatarDao().insertAvatar(avatarEntity)
        }.start()
    }

    private fun loadAvatar() {
        Thread {
            val avatar = database.avatarDao().getAvatar()
            avatar?.let {
                val privateImagePath = it.imagePath
                requireActivity().runOnUiThread {
                    val file = File(privateImagePath)
                    if (file.exists()) {
                        imageView.setImageURI(Uri.fromFile(file))
                    }
                }
            }
        }.start()
    }

    fun getExternalFilesDir(p0: Any) {}
}



