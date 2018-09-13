package com.ruzaaniapps.wkquiz.datamanager

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user_information")
class EntityUser {
    @PrimaryKey
    var id: Int = 1

    var username: String = ""

    var gravatar: String = ""

    var level: Int = 1

    var title: String = ""
}