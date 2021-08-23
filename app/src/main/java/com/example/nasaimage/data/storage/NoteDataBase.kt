package com.example.nasaimage.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nasaimage.domain.model.NoteEntity

@Database(entities = [NoteEntity::class],version = 2,exportSchema = false)
abstract class NoteDataBase : RoomDatabase(){
    abstract fun getNoteDao():NoteDao
}