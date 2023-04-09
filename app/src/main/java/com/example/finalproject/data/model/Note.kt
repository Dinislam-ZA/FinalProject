package com.example.finalproject.data.model

import androidx.room.*
import java.util.Date

@Entity(tableName = "notes_table", indices = [Index(value = ["title"],
    unique = true)], foreignKeys = [
    ForeignKey(entity = Categorie::class,
        parentColumns = ["id"],
        childColumns = ["categorie_id"])])
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Long?,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "note") var note:String? = "",
    @ColumnInfo(name = "createdAt") var createdAt:String,
    @ColumnInfo(name = "categorie_id") var categorie: Long? = null
)
