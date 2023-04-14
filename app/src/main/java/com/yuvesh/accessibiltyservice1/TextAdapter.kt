package com.yuvesh.accessibiltyservice1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextAdapter(private var textList:ArrayList<TextMessage>) :
    RecyclerView.Adapter<TextAdapter.TextViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.textitem, parent, false)
        return TextViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = textList[position]
        holder.packageView.text = item.packageName
        holder.text.text = item.text

    }

    override fun getItemCount() = textList.size

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val packageView: TextView = itemView.findViewById(R.id.packageName)
        val text: TextView = itemView.findViewById(R.id.text_item)

        val deleteButton: Button = itemView.findViewById(R.id.delete)

        init {
            deleteButton.setOnClickListener {
                // Call onDeleteButtonClick method when delete button is clicked
                onDeleteButtonClick(adapterPosition)
            }

        }

        private fun onDeleteButtonClick(position: Int) {
            textList.removeAt(position)
            notifyItemRemoved(position)
        }
    }



        fun setData(data: ArrayList<TextMessage>) {
            textList = data
            notifyDataSetChanged()
        }
    }
