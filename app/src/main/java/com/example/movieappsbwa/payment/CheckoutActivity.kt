package com.example.movieappsbwa.payment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieappsbwa.R
import com.example.movieappsbwa.adapter.CheckoutAdapter
import com.example.movieappsbwa.databinding.ActivityCheckoutBinding
import com.example.movieappsbwa.home.ResultTicketActivity
import com.example.movieappsbwa.payment.success.SuccessPayment
import com.example.movieappsbwa.utils.Preference
import com.example.movieappsbwa.utils.model.Checkout
import com.example.movieappsbwa.utils.model.Film
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding

    private var total: Int = 0
    private var dataList = ArrayList<Checkout>()
    private lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = Preference(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        val data = intent.getParcelableExtra<Film>("datas")

        for (a in dataList.indices) {
            total += dataList[a].harga!!.toInt()
        }
        dataList.add(Checkout("Total Harus di Bayar", total.toString()))

        binding.btnBayarSekarang.setOnClickListener {
            val onSuccess = Intent(this, SuccessPayment::class.java)
            startActivity(onSuccess)
            showNotification(data)
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnBatal.setOnClickListener {
            finish()
        }

        binding.rcCheckout.layoutManager = LinearLayoutManager(this)
        binding.rcCheckout.adapter = CheckoutAdapter(dataList) {

        }
        if (preference.getValue("saldo")!!.isNotEmpty()) {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            binding.tvSaldo.text = formatRupiah.format(preference.getValue("saldo")!!.toDouble())
            binding.btnBayarSekarang.visibility = View.VISIBLE
            binding.textBalanced.visibility = View.INVISIBLE

        } else {
            binding.tvSaldo.text = "Rp 0"
            binding.btnBayarSekarang.visibility = View.INVISIBLE
            binding.textBalanced.visibility = View.VISIBLE
            binding.textBalanced.text = "Saldo pada e-wallet kamu tidak mencukupi\n" +
                    "untuk melakukan transaksi"
        }
    }

    private fun showNotification(datas: Film?) {
        val NOTIFICATION_CHANNEL_ID = "channel_bwa_notif"
        val context = this.applicationContext
        var notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "BWAMOV Notif Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)

        }
        val mIntent = Intent(this, ResultTicketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", datas)
        mIntent.putExtras(bundle)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.logo_mov)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.logo_notification
                )
            )
            .setTicker("notif bwa starting")
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.RED, 3000, 3000)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Sukses Terbeli")
            .setContentText("Tiket " + datas?.judul + " berhasil kamu dapatkan. Enjoy the movie!")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115, builder.build())
    }
}