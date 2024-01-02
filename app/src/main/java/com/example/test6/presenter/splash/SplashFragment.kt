package com.example.test6.presenter.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.test6.R
import com.example.test6.databinding.FragmentSplashBinding
import com.example.test6.presenter.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun initData(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.readSession()
            }
        }

    }

    override fun setUp() {
        bindObservers()
    }

    private fun bindObservers(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sessionFlow.collect{
                    openSpecificFragment(it)
                }
            }
        }
    }

    private fun openSpecificFragment(splashState :SplashState){
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.splashFragment) {

            if(splashState.session){

                val action = SplashFragmentDirections.actionSplashFragmentToHomePageFragment()

                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true) // true to include the current fragment
                    .build()

                navController.navigate(action,navOptions)

            }else{
                val action = SplashFragmentDirections.actionSplashFragmentToPasswordFragment()

                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true) // true to include the current fragment
                    .build()

                navController.navigate(action,navOptions)
            }
        }
    }

}