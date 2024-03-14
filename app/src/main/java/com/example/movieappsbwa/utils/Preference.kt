package com.example.movieappsbwa.utils

import android.content.Context
import android.content.SharedPreferences

class Preference(val context: Context) {
    companion object {
        const val USER_PREFF = "user_preff"
    }

    val sharedPref = context.getSharedPreferences(USER_PREFF, 0)

    fun setValue(key: String, value: String) {
        val edit: SharedPreferences.Editor = sharedPref.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun getValue (key: String): String? {
        return sharedPref.getString(key, "")
    }
}