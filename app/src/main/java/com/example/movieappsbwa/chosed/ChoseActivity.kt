package com.example.movieappsbwa.chosed

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappsbwa.R
import com.example.movieappsbwa.databinding.ActivityChoseBinding
import com.example.movieappsbwa.payment.CheckoutActivity
import com.example.movieappsbwa.utils.model.Checkout
import com.example.movieappsbwa.utils.model.Film

class ChoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChoseBinding
    var statusA3: Boolean = false
    var statusA4: Boolean = false
    var total: Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Film>("data")
        binding.tvKursi.text = data?.judul

        binding.a3.setOnClickListener {
            if (statusA3) {
                binding.a3.setImageResource(R.drawable.ic_rectangle_empty)
                statusA3 = false
                total -= 1
                buyTicket(total)
            } else {
                binding.a3.setImageResource(R.drawable.ic_rectangle_selected)
                statusA3 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A3", "50000")
                dataList.add(data)
            }
        }

        binding.a4.setOnClickListener {
            if (statusA4) {
                binding.a4.setImageResource(R.drawable.ic_rectangle_empty)
                statusA4 = false
                total -= 1
                buyTicket(total)
            } else {
                binding.a4.setImageResource(R.drawable.ic_rectangle_selected)
                statusA4 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A4", "70000")
                dataList.add(data)
            }
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnBeliTiket.setOnClickListener {
            val toCheckout = Intent(this, CheckoutActivity::class.java).putExtra("data", dataList)
                .putExtra("datas", data)
            startActivity(toCheckout)
        }

    }

    private fun buyTicket(total: Int) {
        if (total == 0) {
            binding.btnBeliTiket.text = "Beli Tiket"
            binding.btnBeliTiket.visibility = View.INVISIBLE
        } else {
            binding.btnBeliTiket.text = "Beli Tiket($total)"
            binding.btnBeliTiket.visibility = View.VISIBLE
        }

    }
}