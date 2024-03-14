package com.example.movieappsbwa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappsbwa.R
import com.example.movieappsbwa.utils.model.Checkout

class ResultTicketAdapter(
    private var data: List<Checkout>,
    private val listener: (Checkout) -> Unit
) : RecyclerView.Adapter<ResultTicketAdapter.LeagueViewHolder>() {
    lateinit var ContextAdapter: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.item_result_ticket, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_kursi)

        fun bindItem(
            data: Checkout,
            listener: (Checkout) -> Unit,
            context: Context,
            position: Int
        ) {
            tvTitle.setText("Seat No. " + data.kursi)

            itemView.setOnClickListener {
                listener(data)
            }

        }
    }
}