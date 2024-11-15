package com.taichi.prompts.android.fragment.mine.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import com.taichi.prompts.android.R
import com.taichi.prompts.android.BR
import com.taichi.prompts.android.databinding.FragmentProfileviewBinding
import com.taichi.prompts.android.fragment.mine.ProfileViewModel
import com.taichi.prompts.android.repository.Repository.updateProfile
import com.taichi.prompts.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileviewBinding, ProfileViewModel>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_profileview
    }

    override fun getViewModelId(): Int {
        return BR.profileVm
    }

    override fun initViewData() {
        initClick()
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    fun initClick() {
        binding?.spinnerMarriage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 获取选择的文本
                val selectedText = parent?.getItemAtPosition(position).toString()
                // 更新ViewModel
                Log.e("TEST", "-------marriage" + selectedText)
                viewModel?.marriage?.set(selectedText)
            }
        }
        binding?.spinnerDegree?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 获取选择的文本
                val selectedText = parent?.getItemAtPosition(position).toString()
                // 更新ViewModel
                Log.e("TEST", "-------degree" + selectedText)
                viewModel?.degree?.set(selectedText)
            }
        }
        binding?.spinnerGender?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 获取选择的文本
                val selectedText = parent?.getItemAtPosition(position).toString()
                // 更新ViewModel
                Log.e("TEST", "-------gender" + selectedText)
                viewModel?.gender?.set(selectedText)
            }
        }
        binding?.button?.setOnClickListener{
            viewModel?.updateProfile()
        }

    }

}



