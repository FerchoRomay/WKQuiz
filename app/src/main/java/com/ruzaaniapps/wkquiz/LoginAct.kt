package com.ruzaaniapps.wkquiz

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ruzaaniapps.wkquiz.datamanager.JsonRequest.Companion.User
import com.ruzaaniapps.wkquiz.datamanager.JsonRequest.Companion.InfoKanji
import com.ruzaaniapps.wkquiz.datamanager.JsonRequest.Companion.InfoWord
import com.ruzaaniapps.wkquiz.datamanager.JsonRequest.Companion.WkResultKanji
import com.ruzaaniapps.wkquiz.datamanager.JsonRequest.Companion.WkResultVocabulary
import com.ruzaaniapps.wkquiz.datamanager.*
import kotlinx.android.synthetic.main.act_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class LoginAct : AppCompatActivity() {
    private val mToolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)
        setSupportActionBar(mToolbar)
        addListenerOnClickEditText()
        addListenerOnClickButton()
        mToolbar.title = R.string.app_name.toString()
        Log.wtf("LoginAct", "check for saved instance")
        if (savedInstanceState != null)
            loginApiKey.text.append(savedInstanceState.getString("apitext") ?: "")
    }

    override fun onBackPressed() {
        Log.wtf("LoginAct", "Back button pressed")
    }

    private fun addListenerOnClickEditText() {
        loginApiKey.setOnClickListener { _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(loginApiKey, InputMethodManager.SHOW_IMPLICIT)
            Log.wtf("LoginAct", "opening IME")
        }
    }

    private fun addListenerOnClickButton() {
        loginButton.setOnClickListener { _ ->
            var allKanji: WkResultKanji? = null
            var allVocabulary: WkResultVocabulary? = null
            loginProgress.visibility = View.VISIBLE
            loginLayout.visibility = View.GONE
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(loginLayout.windowToken, 0)
            doAsync {
                var jsonError = false
                try {
                    val apiKey = loginApiKey.text.toString()
                    allKanji = JsonRequest(apiKey).getAllKanji()
                    allVocabulary = JsonRequest(apiKey).getAllVocabulary()
                } catch (e: Exception) {
                    jsonError = true
                    uiThread {
                        toast(R.string.error_url)
                        showLoginLayout()
                    }
                }
                if (!jsonError) {
                    doAsync {
                        saveUserInfo(allKanji!!.user_information)
                        saveAllKanji(allKanji!!.requested_information)
                        saveAllVocabulary(allVocabulary!!.requested_information)
                        saveUserApiKey()
                        uiThread { this@LoginAct.finish() }
                    }
                }
            }
        }
    }

    private fun showLoginLayout() {
        loginProgress.visibility = View.GONE
        loginLayout.visibility = View.VISIBLE
    }

    private fun saveUserApiKey() {
        val prefsFileName = R.string.app_prefs_file.toString()
        val prefUserApi = R.string.app_prefs_user_api_key.toString()
        val currentUserApi = loginApiKey.text.toString()
        val prefs = getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
        prefs.edit().putString(prefUserApi, currentUserApi).apply()
    }

    private fun saveUserInfo(userInfo: User) {
        val newUser = EntityUser()
        with(userInfo) {
            newUser.username = username
            newUser.gravatar = ""
            newUser.level = level
        }
        App.datasource!!.wkDao().insertUserInfo(newUser)
    }

    private fun saveAllKanji(kanjiList: List<InfoKanji>) {
        val wordList = mutableListOf<EntityKanji>()
        for (i in kanjiList) {
            val newKanji = EntityKanji()
            with(newKanji) {
                level = i.level
                kanji = i.character
                meaning = i.meaning
                onyomi = i.onyomi ?: ""
                kunyomi = i.kunyomi ?: ""
                importantReading = i.important_reading
                nanori = i.nanori ?: ""
            }
            wordList.add(newKanji)
            Log.v("", newKanji.kanji)
        }
        App.datasource!!.wkDao().insertAllKanji(wordList)
    }

    private fun saveAllVocabulary(kanjiList: List<InfoWord>) {
        val wordList = mutableListOf<EntityVocabulary>()
        for (i in kanjiList) {
            val newWord = EntityVocabulary()
            with(newWord) {
                level = i.level
                word = i.character
                kana = i.kana
                meaning = i.meaning
            }
            wordList.add(newWord)
        }
        App.datasource!!.wkDao().insertAllVocabulary(wordList)
    }
}