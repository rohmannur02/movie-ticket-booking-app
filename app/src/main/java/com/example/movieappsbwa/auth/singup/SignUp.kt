package com.example.movieappsbwa.auth.singup

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappsbwa.auth.signin.User
import com.example.movieappsbwa.databinding.ActivitySignUpBinding
import com.example.movieappsbwa.utils.Preference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var preference: Preference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preference = Preference(this)

        mDatabase = FirebaseDatabase.getInstance().reference
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")


        binding.btnMasuk.setOnClickListener {
            val uUsername = binding.edtUsername.text.toString()
            val uEmail = binding.edtEmail.text.toString()
            val uName = binding.edtNama.text.toString()
            val uPassword = binding.edtPassword.text.toString()

            if (uUsername.isEmpty()) {
                showError(binding.edtUsername, "Silahkan isi Username")
            } else if (uPassword.isEmpty()) {
                showError(binding.edtPassword, "Silahkan isi Password")
            } else if (uName.isEmpty()) {
                showError(binding.edtNama, "Silahkan isi Nama")
            } else if (uEmail.isEmpty()) {
                showError(binding.edtEmail, "Silahkan isi Email")
            } else {
                saveUser(uUsername, uPassword, uName, uEmail)
            }
        }
    }

    private fun saveUser(uUsername: String, uPassword: String, uName: String, uEmail: String) {
        val user = User()
        user.email = uEmail
        user.nama = uName
        user.username = uUsername
        user.password = uPassword

        if (uUsername != null) {
            checkUsername(uUsername, user)
        }


    }

    private fun checkUsername(uUsername: String, data: User) {
        mFirebaseDatabase.child(uUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mFirebaseDatabase.child(uUsername).setValue(data)

                    preference.setValue("nama", data.nama.toString())
                    preference.setValue("user", data.username.toString())
                    preference.setValue("saldo", "")
                    preference.setValue("url", "")
                    preference.setValue("email", data.email.toString())
                    preference.setValue("status", "1")

                    val intent = Intent(this@SignUp, SignUpPhotoScreen::class.java).putExtra("nama",data.nama)
                    startActivity(intent)
//                    moveToSignUpPhotoScreen()
                } else {
                    Toast.makeText(this@SignUp, "user sudah di gunakan!!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUp, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun moveToSignUpPhotoScreen() {

    }


    private fun showError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}
