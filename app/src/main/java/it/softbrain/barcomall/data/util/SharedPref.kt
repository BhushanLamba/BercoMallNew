package it.softbrain.barcomall.data.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPref {

    private const val BERCO_MALL = "bercoMall"
    const val IS_USER_LOGIN="isUserLogin"



    fun saveString(key:String,value:String,context:Context)
    {
        val editor = context.getSharedPreferences(BERCO_MALL, MODE_PRIVATE).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String): String? {
        val sharedPreference = context.getSharedPreferences(BERCO_MALL, MODE_PRIVATE)
        return sharedPreference.getString(key, "")
    }

    fun setBoolean(context: Context, key: String, value: Boolean) {
        val editor = context.getSharedPreferences(BERCO_MALL, MODE_PRIVATE).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String): Boolean {
        val sharedPreference = context.getSharedPreferences(BERCO_MALL, MODE_PRIVATE)

        return sharedPreference.getBoolean(key, false)
    }

}