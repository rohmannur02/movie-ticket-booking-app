package com.example.movieappsbwa.auth.singup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieappsbwa.R
import com.example.movieappsbwa.auth.signin.User
import com.example.movieappsbwa.databinding.ActivitySignUpPhotoScreenBinding
import com.example.movieappsbwa.home.HomeActivity
import com.example.movieappsbwa.utils.Preference
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.util.UUID


private lateinit var progressDialog: ProgressDialog


class SignUpPhotoScreen : AppCompatActivity(), PermissionListener, MultiplePermissionsListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    private lateinit var binding: ActivitySignUpPhotoScreenBinding

    private lateinit var user: User
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPhotoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = Preference(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")


        binding.textSelamatDatang.text = "Hallo, Selamat Datang\n" + intent.getStringExtra("nama")
        binding.ivAdd.setOnClickListener {

            if (statusAdd) {
                statusAdd = false
                binding.btnSave.visibility = View.INVISIBLE
                binding.ivAdd.setImageResource(R.drawable.ic_add)
                binding.imgUserphoto.setImageResource(R.drawable.photo_user)
            } else {
                Dexter.withActivity(this)
                    .withPermissions(android.Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()
            }
        }

        binding.btnUploadNanti.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.btnSave.setOnClickListener {
            if (filePath != null) {
                progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Loading...")
                progressDialog.show()

                var ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                        ref.downloadUrl.addOnSuccessListener {
                            preference.setValue("url", it.toString())

                        }
                        finishAffinity()
                        var intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { TaskSnapshot ->
                        var progress =
                            100.0 * TaskSnapshot.bytesTransferred / TaskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload" + progress.toInt() + "%")
                    }
            }
        }

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        if (response != null) {
            if (response.permissionName == android.Manifest.permission.CAMERA) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                } else {
                    Toast.makeText(
                        this,
                        "Tidak dapat menemukan aplikasi kamera.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak Bisa Menambahkan Foto!!", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }


    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

    }

    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.getData()!!
            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imgUserphoto)

            binding.btnSave.visibility = View.VISIBLE
            binding.ivAdd.setImageResource(R.drawable.ic_btn_delete)
        } else {
            Toast.makeText(this, "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
        }
    }

}