package com.example.movieappsbwa.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappsbwa.R
import com.example.movieappsbwa.adapter.ComingSoonAdapter
import com.example.movieappsbwa.utils.Preference
import com.example.movieappsbwa.utils.model.Film
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class TicketFragment : Fragment() {

    private lateinit var tvDay: TextView
    private lateinit var tvTotal: TextView
    private lateinit var rvTiket: RecyclerView

    private lateinit var preference: Preference
    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        preference = Preference(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
        rvTiket.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        getData()
    }

    private fun initViews(view: View) {
        tvDay = view.findViewById(R.id.tv_day)
        tvTotal = view.findViewById(R.id.tv_total)
        rvTiket = view.findViewById(R.id.rc_tiket)

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnap in snapshot.children) {
                    val film = getDataSnap.getValue(Film::class.java)
                    dataList.add(film!!)
                }
                rvTiket.adapter = ComingSoonAdapter(dataList) {
                    val toResult = Intent(
                        context,
                        ResultTicketActivity::class.java
                    ).putExtra("data", it)
                    startActivity(toResult)

                }
                tvTotal.text = "${dataList.size} Movies"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

}