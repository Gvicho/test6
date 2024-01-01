package com.example.test6.presenter.passcode

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.test6.R
import com.example.test6.databinding.FragmentPasswordBinding
import com.example.test6.presenter.common.BaseFragment
import com.example.test6.data.common.ResultWrapper
import com.example.test6.domain.passcode.PasscodeResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate),
    ClickCallBack {

    lateinit var myAdaper: ButtonsRecyclerAdapter
    private val viewModel: PasswordViewModel by viewModels()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() { // I want passcode screen to be only vertical
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() { // so that other fragments can get back to normal
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    override fun setUp() {  // start reading here

        myAdaper = ButtonsRecyclerAdapter(this)
        val _layoutManager = GridLayoutManager(context, 3)

        binding.recycler.apply {
            layoutManager = _layoutManager
            setHasFixedSize(false)
            adapter = myAdaper
        }
        myAdaper.submitList(buttonsList())
    }

    private fun buttonsList() = viewModel.buttonList // list is declared in viewModel

    override fun setListeners() {
        lifecycleScope.launch {
            viewModel.passwordFlow.collect{passwordState ->
                val passwordDigits = passwordState.password.size
                val resultWrapper = passwordState.result
                showDotsForCurrentPassword(passwordDigits)

                resultWrapper?.let {
                    d("tag123","showResult")
                    showResult(resultWrapper)
                }

            }
        }
    }

    private fun navigateToHomePage(){
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.passwordFragment) {
            val action = PasswordFragmentDirections.actionPasswordFragmentToHomePageFragment()

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.passwordFragment, true) // true to include the current fragment
                .build()

            navController.navigate(action,navOptions)
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
                binding.circle1.setImageResource(R.drawable.pin_full_dot)
                binding.circle2.setImageResource(R.drawable.pin_full_dot)
                binding.circle3.setImageResource(R.drawable.pin_empty_dot)
            }
            3 -> {
                binding.circle1.setImageResource(R.drawable.pin_full_dot)
                binding.circle2.setImageResource(R.drawable.pin_full_dot)
                binding.circle3.setImageResource(R.drawable.pin_full_dot)
                binding.circle4.setImageResource(R.drawable.pin_empty_dot)
            }
            4 ->{
                binding.circle1.setImageResource(R.drawable.pin_full_dot)
                binding.circle2.setImageResource(R.drawable.pin_full_dot)
                binding.circle3.setImageResource(R.drawable.pin_full_dot)
                binding.circle4.setImageResource(R.drawable.pin_full_dot)
            }
        }
    }

    private fun showResult(result: ResultWrapper<PasscodeResponse>){
        when(result){
            is ResultWrapper.Success -> success()
            is ResultWrapper.Loading -> loading(result.loading)
            else -> incorrectPassword()
        }
    }

    private fun loading(isLoading:Boolean){
        lifecycleScope.launch {
            binding.apply {
                if(isLoading){
                    progressBar.visibility = View.VISIBLE
                    pinCircles.visibility = View.GONE
                }else{
                    progressBar.visibility = View.GONE
                    pinCircles.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun success(){
        navigateToHomePage()
        Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show()
    }

    private fun incorrectPassword(){
        Toast.makeText(context,"Incorrect Password",Toast.LENGTH_SHORT).show()
    }

}