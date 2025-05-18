package com.taichi.prompts.android.fragment.home

import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.activity.home.InfoActivity
import com.taichi.prompts.android.adapter.HomeListAdapter
import com.taichi.prompts.android.databinding.FragmentHomeBinding
import com.taichi.prompts.android.repository.data.UserProfileMatchVOList
import com.taichi.prompts.android.repository.data.UserRecVO
import com.taichi.prompts.base.BaseFragment
import link.fls.swipestack.SwipeStack

/**
 * 首页
 */
class FragHome : BaseFragment<FragmentHomeBinding, HomeViewModel>(),SwipeStack.SwipeStackListener {
    private val mData = ArrayList<UserRecVO>()
    private lateinit var mSwipeStack: SwipeStack
    private lateinit var mAdapter: SwipeStackAdapter
    private lateinit var mFab : Button
    private var isDataChanged = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelId(): Int {
        return BR.fragHomeVm
    }

    override fun initViewData() {
        initListView()
        viewModel?.homeListData?.observe(viewLifecycleOwner) { list ->
            if (list != null && list.isNotEmpty() && !isDataChanged) {
                mData.addAll(list)
                mAdapter.notifyDataSetChanged()
                isDataChanged = true
            }
        }
        viewModel?.recommandData?.observe(viewLifecycleOwner) { info ->
            if (info != null) {
                //Toast.makeText(requireContext(), "今日嘉宾"+ info.userNickName + info.height, Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), InfoActivity::class.java)
                intent.putExtra("name", info.userNickName)
                intent.putExtra("headImg", info.headImgUrl)
                intent.putExtra("mbti", info.mbti)
                intent.putExtra("age", info.age)
                intent.putExtra("height", info.height)
                intent.putExtra("weight", info.weight)
                intent.putExtra("school", info.school)
                intent.putExtra("career", info.career)
                intent.putExtra("hometown", info.hometown)
                intent.putExtra("asset", info.asset)
                intent.putExtra("about", info.introduction)
                intent.putExtra("tag", info.personalTags)
                intent.putExtra("userId", info.userId)
                if (info.showImgUrlList != null) {
                    val imgUrlArrayList = ArrayList(info.showImgUrlList)
                    intent.putExtra("img", imgUrlArrayList)
                } else {
                    val imgUrlArrayList = ArrayList<String>()
                    intent.putExtra("img", imgUrlArrayList)
                }
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel?.getHomeList()
    }

    private fun initListView() {
        mAdapter = SwipeStackAdapter(mData)
        mSwipeStack = binding!!.swipeStack
        mSwipeStack.setAdapter(mAdapter)
        mSwipeStack.setListener(this)
        mFab = binding!!.fabAdd
        mFab.setOnClickListener(){
            val swipedElement = mAdapter.getItem(mSwipeStack.getCurrentPosition())
            viewModel?.getUserDetail(swipedElement.userId)
        }
    }

    override fun onViewSwipedToLeft(position: Int) {
        val swipedElement = mAdapter.getItem(position)
    }

    override fun onViewSwipedToRight(position: Int) {
        val swipedElement = mAdapter.getItem(position)
    }

    override fun onStackEmpty() {
        Toast.makeText(requireContext(), "今日嘉宾已展示完毕", Toast.LENGTH_SHORT).show()
        mFab.visibility = View.INVISIBLE
    }

    inner class SwipeStackAdapter(private val mData: List<UserRecVO>) : BaseAdapter() {

        override fun getCount(): Int {
            return mData.size
        }

        override fun getItem(position: Int): UserRecVO {
            return mData[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = layoutInflater.inflate(R.layout.card, parent, false)
            }
            val item = mData[position]

            val name = view!!.findViewById<TextView>(R.id.userNickName)
            name.text = item?.userNickName + "," + item?.age.toString()

            val city = view!!.findViewById<TextView>(R.id.city)
            city.text = item?.liveCity

            val mbti = view!!.findViewById<TextView>(R.id.mbti)
            mbti.text = item?.mbtiTag

            val school = view!!.findViewById<TextView>(R.id.school)
            school.text = item?.college

            val slender = view!!.findViewById<TextView>(R.id.slender)
            slender.text = item?.weight + "/" + item?.height

            val qus = view!!.findViewById<TextView>(R.id.question)
            qus.text = item?.promptQuestion
            val ans = view!!.findViewById<TextView>(R.id.answer)
            ans.text = item?.promptAnswer

            val img = view!!.findViewById<ImageView>(R.id.headImg)
            val imageUrl : String = item?.headImgUrl.toString()
            Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.centerCropTransform())
                .into(img)

            return view
        }

        private fun showUserProfile(user: UserRecVO) {
            Toast.makeText(requireContext(), "展示用户: ${user.userNickName} 的资料", Toast.LENGTH_SHORT).show()
        }
    }
}

