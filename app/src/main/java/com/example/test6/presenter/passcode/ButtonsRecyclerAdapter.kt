package com.example.test6.presenter.passcode


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test6.R
import com.example.test6.databinding.ButtonItemIconRecyclerBinding
import com.example.test6.databinding.ButtonItemRecyclerBinding


class ButtonsRecyclerAdapter(private val listener: ClickCallBack): ListAdapter<KeyboardButton, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {

    companion object {

        const val NUMBER = 1
        const val ICON = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<KeyboardButton>() {
            override fun areItemsTheSame(oldItem: KeyboardButton, newItem: KeyboardButton): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: KeyboardButton, newItem: KeyboardButton): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class NumberViewHolder(private val binding: ButtonItemRecyclerBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            val button = currentList[position]
            setNumber(button.content)
        }

        private fun setNumber(number :Int){
            binding.button.text = number.toString()
            binding.button.setTextAppearance(R.style.number_style)
            binding.button.setOnClickListener{
                listener.onNumberClicked(number)
            }
        }

    }

    inner class IconViewHolder(private val binding: ButtonItemIconRecyclerBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            val button = currentList[position]
            setIcon(button.content)
            when(button.iconType){
                IconType.DELETE -> {
                    binding.button.setOnClickListener {
                        listener.onDelete()
                    }
                }
                IconType.FINGERPRINT -> {
                    binding.button.setOnClickListener {
                        listener.onFingerprint()
                    }
                }
            }
        }

        private fun setIcon(drawable :Int){
            binding.button.apply {
                setImageResource(drawable)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.buttonType) {
            ButtonType.NUMBER -> NUMBER  //item1
            ButtonType.ICON -> ICON  //item2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == NUMBER){
            return NumberViewHolder(
                ButtonItemRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )
        }else{
            return IconViewHolder(
                ButtonItemIconRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NumberViewHolder)holder.bind(position)
        else if(holder is IconViewHolder)holder.bind(position)
    }
}

interface ClickCallBack{
    fun onNumberClicked(number:Int)
    fun onDelete()
    fun onFingerprint()
}