package com.taichi.prompts.android.activity.login

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.transition.Visibility
import com.blankj.utilcode.util.SPUtils
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.taichi.prompts.android.R
import com.taichi.prompts.android.activity.home.TabActivity
import com.taichi.prompts.android.common.Constants
import com.taichi.prompts.android.repository.data.QuestionConfigVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WelcomeGuideActivity : AppCompatActivity() {
    private lateinit var bubble: RelativeLayout
    private lateinit var bubble1: RelativeLayout
    private lateinit var bubble2: RelativeLayout
    private lateinit var bubble3: RelativeLayout
    private lateinit var answer1: RelativeLayout
    private lateinit var answer2: RelativeLayout
    private lateinit var prof: ImageView
    private lateinit var ask1: RelativeLayout
    private lateinit var ask2: RelativeLayout
    private lateinit var answer3: RelativeLayout
    private lateinit var answer4: RelativeLayout
    private lateinit var prof1: ImageView
    private lateinit var ask3: RelativeLayout
    private lateinit var gender: String
    private lateinit var answer5 : RelativeLayout
    private lateinit var scrollView: ScrollView
    private var preDrawListener: ViewTreeObserver.OnPreDrawListener? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome_guide)

        bubble = findViewById(R.id.bubble)
        bubble1 = findViewById(R.id.bubble1)
        bubble2 = findViewById(R.id.bubble2)
        bubble3 = findViewById(R.id.frame_16244)
        answer1 = findViewById(R.id.answer1)
        answer2 = findViewById(R.id.frame_16241)
        prof = findViewById(R.id.icon94)
        ask1 = findViewById(R.id.bubblee)
        ask2 = findViewById(R.id.frame_1625)
        answer3 = findViewById(R.id.answer3)
        answer4 = findViewById(R.id.frame_162412)
        prof1 = findViewById(R.id.icon9o4)
        ask3 = findViewById(R.id.bubblede)
        answer5 = findViewById(R.id.frame_162f5)

        // 初始设置为不可见
        bubble.visibility = View.INVISIBLE
        bubble1.visibility = View.INVISIBLE
        bubble2.visibility = View.INVISIBLE
        bubble3.visibility = View.INVISIBLE
        answer1.visibility = View.INVISIBLE
        answer2.visibility = View.INVISIBLE
        prof.visibility = View.INVISIBLE
        ask1.visibility = View.INVISIBLE
        ask2.visibility = View.INVISIBLE
        answer3.visibility = View.INVISIBLE
        answer4.visibility = View.INVISIBLE
        prof1.visibility = View.INVISIBLE
        ask3.visibility = View.INVISIBLE
        answer5.visibility = View.INVISIBLE
        lifecycleScope.launch {
            showViewsWithDelay()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun showViewsWithDelay() {
        withContext(Dispatchers.Main) {
            delay(1000)
            bubble.visibility = View.VISIBLE
            delay(1000)
            bubble1.visibility = View.VISIBLE
            delay(1000)
            bubble2.visibility = View.VISIBLE
            delay(1000)
            bubble3.visibility = View.VISIBLE
            delay(1000)
            val dialog = Dialog(this@WelcomeGuideActivity)
            dialog.setContentView(R.layout.custom_dialog)
            val window = dialog.window
            if (window!= null) {
                window.setGravity(Gravity.BOTTOM)  // 设置对话框显示位置为底部
                val layoutParams = window.attributes
                layoutParams.width = baseContext.resources.displayMetrics.widthPixels
                val screenHeight = baseContext.resources.displayMetrics.heightPixels
                layoutParams.height = screenHeight / 4
                window.attributes = layoutParams
            }
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            val boy = dialog.findViewById<Button>(R.id.buttonm)
            val girl = dialog.findViewById<Button>(R.id.buttonf)
            boy.setOnClickListener {
                dialog.dismiss()
                val timeTextView : TextView = findViewById(R.id.textanswer)
                timeTextView.text = "男生"
                gender = "男孩"
                SPUtils.getInstance().put(Constants.SP_USER_GENDER, 1)
                answer1.visibility = View.VISIBLE
                
                val time : TextView = findViewById(R.id.timeTextView)
                val sdf = SimpleDateFormat("h:mm a", Locale.US)
                val currentTime = sdf.format(Date())
                time.text = getTime()
                answer2.visibility = View.VISIBLE
                lifecycleScope.launch {
                    goNextBirth()
                }
            }
            girl.setOnClickListener {
                dialog.dismiss()
                val timeTextView : TextView = findViewById(R.id.textanswer)
                timeTextView.text = "女生"
                gender = "女孩"
                answer1.visibility = View.VISIBLE
                SPUtils.getInstance().put(Constants.SP_USER_GENDER, 2)

                val time : TextView = findViewById(R.id.timeTextView)
                val sdf = SimpleDateFormat("h:mm a", Locale.US)
                val currentTime = sdf.format(Date())
                time.text = getTime()
                answer2.visibility = View.VISIBLE
                lifecycleScope.launch {
                    goNextBirth()
                }

            }
            dialog.show()
        }
    }

    private fun getTime() : String {
        val sdf = SimpleDateFormat("h:mm a", Locale.US)
        val currentTime = sdf.format(Date())
        return currentTime
    }

    private suspend fun ShowNext(gender: String, stars :String) {
        val nextView : View = findViewById(R.id.rectangle_2)
        nextView.visibility = View.VISIBLE
        scrollView = findViewById(R.id.scrollView)
        val questionList1 = intent.getParcelableArrayListExtra<QuestionConfigVO>("question1")
        val questionList2 = intent.getParcelableArrayListExtra<QuestionConfigVO>("question2")

        // 使用匿名内部类创建OnPreDrawListener
        scrollView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                // 确保只处理一次，避免重复执行
                if (scrollView.viewTreeObserver.isAlive) {
                    // 通过this来移除当前匿名内部类实例代表的监听器
                    scrollView.viewTreeObserver.removeOnPreDrawListener(this)
                }

                // 直接调用fullScroll方法滚动到底部
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                return true
            }
        })
        delay(1500)
        var callback : String = "又是一位可爱的" + stars + gender + "捏！😄 接下来\n还差最后一步，请告诉我要怎么称呼你吧"
        val t : TextView = findViewById(R.id.textVidewe)
        t.text = callback
        prof1.visibility = View.VISIBLE
        ask3.visibility = View.VISIBLE
        answer5.visibility = View.VISIBLE
        delay(1000)

        val dialog = Dialog(this@WelcomeGuideActivity)
        dialog.setContentView(R.layout.name)
        val window = dialog.window
        if (window!= null) {
            window.setGravity(Gravity.BOTTOM)  // 设置对话框显示位置为底部
            val layoutParams = window.attributes
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT  // 设置宽度占满屏幕
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT  // 高度自适应内容
            window.attributes = layoutParams
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        val confrim = dialog.findViewById<TextView>(R.id.button_labe)
        val text = dialog.findViewById<EditText>(R.id.editText)
        confrim.setOnClickListener {
            val inputText = text.text.toString().trim()
            if (inputText.isNotEmpty()) {
                Log.d("promptname", "输入的内容是: $inputText")
            }
            dialog.dismiss()
            val intent = Intent(this, CompleteGuideActivity::class.java)
            SPUtils.getInstance().put(Constants.SP_USER_NICKNAME, inputText)
            intent.putExtra("name", inputText)
            intent.putExtra("type", 1)

            if (questionList1 != null) {
                intent.putParcelableArrayListExtra("question1", questionList1)
            }
            if (questionList2 != null) {
                intent.putParcelableArrayListExtra("question2", questionList2)
            }
            startActivity(intent)
        }

        dialog.show()
    }

    private suspend fun goNextBirth() {
        val nextView : View = findViewById(R.id.rectangle_1)
        nextView.visibility = View.VISIBLE
        delay(1000)
        prof.visibility = View.VISIBLE
        ask1.visibility = View.VISIBLE
        delay(300)
        ask2.visibility = View.VISIBLE
        delay(1000)
        /*
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.set(1900, Calendar.JANUARY, 1)

        val maxDateCalendar = Calendar.getInstance()
        maxDateCalendar.set(2025, Calendar.DECEMBER, 31)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                Log.d("SelectedDate", "Selected year: $year, month: ${month + 1}, day: $day")
                val ans = "${year}年${month + 1}月${day}日"
                val timeTextView : TextView = findViewById(R.id.textanswer3)
                timeTextView.text = ans
                answer3.visibility = View.VISIBLE

                val time : TextView = findViewById(R.id.timeTextView3)
                val sdf = SimpleDateFormat("h:mm a", Locale.US)
                val currentTime = sdf.format(Date())
                time.text = getTime()
                answer4.visibility = View.VISIBLE
                val selectedMonth = month + 1
                val selectedDay = day
                var zodiac = ""
                when {
                    (selectedMonth == 1 && selectedDay >= 20) || (selectedMonth == 2 && selectedDay <= 18) -> zodiac = "水瓶座"
                    (selectedMonth == 2 && selectedDay >= 19) || (selectedMonth == 3 && selectedDay <= 20) -> zodiac = "双鱼座"
                    (selectedMonth == 3 && selectedDay >= 21) || (selectedMonth == 4 && selectedDay <= 19) -> zodiac = "白羊座"
                    (selectedMonth == 4 && selectedDay >= 20) || (selectedMonth == 5 && selectedDay <= 20) -> zodiac = "金牛座"
                    (selectedMonth == 5 && selectedDay >= 21) || (selectedMonth == 6 && selectedDay <= 20) -> zodiac = "双子座"
                    (selectedMonth == 6 && selectedDay >= 21) || (selectedMonth == 7 && selectedDay <= 22) -> zodiac = "巨蟹座"
                    (selectedMonth == 7 && selectedDay >= 23) || (selectedMonth == 8 && selectedDay <= 22) -> zodiac = "狮子座"
                    (selectedMonth == 8 && selectedDay >= 23) || (selectedMonth == 9 && selectedDay <= 22) -> zodiac = "处女座"
                    (selectedMonth == 9 && selectedDay >= 23) || (selectedMonth == 10 && selectedDay <= 22) -> zodiac = "天秤座"
                    (selectedMonth == 10 && selectedDay >= 23) || (selectedMonth == 11 && selectedDay <= 21) -> zodiac = "天蝎座"
                    (selectedMonth == 11 && selectedDay >= 22) || (selectedMonth == 12 && selectedDay <= 21) -> zodiac = "射手座"
                    (selectedMonth == 12 && selectedDay >= 22) || (selectedMonth == 1 && selectedDay <= 19) -> zodiac = "摩羯座"
                }
                lifecycleScope.launch {
                    ShowNext(gender, zodiac)
                }

            }, year, month, day)

        datePickerDialog.datePicker.minDate = minDateCalendar.timeInMillis
        datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis

        val window = datePickerDialog.window
        if (window!= null) {
            window.setGravity(Gravity.BOTTOM)  // 设置对话框显示位置为底部
            val layoutParams = window.attributes
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT  // 设置宽度占满屏幕
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT  // 高度自适应内容
            window.attributes = layoutParams
        }
        datePickerDialog.setCancelable(false)
        datePickerDialog.setCanceledOnTouchOutside(false)

        datePickerDialog.show()
        */
        val dialog : DatePickDialog  = DatePickDialog(this)
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
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val ans = "${year}年${month + 1}月${day}日"
                val res = "${year}-${month + 1}-${day}"
                SPUtils.getInstance().put(Constants.SP_USER_BIRTH, res)
                val timeTextView : TextView = findViewById(R.id.textanswer3)
                timeTextView.text = ans
                answer3.visibility = View.VISIBLE

                val time : TextView = findViewById(R.id.timeTextView3)
                val sdf = SimpleDateFormat("h:mm a", Locale.US)
                val currentTime = sdf.format(Date())
                time.text = getTime()
                answer4.visibility = View.VISIBLE

                val selectedMonth = month + 1
                val selectedDay = day
                var zodiac = ""
                when {
                    (selectedMonth == 1 && selectedDay >= 20) || (selectedMonth == 2 && selectedDay <= 18) -> zodiac = "水瓶座"
                    (selectedMonth == 2 && selectedDay >= 19) || (selectedMonth == 3 && selectedDay <= 20) -> zodiac = "双鱼座"
                    (selectedMonth == 3 && selectedDay >= 21) || (selectedMonth == 4 && selectedDay <= 19) -> zodiac = "白羊座"
                    (selectedMonth == 4 && selectedDay >= 20) || (selectedMonth == 5 && selectedDay <= 20) -> zodiac = "金牛座"
                    (selectedMonth == 5 && selectedDay >= 21) || (selectedMonth == 6 && selectedDay <= 20) -> zodiac = "双子座"
                    (selectedMonth == 6 && selectedDay >= 21) || (selectedMonth == 7 && selectedDay <= 22) -> zodiac = "巨蟹座"
                    (selectedMonth == 7 && selectedDay >= 23) || (selectedMonth == 8 && selectedDay <= 22) -> zodiac = "狮子座"
                    (selectedMonth == 8 && selectedDay >= 23) || (selectedMonth == 9 && selectedDay <= 22) -> zodiac = "处女座"
                    (selectedMonth == 9 && selectedDay >= 23) || (selectedMonth == 10 && selectedDay <= 22) -> zodiac = "天秤座"
                    (selectedMonth == 10 && selectedDay >= 23) || (selectedMonth == 11 && selectedDay <= 21) -> zodiac = "天蝎座"
                    (selectedMonth == 11 && selectedDay >= 22) || (selectedMonth == 12 && selectedDay <= 21) -> zodiac = "射手座"
                    (selectedMonth == 12 && selectedDay >= 22) || (selectedMonth == 1 && selectedDay <= 19) -> zodiac = "摩羯座"
                }
                lifecycleScope.launch {
                    ShowNext(gender, zodiac)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        dialog.show()
    }
}