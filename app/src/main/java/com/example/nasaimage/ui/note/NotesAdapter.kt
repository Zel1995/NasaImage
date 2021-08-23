package com.example.nasaimage.ui.note

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaimage.R
import com.example.nasaimage.domain.model.NoteEntity

class NotesAdapter(private val onItemClickListener: OnNoteItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    private val data = mutableListOf<NoteEntity>()


    fun setNotes(setData: List<NoteEntity>) {
        val callback = NotesDiffUtilCallback(data, setData)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(setData)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < data.size && fromPosition >= 0 && toPosition >= 0 && toPosition < data.size) {
            data.removeAt(fromPosition).apply {
                data.add(
                    if (toPosition > fromPosition) toPosition - 1 else toPosition,
                    this
                )
            }
            notifyItemMoved(fromPosition, toPosition)
            onItemClickListener.swapItems(data[fromPosition], data[toPosition])

        }
    }

    override fun onItemDismiss(position: Int) {
        onItemClickListener.itemDelete(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoteViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ItemTouchHelperViewHolder {
        private val noteTitle = itemView.findViewById<TextView>(R.id.note_title)
        private val noteContent = itemView.findViewById<TextView>(R.id.note_content)
        private val deleteImg = itemView.findViewById<ImageView>(R.id.delete_img)
        private val downImg = itemView.findViewById<ImageView>(R.id.move_item_down)
        private val upImg = itemView.findViewById<ImageView>(R.id.move_item_up)
        private val mainLayout = itemView.findViewById<ConstraintLayout>(R.id.note_item_layout)

        fun bind(noteEntity: NoteEntity) {
            noteTitle.text = noteEntity.title
            noteContent.text = noteEntity.content
            noteContent.visibility =
                if (data[layoutPosition].isExpanded) View.VISIBLE else View.GONE
            setListeners()

        }

        private fun setListeners() {
            deleteImg.setOnClickListener {
                onItemClickListener.itemDelete(data[layoutPosition])
            }
            noteTitle.setOnClickListener {
                val note = data[layoutPosition]
                val newNote = note.copy(isExpanded = note.isExpanded.not())
                onItemClickListener.itemChanged(newNote)
            }
            upImg.setOnClickListener {
                layoutPosition.takeIf { it > 0 }?.also {
                    onItemMove(it, it - 1)
                }

            }
            downImg.setOnClickListener {
                layoutPosition.takeIf { it < data.size - 1 }?.also {
                    onItemMove(it, it + 1)
                }
            }
        }

        override fun onItemSelected() {
            mainLayout.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            mainLayout.setBackgroundColor(0)
        }
    }

    class NotesDiffUtilCallback(
        private val oldList: List<NoteEntity>,
        private val newList: List<NoteEntity>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

    }
}

interface OnNoteItemClickListener {
    fun itemDelete(noteEntity: NoteEntity)
    fun getItems()
    fun itemChanged(newNote: NoteEntity)
    fun swapItems(noteOne: NoteEntity, noteTwo: NoteEntity)
}