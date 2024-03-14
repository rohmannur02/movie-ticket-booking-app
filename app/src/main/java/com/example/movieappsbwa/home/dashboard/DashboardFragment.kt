package com.example.movieappsbwa.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieappsbwa.R
import com.example.movieappsbwa.adapter.ComingSoonAdapter
import com.example.movieappsbwa.adapter.MovieListAdapter
import com.example.movieappsbwa.detail.DetailActivity
import com.example.movieappsbwa.utils.model.Film
import com.example.movieappsbwa.utils.Preference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.NumberFormat
import java.util.Locale


class DashboardFragment : Fragment() {
    private lateinit var tvName: TextView
    private lateinit var tvSaldo: TextView
    private lateinit var imgProfile: ImageView
    private lateinit var rvComingSoon: RecyclerView
    private lateinit var rvMovieList: RecyclerView

    private lateinit var preference: Preference
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniViews(view)
        loadData()

    }

    private fun iniViews(view: View) {
        tvName = view.findViewById(R.id.txt_name)
        tvSaldo = view.findViewById(R.id.txt_idr280k)
        imgProfile = view.findViewById(R.id.img_photoUser)
        rvComingSoon = view.findViewById(R.id.rv_comingSoon)
        rvMovieList = view.findViewById(R.id.rv_listMovie)
    }

    private fun loadData() {
        preference = Preference(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tvName.text = preference.getValue("nama")
        if (!preference.getValue("saldo").equals("")) {
            currency(preference.getValue("saldo")!!.toDouble(), tvSaldo)
        }
        Glide.with(this)
            .load(preference.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(imgProfile)
        rvMovieList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvComingSoon.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (getdataSnapshot in snapshot.children) {
                    var film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rvComingSoon.adapter = ComingSoonAdapter(dataList) {

                }
                rvMovieList.adapter = MovieListAdapter(dataList) {
                    val intent = Intent(
                        context,
                        DetailActivity::class.java
                    ).putExtra("data", it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun currency(harga: Double, textview: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textview.text = format.format(harga as Double)

    }
}