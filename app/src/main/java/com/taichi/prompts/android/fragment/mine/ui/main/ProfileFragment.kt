package com.taichi.prompts.android.fragment.mine.ui.main

import android.content.Intent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.github.gzuliyujiang.wheelpicker.AddressPicker
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode
import com.github.gzuliyujiang.wheelpicker.contract.OnAddressPickedListener
import com.github.gzuliyujiang.wheelpicker.entity.CityEntity
import com.github.gzuliyujiang.wheelpicker.entity.CountyEntity
import com.github.gzuliyujiang.wheelpicker.entity.ProvinceEntity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.common.OccupationPickerDialog
import com.taichi.prompts.android.databinding.FragmentProfileviewBinding
import com.taichi.prompts.android.fragment.mine.ProfileViewModel
import com.taichi.prompts.android.repository.Repository.updateProfile
import com.taichi.prompts.base.AppDatabase
import com.taichi.prompts.base.AvatarEntity
import com.taichi.prompts.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileFragment : BaseFragment<FragmentProfileviewBinding, ProfileViewModel>(), OnAddressPickedListener {

    private lateinit var cardView: CardView
    private lateinit var imageView: ImageView
    private lateinit var database: AppDatabase

    private lateinit var rectangleAsset: View
    private lateinit var frameAsset: View
    private lateinit var textFold: TextView
    private lateinit var imgFold: ImageView
    private lateinit var groupFold: RelativeLayout
    private lateinit var photoLayouts: Array<RelativeLayout>
    private var selectLocation = "Unknwon"

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
        val height = SPUtils.getInstance().getInt("height", 0)
        if (height != 0) {
            val text3 = view?.findViewById<TextView>(R.id.text_height)
            text3?.text = height.toString() + " cm"
        }
        val weight = SPUtils.getInstance().getInt("weight", 0)
        if (weight != 0) {
            val text = view?.findViewById<TextView>(R.id.text_weight)
            text?.text = weight.toString() + "kg"
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
            text6?.text = career
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
        photoLayouts = arrayOf(
            view?.findViewById<RelativeLayout>(R.id.photo1),
            view?.findViewById<RelativeLayout>(R.id.photo2),
            view?.findViewById<RelativeLayout>(R.id.photo3),
            view?.findViewById<RelativeLayout>(R.id.photo4),
            view?.findViewById<RelativeLayout>(R.id.photo5),
            view?.findViewById<RelativeLayout>(R.id.photo6)
        ) as Array<RelativeLayout>
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private fun saveText(key: String, text: String) {
        if (key == "weight" || key == "height") {
            try {
                val intValue = text.toInt()
                SPUtils.getInstance().put(key, intValue)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                SPUtils.getInstance().put(key, 0)
            }
        } else {
            SPUtils.getInstance().put(key, text)
        }
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

        if (key == "height") {
            editText.hint = "请输入身高 单位cm"
        } else if (key == "weight") {
            editText.hint = "请输入体重 单位kg"
        } else if (key == "asset") {
            editText.hint = "请输入资产和收入 如资产6位数 收入3k"
        }

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
        val pre = view?.findViewById<ImageView>(R.id.frame_preview)
        pre?.setOnClickListener{
            viewModel?.updateProfile()
        }
        val layout1 = view?.findViewById<RelativeLayout>(R.id.frame_user)
        layout1?.setOnClickListener{
            showBottomSheetDialog("userNickName")
        }
        val layout2 = view?.findViewById<RelativeLayout>(R.id.frame_birth)
        layout2?.setOnClickListener{
            val dialog : DatePickDialog  = DatePickDialog(requireContext())
            dialog.setOnShowListener {
                val cancelButton = dialog.findViewById<View>(R.id.cancel)
                if (cancelButton!= null) {
                    cancelButton.setVisibility(View.GONE)
                }
            }
            dialog.setYearLimt(50);
            dialog.setTitle("选择时间");
            dialog.setType(DateType.TYPE_YMD);
            dialog.setMessageFormat("yyyy-MM-dd")
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setOnChangeLisener(null)
            dialog.setOnSureLisener { selectedDate ->
                val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
                try {
                    val date = sdf.parse(selectedDate.toString())
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    val year = calendar.get(Calendar.YEAR)
                    val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
                    val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
                    val res = "${year}-${month}-${day}"
                    SPUtils.getInstance().put(Constants.SP_USER_BIRTH, res)
                    val text = view?.findViewById<TextView>(R.id.text_birth)
                    text?.text = res

                    val currentCalendar = Calendar.getInstance()
                    val currentYear = currentCalendar.get(Calendar.YEAR)
                    val currentMonth = currentCalendar.get(Calendar.MONTH) + 1
                    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)

                    var age = currentYear - year
                    if (currentMonth < month.toInt()) {
                        age--
                    } else if (currentMonth == month.toInt() && currentDay < day.toInt()) {
                        age--
                    }
                    if (age < 0) {
                        age = 0
                    }
                    SPUtils.getInstance().put("age", age)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            dialog.show()
        }
        val layout3 = view?.findViewById<RelativeLayout>(R.id.frame_height)
        layout3?.setOnClickListener{
            showBottomSheetDialog("height")
        }
        val layout31 = view?.findViewById<RelativeLayout>(R.id.frame_weight)
        layout31?.setOnClickListener{
            showBottomSheetDialog("weight")
        }
        val layout4 = view?.findViewById<RelativeLayout>(R.id.frame_city)
        layout4?.setOnClickListener{
            var picker : AddressPicker = AddressPicker(requireActivity())
            picker.setAddressMode(AddressMode.PROVINCE_CITY)
            picker.setDefaultValue("浙江省", "杭州市", "")
            picker.setOnAddressPickedListener(this)
            selectLocation = "city"
            picker.show()
        }
        val layout5 = view?.findViewById<RelativeLayout>(R.id.frame_school)
        layout5?.setOnClickListener{
            showBottomSheetDialog("school")
        }
        val layout6 = view?.findViewById<RelativeLayout>(R.id.frame_career)
        layout6?.setOnClickListener{
            // 显示选择器
            OccupationPickerDialog(requireActivity()) { industry, occupation ->
                saveText("career", industry + " " + occupation)
            }.show()
        }
        val layout7 = view?.findViewById<RelativeLayout>(R.id.frame_hometown)
        layout7?.setOnClickListener{
            var picker : AddressPicker = AddressPicker(requireActivity())
            picker.setAddressMode(AddressMode.PROVINCE_CITY)
            picker.setDefaultValue("浙江省", "杭州市", "")
            picker.setOnAddressPickedListener(this)
            selectLocation = "hometown"
            picker.show()
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
            selectedImageUri?.let { uri ->
                viewModel?.viewModelScope?.launch(Dispatchers.IO) {
                    val privateImagePath = copyImageToPrivateStorage(requireContext(), uri)
                    privateImagePath?.let { originalPath ->
                        // 压缩图片到1MB以内
                        val compressedPath = compressImage(requireContext(), originalPath, 1024) ?: originalPath

                        // 保存压缩后的图片路径到数据库
                        saveAvatarPathToDatabase(compressedPath)

                        // 更新UI
                        withContext(Dispatchers.Main) {
                            loadAvatar()
                            // 显示压缩后的图片
                            loadImageWithGlide(compressedPath)
                        }

                        viewModel?.updateImg(compressedPath)
                    }
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
            if (avatar == null) {
                requireActivity().runOnUiThread {
                    val url = SPUtils.getInstance().getString("headImgUrl")
                    Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.default_img)
                        .error(R.drawable.default_img)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(RequestOptions().fitCenter())
                        .into(binding!!.headImg)
                }
            }
            avatar?.let {
                val privateImagePath = it.imagePath
                requireActivity().runOnUiThread {
                    loadImageWithGlide(privateImagePath)
                }
            }
        }.start()
    }

    fun getExternalFilesDir(p0: Any) {}
    override fun onAddressPicked(
        province: ProvinceEntity?,
        city: CityEntity?,
        county: CountyEntity?
    ) {
        Log.e("Onselect", province?.name + " " + city?.name)
        SPUtils.getInstance().put(selectLocation, province?.name+" " + city?.name)
        initData()
    }

    private fun loadImageWithGlide(imagePath: String) {
        if (binding?.headImg != null) {
            val file = File(imagePath)
            if (file.exists()) {
                Glide.with(this)
                    .load(file)
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.default_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions().fitCenter())
                    .into(binding!!.headImg)
            } else {
                val url = SPUtils.getInstance().getString("headImgUrl")
                Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.default_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions().fitCenter())
                    .into(binding!!.headImg)
            }
        }
    }
}
/**
 * 压缩图片到指定大小以内
 * @param context 上下文
 * @param imagePath 原始图片路径
 * @param maxSize 目标大小（KB）
 * @return 压缩后的图片路径
 */
suspend fun compressImage(context: Context, imagePath: String, maxSize: Int = 1024): String? {
    return withContext(Dispatchers.IO) {
        try {
            // 1. 获取图片尺寸信息
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imagePath, options)

            // 2. 计算采样率（inSampleSize）
            val targetWidth = 1080 // 目标宽度，可根据需求调整
            val targetHeight = 1920 // 目标高度，可根据需求调整
            options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight)

            // 3. 加载图片（按采样率缩放）
            options.inJustDecodeBounds = false
            val bitmap = BitmapFactory.decodeFile(imagePath, options) ?: return@withContext null

            // 4. 质量压缩
            var quality = 100
            var compressedData: ByteArray? = null

            do {
                ByteArrayOutputStream().use { baos ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
                    compressedData = baos.toByteArray()
                }
                quality -= 10 // 每次降低10%质量
            } while (compressedData?.size?.div(1024) ?: 0 > maxSize && quality > 10)

            // 5. 保存压缩后的图片
            val compressedFile = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
            compressedData?.let { data ->
                compressedFile.outputStream().use { it.write(data) }
                compressedFile.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

/**
 * 计算图片采样率
 */
private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // 计算最大的inSampleSize值，该值为2的幂次方，且使图片尺寸大于等于目标尺寸
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}


