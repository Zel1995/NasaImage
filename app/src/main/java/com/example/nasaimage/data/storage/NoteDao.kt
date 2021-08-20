package com.example.nasaimage.data.storage

import androidx.room.*
import com.example.nasaimage.domain.model.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    suspend fun selectNoteById(id: Int): NoteEntity

    @Query("SELECT * FROM NoteEntity")
    suspend fun getNotes():List<NoteEntity>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)
}