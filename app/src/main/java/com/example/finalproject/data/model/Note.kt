package com.example.finalproject.data.model

import androidx.room.*
import java.util.Date

@Entity(tableName = "notes_table", indices = [Index(value = ["title"],
    unique = true)], foreignKeys = [
    ForeignKey(entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"])])
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Long,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "note") var note:String = "",
    @ColumnInfo(name = "createdAt") val createdAt:String,
    @ColumnInfo(name = "author") var author:String? = "Author",
    @ColumnInfo(name = "parent_task") var parentTask:Long? = null,
    @ColumnInfo(name = "category_id") var categorie: Long? = null
)
