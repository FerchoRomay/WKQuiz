package com.ruzaaniapps.wkquiz.datamanager

import com.google.gson.Gson
import java.net.URL

/**
 * Created by Fer on 11/01/2018.
 *
 */

class JsonRequest(private val api: String) {

    fun getAllKanji(): WkResultKanji {
        val wkJsonStr = URL("$url/$api/kanji/$allLevels").readText()
        return Gson().fromJson<WkResultKanji>(wkJsonStr, WkResultKanji::class.java)
    }

    fun getAllVocabulary(): WkResultVocabulary {
        val wkJsonStr = URL( "$url/$api/vocabulary/$allLevels").readText()
        return Gson().fromJson<WkResultVocabulary>(wkJsonStr, WkResultVocabulary::class.java)
    }

    companion object {
        private val allLevels = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20," +
                "21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40," +
                "41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60"
        private val url = "https://www.wanikani.com/api/v1.4/user"

        data class WkResultKanji(val user_information: User,
                                 val requested_information: List<InfoKanji>)

        data class WkResultVocabulary(val user_information: User,
                                      val requested_information: List<InfoWord>)

        data class User(val username: String, val gravatar: String, val level: Int,
                        val title: String, val about: String?, val website: String?,
                        val twitter: String?, val topics_count: Int, val posts_count: Int,
                        val creation_date: Long, val vacation_date: Long)

        data class InfoKanji(val level: Int, val character: String, val meaning: String,
                             val onyomi: String?, val kunyomi: String?, val important_reading: String,
                             val nanori: String?)

        data class InfoWord(val level: Int, val character: String, val kana: String,
                            val meaning: String)

        /*data class Specific(val srs: String, val srs_numeric: Int, val unlocked_date: Long,
                            val available_date: Long, val burned: String, val burned_date: Long,
                            val meaning_correct: Int, val meaning_incorrect: Int,
                            val meaning_max_streak: Int, val meaning_current_streak: Int,
                            val reading_correct: Int, val reading_incorrect: Int,
                            val reading_max_streak: Int, val reading_current_streak: Int,
                            val meaning_note: String?, val reading_note: String?,
                            val user_synonyms: String)*/
    }
}