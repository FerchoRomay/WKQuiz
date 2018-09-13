package com.ruzaaniapps.wkquiz.datamanager

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "vocabulary")
class EntityVocabulary {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var level: Int = 1

    @ColumnInfo(name = "character")
    var word: String = ""

    var kana: String = ""

    var meaning: String = ""

    var srs: String = ""

    @ColumnInfo(name = "user_synonyms")
    var userSynonyms: String = ""
}