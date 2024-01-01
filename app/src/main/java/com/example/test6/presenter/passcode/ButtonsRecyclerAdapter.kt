package com.example.test6.presenter.passcode


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test6.R
import com.example.test6.databinding.ButtonItemIconRecyclerBinding
import com.example.test6.databinding.ButtonItemRecyclerBinding


class ButtonsRecyclerAdapter(private val listener: ClickCallBack): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = listOf<KeyboardButton>()
        @SuppressLint("NotifyDataSetChanged") // because this will only be changed once...
        set(value) {
            field = value
            notifyDataSetChanged() // Notify any registered observers that the data set has changed.
        }

    companion object {

        const val NUMBER = 1
        const val ICON = 2
    }

    inner class NumberViewHolder(private val binding: ButtonItemRecyclerBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            val button = data[position]
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
            val button = data[position]
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
        val item = data[position]
        return when (item.buttonType) {
            ButtonType.NUMBER -> NUMBER  //item1
            ButtonType.ICON -> ICON  //item2
        }
    }
    override fun getItemCount(): Int {
        return data.size
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