package com.ruzaaniapps.wkquiz.datamanager

import android.arch.persistence.room.*

@Dao interface WkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: EntityUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKanji(kanji: EntityKanji)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVocabulary(item: EntityVocabulary)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllKanji(list: List<EntityKanji>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllVocabulary(list: List<EntityVocabulary>)

    @Query("SELECT * FROM user_information WHERE id = 1")
    fun getUserInfo(): EntityUser

    @Query("SELECT * FROM kanji WHERE id = :id")
    fun getKanji(id: Int): EntityKanji

    @Query("SELECT * FROM vocabulary WHERE id = :id")
    fun getWord(id: Int): EntityVocabulary

    @Query("SELECT * FROM kanji WHERE level IN (:levels)")
    fun getKanjiByLevel(levels: List<Int>): List<EntityKanji>

    @Query("SELECT * FROM kanji WHERE level <= :level AND srs = ''")
    fun getKanjiUnseen(level: Int): List<EntityKanji>

    @Query("SELECT * FROM kanji")
    fun getAllKanji(): List<EntityKanji>

    @Query("SELECT * FROM vocabulary WHERE level IN (:levels)")
    fun getVocabularyByLevel(levels: List<Int>): List<EntityVocabulary>

    @Query("SELECT * FROM vocabulary WHERE level <= :level AND srs = ''")
    fun getVocabularyUnseen(level: Int): List<EntityVocabulary>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun replaceUserInfo(userInfo: EntityUser)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun replaceKanjiInfo(kanjiInfo: EntityKanji)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun replaceWordInfo(wordInfo: EntityVocabulary)
}