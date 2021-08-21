package com.example.nasaimage.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaimage.domain.model.NoteEntity
import com.example.nasaimage.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val localRepository: LocalRepository) : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteEntity>>(listOf())
    val notes = _notes

    fun fetchNotes() {
        viewModelScope.launch {
            val result = localRepository.getNotes()
            _notes.value = result
        }
    }

    fun addNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            localRepository.addNote(noteEntity)
            fetchNotes()
        }
    }

    fun updateNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            localRepository.updateNote(noteEntity)
            fetchNotes()
        }
    }

    fun deleteNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            localRepository.deleteNote(noteEntity)
        }
    }

    fun swapNotes(noteOne: NoteEntity, noteTwo: NoteEntity) {
        viewModelScope.launch {
            localRepository.deleteNote(noteOne)
            localRepository.addNote(noteTwo.copy(id = noteOne.id))
            localRepository.addNote(noteOne.copy(id = noteTwo.id))
        }
    }
}