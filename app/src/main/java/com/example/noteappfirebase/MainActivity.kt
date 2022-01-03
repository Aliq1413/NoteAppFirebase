package com.example.noteappfirebase
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class MainActivity : AppCompatActivity() {
    private val myViewModel by lazy { ViewModelProvider(this).get(ViewModel::class.java) }
    lateinit var rvNotes: RecyclerView
    lateinit var edtNote: EditText
    lateinit var notesArray: List<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel.getNotesList().observe(this){
            updtRC(it)
        }
        myViewModel.getnotes()

        edtNote=findViewById(R.id.edtNote)
        rvNotes=findViewById(R.id.rvNotes)
        notesArray = listOf()

        var subBtn=findViewById(R.id.submBtn) as Button

        subBtn.setOnClickListener {
            val nota = edtNote.text.toString()
            if (nota.isNotEmpty()){
                myViewModel.addNote(Notes("",nota))
                Toast.makeText(this, "note added successfully", Toast.LENGTH_LONG).show()
                edtNote.text.clear()
                edtNote.clearFocus()
            }
            else{
                Toast.makeText(this,"please add note first", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updtRC(lsNote : List<Notes> ){
        rvNotes.adapter = RVAdapter(this,lsNote)
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    fun DialogEdit(note : Notes){
        val dialogBuilder = AlertDialog.Builder(this)
        val newNote = EditText(this)
        newNote.hint = "Enter new note"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    dialog, id->
                val newNote = newNote.text.toString()
                if(newNote.isNotEmpty()){
                    myViewModel.updtNote(note.id, newNote)
                }
                else{
                    Toast.makeText(applicationContext, "You cannot update it with empty!", Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(newNote)
        alert.show()
    }

    fun DialogDel(note : Notes ){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure?")
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id-> myViewModel.delNote(note.id)

            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Delete Note")
        alert.show()
    }
}