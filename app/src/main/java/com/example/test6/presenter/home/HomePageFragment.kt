package com.example.test6.presenter.home

import android.os.Bundle
import com.example.test6.databinding.FragmentHomePageBinding
import com.example.test6.presenter.BaseFragment


class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {

    companion object {

        @JvmStatic
        fun newInstance() =
            HomePageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}