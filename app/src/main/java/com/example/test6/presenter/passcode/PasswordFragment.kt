package com.example.test6.presenter.passcode

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.test6.R
import com.example.test6.databinding.FragmentPasswordBinding
import com.example.test6.presenter.common.BaseFragment
import com.example.test6.presenter.common.ResultWrapper
import kotlinx.coroutines.launch


class PasswordFragment : BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate),
    ClickCallBack {

    lateinit var myAdaper: ButtonsRecyclerAdapter
    private val viewModel: PasswordViewModel by viewModels()

    override fun setUp() {

        myAdaper = ButtonsRecyclerAdapter(this)
        val _layoutManager = GridLayoutManager(context, 3)

        binding.recycler.apply {
            layoutManager = _layoutManager
            setHasFixedSize(false)
            adapter = myAdaper
        }
        myAdaper.submitList(buttonsList())
    }

    private fun buttonsList() = listOf(
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
    )

    override fun setListeners() {
        lifecycleScope.launch {
            viewModel.passwordFlow.collect{passwordState ->
                val passwordDigits = passwordState.password.size
                val resultWrapper = passwordState.result
                showDotsForCurrentPassword(passwordDigits)

                resultWrapper?.let {
                    showResult(resultWrapper)
                }

            }
        }
    }

    override fun onNumberClicked(number: Int) {
        viewModel.apply {
            addDigit(number)
        }
    }

    override fun onDelete() {
        viewModel.apply {
            deleteLastDigit()
        }
    }

    override fun onFingerprint() {
        Toast.makeText(context,"You have no support for FingerPrint",Toast.LENGTH_SHORT).show()
    }

    private fun showDotsForCurrentPassword(currentPasswordLength:Int){
        when(currentPasswordLength){
            0 -> {
                binding.circle1.setImageResource(R.drawable.pin_empty_dot)
                binding.circle2.setImageResource(R.drawable.pin_empty_dot)
                binding.circle3.setImageResource(R.drawable.pin_empty_dot)
                binding.circle4.setImageResource(R.drawable.pin_empty_dot)
            }
            1 -> {
                binding.circle1.setImageResource(R.drawable.pin_full_dot)
                binding.circle2.setImageResource(R.drawable.pin_empty_dot)
            }
            2 -> {
                binding.circle2.setImageResource(R.drawable.pin_full_dot)
                binding.circle3.setImageResource(R.drawable.pin_empty_dot)
            }
            3 -> {
                binding.circle3.setImageResource(R.drawable.pin_full_dot)
                binding.circle4.setImageResource(R.drawable.pin_empty_dot)
            }
            4 ->{
                binding.circle4.setImageResource(R.drawable.pin_full_dot)
            }
        }
    }

    private fun showResult(result: ResultWrapper<ResponseToken>){
        when(result){
            is ResultWrapper.Success -> success()
            else -> incorrectPassword()
        }
    }

    private fun success(){
        Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show()
    }

    private fun incorrectPassword(){
        Toast.makeText(context,"Incorrect Password",Toast.LENGTH_SHORT).show()
    }

}