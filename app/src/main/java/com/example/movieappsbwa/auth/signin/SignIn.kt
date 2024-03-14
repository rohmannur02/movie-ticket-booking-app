package com.example.movieappsbwa.auth.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappsbwa.auth.singup.SignUp
import com.example.movieappsbwa.databinding.ActivitySignInBinding
import com.example.movieappsbwa.home.HomeActivity
import com.example.movieappsbwa.utils.Preference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var preference: Preference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        setContentView(binding.root)
        preference = Preference(this)

        preference.setValue("onboarding", "1")
        if (preference.getValue("status").equals("1")) {
//            movetoSignUpActivity()
            movetoHomeActivity()
        }

        binding.btnMasuk.setOnClickListener {
            val iEmail = binding.edtUserName.text.toString()
            val iPassword = binding.edtPassword.text.toString()

            when {
                iEmail.isBlank() -> {
                    binding.edtUserName.error = " Silahkan Tulis Email atau Username Anda!!"
                    binding.edtUserName.requestFocus()
                }

                iPassword.isBlank() -> {
                    binding.edtPassword.error = " Silahkan Tulis Password Anda!!"
                    binding.edtPassword.requestFocus()
                }

                else -> {
                    pushLogin(iEmail, iPassword)
                }
            }
        }

        binding.btnDaftarBaru.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        // Cek apakah pengguna sudah pernah login sebelumnya atau belum
        if (auth.currentUser != null) {
            // Jika sudah login sebelumnya, langsung arahkan ke halaman HomeActivity
            movetoHomeActivity()
        } else {
            // Jika belum login sebelumnya, biarkan pengguna tetap di halaman SignIn
            // atau tambahkan logika lain sesuai kebutuhan Anda
        }
    }

    private fun movetoHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    private fun movetoSignUpActivity() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }

    private fun pushLogin(iEmail: String, iPassword: String) {
        mDatabase.child(iEmail).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignIn, "User tidak ditemukan!!", Toast.LENGTH_SHORT).show()
                } else {
                    if (user.password.equals(iPassword)) {

                        preference.setValue("nama", user.nama.toString())
                        preference.setValue("username", user.username.toString())
                        preference.setValue("email", user.email.toString())
                        preference.setValue("url", user.url.toString())
                        preference.setValue("saldo", user.saldo.toString())
                        preference.setValue("status", "1")

                        Toast.makeText(this@SignIn, "Selamat Datang", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@SignIn, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignIn, "Password Anda Salah!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SignInActivity", "Error: ${error.message}")
                Toast.makeText(this@SignIn, "User tidak ditemukan!!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}