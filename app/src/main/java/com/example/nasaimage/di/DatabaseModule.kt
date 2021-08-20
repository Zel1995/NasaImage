package com.example.nasaimage.di

import android.app.Application
import androidx.room.Room
import com.example.nasaimage.data.storage.NoteDao
import com.example.nasaimage.data.storage.NoteDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesNotesDatabase(app: Application):NoteDataBase =
        Room.databaseBuilder(app, NoteDataBase::class.java , "NoteDataBase").fallbackToDestructiveMigration().build()

    @Provides
    fun providesNotesDao(dataBase: NoteDataBase):NoteDao = dataBase.getNoteDao()
}