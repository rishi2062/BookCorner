package org.example.project.books.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object StringListTypeConverters {
    @TypeConverter
    fun fromString(string : String) : List<String>{
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun fromList(list : List<String>) : String{
        return Json.encodeToString(list)
    }
}