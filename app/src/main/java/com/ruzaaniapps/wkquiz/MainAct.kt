package com.ruzaaniapps.wkquiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import org.jetbrains.anko.toast

class MainAct : AppCompatActivity(),
        SettingsFrag.OnFragmentInteractionListener {

    private val navigationBar by lazy { findViewById<BottomNavigationView>(R.id.navigationBar) }
    private val mItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        lateinit var fragment: Fragment
        when (item.itemId) {
            R.id.navigation_item_home -> {
                fragment = HomeFrag.newInstance()
            }
            R.id.navigation_item_kanji -> {
                fragment = KanjiFrag.newInstance(prefsOnyomiScript, prefsKanjiColumns)
            }
            R.id.navigation_item_vocabulary -> {
                fragment = VocabularyFrag.newInstance()
            }
            R.id.navigation_item_settings -> {
                fragment = SettingsFrag.newInstance(prefsOnyomiScript, prefsKanjiColumns, prefsSeeAllLevels)
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
        checkIfApiKeyExists()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        openDefaultFragment()
        navigationBar.setOnNavigationItemSelectedListener(mItemSelectedListener)
    }

    override fun onPause() {
        super.onPause()
        val prefsFileName = getString(R.string.app_prefs_file)
        val prefsFile = getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
        saveSharedPreferences(prefsFile)
    }

    private fun checkIfApiKeyExists() {
        val prefsFileName = getString(R.string.app_prefs_file)
        val prefsFile = getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
        //If it doesn't exists then the loginAct is started to register the API Key
        if (!prefsFile.contains(getString(R.string.app_prefs_user_api_key))) {
            Log.wtf("", "Starting LoginAct")
            //Saves the default shared preferences values
            saveSharedPreferences(prefsFile)
            startActivity(Intent(this, LoginAct::class.java))
        }
        //Loads the preferences for later usage
        prefsOnyomiScript = prefsFile?.getString(getString(R.string.app_prefs_onyomi_script), prefsOnyomiScript).toString()
        prefsKanjiColumns = prefsFile.getInt(getString(R.string.app_prefs_kanji_columns), 3)
        prefsSeeAllLevels = prefsFile.getBoolean((getString(R.string.app_prefs_see_all_levels)),false)
    }

    private fun saveSharedPreferences(prefs: SharedPreferences) {
        prefs.edit().putString(getString(R.string.app_prefs_onyomi_script), prefsOnyomiScript).apply()
        prefs.edit().putInt(getString(R.string.app_prefs_kanji_columns), prefsKanjiColumns).apply()
        prefs.edit().putBoolean(getString(R.string.app_prefs_see_all_levels), prefsSeeAllLevels).apply()
    }

    private fun openDefaultFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentFrame, HomeFrag.newInstance())
                .commit()
    }

    override fun onBackPressed() {
        if (back_pressed_time + PERIOD > System.currentTimeMillis())
            super.onBackPressed()
        else
            toast(resources.getString(R.string.double_click_exit))
        back_pressed_time = System.currentTimeMillis()
    }

    override fun onFragmentInteraction(uri: Uri) {
        //you can leave it empty
    }

    companion object {
        var prefsOnyomiScript = "hiragana"
        var prefsKanjiColumns = 3
        var prefsSeeAllLevels = false

        //Double click to exit values
        private const val PERIOD = 2000L
        private var back_pressed_time = 0L
    }
}
