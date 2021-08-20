package com.example.nasaimage.data.repository

import com.example.nasaimage.data.storage.NoteDao
import com.example.nasaimage.domain.model.NoteEntity
import com.example.nasaimage.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val noteDao:NoteDao):LocalRepository {
    override suspend fun getNote(id: Int): NoteEntity {
        return noteDao.selectNoteById(id)
    }

    override suspend fun getNotes(): List<NoteEntity> {
        return noteDao.getNotes()
    }

    override suspend fun addNote(noteEntity: NoteEntity) {
        noteDao.addNote(noteEntity)
    }

    override suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDao.deleteNote(noteEntity)
    }

    override suspend fun updateNote(noteEntity: NoteEntity) {
        noteDao.updateNote(noteEntity)
    }
}