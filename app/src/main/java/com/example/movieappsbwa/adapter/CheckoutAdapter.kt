package com.example.movieappsbwa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappsbwa.R
import com.example.movieappsbwa.utils.model.Checkout
import java.text.NumberFormat
import java.util.Locale

class CheckoutAdapter(
    private var data: List<Checkout>,
    private val listener: (Checkout) -> Unit
) : RecyclerView.Adapter<CheckoutAdapter.LeagueViewHolder>() {
    lateinit var ContextAdapter: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.item_checkout, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_kursi)
        private val tvHarga: TextView = view.findViewById(R.id.tv_harga)


        fun bindItem(
            data: Checkout,
            listener: (Checkout) -> Unit,
            context: Context,
            position: Int
        ) {

            tvTitle.text = data.kursi
            tvHarga.text = data.harga

            val localID = Locale("id", "ID")
            val formatRp = NumberFormat.getCurrencyInstance(localID)
            tvHarga.text = formatRp.format(data.harga!!.toDouble())
            if (data.kursi!!.startsWith("Total")) {
                tvTitle.text = data.kursi
                tvTitle.setCompoundDrawables(null, null, null, null)
            } else {
                tvTitle.text = "Seat No. " + data.kursi
            }

            itemView.setOnClickListener {
                listener(data)
            }

        }
    }
}