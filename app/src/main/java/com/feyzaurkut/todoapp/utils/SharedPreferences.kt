package com.feyzaurkut.todoapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(private val context: Context) {

    private fun createPreferences(): SharedPreferences? {
        return context.getSharedPreferences(Constants.FILE_NAME, Context.MODE_PRIVATE)
    }

    private fun createEditor(): SharedPreferences.Editor? {
        val preferences = createPreferences()
        return preferences?.edit()
    }

    fun putColorInt(key: String, value: Int){
        createEditor()?.putInt(key, value)?.apply()
    }

    fun getColorInt(key: String): Int{
        return createPreferences()?.getInt(key, -16777216) ?: -16777216
    }

    fun putUsernameString(value: String){
        createEditor()?.putString(Constants.USERNAME, value)?.apply()
    }

    fun getUsernameString(): String{
        return createPreferences()?.getString(Constants.USERNAME, "") ?: ""
    }

    fun putCheckboxBoolean(key: String, value: Boolean){
        createEditor()?.putBoolean(key, value)?.apply()
    }

    fun getCheckboxBoolean(key: String): Boolean{
        return createPreferences()?.getBoolean(key, false) ?: false
    }
}