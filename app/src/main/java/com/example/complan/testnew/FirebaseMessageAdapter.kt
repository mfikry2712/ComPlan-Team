package com.example.complan.testnew

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.complan.DataLaporan
import com.example.complan.R
import com.example.complan.databinding.ItemMessageBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FirebaseMessageAdapter(
    options: FirebaseRecyclerOptions<DataLaporan>,
    private val currentUserName: String?
) : FirebaseRecyclerAdapter<DataLaporan, FirebaseMessageAdapter.MessageViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_message, parent, false)
        val binding = ItemMessageBinding.bind(view)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int,
        model: DataLaporan
    ) {
        holder.bind(model)
    }

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataLaporan) {
            binding.namaOknum.text = item.suspect
            binding.idSekolah.text = item.idSchool
        }
    }
}