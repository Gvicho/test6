package com.example.test6.presenter.home

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test6.R
import com.example.test6.data.common.ResultWrapper
import com.example.test6.databinding.FragmentHomePageBinding
import com.example.test6.presenter.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {

    lateinit var myAdaper: TransactionsRecyclerAdapter
    private val viewModel: HomePageViewModel by viewModels()
    override fun setUp() {
        myAdaper = TransactionsRecyclerAdapter()
        viewModel.loadTransactions()

        binding.recyclerTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myAdaper
            setHasFixedSize(false)
        }
    }

    private fun loading(isLoading:Boolean){
        lifecycleScope.launch {
            binding.apply {
                if(isLoading){
                    progressBar.visibility = View.VISIBLE
                }else{
                    progressBar.visibility = View.GONE
                }
            }
        }

    }

    private fun success(){
        Toast.makeText(context,"Successful !", Toast.LENGTH_LONG).show()
        d("tag123","succesfuly loaded")
    }

    private fun errorWhileLoading(){
        Toast.makeText(context,"Incorrect Password", Toast.LENGTH_SHORT).show()
    }

    override fun setListeners() {
        binding.apply {
            btnLogout.setOnClickListener{
                navigateToPasscodePage()
            }
        }

        lifecycleScope.launch {
            viewModel.transactionsFlow.collect{
                when(it!!.transactions){
                    is ResultWrapper.Success ->{
                        success()
                        myAdaper.submitList(it.transactions.data!!.transactionList)
                    }
                    is ResultWrapper.Loading ->{
                        loading(it.transactions.loading)
                    }
                    is ResultWrapper.Error ->{
                        errorWhileLoading()
                    }
                }
            }
        }
    }

    private fun navigateToPasscodePage(){
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.homePageFragment) {
            val action = HomePageFragmentDirections.actionHomePageFragmentToPasswordFragment()

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homePageFragment, true) // true to include the current fragment
                .build()

            navController.navigate(action,navOptions)
        }
    }
}