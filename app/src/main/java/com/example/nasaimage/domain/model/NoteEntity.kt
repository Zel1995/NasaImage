package com.example.nasaimage.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title:String,
    val content:String,
    val date:String,
    val isExpanded: Boolean = false
)