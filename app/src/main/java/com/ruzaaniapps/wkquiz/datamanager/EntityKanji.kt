package com.ruzaaniapps.wkquiz.datamanager

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "kanji")
class EntityKanji {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var level: Int = 1

    @ColumnInfo(name = "character")
    var kanji: String = ""

    var meaning: String = ""

    var onyomi: String = ""

    var kunyomi: String = ""

    @ColumnInfo(name = "important_reading")
    var importantReading: String = ""

    var nanori: String = ""

    var srs: String = ""

    @ColumnInfo(name = "user_synonyms")
    var userSynonyms: String = ""
}