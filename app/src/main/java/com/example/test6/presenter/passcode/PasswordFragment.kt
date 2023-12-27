package com.example.test6.presenter.passcode

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.test6.R
import com.example.test6.databinding.FragmentPasswordBinding
import com.example.test6.presenter.BaseFragment
import com.example.test6.presenter.ButtonsRecyclerAdapter
import com.example.test6.presenter.ClickCallBack


class PasswordFragment : BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate),ClickCallBack {

    lateinit var myAdaper: ButtonsRecyclerAdapter
    private var passwordDigit = 0

    override fun setUp() {

        myAdaper = ButtonsRecyclerAdapter(this)
        val _layoutManager = GridLayoutManager(context, 3)
        binding.recycler.apply {
            adapter = myAdaper
            layoutManager = _layoutManager
        }
        myAdaper.submitList(listOf(
            KeyboardButton(1,ButtonType.NUMBER,1),
            KeyboardButton(2,ButtonType.NUMBER,2),
            KeyboardButton(3,ButtonType.NUMBER,3),
            KeyboardButton(4,ButtonType.NUMBER,4),
            KeyboardButton(5,ButtonType.NUMBER,5),
            KeyboardButton(6,ButtonType.NUMBER,6),
            KeyboardButton(7,ButtonType.NUMBER,7),
            KeyboardButton(8,ButtonType.NUMBER,8),
            KeyboardButton(9,ButtonType.NUMBER,9),
            KeyboardButton(10,ButtonType.ICON, R.drawable.ic_touch),
            KeyboardButton(11,ButtonType.NUMBER,0),
            KeyboardButton(12,ButtonType.ICON,R.drawable.ic_delete)
        ))

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PasswordFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClicked(event: Event) {
        when(event.eventType){
            EventType.ADD -> add(event.number)
            else -> {}
        }
    }

    private fun add(number :Int){
        when(passwordDigit){
            0 -> binding.circle1.setImageResource(R.drawable.pin_full_dot)
            1 -> binding.circle2.setImageResource(R.drawable.pin_full_dot)
            2 -> binding.circle3.setImageResource(R.drawable.pin_full_dot)
            3 -> binding.circle4.setImageResource(R.drawable.pin_full_dot)
        }
        passwordDigit++
    }
}