package com.example.test6.presenter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test6.R
import com.example.test6.databinding.ButtonItemRecyclerBinding
import com.example.test6.presenter.passcode.ButtonType
import com.example.test6.presenter.passcode.Event
import com.example.test6.presenter.passcode.EventType
import com.example.test6.presenter.passcode.KeyboardButton


class ButtonsRecyclerAdapter(private val listener:ClickCallBack): ListAdapter<KeyboardButton, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<KeyboardButton>() {
            override fun areItemsTheSame(oldItem: KeyboardButton, newItem: KeyboardButton): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: KeyboardButton, newItem: KeyboardButton): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CourseViewHolder(private val binding: ButtonItemRecyclerBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            val button = currentList[position]
            when(button.buttonType){
                ButtonType.NUMBER -> setNumber(button.content)
                ButtonType.ICON -> setIcon(button.content)
            }
        }

        private fun setNumber(number :Int){
            binding.button.text = number.toString()
            binding.button.setTextAppearance(R.style.number_style)
            binding.button.setOnClickListener{
                val event = Event(number,EventType.ADD)
                listener.onClicked(event)
            }
        }

        private fun setIcon(drawable :Int){
            binding.button.apply {
                text = ""
                setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
                binding.button.setPaddingRelative(50, 0, 0, 0)
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CourseViewHolder(
            ButtonItemRecyclerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CourseViewHolder)holder.bind(position)
    }
}

interface ClickCallBack{
    fun onClicked(event: Event)
}