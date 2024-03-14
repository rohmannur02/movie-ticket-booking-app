package com.example.movieappsbwa.auth.signin

import android.os.Parcel
import android.os.Parcelable


class User(
    var email: String? = "",
    var nama: String? = "",
    var password: String? = "",
    var url: String? = "",
    var username: String? = "",
    var saldo: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(nama)
        parcel.writeString(password)
        parcel.writeString(url)
        parcel.writeString(username)
        parcel.writeString(saldo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}