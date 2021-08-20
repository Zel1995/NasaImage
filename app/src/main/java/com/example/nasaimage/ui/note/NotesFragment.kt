package com.example.nasaimage.ui.note

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.nasaimage.MainActivity
import com.example.nasaimage.R
import com.example.nasaimage.databinding.FragmentNotesBinding
import com.example.nasaimage.domain.model.NoteEntity
import com.example.nasaimage.domain.repository.LocalRepository
import com.example.nasaimage.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NotesFragment : Fragment(R.layout.fragment_notes) {

    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val adapter: NotesAdapter = NotesAdapter(object : OnNoteItemClickListener {
        override fun itemDelete(noteEntity: NoteEntity) {
            viewModel.deleteNote(noteEntity)
            viewModel.fetchNotes()
        }

        override fun getItems() = viewModel.fetchNotes()
        override fun itemChanged(newNote: NoteEntity) {
            viewModel.updateNote(newNote)
        }

        override fun swapItems(noteOne: NoteEntity, noteTwo: NoteEntity) {
            viewModel.swapNotes(noteOne, noteTwo)
            viewModel.fetchNotes()
        }
    })

    @Inject
    lateinit var notesViewModelFactory: NotesViewModelFactory
    val viewBinding: FragmentNotesBinding by viewBinding(FragmentNotesBinding::bind)
    val viewModel: NotesViewModel by viewModels { notesViewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? MainActivity)?.mainSubcomponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.fetchNotes()
        initAdapter()
        initViewModel()
        initListeners()
        setMenuListener()
        bottomSheetBehavior = BottomSheetBehavior.from(viewBinding.bottomBehaviorAddNote)
    }

    private fun setMenuListener() {
        viewBinding.notesToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add_note) {
                showBottomSheetBehavior()
                true
            } else {
                false
            }
        }
    }

    private fun initListeners() {
        with(viewBinding) {
            saveNoteBtn.setOnClickListener {
                val date =
                    SimpleDateFormat("yyyy/MM/dd HH:mm:ss").apply { format(Date(System.currentTimeMillis())) }
                val note = NoteEntity(
                    0,
                    editTextTitle.text.toString(),
                    editTextContent.text.toString(),
                    date.toString(),
                    false
                )
                if (!note.title.isBlank()) {
                    viewModel.addNote(note)
                    editTextTitle.setText("")
                    editTextContent.setText("")
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                } else {
                    Toast.makeText(requireContext(), "Введите название заметки", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun initAdapter() {
        with(viewBinding) {
            notesRv.adapter = adapter
            ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(notesRv)
        }
    }

    private fun showBottomSheetBehavior() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notes.collect {
                    adapter.setNotes(it)
                }
            }
        }
    }
}

class NotesViewModelFactory @Inject constructor(private val localRepository: LocalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(localRepository) as T
    }
}