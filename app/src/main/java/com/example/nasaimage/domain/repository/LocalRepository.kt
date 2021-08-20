package com.example.nasaimage.domain.repository

import com.example.nasaimage.domain.model.NoteEntity

interface LocalRepository {
    suspend fun getNote(id:Int):NoteEntity

    suspend fun getNotes():List<NoteEntity>

    suspend fun addNote(noteEntity: NoteEntity)

    suspend fun deleteNote(noteEntity: NoteEntity)

    suspend fun updateNote(noteEntity: NoteEntity)

}