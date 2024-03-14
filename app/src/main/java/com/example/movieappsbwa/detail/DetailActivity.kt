package com.example.movieappsbwa.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieappsbwa.chosed.ChoseActivity
import com.example.movieappsbwa.databinding.ActivityDetailBinding
import com.example.movieappsbwa.adapter.PlaysAdapter
import com.example.movieappsbwa.utils.model.Film
import com.example.movieappsbwa.utils.model.Plays
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Plays>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data?.judul.toString())
            .child("play")

        binding.tvJudul.text = data?.judul
        binding.tvGenre.text = data?.genre
        binding.tvDesc.text = data?.desc
        binding.tvRate.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(binding.imgBackground)

        binding.rvPeoplePlayed.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        binding.btnBeliTiket.setOnClickListener {
            var toBooking = Intent(this, ChoseActivity::class.java).putExtra("data", data)
            startActivity(toBooking)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in snapshot.children) {
                    val film = getdataSnapshot.getValue(Plays::class.java)
                    dataList.add(film!!)
                }
                binding.rvPeoplePlayed.adapter = PlaysAdapter(dataList) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailActivity, "" + error, Toast.LENGTH_SHORT).show()
            }

        })
    }
}