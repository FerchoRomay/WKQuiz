package com.ruzaaniapps.wkquiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
//import android.support.v7.widget.Toolbar
import android.util.Log

class MainAct : AppCompatActivity(),
        KanjiFrag.OnFragmentInteractionListener,
        VocabularyFrag.OnFragmentInteractionListener {

    //private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val navigationBar by lazy { findViewById<BottomNavigationView>(R.id.navigationBar) }
    private val mItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment : Fragment = VocabularyFrag.newInstance()
        when (item.itemId) {
            R.id.navigation_item_home -> {
                //TODO: Add Functionality
            }
            R.id.navigation_item_kanji -> {
                fragment = KanjiFrag.newInstance(prefsOnyomiScript, prefsKanjiColumns)
            }
            R.id.navigation_item_vocabulary -> {
                fragment = VocabularyFrag.newInstance()
            }
            R.id.navigation_item_settings -> {
                //TODO: Add Functionality
            }
            else ->
                return@OnNavigationItemSelectedListener false
        }
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .commit()
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.WKTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        checkIfApiKeyExists()
        navigationBar.setOnNavigationItemSelectedListener(mItemSelectedListener)
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
        //Saves the preferences for later usage
        prefsOnyomiScript = prefsFile?.getString(R.string.app_prefs_onyomi_script.toString(), R.string.app_prefs_hiragana.toString()).toString()
        prefsKanjiColumns = prefsFile.getInt(R.string.app_prefs_kanji_columns.toString(), 3)
        prefsSeeAllLevels = prefsFile.getBoolean((R.string.app_prefs_see_all_levels.toString()),false)
    }

    private fun saveDefaultSettings(prefs: SharedPreferences) {
        prefs.edit().putString(R.string.app_prefs_onyomi_script.toString(), R.string.app_prefs_hiragana.toString()).apply()
        prefs.edit().putInt(R.string.app_prefs_kanji_columns.toString(), 3).apply()
        prefs.edit().putBoolean(R.string.app_prefs_see_all_levels.toString(), false).apply()
    }

    override fun onFragmentInteraction(uri: Uri) {
        //you can leave it empty
    }
    companion object {
        var prefsOnyomiScript = ""
        var prefsKanjiColumns = 3
        var prefsSeeAllLevels = false
    }
}
