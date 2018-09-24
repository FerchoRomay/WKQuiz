package com.ruzaaniapps.wkquiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.wtf("MainAct","starting to check for the shared preferences")
        setTheme(R.style.WKTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        checkIfApiKeyExists()
    }

    private fun checkIfApiKeyExists() {
        val prefsFileName = R.string.app_prefs_file.toString()
        val prefsFile = getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
        //If it doesn't exists then the loginAct is started to register the API Key
        if (!prefsFile.contains(R.string.app_prefs_user_api_key.toString())) {
            Log.wtf("", "Starting LoginAct")
            saveDefaultSettings(prefsFile)
            startActivity(Intent(this, LoginAct::class.java))
        }
    }

    private fun saveDefaultSettings(prefs: SharedPreferences) {
        prefs.edit().putString(R.string.app_prefs_onyomi_script.toString(), R.string.app_prefs_hiragana.toString()).apply()
        prefs.edit().putInt(R.string.app_prefs_kanji_columns.toString(), 3).apply()
        prefs.edit().putBoolean(R.string.app_prefs_see_all_levels.toString(), false).apply()
    }
}
